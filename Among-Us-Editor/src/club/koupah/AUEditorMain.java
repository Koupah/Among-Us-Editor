package club.koupah;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.gui.settings.cosmetics.Colors;
import club.koupah.aue.gui.settings.cosmetics.Hats;
import club.koupah.aue.gui.settings.cosmetics.Pets;
import club.koupah.aue.gui.settings.cosmetics.Skins;
import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.Utility;

public class AUEditorMain {

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	public static String desiredLookAndFeel = "WindowsLookAndFeel";

	static double version = 1.48;
	
	public static String title = "Among Us Editor";
	
	public static String discordLink = "https://www.koupah.club/aueditor";

	public static void main(String[] args) {

		try {
			// Idk how to get them to initialize their values cause am big noob
			Hats.values();
			Pets.values();
			Skins.values();
			Colors.values();
			GUIScheme.values();
			SettingType.values();

			// Local variable, I'm not going to use it again from outside this class
			final Editor editor = new Editor(version);

			// Default look and feel, before checking config
			String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

			// Run this first, before me do any GUI stuff
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getClassName().contains(desiredLookAndFeel)) {
					lookAndFeel = info.getClassName();
					editor.windowsOS = true;
					break;
				}
			}

			try {
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				new PopUp("Failed to apply a look and feel!", false);
			}

			// Because JPanels in tabbed panels are fucked and don't let me change the
			// background color,
			// Set the panels to not be opaque that way we use the original background color
			// UIManager.put("TabbedPane.contentOpaque", false);

			// Setup the config and other stuff
			editor.setupManagers();

			// Set the L&F to the one we set above if none specified in config
			if (editor.configManager.getLookAndFeel() == null)
				editor.configManager.setLookAndFeel(lookAndFeel);

			// Finish setting the window up
			editor.setupWindow();

			// Show the frame! Where the magic happens
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {

					editor.setVisible(true);

				}
			});

			// Run update check, this is a seperate thread so it won't interrupt the main
			// thread
			Utility.runUpdateCheck(null);

			// Catch any exception that, for whatever reason wasn't already caught
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Fatal error, send this to Koupah#5129 on discord!\nMessage: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
