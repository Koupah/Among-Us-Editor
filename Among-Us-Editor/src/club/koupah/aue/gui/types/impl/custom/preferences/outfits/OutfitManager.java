package club.koupah.aue.gui.types.impl.custom.preferences.outfits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import club.koupah.AUEditorMain;
import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Outfit;

public class OutfitManager extends GUIComponent {

	String[] alloutfitNames;

	JButton save;

	JButton load;

	JButton delete;

	public Outfit current;

	public int outfitLength = 32;

	// Going to use this to load and delete outfits, saving/sharing will be another
	// component
	public OutfitManager(final JLabel label, JComboBox<String> component) {
		super(label, component);

		// Whenever you change item in this, it sets it as the new outfit
		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!Editor.getInstance().isVisible())
					return;

				String selected = (String) ((JComboBox<String>) OutfitManager.this.component).getSelectedItem();
				if (selected != null && Outfit.outfitExists(selected)) {

					Outfit outfit = Outfit.getOutfit(selected);

					String[] settings = outfit.getSettingsArray();

					for (GUIComponent guicomponent : Editor.getInstance().allGUIComponents) {
						if (guicomponent instanceof Setting) {
							Setting setting = (Setting) guicomponent;

							// This allows InvisibleName and future GUIComponents to avoid impacting
							// settings

							if (Outfit.isOutfitSetting(setting.getSettingIndex())) {
								Editor.getInstance().prefsManager.currentSettings[setting.getSettingIndex()] = settings[Outfit
										.getOutfitIndex(setting.getSettingIndex())];
							}
						}
					}

					current = outfit;
					updateOutfits(selected);

					// We do this to circumvent the normal saveSettings() which changes our current
					// settings array
					Editor.getInstance().prefsManager.savePlayerPrefs();
					Editor.getInstance().refresh();
				} else {
					if (selected == null)
						return;
					if (!selected.equals("None"))
						new PopUp("That outfit doesn't exist!", false);
					else current = null;
				}
			}
		});
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();

		save = new JButton("Save");
		delete = new JButton("Delete");

		label.setBounds(10, 15 + (index * Editor.guiSpacing), 100, 30);
		component.setBounds(260, 20 + (index * Editor.guiSpacing), 160, 20);
		save.setToolTipText("Save the current selected outfit!");
		save.setBounds(110, 20 + (index * Editor.guiSpacing), 70, 20);
		save.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) OutfitManager.this.component).getSelectedItem();
				if (Outfit.outfitExists(selected)) {

					Outfit outfit = Outfit.getOutfit(selected);

					String newSettings[] = makeOutfitConfig(selected);

					outfit.updateSettings(newSettings);

					updateOutfits(selected);
					Editor.getInstance().configManager.saveConfig();
				} else {
					if (selected != null && selected.equals("None"))
						new PopUp("That outfit cannot be saved to!", false);
					else new PopUp("That outfit doesn't exist!", false);
				}
			}
		});

		delete.setToolTipText("Delete the current selected outfit!");
		delete.setBounds(180, 20 + (index * Editor.guiSpacing), 70, 20);
		delete.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) OutfitManager.this.component).getSelectedItem();
				if (Outfit.outfitExists(selected)) {
					Outfit.deleteOutfit(selected);

					updateOutfits(null);
					Editor.getInstance().configManager.saveConfig();

				} else {
					if (selected != null && selected.equals("None"))
						new PopUp("That outfit cannot be deleted!", false);
					else new PopUp("That outfit doesn't exist!", false);
				}
			}
		});

		// I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);

		contentPane.add(this.component);
		contentPane.add(save);
		contentPane.add(delete);
		updateOutfits(null);
	}

	@SuppressWarnings("unchecked")
	public void updateOutfits(String current) {
		alloutfitNames = Outfit.getAllOutfitNames();
		((JComboBox<String>) component).removeAllItems();

		// Add none
		((JComboBox<String>) component).addItem("None");

		for (String outfitName : alloutfitNames) {
			((JComboBox<String>) component).addItem(outfitName);
		}

		if (current != null) {
			((JComboBox<String>) component).setSelectedItem(current);
			if (Outfit.getOutfit(current) != null)
				this.current = Outfit.getOutfit(current);
		}

		if (AUEditorMain.usingRichPresence) {
			AUEditorMain.presence.details = "Outfit: " + (current == null ? "None" : current);
			AUEditorMain.updatePresence();
		}
	}

	public String[] makeOutfitConfig(String name) {
		String[] settings = Outfit.newSettingsArray();
		settings[0] = name;

		for (GUIComponent guicomponent : Editor.getInstance().allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting) guicomponent;

				if (setting.getComponentValue(false) != null && Outfit.isOutfitSetting(setting.getSettingIndex()))
					settings[Outfit.getOutfitIndex(setting.getSettingIndex())] = setting.getComponentValue(false);
			}
		}
		return settings;
	}

	public boolean outfitNameChecks(String name, boolean importing) {
		String tocheck;
		String corrupted = "That outfit is corrupted!\nMake sure you copied it correctly!";
		if (name == null || name.length() < 1 || (tocheck = name.replaceAll(" ", "")).length() < 1) {
			new PopUp(importing ? corrupted : "The outfit doesn't have a name!", false);
			return false;
		}

		if (name.length() > outfitLength) {
			new PopUp(
					importing ? corrupted : "That outfit name is too long!\nMax Length: " + outfitLength + " characters!",
					false);
			return false;
		}

		if (tocheck.length() < 3) {
			new PopUp(importing ? corrupted : "That outfit name is too short!\nYou need atleast 3 characters!", false);
			return false;
		}

		if (name.equals("None")) {
			new PopUp(importing ? corrupted : "Outfits cannot have this name!", false);
			return false;
		}

		if (name.contains(",")) {
			new PopUp(importing ? corrupted : "Outfit names cannot contain \",\"", false);
			return false;
		}

		if (!name.matches("\\A\\p{ASCII}*\\z")) { // Ascii only baby, helps me detect when people mess up importing
																// outfits
			new PopUp(importing ? corrupted : "Outfit names cannot have special symbols/letters!\n(ASCII Characters only)",
					false);
			return false;
		}

		return true;
	}
}
