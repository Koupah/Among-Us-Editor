package club.koupah.aue.gui.types.impl.custom.other;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.PopUp;

public class DiscordButton extends GUIComponent {
	
	String link;
	
	public DiscordButton(JLabel label, JButton component, String discordLink) {
		super(label, component);
		this.link = discordLink;
		
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PopUp.discordPopUp(link);
			}
		});
	}

}
