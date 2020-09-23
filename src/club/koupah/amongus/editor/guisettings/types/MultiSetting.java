package club.koupah.amongus.editor.guisettings.types;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import club.koupah.amongus.editor.PopUp;
import club.koupah.amongus.editor.guisettings.Setting;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic;

public class MultiSetting extends Setting {
	
	List<String> values;
	boolean keepCurrent;
	
	boolean hasPreview = false;
	
	JLabel imageLabel;
	
	int[] imageSettings;
	
	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent, boolean imagePreview, int[] offset, int settingIndex) {
		this(label,component,values,addKeepCurrent,settingIndex);
		this.hasPreview = imagePreview;
		this.imageLabel = new JLabel();
		
		//Per instance offsets for displaying the image & the width/height of the image
		this.imageSettings = offset;
		
		int width = 60; //This is really just the JLabel width, not image
		this.imageLabel.setBounds(component.getX() - width + imageSettings[0], component.getY() +  imageSettings[1] + 10 - (width/2), width + imageSettings[2], width+ imageSettings[3]);
	}
	
	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent, int settingIndex) {
		super(label, component, settingIndex);
		this.keepCurrent = addKeepCurrent;
		this.values = values;
		
		//Updated to show more items to make it easier to choose
		component.setMaximumRowCount(14);
		
		//Essentially, if it's cosmetic then add keep current
		if (addKeepCurrent)
			component.addItem("Keep Current");

		for (String value : values)
			component.addItem(value);
		
		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Separate function to allow for future overrides
				settingChanged(arg0);
			}
			
		});
	}

	@Override
	public void addToPane(JLayeredPane contentPane) {
		contentPane.add(this.label);
		contentPane.add(this.component);
		if (hasPreview)
			contentPane.add(this.imageLabel, imageSettings == null ? -1 : imageSettings[4]);
	}
	
	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getCurrentSettingValue());
	}
	
	public void settingChanged(ActionEvent arg0) {
		if (hasPreview) { 
			this.imageLabel.setIcon(getImage(this.getComponentValue(false)));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateComponent() {
		((JComboBox<String>)component).setSelectedItem(getCurrentSettingValue());
	}
	
	private String getCosmeticImagePath(String cosmeticID) {
		return "images/" //Images package
		+ this.labelText.split(":")[0].toLowerCase() //Returns hat, pet, skin depending on the type. This is just an easy way to make this modular
		+ "/" +  cosmeticID + ".png"; //cosmeticID is easier to work with then naming the pictures proper names, and PNG is just so we have transparency (And source images are PNG :P)
	}
	
	//
	private Icon getImage(String settingValue) {
		try {

		BufferedImage image = ImageIO.read(Cosmetic.class.getResource(getCosmeticImagePath(settingValue)));
		
		//35 for height
		ImageIcon icon = new ImageIcon(properScaleImage(image,50+imageSettings[2],40+imageSettings[3]));

		return icon;
		} catch (Exception e) {
			//Popup message incase the image for some reason won't load
			new PopUp("Couldn't load image for " + this.labelText.split(":")[0] + " ID " + settingValue + "\nFeel free to open an issue on Github if this issue persists.", false);
		}
		//return null, makes the image null
		return null;
	}
	
	//Resize image to match new width and height
	public static BufferedImage resize(BufferedImage img, int width, int height) { 
	    final Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    final BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    
	    //Have to do this for dimg to have graphics dude!!
	    Graphics2D g2d = dimg.createGraphics();
	    //I smacked these rendering hints in, I don't think they do anything but oh well
	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    //Return the image
	    return dimg;
	}  
	
	//Scale the image to keep aspect ratio but fit new width and height
	BufferedImage properScaleImage(BufferedImage image, int w, int h) {
		
		//Cast double to everything here so it doesnt return 0 from int dividing
	    double widthRatio = (double) w / (double) image.getWidth();
	    double heightRatio = (double) h / (double) image.getHeight();
	    
	    //Find the smallest ratio so that we can ensure the entire image will fit
	    double ratio = Math.min(widthRatio, heightRatio);
	    
	    //Return the resized image
	    return resize( image, (int) (image.getWidth()  * ratio),
	                         (int) (image.getHeight() * ratio));
	}
	
	@Override
	public String getProperValue() {
		final String saveValue = getValueToSave();
		
		if (saveValue.equals("ErrorInSaveValue"))
			//System.out.println("Error in save value for: " + label.getText());
			new PopUp("Error in save value for " + label.getText().split(":")[0]); //I really need to add a function so I don't have to split everytime lol
			
		return saveValue;
	}

	@SuppressWarnings("unchecked")
	public void setValues(List<String> items) {
		
		//Was the current item removed
		boolean currentRemoved = !items.contains(getCurrentSettingValue());
		
		((JComboBox<String>)component).removeAllItems();
		
		//If new item size length is 0, add a Keep Current setting
		if (keepCurrent || items.size() == 0 || currentRemoved)
			((JComboBox<String>)component).addItem("Keep Current");
		
		//Add all the new items
		for (String newitems : items) 
			((JComboBox<String>)component).addItem(newitems);
		
	}
	@SuppressWarnings("unchecked")
	public void originalValues() {
		//Remove all current
		((JComboBox<String>)component).removeAllItems();
		
		//Add all original
		for (String original : values) {
			((JComboBox<String>)component).addItem(original);
		}
	}

}
