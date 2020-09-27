package club.koupah.amongus.editor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import club.koupah.amongus.editor.guisettings.GUIComponent;
import club.koupah.amongus.editor.guisettings.Setting;
import club.koupah.amongus.editor.guisettings.types.CheckboxSetting;
import club.koupah.amongus.editor.guisettings.types.CosmeticFilter;
import club.koupah.amongus.editor.guisettings.types.InvisibleName;
import club.koupah.amongus.editor.guisettings.types.MultiSetting;
import club.koupah.amongus.editor.guisettings.types.SliderSetting;
import club.koupah.amongus.editor.guisettings.types.TextSetting;
import club.koupah.amongus.editor.playerprefs.PlayerPrefsFinder;
import club.koupah.amongus.editor.playerprefs.PlayerPrefsManager;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.settings.language.Language;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	private JLayeredPane contentPane;

	public ArrayList<GUIComponent> allGUIComponents = new ArrayList<GUIComponent>();

	public String[] currentSettings;

	public String[] newSettings;

	public File playerPrefs;

	// Should I make this a double? That way with the version check I can check if
	// their version is less than rather than equal to
	// I still don't know, 26/09/2020

	// Update: Made it a double
	static double version = 1.35;
	static boolean preRelease;
	static boolean outdated;

	static String name = "Among Us Editor";

	static Thread updateCheck;

	// Spacing between gui components
	public static int scale = 40;

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	String desiredLookAndFeel = "WindowsLookAndFeel";

	boolean windows = false;

	// This really shouldn't start blank, but :shrug:
	String lookAndFeel;

	// This config file is only for NON windows users
	public File config = new File("AUEConfig");

	// Made these in order to dump my code into them instead of this main class
	PlayerPrefsFinder prefsFinder;
	PlayerPrefsManager prefsManager;

	static Editor editor;

	JButton applySettings;

	int nameIndex = 0;
	int controlIndex = 1;
	int colorIndex = 2;
	int hatIndex = 10;
	int sfxIndex = 11;
	int musicIndex = 12;
	int petIndex = 16;
	int skinIndex = 15;
	int censorChatIndex = 17;
	int languageIndex = 18;
	int vsyncIndex = 19;

	public Editor() {

		editor = this;

		setTitle(name + " (" + version + ") - By Koupah");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel topText = new JLabel("Among Us Editor");
		topText.setFont(new Font("Tahoma", Font.PLAIN, 30));
		topText.setHorizontalAlignment(SwingConstants.CENTER);
		topText.setBounds(0, 11, 439, 42);
		contentPane.add(topText);

	}

	// Made setupWindow function so we can set the layout then continue setting up
	// the window
	void setupWindow() {
		// Not using allGUISettings.addAll(Arrays.asList(Setting,Setting,Setting));
		// because we use the size of the array to get index because am lazy

		add(new TextSetting(new JLabel("Username: "), new JTextField(), nameIndex));

		add(new InvisibleName(new JLabel("Invisible Name: "), new JCheckBox(), nameIndex));
		
		//Shifted hat over 5 pixels to make it more centered on average w/ the other cosmetics
		add(new MultiSetting(new JLabel("Hat: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Hat), true,
				true, new int[] { -5, 0, 0, 0, 1 }, hatIndex));

		add(new MultiSetting(new JLabel("Color: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Color),
				true, true, new int[] { 0, -5, 0, 12, 3 }, colorIndex));

		add(new MultiSetting(new JLabel("Skin: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Skin), true,
				true, new int[] { 0, 0, 0, 0, 2 }, skinIndex));

		add(new MultiSetting(new JLabel("Pet: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Pet), false,
				true, new int[] { -12, -12, 24, 24, 5 }, petIndex));

		add(new CosmeticFilter(new JLabel("Filter Cosmetics: "), new JComboBox<String>()));

		add(new MultiSetting(new JLabel("Language: "), new JComboBox<String>(), Language.getAllLanguagesString(), false,
				languageIndex));

		add(new MultiSetting(new JLabel("Controls: "), new JComboBox<String>(),
				Arrays.asList("Mouse Only", "Keyboard and Mouse"), false, controlIndex));

		add(new CheckboxSetting(new JLabel("VSync: "), new JCheckBox(), vsyncIndex));

		add(new CheckboxSetting(new JLabel("Censor Chat: "), new JCheckBox(), censorChatIndex));

		add(new SliderSetting(new JLabel("Sounds Effects Volume: "), new JSlider(), 0, 255, sfxIndex));

		add(new SliderSetting(new JLabel("Music Volume: "), new JSlider(), 0, 255, musicIndex));

		for (GUIComponent setting : allGUIComponents) {
			setting.addToPane(contentPane);
		}

		// Just cause bro, don't @ me
		int min = 75 + (allGUIComponents.size() * scale) + 80;

		applySettings = new JButton("Apply Settings");
		applySettings.setBounds(147, min - 75, 151, 25);
		contentPane.add(applySettings);

		// Set window bounds too
		setBounds(100, 100, 445, min);

		for (Component comp : contentPane.getComponents()) {
			if (!(comp instanceof JTextField))
				comp.setFocusable(false);
		}
		getRootPane().requestFocus();

		applySettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				for (GUIComponent guicomponent : allGUIComponents) {
					if (guicomponent instanceof Setting) {
						Setting setting = (Setting) guicomponent;

						// This allows InvisibleName and future GUIComponents to avoid impacting
						// settings
						if (setting.getComponentValue(false) != null)
							newSettings[setting.getSettingIndex()] = setting.getComponentValue(false);
					}
				}
				prefsManager.saveSettings(newSettings);
				refresh();
			}
		});

		prefsFinder = new PlayerPrefsFinder(this);
		prefsManager = new PlayerPrefsManager(this);

		// Big handler to decide where the playerPrefs file is or what to do if it isn't
		// saved in a config
		
		//Edit: *small handler, everything has been moved
		if (windows) {

			playerPrefs = prefsFinder.getPlayerPrefs();

		} else if (config.exists()) {
			
			playerPrefs = prefsFinder.loadConfig();
			
		} else if (!config.exists()) {
			
			//Function for non-windows users
			prefsFinder.choosePlayerPrefs();
			
		} else {
			// This shouldn't show up, but if it does, give it a wonky error number so I can
			// search for it
			new PopUp("This error shouldn't occur.\nError #1731");
		}
		refresh();
	}

	// Made this cause it's smaller than writing allGUISettings.add()
	public void add(GUIComponent setting) {
		allGUIComponents.add(setting);
	}

	public void refresh() {
		prefsManager.loadSettings();
		Setting.setCurrentSettings(currentSettings);

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
	
	
	//Moved update check to bottom of class
	void runUpdateCheck() {

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
					String latestVersionString = in.readLine();
					double latestVersion = Double.parseDouble(latestVersionString);

					// Check if the latest version is greater than our version
					outdated = latestVersion > version;

					preRelease = latestVersion < version;

					if (outdated) {
						String info = "";
						String read;
						while ((read = in.readLine()) != null) {
							info += read + "\n";
						}

						String message = "You're on version " + version + " and " + latestVersionString
								+ " is the latest!"
								//If there is info (not just like a space or whatever) then show the message
								+ (info.length() > 2 ? "\n\nVersion " + latestVersionString + ":\n" + info : "");

						// Create a new pop up with the message and allows for opening the new version
						// in browser
						PopUp.downloadPopUp(message, latestVersionString);
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
				
				//Update the title based on version
				editor.setTitle(
						name + " (" + version + (outdated ? " outdated" : (preRelease ? " prerelease" : "")) + ") - By Koupah");

			}
		};

		updateCheck.start();
	}

}
