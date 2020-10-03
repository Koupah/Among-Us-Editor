package club.koupah.aue.gui.types.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.gui.GUIComponent;
import club.koupah.aue.utility.PopUp;

public class DiscordButton extends GUIComponent {

	public DiscordButton(JLabel label, JButton component) {
		super(label, component);

		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PopUp.discordPopUp();
			}
			
		});
	}

}
