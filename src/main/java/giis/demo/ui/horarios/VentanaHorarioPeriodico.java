package giis.demo.ui.horarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.horarios.GestionHorariosPeriodicos;
import giis.demo.model.horarios.HorarioPeriodico;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.SwingUtil;
import giis.demo.util.UnexpectedException;

public class VentanaHorarioPeriodico extends JFrame {

	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private GestionHorariosPeriodicos ghp;

	private JComboBox cbDia;
	private JSpinner spHoraInicio;
	private JSpinner spMinInicio;
	private JSpinner spHoraFin;
	private JSpinner spMinFin;
	private JButton btnGuardar;
	private JLabel lblEmpleados;
	private JLabel lblDiaDeSemana;
	private JLabel lblHoraInicio;
	private JLabel lblMinutoInicio;
	private JLabel lblHoraFinal;
	private JLabel lblMinutoFinal;

	private JTable tablaEmpleadosNoDeportivos;
	private JTable tablaHorariosPeriodicos;
	private DefaultTableModel modeloEmpleados;
	private DefaultTableModel modeloHorarios;
	private JButton btnAtras;
	private JLabel lblHorariosDeEmpleado;
	private JScrollPane scrollPaneEmpleadosNoDep;
	private JScrollPane scrollPaneHorarioPeriodico;
	private JLabel lblHorasSemanaTexto;
	private JLabel lblHorasSemana;

	public VentanaHorarioPeriodico(VentanaPrincipal vp) {
		this.vp = vp;
		this.ghp = vp.getGestionHorariosPeriodicos();

		setTitle("Añadir Horario Periódico");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 480);
		setMinimumSize(new Dimension(680, 400));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 15, 10, 15));
		setContentPane(contentPane);

		// 1. ZONA NORTE: Formulario para añadir turno
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
		panelNorte.add(getLblDiaDeSemana());
		panelNorte.add(getCbDia());
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

		// 2. ZONA CENTRO: Dos columnas (50% empleados, 50% horarios)
		JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 0));

		JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 5));
		panelIzquierdo.add(getLblEmpleados(), BorderLayout.NORTH);
		panelIzquierdo.add(getScrollPaneEmpleadosNoDep(), BorderLayout.CENTER);
		panelCentro.add(panelIzquierdo);

		JPanel panelDerecho = new JPanel(new BorderLayout(0, 5));
		panelDerecho.add(getLblHorariosDeEmpleado(), BorderLayout.NORTH);
		panelDerecho.add(getScrollPaneHorarioPeriodico(), BorderLayout.CENTER);
		panelCentro.add(panelDerecho);

		contentPane.add(panelCentro, BorderLayout.CENTER);

		// 3. ZONA SUR: Contador de horas a la izquierda y botón Atrás a la derecha
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelHoras = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelHoras.add(getLblHorasSemanaTexto());
		panelHoras.add(getLblHorasSemana());

		panelSur.add(panelHoras, BorderLayout.WEST);
		panelSur.add(getBtnAtras(), BorderLayout.EAST);
		contentPane.add(panelSur, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		inicializarTablasYEventos();
	}

	public JComboBox getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox();
			cbDia.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" }));
			cbDia.setPreferredSize(new Dimension(95, 26));
		}
		return cbDia;
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

		String dni = (String) getTablaEmpleadosNoDeportivos().getValueAt(filaSeleccionada, 0);
		int dia = getCbDia().getSelectedIndex() + 1; // 1 = Lunes, ..., 7 = Domingo
		String horaInicio = String.format("%02d:%02d", (int) getSpHoraInicio().getValue(),
				(int) getSpMinInicio().getValue());
		String horaFin = String.format("%02d:%02d", (int) getSpHoraFin().getValue(), (int) getSpMinFin().getValue());

		HorarioPeriodico nuevoHorario = new HorarioPeriodico(0,dni, dia, horaInicio, horaFin);

		try {
			ghp.validarYAnadirHorario(nuevoHorario);
			JOptionPane.showMessageDialog(this, "Horario periódico añadido con éxito.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			cargarHorarios(dni);
			actualizarHorasSemana(dni);
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

	private JLabel getLblDiaDeSemana() {
		if (lblDiaDeSemana == null) {
			lblDiaDeSemana = new JLabel("Día:");
		}
		return lblDiaDeSemana;
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
			lblHoraFinal = new JLabel("Hora final:");
		}
		return lblHoraFinal;
	}

	private JLabel getLblMinutoFinal() {
		if (lblMinutoFinal == null) {
			lblMinutoFinal = new JLabel("Min final:");
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

		modeloHorarios = new DefaultTableModel(new Object[] { "Día", "Hora Inicio", "Hora Fin" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaHorariosPeriodicos().setModel(modeloHorarios);

		cargarEmpleados();

		getTablaEmpleadosNoDeportivos().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && getTablaEmpleadosNoDeportivos().getSelectedRow() != -1) {
				String dniSeleccionado = (String) getTablaEmpleadosNoDeportivos()
						.getValueAt(getTablaEmpleadosNoDeportivos().getSelectedRow(), 0);
				cargarHorarios(dniSeleccionado);
				try {
					actualizarHorasSemana(dniSeleccionado);
				} catch (SQLException ex) {
					throw new UnexpectedException(ex);
				}
			}
		});

		getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarDatos()));
	}

	private void cargarEmpleados() {
		modeloEmpleados.setRowCount(0);
		try {
			List<EmpleadoNoDeportivo> empleados = ghp.obtenerEmpleadosNoDeportivos();
			for (EmpleadoNoDeportivo emp : empleados) {
				modeloEmpleados.addRow(new Object[] { emp.getDni(), emp.getNombre(), emp.getApellido() });
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void cargarHorarios(String dni) {
		modeloHorarios.setRowCount(0);
		try {
			List<HorarioPeriodico> horarios = ghp.obtenerHorariosDeEmpleado(dni);
			for (HorarioPeriodico h : horarios) {
				modeloHorarios.addRow(
						new Object[] { obtenerNombreDia(h.getDia_semana()), h.getHora_inicio(), h.getHora_fin() });
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void actualizarHorasSemana(String dni) throws SQLException {
		long minutos = ghp.calcularMinutosSemanales(dni);
		getLblHorasSemana().setText(String.format("%d h %02d min  (máx. 40 h)", minutos / 60, minutos % 60));
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
			lblHorariosDeEmpleado = new JLabel("Horarios del empleado seleccionado:");
			lblHorariosDeEmpleado.setFont(new Font("Tahoma", Font.BOLD, 12));
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

	private JLabel getLblHorasSemanaTexto() {
		if (lblHorasSemanaTexto == null) {
			lblHorasSemanaTexto = new JLabel("Horas semana:");
			lblHorasSemanaTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblHorasSemanaTexto;
	}

	private JLabel getLblHorasSemana() {
		if (lblHorasSemana == null) {
			lblHorasSemana = new JLabel("-");
			lblHorasSemana.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblHorasSemana.setForeground(new Color(0, 102, 204));
		}
		return lblHorasSemana;
	}
}