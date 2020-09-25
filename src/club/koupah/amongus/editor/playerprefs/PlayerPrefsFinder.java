package club.koupah.amongus.editor.playerprefs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.PopUp;

public class PlayerPrefsFinder {

	List<Path> filePaths = new ArrayList<Path>();
	boolean foundPrefs = false;

	Editor instance;

	public PlayerPrefsFinder(Editor instance) {
		this.instance = instance;
	}

	// Used by windows and other os's
	public File loadConfig() {

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(instance.config), "UTF-8"))) {
			String line = bufferedReader.readLine();
			if (line == null)
				return null;

			File toReturn = new File(line);

			if (!toReturn.exists()) {
				new PopUp("The file directory in the AUEConfig file points to a playerPrefs file that doesn't exist!\n"
						+ "Please check the \"AUEConfig\" file or delete it to reobtain the playerPrefs directory!");
			} else {
				return toReturn;
			}
		} catch (IOException e) {
			new PopUp("Couldn't read the AUEConfig file! Exiting.");
		}
		new PopUp(
				"AUEConfig file wasn't read?\nFatal error, open an issue on GitHub or contact Koupah#5129 on discord!");
		return null;
	}

	// Only used by windows
	public File getPlayerPrefs() {

		// Check if the config exists
		if (instance.config.exists()) {
			File fromConfig = loadConfig();
			// Can return null if the line is null
			if (fromConfig != null)
				return fromConfig;
		}

		// Try and make the config file
		try {
			instance.config.createNewFile();
		} catch (IOException e) {
		}

		// Where my playerPrefs was
		File standardLocation = new File(
				System.getProperty("user.home") + "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs");

		if (standardLocation.exists()) {
			saveToConfig(standardLocation.getAbsolutePath());
			return standardLocation;
		} else {
			// Warn user we're going to scan their PC, don't have an option to deny it
			// **yet**
			new PopUp(
					"Your playerPrefs file wasn't in the expected folder nor a config file!\nPress \"OK\" to begin scanning for it!",
					false);
		}

		// Doesn't return anything
		searchForConfig();

		if (!foundPrefs || filePaths.size() < 1) {
			new PopUp(
					"Are you sure you have Among Us and have played it before?\nCouldn't seem to find your playerPrefs file anywhere!\nPlease open an issue on GitHub if this is incorrect!");
		}

		// Look man, just assume this is the correct file
		saveToConfig(filePaths.get(0).toString());
		return filePaths.get(0).toFile();
	}

	public void searchForConfig() {
		File[] paths = File.listRoots();

		try {
			search(new File(System.getProperty("user.home") + "\\AppData\\").toPath());
		} catch (Exception e) {
			// Do nothing, we basically already check here, but this is extra check :p
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

	public void saveToConfig(String path) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(instance.config), "UTF-8"))) {
			bufferedWriter.write(path);
			bufferedWriter.close();
		} catch (IOException e) {
			new PopUp(
					"Failed to save the playerPrefs file location to AUEConfig.\nContinuing but it'll have to be found again next launch!",
					false);
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
