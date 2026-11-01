package giis.demo.ui.mantenimiento;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.horarios.HorarioSemanal;
import giis.demo.model.mantenimiento.GestionTareaMantenimiento;
import giis.demo.model.mantenimiento.TareaMantenimiento;
import giis.demo.model.reserva.Instalacion;
import giis.demo.model.reserva.Intervalo;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.SwingUtil;
import giis.demo.util.UnexpectedException;

public class VentanaAsignarMantenimiento extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String SIN_FECHA = "Ninguno";

	private VentanaPrincipal vp;
	private GestionTareaMantenimiento gtm;

	// Zona Norte: Parámetros en una sola fila compacta (solo horas, minutos siempre :00)
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cbInstalacion;
	private JButton btnSeleccionarDia;
	private JLabel lblFecha;
	private JTextField txtFecha;
	private JLabel lblHoraInicio;
	private JSpinner spHoraInicio;
	private JLabel lblHoraFinal;
	private JSpinner spHoraFin;

	// Zona Centro: 4 tablas en cuadrícula 2x2
	private JLabel lblJardineros;
	private JTable tablaJardineros;
	private DefaultTableModel modeloJardineros;
	private JScrollPane scrollPaneJardineros;

	private JLabel lblTurnoLaboral;
	private JTable tablaTurnosLaborales;
	private DefaultTableModel modeloTurnosLaborales;
	private JScrollPane scrollPaneTurnosLaborales;

	private JLabel lblMantenimientos;
	private JTable tablaMantenimientos;
	private DefaultTableModel modeloMantenimientos;
	private JScrollPane scrollPaneMantenimientos;

	private JLabel lblOcupaciones;
	private JTable tablaOcupaciones;
	private DefaultTableModel modeloOcupaciones;
	private JScrollPane scrollPaneOcupaciones;

	// Zona Sur: Navegación y acción
	private JButton btnAtras;
	private JButton btnAsignar;

	// Cache de jardineros cargados en la tabla
	private List<EmpleadoNoDeportivo> jardinerosCargados = new ArrayList<>();

	/**
	 * Constructor para integración con VentanaPrincipal
	 */
	public VentanaAsignarMantenimiento(VentanaPrincipal vp) {
		this.vp = vp;
		this.gtm = new GestionTareaMantenimiento();
		inicializarComponentes();
	}

	/**
	 * Constructor por defecto para pruebas o WindowBuilder
	 */
	public VentanaAsignarMantenimiento() {
		this.gtm = new GestionTareaMantenimiento();
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setTitle("Asignar Tareas de Mantenimiento a Jardineros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 500);
		setMinimumSize(new Dimension(700, 420));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 15, 10, 15));
		setContentPane(contentPane);

		// 1. ZONA NORTE: Formulario en una sola fila completa
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
		panelNorte.add(getLblInstalacion());
		panelNorte.add(getCbInstalacion());
		panelNorte.add(getBtnSeleccionarDia());
		panelNorte.add(getLblFecha());
		panelNorte.add(getTxtFecha());
		panelNorte.add(getLblHoraInicio());
		panelNorte.add(getSpHoraInicio());
		panelNorte.add(getLblHoraFinal());
		panelNorte.add(getSpHoraFin());
		contentPane.add(panelNorte, BorderLayout.NORTH);

		// 2. ZONA CENTRO: Dos columnas (Izquierda: Jardineros y turnos / Derecha: Mantenimiento y ocupación)
		JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 0));

		// Columna izquierda: 2 tablas apiladas verticalmente
		JPanel panelIzquierdo = new JPanel(new GridLayout(2, 1, 0, 8));

		JPanel subPanelJardineros = new JPanel(new BorderLayout(0, 3));
		subPanelJardineros.add(getLblJardineros(), BorderLayout.NORTH);
		subPanelJardineros.add(getScrollPaneJardineros(), BorderLayout.CENTER);
		panelIzquierdo.add(subPanelJardineros);

		JPanel subPanelTurnos = new JPanel(new BorderLayout(0, 3));
		subPanelTurnos.add(getLblTurnoLaboral(), BorderLayout.NORTH);
		subPanelTurnos.add(getScrollPaneTurnosLaborales(), BorderLayout.CENTER);
		panelIzquierdo.add(subPanelTurnos);

		panelCentro.add(panelIzquierdo);

		// Columna derecha: 2 tablas apiladas verticalmente
		JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 0, 8));

		JPanel subPanelMantenimientos = new JPanel(new BorderLayout(0, 3));
		subPanelMantenimientos.add(getLblMantenimientos(), BorderLayout.NORTH);
		subPanelMantenimientos.add(getScrollPaneMantenimientos(), BorderLayout.CENTER);
		panelDerecho.add(subPanelMantenimientos);

		JPanel subPanelOcupaciones = new JPanel(new BorderLayout(0, 3));
		subPanelOcupaciones.add(getLblOcupaciones(), BorderLayout.NORTH);
		subPanelOcupaciones.add(getScrollPaneOcupaciones(), BorderLayout.CENTER);
		panelDerecho.add(subPanelOcupaciones);

		panelCentro.add(panelDerecho);

		contentPane.add(panelCentro, BorderLayout.CENTER);

		// 3. ZONA SUR: Botones Atrás y Asignar a la derecha
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		panelSur.add(getBtnAtras());
		panelSur.add(getBtnAsignar());
		contentPane.add(panelSur, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		inicializarTablasYEventos();
	}

	// ==================== GETTERS COMPONENTES ZONA NORTE ====================

	public JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalación:");
			lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblInstalacion;
	}

	public JComboBox<Instalacion> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			cbInstalacion.setPreferredSize(new Dimension(140, 26));
		}
		return cbInstalacion;
	}

	public JButton getBtnSeleccionarDia() {
		if (btnSeleccionarDia == null) {
			btnSeleccionarDia = new JButton("Seleccionar Día");
			btnSeleccionarDia.setPreferredSize(new Dimension(115, 26));
		}
		return btnSeleccionarDia;
	}

	public JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblFecha;
	}

	public JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField(LocalDate.now().toString());
			txtFecha.setEditable(false);
			txtFecha.setPreferredSize(new Dimension(80, 26));
		}
		return txtFecha;
	}

	public JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio:");
			lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblHoraInicio;
	}

	public JSpinner getSpHoraInicio() {
		if (spHoraInicio == null) {
			spHoraInicio = new JSpinner(new SpinnerNumberModel(8, 0, 23, 1));
			spHoraInicio.setPreferredSize(new Dimension(48, 26)); // Agrandado para visualización cómoda de dos dígitos
		}
		return spHoraInicio;
	}

	public JLabel getLblHoraFinal() {
		if (lblHoraFinal == null) {
			lblHoraFinal = new JLabel("Hora fin:");
			lblHoraFinal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblHoraFinal;
	}

	public JSpinner getSpHoraFin() {
		if (spHoraFin == null) {
			spHoraFin = new JSpinner(new SpinnerNumberModel(10, 0, 23, 1));
			spHoraFin.setPreferredSize(new Dimension(48, 26)); // Agrandado para visualización cómoda de dos dígitos
		}
		return spHoraFin;
	}

	// ==================== GETTERS COMPONENTES ZONA CENTRO ====================

	public JLabel getLblJardineros() {
		if (lblJardineros == null) {
			lblJardineros = new JLabel("Jardineros:");
			lblJardineros.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblJardineros;
	}

	public JTable getTablaJardineros() {
		if (tablaJardineros == null) {
			tablaJardineros = new JTable();
			// Selección múltiple para poder asignar uno o varios jardineros
			tablaJardineros.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		return tablaJardineros;
	}

	public JScrollPane getScrollPaneJardineros() {
		if (scrollPaneJardineros == null) {
			scrollPaneJardineros = new JScrollPane();
			scrollPaneJardineros.setViewportView(getTablaJardineros());
		}
		return scrollPaneJardineros;
	}

	public JLabel getLblTurnoLaboral() {
		if (lblTurnoLaboral == null) {
			lblTurnoLaboral = new JLabel("Turno laboral ese día del jardinero seleccionado:");
			lblTurnoLaboral.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblTurnoLaboral;
	}

	public JTable getTablaTurnosLaborales() {
		if (tablaTurnosLaborales == null) {
			tablaTurnosLaborales = new JTable();
		}
		return tablaTurnosLaborales;
	}

	public JScrollPane getScrollPaneTurnosLaborales() {
		if (scrollPaneTurnosLaborales == null) {
			scrollPaneTurnosLaborales = new JScrollPane();
			scrollPaneTurnosLaborales.setViewportView(getTablaTurnosLaborales());
		}
		return scrollPaneTurnosLaborales;
	}

	public JLabel getLblMantenimientos() {
		if (lblMantenimientos == null) {
			lblMantenimientos = new JLabel("Mantenimientos de esa fecha e instalación:");
			lblMantenimientos.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblMantenimientos;
	}

	public JTable getTablaMantenimientos() {
		if (tablaMantenimientos == null) {
			tablaMantenimientos = new JTable();
		}
		return tablaMantenimientos;
	}

	public JScrollPane getScrollPaneMantenimientos() {
		if (scrollPaneMantenimientos == null) {
			scrollPaneMantenimientos = new JScrollPane();
			scrollPaneMantenimientos.setViewportView(getTablaMantenimientos());
		}
		return scrollPaneMantenimientos;
	}

	public JLabel getLblOcupaciones() {
		if (lblOcupaciones == null) {
			lblOcupaciones = new JLabel("Ocupación de la instalación ese día:");
			lblOcupaciones.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblOcupaciones;
	}

	public JTable getTablaOcupaciones() {
		if (tablaOcupaciones == null) {
			tablaOcupaciones = new JTable();
		}
		return tablaOcupaciones;
	}

	public JScrollPane getScrollPaneOcupaciones() {
		if (scrollPaneOcupaciones == null) {
			scrollPaneOcupaciones = new JScrollPane();
			scrollPaneOcupaciones.setViewportView(getTablaOcupaciones());
		}
		return scrollPaneOcupaciones;
	}

	// ==================== GETTERS COMPONENTES ZONA SUR ====================

	public JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.setPreferredSize(new Dimension(100, 28));
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					if (vp != null) {
						vp.setVisible(true);
					}
				}
			});
		}
		return btnAtras;
	}

	public JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar");
			btnAsignar.setPreferredSize(new Dimension(110, 28));
		}
		return btnAsignar;
	}

	// ==================== LÓGICA DE INICIALIZACIÓN Y EVENTOS ====================

	private void inicializarTablasYEventos() {
		// 1. Modelos de tablas no editables
		modeloJardineros = new DefaultTableModel(new Object[] { "DNI", "Nombre", "Apellidos" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaJardineros().setModel(modeloJardineros);

		modeloTurnosLaborales = new DefaultTableModel(new Object[] { "Hora Inicio", "Hora Fin", "Tipo Turno" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaTurnosLaborales().setModel(modeloTurnosLaborales);

		modeloMantenimientos = new DefaultTableModel(new Object[] { "Hora Inicio", "Hora Fin", "Jardinero(s)" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaMantenimientos().setModel(modeloMantenimientos);

		modeloOcupaciones = new DefaultTableModel(new Object[] { "Hora Inicio", "Hora Fin", "Motivo" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaOcupaciones().setModel(modeloOcupaciones);

		// 2. Carga inicial de datos
		cargarInstalaciones();
		cargarJardineros();
		refrescarDatosInstalacion();

		// 3. Eventos de interacción
		// Al cambiar la selección en la tabla de jardineros -> actualizar sus turnos laborales de ese día
		getTablaJardineros().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				refrescarTurnosJardineroSeleccionado();
			}
		});

		// Al cambiar la instalación en el ComboBox -> refrescar mantenimientos y ocupaciones
		getCbInstalacion().addActionListener(e -> refrescarDatosInstalacion());

		// Botón de calendario para elegir día
		getBtnSeleccionarDia().addActionListener(e -> SwingUtil.exceptionWrapper(() -> abrirDialogoCalendario()));

		// Botón Asignar
		getBtnAsignar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> ejecutarAsignacion()));
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
			refrescarDatosInstalacion();
			refrescarTurnosJardineroSeleccionado();
		}
	}

	private void cargarInstalaciones() {
		try {
			List<Instalacion> instalaciones = gtm.obtenerInstalaciones();
			DefaultComboBoxModel<Instalacion> model = new DefaultComboBoxModel<>();
			for (Instalacion inst : instalaciones) {
				model.addElement(inst);
			}
			getCbInstalacion().setModel(model);
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void cargarJardineros() {
		modeloJardineros.setRowCount(0);
		jardinerosCargados.clear();
		try {
			List<EmpleadoNoDeportivo> jardineros = gtm.obtenerJardineros();
			jardinerosCargados.addAll(jardineros);
			for (EmpleadoNoDeportivo j : jardineros) {
				modeloJardineros.addRow(new Object[] { j.getDni(), j.getNombre(), j.getApellido() });
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void refrescarTurnosJardineroSeleccionado() {
		modeloTurnosLaborales.setRowCount(0);
		int filaSeleccionada = getTablaJardineros().getSelectedRow();
		if (filaSeleccionada == -1) {
			return;
		}

		String fecha = getTxtFecha().getText();
		if (fecha.equals(SIN_FECHA) || fecha.isBlank()) {
			return;
		}

		String dni = (String) getTablaJardineros().getValueAt(filaSeleccionada, 0);
		try {
			List<HorarioSemanal> turnos = gtm.obtenerTurnosLaboralesJardineroEnFecha(dni, fecha);
			for (HorarioSemanal t : turnos) {
				modeloTurnosLaborales.addRow(new Object[] { t.getHoraInicio(), t.getHoraFin(), t.getTipoHorario() });
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void refrescarDatosInstalacion() {
		modeloMantenimientos.setRowCount(0);
		modeloOcupaciones.setRowCount(0);

		Instalacion inst = (Instalacion) getCbInstalacion().getSelectedItem();
		if (inst == null) {
			return;
		}

		String fecha = getTxtFecha().getText();
		if (fecha.equals(SIN_FECHA) || fecha.isBlank()) {
			return;
		}

		try {
			// Cargar tareas de mantenimiento asignadas
			List<TareaMantenimiento> tareas = gtm.obtenerTareasInstalacionFecha(inst.getInstalacionId(), fecha);
			for (TareaMantenimiento tm : tareas) {
				modeloMantenimientos.addRow(new Object[] {
						tm.getHoraInicio(), tm.getHoraFin(), tm.getNombresJardinerosFormateados()
				});
			}

			// Cargar ocupaciones generales de la instalación (reservas, partidos, etc.) con motivos cortos
			List<Intervalo> ocupaciones = gtm.obtenerOcupacionesInstalacion(inst.getInstalacionId(), fecha);
			for (Intervalo oc : ocupaciones) {
				modeloOcupaciones.addRow(new Object[] {
						oc.getHoraInicio().toString(), oc.getHoraFin().toString(), acortarMotivo(oc.getMotivo())
				});
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	/**
	 * Normaliza los textos de motivo a versiones concisas para que quepan holgadamente en la tabla.
	 */
	private String acortarMotivo(String motivo) {
		if (motivo == null || motivo.isBlank()) {
			return "-";
		}
		String m = motivo.toLowerCase();
		if (m.contains("margen")) {
			return "Margen mant.";
		}
		if (m.contains("mantenimiento")) {
			return "Mantenimiento";
		}
		if (m.contains("entrenamiento")) {
			return "Entrenamiento";
		}
		if (m.contains("partido")) {
			return "Partido";
		}
		if (m.contains("reserva")) {
			return "Reserva";
		}
		return motivo;
	}

	private void ejecutarAsignacion() {
		Instalacion inst = (Instalacion) getCbInstalacion().getSelectedItem();
		if (inst == null) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona una instalación.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String fecha = getTxtFecha().getText();
		if (fecha.equals(SIN_FECHA) || fecha.isBlank()) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha válida con el calendario.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int[] filasSeleccionadas = getTablaJardineros().getSelectedRows();
		if (filasSeleccionadas.length == 0) {
			JOptionPane.showMessageDialog(this,
					"Por favor, selecciona al menos un jardinero de la tabla (puedes seleccionar varios).",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<EmpleadoNoDeportivo> seleccionados = new ArrayList<>();
		for (int fila : filasSeleccionadas) {
			String dni = (String) getTablaJardineros().getValueAt(fila, 0);
			for (EmpleadoNoDeportivo emp : jardinerosCargados) {
				if (emp.getDni().equals(dni)) {
					seleccionados.add(emp);
					break;
				}
			}
		}

		// Minutos fijados siempre a 00
		String horaInicio = String.format("%02d:00", (int) getSpHoraInicio().getValue());
		String horaFin = String.format("%02d:00", (int) getSpHoraFin().getValue());

		try {
			gtm.validarYAsignarTarea(
					inst.getInstalacionId(),
					fecha,
					horaInicio,
					horaFin,
					"Mantenimiento",
					seleccionados
			);

			JOptionPane.showMessageDialog(this, "Tarea de mantenimiento asignada con éxito.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

			// Refrescar paneles tras la inserción correcta
			refrescarDatosInstalacion();
			refrescarTurnosJardineroSeleccionado();

		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}
}
