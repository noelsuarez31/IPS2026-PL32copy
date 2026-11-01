package giis.demo.ui.equipo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Diálogo modal para fijar el suplemento de abonados en Día del Club.
 * Implementado con patrón Lazy Generation.
 */
public class DialogoDiaDelClub extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel pnAviso;
	private JLabel lbAviso;
	private JPanel pnCentral;
	private JLabel lbPrecio;
	private JSpinner spPrecioAbonado;
	private JPanel pnBotones;
	private JButton btnCancelar;
	private JButton btnAceptar;

	private boolean aceptado = false;
	private double precioAbonado = 15.0;

	public DialogoDiaDelClub(Frame parent, double precioInicial) {
		super(parent, "Configurar Día del Club", true);
		this.precioAbonado = (precioInicial > 0) ? precioInicial : 15.0;

		setTitle("Configurar Día del Club");
		setResizable(false);
		setBounds(100, 100, 430, 240);
		setLocationRelativeTo(parent);

		setContentPane(getContentPane());
	}

	@Override
	public JPanel getContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel(new BorderLayout(10, 10));
			contentPane.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
			contentPane.add(getPnAviso(), BorderLayout.NORTH);
			contentPane.add(getPnCentral(), BorderLayout.CENTER);
			contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		}
		return contentPane;
	}

	private JPanel getPnAviso() {
		if (pnAviso == null) {
			pnAviso = new JPanel(new BorderLayout(5, 5));
			pnAviso.setBackground(new Color(239, 246, 255));
			pnAviso.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(new Color(191, 219, 254), 1),
					BorderFactory.createEmptyBorder(8, 8, 8, 8)));
			pnAviso.add(getLbAviso(), BorderLayout.CENTER);
		}
		return pnAviso;
	}

	private JLabel getLbAviso() {
		if (lbAviso == null) {
			lbAviso = new JLabel("<html><b>ℹ️ Día del Club:</b> Los abonados deberán abonar este suplemento para acceder o retirar su asiento habitual.</html>");
			lbAviso.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbAviso.setForeground(new Color(30, 58, 138));
		}
		return lbAviso;
	}

	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
			pnCentral.add(getLbPrecio());
			pnCentral.add(getSpPrecioAbonado());
		}
		return pnCentral;
	}

	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio / Suplemento por Asiento (€):");
			lbPrecio.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lbPrecio;
	}

	public JSpinner getSpPrecioAbonado() {
		if (spPrecioAbonado == null) {
			spPrecioAbonado = new JSpinner(new SpinnerNumberModel(precioAbonado, 0.01, 9999.00, 0.50));
			spPrecioAbonado.setFont(new Font("Tahoma", Font.BOLD, 13));
			JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spPrecioAbonado, "0.00 €");
			spPrecioAbonado.setEditor(editor);
		}
		return spPrecioAbonado;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
			pnBotones.add(getBtnCancelar());
			pnBotones.add(getBtnAceptar());
		}
		return pnBotones;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(e -> {
				aceptado = false;
				dispose();
			});
		}
		return btnCancelar;
	}

	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnAceptar.addActionListener(e -> {
				double val = ((Number) getSpPrecioAbonado().getValue()).doubleValue();
				if (val <= 0) {
					JOptionPane.showMessageDialog(this, "El suplemento para abonados debe ser mayor a 0 €.",
							"Precio no válido", JOptionPane.WARNING_MESSAGE);
					return;
				}
				precioAbonado = val;
				aceptado = true;
				dispose();
			});
		}
		return btnAceptar;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public double getPrecioAbonado() {
		return precioAbonado;
	}
}