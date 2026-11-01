package giis.demo.ui.contrataciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import giis.demo.exceptions.EmployeeException;
import giis.demo.model.contrataciones.GestorEmpleados;
import giis.demo.model.empleado.BaseEmpleado;
import giis.demo.model.empleado.EmpleadoDeportivo;
import giis.demo.ui.VentanaPrincipal;

/**
 * Permite elegir un empleado existente, modificar sus datos personales y su
 * salario (no su puesto de trabajo) o eliminarlo del sistema
 */
public class VentanaGestionEmpleados extends JFrame {

	private static final long serialVersionUID = 1L;

	private GestorEmpleados gestor;

	private List<BaseEmpleado> empleados = new ArrayList<>();
	// Empleado de cada fila de la tabla (mismo orden), ya filtrado
	private List<BaseEmpleado> empleadosMostrados = new ArrayList<>();
	private BaseEmpleado empleadoSeleccionado;

	private JTextField txFiltro;
	private JTable tablaEmpleados;
	private DefaultTableModel modeloEmpleados;

	private JTextField txNombre, txApellido, txDni, txTelefono, txSalario;
	private JDateChooser dcFechaNac;
	private JLabel lbTipoValor, lbPosicionValor;
	private JButton btGuardar, btEliminar;

	public VentanaGestionEmpleados(VentanaPrincipal ventanaPrincipal, GestorEmpleados gestor) {
		this.gestor = gestor;

		setLocationRelativeTo(null);

		setTitle("Gestión de Club - Modificar y eliminar empleados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 560);
		setLocationRelativeTo(ventanaPrincipal);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				ventanaPrincipal.setVisible(true);
			}
		});

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);

		JLabel lbTitulo = new JLabel("Modificar y eliminar empleados");
		lbTitulo.setFont(new Font("Arial Black", Font.PLAIN, 22));
		contentPane.add(lbTitulo, BorderLayout.NORTH);
		contentPane.add(crearPanelListado(), BorderLayout.CENTER);
		contentPane.add(crearPanelFormulario(), BorderLayout.EAST);

		cargarEmpleados();
		habilitarFormulario(false);
	}

	// Listado

	private JPanel crearPanelListado() {
		JPanel pn = new JPanel(new BorderLayout(5, 5));
		pn.setBackground(Color.WHITE);
		pn.setBorder(BorderFactory.createTitledBorder("Empleados"));

		JPanel pnFiltro = new JPanel(new BorderLayout(5, 5));
		pnFiltro.setBackground(Color.WHITE);
		pnFiltro.add(new JLabel("Buscar (DNI, nombre o apellido):"), BorderLayout.WEST);
		txFiltro = new JTextField();
		txFiltro.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			public void removeUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			public void changedUpdate(DocumentEvent e) {
				aplicarFiltro();
			}
		});
		pnFiltro.add(txFiltro, BorderLayout.CENTER);
		pn.add(pnFiltro, BorderLayout.NORTH);

		modeloEmpleados = new DefaultTableModel(new String[] { "DNI", "Apellido", "Nombre", "Tipo", "Posición" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaEmpleados = new JTable(modeloEmpleados);
		tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				seleccionarEmpleado();
			}
		});
		pn.add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);
		return pn;
	}

	private void cargarEmpleados() {
		try {
			empleados = gestor.obtenerEmpleados();
		} catch (EmployeeException e) {
			mostrarError(e.getMessage());
			empleados = new ArrayList<>();
		}
		aplicarFiltro();
	}

	private void aplicarFiltro() {
		String filtro = txFiltro.getText().trim().toLowerCase();
		modeloEmpleados.setRowCount(0);
		empleadosMostrados.clear();

		for (BaseEmpleado emp : empleados) {
			if (filtro.isEmpty() || emp.getDni().toLowerCase().contains(filtro)
					|| emp.getNombre().toLowerCase().contains(filtro)
					|| emp.getApellido().toLowerCase().contains(filtro)) {
				empleadosMostrados.add(emp);
				modeloEmpleados.addRow(new Object[] { emp.getDni(), emp.getApellido(), emp.getNombre(), textoTipo(emp),
						VentanaAltaEmpleado.formatearPosicion(emp.getPosicion()) });
			}
		}
		// Al refiltrar se pierde la seleccion
		limpiarFormulario();
	}

	private void seleccionarEmpleado() {
		int fila = tablaEmpleados.getSelectedRow();
		if (fila == -1) {
			limpiarFormulario();
			return;
		}
		empleadoSeleccionado = empleadosMostrados.get(fila);
		rellenarFormulario(empleadoSeleccionado);
		habilitarFormulario(true);
	}

	// Formulario

	private JPanel crearPanelFormulario() {
		JPanel pn = new JPanel(new BorderLayout(5, 10));
		pn.setBackground(Color.WHITE);
		pn.setPreferredSize(new Dimension(380, 0));

		JPanel pnDatos = new JPanel(new GridLayout(2, 1, 5, 10));
		pnDatos.setBackground(Color.WHITE);
		pnDatos.add(crearPanelDatosPersonales());
		pnDatos.add(crearPanelDatosProfesionales());
		pn.add(pnDatos, BorderLayout.CENTER);
		pn.add(crearPanelBotones(), BorderLayout.SOUTH);
		return pn;
	}

	private JPanel crearPanelDatosPersonales() {
		JPanel pn = new JPanel(new GridLayout(5, 2, 5, 8));
		pn.setBackground(Color.WHITE);
		pn.setBorder(BorderFactory.createTitledBorder("Datos personales"));

		txNombre = new JTextField();
		txApellido = new JTextField();
		txDni = new JTextField();
		txTelefono = new JTextField();
		dcFechaNac = VentanaAltaEmpleado.crearSelectorFechaNacimiento();

		pn.add(new JLabel("Nombre:"));
		pn.add(txNombre);
		pn.add(new JLabel("Apellido:"));
		pn.add(txApellido);
		pn.add(new JLabel("DNI:"));
		pn.add(txDni);
		pn.add(new JLabel("Teléfono:"));
		pn.add(txTelefono);
		pn.add(new JLabel("Fecha de nacimiento:"));
		pn.add(dcFechaNac);
		return pn;
	}

	private JPanel crearPanelDatosProfesionales() {
		JPanel pn = new JPanel(new GridLayout(3, 2, 5, 8));
		pn.setBackground(Color.WHITE);
		pn.setBorder(BorderFactory.createTitledBorder("Datos profesionales"));

		txSalario = new JTextField();
		// El puesto de trabajo no se puede cambiar: se muestra solo como informacion
		lbTipoValor = new JLabel("-");
		lbPosicionValor = new JLabel("-");
		lbTipoValor.setForeground(Color.DARK_GRAY);
		lbPosicionValor.setForeground(Color.DARK_GRAY);

		pn.add(new JLabel("Salario Anual Bruto (€):"));
		pn.add(txSalario);
		pn.add(new JLabel("Sector:"));
		pn.add(lbTipoValor);
		pn.add(new JLabel("Posición (no modificable):"));
		pn.add(lbPosicionValor);
		return pn;
	}

	private JPanel crearPanelBotones() {
		JPanel pn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pn.setBackground(Color.WHITE);

		btEliminar = new JButton("Eliminar empleado");
		btEliminar.setBackground(Color.RED);
		btEliminar.setForeground(Color.WHITE);
		btEliminar.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btEliminar.addActionListener(e -> eliminarEmpleado());

		btGuardar = new JButton("Guardar cambios");
		btGuardar.setBackground(new Color(0, 128, 0));
		btGuardar.setForeground(Color.WHITE);
		btGuardar.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btGuardar.addActionListener(e -> guardarCambios());

		pn.add(btEliminar);
		pn.add(btGuardar);
		return pn;
	}

	private void rellenarFormulario(BaseEmpleado emp) {
		txNombre.setText(emp.getNombre());
		txApellido.setText(emp.getApellido());
		txDni.setText(emp.getDni());
		txTelefono.setText(emp.getNumeroTelefono());
		dcFechaNac.setDate(VentanaAltaEmpleado.aDate(emp.getFechaNacimiento()));
		txSalario.setText(emp.getSalario().toPlainString());
		lbTipoValor.setText(textoTipo(emp));
		lbPosicionValor.setText(VentanaAltaEmpleado.formatearPosicion(emp.getPosicion()));
	}

	private void limpiarFormulario() {
		empleadoSeleccionado = null;
		for (JTextField tx : new JTextField[] { txNombre, txApellido, txDni, txTelefono, txSalario }) {
			if (tx != null) {
				tx.setText("");
			}
		}
		if (dcFechaNac != null) {
			dcFechaNac.setDate(null);
		}
		if (lbTipoValor != null) {
			lbTipoValor.setText("-");
			lbPosicionValor.setText("-");
		}
		habilitarFormulario(false);
	}

	private void habilitarFormulario(boolean habilitar) {
		for (JTextField tx : new JTextField[] { txNombre, txApellido, txDni, txTelefono, txSalario }) {
			if (tx != null) {
				tx.setEnabled(habilitar);
			}
		}
		if (dcFechaNac != null) {
			dcFechaNac.setEnabled(habilitar);
		}
		if (btGuardar != null) {
			btGuardar.setEnabled(habilitar);
			btEliminar.setEnabled(habilitar);
		}
	}

	// Acciones

	private void guardarCambios() {
		if (empleadoSeleccionado == null || !validarFormulario()) {
			return;
		}
		try {
			gestor.modificarEmpleado(empleadoSeleccionado, txDni.getText().trim(), txNombre.getText().trim(),
					txApellido.getText().trim(), new BigDecimal(txSalario.getText().trim()),
					VentanaAltaEmpleado.aLocalDate(dcFechaNac.getDate()), txTelefono.getText().trim());

			JOptionPane.showMessageDialog(this, "Empleado modificado con éxito", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			recargarYSeleccionar(txDni.getText().trim());
		} catch (EmployeeException e) {
			mostrarError(e.getMessage());
		}
	}

	private void eliminarEmpleado() {
		if (empleadoSeleccionado == null) {
			return;
		}
		String mensaje = String.format(
				"¿Seguro que desea eliminar a %s %s (%s)?%n"
						+ "También se eliminarán sus horarios, franjas y entrevistas.",
				empleadoSeleccionado.getNombre(), empleadoSeleccionado.getApellido(), empleadoSeleccionado.getDni());
		if (gestor.perteneceAEquipo(empleadoSeleccionado)) {
			mensaje += "\n\nAtención: este empleado pertenece a un equipo y dejará de formar parte de él.";
		}

		int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar baja", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (opcion != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			gestor.eliminarEmpleado(empleadoSeleccionado);
			JOptionPane.showMessageDialog(this, "Empleado eliminado del sistema", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			cargarEmpleados();
		} catch (EmployeeException e) {
			mostrarError(e.getMessage());
		}
	}

	/**
	 * Recarga la tabla y vuelve a seleccionar al empleado recien modificado
	 */
	private void recargarYSeleccionar(String dni) {
		cargarEmpleados();
		for (int i = 0; i < empleadosMostrados.size(); i++) {
			if (empleadosMostrados.get(i).getDni().equals(dni)) {
				tablaEmpleados.setRowSelectionInterval(i, i);
				tablaEmpleados.scrollRectToVisible(tablaEmpleados.getCellRect(i, 0, true));
				return;
			}
		}
	}

	/**
	 * Valida solo el formato de los campos. Las reglas de negocio las valida
	 * GestorEmpleados
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
		if (!VentanaAltaEmpleado.esFechaNacimientoValida(VentanaAltaEmpleado.aLocalDate(dcFechaNac.getDate()))) {
			mostrarError("La fecha de nacimiento debe ser posterior a 1950 y no puede ser futura");
			return false;
		}

		// Validación de teléfono de 9 dígitos
		if (!txTelefono.getText().trim().matches("\\d{9}")) {
			mostrarError("El teléfono debe ser un número de 9 dígitos");
			return false;
		}

		return true;
	}

	private static String textoTipo(BaseEmpleado emp) {
		return emp instanceof EmpleadoDeportivo ? "Deportivo" : "No deportivo";
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
