package club.koupah.aue.gui.types.impl.custom.servers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;

public class CustomServerInfo extends Setting {

	LookAndFeelInfo[] allLookAndFeels;

	JTextField ipTextField;

	public CustomServerInfo(JLabel label, final JSpinner portSpinner) {
		super(label, portSpinner);

		portSpinner.setModel(new SpinnerNumberModel(22023, 0, 65535, 1)); // 0-65535

		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(portSpinner, "#"); // Removes commas from number
		portSpinner.setEditor(editor);

		ipTextField = new JTextField("Enter IP");

		ipTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (ipTextField.getText().equals("Enter IP"))
					ipTextField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});

		ipTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
	}

	@Override
	public String getComponentValue(boolean fromLabel) {
		
		if (Editor.getInstance().serverSelector.customServer) {
			String ip = ipTextField.getText();

			if (ip.split("\\.").length == 4)
				Editor.getInstance().configManager.setCustomServerInfo(ipTextField.getText(),
						((Integer) ((JSpinner)component).getValue()).shortValue());
			else if (!ip.equals("Enter IP")) new PopUp("That isn't a valid IP Address\n(For Custom Server Info)", false);
		}

		return null;
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);

		ipTextField.setBounds(110, 20 + (index * Editor.guiSpacing), 140, 20);
		contentPane.add(ipTextField);
	}

}
