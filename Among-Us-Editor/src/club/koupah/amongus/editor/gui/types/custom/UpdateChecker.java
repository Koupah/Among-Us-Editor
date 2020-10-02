package club.koupah.amongus.editor.gui.types.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager.LookAndFeelInfo;
import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.gui.GUIComponent;

public class UpdateChecker extends GUIComponent {

	LookAndFeelInfo[] allLookAndFeels;
	
	public UpdateChecker(JLabel label, JButton component) {
		super(label, component);
		
		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.getInstance().runUpdateCheck((JButton) UpdateChecker.this.component);
			}
			
		});
	}

}
