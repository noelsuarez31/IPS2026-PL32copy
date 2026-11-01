package giis.demo.ui.accion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import giis.demo.model.accion.Accion;
import giis.demo.model.accion.Accionista;
import giis.demo.model.accion.GestionAcciones;
import giis.demo.model.accion.ResumenParticipacion;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.SwingUtil;
import giis.demo.util.UnexpectedException;

/**
 * Ventana del Portal del Accionista para la consulta de acciones en propiedad,
 * origen, estado de venta y porcentaje de participación en el club.
 */
public class VentanaPortalAccionista extends JFrame {

	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private GestionAcciones gestionAcciones;

	// Zona Norte: Autenticación / Selección del DNI del accionista
	private JLabel lblDniAccionista;
	private JComboBox<String> cbDni;
	private JButton btnAcceder;
	private JLabel lblTitularTexto;
	private JLabel lblTitular;

	// Zona Centro: Listado tabular de acciones
	private JLabel lblAccionesPropiedad;
	private JTable tablaAcciones;
	private DefaultTableModel modeloAcciones;
	private JScrollPane scrollPaneAcciones;

	// Zona Sur: Métricas de participación y botón de navegación
	private JLabel lblTotalAccionesTexto;
	private JLabel lblTotalAcciones;
	private JLabel lblTotalClubTexto;
	private JLabel lblTotalClub;
	private JLabel lblParticipacionTexto;
	private JLabel lblParticipacion;
	private JButton btnAtras;

	/**
	 * Constructor para integración con la VentanaPrincipal.
	 */
	public VentanaPortalAccionista(VentanaPrincipal vp) {
		this.vp = vp;
		this.gestionAcciones = new GestionAcciones();
		inicializarComponentes();
	}

