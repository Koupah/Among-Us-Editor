package club.koupah.aue.gui.types;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;

public enum SettingType {

	COSMETIC(new GUIPanel("Cosmetics", "cosmetics.png")),
	SETTING(new GUIPanel("Settings", "settings.png", "settingswhite.png")),
	HOST_SETTINGS(new GUIPanel("Host Settings", "settings.png", "settingswhite.png")),
	STATS(new GUIPanel("Stats", "settings.png", "settingswhite.png")),
	PREFERENCES(new GUIPanel("Preferences", "preferences.png", "preferenceswhite.png")),
	OTHER(new GUIPanel("Other", "other.png", "otherwhite.png")),
	RAT(new GUIPanel("Rat", "rat.jpg"), false);// DO NOT SET THIS TO TRUE UNLESS YOU WANT A RAT

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
			Editor.getInstance().tabbedPanel.insertTab(getPanel().getName(), getPanel().getIcon(false), getPanel(),
					getPanel().getDescription(), getIndex() > Editor.getInstance().tabbedPanel.getTabCount() ? Editor.getInstance().tabbedPanel.getTabCount() : getIndex());
		else if (Editor.getInstance().tabbedPanel != null && Editor.getInstance().tabbedPanel.findTabByName(this.getPanel().getName()) != -1)
			Editor.getInstance().tabbedPanel
					.removeTabAt(Editor.getInstance().tabbedPanel.indexOfTab(this.getPanel().getName()));
	}

	// gets the index of this in the array (Cosmetic 0, Setting 1, etc)
	public int getIndex() {
		int index = 0;
		for (SettingType type : values()) {
			if (type.equals(this))
				return index;
			index++;
		}
		return index; // return the very last possible index
	}

}
