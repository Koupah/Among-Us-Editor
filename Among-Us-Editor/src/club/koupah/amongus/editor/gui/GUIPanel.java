package club.koupah.amongus.editor.gui;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import com.sun.glass.events.KeyEvent;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.utility.ImageUtil;
import club.koupah.amongus.editor.utility.PopUp;

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
		
		settingCount++;
		maxHeight = 60 + (settingCount * Editor.scale);
		
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
	


	
	
	
}
