package club.koupah.aue.utility.config;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.utility.PopUp;

public class ConfigManager {

	String configName;

	File config;

	File playerPrefs;

	String lookAndFeel;

	String[] profiles;

	Editor instance;
	
	String customColors;
	
	//Default scheme/look
	GUIScheme scheme = GUIScheme.Normal;
	
	public ConfigManager(File configFile, Editor instance) {
		this.config = configFile;
		this.configName = configFile.getName();
		this.instance = instance; 
	}

	// Used by windows and other os's
	public void loadConfig() {

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(config), "UTF-8"))) {

			String line = bufferedReader.readLine();

			if (line == null)
				return;

			// player prefs directory should be the first line in config
			playerPrefs = new File(line);

			// the look and feel should be the second line
			if ((line = bufferedReader.readLine()) != null)
				lookAndFeel = line;
			else
				return;
			
			//GUI Scheme should be third line
			if ((line = bufferedReader.readLine()) != null) {
				GUIScheme scheme = GUIScheme.findByName(line);
				if (scheme != null) 
					setScheme(scheme);
			}
			else
				return;
			
			//Custom Color should be fourth line Example: aueCC:-3333:3333
			if ((line = bufferedReader.readLine()) != null && line.contains("aueCC:")) {
					customColors = line.split("aueCC:")[1];
					GUIScheme.Custom.setForeground(new Color(Integer.parseInt(customColors.split(":")[0])));
					GUIScheme.Custom.setBackground(new Color(Integer.parseInt(customColors.split(":")[1])));
			}
			
			
			// Continuously loop to find profiles line, this ensures backwards & forwards
			// compatibility
			while ((line = bufferedReader.readLine()) != null) {
				if (line.toLowerCase().contains("[profiles]")) {
					// Loop through all profiles
					while ((line = bufferedReader.readLine()) != null) {
						new Profile(line);
					}
					break;
				}
			}

			// Return so we don't hit the below message that'll only run after the try catch
			return;

		} catch (IOException e) {
			new PopUp(String.format("Couldn't read the %s file! Exiting.", configName));
		}
		new PopUp(String.format(
				"%s file wasn't read?\nFatal error, open an issue on GitHub or contact Koupah#5129 on discord!",
				configName));
		return;
	}

	
	public void saveConfig() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(config), "UTF-8"))) {
			
			if (!playerPrefs.exists()) {
				new PopUp("Can't save " + configName + " file because playerPrefs doesn't exist!");
				return;
			}
			
			bufferedWriter.write(playerPrefs.getAbsolutePath());
			bufferedWriter.newLine();
			
			//We should only continue writing if the line that should exist next, exists. This is for 100% backwards/forwards compatibility
			if (lookAndFeel != null) {
				
				bufferedWriter.write(lookAndFeel);
				bufferedWriter.newLine();	
				bufferedWriter.write(scheme.getName());
				bufferedWriter.newLine();
				bufferedWriter.write("aueCC:" + (GUIScheme.Custom.getForeground().getRGB()) + ":" + (GUIScheme.Custom.getBackground().getRGB()));
				bufferedWriter.newLine();
				
				
				//Write profiles here
				  bufferedWriter.write("[profiles]");
				  bufferedWriter.newLine();
				  for (Profile profile : Profile.profiles) {
				  	bufferedWriter.write(profile.getConfigLine());
				  	bufferedWriter.newLine();
				  }
				
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			new PopUp(String.format(
					"Failed to save the playerPrefs file location to %s.\nContinuing but it'll have to be found again next launch!",
					configName),
					false);

		}
	}
	
	public boolean configExists() {
		return this.config.exists();
	}

	public File getPlayerPrefs() {
		return this.playerPrefs;
	}

	public void createConfigFile() {
		try {
			this.config.createNewFile();
		} catch (IOException e) {
			new PopUp(
					"Failed to create the " + configName + " file!\nNo settings will be saved!\nMessage: " + e.getMessage(),
					false);
		}
	}

	public String configName() {
		return configName;
	}

	public void setPlayerPrefs(File playerPrefs) {
		this.playerPrefs = playerPrefs;
	}

	public void setLookAndFeel(String lnf) {
		this.lookAndFeel = lnf;
	}

	public String getLookAndFeel() {
		return this.lookAndFeel;
	}

	public void setScheme(GUIScheme scheme) {
		this.scheme = scheme;
	}

	public GUIScheme getScheme() {
		return this.scheme;
	}

}
