package club.koupah.aue.gui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import com.sun.glass.events.KeyEvent;

import club.koupah.aue.Editor;
import club.koupah.aue.utility.ImageUtil;

public class GUIPanel extends JLayeredPane {

	private static final long serialVersionUID = -5702287851924902148L;

	int settingCount = 0;
	
	String name;
	
	Icon icon;
	
	int maxHeight = 0;
	
	public GUIPanel(String name, String iconFileName) {
		this.name = name;
		
		this.icon = ImageUtil.getIcon(this.getClass(), "tabicons/" + iconFileName, 50, 20);
		
		setLayout(null);
		setOpaque(true);
	}
	
	public Component addLabel(JLabel comp) {
		
		settingCount = getSettingCount() + 1;
		maxHeight = 60 + (getSettingCount() * Editor.scale);
		
		return super.add(comp);
	}

	public Icon getIcon() {
		// TODO Auto-generated method stub
		return this.icon;
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
	


	
	
	
}
