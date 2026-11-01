package giis.demo.ui.equipo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import giis.demo.exceptions.TrainingException;
import giis.demo.model.equipo.CrearEntrenamientoService;
import giis.demo.model.equipo.EventoOcupacion;
import giis.demo.model.reserva.Instalacion;
import giis.demo.ui.VentanaPrincipal;

public class VentanaCrearEntrenamiento extends JFrame {

	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vPrincipal;
	private CrearEntrenamientoService service;

	private JPanel contentPane;
	private JPanel pnSuperior;
	private JPanel pnCentro;
	private JPanel pnInferior;
	private JLabel lbDniEntrenador;
	private JComboBox<String> cbDniEntrenador;
	private JLabel lbEquipo;
	private JLabel lbNombreEquipo;
	private JPanel pnIzquierda;
	private JPanel pnInstalaciones;
	private JScrollPane scrInstalaciones;
	private JList<Instalacion> listInstalaciones;
	private DefaultListModel<Instalacion> modeloInstalaciones;
	private JPanel pnFecha;
	private JCalendar calendarioFecha;
	private JPanel pnDerecha;
	private JPanel pnOcupacion;
	private JScrollPane scrOcupacion;
	private JTable tablaOcupacion;
	private DefaultTableModel modeloTablaOcupacion;
	private JPanel pnFormularioHoras;
	private JPanel pnHoraInicio;
	private JLabel lbHoraInicio;
	private JSpinner spHoraInicio;
	private JLabel lbSeparadorInicio;
	private JSpinner spMinInicio;
	private JPanel pnHoraFin;
	private JLabel lbHoraFin;
	private JSpinner spHoraFin;
	private JLabel lbSeparadorFin;
	private JSpinner spMinFin;
	private JPanel pnBotonCrear;
	private JButton btnCrearEntrenamiento;
	private JButton btnAtras;

	public VentanaCrearEntrenamiento(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		this.service = this.vPrincipal.getCrearEntrenamientoService();

		setTitle("Planificar Entrenamiento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1160, 650);
		setLocationRelativeTo(null);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (vPrincipal != null) {
					vPrincipal.setVisible(true);
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 10));
		setContentPane(contentPane);

		contentPane.add(getPnSuperior(), BorderLayout.NORTH);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);
		contentPane.add(getPnInferior(), BorderLayout.SOUTH);

