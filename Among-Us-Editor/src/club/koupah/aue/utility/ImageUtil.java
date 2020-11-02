package club.koupah.aue.utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageUtil {

	public static BufferedImage getImage(Class<?> relative, String imagePath, int width, int height) {
		try {

			final BufferedImage image = ImageIO.read(relative.getResource(imagePath));

			return scaleProper(image, width, height, true);
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

			ImageIcon icon = new ImageIcon(scaleProper(image, width, height, true));

			return icon;
		} catch (Exception e) {
			// Popup message incase the image for some reason won't load
			e.printStackTrace();
			new PopUp("Failed to get Image");

		}
		// return null, makes the image null
		return null;
	}

	public static Icon makeIcon(BufferedImage image) {
		return new ImageIcon(image);
	}

	// Scale the image to keep aspect ratio but fit new width and height
	public static BufferedImage scaleProper(BufferedImage image, int w, int h, boolean hq) {

		// Cast double to everything here so it doesnt return 0 from int dividing
		double widthRatio = (double) w / (double) image.getWidth();
		double heightRatio = (double) h / (double) image.getHeight();

		// Find the smallest ratio so that we can ensure the entire image will fit
		double ratio = Math.min(widthRatio, heightRatio);

		// Return the resized image
		BufferedImage output = getScaledInstance(image, (int) (image.getWidth() * ratio),
				(int) (image.getHeight() * ratio),
				hq ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, hq);

		return output;
	}

	// Scale the image to keep aspect ratio but fit new width and height
	public static BufferedImage scaleFixed(BufferedImage image, int w, int h, boolean hq) {

		// Return the resized image
		BufferedImage output = getScaledInstance(image, w, h,
				hq ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, hq);

		return output;
	}

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			if (img.getWidth() <= 1)
				w = targetWidth;
			else w = img.getWidth();

			if (img.getHeight() <= 1)
				h = targetHeight;
			else h = img.getHeight();
		} else {
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

}
