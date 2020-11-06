package club.koupah.aue.gui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.event.KeyEvent;

import club.koupah.aue.Editor;
import club.koupah.aue.utility.ImageUtil;

public class GUIPanel extends JLayeredPane {

	private static final long serialVersionUID = -5702287851924902148L;

	int settingCount = 0;

	String name;

	Icon icon;
	Icon whiteIcon;
	
	boolean hasWhite;
	boolean white = false;
	
	int maxHeight = 0;
	
	String iconName;
	String whiteIconName;
	
	public GUIPanel(String name, String iconFileName) {
		this(name, iconFileName, iconFileName);
	}
	
	public GUIPanel(String name, String iconFileName, String whiteIcon) {
		this.name = name;
		hasWhite = !iconFileName.equals(whiteIcon);
		this.icon = ImageUtil.getIcon(this.getClass(), "icons/" + iconFileName, 50, 20);
		this.whiteIcon = ImageUtil.getIcon(this.getClass(), "icons/" + whiteIcon, 50, 20);
		setLayout(null);
		setOpaque(true);
		iconName = iconFileName.split("\\.")[0];
		whiteIconName = whiteIcon.split("\\.")[0];
		
	}

	public Component addLabel(JLabel comp) {

		settingCount = getSettingCount() + 1;
		maxHeight = 20 + (getSettingCount() * Editor.guiSpacing);

		return super.add(comp);
	}

	public Icon getIcon(boolean white) {
		this.white = white;
		return white ? this.whiteIcon : this.icon;
	}

	public String getDescription() {
		return "This is the tab for " + name + ". Click it to see all " + name + "!";
	}

	public int activateKey() {
		return KeyEvent.VK_LEFT;
	}

	public int getMaxHeight() {
		return this.maxHeight;
	}

	public int getSettingCount() {
		return settingCount;
	}

	public String getName() {
		return this.name;
	}

	public String getDiscordImageKey() {
		return white ? this.whiteIconName : this.iconName;
	}
	
}
