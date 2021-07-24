package club.koupah.aue.utility.playerprefs;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import club.koupah.aue.Editor;
import club.koupah.aue.utility.PopUp;

public class PlayerPrefsFinder {

	List<Path> filePaths = new ArrayList<Path>();
	boolean foundPrefs = false;

	Editor instance;

	public PlayerPrefsFinder(Editor instance) {
		this.instance = instance;
	}

	// Only used by windows
	public File getPlayerPrefs() {

		// Check if the config exists
		if (instance.configManager.configExists()) {
			File fromConfig = instance.configManager.getPlayerPrefs();
			// Can return null if the line
			if (fromConfig != null && fromConfig.exists()) {// Removed exists check as I want the user to know if it
																			// doesn't exist anymore
				return fromConfig;
			} else {
				new PopUp(
						"The playerPrefs file in your AUEConfig doesn't exist!\nThis can happen if you use someone elses AUEConfig file!\n\nGoing to search for correct the playerPrefs file now!",
						false);
			}
		} else {
			instance.configManager.createConfigFile();
		}

		// Where my playerPrefs was
		/*
		 * File standardLocation = new File( System.getProperty("user.home") +
		 * "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs");
		 */
		File amongUsFolder = new File(
				System.getProperty("user.home") + "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs");

		File prefs = null;

		for (File file : amongUsFolder.listFiles()) {
			// Make sure it's not a copy of player prefs
			if (!file.isDirectory() && file.getName().startsWith("playerPrefs") && !file.getName().contains("Copy"))
				prefs = file;
		}

		if (prefs != null && prefs.exists()) {
			instance.configManager.setPlayerPrefs(prefs);
			return prefs;
		} else {
			// Warn user we're going to scan their PC, don't have an option to deny it
			// **yet**

			new PopUp(String.format(
					"Your playerPrefs file wasn't in the expected folder nor the %s file!\nPress \"OK\" to begin scanning for it!\n\nNote: This may take a couple minutes",
					instance.configManager.configName()), false);
		}

		// Doesn't return anything
		searchForPlayerPrefs();

		if (!foundPrefs || filePaths.size() < 1) {
			new PopUp(
					"Are you sure you have Among Us and have played it before?\nCouldn't seem to find your playerPrefs file anywhere!\nPress ok to manually select it.",
					false);

			choosePlayerPrefs();

			return instance.configManager.getPlayerPrefs();
		}

		// Look man, just assume this is the correct file
		File fileFromSearch = filePaths.get(0).toFile(); // Can't be null, if it didnt exist, the user is notified above
		instance.configManager.setPlayerPrefs(fileFromSearch);
		return fileFromSearch;
	}

	public void searchForPlayerPrefs() {
		File[] paths = File.listRoots();
		try {
			search(new File(System.getProperty("user.home") + "\\AppData\\").toPath());
		} catch (Exception e) {
			System.out.println("Error during searchForPlayerPrefs(): " + e.getMessage());
		}
		// for each pathname in pathname array
		for (File drive : paths) {
			try {
				search(drive.toPath());
			} catch (IOException e) {
				System.out.println("Drive " + drive + " not searchable.");
			}
		}
	}

	public void search(Path dir) throws IOException {
		String cPath; // current path, I'm writing this code for all these classes at 3AM AEST, leave
							// me alone
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {

				// Don't want to continue iteration if we found it
				if (foundPrefs)
					return;

				cPath = path.toString();

				// I doubt someones steam config is in these folders lol, but we still allow the
				// appdata folder cause of the default location of the thingy
				if (cPath.contains("\\Windows\\") || cPath.contains("\\$Recycle")
						|| ((cPath.contains("\\Users\\") && !cPath.contains("AppData")))) {
					/*
					 * The users stuff search from going through users folder (Unless it's the
					 * AppData folder specified in the searchForConfig() function)
					 */
					continue;
				}

				if (!Files.isDirectory(path)) {

					// Apparently not case sensitive, very useful
					if (path.endsWith("Among Us\\playerPrefs")) {
						System.out.println("Found playerPrefs (I hope): " + cPath);
						foundPrefs = true;
						filePaths.add(path);
						return;
					}
				} else {
					search(path);
				}
			}
		} catch (IOException e) {
		}
	}

	public void choosePlayerPrefs() {
		// I'll probably get around to looping through all files in a chosen directory
		// to find the playerPrefs file,
		// But I don't know
		new PopUp(
				"Please navigate to the playerPrefs file for Among Us\nThis will typically be in:\nWindows: AppData/LocalLow/Innersloth/Among Us\nLinux: /home/yourname/.steam/steam/steamapps/compatdata/945360/pfx/drive_c/users/steamuser/AppData/LocalLow/Innersloth/Among Us/\nLinux (Wine): ~/.wine/drive_c/users/$USER/AppData/LocalLow/Innersloth/Among Us/playerPrefs\n\nThis is a one time thing, your selection will be saved in a file named \"AUEConfig\"",
				false);

		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File("playerPrefs"));
		chooser.setMultiSelectionEnabled(false);
		// Just to ensure that it shows both, it should by default though
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Show hidden files such as the .steam file on Linux. Very Crucial.
		// Also big shoutout to Slymeball, I will be giving them credit in the readme
		// but
		// without them mac/linux support probably wouldn't have existed, atleast not
		// this soon
		chooser.setFileHidingEnabled(false);

		// Switch to go through the different user actions
		switch (chooser.showOpenDialog(instance)) {

		case JFileChooser.APPROVE_OPTION: {
			instance.configManager.setPlayerPrefs(chooser.getSelectedFile());

			if (!instance.configManager.getPlayerPrefs().exists()) {
				new PopUp("The playerPrefs file you selected, doesn't exist? Exiting.", true);
			}

			if (!instance.configManager.configExists())
				instance.configManager.createConfigFile();

			break;
		}

		case JFileChooser.CANCEL_OPTION: {
		}
		case JFileChooser.ERROR_OPTION: {
			new PopUp("You didn't seem to choose a file? Exiting.");
		}
		}
	}

	/*
	 * 
	 * THIS CODE WILL BE USED TO FIND THE USERS STEAM CONFIG LOCATION TO THEN FIND
	 * THEIR INSTALLATION FOLDERS TO THEN TRY AND QUICKLY FIND THEIR PLAYER PREFS
	 * 
	 * REMOVED FOR NOW, JUST GOING TO OPT IN FOR THE SAFER SEARCHING FOR
	 * "playerPrefs" INSTEAD!
	 * 
	 * 
	 * 
	 * public static void list(Path dir) throws IOException {
	 * 
	 * String cPath; try (DirectoryStream<Path> stream =
	 * Files.newDirectoryStream(dir)) { for (Path path : stream) {
	 * 
	 * if (foundPrefs) return;
	 * 
	 * cPath = path.toString();
	 * 
	 * // I doubt someones steam config is in these folders lol, but we still allow
	 * the appdata folder cause of the default location if
	 * (cPath.contains("\\Windows\\") || cPath.contains("\\
	 * $Recycle") && !(!cPath.contains(":\\Users\\") &&
	 * cPath.endsWith("\\Users\\") && cPath.contains("AppData"))) { continue; }
	 * 
	 * if (!Files.isDirectory(path)) {
	 * 
	 * if (path.endsWith("config\\config.vdf")) { System.out.println(cPath);
	 * filePaths.add(path); } else if (path.endsWith("playerPrefs")) {
	 * System.out.println(cPath); foundPrefs = true; filePaths.clear();
	 * filePaths.add(path); return; }
	 * 
	 * } else { list(path); } } } catch (IOException e) {
	 * 
	 * } }
	 */

}
