package club.koupah.aue.gui.types;

import club.koupah.aue.gui.GUIPanel;

public enum SettingType {

	COSMETIC(new GUIPanel("Cosmetics", "cosmetics.png")),
	SETTING(new GUIPanel("Settings", "settings.png")),
	PREFERENCES(new GUIPanel("Preferences", "preferences.png")),
	OTHER(new GUIPanel("Other", "other.png"));
	
	GUIPanel panel;
	
	SettingType(GUIPanel panel) {
		this.panel = panel;
	}
	
	public GUIPanel getPanel() {
		return this.panel;
	}
	
}
