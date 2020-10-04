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

	static double version = 1.482;

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
			StackTraceElement cause = e.getStackTrace()[0];
			System.out.println(cause.getClass());
			
			StackTraceElement origin = null;
			for (StackTraceElement ste : e.getStackTrace()) {
				if (ste.getClassName().contains("koupah")) {
					origin = ste;
					break;
				}
			}
			
			JOptionPane.showMessageDialog(null,
					"Fatal error #0001, send this to Koupah#5129 on discord or open an issue on the GitHub with the below!\nVersion: " + version + "\nMessage: " + e.getMessage()
							+ "\nException Type: " + cause.getClassName() + "\nMethod: " + cause.getMethodName() + (origin == null ? "" : "\nOrigin Class: " + origin.getClassName() + "\nOrigin Method: " + origin.getMethodName() + "\nOrigin Line: " + origin.getLineNumber()) + "\n\nNote: There is a chance you can fix this error yourself by deleting your AUEConfig file\nand by also deleting your playerPrefs file (If you know where it is)");
			
			e.printStackTrace();
		}
	}

}
