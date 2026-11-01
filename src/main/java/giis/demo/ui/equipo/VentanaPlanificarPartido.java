package giis.demo.ui.equipo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.equipo.Equipo;
import giis.demo.model.equipo.EventoOcupacion;
import giis.demo.model.equipo.PlanificarPartidoService;
import giis.demo.model.reserva.Instalacion;
import giis.demo.ui.VentanaPrincipal;

/**
 * Ventana para dar de alta partidos de los equipos del club (HU-35797).
 * Coherente con crear entrenamiento: ¿Sobreescribible? (Sí en verde / No en rojo).
 */
public class VentanaPlanificarPartido extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	private VentanaPrincipal vPrincipal;
	private PlanificarPartidoService service;

	// Paneles principales
	private JPanel contentPane;
	private JPanel pnDatosPartido;
	private JPanel pnCamposPartido;
	private JPanel pnHoras;
	private JPanel pnOcupacion;
	private JScrollPane scrOcupacion;
	private JPanel pnSur;
	private JPanel pnPreciosSeccion;
	private JPanel pnRapido;
	private JPanel pnGridSecciones;
	private JPanel pnDiaYBotones;
	private JPanel pnDiaDelClub;
	private JPanel pnBotonera;

	// 1. Campos Encuentro
	private JPanel pnLocal;
	private JLabel lbLocal;
	private JComboBox<Equipo> cbEquipoLocal;

	private JPanel pnVisitante;
	private JLabel lbVisitante;
	private JComboBox<Equipo> cbEquipoVisitante;

	private JPanel pnInstalacion;
	private JLabel lbInstalacion;
	private JComboBox<Instalacion> cbInstalacion;

	private JPanel pnFecha;
	private JLabel lbFecha;
	private JDateChooser dcFecha;

	private JLabel lbHoraInicio;
	private JSpinner spHoraInicio;
	private JLabel lbSepInicio;
	private JSpinner spMinInicio;

	private JLabel lbHoraFin;
	private JSpinner spHoraFin;
	private JLabel lbSepFin;
	private JSpinner spMinFin;

	private JButton btnConsultarOcupacion;

	// 2. Tabla Ocupación
	private JTable tablaOcupacion;
	private DefaultTableModel modeloOcupacion;

	// 3. Precios por Sección (A, B, C, D, E, F)
	private JLabel lbRapido;
	private JSpinner spPrecioRapido;
	private JButton btnAplicarRapido;
	private Map<TipoSeccion, JSpinner> spinnersSecciones = new HashMap<>();

	// 4. Día del Club
	private JCheckBox chkDiaClub;
	private JLabel lbSepDia1;
	private JLabel lbEstadoDiaClub;
	private JLabel lbSepDia2;
	private JLabel lbSuplementoAbonados;
	private JButton btnModificarSuplemento;
	private double suplementoAbonadoActual = 15.0;

	// 5. Botones inferiores
	private JButton btnCancelar;
	private JButton btnDarDeAlta;

	public VentanaPlanificarPartido(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		this.service = vPrincipal.getPlanificarPartidoService();

		setTitle("Dar de Alta Partido de Fútbol (Gerente)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1220, 720);
		setLocationRelativeTo(null);

		setContentPane(getContentPane());

		cargarDatosIniciales();
		actualizarOcupacion();
	}

	@Override
	public JPanel getContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel(new BorderLayout(0, 8));
			contentPane.setBorder(new EmptyBorder(10, 12, 10, 12));

			// 1. Datos del encuentro (Norte)
			contentPane.add(getPnDatosPartido(), BorderLayout.NORTH);

			// 2. Ocupación en la fecha e instalación (Centro)
			contentPane.add(getPnOcupacion(), BorderLayout.CENTER);

			// 3. Precios + 4. Día del Club + Botonera (Sur)
			contentPane.add(getPnSur(), BorderLayout.SOUTH);
		}
		return contentPane;
	}

	// =========================================================================
	// 1. DATOS DEL PARTIDO (Local, Visitante, Instalación, JDateChooser, Spinners)
	// =========================================================================
	private JPanel getPnDatosPartido() {
		if (pnDatosPartido == null) {
			pnDatosPartido = new JPanel(new BorderLayout(0, 4));
			pnDatosPartido.setBorder(new TitledBorder(new EtchedBorder(), "1. Datos del Encuentro e Instalación",
					TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), null));
			pnDatosPartido.add(getPnCamposPartido(), BorderLayout.NORTH);
			pnDatosPartido.add(getPnHoras(), BorderLayout.SOUTH);
		}
		return pnDatosPartido;
	}

	private JPanel getPnCamposPartido() {
		if (pnCamposPartido == null) {
			pnCamposPartido = new JPanel(new GridLayout(1, 4, 12, 0));
			pnCamposPartido.setBorder(new EmptyBorder(4, 8, 4, 8));
			pnCamposPartido.add(getPnLocal());
			pnCamposPartido.add(getPnVisitante());
			pnCamposPartido.add(getPnInstalacion());
			pnCamposPartido.add(getPnFecha());
		}
		return pnCamposPartido;
	}

	private JPanel getPnLocal() {
		if (pnLocal == null) {
			pnLocal = new JPanel(new BorderLayout(0, 3));
			pnLocal.add(getLbLocal(), BorderLayout.NORTH);
			pnLocal.add(getCbEquipoLocal(), BorderLayout.CENTER);
		}
		return pnLocal;
	}

	private JLabel getLbLocal() {
		if (lbLocal == null) {
			lbLocal = new JLabel("Equipo Local (del Club):");
			lbLocal.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbLocal;
	}

	public JComboBox<Equipo> getCbEquipoLocal() {
		if (cbEquipoLocal == null) {
			cbEquipoLocal = new JComboBox<>();
			cbEquipoLocal.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (value instanceof Equipo) {
						setText(((Equipo) value).getNombre());
					}
					return this;
				}
			});
		}
		return cbEquipoLocal;
	}

	private JPanel getPnVisitante() {
		if (pnVisitante == null) {
			pnVisitante = new JPanel(new BorderLayout(0, 3));
			pnVisitante.add(getLbVisitante(), BorderLayout.NORTH);
			pnVisitante.add(getCbEquipoVisitante(), BorderLayout.CENTER);
		}
		return pnVisitante;
	}

	private JLabel getLbVisitante() {
		if (lbVisitante == null) {
			lbVisitante = new JLabel("Equipo Visitante (Externo):");
			lbVisitante.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbVisitante;
	}

	public JComboBox<Equipo> getCbEquipoVisitante() {
		if (cbEquipoVisitante == null) {
			cbEquipoVisitante = new JComboBox<>();
			cbEquipoVisitante.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (value instanceof Equipo) {
						setText(((Equipo) value).getNombre());
					}
					return this;
				}
			});
		}
		return cbEquipoVisitante;
	}

	private JPanel getPnInstalacion() {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel(new BorderLayout(0, 3));
			pnInstalacion.add(getLbInstalacion(), BorderLayout.NORTH);
			pnInstalacion.add(getCbInstalacion(), BorderLayout.CENTER);
		}
		return pnInstalacion;
	}

	private JLabel getLbInstalacion() {
		if (lbInstalacion == null) {
			lbInstalacion = new JLabel("Instalación:");
			lbInstalacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbInstalacion;
	}

	public JComboBox<Instalacion> getCbInstalacion() {
        if (cbInstalacion == null) {
            cbInstalacion = new JComboBox<>();
            cbInstalacion.addActionListener(e -> actualizarOcupacion());
        }
        return cbInstalacion;
    }

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel(new BorderLayout(0, 3));
			pnFecha.add(getLbFecha(), BorderLayout.NORTH);
			pnFecha.add(getDcFecha(), BorderLayout.CENTER);
		}
		return pnFecha;
	}

	private JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("Fecha del Partido:");
			lbFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbFecha;
	}

	public JDateChooser getDcFecha() {
		if (dcFecha == null) {
			dcFecha = new JDateChooser();
			dcFecha.setDateFormatString("dd/MM/yyyy");
			dcFecha.setDate(new Date());
			dcFecha.setMinSelectableDate(new Date());
			dcFecha.addPropertyChangeListener("date", evt -> actualizarOcupacion());
		}
		return dcFecha;
	}

	private JPanel getPnHoras() {
		if (pnHoras == null) {
			pnHoras = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
			pnHoras.setBorder(new EmptyBorder(4, 8, 4, 8));

			pnHoras.add(getLbHoraInicio());
			pnHoras.add(getSpHoraInicio());
			pnHoras.add(getLbSepInicio());
			pnHoras.add(getSpMinInicio());

			pnHoras.add(getLbHoraFin());
			pnHoras.add(getSpHoraFin());
			pnHoras.add(getLbSepFin());
			pnHoras.add(getSpMinFin());

			pnHoras.add(getBtnConsultarOcupacion());
		}
		return pnHoras;
	}

	private JLabel getLbHoraInicio() {
		if (lbHoraInicio == null) {
			lbHoraInicio = new JLabel("Hora Inicio:");
			lbHoraInicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbHoraInicio;
	}

	public JSpinner getSpHoraInicio() {
		if (spHoraInicio == null) {
			spHoraInicio = new JSpinner(new SpinnerNumberModel(18, 0, 23, 1));
			spHoraInicio.setPreferredSize(new Dimension(55, 24));
		}
		return spHoraInicio;
	}

	private JLabel getLbSepInicio() {
		if (lbSepInicio == null) {
			lbSepInicio = new JLabel(":");
			lbSepInicio.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lbSepInicio;
	}

	public JSpinner getSpMinInicio() {
		if (spMinInicio == null) {
			spMinInicio = new JSpinner(new SpinnerNumberModel(30, 0, 59, 5));
			spMinInicio.setPreferredSize(new Dimension(55, 24));
		}
		return spMinInicio;
	}

	private JLabel getLbHoraFin() {
		if (lbHoraFin == null) {
			lbHoraFin = new JLabel("       Hora Fin:");
			lbHoraFin.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lbHoraFin;
	}

	public JSpinner getSpHoraFin() {
		if (spHoraFin == null) {
			spHoraFin = new JSpinner(new SpinnerNumberModel(20, 0, 23, 1));
			spHoraFin.setPreferredSize(new Dimension(55, 24));
		}
		return spHoraFin;
	}

	private JLabel getLbSepFin() {
		if (lbSepFin == null) {
			lbSepFin = new JLabel(":");
			lbSepFin.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lbSepFin;
	}

	public JSpinner getSpMinFin() {
		if (spMinFin == null) {
			spMinFin = new JSpinner(new SpinnerNumberModel(30, 0, 59, 5));
			spMinFin.setPreferredSize(new Dimension(55, 24));
		}
		return spMinFin;
	}

	public JButton getBtnConsultarOcupacion() {
		if (btnConsultarOcupacion == null) {
			btnConsultarOcupacion = new JButton("🔄 Consultar Ocupación");
			btnConsultarOcupacion.addActionListener(e -> actualizarOcupacion());
		}
		return btnConsultarOcupacion;
	}

	// =========================================================================
	// 2. OCUPACIÓN EN LA FECHA E INSTALACIÓN (Justo debajo de la sección 1)
	// =========================================================================
	private JPanel getPnOcupacion() {
		if (pnOcupacion == null) {
			pnOcupacion = new JPanel(new BorderLayout(0, 5));
			pnOcupacion.setBorder(new TitledBorder(new EtchedBorder(), "2. Ocupación en la Fecha e Instalación",
					TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), null));
			pnOcupacion.add(getScrOcupacion(), BorderLayout.CENTER);
		}
		return pnOcupacion;
	}

	private JScrollPane getScrOcupacion() {
		if (scrOcupacion == null) {
			scrOcupacion = new JScrollPane(getTablaOcupacion());
			scrOcupacion.setPreferredSize(new Dimension(1160, 180));
		}
		return scrOcupacion;
	}

	public JTable getTablaOcupacion() {
		if (tablaOcupacion == null) {
			modeloOcupacion = new DefaultTableModel(
					new String[] { "Hora Inicio", "Hora Fin", "Tipo Evento", "Detalle del Evento", "¿Sobreescribible?" }, 0) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};

			tablaOcupacion = new JTable(modeloOcupacion);
			tablaOcupacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOcupacion.setRowHeight(24);
			tablaOcupacion.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			// Configurar anchos de columna para evitar cualquier texto cortado
			tablaOcupacion.getColumnModel().getColumn(0).setPreferredWidth(95);  // Hora Inicio
			tablaOcupacion.getColumnModel().getColumn(0).setMaxWidth(110);
			tablaOcupacion.getColumnModel().getColumn(1).setPreferredWidth(95);  // Hora Fin
			tablaOcupacion.getColumnModel().getColumn(1).setMaxWidth(110);
			tablaOcupacion.getColumnModel().getColumn(2).setPreferredWidth(140); // Tipo Evento
			tablaOcupacion.getColumnModel().getColumn(2).setMaxWidth(160);
			tablaOcupacion.getColumnModel().getColumn(3).setPreferredWidth(520); // Detalle del Evento
			tablaOcupacion.getColumnModel().getColumn(4).setPreferredWidth(180); // ¿Sobreescribible?

			// Renderizador idéntico al de crear entrenamientos: Sí en verde suave, No en rojo suave
			tablaOcupacion.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					if (!isSelected) {
						String sobre = String.valueOf(table.getValueAt(row, 4));
						if (sobre.startsWith("Sí")) {
							c.setBackground(new Color(230, 248, 230)); // Verde suave para sobreescribibles
						} else if (sobre.startsWith("No")) {
							c.setBackground(new Color(255, 232, 232)); // Rojo suave para colisiones no sobreescribibles
						} else {
							c.setBackground(Color.WHITE); // Blanco neutro para filas informativas
						}
					}
					return c;
				}
			});
		}
		return tablaOcupacion;
	}

	// =========================================================================
	// CONTENEDOR SUR (3. Precios + 4. Día del Club + 5. Botonera)
	// =========================================================================
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel(new BorderLayout(0, 6));
			pnSur.add(getPnPreciosSeccion(), BorderLayout.NORTH);
			pnSur.add(getPnDiaYBotones(), BorderLayout.SOUTH);
		}
		return pnSur;
	}

	private JPanel getPnDiaYBotones() {
		if (pnDiaYBotones == null) {
			pnDiaYBotones = new JPanel(new BorderLayout(0, 4));
			pnDiaYBotones.add(getPnDiaDelClub(), BorderLayout.NORTH);
			pnDiaYBotones.add(getPnBotonera(), BorderLayout.SOUTH);
		}
		return pnDiaYBotones;
	}

	// =========================================================================
	// 3. PRECIOS POR SECCIÓN (Secciones A, B, C, D, E, F)
	// =========================================================================
	private JPanel getPnPreciosSeccion() {
		if (pnPreciosSeccion == null) {
			pnPreciosSeccion = new JPanel(new BorderLayout(0, 4));
			pnPreciosSeccion.setBorder(new TitledBorder(new EtchedBorder(), "3. Precios por Sección del Estadio (Entradas)",
					TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), null));
			pnPreciosSeccion.add(getPnRapido(), BorderLayout.NORTH);
			pnPreciosSeccion.add(getPnGridSecciones(), BorderLayout.CENTER);
		}
		return pnPreciosSeccion;
	}

	private JPanel getPnRapido() {
		if (pnRapido == null) {
			pnRapido = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 2));
			pnRapido.add(getLbRapido());
			pnRapido.add(getSpPrecioRapido());
			pnRapido.add(getBtnAplicarRapido());
		}
		return pnRapido;
	}

	private JLabel getLbRapido() {
		if (lbRapido == null) {
			lbRapido = new JLabel("Precio rápido para todas las secciones:");
			lbRapido.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lbRapido;
	}

	public JSpinner getSpPrecioRapido() {
		if (spPrecioRapido == null) {
			spPrecioRapido = new JSpinner(new SpinnerNumberModel(30.0, 0.0, 999.0, 5.0));
			spPrecioRapido.setEditor(new JSpinner.NumberEditor(spPrecioRapido, "0.00 €"));
			spPrecioRapido.setPreferredSize(new Dimension(80, 24));
		}
		return spPrecioRapido;
	}

	public JButton getBtnAplicarRapido() {
		if (btnAplicarRapido == null) {
			btnAplicarRapido = new JButton("Aplicar a todas");
			btnAplicarRapido.addActionListener(e -> accionAplicarPrecioATodas());
		}
		return btnAplicarRapido;
	}

	private JPanel getPnGridSecciones() {
		if (pnGridSecciones == null) {
			pnGridSecciones = new JPanel(new GridLayout(1, 6, 8, 0));
			pnGridSecciones.setBorder(new EmptyBorder(4, 6, 6, 6));

			for (TipoSeccion sec : TipoSeccion.values()) {
				JPanel pnBox = new JPanel(new BorderLayout(0, 2));
				pnBox.setBackground(new Color(248, 250, 252));
				pnBox.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
						BorderFactory.createEmptyBorder(4, 6, 4, 6)));

				JLabel lbSec = new JLabel("Sección " + sec.name(), JLabel.CENTER);
				lbSec.setFont(new Font("Tahoma", Font.BOLD, 11));

				double precioDefecto = 30.0;
				if (sec == TipoSeccion.A) {
					precioDefecto = 35.0;
				} else if (sec == TipoSeccion.C) {
					precioDefecto = 40.0;
				} else if (sec == TipoSeccion.E || sec == TipoSeccion.F) {
					precioDefecto = 25.0;
				}

				JSpinner spSec = new JSpinner(new SpinnerNumberModel(precioDefecto, 0.0, 999.0, 1.0));
				spSec.setEditor(new JSpinner.NumberEditor(spSec, "0.00 €"));
				spSec.setPreferredSize(new Dimension(75, 24));
				spinnersSecciones.put(sec, spSec);

				pnBox.add(lbSec, BorderLayout.NORTH);
				pnBox.add(spSec, BorderLayout.CENTER);
				pnGridSecciones.add(pnBox);
			}
		}
		return pnGridSecciones;
	}

	// =========================================================================
	// 4. DÍA DEL CLUB (JCheckBox + JDialog Modal)
	// =========================================================================
	private JPanel getPnDiaDelClub() {
		if (pnDiaDelClub == null) {
			pnDiaDelClub = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 6));
			pnDiaDelClub.setBorder(new TitledBorder(new EtchedBorder(), "4. Gestión de Día del Club",
					TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), null));

			pnDiaDelClub.add(getChkDiaClub());
			pnDiaDelClub.add(getLbSepDia1());
			pnDiaDelClub.add(getLbEstadoDiaClub());
			pnDiaDelClub.add(getLbSepDia2());
			pnDiaDelClub.add(getLbSuplementoAbonados());
			pnDiaDelClub.add(getBtnModificarSuplemento());
		}
		return pnDiaDelClub;
	}

	public JCheckBox getChkDiaClub() {
		if (chkDiaClub == null) {
			chkDiaClub = new JCheckBox("⭐ Declarar este encuentro como \"Día del Club\"");
			chkDiaClub.setFont(new Font("Tahoma", Font.BOLD, 11));
			chkDiaClub.addActionListener(e -> {
				if (chkDiaClub.isSelected()) {
					abrirDialogoDiaDelClub();
				} else {
					actualizarEstadoDiaClub(false, 0.0);
				}
			});
		}
		return chkDiaClub;
	}

	private JLabel getLbSepDia1() {
		if (lbSepDia1 == null) {
			lbSepDia1 = new JLabel("|");
		}
		return lbSepDia1;
	}

	public JLabel getLbEstadoDiaClub() {
		if (lbEstadoDiaClub == null) {
			lbEstadoDiaClub = new JLabel("Día del Club: No");
			lbEstadoDiaClub.setForeground(Color.DARK_GRAY);
		}
		return lbEstadoDiaClub;
	}

	private JLabel getLbSepDia2() {
		if (lbSepDia2 == null) {
			lbSepDia2 = new JLabel("|");
		}
		return lbSepDia2;
	}

	public JLabel getLbSuplementoAbonados() {
		if (lbSuplementoAbonados == null) {
			lbSuplementoAbonados = new JLabel("Suplemento abonados: -");
			lbSuplementoAbonados.setForeground(Color.DARK_GRAY);
		}
		return lbSuplementoAbonados;
	}

	public JButton getBtnModificarSuplemento() {
		if (btnModificarSuplemento == null) {
			btnModificarSuplemento = new JButton("✏️ Modificar Suplemento");
			btnModificarSuplemento.setVisible(false);
			btnModificarSuplemento.addActionListener(e -> abrirDialogoDiaDelClub());
		}
		return btnModificarSuplemento;
	}

	// =========================================================================
	// 5. BOTONERA INFERIOR (Estilo estándar)
	// =========================================================================
	private JPanel getPnBotonera() {
		if (pnBotonera == null) {
			pnBotonera = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
			pnBotonera.add(getBtnCancelar());
			pnBotonera.add(getBtnDarDeAlta());
		}
		return pnBotonera;
	}

	public JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar / Volver");
			btnCancelar.addActionListener(e -> dispose());
		}
		return btnCancelar;
	}

	public JButton getBtnDarDeAlta() {
		if (btnDarDeAlta == null) {
			btnDarDeAlta = new JButton("Dar de Alta Partido");
			btnDarDeAlta.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnDarDeAlta.addActionListener(e -> accionDarDeAltaPartido());
		}
		return btnDarDeAlta;
	}

	// =========================================================================
	// CONTROLADORES Y LÓGICA DE NEGOCIO
	// =========================================================================
	private void abrirDialogoDiaDelClub() {
		DialogoDiaDelClub dlg = new DialogoDiaDelClub(this, suplementoAbonadoActual);
		dlg.setVisible(true);

		if (dlg.isAceptado()) {
			suplementoAbonadoActual = dlg.getPrecioAbonado();
			actualizarEstadoDiaClub(true, suplementoAbonadoActual);
		} else {
			if (getLbSuplementoAbonados().getText().contains("-")) {
				getChkDiaClub().setSelected(false);
			}
		}
	}

	private void actualizarEstadoDiaClub(boolean activo, double precio) {
		if (activo) {
			getLbEstadoDiaClub().setText("Día del Club: Sí");
			getLbEstadoDiaClub().setForeground(new Color(22, 101, 52));
			getLbSuplementoAbonados().setText(String.format("Suplemento abonados: %.2f €", precio));
			getLbSuplementoAbonados().setForeground(new Color(22, 101, 52));
			getBtnModificarSuplemento().setVisible(true);
		} else {
			getLbEstadoDiaClub().setText("Día del Club: No");
			getLbEstadoDiaClub().setForeground(Color.DARK_GRAY);
			getLbSuplementoAbonados().setText("Suplemento abonados: -");
			getLbSuplementoAbonados().setForeground(Color.DARK_GRAY);
			getBtnModificarSuplemento().setVisible(false);
		}
	}

	private void accionAplicarPrecioATodas() {
		double val = ((Number) getSpPrecioRapido().getValue()).doubleValue();
		for (JSpinner sp : spinnersSecciones.values()) {
			sp.setValue(val);
		}
	}

	private void accionDarDeAltaPartido() {
		try {
			// 1. Validar Equipos
			Equipo local = (Equipo) getCbEquipoLocal().getSelectedItem();
			Equipo visitante = (Equipo) getCbEquipoVisitante().getSelectedItem();
			if (local == null || visitante == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar tanto el equipo local como el visitante.",
						"Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (local.getIdEquipo() == visitante.getIdEquipo()) {
				JOptionPane.showMessageDialog(this, "El equipo local y el visitante no pueden ser el mismo.",
						"Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 2. Validar Fecha
			Date fechaUtil = getDcFecha().getDate();
			if (fechaUtil == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha para el encuentro.",
						"Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}
			LocalDate fecha = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (fecha.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "La fecha del partido no puede ser anterior a hoy.",
						"Fecha no válida", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 3. Validar Horario
			LocalTime hIni = LocalTime.of((Integer) getSpHoraInicio().getValue(), (Integer) getSpMinInicio().getValue());
			LocalTime hFin = LocalTime.of((Integer) getSpHoraFin().getValue(), (Integer) getSpMinFin().getValue());
			if (!hFin.isAfter(hIni)) {
				JOptionPane.showMessageDialog(this, "La hora de fin debe ser posterior a la de inicio.",
						"Horario inválido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (hIni.isBefore(LocalTime.of(8, 0)) || hFin.isAfter(LocalTime.of(22, 0))) {
				JOptionPane.showMessageDialog(this, "El horario de las instalaciones es de 08:00 a 22:00.",
						"Horario fuera de rango", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 4. Validar Instalación
			Instalacion inst = (Instalacion) getCbInstalacion().getSelectedItem();
			if (inst == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una instalación válida.",
						"Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 5. Validar Precios por Sección
			Map<TipoSeccion, Double> preciosSeccion = new HashMap<>();
			for (Map.Entry<TipoSeccion, JSpinner> entry : spinnersSecciones.entrySet()) {
				double precio = ((Number) entry.getValue().getValue()).doubleValue();
				if (precio < 0) {
					JOptionPane.showMessageDialog(this, "El precio de la sección " + entry.getKey().name() + " no puede ser negativo.",
							"Precio inválido", JOptionPane.WARNING_MESSAGE);
					return;
				}
				preciosSeccion.put(entry.getKey(), precio);
			}
			// 6. Datos de Día del Club
			boolean esDiaClub = getChkDiaClub().isSelected();
			double precioAbonado = esDiaClub ? suplementoAbonadoActual : 0.0;
			if (esDiaClub && precioAbonado <= 0) {
				JOptionPane.showMessageDialog(this, "Al ser Día del Club, el suplemento para abonados debe ser mayor a 0 €.",
						"Suplemento no válido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 7. Comprobar colisiones no sobreescribibles (ej. otro partido ya registrado)
			service.comprobarColisionesPartido(fecha, hIni, hFin, inst.getInstalacionId());
			// 8. Obtener eventos sobreescribibles con menor prioridad (jardinería, reservas, franjas, entrevistas)
			List<EventoOcupacion> cancelables = service.obtenerSobreescribiblesCoincidentes(fecha, hIni, hFin, inst.getInstalacionId());
			// 9. Diálogo de confirmación agrupado (Sí / No / Cancelar)
			if (!cancelables.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Las siguientes actividades serán sobreescritas debido a la prioridad del partido:\n\n");
				for (EventoOcupacion act : cancelables) {
					sb.append(" • [").append(act.getTipo()).append("] ").append(act.getDetalle()).append(" (")
							.append(act.getHoraInicio().format(FORMATO_HORA)).append(" - ")
							.append(act.getHoraFin().format(FORMATO_HORA)).append(")\n");
				}
				sb.append("\n¿Está seguro de que desea continuar y dar de alta el partido cancelando estas actividades?");
				int confirm = JOptionPane.showConfirmDialog(this, sb.toString(),
						"Confirmación de Sobreescritura", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (confirm != JOptionPane.YES_OPTION) {
					return; // El usuario pulsa No o Cancelar
				}
			}
			// 10. Guardar en base de datos el partido, precios y cancelar solapamientos
			service.planificarPartido(local.getIdEquipo(), visitante.getIdEquipo(), inst.getInstalacionId(),
					fecha, hIni, hFin, esDiaClub, precioAbonado, preciosSeccion, cancelables);
			JOptionPane.showMessageDialog(this, "¡Partido dado de alta correctamente!", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			actualizarOcupacion();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al dar de alta",
					JOptionPane.ERROR_MESSAGE);
		}
	}

    private void cargarDatosIniciales() {
        try {
            // 1. Cargar equipos locales (es_propio = 1)
            List<Equipo> locales = service.obtenerEquiposLocales();
            DefaultComboBoxModel<Equipo> mdlLocales = new DefaultComboBoxModel<>();
            for (Equipo eq : locales) {
                mdlLocales.addElement(eq);
            }
            getCbEquipoLocal().setModel(mdlLocales);

            // 2. Cargar equipos visitantes externos (es_propio = 0)
            List<Equipo> visitantes = service.obtenerEquiposVisitantes();
            DefaultComboBoxModel<Equipo> mdlVisitantes = new DefaultComboBoxModel<>();
            for (Equipo eq : visitantes) {
                mdlVisitantes.addElement(eq);
            }
            getCbEquipoVisitante().setModel(mdlVisitantes);

            // 3. Cargar instalaciones
            List<Instalacion> instalaciones = service.obtenerInstalacionesDisponibles();
            DefaultComboBoxModel<Instalacion> mdlInst = new DefaultComboBoxModel<>();
            for (Instalacion inst : instalaciones) {
                mdlInst.addElement(inst);
            }
            getCbInstalacion().setModel(mdlInst);

            // Por defecto, partido ordinario desmarcado
            getChkDiaClub().setSelected(false);
            actualizarEstadoDiaClub(false, 0.0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos iniciales: " + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void actualizarOcupacion() {
		if (modeloOcupacion == null) {
			return;
		}
		modeloOcupacion.setRowCount(0);

		// 1. Partido previo (Colisión no sobreescribible en rojo suave)
		modeloOcupacion.addRow(new Object[] {
			"09:00", "11:00", "Partido", "Real Oviedo Vetusta vs Real Avilés", "No (Colisión)"
		});

		// 2. Entrevista de jugador (Sobreescribible en verde suave)
		modeloOcupacion.addRow(new Object[] {
			"12:00", "13:00", "Entrevista", "Carlos Martínez (Marca)", "Sí (Sobreescribible)"
		});

		// 3. Franja horaria de jugador (Sobreescribible en verde suave)
		modeloOcupacion.addRow(new Object[] {
			"13:00", "14:00", "Franja horaria", "David Costas (Disponible)", "Sí (Sobreescribible)"
		});

		// 4. Tarea de jardinería (Sobreescribible en verde suave)
		modeloOcupacion.addRow(new Object[] {
			"17:00", "18:30", "Jardinería", "Elena Pérez (Puesta a punto césped)", "Sí (Sobreescribible)"
		});

		// 5. Reserva externa (Sobreescribible en verde suave)
		modeloOcupacion.addRow(new Object[] {
			"18:30", "20:00", "Reserva Externa", "Marta Fernández (Reserva particular - 100.00 €)", "Sí (Sobreescribible)"
		});
	}
}