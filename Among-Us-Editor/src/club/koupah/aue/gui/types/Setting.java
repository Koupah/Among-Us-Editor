package club.koupah.aue.gui.types;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.cosmetics.Colors;
import club.koupah.aue.gui.settings.cosmetics.Cosmetic;
import club.koupah.aue.gui.settings.cosmetics.Cosmetic.CosmeticType;
import club.koupah.aue.gui.settings.language.Language;
import club.koupah.aue.utility.PopUp;

public class Setting extends GUIComponent {

	int settingIndex;

	static String[] currentSettings;

	public Setting(JLabel label, JComponent component, int settingIndex) {
		super(label,component);
		this.settingIndex = settingIndex;
	}

	public Setting(JLabel label, JComboBox<String> component) {
		this(label, component, -1);
	}

	public void updateLabel() {
		System.out.println("Update Labels function not overriden.");
	}

	public void updateComponent() {
		System.out.println("Update Component function not overriden.");
	}

	public String getComponentValue(boolean fromLabel) {
		if (fromLabel)
			return this.label.getText().split(getLabelText())[1];

		return getProperValue();
	}

	public String getProperValue() {
		System.out.println("Something didn't override getProperValue()!");
		return "Error";
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected String getValueToSave() {
		switch (settingIndex) {
		case (1): {
			return String.valueOf(((JComboBox<String>)component).getSelectedIndex());
		}
		case (2): {
			String id = Cosmetic.getIDbyName(CosmeticType.Color, (String) ((JComboBox<String>)component).getSelectedItem());
			//I should just make the above return the currentSetting if it had an error finding it, probably pass it the settingIndex
			if (id.equals("ErrorFinding"))
				return currentSettings[settingIndex];
			
			return id;
		}
		case (10): {
			String id = Cosmetic.getIDbyName(CosmeticType.Hat, (String) ((JComboBox<String>)component).getSelectedItem());
			if (id.equals("ErrorFinding"))
				return currentSettings[settingIndex];
			return id;
		}
		case (15): {
			String id = Cosmetic.getIDbyName(CosmeticType.Skin, (String) ((JComboBox<String>)component).getSelectedItem());
			if (id.equals("ErrorFinding"))
				return currentSettings[settingIndex];
			return id;
		}
		case (16): {
			String id = Cosmetic.getIDbyName(CosmeticType.Pet, (String) ((JComboBox<String>)component).getSelectedItem());
			if (id.equals("ErrorFinding"))
				return currentSettings[settingIndex];
			return id;
		}
		case (18): {
			String id = String.valueOf(Language.getLanguageIDbyName((String) ((JComboBox<String>)component).getSelectedItem()));
			if (id.equals("69")) {
				System.out.println("There must've be an error getting the language ID, language was " + (String) ((JComboBox<String>)component).getSelectedItem());
			}
			return id;
		}
		}
		//I should've done this from the start, no one wants a messed up playerPrefs file
		new PopUp("Error in save value??", true);
		return "ErrorInSaveValue";
	}
	
	boolean fortegreenWarning = true;
	@SuppressWarnings("unchecked")
	protected String getCurrentSettingValue() {
		switch (settingIndex) {
		//name
		case (0): {
			return currentSettings[settingIndex];
		}
		//movement type
		case (1): {
			return String.valueOf(((JComboBox<String>)component).getItemAt(Integer.parseInt(currentSettings[settingIndex])));
		}
		//Color
		case (2): {
			//Warn people when saving the Fortegreen color about it's issues
			if (Editor.getInstance().isVisible() && Integer.valueOf(currentSettings[settingIndex]) >= Colors.Fortegreen.getID() && fortegreenWarning) {
				fortegreenWarning = false;
				new PopUp("Using the Fortegreen color can cause the following:\n-Hats not showing\n-Skins not showing\n-Pets not showing\n-Black Screens when hosting\n-Incorrect spawn location\n-Not being able to talk in lobby chat\n\nYou can find all the issues listed on the GitHub page!", false);
			}
			return Cosmetic.getItemName(CosmeticType.Color, currentSettings[settingIndex]);
		}
		case (10): {
			return Cosmetic.getItemName(CosmeticType.Hat, currentSettings[settingIndex]);
		}
		// sfx
		case (11): {
			return currentSettings[settingIndex];
		}
		// music
		case (12): {
			return currentSettings[settingIndex];
		}
		case (15): {
			return Cosmetic.getItemName(CosmeticType.Skin, currentSettings[settingIndex]);
		}
		case (16): {
			return Cosmetic.getItemName(CosmeticType.Pet, currentSettings[settingIndex]);
		}
		case (17): {
			return currentSettings[settingIndex].equals("True") ? "On" : "Off";
		}
		case (18): {
			return Language.getLanguage(Integer.parseInt(currentSettings[settingIndex])).getName();
		}
		case (19): {
			return currentSettings[settingIndex].equals("True") ? "On" : "Off";
		}

		}
		return "Doesn't Exist";
	} 

	public int getSettingIndex() {
		return this.settingIndex;
	}

	public static void setCurrentSettings(String[] currentSettings2) {
		currentSettings = currentSettings2;
	}
}
