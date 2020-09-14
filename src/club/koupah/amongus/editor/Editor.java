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
import javax.swing.border.EmptyBorder;

import club.koupah.amongus.editor.cosmetics.Colors;
import club.koupah.amongus.editor.cosmetics.Cosmetic;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Hats;
import club.koupah.amongus.editor.cosmetics.Pets;
import club.koupah.amongus.editor.cosmetics.Skins;
import club.koupah.amongus.editor.guisettings.CheckboxSetting;
import club.koupah.amongus.editor.guisettings.MultiSetting;
import club.koupah.amongus.editor.guisettings.Setting;
import club.koupah.amongus.editor.guisettings.SliderSetting;
import club.koupah.amongus.editor.guisettings.TextSetting;
import club.koupah.amongus.editor.settings.Language;

public class Editor extends JFrame {

	/**
	 * Eclipse be like br0 make this
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/*
	 * Values:
	 * 
	 * 1st: name 2nd: Controls 0 Mouse 1 Mouse and Keyboard 3rd: Preferred color
	 * 4th: Unknown 5th: Unknown 6th: Unknown 7th: Unknown 8th: Unknown 9th: Unknown
	 * 10th: Unknown 11th: Hat 12th: SFX volume 0-255 13th: Music volume 0-255 14th:
	 * Unknown 15th: Unknown 16th: Skin (costume) 17th: pet 18th: censor chat
	 * boolean 19th: language 0 english 20th: vsync
	 */

	// Colors
	/*
	 * Negative numbers give random colors every launch 0: red 1: dark blue 2: dark
	 * green 3: pink 4: orange 5: yellow 6: black 7: white 8: purple 9: brown 10:
	 * light blue 11: light green 12: Secret green color and weird glitches (Chat
	 * doesn't work in the lobby) (No name at meetings/chat and appear red in
	 * meetings too)
	 * 
	 */

	// Skins (Costumes)
	/*
	 * 10: orange karate 11: weird blue suit 12: construction worker (hi vis) 13:
	 * overalls 14: puffer jacket 15: Indiana Jones
	 * 
	 */

	// Hats
	/*
	 * 68 in game 26 below 20: Snowman 21: Antlers 22: Christmas Lights 23: Santa
	 * Hat 24: Christmas Tree 25: Present Box 26: Candy Canes 27: Elf Hat & Ears
	 * 
	 * 45: Headphones 46: Gas Mask
	 * 
	 * 48: Hat & Glasses (Disguise)
	 * 
	 * 61: cat hat 62: bat wings 63: devil horns 64: mohawk 65: pumpkin 66: spooky
	 * paper bag 67: witch hat 68: wolf ears 69: pirate hat 70: Beak Mask (Plague
	 * doctor) 71: sword in head 72: jason mask 73: miner hat 74: blue beanie 75:
	 * icy cowboy hat 76 - 93: in game 94: none
	 * 
	 */

	// Name
	/*
	 * # - Banned from rooms \ - Banned from rooms ) - Banned from rooms [ - Banned
	 * from rooms , - Breaks format
	 * 
	 * Server refuses names longer than 10 characters
	 */

	// PETS
	/*
	 * 0 No pet 1 Green Alien 2 Mini Me (Mini Crewmate) 3 Blue Alien Dog 4 Stickmin
	 * (Male) 5 Hamster Pet (In Ball) 6 Robot (Crewmate) 7 UFO 8 Stickmin (Female) 9
	 * Bed Crab 1 10 Bed Crab 2 (Crab?)
	 */

	public static ArrayList<Setting> allGUISettings = new ArrayList<Setting>();

	String[] currentSettings;

	String[] newSettings;

	static File playerPrefs;

	static String directory;

	public static void main(String[] args) {

		directory = System.getProperty("user.home") + "\\AppData\\LocalLow\\Innersloth\\Among Us\\playerPrefs";

		playerPrefs = new File(directory);

		if (!playerPrefs.exists()) {
			new PopUp("You don't seem to have the game installed?\nIf you do, try running the game then trying again.",
					true);
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
	}

	/**
	 * Create the frame.
	 */

	JButton applySettings;
	/*
	 * Values:
	 * 
	 * 2nd: Controls 0 Mouse 1 Mouse and Keyboard 3rd: Preferred color 4th: Unknown
	 * 5th: Unknown 6th: Unknown 7th: Unknown 8th: Unknown 9th: Unknown 10th:
	 * Unknown 11th: Hat 12th: SFX volume 0-255 13th: Music volume 0-255 14th:
	 * Unknown 15th: Unknown 16th: Skin (costume) 17th: pet 18th: censor chat
	 * boolean 19th: language 0 english 20th: vsync
	 */
	int nameIndex = 0;
	int hatIndex = 10;
	int petIndex = 16;
	int skinIndex = 15;
	int colorIndex = 2;
	int languageIndex = 18;
	int vsyncIndex = 19;
	int censorChatIndex = 17;
	int sfxIndex = 11;
	int musicIndex = 12;
	int controlIndex = 1;

	public Editor() {
		setTitle("Among Us Editor - By Koupah");
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

		for (Setting setting : allGUISettings) {
			setting.addToPane(contentPane);
		}
		
		//Just cause bro, don't @ me
		int min =  75 + (allGUISettings.size() * 31) + 80;
		
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
				for (Setting setting : allGUISettings) {
					newSettings[setting.getSettingIndex()] = setting.getValue(false);
				}
				saveSettings();
				refresh();
			}
		});

		refresh();
	}

	/*
	 * Values:
	 * 
	 * 1st: name 2nd: Controls 0 Mouse 1 Mouse and Keyboard 3rd: Preferred color
	 * 4th: Unknown 5th: Unknown 6th: Unknown 7th: Unknown 8th: Unknown 9th: Unknown
	 * 10th: Unknown 11th: Hat 12th: SFX volume 0-255 13th: Music volume 0-255 14th:
	 * Unknown 15th: Unknown 16th: Skin (costume) 17th: pet 18th: censor chat
	 * boolean 19th: language 0 english 20th: vsync
	 */

	public void add(Setting setting) {
		allGUISettings.add(setting);
	}

	public void refresh() {
		loadSettings();
		Setting.updateAllLabels(currentSettings);
		Setting.updateAllComponents();
	}

	public void saveSettings() {
		try (FileWriter fileWriter = new FileWriter(playerPrefs)) {
			fileWriter.write(String.join(",", newSettings));
			System.out.println("Saved the following line to the settings file:\n" + String.join(",", newSettings));
			fileWriter.close();
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
				new PopUp(
						"Error loading settings (Potentially newer version?)\nScreenshot the following and send it to Koupah#5129 (Discord)\n"
								+ line,
						true);

			// Added this later, realized I never set it anywhere :p
			newSettings = currentSettings;

		} catch (FileNotFoundException e) {
			// Exception handling
		} catch (IOException e) {
			// Exception handling
		}
	}
}
