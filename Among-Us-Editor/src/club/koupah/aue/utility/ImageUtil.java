package club.koupah.aue.utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageUtil {
	
	public static BufferedImage getImage(Class<?> relative, String imagePath, int width, int height) {
		try {

			final BufferedImage image = ImageIO.read(relative.getResource(imagePath));
			
			return scaleImage(image, width, height);
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Failed to get Image");
			
		}
		// return null, makes the image null
		return null;
	}
	public static BufferedImage getImage(Class<?> relative, String imagePath) {
		try {

			final BufferedImage image = ImageIO.read(relative.getResource(imagePath));
			
			return image;
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Failed to get Image");
			
		}
		// return null, makes the image null
		return null;
	}
	public static Icon getIcon(Class<?> relative, String imagePath) {
		try {
			final BufferedImage image = ImageIO.read(relative.getResource(imagePath));
			
			ImageIcon icon = new ImageIcon(image);

			return icon;
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Failed to get Image");
		}
		// return null, makes the image null
		return null;
	}
	
	public static Icon getIcon(Class<?> relative, String imagePath, int width, int height) {
		try {

			final BufferedImage image = ImageIO.read(relative.getResource(imagePath));
			
			ImageIcon icon = new ImageIcon(scaleImage(image, width, height));

			return icon;
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Failed to get Image");
			
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
	public static BufferedImage scaleImage(BufferedImage image, int w, int h) {

		// Cast double to everything here so it doesnt return 0 from int dividing
		double widthRatio = (double) w / (double) image.getWidth();
		double heightRatio = (double) h / (double) image.getHeight();

		// Find the smallest ratio so that we can ensure the entire image will fit
		double ratio = Math.min(widthRatio, heightRatio);

		// Return the resized image
		return resize(image, (int) (image.getWidth() * ratio), (int) (image.getHeight() * ratio));
	}
	
}
