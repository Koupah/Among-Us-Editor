package club.koupah.aue;

import static club.koupah.aue.gui.types.SettingType.COSMETIC;
import static club.koupah.aue.gui.types.SettingType.PREFERENCES;
import static club.koupah.aue.gui.types.SettingType.SETTING;
import static club.koupah.aue.utility.playerprefs.Indexes.censorChat;
import static club.koupah.aue.utility.playerprefs.Indexes.color;
import static club.koupah.aue.utility.playerprefs.Indexes.control;
import static club.koupah.aue.utility.playerprefs.Indexes.hat;
import static club.koupah.aue.utility.playerprefs.Indexes.language;
import static club.koupah.aue.utility.playerprefs.Indexes.music;
import static club.koupah.aue.utility.playerprefs.Indexes.name;
import static club.koupah.aue.utility.playerprefs.Indexes.pet;
import static club.koupah.aue.utility.playerprefs.Indexes.sfx;
import static club.koupah.aue.utility.playerprefs.Indexes.skin;
import static club.koupah.aue.utility.playerprefs.Indexes.vsync;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import club.koupah.aue.gui.GUIComponent;
import club.koupah.aue.gui.GUIManager;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.GUITabbedPanel;
import club.koupah.aue.gui.Setting;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.gui.settings.cosmetics.Cosmetic;
import club.koupah.aue.gui.settings.cosmetics.Cosmetic.CosmeticType;
import club.koupah.aue.gui.settings.language.Language;
import club.koupah.aue.gui.types.CheckboxSetting;
import club.koupah.aue.gui.types.MultiSetting;
import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.gui.types.SliderSetting;
import club.koupah.aue.gui.types.TextSetting;
import club.koupah.aue.gui.types.custom.CosmeticFilter;
import club.koupah.aue.gui.types.custom.DiscordButton;
import club.koupah.aue.gui.types.custom.InvisibleName;
import club.koupah.aue.gui.types.custom.LookAndFeelChooser;
import club.koupah.aue.gui.types.custom.SchemeChooser;
import club.koupah.aue.gui.types.custom.UpdateChecker;
import club.koupah.aue.utility.ImageUtil;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.ConfigManager;
import club.koupah.aue.utility.playerprefs.PlayerPrefsFinder;
import club.koupah.aue.utility.playerprefs.PlayerPrefsManager;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	private GUIPanel cosmeticPanel;
	private GUIPanel settingsPanel;
	private GUIPanel preferencesPanel;

	public JPanel panel;

	public GUITabbedPanel tabbedPanel;

	public ArrayList<GUIComponent> allGUIComponents = new ArrayList<GUIComponent>();

	public File playerPrefs;

	// Should I make this a double? That way with the version check I can check if
	// their version is less than rather than equal to
	// I still don't know, 26/09/2020

	// Update: Made it a double
	public static double version;
	static boolean preRelease;
	static boolean outdated;

	public static String title = "Among Us Editor";

	static Thread updateCheck;

	// Spacing between gui components
	public static int scale = 40;

	public boolean windows = false;

	// This config file is only for NON windows users
	private File config = new File("AUEConfig");

	// Made these in order to dump my code into them instead of this main class
	public PlayerPrefsFinder prefsFinder; // public so i can save config
	PlayerPrefsManager prefsManager;

	static Editor editor;

	JButton applySettings;

	int width = 445;

	int height = 0;

	public ConfigManager configManager;

	// Default settings for when a user has no settings
	public String defaultSettings = "Username,1,0,1,False,False,False,0,False,False,0,255,94,0.5,0,0,0,True,0,False";

	public static Color background;

	// Use this for persistent look and feel
	public String currentLookAndFeel;

	public GUIManager guiManager;

	public Editor(double ver) {

		// Made it like this to easily update version number from main class :p
		version = ver;

		editor = this;

		this.setIconImage(ImageUtil.getImage(GUIPanel.class, "tabicons/cosmetics.png"));

		setTitle(title + " (v" + version + ") - By Koupah");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);

		background = new Color(238, 238, 238);
		panel.setBackground(background);
	}

	public void setupFiles() {

		guiManager = new GUIManager(this);

		configManager = new ConfigManager(config, this);
		configManager.loadConfig();

		prefsFinder = new PlayerPrefsFinder(this);
		prefsManager = new PlayerPrefsManager(this);

		// Big handler to decide where the playerPrefs file is or what to do if it isn't
		// saved in a config

		// Edit: *small handler, everything has been moved
		if (windows) {

			playerPrefs = prefsFinder.getPlayerPrefs();

		} else if (configManager.configExists()) {
			// Just get the one from config if we're on mac/linux
			playerPrefs = configManager.getPlayerPrefs();

			if (!playerPrefs.exists()) {
				new PopUp("The playerPrefs file in your config doesn't exist!\nPlease choose it again!");
				// Function for non-windows users
				prefsFinder.choosePlayerPrefs();
			}

		} else if (!configManager.configExists()) {

			// Function for non-windows users
			prefsFinder.choosePlayerPrefs();

		} else {
			// This shouldn't show up, but if it does, give it a wonky error number so I can
			// search for it
			new PopUp("This error shouldn't occur.\nError #1731\nMessage Koupah#5129 on discord.");
		}

		// This really shouldn't be possible because the above should counter it, but
		// **just** incase
		if (!playerPrefs.exists()) {
			new PopUp("The playerPrefs file doesn't exist!\nError #1932\nMessage Koupah#5129 on discord.");
		}

	}

	// Made setupWindow function so we can set the layout then continue setting up
	// the window
	public void setupWindow() {
		Font font = new Font("Tahoma", Font.PLAIN, 30);
		JLabel topText = new JLabel(title);
		topText.setFont(font);
		topText.setHorizontalAlignment(SwingConstants.CENTER);

		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

		int titleWidth = (int) (font.getStringBounds(title, frc).getWidth());

		topText.setBounds(width / 2 - (titleWidth / 2) - 10, 5, titleWidth + 20, 42);
		panel.add(topText);

		font = new Font("Tahoma", Font.PLAIN, 12);
		String verString = "v" + version;
		int verwidth = (int) (font.getStringBounds(verString, frc).getWidth());

		JLabel versionText = new JLabel(verString);
		versionText.setFont(font);
		versionText.setHorizontalAlignment(SwingConstants.RIGHT);
		versionText.setBounds(width / 2 + (titleWidth / 2) - 10, 25, verwidth + 20, 20);

		panel.add(versionText);

		tabbedPanel = new GUITabbedPanel();
		tabbedPanel.setBounds(1, 54, width, 400);
		tabbedPanel.setBackground(background);
		tabbedPanel.setForeground(Color.BLACK);

		panel.add(tabbedPanel);

		cosmeticPanel = new GUIPanel("Cosmetics", "cosmetics.png");

		settingsPanel = new GUIPanel("Settings", "settings.png");

		preferencesPanel = new GUIPanel("Preferences", "preferences.png");

		// TODO remove these 3 lines, have some sort of for loop

		tabbedPanel.addTab("Cosmetics", cosmeticPanel.getIcon(), cosmeticPanel, cosmeticPanel.getDescription());

		tabbedPanel.addTab("Settings", settingsPanel.getIcon(), settingsPanel, settingsPanel.getDescription());

		tabbedPanel.addTab("Preferences", preferencesPanel.getIcon(), preferencesPanel,
				preferencesPanel.getDescription());

		tabbedPanel.getComponentAt(0).setBackground(Color.RED);
		/*
		 * 
		 * COSMETIC SETTINGS!
		 * 
		 */

		// Not using allGUISettings.addAll(Arrays.asList(Setting,Setting,Setting));
		// because we use the size of the array to get index because am lazy
		add(new TextSetting(new JLabel("Username: "), new JTextField(), name.index()), COSMETIC);

		add(new InvisibleName(new JLabel("Invisible Name: "), new JCheckBox(), name.index()), COSMETIC);

		// Shifted hat over 5 pixels to make it more centered on average w/ the other
		// cosmetics
		add(new MultiSetting(new JLabel("Hat: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Hat), true,
				true, new int[] { -5, 0, 0, 0, 1 }, hat.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Color: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Color),
				true, true, new int[] { 0, -5, 0, 12, 3 }, color.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Skin: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Skin), true,
				true, new int[] { 0, 0, 0, 0, 2 }, skin.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Pet: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Pet), false,
				true, new int[] { -12, -12, 24, 24, 5 }, pet.index()), COSMETIC);

		add(new CosmeticFilter(new JLabel("Filter Cosmetics: "), new JComboBox<String>()), COSMETIC);

		/*
		 * 
		 * SETTING SETTINGS!
		 * 
		 */

		add(new MultiSetting(new JLabel("Language: "), new JComboBox<String>(), Language.getAllLanguagesString(), false,
				language.index()), SETTING);

		add(new MultiSetting(new JLabel("Controls: "), new JComboBox<String>(),
				Arrays.asList("Mouse Only", "Keyboard and Mouse"), false, control.index()), SETTING);

		add(new CheckboxSetting(new JLabel("VSync: "), new JCheckBox(), vsync.index()), SETTING);

		add(new CheckboxSetting(new JLabel("Censor Chat: "), new JCheckBox(), censorChat.index()), SETTING);

		add(new SliderSetting(new JLabel("Sounds Effects Volume: "), new JSlider(), 0, 255, sfx.index()), SETTING);

		add(new SliderSetting(new JLabel("Music Volume: "), new JSlider(), 0, 255, music.index()), SETTING);

		/*
		 * 
		 * PREFERENCES SETTINGS!
		 * 
		 */

		add(new LookAndFeelChooser(new JLabel("Look & Feel: "), new JComboBox<String>()), PREFERENCES);
		add(new SchemeChooser(new JLabel("GUI Mode: "), new JComboBox<String>()), PREFERENCES);

		add(new UpdateChecker(new JLabel("Version: "), new JButton("Check for Update")), PREFERENCES);
		add(new DiscordButton(new JLabel("Join the discord server, click the button!"), new JButton("Join Server")),
				PREFERENCES);

		for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
			GUIPanel panel = (GUIPanel) tabbedPanel.getComponentAt(i);

			for (Component comp : panel.getComponents()) {
				if (!(comp instanceof JTextField))
					comp.setFocusable(false);
			}

		}

		int maxHeight = Math.max(cosmeticPanel.getMaxHeight(), settingsPanel.getMaxHeight());

		// Just cause bro, don't @ me
		height = 75 + maxHeight + 80;

		tabbedPanel.setBounds(tabbedPanel.getX(), tabbedPanel.getY(), width - 18, maxHeight);
		applySettings = new JButton("Apply Settings");
		applySettings.setBounds(147, height - 85, 151, 24);
		panel.add(applySettings);

		// Set window bounds too
		setBounds(100, 100, width, height);

		for (Component comp : panel.getComponents()) {
			if (!(comp instanceof JTextField))
				comp.setFocusable(false);
		}

		getRootPane().requestFocus();

		applySettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveSettings();
			}
		});

		/*
		 * 
		 * FINAL UI STUFF
		 * 
		 */
		if (configManager.getLookAndFeel() != null) {
			guiManager.updateLookAndFeel();
		} else {
			// If the look and feel value doesn't exist, update it
			configManager.setLookAndFeel(UIManager.getLookAndFeel().getClass().toString());
		}
		
		if (configManager.getScheme() != null) {
			guiManager.updateColorScheme();
		} else {
			configManager.setScheme(GUIScheme.Light);
			guiManager.updateColorScheme();
		}
		

		// After everything, save the config
		configManager.saveConfig();

		refresh();
	}

	public void saveSettings() {
		for (GUIComponent guicomponent : allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting) guicomponent;

				// This allows InvisibleName and future GUIComponents to avoid impacting
				// settings
				if (setting.getComponentValue(false) != null)
					prefsManager.newSettings[setting.getSettingIndex()] = setting.getComponentValue(false);
			}
		}
		prefsManager.savePlayerPrefs();
		refresh();
	}

	// Made this cause it's smaller than writing allGUISettings.add()
	public void add(GUIComponent setting, SettingType type) {
		allGUIComponents.add(setting);

		switch (type) {
		case COSMETIC: {
			setting.addToPane(cosmeticPanel);
			break;
		}
		case SETTING: {
			setting.addToPane(settingsPanel);
			break;
		}
		case PREFERENCES: {
			setting.addToPane(preferencesPanel);
			break;
		}
		default:
			break;
		}
	}

	public void refresh() {
		prefsManager.loadSettings();

		for (GUIComponent guicomponent : allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting) guicomponent;
				setting.updateLabel();
				setting.updateComponent();

			}
		}
	}

	public static Editor getInstance() {
		return editor;
	}

	// Moved update check to bottom of class (Button is for manual update check)
	public void runUpdateCheck(final JButton button) {

		if (button != null)
			button.setEnabled(false);

		/*
		 * 
		 * VERSION CHECK FOR PEOPLE WHO DOWNLOAD STRAIGHT FROM A YOUTUBE VIDEO ETC Feel
		 * free to remove this if you're compiling yourself!
		 * 
		 */
		updateCheck = new Thread() {
			public void run() {
				try {
					URLConnection connection = new URL(
							"https://raw.githubusercontent.com/Koupah/Among-Us-Editor/master/version").openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

					// The string value of the latest version
					String latestVersionString = in.readLine();
					// The double value of the latest version
					double latestVersion = Double.parseDouble(latestVersionString);

					// Check if the latest version is greater than our version
					outdated = latestVersion > version;

					// If the current version is ahead of the 'latest' then we're on a prerelease
					preRelease = latestVersion < version;

					if (outdated) {
						String info = "";
						String read;
						while ((read = in.readLine()) != null) {
							info += read + "\n";
						}

						String message = "You're on version " + version + " and " + latestVersionString
								+ " is the latest!"
						// If there is info (not just like a space or whatever) then show the message
								+ (info.length() > 2 ? "\n\nVersion " + latestVersionString + ":\n" + info : "");

						// Create a new pop up with the message and allows for opening the new version
						// in browser
						PopUp.downloadPopUp(message, latestVersionString);
					} else {
						if (button != null) {
							if (preRelease) {
								new PopUp("You are using a PRE release version!\nThe latest version is " + latestVersionString
										+ " but you're on " + version, false);
							} else {
								new PopUp("No updates available!\n\nYou are using the latest version,\nVersion: "
										+ latestVersionString, false);
							}
						}
					}
					in.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					new PopUp("Couldn't check if this is the latest version\nFeel free to close this message!", false);
				}

				while (editor == null) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}

				// Update the title based on version
				editor.setTitle(title + " (v" + version + (outdated ? " outdated" : (preRelease ? " prerelease" : ""))
						+ ") - By Koupah");

				if (button != null)
					button.setEnabled(true);
			}
		};

		updateCheck.start();
	}

}
