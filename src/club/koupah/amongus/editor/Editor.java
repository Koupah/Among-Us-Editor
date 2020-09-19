package club.koupah.amongus.editor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import club.koupah.amongus.editor.cosmetics.Colors;
import club.koupah.amongus.editor.cosmetics.Cosmetic;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Hats;
import club.koupah.amongus.editor.cosmetics.Pets;
import club.koupah.amongus.editor.cosmetics.Skins;
import club.koupah.amongus.editor.guisettings.GUIComponent;
import club.koupah.amongus.editor.guisettings.Setting;
import club.koupah.amongus.editor.guisettings.types.CheckboxSetting;
import club.koupah.amongus.editor.guisettings.types.CosmeticFilter;
import club.koupah.amongus.editor.guisettings.types.MultiSetting;
import club.koupah.amongus.editor.guisettings.types.SliderSetting;
import club.koupah.amongus.editor.guisettings.types.TextSetting;
import club.koupah.amongus.editor.settings.Language;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public static ArrayList<GUIComponent> allGUIComponents = new ArrayList<GUIComponent>();

	private String[] currentSettings;

	private String[] newSettings;

	private File playerPrefs;

	private String directory;
	
	//Should I make this a double, that way with the version check I can check if their version is less than rather than equal to
	private static String version = "1.2";
	
	static String name = "Among Us Editor";
	
	static Thread versionCheck;
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			new PopUp("Your system doesn't seem to want to work! Are you on Windows?\n" + e1.getMessage(), true);
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
		 * VERSION CHECK FOR PEOPLE WHO DOWNLOAD STRAIGHT FROM A YOUTUBE VIDEO ETC
		 * Feel free to remove this if you're compiling yourself!
		 * 
		 */
		versionCheck = new Thread() {
			public void run() {
				try {
		        URL website = new URL("https://raw.githubusercontent.com/Koupah/Among-Us-Editor/master/version");
		        URLConnection connection = website.openConnection();
		        BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                    connection.getInputStream()));
		        String latest = in.readLine();
		        if (!latest.equals(version)) {
		        	new PopUp("Your Among Us Editor is outdated!\nYou're on version " + version + " and " + latest + " is the latest.\nLatest version can be downloaded from: https://github.com/Koupah/Among-Us-Editor\n\nYou do not HAVE to update, it is optional!", false);
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

		contentPane = new JPanel();
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

		add(new MultiSetting(new JLabel("Hat: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Hat), true,
				hatIndex));

		add(new MultiSetting(new JLabel("Skin: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Skin), true,
				skinIndex));

		add(new MultiSetting(new JLabel("Pet: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Pet), false,
				petIndex));

		add(new MultiSetting(new JLabel("Color: "), new JComboBox<String>(), Cosmetic.getItems(CosmeticType.Color),
				true, colorIndex));

		add(new MultiSetting(new JLabel("Language: "), new JComboBox<String>(), Language.getAllLanguagesString(), false,
				languageIndex));

		add(new MultiSetting(new JLabel("Controls: "), new JComboBox<String>(),
				Arrays.asList("Mouse Only", "Keyboard and Mouse"), false, controlIndex));

		add(new CheckboxSetting(new JLabel("VSync: "), new JCheckBox(), vsyncIndex));

		add(new CheckboxSetting(new JLabel("Censor Chat: "), new JCheckBox(), censorChatIndex));

		add(new SliderSetting(new JLabel("Sounds Effects Volume: "), new JSlider(), 0, 255, sfxIndex));

		add(new SliderSetting(new JLabel("Music Volume: "), new JSlider(), 0, 255, musicIndex));

		add(new CosmeticFilter(new JLabel("Filter Cosmetics: "), new JComboBox<String>()));
		
		for (GUIComponent setting : allGUIComponents) {
			setting.addToPane(contentPane);
		}
	
		
		
		
		
		//Just cause bro, don't @ me
				int min =  75 + (allGUIComponents.size() * 31) + 80;
				
				applySettings = new JButton("Apply Settings");
				applySettings.setBounds(147, min-60, 151, 25);
				contentPane.add(applySettings);
				
				//Set window bounds too
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
						Setting setting = (Setting)guicomponent;
						newSettings[setting.getSettingIndex()] = setting.getValue(false);
					}
				}
				saveSettings();
				refresh();
			}
		});
		
		

		// I moved this stuff out of the main method so I don't need to make stuff static
		directory = System.getProperty("user.home") + "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs";

		playerPrefs = new File(directory);

		if (!playerPrefs.exists()) {
			new PopUp("You don't seem to have the game installed?\nIf you do, try running the game then trying again.",
					true);
		}
		
		refresh();
	}
	
	//Made this cause it's smaller than writing allGUISettings.add()
	public void add(GUIComponent setting) {
		allGUIComponents.add(setting);
	}

	public void refresh() {
		loadSettings();
		Setting.setCurrentSettings(currentSettings);
		
		for (GUIComponent guicomponent : allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting)guicomponent;
				setting.updateLabel();
				setting.updateComponent();

			}
		}
	}

	public void saveSettings() {
		try (FileWriter fileWriter = new FileWriter(playerPrefs)) {
			fileWriter.write(String.join(",", newSettings));
			fileWriter.close();
			System.out.println("Saved the following line to the settings file:\n" + String.join(",", newSettings));
		} catch (IOException e) {
			new PopUp("Error! " + e.getMessage(), true);
		}
	}

	public void loadSettings() {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(playerPrefs))) {

			String line = bufferedReader.readLine();
			System.out.println("Read following line from settings file: \n" + line);
			
			// This is unnecessary, playerPrefs file is 1 line
			// while(line != null) { line = bufferedReader.readLine(); }
			
			bufferedReader.close();
			if (line.contains(",") && line.split(",").length == 20)
				currentSettings = line.split(",");
			else
				new PopUp("Error loading settings (Potentially newer version?)\nScreenshot the following and send it to Koupah#5129 (Discord)\n"
								+ line, true);


			newSettings = currentSettings;

		} catch (FileNotFoundException e) {
			new PopUp("Error loading settings, file doesn't exist?\nAre you sure you have the game and have run it before?", true);
		} catch (IOException e) {
			new PopUp("Error! " + e.getMessage(), true);
		}
	}
}
