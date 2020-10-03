package club.koupah.aue.gui.types.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIComponent;

public class UpdateChecker extends GUIComponent {

	LookAndFeelInfo[] allLookAndFeels;
	
	public UpdateChecker(JLabel label, JButton component) {
		super(label, component);
		label.setText(this.labelText + Editor.version);
		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.getInstance().runUpdateCheck((JButton) UpdateChecker.this.component);
			}
			
		});
	}

}
