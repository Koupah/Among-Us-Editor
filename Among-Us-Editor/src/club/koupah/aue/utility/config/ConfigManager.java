package club.koupah.aue.utility.config;

import static club.koupah.aue.utility.config.ConfigType.*;
import static club.koupah.aue.utility.config.ConfigType.isSetting;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

import club.koupah.AUEditorMain;
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

	String rgbSpeed = "20"; // Default

	GUIScheme scheme = GUIScheme.Normal; // Default scheme/look

	boolean alwaysOnTop = false; // Default Value

	ArrayList<String> configLines = new ArrayList<String>();

	String applicationDirectory;

	public ConfigManager(String configFileName, Editor instance) {
		CodeSource codeSource = AUEditorMain.class.getProtectionDomain().getCodeSource();
		try {
			this.applicationDirectory = new File(codeSource.getLocation().toURI().getPath()).getParent();
		} catch (URISyntaxException e) {
			this.applicationDirectory = System.getProperty("user.dir"); // If we can't get the source, just get current
																							// directory
		}

		this.config = new File(applicationDirectory, configFileName);
		this.configName = config.getName();
		this.instance = instance;
	}

	// Used by windows and other os's
	public void loadConfig() {
		configLines.clear();
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(config), "UTF-8"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null)
				configLines.add(line);

			int lineNum = 0;
			for (String config : configLines) {

				// Look, it's not a switch or anything but it's cleaner then just checking every
				// line and makes some settings in config optional for proper loading
				if (isSetting(PlayerPrefs, config, lineNum)) {

					playerPrefs = new File(config); // Don't need to put current directory, it's a path

				} else if (isSetting(LookAndFeel, config, lineNum)) {

					lookAndFeel = config;

				} else if (isSetting(Scheme, config, lineNum)) {

					GUIScheme scheme = GUIScheme.findByName(config);
					if (scheme != null)
						setScheme(scheme);

				} else if (isSetting(CustomColors, config, lineNum)) {

					customColors = config.split(CustomColors.lineStart)[1];
					GUIScheme.Custom.setForeground(new Color(Integer.parseInt(customColors.split(":")[0])));
					GUIScheme.Custom.setBackground(new Color(Integer.parseInt(customColors.split(":")[1])));

				} else if (isSetting(RGBSpeed, config, lineNum)) {

					rgbSpeed = config.split(RGBSpeed.lineStart)[1];

				} else if (isSetting(AOT, config, lineNum)) {

					alwaysOnTop = config.split(":")[1].equals("true");

				} else if (config.contains(",") && config.split(",").length > 5) {

					new Profile(config);
				}

				lineNum++;
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

	// Still messy config saving tho
	public void saveConfig() {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config), "UTF-8"))) {

			if (!playerPrefs.exists()) {
				new PopUp("Can't save " + configName + " file because playerPrefs doesn't exist!");
				return;
			}

			// We should only continue writing if the line that should exist next, exists.
			// This is for 100% backwards/forwards compatibility

			PlayerPrefs.write(writer, playerPrefs.getAbsolutePath());

			LookAndFeel.write(writer, lookAndFeel);

			Scheme.write(writer, scheme.getName());

			CustomColors.write(writer,
					(GUIScheme.Custom.getForeground().getRGB()) + ":" + (GUIScheme.Custom.getBackground().getRGB()));

			RGBSpeed.write(writer, rgbSpeed);

			AOT.write(writer, String.valueOf(alwaysOnTop));

			// Write profiles here
			writer.write("[profiles]");
			writer.newLine();
			for (Profile profile : Profile.profiles) {
				writer.write(profile.getConfigLine());
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			new PopUp(config.getAbsolutePath());
			new PopUp(String.format(
					"Failed to save the playerPrefs file location to %s.\nContinuing but it'll have to be found again next launch!",
					configName), false);

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

	public int getRGBSpeed() {
		return Integer.valueOf(rgbSpeed);
	}

	public void setRGBSpeed(int value) {
		rgbSpeed = String.valueOf(value);
	}

	public boolean getAOT() {
		return this.alwaysOnTop;
	}

	public void setAOT(boolean aot) {
		this.alwaysOnTop = aot;
	}

}