		cargarDatosIniciales();
		actualizarEventosYOcupacion();
	}

	private JPanel getPnSuperior() {
		if (pnSuperior == null) {
			pnSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
			pnSuperior.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, null, null),
					"Datos del Entrenador", TitledBorder.LEADING, TitledBorder.TOP,
					new Font("Tahoma", Font.BOLD, 13), new Color(0, 0, 0)));
			pnSuperior.add(getLbDniEntrenador());
			pnSuperior.add(getCbDniEntrenador());
			pnSuperior.add(getLbEquipo());
			pnSuperior.add(getLbNombreEquipo());
		}
		return pnSuperior;
	}

	private JLabel getLbDniEntrenador() {
		if (lbDniEntrenador == null) {
			lbDniEntrenador = new JLabel("DNI Entrenador:");
			lbDniEntrenador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbDniEntrenador;
	}

	private JComboBox<String> getCbDniEntrenador() {
		if (cbDniEntrenador == null) {
			cbDniEntrenador = new JComboBox<String>();
			cbDniEntrenador.setFont(new Font("Tahoma", Font.PLAIN, 14));
			cbDniEntrenador.setPreferredSize(new Dimension(140, 26));
			cbDniEntrenador.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarEquipoEntrenador();
					actualizarEventosYOcupacion();
				}
			});
		}
		return cbDniEntrenador;
	}

	private JLabel getLbEquipo() {
		if (lbEquipo == null) {
			lbEquipo = new JLabel("Equipo asignado:");
			lbEquipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbEquipo;
	}

	private JLabel getLbNombreEquipo() {
		if (lbNombreEquipo == null) {
			lbNombreEquipo = new JLabel("Sin equipo");
			lbNombreEquipo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lbNombreEquipo.setForeground(new Color(0, 70, 140));
		}
		return lbNombreEquipo;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel(new BorderLayout(15, 0));
			pnCentro.add(getPnIzquierda(), BorderLayout.WEST);
			pnCentro.add(getPnDerecha(), BorderLayout.CENTER);
		}
		return pnCentro;
	}

	private JPanel getPnIzquierda() {
		if (pnIzquierda == null) {
			pnIzquierda = new JPanel(new BorderLayout(0, 10));
			pnIzquierda.setPreferredSize(new Dimension(360, 0));
			pnIzquierda.add(getPnInstalaciones(), BorderLayout.NORTH);
			pnIzquierda.add(getPnFecha(), BorderLayout.CENTER);
		}
		return pnIzquierda;
	}

	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel(new BorderLayout(0, 5));
			pnInstalaciones.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, null, null),
					"1. Seleccione la Instalaci\u00F3n (Selecci\u00F3n \u00FAnica)",
					TitledBorder.LEADING, TitledBorder.TOP,
					new Font("Tahoma", Font.BOLD, 13), null));
			pnInstalaciones.setPreferredSize(new Dimension(0, 135));
			pnInstalaciones.add(getScrInstalaciones(), BorderLayout.CENTER);
		}
		return pnInstalaciones;
	}

	private JScrollPane getScrInstalaciones() {
		if (scrInstalaciones == null) {
			scrInstalaciones = new JScrollPane(getListInstalaciones());
		}
		return scrInstalaciones;
	}

	private JList<Instalacion> getListInstalaciones() {
		if (listInstalaciones == null) {
			modeloInstalaciones = new DefaultListModel<Instalacion>();
			listInstalaciones = new JList<Instalacion>(modeloInstalaciones);
			listInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 13));
			listInstalaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listInstalaciones.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						actualizarEventosYOcupacion();
					}
				}
			});
		}
		return listInstalaciones;
	}

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel(new BorderLayout(0, 5));
			pnFecha.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, null, null),
					"2. Fecha del Entrenamiento", TitledBorder.LEADING, TitledBorder.TOP,
					new Font("Tahoma", Font.BOLD, 13), null));
			pnFecha.add(getCalendarioFecha(), BorderLayout.CENTER);
		}
		return pnFecha;
	}

	private JCalendar getCalendarioFecha() {
		if (calendarioFecha == null) {
			calendarioFecha = new JCalendar();
			PropertyChangeListener listenerFecha = new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					actualizarEventosYOcupacion();
				}
			};
			calendarioFecha.getDayChooser().addPropertyChangeListener("day", listenerFecha);
			calendarioFecha.getDayChooser().addPropertyChangeListener("month", listenerFecha);
			calendarioFecha.getDayChooser().addPropertyChangeListener("year", listenerFecha);
		}
		return calendarioFecha;
	}

	private JPanel getPnDerecha() {
		if (pnDerecha == null) {
			pnDerecha = new JPanel(new BorderLayout(0, 10));
			pnDerecha.add(getPnOcupacion(), BorderLayout.CENTER);
			pnDerecha.add(getPnFormularioHoras(), BorderLayout.SOUTH);
		}
		return pnDerecha;
	}

	private JPanel getPnOcupacion() {
		if (pnOcupacion == null) {
			pnOcupacion = new JPanel(new BorderLayout(0, 5));
			pnOcupacion.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, null, null),
					"3. Ocupaci\u00F3n y Eventos (D\u00EDa e Instalaci\u00F3n)",
					TitledBorder.LEADING, TitledBorder.TOP,
					new Font("Tahoma", Font.BOLD, 13), null));
			pnOcupacion.add(getScrOcupacion(), BorderLayout.CENTER);
		}
		return pnOcupacion;
	}

	private JScrollPane getScrOcupacion() {
		if (scrOcupacion == null) {
			scrOcupacion = new JScrollPane();
			scrOcupacion.setViewportView(getTablaOcupacion());
		}
		return scrOcupacion;
	}

	private JTable getTablaOcupacion() {
		if (tablaOcupacion == null) {
			String[] columnas = { "Inicio", "Fin", "Evento", "Detalle", "¿Sobreescribible?" };
			modeloTablaOcupacion = new DefaultTableModel(columnas, 0) {
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tablaOcupacion = new JTable(modeloTablaOcupacion);
			tablaOcupacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOcupacion.setRowHeight(24);
			tablaOcupacion.getTableHeader().setReorderingAllowed(false);
			tablaOcupacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			tablaOcupacion.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

			// Ajuste de anchos generosos para ver todo el texto completo sin "..."
			tablaOcupacion.getColumnModel().getColumn(0).setPreferredWidth(55);
			tablaOcupacion.getColumnModel().getColumn(0).setMaxWidth(70);
			tablaOcupacion.getColumnModel().getColumn(1).setPreferredWidth(55);
			tablaOcupacion.getColumnModel().getColumn(1).setMaxWidth(70);
			tablaOcupacion.getColumnModel().getColumn(2).setPreferredWidth(125);
			tablaOcupacion.getColumnModel().getColumn(3).setPreferredWidth(320);
			tablaOcupacion.getColumnModel().getColumn(4).setPreferredWidth(145);

			// Renderizador con colores suaves para distinguir sobreescribibles de colisiones
			tablaOcupacion.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (!isSelected) {
						String sobre = String.valueOf(table.getValueAt(row, 4));
						if (sobre.startsWith("Sí")) {
							c.setBackground(new Color(230, 248, 230)); // verde suave para sobreescribibles
						} else if (sobre.startsWith("No")) {
							c.setBackground(new Color(255, 232, 232)); // rojo suave para colisiones no sobreescribibles
						} else {
							c.setBackground(Color.WHITE); // blanco neutro para fila informativa
						}
					}
					return c;
				}
			});
		}
		return tablaOcupacion;
	}

	private JPanel getPnFormularioHoras() {
		if (pnFormularioHoras == null) {
			pnFormularioHoras = new JPanel(new GridLayout(3, 1, 0, 5));
			pnFormularioHoras.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, null, null),
					"4. Horario del Entrenamiento", TitledBorder.LEADING, TitledBorder.TOP,
					new Font("Tahoma", Font.BOLD, 13), null));
			pnFormularioHoras.add(getPnHoraInicio());
			pnFormularioHoras.add(getPnHoraFin());
			pnFormularioHoras.add(getPnBotonCrear());
		}
		return pnFormularioHoras;
	}

	private JPanel getPnHoraInicio() {
		if (pnHoraInicio == null) {
			pnHoraInicio = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
			pnHoraInicio.add(getLbHoraInicio());
			pnHoraInicio.add(getSpHoraInicio());
			pnHoraInicio.add(getLbSeparadorInicio());
			pnHoraInicio.add(getSpMinInicio());
		}
		return pnHoraInicio;
	}

	private JLabel getLbHoraInicio() {
		if (lbHoraInicio == null) {
			lbHoraInicio = new JLabel("Hora inicio: ");
			lbHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbHoraInicio;
	}

	private JSpinner getSpHoraInicio() {
		if (spHoraInicio == null) {
			spHoraInicio = new JSpinner(new SpinnerNumberModel(8, 0, 23, 1));
			spHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spHoraInicio.setPreferredSize(new Dimension(50, 26));
			spHoraInicio.setEditor(new JSpinner.NumberEditor(spHoraInicio, "00"));
		}
		return spHoraInicio;
	}

	private JLabel getLbSeparadorInicio() {
		if (lbSeparadorInicio == null) {
			lbSeparadorInicio = new JLabel(":");
			lbSeparadorInicio.setFont(new Font("Tahoma", Font.BOLD, 14));
		}
		return lbSeparadorInicio;
	}

	private JSpinner getSpMinInicio() {
		if (spMinInicio == null) {
			spMinInicio = new JSpinner(new SpinnerNumberModel(0, 0, 55, 5));
			spMinInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spMinInicio.setPreferredSize(new Dimension(50, 26));
			spMinInicio.setEditor(new JSpinner.NumberEditor(spMinInicio, "00"));
		}
		return spMinInicio;
	}

	private JPanel getPnHoraFin() {
		if (pnHoraFin == null) {
			pnHoraFin = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
			pnHoraFin.add(getLbHoraFin());
			pnHoraFin.add(getSpHoraFin());
			pnHoraFin.add(getLbSeparadorFin());
			pnHoraFin.add(getSpMinFin());
		}
		return pnHoraFin;
	}

	private JLabel getLbHoraFin() {
		if (lbHoraFin == null) {
			lbHoraFin = new JLabel("Hora fin:     ");
			lbHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbHoraFin;
	}

	private JSpinner getSpHoraFin() {
		if (spHoraFin == null) {
			spHoraFin = new JSpinner(new SpinnerNumberModel(10, 0, 23, 1));
			spHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spHoraFin.setPreferredSize(new Dimension(50, 26));
			spHoraFin.setEditor(new JSpinner.NumberEditor(spHoraFin, "00"));
		}
		return spHoraFin;
	}

	private JLabel getLbSeparadorFin() {
		if (lbSeparadorFin == null) {
			lbSeparadorFin = new JLabel(":");
			lbSeparadorFin.setFont(new Font("Tahoma", Font.BOLD, 14));
		}
		return lbSeparadorFin;
	}

	private JSpinner getSpMinFin() {
		if (spMinFin == null) {
			spMinFin = new JSpinner(new SpinnerNumberModel(0, 0, 55, 5));
			spMinFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spMinFin.setPreferredSize(new Dimension(50, 26));
			spMinFin.setEditor(new JSpinner.NumberEditor(spMinFin, "00"));
		}
		return spMinFin;
	}

	private JPanel getPnBotonCrear() {
		if (pnBotonCrear == null) {
			pnBotonCrear = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
			pnBotonCrear.add(getBtnCrearEntrenamiento());
		}
		return pnBotonCrear;
	}

	private JButton getBtnCrearEntrenamiento() {
		if (btnCrearEntrenamiento == null) {
			btnCrearEntrenamiento = new JButton("Crear Entrenamiento");
			btnCrearEntrenamiento.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnCrearEntrenamiento.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					accionCrearEntrenamiento();
				}
			});
		}
		return btnCrearEntrenamiento;
	}

	private JPanel getPnInferior() {
		if (pnInferior == null) {
			pnInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
			pnInferior.add(getBtnAtras());
		}
		return pnInferior;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atr\u00E1s");
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					if (vPrincipal != null) {
						vPrincipal.setVisible(true);
					}
				}
			});
		}
		return btnAtras;
	}

	private void cargarDatosIniciales() {
		List<String> dnis = service.obtenerDnisEntrenadores();
		getCbDniEntrenador().setModel(new DefaultComboBoxModel<>(dnis.toArray(new String[0])));
		actualizarEquipoEntrenador();

		modeloInstalaciones.clear();
		for (Instalacion inst : service.obtenerInstalacionesDisponibles()) {
			modeloInstalaciones.addElement(inst);
		}
		if (modeloInstalaciones.getSize() > 0) {
			getListInstalaciones().setSelectedIndex(0);
		}
	}

	private void actualizarEquipoEntrenador() {
		String dni = (String) getCbDniEntrenador().getSelectedItem();
		String nombreEquipo = service.obtenerNombreEquipoDeEntrenador(dni);
		getLbNombreEquipo().setText(nombreEquipo);
	}

	private void actualizarEventosYOcupacion() {
		Date fechaUtil = getCalendarioFecha().getDate();
		if (fechaUtil == null) {
			return;
		}
		LocalDate fecha = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Instalacion instalacionSel = getListInstalaciones().getSelectedValue();
		int idInstalacion = (instalacionSel != null) ? instalacionSel.getInstalacionId() : 0;
		String nombreInstalacion = (instalacionSel != null) ? instalacionSel.getName() : "Instalación";
		String dni = (String) getCbDniEntrenador().getSelectedItem();
		int idEquipo = service.obtenerIdEquipoDeEntrenador(dni);

		getPnOcupacion().setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"3. Ocupación (" + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + nombreInstalacion + ")",
				TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 13), null));

		DefaultTableModel modelo = (DefaultTableModel) getTablaOcupacion().getModel();
		modelo.setRowCount(0);
		List<Object[]> filas = service.obtenerFilasOcupacion(idEquipo, idInstalacion, fecha);

		if (filas.isEmpty()) {
			modelo.addRow(new Object[] { "--:--", "--:--", "Libre", "Instalación libre sin eventos ni ocupación", "-" });
		} else {
			for (Object[] fila : filas) {
				modelo.addRow(fila);
			}
		}
	}

	private void accionCrearEntrenamiento() {
		try {
			int hIniNum = (Integer) getSpHoraInicio().getValue();
			int mIniNum = (Integer) getSpMinInicio().getValue();
			LocalTime hIni = LocalTime.of(hIniNum, mIniNum);

			int hFinNum = (Integer) getSpHoraFin().getValue();
			int mFinNum = (Integer) getSpMinFin().getValue();
			LocalTime hFin = LocalTime.of(hFinNum, mFinNum);

			Date fechaUtil = getCalendarioFecha().getDate();
			if (fechaUtil == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha en el calendario.", "Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}
			LocalDate fecha = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			Instalacion inst = getListInstalaciones().getSelectedValue();
			if (inst == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una instalación.", "Validación", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String dni = (String) getCbDniEntrenador().getSelectedItem();
			int idEquipo = service.obtenerIdEquipoDeEntrenador(dni);
			DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

			// 1. Comprobar colisiones no sobreescribibles y obtener sobreescribibles coincidentes
			List<EventoOcupacion> sobreescribibles = service.obtenerSobreescribiblesCoincidentes(fecha, hIni, hFin, idEquipo, inst.getInstalacionId());

			// 2. Si hay reservas, jardinería o franjas/entrevistas coincidentes, pedir confirmación al entrenador
			if (!sobreescribibles.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("El entrenamiento coincide con las siguientes tareas/reservas/franjas con menor prioridad:\n\n");
				for (EventoOcupacion e : sobreescribibles) {
					sb.append(" • ").append(e.getTipo()).append(": ").append(e.getDetalle()).append(" (")
							.append(e.getHoraInicio().format(formatoHora)).append(" - ")
							.append(e.getHoraFin().format(formatoHora)).append(")\n");
				}
				sb.append("\nAl tener prioridad el entrenamiento, estas serán canceladas y se eliminarán del sistema.\n");
				sb.append("¿Desea continuar y crear el entrenamiento?");
				int confirm = JOptionPane.showConfirmDialog(this, sb.toString(),
						"Confirmar cancelación de eventos coincidentes", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (confirm != JOptionPane.YES_OPTION) {
					return; // El entrenador cancela la operación
				}
			}

			// 3. Crear el entrenamiento y cancelar en base de datos los coincidentes
			service.crearEntrenamiento(fecha, hIni, hFin, idEquipo, inst.getInstalacionId(), sobreescribibles);

			JOptionPane.showMessageDialog(this, "¡Entrenamiento planificado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			actualizarEventosYOcupacion();

		} catch (TrainingException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Colisión detectada", JOptionPane.WARNING_MESSAGE);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}