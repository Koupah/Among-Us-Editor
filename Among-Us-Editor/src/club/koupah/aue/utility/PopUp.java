package club.koupah.aue.utility;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import club.koupah.AUEditorMain;
import club.koupah.aue.Editor;

public class PopUp {

	public PopUp(String message) {
		new PopUp(message, true);
	}

	public PopUp(String message, boolean exit) {
		JFrame jf = new JFrame();

		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, message, Editor.title,
				exit ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE);

		if (exit)
			System.exit(0);
	}

	public static void downloadPopUp(String message, String version) {

		JFrame jf = new JFrame();

		jf.setAlwaysOnTop(true);

		Object[] options = { "Download", "Continue" };

		int userChoice = JOptionPane.showOptionDialog(jf,
				message,
				"Update Available!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null, // No icon
				options,
				options[0]);

		switch (userChoice) {
		case JOptionPane.YES_OPTION: {
			if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop()
							.browse(new URI("https://github.com/Koupah/Among-Us-Editor/releases/tag/" + version));
				} catch (IOException | URISyntaxException e) {
					// If opening the browser doesn't want to see to work, show this popup
					showGitHubInput(version);
				}
			} else {
				// I'm assuming this is what will show up for non windows users.
				// I should really load a linux distro on my other PC to test
				showGitHubInput(version);
			}
			break;
		}

		// Continue on old version
		case JOptionPane.CLOSED_OPTION: {
		}
		case JOptionPane.NO_OPTION: {
			System.out
					.println("User chose not to update to version " + version + ". Current Version is " + Editor.version);
			break;
		}
		}

	}

	// Here I just show a link, I already learnt my lesson about supporting linux
	// And apparently copying to the clipboard in linux is iffy sooo
	static void showGitHubInput(String version) {
		Object[] options = { "OK" };
		String download = "https://github.com/Koupah/Among-Us-Editor/releases/tag/" + version;

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField text = new JTextField(download);
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setEditable(false);
		panel.add(new JLabel(
				"You can download version " + version + " by copying the link below and opening it in your browser!"));
		panel.add(text);
		JOptionPane.showOptionDialog(null, panel, "Download Update",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, null);
	}

	public static void discordPopUp() {

		if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop()
						.browse(new URI(AUEditorMain.discordLink));
			} catch (IOException | URISyntaxException e) {
				// If opening the browser doesn't want to see to work, show this popup
				showDiscordLink(AUEditorMain.discordLink);
			}
		} else {
			// I'm assuming this is what will show up for non windows users.
			// I should really load a linux distro on my other PC to test
			showDiscordLink(AUEditorMain.discordLink);
		}

	}

	static void showDiscordLink(String link) {
		Object[] options = { "Done!" };

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField discord = new JTextField(link);
		discord.setHorizontalAlignment(JTextField.CENTER);
		discord.setEditable(false);
		panel.add(new JLabel("Join the discord by copying the below link and putting it into your browser!"));
		panel.add(discord);
		
		JOptionPane.showOptionDialog(null, panel, "Download Update",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, null);

	}

}
