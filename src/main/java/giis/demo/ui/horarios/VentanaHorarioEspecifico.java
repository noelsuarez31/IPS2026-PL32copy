package giis.demo.ui.horarios;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.horarios.GestionHorariosEspecificos;
import giis.demo.model.horarios.HorarioEspecifico;
import giis.demo.model.horarios.HorarioPeriodico;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.SwingUtil;
import giis.demo.util.UnexpectedException;

public class VentanaHorarioEspecifico extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String SIN_FECHA = "Ninguno";

	private VentanaPrincipal vp;
	private GestionHorariosEspecificos ghe;

	private JSpinner spHoraInicio;
	private JSpinner spMinInicio;
	private JSpinner spHoraFin;
	private JSpinner spMinFin;
	private JButton btnGuardar;
	private JLabel lblEmpleados;
	private JLabel lblDiaEspecifico;
	private JLabel lblHoraInicio;
	private JLabel lblMinutoInicio;
	private JLabel lblHoraFinal;
	private JLabel lblMinutoFinal;

	private JTable tablaEmpleadosNoDeportivos;
	private JTable tablaHorariosPeriodicos;
	private DefaultTableModel modeloEmpleados;
	private DefaultTableModel modeloHorariosPeriodicos;
	private DefaultTableModel modeloHorariosEspecificos;
	private JButton btnAtras;
	private JLabel lblHorariosDeEmpleado;
	private JScrollPane scrollPaneEmpleadosNoDep;
	private JScrollPane scrollPaneHorarioPeriodico;
	private JButton btnSeleccionarDia;
	private JTextField TxtFecha;
	private JTable tablaHorariosEspecificos;
	private JScrollPane scrollPaneHorarioEspecifico;
	private JLabel lblHorariosEspecificos;

	public VentanaHorarioEspecifico(VentanaPrincipal vp) {
		this.vp = vp;
		this.ghe = vp.getGestionHorariosEspecificos();

		setTitle("Añadir Horario de Día Específico");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 500);
		setMinimumSize(new Dimension(700, 420));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 15, 10, 15));
		setContentPane(contentPane);

		// 1. ZONA NORTE: Formulario horizontal ordenado
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
		panelNorte.add(getBtnSeleccionarDia());
		panelNorte.add(getLblDiaEspecifico());
		panelNorte.add(getTxtFecha());
		panelNorte.add(getLblHoraInicio());
		panelNorte.add(getSpHoraInicio());
		panelNorte.add(getLblMinutoInicio());
		panelNorte.add(getSpMinInicio());
		panelNorte.add(getLblHoraFinal());
		panelNorte.add(getSpHoraFin());
		panelNorte.add(getLblMinutoFinal());
		panelNorte.add(getSpMinFin());
		panelNorte.add(getBtnGuardar());
		contentPane.add(panelNorte, BorderLayout.NORTH);

		// 2. ZONA CENTRO: Dos columnas (50% izquierda empleados, 50% derecha las 2 tablas)
		JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 0));

		// Columna izquierda: Tabla empleados
		JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 5));
		panelIzquierdo.add(getLblEmpleados(), BorderLayout.NORTH);
		panelIzquierdo.add(getScrollPaneEmpleadosNoDep(), BorderLayout.CENTER);
		panelCentro.add(panelIzquierdo);

		// Columna derecha: Dos tablas apiladas en vertical (50% periódico / 50% específico)
		JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 0, 8));

		JPanel subPanelPeriodicos = new JPanel(new BorderLayout(0, 3));
		subPanelPeriodicos.add(getLblHorariosDeEmpleado(), BorderLayout.NORTH);
		subPanelPeriodicos.add(getScrollPaneHorarioPeriodico(), BorderLayout.CENTER);
		panelDerecho.add(subPanelPeriodicos);

		JPanel subPanelEspecificos = new JPanel(new BorderLayout(0, 3));
		subPanelEspecificos.add(getLblHorariosEspecificos(), BorderLayout.NORTH);
		subPanelEspecificos.add(getScrollPaneHorarioEspecifico(), BorderLayout.CENTER);
		panelDerecho.add(subPanelEspecificos);

		panelCentro.add(panelDerecho);
		contentPane.add(panelCentro, BorderLayout.CENTER);

		// 3. ZONA SUR: Botón Atrás
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelSur.add(getBtnAtras());
		contentPane.add(panelSur, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		inicializarTablasYEventos();
	}

	public JSpinner getSpHoraInicio() {
		if (spHoraInicio == null) {
			spHoraInicio = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
			spHoraInicio.setPreferredSize(new Dimension(45, 26));
		}
		return spHoraInicio;
	}

	public JSpinner getSpMinInicio() {
		if (spMinInicio == null) {
			spMinInicio = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
			spMinInicio.setPreferredSize(new Dimension(45, 26));
		}
		return spMinInicio;
	}

	public JSpinner getSpHoraFin() {
		if (spHoraFin == null) {
			spHoraFin = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
			spHoraFin.setPreferredSize(new Dimension(45, 26));
		}
		return spHoraFin;
	}

	public JSpinner getSpMinFin() {
		if (spMinFin == null) {
			spMinFin = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
			spMinFin.setPreferredSize(new Dimension(45, 26));
		}
		return spMinFin;
	}

	public JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.setPreferredSize(new Dimension(85, 28));
		}
		return btnGuardar;
	}

	private void guardarDatos() {
		int filaSeleccionada = getTablaEmpleadosNoDeportivos().getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona un empleado de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String fecha = getTxtFecha().getText();
		if (fecha.equals(SIN_FECHA)) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona un día con el calendario.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String dni = (String) getTablaEmpleadosNoDeportivos().getValueAt(filaSeleccionada, 0);
		String horaInicio = String.format("%02d:%02d", (int) getSpHoraInicio().getValue(),
				(int) getSpMinInicio().getValue());
		String horaFin = String.format("%02d:%02d", (int) getSpHoraFin().getValue(), (int) getSpMinFin().getValue());

		HorarioEspecifico nuevoHorario = new HorarioEspecifico(0,dni, fecha, horaInicio, horaFin);

		try {
			ghe.validarYAnadirHorario(nuevoHorario);
			JOptionPane.showMessageDialog(this, "Horario específico añadido con éxito.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			cargarHorarios(dni);
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private JLabel getLblEmpleados() {
		if (lblEmpleados == null) {
			lblEmpleados = new JLabel("Empleados:");
			lblEmpleados.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblEmpleados;
	}

	private JLabel getLblDiaEspecifico() {
		if (lblDiaEspecifico == null) {
			lblDiaEspecifico = new JLabel("Día:");
		}
		return lblDiaEspecifico;
	}

	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio:");
		}
		return lblHoraInicio;
	}

	private JLabel getLblMinutoInicio() {
		if (lblMinutoInicio == null) {
			lblMinutoInicio = new JLabel("Min inicio:");
		}
		return lblMinutoInicio;
	}

	private JLabel getLblHoraFinal() {
		if (lblHoraFinal == null) {
			lblHoraFinal = new JLabel("Hora fin:");
		}
		return lblHoraFinal;
	}

	private JLabel getLblMinutoFinal() {
		if (lblMinutoFinal == null) {
			lblMinutoFinal = new JLabel("Min fin:");
		}
		return lblMinutoFinal;
	}

	public VentanaPrincipal getVp() {
		return this.vp;
	}

	private JTable getTablaEmpleadosNoDeportivos() {
		if (tablaEmpleadosNoDeportivos == null) {
			tablaEmpleadosNoDeportivos = new JTable();
		}
		return tablaEmpleadosNoDeportivos;
	}

	private JTable getTablaHorariosPeriodicos() {
		if (tablaHorariosPeriodicos == null) {
			tablaHorariosPeriodicos = new JTable();
		}
		return tablaHorariosPeriodicos;
	}

	private void inicializarTablasYEventos() {
		modeloEmpleados = new DefaultTableModel(new Object[] { "DNI", "Nombre", "Apellidos" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaEmpleadosNoDeportivos().setModel(modeloEmpleados);

		modeloHorariosPeriodicos = new DefaultTableModel(new Object[] { "Día", "Hora Inicio", "Hora Fin" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaHorariosPeriodicos().setModel(modeloHorariosPeriodicos);

		modeloHorariosEspecificos = new DefaultTableModel(new Object[] { "Fecha", "Hora Inicio", "Hora Fin" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaHorariosEspecificos().setModel(modeloHorariosEspecificos);

		cargarEmpleados();

		getTablaEmpleadosNoDeportivos().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				refrescarHorariosDelEmpleado();
			}
		});

		getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarDatos()));
		getBtnSeleccionarDia().addActionListener(e -> SwingUtil.exceptionWrapper(() -> abrirDialogoCalendario()));
	}

	private void abrirDialogoCalendario() {
		JCalendar calendario = new JCalendar();
		int opcion = JOptionPane.showConfirmDialog(
				this,
				calendario,
				"Selecciona una fecha",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
		);

		if (opcion == JOptionPane.OK_OPTION) {
			Date fechaSeleccionada = calendario.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			getTxtFecha().setText(sdf.format(fechaSeleccionada));
		}
	}

	private void cargarEmpleados() {
		modeloEmpleados.setRowCount(0);
		try {
			List<EmpleadoNoDeportivo> empleados = ghe.obtenerEmpleadosNoDeportivos();
			for (EmpleadoNoDeportivo emp : empleados) {
				modeloEmpleados.addRow(new Object[] { emp.getDni(), emp.getNombre(), emp.getApellido() });
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void refrescarHorariosDelEmpleado() {
		int fila = getTablaEmpleadosNoDeportivos().getSelectedRow();
		if (fila == -1) {
			modeloHorariosPeriodicos.setRowCount(0);
			modeloHorariosEspecificos.setRowCount(0);
			return;
		}
		String dni = (String) getTablaEmpleadosNoDeportivos().getValueAt(fila, 0);
		cargarHorarios(dni);
	}

	private void cargarHorarios(String dni) {
		modeloHorariosPeriodicos.setRowCount(0);
		modeloHorariosEspecificos.setRowCount(0);
		try {
			List<HorarioPeriodico> periodicos = ghe.obtenerHorariosPeriodicosDeEmpleado(dni);
			for (HorarioPeriodico h : periodicos) {
				modeloHorariosPeriodicos.addRow(new Object[] {
						obtenerNombreDia(h.getDia_semana()), h.getHora_inicio(), h.getHora_fin()
				});
			}

			List<HorarioEspecifico> especificos = ghe.obtenerTodosLosHorariosEspecificosDeEmpleado(dni);
			for (HorarioEspecifico h : especificos) {
				modeloHorariosEspecificos.addRow(new Object[] {
						h.getFecha(), h.getHora_inicio(), h.getHora_fin()
				});
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private String obtenerNombreDia(int dia) {
		String[] dias = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };
		if (dia >= 1 && dia <= 7) {
			return dias[dia];
		} else {
			return "desconocido";
		}
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.setPreferredSize(new Dimension(110, 30));
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					vp.setVisible(true);
				}
			});
		}
		return btnAtras;
	}

	private JLabel getLblHorariosDeEmpleado() {
		if (lblHorariosDeEmpleado == null) {
			lblHorariosDeEmpleado = new JLabel("Horario periódico del empleado:");
			lblHorariosDeEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblHorariosDeEmpleado;
	}

	private JScrollPane getScrollPaneEmpleadosNoDep() {
		if (scrollPaneEmpleadosNoDep == null) {
			scrollPaneEmpleadosNoDep = new JScrollPane();
			scrollPaneEmpleadosNoDep.setViewportView(getTablaEmpleadosNoDeportivos());
		}
		return scrollPaneEmpleadosNoDep;
	}

	private JScrollPane getScrollPaneHorarioPeriodico() {
		if (scrollPaneHorarioPeriodico == null) {
			scrollPaneHorarioPeriodico = new JScrollPane();
			scrollPaneHorarioPeriodico.setViewportView(getTablaHorariosPeriodicos());
		}
		return scrollPaneHorarioPeriodico;
	}

	private JButton getBtnSeleccionarDia() {
		if (btnSeleccionarDia == null) {
			btnSeleccionarDia = new JButton("Seleccionar Día");
			btnSeleccionarDia.setPreferredSize(new Dimension(130, 28));
		}
		return btnSeleccionarDia;
	}

	private JTextField getTxtFecha() {
		if (TxtFecha == null) {
			TxtFecha = new JTextField(SIN_FECHA);
			TxtFecha.setFont(new Font("Tahoma", Font.PLAIN, 10));
			TxtFecha.setEditable(false);
			TxtFecha.setPreferredSize(new Dimension(85, 26));
			TxtFecha.setColumns(10);
		}
		return TxtFecha;
	}

	private JTable getTablaHorariosEspecificos() {
		if (tablaHorariosEspecificos == null) {
			tablaHorariosEspecificos = new JTable();
		}
		return tablaHorariosEspecificos;
	}

	private JLabel getLblHorariosEspecificos() {
		if (lblHorariosEspecificos == null) {
			lblHorariosEspecificos = new JLabel("Horarios específicos del empleado:");
			lblHorariosEspecificos.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblHorariosEspecificos;
	}

	private JScrollPane getScrollPaneHorarioEspecifico() {
		if (scrollPaneHorarioEspecifico == null) {
			scrollPaneHorarioEspecifico = new JScrollPane();
			scrollPaneHorarioEspecifico.setViewportView(getTablaHorariosEspecificos());
		}
		return scrollPaneHorarioEspecifico;
	}
}