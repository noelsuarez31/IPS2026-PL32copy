package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.ui.tienda.VentanaTienda;
import giis.demo.model.contrataciones.GestorEmpleados;
import giis.demo.model.entrevistas.CrearFranjaService;
import giis.demo.model.equipo.AñadirEquipoService;
import giis.demo.model.historial.HistorialVentas;
import giis.demo.model.horarios.GestionHorariosEspecificos;
import giis.demo.model.horarios.GestionHorariosPeriodicos;
import giis.demo.model.tienda.VentaMerchandising;
import giis.demo.ui.abonos.VentanaAbono;
import giis.demo.ui.contrataciones.VentanaAltaEmpleado;
import giis.demo.ui.contrataciones.VentanaGestionEmpleados;
import giis.demo.ui.entradas.VentanaVentaEntradas;
import giis.demo.ui.equipo.VentanaAñadirEquipo;
import giis.demo.ui.historial.VentanaHistorialVentas;
import giis.demo.ui.horarios.VentanaHorarioPeriodico;
import giis.demo.ui.horarios.VentanaHorarioEspecifico;
import giis.demo.ui.reserva.VentanaReserva;
import giis.demo.model.entrevistas.CrearEntrevistaService;
import giis.demo.ui.entrevistas.VentanaCrearEntrevistas;
import giis.demo.ui.entrevistas.VentanaCrearFranjas;
import giis.demo.model.horarios.GestionHorarioSemanal;
import giis.demo.ui.horarios.VentanaHorarioSemanal;
import giis.demo.ui.mantenimiento.VentanaAsignarMantenimiento;