	/**
	 * Constructor por defecto para pruebas unitarias o WindowBuilder.
	 */
	public VentanaPortalAccionista() {
		this.gestionAcciones = new GestionAcciones();
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setTitle("Portal del accionista");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 500);
		setMinimumSize(new Dimension(750, 420));

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 15, 10, 15));
		setContentPane(contentPane);

		// 1. ZONA NORTE: Barra de identificación y titular
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelNorte.add(getLblDniAccionista());
		panelNorte.add(getCbDni());
		panelNorte.add(getBtnAcceder());
		panelNorte.add(getLblTitularTexto());
		panelNorte.add(getLblTitular());
		contentPane.add(panelNorte, BorderLayout.NORTH);

		// 2. ZONA CENTRO: Tabla con las acciones en propiedad
		JPanel panelCentro = new JPanel(new BorderLayout(0, 5));
		panelCentro.add(getLblAccionesPropiedad(), BorderLayout.NORTH);
		panelCentro.add(getScrollPaneAcciones(), BorderLayout.CENTER);
		contentPane.add(panelCentro, BorderLayout.CENTER);

		// 3. ZONA SUR: Métricas de participación (izquierda) y botón Atrás (derecha)
		JPanel panelSur = new JPanel(new BorderLayout());

		JPanel panelMetricas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
		panelMetricas.add(getLblTotalAccionesTexto());
		panelMetricas.add(getLblTotalAcciones());
		panelMetricas.add(getLblTotalClubTexto());
		panelMetricas.add(getLblTotalClub());
		panelMetricas.add(getLblParticipacionTexto());
		panelMetricas.add(getLblParticipacion());

		panelSur.add(panelMetricas, BorderLayout.WEST);
		panelSur.add(getBtnAtras(), BorderLayout.EAST);
		contentPane.add(panelSur, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		configurarEventosYCargaInicial();
	}

	private void configurarEventosYCargaInicial() {
		// Modelo no editable para la tabla de acciones
		modeloAcciones = new DefaultTableModel(
				new Object[] { "ID acción", "Fecha adquisición", "Origen", "Precio pagado", "En venta", "Precio venta" },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		getTablaAcciones().setModel(modeloAcciones);

		// Cargar lista inicial de DNIs de accionistas en el ComboBox
		cargarDnisAccionistas();

		// Acción al pulsar el botón Acceder
		getBtnAcceder().addActionListener(e -> SwingUtil.exceptionWrapper(() -> ejecutarAcceso()));

		// Acción al pulsar ENTER en el editor del ComboBox
		getCbDni().getEditor().addActionListener(e -> SwingUtil.exceptionWrapper(() -> ejecutarAcceso()));

		// Botón Atrás
		getBtnAtras().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarVentana();
			}
		});

		// Restaurar VentanaPrincipal al cerrar la ventana con la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarVentana();
			}
		});
	}

	/**
	 * Puebla el ComboBox con los DNIs de todos los accionistas registrados.
	 */
	private void cargarDnisAccionistas() {
		try {
			List<Accionista> lista = gestionAcciones.obtenerAccionistas();
			getCbDni().removeAllItems();
			for (Accionista a : lista) {
				getCbDni().addItem(a.getDni());
			}
			getCbDni().setSelectedIndex(-1);
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	/**
	 * Valida el DNI seleccionado o introducido y carga los datos del portal.
	 */
	private void ejecutarAcceso() {
		Object item = getCbDni().getEditor().getItem();
		String dni = (item != null) ? item.toString().trim() : "";

		// Validación de negocio (lanza ApplicationException si no es accionista o está en blanco)
		Accionista accionista = null;
		try {
			accionista = gestionAcciones.validarYObtenerAccionista(dni);
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}

		// Si es válido, actualizamos el nombre del titular
		getLblTitular().setText(accionista.getNombreCompleto());

		try {
			// 1. Cargar métricas de participación
			ResumenParticipacion resumen = gestionAcciones.obtenerResumenParticipacion(accionista.getDni());
			getLblTotalAcciones().setText(String.valueOf(resumen.getTotalAccionesPropias()));
			getLblTotalClub().setText(String.valueOf(resumen.getTotalAccionesClub()));
			getLblParticipacion().setText(String.format(Locale.GERMAN, "%.2f %%", resumen.getPorcentajeParticipacion()));

			// 2. Cargar tabla de acciones en propiedad ordenadas por fecha de adquisición
			List<Accion> acciones = gestionAcciones.obtenerAccionesAccionista(accionista.getDni());
			modeloAcciones.setRowCount(0);
			for (Accion a : acciones) {
				String strPrecioPagado = String.format(Locale.GERMAN, "%.2f €", a.getPrecioPagado());
				String strEnVenta = a.isEnVenta() ? "Sí" : "No";
				String strPrecioVenta = (a.getPrecioVenta() != null)
						? String.format(Locale.GERMAN, "%.2f €", a.getPrecioVenta())
						: "-";

				modeloAcciones.addRow(new Object[] {
						String.format("%03d", a.getIdAccion()),
						a.getFechaAdquisicion(),
						a.getOrigen(),
						strPrecioPagado,
						strEnVenta,
						strPrecioVenta
				});
			}
		} catch (SQLException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private void cerrarVentana() {
		dispose();
		if (vp != null) {
			vp.setVisible(true);
		}
	}

	// ==================== GETTERS COMPONENTES VISUALES ====================

	private JLabel getLblDniAccionista() {
		if (lblDniAccionista == null) {
			lblDniAccionista = new JLabel("Dni accionista :");
			lblDniAccionista.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblDniAccionista;
	}

	private JComboBox<String> getCbDni() {
		if (cbDni == null) {
			cbDni = new JComboBox<>();
			cbDni.setEditable(true);
			cbDni.setPreferredSize(new Dimension(130, 26));
		}
		return cbDni;
	}

	private JButton getBtnAcceder() {
		if (btnAcceder == null) {
			btnAcceder = new JButton("Acceder");
			btnAcceder.setPreferredSize(new Dimension(100, 26));
		}
		return btnAcceder;
	}

	private JLabel getLblTitularTexto() {
		if (lblTitularTexto == null) {
			lblTitularTexto = new JLabel("Titular :");
			lblTitularTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblTitularTexto;
	}

	private JLabel getLblTitular() {
		if (lblTitular == null) {
			lblTitular = new JLabel("-");
			lblTitular.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTitular.setForeground(new Color(0, 102, 204));
		}
		return lblTitular;
	}

	private JLabel getLblAccionesPropiedad() {
		if (lblAccionesPropiedad == null) {
			lblAccionesPropiedad = new JLabel("Acciones en propiedad :");
			lblAccionesPropiedad.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblAccionesPropiedad;
	}

	private JTable getTablaAcciones() {
		if (tablaAcciones == null) {
			tablaAcciones = new JTable();
			tablaAcciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tablaAcciones;
	}

	private JScrollPane getScrollPaneAcciones() {
		if (scrollPaneAcciones == null) {
			scrollPaneAcciones = new JScrollPane(getTablaAcciones());
		}
		return scrollPaneAcciones;
	}

	private JLabel getLblTotalAccionesTexto() {
		if (lblTotalAccionesTexto == null) {
			lblTotalAccionesTexto = new JLabel("Total acciones :");
			lblTotalAccionesTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblTotalAccionesTexto;
	}

	private JLabel getLblTotalAcciones() {
		if (lblTotalAcciones == null) {
			lblTotalAcciones = new JLabel("0");
			lblTotalAcciones.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotalAcciones.setForeground(new Color(0, 102, 204));
		}
		return lblTotalAcciones;
	}

	private JLabel getLblTotalClubTexto() {
		if (lblTotalClubTexto == null) {
			lblTotalClubTexto = new JLabel("Total club :");
			lblTotalClubTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblTotalClubTexto;
	}

	private JLabel getLblTotalClub() {
		if (lblTotalClub == null) {
			lblTotalClub = new JLabel("0");
			lblTotalClub.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblTotalClub;
	}

	private JLabel getLblParticipacionTexto() {
		if (lblParticipacionTexto == null) {
			lblParticipacionTexto = new JLabel("Participación :");
			lblParticipacionTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblParticipacionTexto;
	}

	private JLabel getLblParticipacion() {
		if (lblParticipacion == null) {
			lblParticipacion = new JLabel("0.00 %");
			lblParticipacion.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblParticipacion.setForeground(new Color(0, 102, 204));
		}
		return lblParticipacion;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.setPreferredSize(new Dimension(110, 30));
		}
		return btnAtras;
	}
}
