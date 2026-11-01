package giis.demo.ui.contrataciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import giis.demo.exceptions.EmployeeException;
import giis.demo.model.contrataciones.GestorEmpleados;
import giis.demo.model.empleado.Posicion;

public class VentanaAltaEmpleado extends JFrame {
	private static final long serialVersionUID = 1L;
	static final LocalDate FECHA_NACIMIENTO_MINIMA = LocalDate.of(1951, 1, 1);
	private JPanel contentPane;
	private JPanel pnNorte;
	private JLabel lblTitulo;
	private JPanel pnCentro;
	private JPanel pnDatosPersonales;
	private JPanel pnDatosProfesionales;
	private JPanel pnSur;
	private JButton btGuardar;
	private JButton btCancelar;

	// Componentes Datos Personales
	private JLabel lbNombre, lbApellido, lbDni, lbTelefono, lbFechaNac;
	private JTextField txNombre, txApellido, txDni, txTelefono;
	private JDateChooser dcFechaNac;

	// Componentes Datos Profesionales
	private JLabel lbSalario, lbTipo, lbPosicion;
	private JTextField txSalario;
	private JRadioButton rbDeportivo, rbNoDeportivo;
	private final ButtonGroup grTipoEmpleado = new ButtonGroup();
	private JComboBox<Posicion> cbPosicion;

	private GestorEmpleados gestor;

