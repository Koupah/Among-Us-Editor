package club.koupah.aue.gui.types;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.ScrollPanel;

public enum SettingType {

	COSMETIC(new ScrollPanel(new GUIPanel("Cosmetics", "cosmetics.png"))),
	SETTING(new ScrollPanel(new GUIPanel("Settings", "settings.png", "settingswhite.png"))),
	HOST_SETTINGS(new ScrollPanel(new GUIPanel("Host Settings", "settings.png", "settingswhite.png"))),
	STATS(new ScrollPanel(new GUIPanel("Stats", "settings.png", "settingswhite.png"))),
	PREFERENCES(new ScrollPanel(new GUIPanel("Preferences", "preferences.png", "preferenceswhite.png"))),
	OTHER(new ScrollPanel(new GUIPanel("Other", "other.png", "otherwhite.png"))),
	RAT(new ScrollPanel(new GUIPanel("Rat", "rat.jpg")), false);// DO NOT SET THIS TO TRUE UNLESS YOU WANT A RAT

	ScrollPanel panel;
	
	GUIPanel guiPanel;
	
	boolean visible = true;

	SettingType(ScrollPanel panel) {
		this.panel = panel;
		this.guiPanel = (GUIPanel) panel.getViewport().getView();
	}

	SettingType(ScrollPanel panel, boolean visible) {
		this(panel);
		this.visible = visible;
	}

	public ScrollPanel getPanel() {
		return this.panel;
	}
	public GUIPanel getGUIPanel() {
		return this.guiPanel;
	}
	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean update) {
		if (this.visible = update)
			Editor.getInstance().tabbedPanel.insertTab(getGUIPanel().getName(), getGUIPanel().getIcon(false), getPanel(),
					getGUIPanel().getDescription(), getIndex() > Editor.getInstance().tabbedPanel.getTabCount() ? Editor.getInstance().tabbedPanel.getTabCount() : getIndex());
		else if (Editor.getInstance().tabbedPanel != null && Editor.getInstance().tabbedPanel.findTabByName(this.getPanel().getName()) != -1)
			Editor.getInstance().tabbedPanel
					.removeTabAt(Editor.getInstance().tabbedPanel.indexOfTab(this.getGUIPanel().getName()));
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
