package club.koupah.aue.gui.types;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;

public enum SettingType {

	COSMETIC(new GUIPanel("Cosmetics", "cosmetics.png")),
	SETTING(new GUIPanel("Settings", "settings.png")),
	PREFERENCES(new GUIPanel("Preferences", "preferences.png")),
	OTHER(new GUIPanel("Other", "other.png")),
	
	RAT(new GUIPanel("Rat","rat.jpg"), false);//DO NOT SET THIS TO TRUE UNLESS YOU WANT A RAT
	
	GUIPanel panel;
	boolean visible = true;
	
	SettingType(GUIPanel panel) {
		this.panel = panel;
	}
	
	SettingType(GUIPanel panel, boolean visible) {
		this(panel);
		this.visible = visible;
	}
	
	public GUIPanel getPanel() {
		return this.panel;
	}

	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean update) {
		if (this.visible = update)
			Editor.getInstance().tabbedPanel.insertTab(getPanel().getName(), getPanel().getIcon(), getPanel(), getPanel().getDescription(), getIndex());
		else
			Editor.getInstance().tabbedPanel.remove(this.getPanel());
	}
	
	//gets the index of this in the array (Cosmetic 0, Setting 1, etc)
	int getIndex() {
		int index = 0;
		for (SettingType type : values()) {
			if (type.equals(this))
				return index;
			index++;
		}
		return index; //return the very last possible index
	}
	
}
