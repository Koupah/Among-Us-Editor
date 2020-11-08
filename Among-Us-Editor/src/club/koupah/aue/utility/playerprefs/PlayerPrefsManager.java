package club.koupah.aue.utility.playerprefs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;

public class PlayerPrefsManager {

	Editor instance;
	public String[] newSettings;
	public String[] currentSettings;

	int minPlayerPrefsSize = 20;

	public PlayerPrefsManager(Editor instance) {
		this.instance = instance;
	}

	public void savePlayerPrefs() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(instance.configManager.getPlayerPrefs()), "UTF-8"))) {
			bufferedWriter.write(String.join(",", newSettings));
			bufferedWriter.close();
		} catch (IOException e) {
			new PopUp("Error writing to file!\n" + e.getMessage(), true);
		}
	}

	public void loadSettings() {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(instance.configManager.getPlayerPrefs()), "UTF-8"))) {

			String line = bufferedReader.readLine();
			System.out.println("Read the following from the playerPrefs file: \n" + line + " (Length: " + line.length() + ", indexes: " + (line.contains(",") ? line.split(",").length : 0) +")");

			// This is unnecessary, playerPrefs file is 1 line
			// while(line != null) { line = bufferedReader.readLine(); }

			bufferedReader.close();

			boolean save = false;

			if (line == null || line.replaceAll(" ", "").replaceAll("\n", "").length() < 1
					|| !(line.length() >= minPlayerPrefsSize)) {/* Forgot to check if this was null lol */
				// Default setting for people who for some reason have an empty playerPrefs file
				// Red, no cosmetics on and mouse+keyboard movement
				line = instance.defaultSettings;
				new PopUp(
						"It doesn't look like you played the game before...\nYour settings have now been set to some default ones!",
						false);
				save = true;
			}

			if (line != null && line.contains(",") && line.split(",").length >= minPlayerPrefsSize)
				currentSettings = line.split(",");
			else new PopUp(
					"Couldn't load that playerPrefs file as it was smaller than expected,\nPlease join the discord server and check if there is an update!\nDiscord Server: www.koupah.club/aueditor\n\nNerdy Stuff: "
							+ line + "\n" + (line != null),
					true);

			newSettings = currentSettings;

			Setting.setCurrentSettings(currentSettings);

			// Save it so we don't have a "first time launching" message every launch until
			// they save settings lol
			if (save) {
				savePlayerPrefs();
			}
		} catch (FileNotFoundException e) {
			new PopUp(
					"Error! playerPrefs file doesn't exist?\nAre you sure you have the game and have change your settings before?",
					true);
		} catch (IOException e) {
			new PopUp("Error reading playerPrefs!\n" + e.getMessage(), true);
		}
	}
}
