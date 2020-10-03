package club.koupah.amongus.editor.utility.playerprefs;

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

import javax.swing.JFileChooser;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.utility.PopUp;

public class PlayerPrefsFinder {

	List<Path> filePaths = new ArrayList<Path>();
	boolean foundPrefs = false;

	Editor instance;
	
	String configName;
	
	public PlayerPrefsFinder(Editor instance) {
		this.instance = instance;
		this.configName = instance.config.getName();
	}

	// Used by windows and other os's
	public File loadConfig() {

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(instance.config), "UTF-8"))) {
			String line = bufferedReader.readLine();
			if (line == null)
				return null;

			File toReturn = new File(line);
			
			if ((line = bufferedReader.readLine()) != null)
				Editor.currentLookAndFeel = line;
			
			if (!toReturn.exists()) {
				new PopUp(String.format("The file directory in the %s file points to a playerPrefs file that doesn't exist!\n"
						+ "Please check the \"%s\" file or delete it to reobtain the playerPrefs directory!", configName));
			} else {
				return toReturn;
			}
		} catch (IOException e) {
			new PopUp(String.format("Couldn't read the %s file! Exiting.",configName));
		}
		new PopUp(String.format("%s file wasn't read?\nFatal error, open an issue on GitHub or contact Koupah#5129 on discord!", configName));
		return null;
	}

	// Only used by windows
	public File getPlayerPrefs() {

		// Check if the config exists
		if (instance.config.exists()) {
			File fromConfig = loadConfig();
			// Can return null if the line 
			if (fromConfig != null /*&& fromConfig.exists()*/) //Removed exists check as I want the user to know if it doesn't exist anymore
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
			new PopUp(String.format("Your playerPrefs file wasn't in the expected folder nor the %s file!\nPress \"OK\" to begin scanning for it!", configName),
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

	public void saveToConfig(String settings) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(instance.config), "UTF-8"))) {
			bufferedWriter.write(settings);
			if (Editor.currentLookAndFeel != null) {
			bufferedWriter.newLine();
			bufferedWriter.write(Editor.currentLookAndFeel);
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			new PopUp(String.format("Failed to save the playerPrefs file location to %s.\nContinuing but it'll have to be found again next launch!", configName),
					false);
			
		}
	}

	public void choosePlayerPrefs() {
		// I'll probably get around to looping through all files in a chosen directory
		// to find the playerPrefs file,
		// But I don't know
		new PopUp("Please navigate to the playerPrefs file for Among Us\nThis will typically be in your Steam folder\n\nThis will save in a file named \""
				+ instance.config.getName() + "\"", false);

		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File("playerPrefs"));
		chooser.setMultiSelectionEnabled(false);
		// Just to ensure that it shows both, it should by default though
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Show hidden files such as the .steam file on Linux. Very Crucial.
		// Also big shoutout to Slymeball, I will be giving them credit in the readme
		// but
		// without them mac/linux support probably wouldn't have existed, atleast not
		// this soon
		chooser.setFileHidingEnabled(false);

		// Switch to go through the different user actions
		switch (chooser.showOpenDialog(instance)) {

		case JFileChooser.APPROVE_OPTION: {
			instance.playerPrefs = chooser.getSelectedFile();

			if (!instance.playerPrefs.exists()) {
				new PopUp("The playerPrefs file you selected, doesn't exist? Exiting.", true);
			}

			try {
				instance.config.createNewFile();
			} catch (IOException e) {
				new PopUp(
						"Notice, couldn't create the config file.\nYou'll have to reselect the playerPrefs file next time you launch.",
						false);
				break;
			}

			// save config now
			saveToConfig(instance.playerPrefs.getAbsolutePath());

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
