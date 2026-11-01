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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.horarios.GestionHorarioSemanal;
import giis.demo.model.horarios.HorarioSemanal;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.SwingUtil;
import giis.demo.util.UnexpectedException;

public class VentanaHorarioSemanal extends JFrame {

	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private GestionHorarioSemanal ghs;

	private JLabel lblEmpleados;
	private JTable tablaEmpleadosNoDeportivos;
	private DefaultTableModel modeloEmpleados;

	private JButton btnSeleccionarDia;
	private JLabel lblFecha;
	private JTextField txtFecha;
	private JLabel lblRangoSemana;

	private JLabel lblHorariosSemana;
	private JTable tablaHorariosSemanales;
	private DefaultTableModel modeloHorariosSemanales;

	private JLabel lblHorasSemanaTexto;
	private JLabel lblHorasSemana;
	private JButton btnAtras;
	private JScrollPane scrollPaneEmpleadosNoDeportivos;
	private JScrollPane scrollPaneHorariosEmpleado;

	public VentanaHorarioSemanal(VentanaPrincipal vp) {
		this.vp = vp;
		this.ghs = vp.getGestionHorarioSemanal();

		setTitle("Consultar Horarios Semanales - Empleados No Deportivos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 480);
		setMinimumSize(new Dimension(650, 400)); // Tamaño mínimo seguro

		// Panel principal con BorderLayout y márgenes
		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 15, 10, 15));
		setContentPane(contentPane);

		// 1. ZONA NORTE: Barra de selección de fecha y rango de la semana
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		panelNorte.add(getBtnSeleccionarDia());
		panelNorte.add(getLblFecha());
		panelNorte.add(getTxtFecha());
		panelNorte.add(getLblRangoSemana());
		contentPane.add(panelNorte, BorderLayout.NORTH);

