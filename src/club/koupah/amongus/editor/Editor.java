package club.koupah.amongus.editor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import club.koupah.amongus.editor.guisettings.GUIComponent;
import club.koupah.amongus.editor.guisettings.Setting;
import club.koupah.amongus.editor.guisettings.types.CheckboxSetting;
import club.koupah.amongus.editor.guisettings.types.CosmeticFilter;
import club.koupah.amongus.editor.guisettings.types.InvisibleName;
import club.koupah.amongus.editor.guisettings.types.MultiSetting;
import club.koupah.amongus.editor.guisettings.types.SliderSetting;
import club.koupah.amongus.editor.guisettings.types.TextSetting;
import club.koupah.amongus.editor.settings.cosmetics.Colors;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic;
import club.koupah.amongus.editor.settings.cosmetics.Hats;
import club.koupah.amongus.editor.settings.cosmetics.Pets;
import club.koupah.amongus.editor.settings.cosmetics.Skins;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.settings.language.Language;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	private JLayeredPane contentPane;

	public static ArrayList<GUIComponent> allGUIComponents = new ArrayList<GUIComponent>();

	private String[] currentSettings;

	private String[] newSettings;

	private File playerPrefs;

	// Should I make this a double? That way with the version check I can check if
	// their version is less than rather than equal to
	private static String version = "1.25";

	static String name = "Among Us Editor";

	static Thread versionCheck;

	// Spacing between gui components
	public static int scale = 40;

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	static String desiredLookAndFeel = "WindowsLookAndFeel";

	static boolean windows = false;

	static String lookAndFeel;
	
	//This config file is only for NON windows users
	File config = new File("AUEConfig");

	public static void main(String[] args) {

		// Run this first, before me do any GUI stuff
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getClassName().contains(desiredLookAndFeel)) {
				lookAndFeel = info.getClassName();
				windows = true;
				break;
			}
		}
		if (windows) {
			try {
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				new PopUp("Your system reported itself as having the Windows Look & Feel but didn't work!\n"
						+ e1.getMessage(), true);
			}
		} else {
			/*
			 * 
			 * Don't set any look & feel, Sorry non-windows users.
			 * You'll have to deal with Java's ugly L&F until I write up my own.
			 * (Unless someone wants to provide a nice Linux/Mac L&F :P)
			 * 
			 */
		}

		// Idk how to get them to initialize their values cause am big noob
		Hats.values();
		Pets.values();
		Skins.values();
		Colors.values();

		// Lol swing gui maker go brrrrr
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * 
		 * VERSION CHECK FOR PEOPLE WHO DOWNLOAD STRAIGHT FROM A YOUTUBE VIDEO ETC Feel
		 * free to remove this if you're compiling yourself!
		 * 
		 */
		versionCheck = new Thread() {
			public void run() {
				try {
					URL website = new URL("https://raw.githubusercontent.com/Koupah/Among-Us-Editor/master/version");
					URLConnection connection = website.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String latest = in.readLine();
					if (!latest.equals(version)) {
						new PopUp("Your Among Us Editor is outdated!\nYou're on version " + version + " and " + latest
								+ " is the latest.\nLatest version can be downloaded from: https://github.com/Koupah/Among-Us-Editor\n\nYou do not HAVE to update, it is optional!",
								false);
					}
					in.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					new PopUp("Couldn't check if this is the latest version\nFeel free to close this message!", false);
				}
				try {
					finalize();
				} catch (Throwable e) {
				}
			}
		};

		versionCheck.start();
	}

	/**
	 * Create the frame.
	 */

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

		// Not using allGUISettings.addAll(Arrays.asList(Setting,Setting,Setting));
		// because we use the size of the array to get index because am lazy

		add(new TextSetting(new JLabel("Username: "), new JTextField(), nameIndex));

		add(new InvisibleName(new JLabel("Invisible Name: "), new JCheckBox(), nameIndex));

		add(new MultiSetting(new JLabel("Hat: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Hat), true,
				true, new int[] { 0, 0, 0, 0, 1 }, hatIndex));

		add(new MultiSetting(new JLabel("Color: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Color),
				true, true, new int[] { 0, -5, 0, 12, 3 }, colorIndex));

		add(new MultiSetting(new JLabel("Skin: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Skin), true,
				true, new int[] { 0, 0, 0, 0, 2 }, skinIndex));

		add(new MultiSetting(new JLabel("Pet: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Pet), false,
				true, new int[] { 0, 0, 0, 0, 5 }, petIndex));

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
				saveSettings();
				refresh();
			}
		});
		
		
		//Big handler to decide where the playerPrefs file is or what to do if it isn't saved in a config
		if (windows) {
			// I moved this stuff out of the main method so I don't need to make stuff
			// static


			playerPrefs = new File(System.getProperty("user.home") + "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs");

			if (!playerPrefs.exists()) {
				new PopUp(
						"You don't seem to have the game installed?\nIf you do, try running the game then trying again.",
						true);
			}

		} else if (!config.exists()) {
			// I'll probably get around to looping through all files in a chosen directory
			// to find the playerPrefs file,
			// But I don't know
			new PopUp("Please navigate to the playerPrefs file for Among Us\nThis will save in a file named \""
					+ config.getName() + "\"", false);

			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File("playerPrefs"));
			chooser.setMultiSelectionEnabled(false);

			switch (chooser.showOpenDialog(this)) {

			case JFileChooser.APPROVE_OPTION: {
				playerPrefs = chooser.getSelectedFile();

				if (!playerPrefs.exists()) {
					new PopUp("The playerPrefs file you selected, doesn't exist? Exiting.", true);
				}

				try {
					config.createNewFile();
				} catch (IOException e) {
					new PopUp(
							"Notice, couldn't create the config file.\nYou'll have to reselect the playerPrefs file next time you launch.",
							false);
					break;
				}

				try (BufferedWriter bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(config), "UTF-8"))) {
					bufferedWriter.write(playerPrefs.getAbsolutePath());
					bufferedWriter.close();
				} catch (IOException e) {
					new PopUp(
							"Failed to save the playerPrefs file location to AUEConfig.\nContinuing but you'll have to reselect it next launch!",
							false);
					break;
				}

				break;
			}

			case JFileChooser.CANCEL_OPTION: {
			}
			case JFileChooser.ERROR_OPTION: {
				new PopUp("You didn't seem to choose a file? Exiting.");
			}
			}

		} else if (config.exists()) {
			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(config), "UTF-8"))) {

				playerPrefs = new File(bufferedReader.readLine());

			} catch (IOException e) {
				new PopUp("Couldn't read the AUEConfig file! Exiting.");
			}

			if (!playerPrefs.exists()) {
				new PopUp("The file directory in the AUEConfig file points to a playerPrefs file that doesn't exist!\n"
						+ "Please check the config file or delete it to rechoose the directory!");
			}
			
		}

		refresh();
	}

	// Made this cause it's smaller than writing allGUISettings.add()
	public void add(GUIComponent setting) {
		allGUIComponents.add(setting);
	}

	public void refresh() {
		loadSettings();
		Setting.setCurrentSettings(currentSettings);

		for (GUIComponent guicomponent : allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting) guicomponent;
				setting.updateLabel();
				setting.updateComponent();

			}
		}
	}

	public void saveSettings() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(playerPrefs), "UTF-8"))) {
			bufferedWriter.write(String.join(",", newSettings));
			bufferedWriter.close();
		} catch (IOException e) {
			new PopUp("Error writing to file!\n" + e.getMessage(), true);
		}
	}

	public void loadSettings() {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(playerPrefs), "UTF-8"))) {

			String line = bufferedReader.readLine();
			System.out.println("Read following line from settings file: \n" + line);

			// This is unnecessary, playerPrefs file is 1 line
			// while(line != null) { line = bufferedReader.readLine(); }

			bufferedReader.close();
			if (line.contains(",") && line.split(",").length == 20)
				currentSettings = line.split(",");
			else
				new PopUp(
						"Error loading settings (Potentially newer version?)\nScreenshot the following and send it to Koupah#5129 (Discord)\n"
								+ line,
						true);

			newSettings = currentSettings;

		} catch (FileNotFoundException e) {
			new PopUp(
					"Error loading settings, file doesn't exist?\nAre you sure you have the game and have run it before?",
					true);
		} catch (IOException e) {
			new PopUp("Error reading playerPrefs!\n" + e.getMessage(), true);
		}
	}
}
