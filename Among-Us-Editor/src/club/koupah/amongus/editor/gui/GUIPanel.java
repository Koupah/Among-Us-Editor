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
import club.koupah.amongus.editor.utility.PopUp;

public class GUIPanel extends JLayeredPane {

	private static final long serialVersionUID = -5702287851924902148L;

	int settingCount = 0;
	
	String name;
	
	Icon icon;
	
	int maxHeight = 0;
	
	public GUIPanel(String name, String iconName) {
		this.name = name;
		
		this.icon = getImage(iconName);
		
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
	
	private Icon getImage(String imagename) {
		try {

			BufferedImage image = ImageIO.read(GUIPanel.class.getResource("tabicons/" + imagename));

			ImageIcon icon = new ImageIcon(properScaleImage(image, 50, 20));

			return icon;
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Tab Icon image failed");
		}
		// return null, makes the image null
		return null;
	}

	// Resize image to match new width and height
	public static BufferedImage resize(BufferedImage img, int width, int height) {
		final Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		final BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Have to do this for dimg to have graphics dude!!
		Graphics2D g2d = dimg.createGraphics();
		// I smacked these rendering hints in, I don't think they do anything but oh
		// well
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		// Return the image
		return dimg;
	}

	// Scale the image to keep aspect ratio but fit new width and height
	BufferedImage properScaleImage(BufferedImage image, int w, int h) {

		// Cast double to everything here so it doesnt return 0 from int dividing
		double widthRatio = (double) w / (double) image.getWidth();
		double heightRatio = (double) h / (double) image.getHeight();

		// Find the smallest ratio so that we can ensure the entire image will fit
		double ratio = Math.min(widthRatio, heightRatio);

		// Return the resized image
		return resize(image, (int) (image.getWidth() * ratio), (int) (image.getHeight() * ratio));
	}

	public int getMaxHeight() {
		return this.maxHeight;
	}
	


	
	
	
}
