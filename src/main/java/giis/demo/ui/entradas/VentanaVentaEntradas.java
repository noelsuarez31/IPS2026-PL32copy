package giis.demo.ui.entradas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.entradas.Butaca;
import giis.demo.model.entradas.Partido;
import giis.demo.model.entradas.VentaDeEntradas;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;
import giis.demo.exceptions.SinDisponibilidadException;
import giis.demo.ui.VentanaPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaVentaEntradas extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JLabel lbSelectTribuna;
	private JComboBox<TipoTribuna> cbTribuna;

	private JLabel lbSelectSeccion;
	private JComboBox<TipoSeccion> cbSeccion;

	private JLabel lbNumEntradas;
	private JSpinner spNumEntradas;

	private JLabel lbPartido;
	private JComboBox<Partido> cbPartido;

	private JButton btBuscarAsientos;
	private JTextField tfNoAsientosDisponibles;
	private JButton btCancelar;

	private VentaDeEntradas venta;

	private VentanaPrincipal vp;
	private JTable tablaAsientos;
	private JPanel panelLeyenda;
	private JButton btVerde;
	private JLabel lbLibre;
	private JLabel lbOcupado;
	private JPanel panelTitulo;
	private JLabel lbTitulo;
	private JPanel panelInfo;
	private JLabel lbAsientosReservados;
	private JTextField tfAsientosObtenidos;
	private JLabel lbPrecio;
	private JTextField tfPrecio;
	private JPanel panelBotones;
	private JButton btVolver;
	private JButton btConfirma;

	private MostrarAsientos ma;
	private CerrarVentana cv;
	private JLabel lbReservado;
	private JButton btRojo;
	private JButton btAzul;
	private JLabel lbEmail;
	private JTextField tfEmail;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public VentanaVentaEntradas(VentanaPrincipal vp) throws SQLException {

		this.ma = new MostrarAsientos();
		this.cv = new CerrarVentana();

		this.addWindowListener(cv);

		this.setTitle("Venta de entradas");

		this.vp = vp;
		this.venta = new VentaDeEntradas();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1172, 642);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(10, 10));
		contentPane.add(getPanelSuperior(), BorderLayout.NORTH);
		contentPane.add(getPanelCentral(), BorderLayout.CENTER);
		contentPane.add(getPanelInferior(), BorderLayout.SOUTH);
		contentPane.add(getPanelVisualizacion(), BorderLayout.EAST);

		this.setLocationRelativeTo(null);

		if (cbPartido.getItemCount() == 0) {

			tfNoAsientosDisponibles.setText("No hay partidos próximos para vender entradas.");

			btBuscarAsientos.setEnabled(false);
		}

		obtenerInfoAsientos();
	}

	/**
	 * Panel superior: Partido, tribuna y sección.
	 * 
	 * @throws SQLException
	 */
	private JPanel getPanelSuperior() throws SQLException {

		JPanel panelSeleccion = new JPanel();
		panelSeleccion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelSeleccion.setBackground(new Color(240, 240, 240));
		panelSeleccion.setLayout(new GridLayout(2, 3, 20, 20));

		panelSeleccion.add(getLbPartido());

		panelSeleccion.add(getLbSelectTribuna());

		panelSeleccion.add(getLbSelectSeccion());
		panelSeleccion.add(getCbPartido());
		panelSeleccion.add(getCbTribuna());
		panelSeleccion.add(getCbSeccion());

		return panelSeleccion;
	}

	/**
	 * Panel central: Número de entradas, buscar, cancelar y mensaje.
	 */
	private JPanel getPanelCentral() {

		JPanel panelNumAsientos = new JPanel();
		panelNumAsientos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelNumAsientos.setBackground(new Color(240, 240, 240));
		panelNumAsientos.setLayout(null);

		panelNumAsientos.add(getLbNumEntradas());
		panelNumAsientos.add(getSpNumEntradas());

		panelNumAsientos.add(getLbEmail());
		panelNumAsientos.add(getTfEmail());

		panelNumAsientos.add(getBtBuscarAsientos());
		panelNumAsientos.add(getBtCancelar());

		panelNumAsientos.add(getTfNoAsientosDisponibles());

		return panelNumAsientos;
	}

	/**
	 * Panel inferior: Asientos, precio, volver y confirmar.
	 */
	private JPanel getPanelInferior() {

		JPanel panelInformacion = new JPanel();
		panelInformacion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelInformacion.setBackground(new Color(240, 240, 240));
		panelInformacion.setLayout(new BorderLayout(0, 0));
		panelInformacion.add(getPanelBotones(), BorderLayout.EAST);
		panelInformacion.add(getPanelInfo(), BorderLayout.WEST);

		return panelInformacion;
	}

	private JLabel getLbSelectTribuna() {

		if (lbSelectTribuna == null) {
			lbSelectTribuna = new JLabel("Selecciona la tribuna:");
			lbSelectTribuna.setDisplayedMnemonic('T');
			lbSelectTribuna.setLabelFor(getCbTribuna());
		}

		return lbSelectTribuna;
	}

	private JComboBox<TipoTribuna> getCbTribuna() {

		if (cbTribuna == null) {
			cbTribuna = new JComboBox<TipoTribuna>();
			cbTribuna.setToolTipText("Selecciona la tribuna para la entrada");
			cbTribuna.setModel(new DefaultComboBoxModel<TipoTribuna>(venta.getTipoTribuna()));
			cbTribuna.addItemListener(ma);
		}

		return cbTribuna;
	}

	private JLabel getLbSelectSeccion() {

		if (lbSelectSeccion == null) {
			lbSelectSeccion = new JLabel("Selecciona la sección:");
			lbSelectSeccion.setLabelFor(getCbSeccion());
			lbSelectSeccion.setDisplayedMnemonic('S');
		}

		return lbSelectSeccion;
	}

	private JComboBox<TipoSeccion> getCbSeccion() {

		if (cbSeccion == null) {

			cbSeccion = new JComboBox<TipoSeccion>();
			cbSeccion.setModel(new DefaultComboBoxModel<TipoSeccion>(venta.getTipoSeccion()));
			cbSeccion.setToolTipText("Selecciona la sección para la entrada");
			cbSeccion.addItemListener(ma);
		}

		return cbSeccion;
	}

	private JLabel getLbNumEntradas() {

		if (lbNumEntradas == null) {

			lbNumEntradas = new JLabel("Número de asientos:");
			lbNumEntradas.setLabelFor(getSpNumEntradas());
			lbNumEntradas.setBounds(22, 50, 152, 30);
			lbNumEntradas.setDisplayedMnemonic('N');
		}

		return lbNumEntradas;
	}

	private JSpinner getSpNumEntradas() {

		if (spNumEntradas == null) {

			spNumEntradas = new JSpinner();
			spNumEntradas.setBounds(183, 57, 51, 27);
			spNumEntradas.setModel(new SpinnerNumberModel(1, 1, 15, 1));
		}

		return spNumEntradas;
	}

	private JLabel getLbPartido() throws SQLException {

		if (lbPartido == null) {
			lbPartido = new JLabel("Selecciona el partido:");
			lbPartido.setLabelFor(getCbPartido());
			lbPartido.setDisplayedMnemonic('P');
		}

		return lbPartido;
	}

	private JComboBox<Partido> getCbPartido() throws SQLException {

		if (cbPartido == null) {
			cbPartido = new JComboBox<Partido>();
			cbPartido.setModel(new DefaultComboBoxModel<Partido>(venta.getPartidos()));
			cbPartido.setToolTipText("Selecciona el partido para la entrada");
			cbPartido.addItemListener(ma);
		}

		return cbPartido;
	}

	private JButton getBtBuscarAsientos() {

		if (btBuscarAsientos == null) {
			btBuscarAsientos = new JButton("Buscar asientos");
			btBuscarAsientos.setBounds(256, 56, 130, 28);
			btBuscarAsientos.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					try {

						seleccionarAsientos();
						getTablaAsientos().repaint();

					} catch (SQLException e1) {

						JOptionPane.showMessageDialog(null,
								"Se ha producido un error con la base de datos, pruebe otra vez", "Error",
								JOptionPane.ERROR_MESSAGE);

						System.err.print("Se ha producido un error con la BBDD");
						e1.printStackTrace();

					} catch (SinDisponibilidadException sde) {

						getTfNoAsientosDisponibles().setText(sde.getMessage());
						System.err.println(sde.getMessage());
					}
				}
			});

			btBuscarAsientos.setBackground(new Color(128, 255, 128));
			btBuscarAsientos.setMnemonic('B');
		}

		return btBuscarAsientos;
	}

	private JTextField getTfNoAsientosDisponibles() {

		if (tfNoAsientosDisponibles == null) {
			tfNoAsientosDisponibles = new JTextField();
			tfNoAsientosDisponibles.setBounds(22, 182, 304, 13);
			tfNoAsientosDisponibles.setBorder(null);
			tfNoAsientosDisponibles.setBackground(new Color(240, 240, 240));
			tfNoAsientosDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 10));
			tfNoAsientosDisponibles.setForeground(Color.RED);
			tfNoAsientosDisponibles.setEditable(false);
			tfNoAsientosDisponibles.setColumns(25);
		}

		return tfNoAsientosDisponibles;
	}

	/**
	 * Se encaraga de la seleccion de los asientos
	 */
	private void seleccionarAsientos() throws SQLException, SinDisponibilidadException {

		tfNoAsientosDisponibles.setText("");

		Partido partido = (Partido) this.cbPartido.getSelectedItem();
		TipoTribuna tribuna = (TipoTribuna) this.cbTribuna.getSelectedItem();
		TipoSeccion seccion = (TipoSeccion) this.cbSeccion.getSelectedItem();
		int numEntradas = (int) this.spNumEntradas.getValue();

		List<Butaca> butacasReservadas = venta.seleccionar(partido, tribuna, seccion, numEntradas);

		mostrarAsientos(numEntradas, butacasReservadas);
	}

	/**
	 * Muestra de forma gráfica los asientos disponibles y ocupadas
	 * 
	 * @param numEntradas
	 * @param butacasReservadas
	 */
	private void mostrarAsientos(int numEntradas, List<Butaca> butacasReservadas) {

		if (!mostrarPrecio(numEntradas)) {

			reiniciar();
			return;
		}

		Butaca primera = butacasReservadas.get(0);
		Butaca ultima = butacasReservadas.get(butacasReservadas.size() - 1);

		if (butacasReservadas.size() == 1) {

			tfAsientosObtenidos.setText("Fila:" + primera.getFila() + ", asiento: " + primera.getAsiento());

		} else {

			tfAsientosObtenidos.setText("Fila:" + primera.getFila() + ", asientos de: " + primera.getAsiento() + " a "
					+ ultima.getAsiento());
		}

		this.cbPartido.setEnabled(false);
		this.cbSeccion.setEnabled(false);
		this.cbTribuna.setEnabled(false);
		this.spNumEntradas.setEnabled(false);

		this.btBuscarAsientos.setEnabled(false);
		this.btCancelar.setEnabled(true);
		this.btConfirma.setEnabled(true);
		this.btVolver.setEnabled(false);

		getTablaAsientos().repaint();
	}

	/**
	 * Mostrar el precio de las butcas compradas
	 */
	private boolean mostrarPrecio(int numEntradas) {

		Partido partido = (Partido) cbPartido.getSelectedItem();
		TipoSeccion seccion = (TipoSeccion) cbSeccion.getSelectedItem();

		try {

			double precio = venta.getPrecio(partido.getId_partido(), seccion.name());
			tfPrecio.setText(String.format("%.2f€", numEntradas * precio));

			return true;

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this, "No se ha podido obtener el precio de la entrada", "Error",
					JOptionPane.ERROR_MESSAGE);

			System.err.println("Error al obtener el precio: " + e.getMessage());

			return false;
		}

	}

	/**
	 * Permite selecionar los asientos de manera manual
	 * 
	 * @param fila
	 * @param asiento
	 */
	private void seleccionarAsientoManual(int fila, int asiento) {

		if (fila < 1 || asiento < 1 || !btBuscarAsientos.isEnabled()) {

			return;

		}

		if ((int) spNumEntradas.getValue() != 1) {

			tfNoAsientosDisponibles.setText("Para elegir el asiento en el mapa, selecciona 1 entrada.");
			return;
		}

		Partido partido = (Partido) cbPartido.getSelectedItem();
		TipoTribuna tribuna = (TipoTribuna) cbTribuna.getSelectedItem();
		TipoSeccion seccion = (TipoSeccion) cbSeccion.getSelectedItem();

		try {

			tfNoAsientosDisponibles.setText("");

			venta.seleccionarAsientoManual(partido, tribuna, seccion, fila, asiento);
			mostrarAsientos(1, venta.getButacasSeleccionadas());

		} catch (SinDisponibilidadException e) {

			JOptionPane.showMessageDialog(this, "Este asiento ya está ocupado. Seleccione otro por favor.",
					"Asiento no disponible", JOptionPane.WARNING_MESSAGE);
		}

	}

	private JButton getBtCancelar() {

		if (btCancelar == null) {

			btCancelar = new JButton("Cancelar");
			btCancelar.setBounds(256, 90, 130, 28);
			btCancelar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					reiniciar();
				}
			});

			btCancelar.setMnemonic('C');
			btCancelar.setEnabled(false);
			btCancelar.setBackground(new Color(255, 128, 128));
		}

		return btCancelar;
	}

	/**
	 * Reiniciar la interfaz gráfica para una nueva compra.
	 */
	public void reiniciar() {

		this.cbPartido.setEnabled(true);
		this.cbSeccion.setEnabled(true);
		this.cbTribuna.setEnabled(true);
		this.spNumEntradas.setEnabled(true);

		if (cbPartido.getItemCount() > 0) {

			this.cbPartido.setSelectedIndex(0);

		} else {

			tfNoAsientosDisponibles.setText("No hay partidos próximos para vender entradas.");
		}

		this.cbTribuna.setSelectedIndex(0);
		this.cbSeccion.setSelectedIndex(0);
		this.spNumEntradas.setValue(1);

		this.tfAsientosObtenidos.setText("");
		this.tfNoAsientosDisponibles.setText("");
		this.tfPrecio.setText("");
		this.tfEmail.setText("");

		this.btBuscarAsientos.setEnabled(cbPartido.getItemCount() > 0);
		this.btCancelar.setEnabled(false);
		this.btConfirma.setEnabled(false);
		this.btVolver.setEnabled(true);

		venta.reiniciar();
		obtenerInfoAsientos();
		tablaAsientos.repaint();
	}

	/**
	 * Almacena la venta y entradas en la BBDD, crea la ventana de confirmación.
	 * 
	 * @throws SQLException
	 */
	private void mostrarConfirmacionReserva() throws SQLException {

		venta.almacenar(venta.getIdPartido(), venta.getButacasSeleccionadas());

		VentanaVentaConfirmacion vvc = new VentanaVentaConfirmacion(this);

		vvc.setVisible(true);
	}

	/**
	 * Método para volver al menú principal.
	 * 
	 * @throws SQLException
	 */
	private void volverAlMenu() throws SQLException {

		this.vp.setVisible(true);
		venta.close();
		this.dispose();
	}

	private JPanel getPanelVisualizacion() {
		JPanel panelVisualizacion = new JPanel(new BorderLayout(5, 5));
		panelVisualizacion.setBackground(Color.WHITE);
		panelVisualizacion.add(getTablaAsientos(), BorderLayout.CENTER);
		panelVisualizacion.add(getPanelLeyenda(), BorderLayout.SOUTH);
		panelVisualizacion.add(getPanelTitulo(), BorderLayout.NORTH);

		return panelVisualizacion;
	}

	public JTable getTablaAsientos() {

		if (tablaAsientos == null) {

			tablaAsientos = new JTable(10, 15){
			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
			};
		
			tablaAsientos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					int fila = tablaAsientos.rowAtPoint(e.getPoint()) + 1;
					int asiento = tablaAsientos.columnAtPoint(e.getPoint()) + 1;

					seleccionarAsientoManual(fila, asiento);

				}
			});
			tablaAsientos.setBackground(new Color(240, 240, 240));
			tablaAsientos.setFocusable(false);
			tablaAsientos.setRowSelectionAllowed(false);

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

			// Renderer para cambiar el aspecto de las celdas

			tablaAsientos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {

					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					c.setBackground(new Color(128,255,128));

					try {

						if (venta.isOccupied(((Partido) getCbPartido().getSelectedItem()).getId_partido(),
								((TipoTribuna) getCbTribuna().getSelectedItem()),
								((TipoSeccion) getCbSeccion().getSelectedItem()), row + 1, column + 1)) {

							c.setBackground(new Color(255,128,128));

						} else if (venta.isBooked(((Partido) getCbPartido().getSelectedItem()).getId_partido(),
								((TipoTribuna) getCbTribuna().getSelectedItem()),
								((TipoSeccion) getCbSeccion().getSelectedItem()), row + 1, column + 1)) {

							c.setBackground(new Color(0,128,255));

						}

					} catch (Exception e) {
						c.setBackground(Color.WHITE);
					}

					c.setForeground(Color.BLACK);
					setHorizontalAlignment(CENTER);

					return c;
				}
			});

		}
	
		return tablaAsientos;

	}

	private JPanel getPanelLeyenda() {
		if (panelLeyenda == null) {
			panelLeyenda = new JPanel();
			panelLeyenda
					.setBorder(new TitledBorder(null, "Leyenda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelLeyenda.setBackground(new Color(240, 240, 240));
			panelLeyenda.setLayout(new GridLayout(3, 2, 20, 20));
			panelLeyenda.add(getBtVerde());
			panelLeyenda.add(getLbLibre());
			panelLeyenda.add(getBtRojo());
			panelLeyenda.add(getLbOcupado());
			panelLeyenda.add(getBtAzul());
			panelLeyenda.add(getLbReservado());
		}
		return panelLeyenda;
	}

	private JButton getBtVerde() {
		if (btVerde == null) {
			btVerde = new JButton("");
			btVerde.setBackground(new Color(128,255,128));
		}
		return btVerde;
	}

	private JLabel getLbLibre() {
		if (lbLibre == null) {
			lbLibre = new JLabel("Libre");
			lbLibre.setBackground(new Color(255, 255, 255));
			lbLibre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lbLibre;
	}

	private JLabel getLbOcupado() {
		if (lbOcupado == null) {
			lbOcupado = new JLabel("Ocupado");
			lbOcupado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbOcupado.setBackground(Color.WHITE);
		}
		return lbOcupado;
	}

	private JPanel getPanelTitulo() {
		if (panelTitulo == null) {
			panelTitulo = new JPanel();
			panelTitulo.setBackground(new Color(240, 240, 240));
			panelTitulo.add(getLbTitulo());
		}
		return panelTitulo;
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Visualización de asientos");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lbTitulo;
	}

	private JPanel getPanelInfo() {
		if (panelInfo == null) {
			panelInfo = new JPanel();
			panelInfo.setBackground(new Color(240, 240, 240));
			panelInfo.add(getLbAsientosReservados());
			panelInfo.add(getTfAsientosObtenidos());
			panelInfo.add(getLbPrecio());
			panelInfo.add(getTfPrecio());
		}
		return panelInfo;
	}

	private JLabel getLbAsientosReservados() {
		if (lbAsientosReservados == null) {
			lbAsientosReservados = new JLabel("Asientos:");
			lbAsientosReservados.setDisplayedMnemonic('A');
		}
		return lbAsientosReservados;
	}

	private JTextField getTfAsientosObtenidos() {
		if (tfAsientosObtenidos == null) {
			tfAsientosObtenidos = new JTextField();
			tfAsientosObtenidos.setForeground(Color.RED);
			tfAsientosObtenidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
			tfAsientosObtenidos.setEditable(false);
			tfAsientosObtenidos.setColumns(25);
			tfAsientosObtenidos.setBackground(Color.WHITE);
		}
		return tfAsientosObtenidos;
	}

	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Total:");
			lbPrecio.setDisplayedMnemonic('L');
		}
		return lbPrecio;
	}

	private JTextField getTfPrecio() {
		if (tfPrecio == null) {
			tfPrecio = new JTextField();
			tfPrecio.setForeground(Color.RED);
			tfPrecio.setEditable(false);
			tfPrecio.setColumns(8);
			tfPrecio.setBackground(Color.WHITE);
		}
		return tfPrecio;
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBackground(new Color(240, 240, 240));
			FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelBotones.add(getBtVolver());
			panelBotones.add(getBtConfirma());
		}
		return panelBotones;
	}

	private JButton getBtVolver() {
		if (btVolver == null) {
			btVolver = new JButton("Volver al menú");
			btVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {

						volverAlMenu();

					} catch (SQLException e1) {

						JOptionPane.showMessageDialog(null,
								"Se ha producido un error al cerrar la conexión con la base de datos", "Error",
								JOptionPane.ERROR_MESSAGE);
						System.err.print("Se ha producido un error al cerrar la conexión con la BBDD");
						e1.printStackTrace();
					}

				}
			});
			btVolver.setToolTipText("Pulsa para volver a la pestaña inicial");
			btVolver.setMnemonic('V');
			btVolver.setBackground(new Color(255, 128, 128));
		}
		return btVolver;
	}

	private JButton getBtConfirma() {
		if (btConfirma == null) {
			btConfirma = new JButton("Confirmar compra");
			btConfirma.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (!comprobarEmail()) {

						return;
					}

					try {

						mostrarConfirmacionReserva();

					} catch (SQLException e1) {

						JOptionPane.showMessageDialog(null, "No se ha podido completar la compra, vuelva a intentarlo",
								"Aviso", JOptionPane.WARNING_MESSAGE);

						reiniciar();
						System.err.println(
								"No se ha podido realizar la compra de las entradas por un error " + e1.getMessage());
					}

				}
			});
			btConfirma.setMnemonic('F');
			btConfirma.setEnabled(false);
			btConfirma.setBackground(new Color(128, 255, 128));
		}
		return btConfirma;
	}

	private boolean comprobarEmail() {

		String email = getTfEmail().getText().trim();

		if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {

			JOptionPane.showMessageDialog(this, "El formato del email debe ser xxxx@xx.xxx", "Email no válido",
					JOptionPane.WARNING_MESSAGE);

			getTfEmail().requestFocus();

			return false;
		}

		return true;
	}

	private JTextField getTfEmail() {

		if (tfEmail == null) {

			tfEmail = new JTextField();
			tfEmail.setBounds(183, 132, 203, 26);
			tfEmail.setColumns(20);
			tfEmail.setToolTipText("Email del cliente (xxxx@xxx.xx)");
		}

		return tfEmail;
	}

	private class CerrarVentana extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent ew) {

			try {
				venta.close();
			} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la conexion con la base de datos", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.err.println("Error al cerrar la BBDD");
				e.printStackTrace();
			}

			dispose();

		}

	}

	/**
	 * Método privado para actualizar la tabla de los asientos dependiendo si está
	 * ocupado, reservado o libre
	 */
	private void obtenerInfoAsientos() {

		Partido partido = (Partido) cbPartido.getSelectedItem();

		if (partido == null) {
			return;
		}

		TipoTribuna tribuna = (TipoTribuna) cbTribuna.getSelectedItem();
		TipoSeccion seccion = (TipoSeccion) cbSeccion.getSelectedItem();

		try {

			venta.getButcasLibres(partido, tribuna, seccion);

		} catch (SQLException e1) {

			System.err.println("Se ha producido un error con la BBDD");
			e1.printStackTrace();

		}

		tablaAsientos.repaint();

	}

	private class MostrarAsientos implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (e.getStateChange() == ItemEvent.SELECTED) {

				obtenerInfoAsientos();

			}

		}
	}

	private JLabel getLbReservado() {
		if (lbReservado == null) {
			lbReservado = new JLabel("Reservado");
			lbReservado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbReservado.setBackground(Color.WHITE);
		}
		return lbReservado;
	}

	private JButton getBtRojo() {
		if (btRojo == null) {
			btRojo = new JButton("");
			btRojo.setBackground(new Color(255,128,128));
		}
		return btRojo;
	}

	private JButton getBtAzul() {
		if (btAzul == null) {
			btAzul = new JButton("");
			btAzul.setBackground(new Color(0,128,255));
		}
		return btAzul;
	}

	private JLabel getLbEmail() {
		if (lbEmail == null) {
			lbEmail = new JLabel("Email:");
			lbEmail.setDisplayedMnemonic('E');
			lbEmail.setBounds(22, 130, 152, 30);
		}
		return lbEmail;
	}
}