		// 2. ZONA CENTRO: Dos columnas proporcionales (50% / 50%)
		JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 0));

		// Columna izquierda: Empleados
		JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 5));
		panelIzquierdo.add(getLblEmpleados(), BorderLayout.NORTH);
		panelIzquierdo.add(getScrollPaneEmpleadosNoDeportivos(), BorderLayout.CENTER);
		panelCentro.add(panelIzquierdo);

		// Columna derecha: Horarios de la semana
		JPanel panelDerecho = new JPanel(new BorderLayout(0, 5));
		panelDerecho.add(getLblHorariosSemana(), BorderLayout.NORTH);
		panelDerecho.add(getScrollPaneHorariosEmpleado(), BorderLayout.CENTER);
		panelCentro.add(panelDerecho);

		contentPane.add(panelCentro, BorderLayout.CENTER);

		// 3. ZONA SUR: Resumen de horas a la izquierda y botón Atrás a la derecha
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

	private JLabel getLblEmpleados() {
		if (lblEmpleados == null) {
			lblEmpleados = new JLabel("Empleados:");
			lblEmpleados.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblEmpleados;
	}

	private JTable getTablaEmpleadosNoDeportivos() {
		if (tablaEmpleadosNoDeportivos == null) {
			tablaEmpleadosNoDeportivos = new JTable();
		}
		return tablaEmpleadosNoDeportivos;
	}

	private JButton getBtnSeleccionarDia() {
		if (btnSeleccionarDia == null) {
			btnSeleccionarDia = new JButton("Seleccionar Día");
			btnSeleccionarDia.setPreferredSize(new Dimension(130, 28));
		}
		return btnSeleccionarDia;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
		}
		return lblFecha;
	}

	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField(LocalDate.now().toString());
			txtFecha.setEditable(false);
			txtFecha.setPreferredSize(new Dimension(90, 26));
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}

	private JLabel getLblRangoSemana() {
		if (lblRangoSemana == null) {
			lblRangoSemana = new JLabel("Semana: -");
			lblRangoSemana.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblRangoSemana;
	}

	private JLabel getLblHorariosSemana() {
		if (lblHorariosSemana == null) {
			lblHorariosSemana = new JLabel("Horarios de la semana seleccionada:");
			lblHorariosSemana.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblHorariosSemana;
	}

	private JTable getTablaHorariosSemanales() {
		if (tablaHorariosSemanales == null) {
			tablaHorariosSemanales = new JTable();
		}
		return tablaHorariosSemanales;
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
			lblHorasSemana = new JLabel("0 h 00 min (máx. 40 h)");
			lblHorasSemana.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblHorasSemana.setForeground(new Color(0, 102, 204));
		}
		return lblHorasSemana;
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

	private void inicializarTablasYEventos() {
		modeloEmpleados = new DefaultTableModel(
				new Object[] { "DNI", "Nombre", "Apellidos" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaEmpleadosNoDeportivos().setModel(modeloEmpleados);

		modeloHorariosSemanales = new DefaultTableModel(
				new Object[] { "Día", "Fecha", "Hora Inicio", "Hora Fin", "Duración" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaHorariosSemanales().setModel(modeloHorariosSemanales);

		getTablaEmpleadosNoDeportivos().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				refrescarTablaSemana();
			}
		});

		getBtnSeleccionarDia().addActionListener(e -> SwingUtil.exceptionWrapper(() -> abrirDialogoCalendario()));

		cargarEmpleados();
		actualizarTextoRangoSemana();
	}

	private void abrirDialogoCalendario() {
		JCalendar calendario = new JCalendar();
		int opcion = JOptionPane.showConfirmDialog(
				this,
				calendario,
				"Selecciona una fecha de la semana a consultar",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
		);

		if (opcion == JOptionPane.OK_OPTION) {
			Date fecha = calendario.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			getTxtFecha().setText(sdf.format(fecha));
			actualizarTextoRangoSemana();
			refrescarTablaSemana();
		}
	}

	private void cargarEmpleados() {
		modeloEmpleados.setRowCount(0);
		try {
			List<EmpleadoNoDeportivo> empleados = ghs.obtenerEmpleadosNoDeportivos();
			for (EmpleadoNoDeportivo emp : empleados) {
				modeloEmpleados.addRow(new Object[] {
					emp.getDni(), emp.getNombre(), emp.getApellido()
				});
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void actualizarTextoRangoSemana() {
		String fechaStr = getTxtFecha().getText();
		try {
			LocalDate lunes = ghs.obtenerLunesDeSemana(fechaStr);
			LocalDate domingo = ghs.obtenerDomingoDeSemana(fechaStr);
			getLblRangoSemana().setText("Semana: " + lunes.toString() + " al " + domingo.toString());
		} catch (Exception e) {
			getLblRangoSemana().setText("Semana: -");
		}
	}

	private void refrescarTablaSemana() {
		modeloHorariosSemanales.setRowCount(0);
		int fila = getTablaEmpleadosNoDeportivos().getSelectedRow();

		if (fila == -1) {
			getLblHorasSemana().setText("0 h 00 min (máx. 40 h)");
			return;
		}

		String dni = (String) getTablaEmpleadosNoDeportivos().getValueAt(fila, 0);
		String fechaStr = getTxtFecha().getText();

		try {
			List<HorarioSemanal> turnos = ghs.obtenerHorariosDeSemana(dni, fechaStr);
			for (HorarioSemanal h : turnos) {
				modeloHorariosSemanales.addRow(new Object[] {
					h.getNombreDia(),
					h.getFecha(),
					h.getHoraInicio(),
					h.getHoraFin(),
					h.getDuracionFormateada()
				});
			}

			long totalMinutos = ghs.calcularMinutosTotalesSemana(turnos);
			long horas = totalMinutos / 60;
			long mins = totalMinutos % 60;
			getLblHorasSemana().setText(String.format("%d h %02d min (máx. 40 h)", horas, mins));

		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private JScrollPane getScrollPaneEmpleadosNoDeportivos() {
		if (scrollPaneEmpleadosNoDeportivos == null) {
			scrollPaneEmpleadosNoDeportivos = new JScrollPane();
			scrollPaneEmpleadosNoDeportivos.setViewportView(getTablaEmpleadosNoDeportivos());
		}
		return scrollPaneEmpleadosNoDeportivos;
	}

	private JScrollPane getScrollPaneHorariosEmpleado() {
		if (scrollPaneHorariosEmpleado == null) {
			scrollPaneHorariosEmpleado = new JScrollPane();
			scrollPaneHorariosEmpleado.setViewportView(getTablaHorariosSemanales());
		}
		return scrollPaneHorariosEmpleado;
	}
}