	public VentanaAltaEmpleado(GestorEmpleados gestor) {
		this.gestor = gestor;
		setTitle("Gestión de Club - Alta de Empleados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(getPnNorte(), BorderLayout.NORTH);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);

		actualizarModeloPosicion();
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBackground(Color.WHITE);
			lblTitulo = new JLabel("Alta de Nuevo Empleado");
			lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 24));
			pnNorte.add(lblTitulo);
		}
		return pnNorte;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new GridLayout(2, 1, 10, 10));
			pnCentro.add(getPnDatosPersonales());
			pnCentro.add(getPnDatosProfesionales());
		}
		return pnCentro;
	}

	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14), new Color(51, 51, 51)));
			pnDatosPersonales.setBackground(Color.WHITE);
			pnDatosPersonales.setLayout(new GridLayout(5, 2, 5, 10));

			lbNombre = new JLabel("Nombre:");
			txNombre = new JTextField();
			lbApellido = new JLabel("Apellido:");
			txApellido = new JTextField();
			lbDni = new JLabel("DNI:");
			txDni = new JTextField();
			lbTelefono = new JLabel("Teléfono:");
			txTelefono = new JTextField();
			lbFechaNac = new JLabel("Fecha de nacimiento:");
			dcFechaNac = crearSelectorFechaNacimiento();

			pnDatosPersonales.add(lbNombre);
			pnDatosPersonales.add(txNombre);
			pnDatosPersonales.add(lbApellido);
			pnDatosPersonales.add(txApellido);
			pnDatosPersonales.add(lbDni);
			pnDatosPersonales.add(txDni);
			pnDatosPersonales.add(lbTelefono);
			pnDatosPersonales.add(txTelefono);
			pnDatosPersonales.add(lbFechaNac);
			pnDatosPersonales.add(dcFechaNac);
		}
		return pnDatosPersonales;
	}

	private JPanel getPnDatosProfesionales() {
		if (pnDatosProfesionales == null) {
			pnDatosProfesionales = new JPanel();
			pnDatosProfesionales
					.setBorder(new TitledBorder(null, "Datos Profesionales", TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14), new Color(51, 51, 51)));
			pnDatosProfesionales.setBackground(Color.WHITE);
			pnDatosProfesionales.setLayout(new GridLayout(4, 2, 5, 10));

			lbSalario = new JLabel("Salario Anual Bruto (€):");
			txSalario = new JTextField();

			lbTipo = new JLabel("Sector:");
			JPanel pnRadios = new JPanel(new GridLayout(1, 2));
			pnRadios.setBackground(Color.WHITE);
			pnRadios.add(getRbDeportivo());
			pnRadios.add(getRbNoDeportivo());

			lbPosicion = new JLabel("Posición:");

			pnDatosProfesionales.add(lbSalario);
			pnDatosProfesionales.add(txSalario);
			pnDatosProfesionales.add(lbTipo);
			pnDatosProfesionales.add(pnRadios);
			pnDatosProfesionales.add(lbPosicion);
			pnDatosProfesionales.add(getCbPosicion());
		}
		return pnDatosProfesionales;
	}

	private JRadioButton getRbDeportivo() {
		if (rbDeportivo == null) {
			rbDeportivo = new JRadioButton("Deportivo");
			rbDeportivo.setBackground(Color.WHITE);
			rbDeportivo.setSelected(true); // Por defecto
			grTipoEmpleado.add(rbDeportivo);
			rbDeportivo.addActionListener(e -> actualizarModeloPosicion());
		}
		return rbDeportivo;
	}

	private JRadioButton getRbNoDeportivo() {
		if (rbNoDeportivo == null) {
			rbNoDeportivo = new JRadioButton("No Deportivo");
			rbNoDeportivo.setBackground(Color.WHITE);
			grTipoEmpleado.add(rbNoDeportivo);
			rbNoDeportivo.addActionListener(e -> actualizarModeloPosicion());
		}
		return rbNoDeportivo;
	}

	private JComboBox<Posicion> getCbPosicion() {
		if (cbPosicion == null) {
			cbPosicion = new JComboBox<Posicion>();
			cbPosicion.setFont(new Font("Arial", Font.PLAIN, 14));
			cbPosicion.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, formatearPosicion(value), index, isSelected,
							cellHasFocus);
				}
			});
		}
		return cbPosicion;
	}

	static String formatearPosicion(Object posicion) {
		if (posicion == null) {
			return "";
		}
		String texto = posicion.toString().replace('_', ' ').toLowerCase();
		return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
	}

	private void actualizarModeloPosicion() {
		DefaultComboBoxModel<Posicion> modelo = new DefaultComboBoxModel<>();
		if (getRbDeportivo().isSelected()) {
			modelo.addElement(Posicion.JUGADOR);
			modelo.addElement(Posicion.ENTRENADOR);
			modelo.addElement(Posicion.TECNICO_ADICIONAL);
		} else {
			modelo.addElement(Posicion.GERENTE);
			modelo.addElement(Posicion.VENDEDOR_DE_ENTRADAS);
			modelo.addElement(Posicion.ENCARGADO_DE_TIENDA);
			modelo.addElement(Posicion.GESTOR_DE_INSTALACIONES);
			modelo.addElement(Posicion.EMPLEADO_DE_TIENDA);
			modelo.addElement(Posicion.JARDINERIA);
			modelo.addElement(Posicion.COCINA);
			modelo.addElement(Posicion.DIRECTOR_DE_COMUNICACIONES);
		}
		getCbPosicion().setModel(modelo);
	}

	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(Color.WHITE);
			pnSur.add(getBtCancelar());
			pnSur.add(getBtGuardar());
		}
		return pnSur;
	}

	private JButton getBtGuardar() {
		if (btGuardar == null) {
			btGuardar = new JButton("Guardar Empleado");
			btGuardar.setBackground(new Color(0, 128, 0));
			btGuardar.setForeground(Color.WHITE);
			btGuardar.setFont(new Font("Arial Black", Font.PLAIN, 14));
			btGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (validarFormulario()) {
						crearYGuardarEmpleado();
					}
				}
			});
		}
		return btGuardar;
	}

	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setBackground(Color.RED);
			btCancelar.setForeground(Color.WHITE);
			btCancelar.setFont(new Font("Arial Black", Font.PLAIN, 14));
			btCancelar.addActionListener(e -> dispose());
		}
		return btCancelar;
	}

	/**
	 * Valida solo el formato de los campos. Las reglas de negocio (menores de edad,
	 * dni/telefono duplicados...) las valida GestorEmpleados
	 */
	private boolean validarFormulario() {
		if (txNombre.getText().isBlank() || txApellido.getText().isBlank() || txDni.getText().isBlank()
				|| txTelefono.getText().isBlank() || dcFechaNac.getDate() == null || txSalario.getText().isBlank()) {
			mostrarError("Todos los campos son obligatorios");
			return false;
		}

		try {
			if (new BigDecimal(txSalario.getText().trim()).compareTo(BigDecimal.ZERO) <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			mostrarError("El salario debe ser un número mayor que cero");
			return false;
		}

		// El calendario ya limita el rango, pero la fecha tambien se puede escribir a
		// mano
		if (!esFechaNacimientoValida(aLocalDate(dcFechaNac.getDate()))) {
			mostrarError("La fecha de nacimiento debe ser posterior a 1950 y no puede ser futura");
			return false;
		}

		// Validación de que el teléfono sean exactamente 9 dígitos numéricos
		if (!txTelefono.getText().trim().matches("\\d{9}")) {
			mostrarError("El teléfono debe ser un número de 9 dígitos");
			return false;
		}

		return true;
	}

	private void crearYGuardarEmpleado() {
		try {
			LocalDate fechaNac = aLocalDate(dcFechaNac.getDate());
			BigDecimal salario = new BigDecimal(txSalario.getText().trim());
			boolean esDeportivo = getRbDeportivo().isSelected();
			Posicion posicion = (Posicion) getCbPosicion().getSelectedItem();

			gestor.registrarNuevoEmpleado(txDni.getText().trim(), txNombre.getText().trim(),
					txApellido.getText().trim(), salario, fechaNac, txTelefono.getText().trim(), esDeportivo, posicion);

			JOptionPane.showMessageDialog(this, "Empleado registrado con éxito", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarTextos();
		} catch (EmployeeException e) {
			mostrarError(e.getMessage());
		}
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void limpiarTextos() {
		txNombre.setText("");
		txApellido.setText("");
		txDni.setText("");
		txTelefono.setText("");
		dcFechaNac.setDate(null);
		txSalario.setText("");
		getRbDeportivo().setSelected(true);
		actualizarModeloPosicion();
	}

	// Utilidades de fecha compartidas con VentanaGestionEmpleados

	/**
	 * Selector de fecha (como el del historial de ventas) limitado a fechas de
	 * nacimiento validas: desde 1951 hasta hoy
	 */
	static JDateChooser crearSelectorFechaNacimiento() {
		JDateChooser selector = new JDateChooser();
		selector.setDateFormatString("dd/MM/yyyy");
		selector.setMinSelectableDate(aDate(FECHA_NACIMIENTO_MINIMA));
		selector.setMaxSelectableDate(aDate(LocalDate.now()));
		return selector;
	}

	static boolean esFechaNacimientoValida(LocalDate fecha) {
		return fecha != null && !fecha.isBefore(FECHA_NACIMIENTO_MINIMA) && !fecha.isAfter(LocalDate.now());
	}

	static LocalDate aLocalDate(Date fecha) {
		return fecha == null ? null : fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	static Date aDate(LocalDate fecha) {
		return fecha == null ? null : Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
