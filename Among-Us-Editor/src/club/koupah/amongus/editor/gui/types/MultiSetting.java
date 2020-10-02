package club.koupah.amongus.editor.gui.types;

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

import club.koupah.amongus.editor.gui.GUIPanel;
import club.koupah.amongus.editor.gui.Setting;
import club.koupah.amongus.editor.gui.settings.cosmetics.Cosmetic;
import club.koupah.amongus.editor.gui.settings.cosmetics.Hats;
import club.koupah.amongus.editor.utility.ImageUtil;
import club.koupah.amongus.editor.utility.PopUp;

public class MultiSetting extends Setting {

	List<String> values;
	boolean keepCurrent;

	boolean hasPreview = false;

	JLabel imageLabel;

	int[] imageSettings;

	boolean customOffsets;

	int[] bounds;

	int[] currentBounds;
	
	int[] cosmeticOffset;
	
	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent,
			boolean imagePreview, int[] offset, int settingIndex) {
		this(label, component, values, addKeepCurrent, settingIndex);
		this.hasPreview = imagePreview;
		this.imageLabel = new JLabel();

		// Per instance offsets for displaying the image & the width/height of the image
		this.imageSettings = offset;
		
		//Only hats are currently supporting custom offsets, so easy hard coded check
		customOffsets = label.getText().split(":")[0].equals("Hat");
		cosmeticOffset = new int[] {0,0,0,0};
	}

	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent,
			int settingIndex) {
		super(label, component, settingIndex);
		this.keepCurrent = addKeepCurrent;
		this.values = values;

		// Updated to show more items to make it easier to choose
		component.setMaximumRowCount(14);

		// Essentially, if it's cosmetic then add keep current
		if (addKeepCurrent)
			component.addItem("Keep Current");

		for (String value : values)
			component.addItem(value);

		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Separate function to allow for future overrides
				settingChanged(arg0);
			}

		});
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);

		if (hasPreview) {

			int width = 60; // This is really just the JLabel width, not image

			this.bounds = new int[] { component.getX() - width + imageSettings[0],
					component.getY() + imageSettings[1] + 10 - (width / 2), width + imageSettings[2],
					width + imageSettings[3] };

			this.currentBounds = this.bounds.clone();
			
			this.imageLabel.setBounds(currentBounds[0], currentBounds[1], currentBounds[2], currentBounds[3]);

			contentPane.add(this.imageLabel, imageSettings == null ? -1 : imageSettings[4]);
		}
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getCurrentSettingValue());
	}

	public void settingChanged(ActionEvent arg0) {
		if (hasPreview) {

			if (customOffsets) {
				cosmeticOffset = Hats.getOffsetByID(this.getComponentValue(false));
				this.currentBounds = this.bounds.clone();
			}
			
			this.imageLabel.setBounds(currentBounds[0] + cosmeticOffset[0], currentBounds[1] + cosmeticOffset[1], currentBounds[2] + cosmeticOffset[2], currentBounds[3] + cosmeticOffset[3]);
			
			this.imageLabel.setIcon(ImageUtil.getIcon(Cosmetic.class, getCosmeticImagePath(this.getComponentValue(false)), 50 + imageSettings[2] + cosmeticOffset[2], 40 + imageSettings[3] + cosmeticOffset[3]));

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateComponent() {
		((JComboBox<String>) component).setSelectedItem(getCurrentSettingValue());
	}

	private String getCosmeticImagePath(String cosmeticID) {
		return "images/" // Images package
				+ this.labelText.split(":")[0].toLowerCase() // Returns hat, pet, skin depending on the type. This is
																// just an easy way to make this modular
				+ "/" + cosmeticID + ".png"; // cosmeticID is easier to work with then naming the pictures proper names,
												// and PNG is just so we have transparency (And source images are PNG
												// :P)
	}
	

	@Override
	public String getProperValue() {
		final String saveValue = getValueToSave();

		if (saveValue.equals("ErrorInSaveValue")) {
			System.out.println("Error in save value for: " + label.getText());
			new PopUp("Error in save value for " + label.getText().split(":")[0]); // I really need to add a function so
		} // I don't have to split everytime
			// lol

		return saveValue;
	}

	@SuppressWarnings("unchecked")
	public void setValues(List<String> items) {

		// Was the current item removed
		boolean currentRemoved = !items.contains(getCurrentSettingValue());

		((JComboBox<String>) component).removeAllItems();

		// If new item size length is 0, add a Keep Current setting
		if (keepCurrent || items.size() == 0 || currentRemoved)
			((JComboBox<String>) component).addItem("Keep Current");

		// Add all the new items
		for (String newitems : items)
			((JComboBox<String>) component).addItem(newitems);

	}

	@SuppressWarnings("unchecked")
	public void originalValues() {
		// Remove all current
		((JComboBox<String>) component).removeAllItems();

		// Add all original
		for (String original : values) {
			((JComboBox<String>) component).addItem(original);
		}
	}

}
