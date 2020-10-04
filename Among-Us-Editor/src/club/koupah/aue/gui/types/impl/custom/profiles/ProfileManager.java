package club.koupah.aue.gui.types.impl.custom.profiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Profile;

public class ProfileManager extends GUIComponent {

	String[] allProfileNames;

	JButton save;

	JButton load;

	JButton delete;
	
	public Profile current;
	
	// Going to use this to load and delete profiles, saving/sharing will be another
	// component
	public ProfileManager(final JLabel label, JComboBox<String> component) {
		super(label, component);

		// Whenever you change item in this, it sets it as the new profile
		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!Editor.getInstance().isVisible())
					return;
				
				String selected = (String) ((JComboBox<String>) ProfileManager.this.component).getSelectedItem();
				if (selected != null && Profile.profileExists(selected)) {

					Profile profile = Profile.getProfile(selected);

					String[] settings = profile.getSettingsArray();

					for (GUIComponent guicomponent : Editor.getInstance().allGUIComponents) {
						if (guicomponent instanceof Setting) {
							Setting setting = (Setting) guicomponent;

							// This allows InvisibleName and future GUIComponents to avoid impacting
							// settings

							if (setting.getComponentValue(false) != null
									&& Profile.isProfileSetting(setting.getSettingIndex())) {
								Editor.getInstance().prefsManager.currentSettings[setting.getSettingIndex()] = settings[Profile
										.getProfileIndex(setting.getSettingIndex())];
							}
						}
					}
					
					current = profile;
					updateProfiles(selected);
					
					//We do this to circumvent the normal saveSettings() which changes our current settings array
					Editor.getInstance().prefsManager.savePlayerPrefs();
					Editor.getInstance().refresh();
				} else {
					if (selected == null)
						return;
					if (!selected.equals("None"))
						new PopUp("That profile doesn't exist!", false);
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
		save.setToolTipText("Save the current selected profile!");
		save.setBounds(110, 20 + (index * Editor.guiSpacing), 70, 20);
		save.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) ProfileManager.this.component).getSelectedItem();
				if (Profile.profileExists(selected)) {

					Profile profile = Profile.getProfile(selected);

					String newSettings[] = makeProfileConfig(selected);

					profile.updateSettings(newSettings);
					
					updateProfiles(selected);
					Editor.getInstance().configManager.saveConfig();
				} else {
					if (selected != null && selected.equals("None"))
						new PopUp("That profile cannot be saved to!", false);
					else
						new PopUp("That profile doesn't exist!", false);
				}
			}
		});
		
		delete.setToolTipText("Delete the current selected profile!");
		delete.setBounds(180, 20 + (index * Editor.guiSpacing), 70, 20);
		delete.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) ProfileManager.this.component).getSelectedItem();
				if (Profile.profileExists(selected)) {
					Profile.deleteProfile(selected);

					updateProfiles(null);
					Editor.getInstance().configManager.saveConfig();

				} else {
					if (selected != null && selected.equals("None"))
						new PopUp("That profile cannot be deleted!", false);
					else
						new PopUp("That profile doesn't exist!", false);
				}
			}
		});

		// I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);

		contentPane.add(this.component);
		contentPane.add(save);
		contentPane.add(delete);
		updateProfiles(null);
	}

	@SuppressWarnings("unchecked")
	public void updateProfiles(String current) {
		allProfileNames = Profile.getAllProfileNames();
		((JComboBox<String>) component).removeAllItems();

		// Add none
		((JComboBox<String>) component).addItem("None");
		for (String profileName : allProfileNames) {
			((JComboBox<String>) component).addItem(profileName);
		}
		if (allProfileNames.length == 0) {
			((JComboBox<String>) component).addItem("None");
		}

		if (current != null) {
			((JComboBox<String>) component).setSelectedItem(current);
			if (Profile.getProfile(current) != null)
				this.current = Profile.getProfile(current);
		}
	}

	public String[] makeProfileConfig(String name) {
		String[] settings = Profile.newSettingsArray();
		settings[0] = name;

		for (GUIComponent guicomponent : Editor.getInstance().allGUIComponents) {
			if (guicomponent instanceof Setting) {
				Setting setting = (Setting) guicomponent;

				if (setting.getComponentValue(false) != null && Profile.isProfileSetting(setting.getSettingIndex()))
					settings[Profile.getProfileIndex(setting.getSettingIndex())] = setting.getComponentValue(false);
			}
		}
		return settings;
	}

}
