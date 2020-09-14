package club.koupah.amongus.editor.guisettings;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.cosmetics.Cosmetic;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.settings.Language;

public class Setting {

	JLabel label;
	String labelText;

	JComponent component;

	int index;

	int settingIndex;

	int id;

	static String[] currentSettings;

	public Setting(JLabel label, JComponent component, int settingIndex) {
		this.settingIndex = settingIndex;
		this.label = label;
		this.labelText = label.getText();
		this.component = component;
		index = Editor.allGUISettings.size();
		label.setBounds(10, 75 + (index * 31), 250, 30);
		component.setBounds(270, 80 + (index * 31), 159, 20);
	}

	public void updateLabel() {
		System.out.println("Update Labels function not overriden.");
	}

	public void updateComponent() {
		System.out.println("Update Component function not overriden.");
	}

	public static void updateAllLabels(String[] currentSettings) {
		updateSettings(currentSettings);
		for (Setting set : Editor.allGUISettings) {
			set.updateLabel();
		}
	}

	public static void updateAllComponents() {
		for (Setting set : Editor.allGUISettings) {
			set.updateComponent();
		}
	}

	public void setValue(int id) {
		this.id = id;
	}

	public static void updateSettings(String[] cs) {
		currentSettings = cs;
	}

	public String getValue(boolean fromLabel) {
		if (fromLabel)
			return this.label.getText().split(labelText)[1];

		return getProperValue();
	}

	public String getProperValue() {
		System.out.println("Something didn't override getProperValue()!");
		return "Error";
	}

	@SuppressWarnings("unchecked")
	String getSaveValue() {
		switch (settingIndex) {
		case (1): {
			return String.valueOf(((JComboBox<String>)component).getSelectedIndex());
		}
		case (2): {
			String id = Cosmetic.getIDbyName(CosmeticType.Color, (String) ((JComboBox<String>)component).getSelectedItem());
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
		return "ErrorInSaveValue";
	}

	@SuppressWarnings("unchecked")
	String getSettingValue() {
		switch (settingIndex) {
		case (0): {
			return currentSettings[settingIndex];
		}
		case (1): {
			return String.valueOf(((JComboBox<String>)component).getItemAt(Integer.parseInt(currentSettings[settingIndex])));
		}
		case (2): {
			return Cosmetic.getItem(CosmeticType.Color, currentSettings[settingIndex]);
		}
		case (10): {
			return Cosmetic.getItem(CosmeticType.Hat, currentSettings[settingIndex]);
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
			return Cosmetic.getItem(CosmeticType.Skin, currentSettings[settingIndex]);
		}
		case (16): {
			return Cosmetic.getItem(CosmeticType.Pet, currentSettings[settingIndex]);
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
		return "No case";
	}

	public void addToPane(JPanel contentPane) {
		contentPane.add(this.label);
		contentPane.add(this.component);

	}

	public int getSettingIndex() {
		return this.settingIndex;
	}
}
