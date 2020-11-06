package club.koupah;

import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.gui.types.impl.CheckboxSetting;
import club.koupah.aue.gui.types.impl.custom.hostsettings.HostSetting;
import club.koupah.aue.gui.types.impl.custom.playerstats.PlayerStat;
import club.koupah.aue.gui.values.GUIScheme;
import club.koupah.aue.gui.values.cosmetics.Colors;
import club.koupah.aue.gui.values.cosmetics.Hats;
import club.koupah.aue.gui.values.cosmetics.Pets;
import club.koupah.aue.gui.values.cosmetics.Skins;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.Utility;
import club.koupah.aue.utility.config.ConfigType;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;

public class AUEditorMain {

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	public static String desiredLookAndFeel = "WindowsLookAndFeel";

	public static double version = 1.56;

	public static String title = "Among Us Editor";

	public static String discordLink = "https://www.koupah.club/aueditor";

	public static HashMap<String, String> warnings = new HashMap<String, String>();

	private static DiscordRPC lib = DiscordRPC.INSTANCE;

	private static String AUEAppID = "772680842749018114";

	public static DiscordRichPresence presence;

	public static DiscordUser discordUser;

	public static boolean usingRichPresence = false;

	public static void main(String[] args) {

		System.out.println(String.format("Starting up %s version %s", title, version));

		try {

			// Idk how to get them to initialize their values cause am big noob
			Hats.values();
			Pets.values();
			Skins.values();
			Colors.values();
			GUIScheme.values();
			SettingType.values();
			ConfigType.values();

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
			Utility.getWarnings();

			// Catch any exception that, for whatever reason wasn't already caught
		} catch (Exception e) {
			StackTraceElement cause = e.getStackTrace()[0];

			StackTraceElement origin = null;
			for (StackTraceElement ste : e.getStackTrace()) {
				if (ste.getClassName().contains("koupah")) {
					origin = ste;
					break;
				}
			}
			System.out.println("Exception Type: " + cause.getClassName());
			System.out.println("Method: " + cause.getMethodName());
			System.out.println(origin == null ? ""
					: "\nOrigin Class: " + origin.getClassName() + "\nOrigin Method: " + origin.getMethodName()
							+ "\nOrigin Line: " + origin.getLineNumber());
			JOptionPane.showMessageDialog(null,
					"Fatal error #0001, send this to Koupah#5129 on discord or open an issue on the GitHub with the below!\nVersion: "
							+ version + "\nMessage: " + e.getMessage() + "\nException Type: " + cause.getClassName()
							+ "\nMethod: " + cause.getMethodName()
							+ (origin == null ? ""
									: "\nOrigin Class: " + origin.getClassName() + "\nOrigin Method: " + origin.getMethodName()
											+ "\nOrigin Line: " + origin.getLineNumber())
							+ "\n\nNote: There is a chance you can fix this error yourself by deleting your AUEConfig file\nand by also deleting your playerPrefs file (If you know where it is)");

			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void checkWarning(Setting setting, String value) {
		String current = setting.getLabelText().replaceAll(" ", "").replaceAll(":", "");
		checkWarning(current + "|" + value);
		checkWarning(current + "|Index:" + setting.getSettingIndex());
	}

	public static void checkWarning(String input) {
		System.out.println("Checking warning for: " + input);
		if (warnings.containsKey(input)) {
			String warning = "";
			System.out.println(input.contains("\\\\n"));
			for (String warningLine : warnings.get(input).split("\\\\n")) {
				warning += warningLine + "\n";
				System.out.println("line: " + warningLine);
			}
			new PopUp("Warning Message:\n" + warning, false);

			warnings.remove(input); // We only want to show warnings once
		}
	}

	public static void checkWarning(HostSetting hs) {
		checkWarning(hs.getLabelText().replaceAll(" ", "").replaceAll(":", "") + "|Index:" + hs.getIndex());
	}

	public static void checkWarning(PlayerStat ps) {
		checkWarning(ps.getLabelText().replaceAll(" ", "").replaceAll(":", "") + "|Index:" + ps.getIndex());
	}

	public static void checkWarning(CheckboxSetting setting) {
		checkWarning(setting.getLabelText().replaceAll(" ", "").replaceAll(":", "") + "|" + setting.isCheckboxSelected());
	}

	public static void setupRichPresence() {
		usingRichPresence = true;
		if (presence != null) {
			updatePresence();
			return;
		}

		try {
			System.out.println("Starting Rich Presence");

			presence = new DiscordRichPresence();
			String applicationId = AUEAppID;

			lib.Discord_Initialize(applicationId, null, true, "");

			presence.details = "Testing stuff";
			presence.largeImageKey = "aue";
			presence.startTimestamp = System.currentTimeMillis() / 1000;
			lib.Discord_UpdatePresence(presence);
			// in a worker thread
			new Thread() {
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						if (usingRichPresence)
							lib.Discord_RunCallbacks();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException ignored) {
						}
					}

				}
			}.start();

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					if (usingRichPresence) {
						lib.Discord_ClearPresence();
						lib.Discord_Shutdown();
					}
				}
			});

		} catch (Exception e) {
			System.out.println("Rich Presence failed, disabling it.");
			usingRichPresence = false;
		}
	}

	public static void updatePresence() {
		lib.Discord_UpdatePresence(presence);
	}

	public static void removeRichPresence() {
		usingRichPresence = false;
		lib.Discord_ClearPresence();
	}
}
