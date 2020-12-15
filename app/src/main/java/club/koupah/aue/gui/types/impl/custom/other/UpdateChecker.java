package club.koupah.aue.gui.types.impl.custom.other;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.Utility;

public class UpdateChecker extends GUIComponent {

	LookAndFeelInfo[] allLookAndFeels;
	
	
	public UpdateChecker(JLabel label, JButton component) {
		super(label, component);
		label.setText(this.labelText + Editor.version);
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Utility.runUpdateCheck((JButton) UpdateChecker.this.component);
			}
		});
	}
	
	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);	
	}
	
}
