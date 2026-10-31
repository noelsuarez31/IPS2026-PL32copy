package giis.demo.ui.abonos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

import giis.demo.exceptions.AbonoNoPermitidoException;
import giis.demo.model.abonos.Abonado;
import giis.demo.model.abonos.Abono;
import giis.demo.model.abonos.Abono.Categoria;
import giis.demo.model.abonos.VentaDeAbonos;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;
import giis.demo.model.equipo.Equipo;
import giis.demo.ui.VentanaPrincipal;

/**
 * Create the frame.
 */
public class VentanaAbono extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelUsuario;
	private JLabel lbNombre;
	private JLabel lblApellidos;
	private JLabel lblDni;
	private JLabel lblEmail;
	private JLabel lbNacimiento;
	private JTextField tfNombre;
	private JTextField tfApellidos;
	private JTextField tfDni;
	private JTextField tfEmail;
	private JDateChooser dcNacimiento;
	private JPanel panelSeleccion;
	private JPanel panelVisualización;
	private JPanel panelEleccion;
	private JLabel lbEquipo;
	private JComboBox<Equipo> cbEquipo;
	private JLabel lblTribuna;
	private JComboBox<TipoTribuna> cbTribuna;
	private JLabel lblSeccion;
	private JComboBox<TipoSeccion> cbSeccion;
	private JLabel lblFila;
	private JLabel lblAsiento;
	private JSpinner spFila;
	private JSpinner spAsiento;
	private JPanel panelTabla;
	private JPanel panelLeyenda;
	private JPanel panelPrecio;
	private JLabel lbPrecio;
	private JTextField tfPrecio;
	private JPanel panelBotones;
	private JButton btFinalizar;

	private JTable tablaAsientos;
	private boolean[][] asientosSeleccionados = new boolean[10][15];

	private VentaDeAbonos va;

	private JLabel lbColorLibre;
	private JLabel lbLibre;
	private JLabel lbColorNoDisponible;
	private JLabel lbNoDisponible;
	private JLabel lbColorSeleccion;
	private JLabel lbSeleccion;

	private VentanaPrincipal vp;
	private JButton btConsultar;
	private JLabel lblCategora;
	private JTextField tfCategoria;
	private JButton btVolver;
	private JButton btCancelar;

	private CambiarTabla ct;
	private int filaSeleccionada = -1;
	private int asientoSeleccionado = -1;

	private CambiarSpinner cs;
	private CerrarVentana cv;

	public VentanaAbono(VentanaPrincipal vp) {

		this.vp = vp;

		va = new VentaDeAbonos();
		ct = new CambiarTabla(this);
		cs = new CambiarSpinner();
		cv = new CerrarVentana();

		setTitle("Venta de abonos");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1007, 639);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelUsuario(), BorderLayout.NORTH);
		getContentPane().add(getPanelSeleccion(), BorderLayout.CENTER);
		getContentPane().add(getPanelVisualización(), BorderLayout.EAST);
		getContentPane().add(getPanelEleccion(), BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		addWindowListener(cv);

	}

	private JPanel getPanelUsuario() {

		if (panelUsuario == null) {
			panelUsuario = new JPanel();
			panelUsuario.setPreferredSize(new Dimension(0, 240));
			panelUsuario.setBorder(
					new TitledBorder(null, "Datos del Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelUsuario.setLayout(null);
			panelUsuario.add(getLbNombre());
			panelUsuario.add(getLblApellidos());
			panelUsuario.add(getLblDni());
			panelUsuario.add(getLblEmail());
			panelUsuario.add(getLbNacimiento());
			panelUsuario.add(getTfNombre());
			panelUsuario.add(getTfApellidos());
			panelUsuario.add(getTfDni());
			panelUsuario.add(getTfEmail());
			panelUsuario.add(getDcNacimiento());
			panelUsuario.add(getBtConsultar());
			panelUsuario.add(getBtCancelar());
		}
		return panelUsuario;
	}

	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre:");
			lbNombre.setBounds(11, 30, 63, 20);
			lbNombre.setDisplayedMnemonic('N');
			lbNombre.setLabelFor(getTfNombre());
		}
		return lbNombre;
	}

	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setBounds(11, 66, 71, 20);
			lblApellidos.setDisplayedMnemonic('P');
			lblApellidos.setLabelFor(getTfApellidos());
		}
		return lblApellidos;
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni:");
			lblDni.setBounds(11, 102, 30, 20);
			lblDni.setDisplayedMnemonic('D');
			lblDni.setLabelFor(getTfDni());
		}
		return lblDni;
	}

	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Email:");
			lblEmail.setBounds(11, 182, 45, 20);
			lblEmail.setDisplayedMnemonic('M');
			lblEmail.setLabelFor(getTfEmail());
		}
		return lblEmail;
	}

	private JLabel getLbNacimiento() {
		if (lbNacimiento == null) {
			lbNacimiento = new JLabel("Fecha de nacimiento:");
			lbNacimiento.setBounds(11, 142, 150, 20);
			lbNacimiento.setDisplayedMnemonic('F');
			lbNacimiento.setLabelFor(getDcNacimiento());
		}
		return lbNacimiento;
	}

	private JTextField getTfNombre() {
		if (tfNombre == null) {
			tfNombre = new JTextField();
			tfNombre.setBounds(97, 30, 280, 26);
			tfNombre.setColumns(10);
		}
		return tfNombre;
	}

	private JTextField getTfApellidos() {
		if (tfApellidos == null) {
			tfApellidos = new JTextField();
			tfApellidos.setBounds(97, 66, 280, 26);
			tfApellidos.setColumns(10);
		}
		return tfApellidos;
	}

	private JTextField getTfDni() {
		if (tfDni == null) {
			tfDni = new JTextField();
			tfDni.setBounds(97, 102, 280, 26);
			tfDni.setColumns(10);
		}
		return tfDni;
	}

	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setBounds(101, 179, 280, 26);
			tfEmail.setColumns(10);
		}
		return tfEmail;
	}

	private JDateChooser getDcNacimiento() {

		if (dcNacimiento == null) {

			dcNacimiento = new JDateChooser();
			dcNacimiento.setDateFormatString("dd/MM/yyyy");

			dcNacimiento.setMaxSelectableDate(new Date());

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -18);

			dcNacimiento.setDate(calendar.getTime());
			dcNacimiento.setBounds(176, 139, 150, 26);
		}
		return dcNacimiento;
	}

	private JPanel getPanelSeleccion() {
		if (panelSeleccion == null) {
			panelSeleccion = new JPanel();
			panelSeleccion.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					"Selecci\u00F3n de asientos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelSeleccion.setLayout(null);
			panelSeleccion.add(getLbEquipo());
			panelSeleccion.add(getCbEquipo());
			panelSeleccion.add(getLblTribuna());
			panelSeleccion.add(getCbTribuna());
			panelSeleccion.add(getLblSeccion());
			panelSeleccion.add(getCbSeccion());
			panelSeleccion.add(getLblFila());
			panelSeleccion.add(getLblAsiento());
			panelSeleccion.add(getSpFila());
			panelSeleccion.add(getSpAsiento());
			panelSeleccion.add(getLblCategora());
			panelSeleccion.add(getTfCategoria());
		}
		return panelSeleccion;
	}

	private JPanel getPanelVisualización() {
		if (panelVisualización == null) {
			panelVisualización = new JPanel();
			panelVisualización.setPreferredSize(new Dimension(420, 0));
			panelVisualización.setLayout(new BorderLayout(0, 0));
			panelVisualización.add(getPanelTabla(), BorderLayout.CENTER);
			panelVisualización.add(getPanelLeyenda(), BorderLayout.SOUTH);
		}
		return panelVisualización;
	}

	private JPanel getPanelEleccion() {
		if (panelEleccion == null) {
			panelEleccion = new JPanel();
			panelEleccion.setLayout(new BorderLayout(0, 0));
			panelEleccion.add(getPanelPrecio(), BorderLayout.WEST);
			panelEleccion.add(getPanelBotones(), BorderLayout.EAST);
		}
		return panelEleccion;
	}

	private JLabel getLbEquipo() {
		if (lbEquipo == null) {
			lbEquipo = new JLabel("Equipo:");
			lbEquipo.setDisplayedMnemonic('Q');
			lbEquipo.setBounds(15, 40, 63, 20);
		}
		return lbEquipo;
	}

	private JComboBox<Equipo> getCbEquipo() {
		if (cbEquipo == null) {
			cbEquipo = new JComboBox<Equipo>();
			cbEquipo.setEnabled(false);
			cbEquipo.addItemListener(ct);
			try {
				cbEquipo.setModel(new DefaultComboBoxModel<Equipo>(va.getEquiposLocales()));
			} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "Error al obtener los equipos locales de la base de datos", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			cbEquipo.setBounds(93, 36, 298, 28);
		}
		return cbEquipo;
	}

	private JLabel getLblTribuna() {
		if (lblTribuna == null) {
			lblTribuna = new JLabel("Tribuna:");
			lblTribuna.setDisplayedMnemonic('T');
			lblTribuna.setBounds(15, 84, 63, 20);
		}
		return lblTribuna;
	}

	private JComboBox<TipoTribuna> getCbTribuna() {
		if (cbTribuna == null) {
			cbTribuna = new JComboBox<TipoTribuna>(va.getTipoTribuna());
			cbTribuna.setEnabled(false);
			cbTribuna.setBounds(93, 80, 298, 28);
			cbTribuna.addItemListener(ct);
		}
		return cbTribuna;
	}

	private JLabel getLblSeccion() {
		if (lblSeccion == null) {
			lblSeccion = new JLabel("Seccion:");
			lblSeccion.setDisplayedMnemonic('S');
			lblSeccion.setBounds(15, 127, 63, 20);
		}
		return lblSeccion;
	}

	private JComboBox<TipoSeccion> getCbSeccion() {
		if (cbSeccion == null) {
			cbSeccion = new JComboBox<TipoSeccion>(va.getTipoSeccion());
			cbSeccion.setEnabled(false);
			cbSeccion.setBounds(93, 123, 298, 28);
			cbSeccion.addItemListener(ct);
		}
		return cbSeccion;
	}

	private JLabel getLblFila() {
		if (lblFila == null) {
			lblFila = new JLabel("Fila:");
			lblFila.setDisplayedMnemonic('L');
			lblFila.setBounds(406, 40, 63, 20);
		}
		return lblFila;
	}

	private JLabel getLblAsiento() {
		if (lblAsiento == null) {
			lblAsiento = new JLabel("Asiento:");
			lblAsiento.setDisplayedMnemonic('A');
			lblAsiento.setBounds(406, 84, 63, 20);
		}
		return lblAsiento;
	}

	private JSpinner getSpFila() {
		if (spFila == null) {
			spFila = new JSpinner();
			spFila.setModel(new SpinnerNumberModel(1, 1, 10, 1));
			spFila.setEnabled(false);
			spFila.setBounds(480, 38, 53, 26);
			spFila.addChangeListener(cs);
		}
		return spFila;
	}

	private JSpinner getSpAsiento() {

		if (spAsiento == null) {

			spAsiento = new JSpinner();
			spAsiento.setModel(new SpinnerNumberModel(1, 1, 15, 1));
			spAsiento.setEnabled(false);
			spAsiento.setBounds(480, 81, 53, 26);
			spAsiento.addChangeListener(cs);
		}
		return spAsiento;
	}

	private JPanel getPanelTabla() {
		
		if (panelTabla == null) {
			panelTabla = new JPanel();
			panelTabla.setLayout(new BorderLayout(0, 0));
			panelTabla.add(getTablaAsientos(), BorderLayout.CENTER);
		}
		return panelTabla;
	}

	private JPanel getPanelLeyenda() {
		if (panelLeyenda == null) {
			panelLeyenda = new JPanel();
			panelLeyenda
					.setBorder(new TitledBorder(null, "Leyenda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelLeyenda.setLayout(new GridLayout(3, 2, 5, 5));
			panelLeyenda.add(getLbColorLibre());
			panelLeyenda.add(getLbLibre());
			panelLeyenda.add(getLbColorNoDisponible());
			panelLeyenda.add(getLbNoDisponible());
			panelLeyenda.add(getLbColorSeleccion());
			panelLeyenda.add(getLbSeleccion());
		}
		return panelLeyenda;
	}

	private JLabel getLbColorLibre() {
		if (lbColorLibre == null) {
			lbColorLibre = new JLabel();
			lbColorLibre.setOpaque(true);
			lbColorLibre.setBackground(new Color(128, 255, 128));
		}
		return lbColorLibre;
	}

	private JLabel getLbLibre() {
		if (lbLibre == null) {
			lbLibre = new JLabel("Libre");
		}
		return lbLibre;
	}

	private JLabel getLbColorNoDisponible() {
		if (lbColorNoDisponible == null) {
			lbColorNoDisponible = new JLabel();
			lbColorNoDisponible.setOpaque(true);
			lbColorNoDisponible.setBackground(new Color(255, 128, 128));
		}
		return lbColorNoDisponible;
	}

	private JLabel getLbNoDisponible() {
		if (lbNoDisponible == null) {
			lbNoDisponible = new JLabel("No disponible");
		}
		return lbNoDisponible;
	}

	private JLabel getLbColorSeleccion() {
		if (lbColorSeleccion == null) {
			lbColorSeleccion = new JLabel();
			lbColorSeleccion.setOpaque(true);
			lbColorSeleccion.setBackground(new Color(0, 128, 255));
		}
		return lbColorSeleccion;
	}

	private JLabel getLbSeleccion() {
		if (lbSeleccion == null) {
			lbSeleccion = new JLabel("Tu asiento");
		}
		return lbSeleccion;
	}

	private JPanel getPanelPrecio() {
		if (panelPrecio == null) {
			panelPrecio = new JPanel();
			panelPrecio.add(getLbPrecio());
			panelPrecio.add(getTfPrecio());
		}
		return panelPrecio;
	}

	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio:");
		}
		return lbPrecio;
	}

	private JTextField getTfPrecio() {
		if (tfPrecio == null) {
			tfPrecio = new JTextField();
			tfPrecio.setEditable(false);
			tfPrecio.setForeground(new Color(255, 0, 0));
			tfPrecio.setColumns(10);
		}
		return tfPrecio;
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.add(getBtVolver());
			panelBotones.add(getBtFinalizar());
		}
		return panelBotones;
	}

	private void volverAlMenu() {

		try {
			va.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cerrar la conexion con la base de datos", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		this.dispose();
		vp.setVisible(true);

	}

	private JButton getBtFinalizar() {
		if (btFinalizar == null) {
			btFinalizar = new JButton("Finalizar");
			btFinalizar.setBackground(new Color(128, 255, 128));
			btFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					finalizar();

				}
			});
			btFinalizar.setEnabled(false);
			btFinalizar.setToolTipText("Pulsa para finalizar la compra");
			btFinalizar.setMnemonic('Z');
		}
		return btFinalizar;
	}

	private void finalizar() {

		Equipo equipo = (Equipo) getCbEquipo().getSelectedItem();

		if (equipo == null) {

			JOptionPane.showMessageDialog(null, "Seleccione un equipo", "Error", JOptionPane.ERROR_MESSAGE);
			return;

		}

		if (filaSeleccionada < 0 || asientoSeleccionado < 0) {

			JOptionPane.showMessageDialog(null, "Seleccione un asiento anyes de finalizar la compra",
					"Asiento no seleccionado", JOptionPane.WARNING_MESSAGE);
			return;

		}

		if (asientosSeleccionados[filaSeleccionada][asientoSeleccionado]) {

			JOptionPane.showMessageDialog(null, "El asiento seleccionado ya no está disponible.",
					"Asiento no disponible", JOptionPane.WARNING_MESSAGE);

			actualizarTablaAsientos();
			return;
		}

		try {

			LocalDate fechaNacimiento = getDcNacimiento().getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			Abonado abonado = obtenerDatosAbonado();
			Abono abono = obtenerDatosAbono(equipo, fechaNacimiento);

			va.venderAbono(abonado, abono, equipo.getIdEquipo());
			enviarEmail(getTfEmail().getText().trim(), construirCuerpoEmail(abonado, abono, equipo));

			JOptionPane.showMessageDialog(null,
					"Abono vendido correctamente.\n" + "Abonado: " + abonado.getNombre() + " " + abonado.getApellidos()
							+ "\nDNI: " + abonado.getDni() + "\nCategoría: " + abono.getCategoria() + "\nAsiento: fila "
							+ abono.getFila() + ", asiento " + abono.getAsiento() + "\nPrecio: " + abono.getPrecio()
							+ " €",
					"Venta realizada", JOptionPane.INFORMATION_MESSAGE);

			actualizarTablaAsientos();
			reiniciar();

		} catch (SQLException | AbonoNoPermitidoException ex) {

			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error el vender el abono", JOptionPane.ERROR_MESSAGE);

			actualizarTablaAsientos();

		}

	}
	
	private String construirCuerpoEmail(Abonado abonado, Abono abono, Equipo equipo) {

		StringBuilder sb = new StringBuilder();

		sb.append("Hola ").append(abonado.getNombre()).append(" ").append(abonado.getApellidos()).append(",\n\n");
		sb.append("Gracias por abonarte. Estos son los datos de tu abono:\n\n");
		sb.append("Equipo: ").append(equipo.getNombre()).append("\n");
		sb.append("Tribuna: ").append(getCbTribuna().getSelectedItem()).append("\n");
		sb.append("Sección: ").append(getCbSeccion().getSelectedItem()).append("\n");
		sb.append("Fila ").append(abono.getFila()).append(", asiento ").append(abono.getAsiento()).append("\n");
		sb.append("Categoría: ").append(abono.getCategoria()).append("\n");
		sb.append("Precio: ").append(abono.getPrecio()).append(" €\n");

		return sb.toString();
	}

	private void enviarEmail(final String destinatario, final String cuerpo) {

		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				new ServicioEmail().enviar(destinatario, "Confirmación de abono de temporada", cuerpo);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(VentanaAbono.this,
							"El abono se ha vendido, pero no se ha podido enviar el email de confirmación.",
							"Aviso", JOptionPane.WARNING_MESSAGE);
					System.err.println("Error al enviar el email: " + e.getMessage());
				}
			}
		}.execute();
	}

	private Abonado obtenerDatosAbonado() {

		String dni = getTfDni().getText().trim().toUpperCase();
		String name = getTfNombre().getText().trim();
		String apellidos = getTfApellidos().getText().trim();
		String email = getTfEmail().getText().trim();

		LocalDate fechaNacimiento = getDcNacimiento().getDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();

		return new Abonado(dni, name, apellidos, fechaNacimiento, email);
	}

	private Abono obtenerDatosAbono(Equipo equipo, LocalDate fechaNacimiento) {

		Categoria categoria = va.calcularCategoria(fechaNacimiento);
		double precio = va.getPrecio(categoria);

		LocalDate hoy = LocalDate.now();
		LocalDate fechaInicio;
		LocalDate fechaFin;

		if (hoy.getMonthValue() >= 7) {

			fechaInicio = LocalDate.of(hoy.getYear(), 7, 1);
			fechaFin = LocalDate.of(hoy.getYear() + 1, 6, 30);

		} else {

			fechaInicio = LocalDate.of(hoy.getYear() - 1, 7, 1);
			fechaFin = LocalDate.of(hoy.getYear(), 6, 30);

		}

		TipoTribuna tribuna = (TipoTribuna) getCbTribuna().getSelectedItem();
		TipoSeccion seccion = (TipoSeccion) getCbSeccion().getSelectedItem();

		int fila = filaSeleccionada + 1;
		int asiento = asientoSeleccionado + 1;

		return new Abono(equipo.getNombre(), tribuna.name(), seccion.name(), fila, asiento, fechaInicio, fechaFin,
				LocalDateTime.now(), categoria, precio);
	}

	private JTable getTablaAsientos() {

		if (tablaAsientos == null) {

			tablaAsientos = new JTable(10, 15);
			tablaAsientos.setBackground(new Color(240, 240, 240));
			tablaAsientos.setEnabled(false);

			// Para evitar que el usario pueda editar las celdas

			tablaAsientos.setDefaultEditor(Object.class, null);

			// Pintar las celdas

			tablaAsientos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					if (!getTablaAsientos().isEnabled()) {
						return;
					}

					int fila = tablaAsientos.rowAtPoint(e.getPoint());
					int columna = tablaAsientos.columnAtPoint(e.getPoint());

					if (!checksFilaColumna(fila, columna)) {

						return;
					}

					if (asientosSeleccionados[fila][columna]) {

						JOptionPane.showMessageDialog(null, "Este asiento ya está ocupado. Seleccione otro por favor.",
								"Asiento no disponible", JOptionPane.WARNING_MESSAGE);
						return;
					}

					filaSeleccionada = fila;
					asientoSeleccionado = columna;

					spFila.setValue(fila + 1);
					spAsiento.setValue(columna + 1);

					tablaAsientos.repaint();

				}

			});
			tablaAsientos.setTableHeader(null);
			tablaAsientos.setRowHeight(22);
			tablaAsientos.setShowGrid(true);
			tablaAsientos.setGridColor(Color.BLACK);

			for (int i = 0; i < 15; i++) {
				tablaAsientos.getColumnModel().getColumn(i).setPreferredWidth(25);
			}
			for (int fila = 0; fila < 10; fila++) {
				for (int asiento = 0; asiento < 15; asiento++) {
					tablaAsientos.setValueAt(asiento + 1, fila, asiento);
				}
			}

			tablaAsientos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					if (asientosSeleccionados[row][column]) {

						c.setBackground(new Color(255, 128, 128));

					} else if (row == filaSeleccionada && column == asientoSeleccionado) {

						c.setBackground(new Color(0, 128, 255));

					} else {

						c.setBackground(new Color(128, 255, 128));

					}

					c.setForeground(Color.BLACK);
					setHorizontalAlignment(CENTER);
					return c;
				}
			});
		}
		return tablaAsientos;
	}

	private boolean checksFilaColumna(int fila, int columna) {

		return fila >= 0 && columna >= 0 && fila < 10 && columna < 15;

	}

	private void actualizarTablaAsientos() {

		for (boolean[] fila : asientosSeleccionados) {

			Arrays.fill(fila, false);
		}

		filaSeleccionada = -1;
		asientoSeleccionado = -1;

		Equipo equipoSeleccionado = (Equipo) getCbEquipo().getSelectedItem();

		if (equipoSeleccionado != null) {

			TipoTribuna tribunaSeleccionada = (TipoTribuna) getCbTribuna().getSelectedItem();
			TipoSeccion seccionSeleccionada = (TipoSeccion) getCbSeccion().getSelectedItem();

			if (tribunaSeleccionada != null && seccionSeleccionada != null) {

				try {

					LocalDate hoy = LocalDate.now();
					LocalDate fechaInicio;
					LocalDate fechaFin;

					if (hoy.getMonthValue() >= 7) {

						fechaInicio = LocalDate.of(hoy.getYear(), 7, 1);
						fechaFin = LocalDate.of(hoy.getYear() + 1, 6, 30);

					} else {

						fechaInicio = LocalDate.of(hoy.getYear() - 1, 7, 1);
						fechaFin = LocalDate.of(hoy.getYear(), 6, 30);

					}

					for (int[] asiento : va.getAsientosOcupados(equipoSeleccionado, tribunaSeleccionada,
							seccionSeleccionada, fechaInicio, fechaFin)) {

						asientosSeleccionados[asiento[0] - 1][asiento[1] - 1] = true;
					}
				} catch (SQLException e) {

					JOptionPane.showMessageDialog(null, "Error al obtener los asientos ocupados de la base de datos",
							"Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}

		}

		sincronizarSeleccion();
		getTablaAsientos().repaint();
	}

	private void sincronizarSeleccion() {

		int fila = (int) getSpFila().getValue() - 1;
		int asiento = (int) getSpAsiento().getValue() - 1;

		if (asientosSeleccionados[fila][asiento]) {

			filaSeleccionada = -1;
			asientoSeleccionado = -1;

		} else {

			filaSeleccionada = fila;
			asientoSeleccionado = asiento;

		}

		getTablaAsientos().repaint();

	}

	private JButton getBtConsultar() {
		if (btConsultar == null) {
			btConsultar = new JButton("Consultar");
			btConsultar.setBackground(new Color(128, 255, 128));
			btConsultar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					consultarAbonado();
				}
			});
			btConsultar.setToolTipText("Pulsa para consultar");
			btConsultar.setMnemonic('O');
			btConsultar.setBounds(434, 29, 117, 29);
		}
		return btConsultar;
	}

	/**
	 * Consulta si el abonado existe en la base de datos y si tiene un abono activo,
	 * si es asi muestra un mensaje de error y no permite continuar
	 */
	private void consultarAbonado() {

		if (checkCamposVacios()) {

			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!comprobarValidez()) {
			return;
		}

		String dni = this.getTfDni().getText().trim().toUpperCase();

		try {

			if (va.tieneAbonoEstaTemporada(dni)) {

				Abono abono = va.getInfoAbono(dni);

				JOptionPane.showMessageDialog(null,
						"El dni " + dni + " ya tiene un abono para esta temporada: " + abono.toString(),
						"Abono existente", JOptionPane.WARNING_MESSAGE);

				reiniciar();
				return;
			}

		} catch (SQLException sqle) {

			JOptionPane.showMessageDialog(null,
					"Se ha producido un error al comprobar el abonado en la Base de datos, pongase en contacto con ayuda al cliente",
					"Error", JOptionPane.ERROR_MESSAGE);
			reiniciar();
			return;

		}

		LocalDate fechaNacimiento = getDcNacimiento().getDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		Categoria categoria = va.calcularCategoria(fechaNacimiento);

		getTfCategoria().setText(categoria.toString());
		getTfPrecio().setText(va.getPrecio(categoria) + " €");

		habilitarCamposSeleccion(true);
		actualizarTablaAsientos();
	}

	private boolean checkCamposVacios() {

		return getTfNombre().getText().trim().isEmpty() || getTfApellidos().getText().isEmpty()
				|| getTfDni().getText().isEmpty() || getTfEmail().getText().isEmpty()
				|| getDcNacimiento().getDate() == null;

	}

	private boolean comprobarValidez() {

		String dni = getTfDni().getText();

		if (!dni.matches("\\d{8}[A-Za-z]")) {

			JOptionPane.showMessageDialog(null, "El DNI debe tener 8 números y una letra.");
			return false;
		}

		String correo = getTfEmail().getText();

		if (!ValidadorEmail.esValido(correo)) {
			
			JOptionPane.showMessageDialog(null, "El formato del email debe ser xxxx@xx.xxx");
			
			getTfEmail().requestFocus();
			return false;
		}
		
		return true;
		
	}

	/**
	 * Habilita o deshabilita los campos de seleccion de asientos y el boton de
	 * finalizar
	 * 
	 * @param habilitar
	 */
	private void habilitarCamposSeleccion(boolean habilitar) {

		getBtConsultar().setEnabled(!habilitar);
		getTfNombre().setEnabled(!habilitar);
		getTfApellidos().setEnabled(!habilitar);
		getTfDni().setEnabled(!habilitar);
		getDcNacimiento().setEnabled(!habilitar);
		getTfEmail().setEnabled(!habilitar);

		getCbEquipo().setEnabled(habilitar);
		getCbTribuna().setEnabled(habilitar);
		getCbSeccion().setEnabled(habilitar);
		getSpFila().setEnabled(habilitar);
		getSpAsiento().setEnabled(habilitar);
		getTablaAsientos().setEnabled(habilitar);

		getBtFinalizar().setEnabled(habilitar);
		getBtCancelar().setEnabled(habilitar);
	}

	private JLabel getLblCategora() {
		if (lblCategora == null) {
			lblCategora = new JLabel("Categoría:");
			lblCategora.setBounds(15, 237, 98, 20);
		}
		return lblCategora;
	}

	private JTextField getTfCategoria() {
		if (tfCategoria == null) {
			tfCategoria = new JTextField();
			tfCategoria.setForeground(Color.RED);
			tfCategoria.setEditable(false);
			tfCategoria.setColumns(10);
			tfCategoria.setBounds(92, 234, 146, 26);
		}
		return tfCategoria;
	}

	private JButton getBtVolver() {
		if (btVolver == null) {
			btVolver = new JButton("Volver");
			btVolver.setBackground(new Color(255, 128, 128));
			btVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					volverAlMenu();
				}
			});
			btVolver.setToolTipText("Pulsa para volver al menú inicial");
			btVolver.setMnemonic('V');
		}
		return btVolver;
	}

	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setBackground(new Color(255, 128, 128));
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					reiniciar();
				}
			});
			btCancelar.setEnabled(false);
			btCancelar.setToolTipText("Cancelar la compra");
			btCancelar.setMnemonic('C');
			btCancelar.setBounds(434, 66, 117, 29);
		}
		return btCancelar;
	}

	private void reiniciar() {

		getTfNombre().setText("");
		getTfApellidos().setText("");
		getTfDni().setText("");
		getTfEmail().setText("");

		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.YEAR, -18);

		getDcNacimiento().setDate(calendario.getTime());

		getTfCategoria().setText("");
		getTfPrecio().setText("");

		if (getCbEquipo().getItemCount() > 0) {
			getCbEquipo().setSelectedIndex(0);
		}

		if (getCbTribuna().getItemCount() > 0) {
			getCbTribuna().setSelectedIndex(0);
		}

		if (getCbSeccion().getItemCount() > 0) {
			getCbSeccion().setSelectedIndex(0);
		}

		getSpFila().setValue(1);
		getSpAsiento().setValue(1);

		habilitarCamposSeleccion(false);

		filaSeleccionada = -1;
		asientoSeleccionado = -1;
		getTablaAsientos().repaint();
	}

	private class CambiarTabla implements ItemListener {

		private VentanaAbono ventana;

		public CambiarTabla(VentanaAbono ventanaAbono) {
			this.ventana = ventanaAbono;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (e.getStateChange() == ItemEvent.SELECTED) {

				filaSeleccionada=-1;
				asientoSeleccionado=-1;
				
				sincronizarSeleccion();
				
				ventana.actualizarTablaAsientos();

			}

		}

	}

	private class CambiarSpinner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {

			sincronizarSeleccion();

		}

	}

	private class CerrarVentana extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			volverAlMenu();
		}
	}

}