import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private GestionaTienda gestionaTienda = new GestionaTienda();
	private AñadirEquipoService añadirEquipo = new AñadirEquipoService();
	private GestionHorariosPeriodicos ghp = new GestionHorariosPeriodicos();
	private GestionHorariosEspecificos ghe = new GestionHorariosEspecificos();
	private GestionHorarioSemanal ghs = new GestionHorarioSemanal();
	private CrearFranjaService cfs = new CrearFranjaService();
	private CrearEntrevistaService ces = new CrearEntrevistaService();
	private GestorEmpleados gestorEmpleados = new GestorEmpleados();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnEmpleado;
	private JMenu mnEntrenador;
	private JMenu mnGerente;
	private JMenu mnVendedorDeEntradas;
	private JMenuItem mniVender;
	private JMenu mnTienda;
	private JMenuItem mntmVerTienda;
	private JMenuItem mntmAñadirEquipo;
	private JMenuItem mntmAltaEmpleado;
	private JMenuItem mntmGestionEmpleados;
	private JMenuItem mntmHistorialVentas;
	private JMenuItem mntmAñadirHorarioPeriodico;
	private JMenuItem mntmAñadirHorarioEspecifico;
	private JMenuItem mntmVisualizarHorarioSemanal;
	private JMenu mnGestorDeInstalaciones;
	private JMenuItem mntmCrearFranjas;
	private JMenu mnDirectorDeComunicaciones;
	private JMenuItem mntmAsignarEntrevistas;
	private JMenu mnAbonado;
	private JMenuItem mntmComprarAbono;
	private JMenuItem mntmAñadirMantenimiento;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setTitle("Ventana Inicial");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 420);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbInicio = new JLabel("¡Bienvenido!");
		lbInicio.setBackground(new Color(240, 240, 240));
		lbInicio.setBounds(329, 155, 90, 20);
		contentPane.add(lbInicio);

		this.setLocationRelativeTo(null);

	}

	public AñadirEquipoService getAñadirEquipoService() {
		return this.añadirEquipo;
	}

	public GestionaTienda getGestionaTienda() {
		return this.gestionaTienda;
	}

	public GestionHorariosPeriodicos getGestionHorariosPeriodicos() {
		return this.ghp;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnAbonado());
			menuBar.add(getMnDirectorDeComunicaciones());
			menuBar.add(getMnEmpleado());
			menuBar.add(getMnEntrenador());
			menuBar.add(getMnGerente());
			menuBar.add(getMnGestorDeInstalaciones());
			menuBar.add(getMnTienda());
			menuBar.add(getMnVendedorDeEntradas());
		}
		return menuBar;
	}

	private JMenu getMnEmpleado() {
		if (mnEmpleado == null) {
			mnEmpleado = new JMenu("Empleado");
			mnEmpleado.setMnemonic('P');
			mnEmpleado.add(getMntmVisualizarHorarioSemanal());
		}
		return mnEmpleado;
	}

	private JMenu getMnEntrenador() {
		if (mnEntrenador == null) {
			mnEntrenador = new JMenu("Entrenador");
			mnEntrenador.setMnemonic('T');
			mnEntrenador.add(getMntmCrearFranjas());
		}
		return mnEntrenador;
	}

	private JMenu getMnGerente() {
		if (mnGerente == null) {
			mnGerente = new JMenu("Gerente");
			mnGerente.setMnemonic('G');
			mnGerente.add(getMntmAñadirEquipo());
			mnGerente.add(getMntmAñadirHorarioPeriodico());
			mnGerente.add(getMntmAñadirHorarioEspecifico());
			mnGerente.add(getMntmAltaEmpleado());
			mnGerente.add(getMntmGestionEmpleados());
			mnGerente.add(getMntmHistorialVentas());
			mnGerente.add(getMntmAñadirMantenimiento());
		}
		return mnGerente;
	}

	private JMenuItem getMntmHistorialVentas() {
		if (mntmHistorialVentas == null) {
			mntmHistorialVentas = new JMenuItem("Historial de ventas");
			mntmHistorialVentas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaHistorialVentas();
				}
			});
		}
		return mntmHistorialVentas;
	}

	private void abrirVentanaHistorialVentas() {
		VentanaHistorialVentas ventana = new VentanaHistorialVentas(this, new HistorialVentas());
		this.setVisible(false);
		ventana.setVisible(true);
	}

	private JMenuItem getMntmAltaEmpleado() {
		if (mntmAltaEmpleado == null) {
			mntmAltaEmpleado = new JMenuItem("Alta de empleado");
			mntmAltaEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAltaEmpleado();
				}
			});
		}
		return mntmAltaEmpleado;
	}

	private void abrirVentanaAltaEmpleado() {
		VentanaAltaEmpleado ventana = new VentanaAltaEmpleado(gestorEmpleados);
		this.setVisible(false);
		ventana.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				VentanaPrincipal.this.setVisible(true);
			}
		});
		ventana.setVisible(true);
	}

	private JMenuItem getMntmGestionEmpleados() {
		if (mntmGestionEmpleados == null) {
			mntmGestionEmpleados = new JMenuItem("Modificar / eliminar empleados");
			mntmGestionEmpleados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaGestionEmpleados();
				}
			});
		}
		return mntmGestionEmpleados;
	}

	private void abrirVentanaGestionEmpleados() {
		VentanaGestionEmpleados ventana = new VentanaGestionEmpleados(this, gestorEmpleados);
		this.setVisible(false);
		ventana.setVisible(true);
	}

	private JMenu getMnVendedorDeEntradas() {
		if (mnVendedorDeEntradas == null) {
			mnVendedorDeEntradas = new JMenu("Vendedor de Entradas");
			mnVendedorDeEntradas.setMnemonic('V');
			mnVendedorDeEntradas.add(getMniVender());
		}
		return mnVendedorDeEntradas;
	}

	private JMenuItem getMniVender() {
		if (mniVender == null) {
			mniVender = new JMenuItem("Venta de entradas");
			mniVender.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						abrirVentanaVentaEntradas();
					} catch (SQLException e1) {

						System.err.print("Se ha producido un error al intentar abrir la ventana de ventas");
						e1.printStackTrace();
					}

				}
			});
		}
		return mniVender;
	}

	/**
	 * Abre la ventana para vender las entradas
	 * 
	 * @throws SQLException
	 */
	private void abrirVentanaVentaEntradas() throws SQLException {

		VentanaVentaEntradas vve = new VentanaVentaEntradas(this);
		this.setVisible(false);
		vve.setVisible(true);

	}

	private JMenu getMnTienda() {
		if (mnTienda == null) {
			mnTienda = new JMenu("Tienda");
			mnTienda.setMnemonic('T');
			mnTienda.add(getMntmVerTienda());
		}
		return mnTienda;
	}

	private class GestionaTienda implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			abrirVentanaTienda();
		}

	}

	public void abrirVentanaTienda() {
		try {
			// Instanciar lógica
			VentaMerchandising ventaActiva = new VentaMerchandising();

			// Cargar BBDD a memoria
			ventaActiva.cargarCatalogo();

			// Pasarle el controlador
			VentanaTienda vT = new VentanaTienda(this, ventaActiva);
			this.setVisible(false);
			vT.setVisible(true);

		} catch (SQLException ex) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Error al conectar con la base de datos para cargar el catálogo:\n" + ex.getMessage(),
					"Error Crítico", javax.swing.JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private JMenuItem getMntmVerTienda() {
		if (mntmVerTienda == null) {
			mntmVerTienda = new JMenuItem("Ver Tienda");
			mntmVerTienda.addActionListener(gestionaTienda);
		}
		return mntmVerTienda;
	}

	private JMenuItem getMntmAñadirEquipo() {
		if (mntmAñadirEquipo == null) {
			mntmAñadirEquipo = new JMenuItem("Añadir equipo");
			mntmAñadirEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAñadirEquipo();
				}
			});
		}
		return mntmAñadirEquipo;
	}

	private void abrirVentanaAñadirEquipo() {
		VentanaAñadirEquipo vAñadirEquipo = new VentanaAñadirEquipo(this);
		this.setVisible(false);
		vAñadirEquipo.setVisible(true);

	}

	private JMenuItem getMntmAñadirHorarioPeriodico() {
		if (mntmAñadirHorarioPeriodico == null) {
			mntmAñadirHorarioPeriodico = new JMenuItem("Añadir horario periódico");
			mntmAñadirHorarioPeriodico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAñadirHorarioPeriodico();
				}
			});
		}
		return mntmAñadirHorarioPeriodico;
	}

	private void abrirVentanaAñadirHorarioPeriodico() {
		VentanaHorarioPeriodico vHorarioPeriodico = new VentanaHorarioPeriodico(this);
		this.setVisible(false);
		vHorarioPeriodico.setVisible(true);

	}

	private JMenuItem getMntmAñadirHorarioEspecifico() {
		if (mntmAñadirHorarioEspecifico == null) {
			mntmAñadirHorarioEspecifico = new JMenuItem("Añadir horario específico");
			mntmAñadirHorarioEspecifico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAñadirHorarioEspecifico();
				}
			});
		}
		return mntmAñadirHorarioEspecifico;
	}

	private void abrirVentanaAñadirHorarioEspecifico() {
		VentanaHorarioEspecifico vHorarioEspecifico = new VentanaHorarioEspecifico(this);
		this.setVisible(false);
		vHorarioEspecifico.setVisible(true);
	}

	public GestionHorariosEspecificos getGestionHorariosEspecificos() {

		return this.ghe;
	}

	private JMenu getMnGestorDeInstalaciones() {
		if (mnGestorDeInstalaciones == null) {
			mnGestorDeInstalaciones = new JMenu("Gestor de Instalaciones");
			mnGestorDeInstalaciones.setMnemonic('S');

			JMenuItem mntmReservaDeInstalaciones = new JMenuItem("Reserva de Instalaciones");
			mntmReservaDeInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						abrirVentanaReserva();
					} catch (SQLException e1) {

						System.err.print("Se ha producido un error al intentar abrir la ventana de reserva");
						e1.printStackTrace();
					}
				}
			});
			mnGestorDeInstalaciones.add(mntmReservaDeInstalaciones);
		}
		return mnGestorDeInstalaciones;
	}

	private JMenuItem getMntmCrearFranjas() {
		if (mntmCrearFranjas == null) {
			mntmCrearFranjas = new JMenuItem("Crear franjas");
			mntmCrearFranjas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaCrearFranjas();
				}
			});
		}
		return mntmCrearFranjas;
	}

	private void abrirVentanaCrearFranjas() {
		VentanaCrearFranjas vFranjas = new VentanaCrearFranjas(this);
		this.setVisible(false);
		vFranjas.setVisible(true);
	}

	public CrearFranjaService getCrearFranjaService() {
		return this.cfs;
	}

	/**
	 * Abre la ventana para realizar la ventana de la reserva
	 * 
	 * @throws SQLException
	 */
	private void abrirVentanaReserva() throws SQLException {

		VentanaReserva vr = new VentanaReserva(this);
		this.setVisible(false);
		vr.setVisible(true);
	}

	private JMenu getMnDirectorDeComunicaciones() {
		if (mnDirectorDeComunicaciones == null) {
			mnDirectorDeComunicaciones = new JMenu("Director de comunicaciones");
			mnDirectorDeComunicaciones.add(getMntmAsignarEntrevistas());
		}
		return mnDirectorDeComunicaciones;
	}

	private JMenuItem getMntmAsignarEntrevistas() {
		if (mntmAsignarEntrevistas == null) {
			mntmAsignarEntrevistas = new JMenuItem("Asignar entrevistas");
			mntmAsignarEntrevistas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaCrearEntrevistas();
				}
			});
		}
		return mntmAsignarEntrevistas;
	}

	private void abrirVentanaCrearEntrevistas() {
		VentanaCrearEntrevistas vCrearEntrev = new VentanaCrearEntrevistas(this);
		this.setVisible(false);
		vCrearEntrev.setVisible(true);
	}

	public CrearEntrevistaService getCrearEntrevistaService() {
		return this.ces;
	}

	private JMenuItem getMntmVisualizarHorarioSemanal() {
		if (mntmVisualizarHorarioSemanal == null) {
			mntmVisualizarHorarioSemanal = new JMenuItem("Consultar horarios semanales");
			mntmVisualizarHorarioSemanal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaHorarioSemanal();
				}
			});
		}
		return mntmVisualizarHorarioSemanal;
	}

	private void abrirVentanaHorarioSemanal() {
		VentanaHorarioSemanal vHorarioSemanal = new VentanaHorarioSemanal(this);
		this.setVisible(false);
		vHorarioSemanal.setVisible(true);
	}

	public GestionHorarioSemanal getGestionHorarioSemanal() {
		return this.ghs;
	}
	private JMenu getMnAbonado() {
		if (mnAbonado == null) {
			mnAbonado = new JMenu("Abonado");
			mnAbonado.setMnemonic('B');
			mnAbonado.add(getMntmComprarAbono());
		}
		return mnAbonado;
	}
	private JMenuItem getMntmComprarAbono() {
		if (mntmComprarAbono == null) {
			mntmComprarAbono = new JMenuItem("Comprar abono");
			mntmComprarAbono.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					abrirVentaAbono();
					
				}
			});
		}
		return mntmComprarAbono;
	}
	
	private void abrirVentaAbono() {
		
		
		VentanaAbono va = new VentanaAbono(this);
		this.setVisible(false);
		va.setVisible(true);
	}
	private JMenuItem getMntmAñadirMantenimiento() {
		if (mntmAñadirMantenimiento == null) {
			mntmAñadirMantenimiento = new JMenuItem("Añadir servicio de mantenimiento");
			mntmAñadirMantenimiento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAñadirMantenimiento();
				}

				
			});
		}
		return mntmAñadirMantenimiento;
	}
	
	private void abrirVentanaAñadirMantenimiento() {
		VentanaAsignarMantenimiento vAsignarMantenimiento = new VentanaAsignarMantenimiento(this);
		this.setVisible(false);
		vAsignarMantenimiento.setVisible(true);
		
	}
}
