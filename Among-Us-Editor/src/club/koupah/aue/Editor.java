package club.koupah.aue;

import static club.koupah.aue.gui.types.SettingType.COSMETIC;
import static club.koupah.aue.gui.types.SettingType.OTHER;
import static club.koupah.aue.gui.types.SettingType.PREFERENCES;
import static club.koupah.aue.gui.types.SettingType.SETTING;
import static club.koupah.aue.gui.types.SettingType.RAT;
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

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
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

import club.koupah.aue.gui.GUIManager;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.GUITabbedPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.gui.types.impl.CheckboxSetting;
import club.koupah.aue.gui.types.impl.MultiSetting;
import club.koupah.aue.gui.types.impl.SliderSetting;
import club.koupah.aue.gui.types.impl.TextSetting;
import club.koupah.aue.gui.types.impl.custom.cosmetics.CosmeticFilter;
import club.koupah.aue.gui.types.impl.custom.cosmetics.InvisibleName;
import club.koupah.aue.gui.types.impl.custom.other.DiscordButton;
import club.koupah.aue.gui.types.impl.custom.other.UpdateChecker;
import club.koupah.aue.gui.types.impl.custom.preferences.AlwaysOnTop;
import club.koupah.aue.gui.types.impl.custom.preferences.LookAndFeelChooser;
import club.koupah.aue.gui.types.impl.custom.preferences.ResizableGUIOption;
import club.koupah.aue.gui.types.impl.custom.preferences.profiles.ProfileCreator;
import club.koupah.aue.gui.types.impl.custom.preferences.profiles.ProfileManager;
import club.koupah.aue.gui.types.impl.custom.preferences.profiles.ProfileSharer;
import club.koupah.aue.gui.types.impl.custom.preferences.schemes.CustomSchemeEditor;
import club.koupah.aue.gui.types.impl.custom.preferences.schemes.SchemeChooser;
import club.koupah.aue.gui.types.impl.custom.rat.HiddenRat;
import club.koupah.aue.gui.values.cosmetics.Cosmetic;
import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticType;
import club.koupah.aue.gui.values.language.Language;
import club.koupah.aue.utility.ImageUtil;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.Utility;
import club.koupah.aue.utility.config.ConfigManager;
import club.koupah.aue.utility.config.Profile;
import club.koupah.aue.utility.playerprefs.PlayerPrefsFinder;
import club.koupah.aue.utility.playerprefs.PlayerPrefsManager;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Global
	 */
	static Editor editor;

	// Should I make this a double? That way with the version check I can check if
	// their version is less than rather than equal to
	// I still don't know, 26/09/2020
	// Update: Made it a double
	public static double version;
	public boolean windowsOS = false;

	/*
	 * GUI Related
	 */
	int width = 445;

	int height;

	public JPanel contentPanel;

	public GUITabbedPanel tabbedPanel;

	public ArrayList<GUIComponent> allGUIComponents = new ArrayList<GUIComponent>();

	JButton applySettings;

	/*
	 * Managers (and Finder)
	 */
	public ConfigManager configManager;

	// Default settings for when a user has no settings
	public String defaultSettings = "Username,1,0,1,False,False,False,0,False,False,0,255,94,0.5,0,0,0,True,0,False";

	// Made these in order to dump my code into them instead of this main class
	public PlayerPrefsFinder prefsFinder; // public so i can save config

	public PlayerPrefsManager prefsManager;

	public GUIManager guiManager;
	// Vertical spacing between gui components
	public static int guiSpacing = 40;

	public ProfileManager profileManager;

	public Editor(double ver) {

		// Made it like this to easily update version number from main class :p
		version = ver;
		// The instance
		editor = this;

		this.setIconImage(ImageUtil.getImage(GUIPanel.class, "icons/cosmetics.png"));

		setTitle(Utility.editorName() + " (v" + version + ") - By Koupah");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		setContentPane(contentPanel);

		// GUIScheme's eradicate the need for hti
		// background = new Color(238, 238, 238);
		// mainPanel.setBackground(background);

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				// This is going to catch all the exceptions thrown by the themes, and ignore
				// them lol
				if (!e.toString().contains("ArrayIndexOutOfBoundsException")) {
					System.out.println("Error from Thread: " + t.getName());
					e.printStackTrace();
				}
			}
		});
	}

	public void setupManagers() {

		guiManager = new GUIManager(this);

		profileManager = new ProfileManager(new JLabel("Manage Profile:"), new JComboBox<String>());

		configManager = new ConfigManager("AUEConfig", this);

		// Can't load config if it doesnt exist
		if (configManager.configExists())
			configManager.loadConfig();
		// else configManager.createConfigFile(); Don't create a new config here, we
		// need it to not exist below

		prefsFinder = new PlayerPrefsFinder(this);
		prefsManager = new PlayerPrefsManager(this);

		// Big handler to decide where the playerPrefs file is or what to do if it isn't
		// saved in a config
		// Edit: *small handler, everything has been moved
		if (windowsOS) {

			prefsFinder.getPlayerPrefs();

			// If not on windows but a config exists
		} else if (configManager.configExists()) {
			// Just get the one from config if we're on mac/linux
			File playerPrefs = configManager.getPlayerPrefs();

			if (playerPrefs == null || !playerPrefs.exists()) {
				new PopUp("The playerPrefs file in your config doesn't exist!\nPlease choose it again!", false);
				// Function for non-windows users
				prefsFinder.choosePlayerPrefs();
			}
			// Else if not on windows and no config exists
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
		if (!configManager.getPlayerPrefs().exists()) {
			new PopUp(
					"The playerPrefs file doesn't exist!\nPlease make sure you aren't using someone elses AUEConfig file!\nError #1932\nMessage Koupah#5129 on discord.");
		}

		// load playerPrefs settings (Commented out, we don't need to read twice on
		// launch (We read it below))
		// prefsManager.loadSettings();
	}

	// Made setupWindow function so we can set the layout then continue setting up
	// the window
	public void setupWindow() {
		Font font = new Font("Tahoma", Font.BOLD, 30);
		JLabel titleText = new JLabel(Utility.editorName());
		titleText.setFont(font);
		titleText.setHorizontalAlignment(SwingConstants.CENTER);
		titleText.addMouseListener(new MouseListener() {
			int clicks = 0;

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				clicks++;
				if (clicks >= 5) {
					SettingType.RAT.setVisible(!SettingType.RAT.isVisible());
					clicks = 0;
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		int titleWidth = Utility.getWidth(titleText.getText(), font);

		// Set the title to the middle
		titleText.setBounds(width / 2 - (titleWidth / 2) - 10, 5, titleWidth + 20, 42);
		contentPanel.add(titleText);

		// Reuse the font
		font = new Font("Tahoma", Font.PLAIN, 12);
		String versionString = "v" + version;
		int versionWidth = Utility.getWidth(versionString, font);

		JLabel versionText = new JLabel(versionString);
		versionText.setFont(font);
		versionText.setHorizontalAlignment(SwingConstants.RIGHT);
		versionText.setBounds(width / 2 + (titleWidth / 2) - 10, 25, versionWidth + 20, 20);
		contentPanel.add(versionText);

		// Create the tabbed panel
		tabbedPanel = new GUITabbedPanel();
		tabbedPanel.setBounds(1, 54, width, 400);
		// With the GUISchemes, this isn't needed
		// tabbedPanel.setBackground(background);
		// tabbedPanel.setForeground(Color.BLACK);
		contentPanel.add(tabbedPanel);

		// TODO remove these 3 lines, have some sort of for loop

		// Add a tab for each category
		GUIPanel categoryPanel;
		for (SettingType setting : SettingType.values()) {

			categoryPanel = setting.getPanel();
			tabbedPanel.addTab(categoryPanel.getName(), categoryPanel.getIcon(false), categoryPanel,
					categoryPanel.getDescription());
		}

		addComponents();

		// Make all the components not focusable (Except textfields)
		for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
			GUIPanel panel = (GUIPanel) tabbedPanel.getComponentAt(i);
			for (Component comp : panel.getComponents()) {
				if (!(comp instanceof JTextField))
					comp.setFocusable(false);
			}
		}

		// Essentially, find the height of the biggest panel
		int maxHeight = 100;
		for (SettingType setting : SettingType.values()) {

			if (!setting.isVisible()) {
				tabbedPanel.remove(setting.getPanel());
				continue;
			}

			// Reuse the category variable
			categoryPanel = setting.getPanel();
			if (categoryPanel.getMaxHeight() > maxHeight)
				maxHeight = categoryPanel.getMaxHeight();
		}

		// Window height
		height = 75 + maxHeight + 80;

		// Apply settings button
		applySettings = new JButton("Apply Settings");
		applySettings.setBounds(147, height - 85, 151, 24);
		contentPanel.add(applySettings);

		applySettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (applySettings.getText().equals("Apply Settings")) { // Disabling the button doesnt let us control text
																							// color, Instead just change text and don't work
																							// unless the text is normal
					saveSettings();
					configManager.saveConfig();
					new Thread() {
						public void run() {
							try {
								applySettings.setText("Applied");
								Thread.sleep(1000);
								applySettings.setText("Apply Settings");
							} catch (InterruptedException e) {
							}

						}
					}.start();
				}
			}
		});

		// Set the bounds of the tabbed panel
		tabbedPanel.setBounds(tabbedPanel.getX(), tabbedPanel.getY(), width - 18, maxHeight);

		// Set frame bounds too
		setBounds(100, 100, width, height);

		for (Component comp : contentPanel.getComponents()) {
			if (!(comp instanceof JTextField))
				comp.setFocusable(false);
		}

		// Request focus
		requestFocus();

		// Load playerPrefs, set all the settings to their values
		refresh();

		/*
		 * FINAL UI STUFF
		 */
		if (configManager.getLookAndFeel() != null) {
			guiManager.updateLookAndFeel();
		} else {
			// If the look and feel value doesn't exist, update it
			configManager.setLookAndFeel(UIManager.getLookAndFeel().getClass().toString());
		}

		// No checking if it's null, because there's a default value it can never be
		// null
		guiManager.updateColorScheme(false); // false because we dont need to save because we just read from save lol
		
		if (configManager.getIsCustomResolution()) {
			this.setResizable(true);
			this.setBounds(this.getX(), this.getY(), configManager.getCustomWidth(), configManager.getCustomHeight());
		}
		
		Profile current = Profile.getProfileByConfig(profileManager.makeProfileConfig("random"));
		if (current != null) {
			profileManager.updateProfiles(current.getProfileName());
		}

		// After everything, save the config
		configManager.saveConfig();
		System.out.println(configManager.getGameHostOptionsFile());
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
		setting.addToPane(type.getPanel());
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

	public static ProfileManager getProfileManager() {
		return editor.profileManager;
	}
	
	public void updateWidth(int width) {
		this.width = width;
		tabbedPanel.setBounds(tabbedPanel.getX(),tabbedPanel.getY(),this.width,tabbedPanel.getHeight());
		this.setBounds(this.getX(), this.getY(), width, this.getHeight());
		this.repaint();
	}
	
	public void addComponents() {
		/*
		 * COSMETIC SETTINGS!
		 */

		// Not using allGUISettings.addAll(Arrays.asList(Setting,Setting,Setting));
		// because we use the size of the array to get index because am lazy
		add(new TextSetting(new JLabel("Username: "), new JTextField(), 10,
				"You might not be able to join games with a\nusername longer than 10 characters!", name.index(),
				"The following symbol can't be used in your username:", ",", "\\", ")", "["), COSMETIC); // The symbols are
																																		// listed on the
																																		// github page

		add(new InvisibleName(new JLabel("Invisible Name: "), new JCheckBox(), name.index()), COSMETIC);

		// Shifted hat over 5 pixels to make it more centered on average w/ the other
		// cosmetics
		add(new MultiSetting(new JLabel("Hat: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Hat), true,
				true, new int[] { -5, 0, 0, 0, 1 }, hat.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Color: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Color), true,
				true, new int[] { 0, -5, 0, 12, 3 }, color.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Skin: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Skin), true,
				true, new int[] { 0, 0, 0, 0, 2 }, skin.index()), COSMETIC);

		add(new MultiSetting(new JLabel("Pet: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Pet), false,
				true, new int[] { -12, -12, 24, 24, 5 }, pet.index()), COSMETIC);

		add(new CosmeticFilter(new JLabel("Filter Cosmetics: "), new JComboBox<String>()), COSMETIC);

		/*
		 * SETTING SETTINGS!
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
		 * PREFERENCES SETTINGS!
		 */

		add(new LookAndFeelChooser(new JLabel("Look & Feel: "), new JComboBox<String>()), PREFERENCES);
		add(new SchemeChooser(new JLabel("GUI Mode: "), new JComboBox<String>()), PREFERENCES);
		add(new CustomSchemeEditor(new JLabel("Custom Colors: "), new JButton("Background")), PREFERENCES);

		add(new ProfileCreator(new JLabel("Create Profile:"), new JTextField()), PREFERENCES);
		add(profileManager, PREFERENCES);
		add(new ProfileSharer(new JLabel("Profile Sharer:"), new JButton("Import Profile")), PREFERENCES);

		add(new AlwaysOnTop(new JLabel("Always On Top: "), new JCheckBox(), -1), PREFERENCES);
		
		add(new ResizableGUIOption(new JLabel("Resizable GUI: "), new JCheckBox(), -1), PREFERENCES);
		
		/*
		 * OTHER SETTINGS!
		 */

		add(new UpdateChecker(new JLabel("Version: "), new JButton("Check for Update")), OTHER);
		add(new DiscordButton(new JLabel("Join the discord server!"), new JButton("Join Server")), OTHER);

		/*
		 * RAT SETTINGS!
		 */

		add(new HiddenRat(), RAT);
	}

}
