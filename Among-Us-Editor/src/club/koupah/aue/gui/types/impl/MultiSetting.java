package club.koupah.aue.gui.types.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.gui.values.cosmetics.Cosmetic;
import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticType;
import club.koupah.aue.gui.values.cosmetics.Hats;
import club.koupah.aue.utility.ImageUtil;
import club.koupah.aue.utility.PopUp;

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

	CosmeticType cosmeticType;

	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent,
			boolean imagePreview, int[] offset, CosmeticType ct, int settingIndex) {
		this(label, component, values, addKeepCurrent, settingIndex);

		this.cosmeticType = ct;
		this.hasPreview = imagePreview;
		if (hasPreview) {
			ComboBoxRenderer renderer = new ComboBoxRenderer();

			component.setRenderer(renderer);
		}
		this.imageLabel = new JLabel();

		// Per instance offsets for displaying the image & the width/height of the image
		this.imageSettings = offset;

		// Only hats are currently supporting custom offsets, so easy hard coded check
		customOffsets = label.getText().split(":")[0].equals("Hat");
		cosmeticOffset = new int[] { 0, 0, 0, 0 };
	}

	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent,
			int settingIndex) {
		super(label, component, settingIndex);
		this.keepCurrent = addKeepCurrent;
		this.values = values;

		// Updated to show more items to make it easier to choose
		component.setMaximumRowCount(14);

		updateValues(values);

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

			this.imageLabel.setBounds(currentBounds[0] + cosmeticOffset[0], currentBounds[1] + cosmeticOffset[1],
					currentBounds[2] + cosmeticOffset[2], currentBounds[3] + cosmeticOffset[3]);

			this.imageLabel.setIcon(ImageUtil.getIcon(Cosmetic.class, getCosmeticImagePath(this.getComponentValue(false)),
					50 + imageSettings[2] + cosmeticOffset[2], 40 + imageSettings[3] + cosmeticOffset[3]));

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

	public void setValues(List<String> items) {
		updateValues(items);
	}

	public void updateValues(List<String> values) {
		@SuppressWarnings("unchecked")
		JComboBox<String> component = ((JComboBox<String>) this.component);
		component.removeAllItems();

		boolean currentRemoved = Editor.getInstance().isVisible() ? !values.contains(getCurrentSettingValue()) : true;

		// Essentially, if it's cosmetic then add keep current
		if (keepCurrent)
			Collections.sort(values);

		if (keepCurrent || values.size() == 0 || currentRemoved) {
			component.insertItemAt("Keep Current", 0);
			component.setSelectedIndex(0);
		}

		for (String value : values) {
			if (value.equals("None"))
				component.insertItemAt(value, 1);
			else component.addItem(value);
		}

	}

	public void originalValues() {
		updateValues(values);
	}

	class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

		private static final long serialVersionUID = 1L;

		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
			for (String value : MultiSetting.this.values) {
				String ID = Cosmetic.getIDbyName(MultiSetting.this.cosmeticType, value);
				if (!ID.equals("ErrorFinding")) {
					images.put(value, new ImageIcon(ImageUtil
							.scaleProper(ImageUtil.getImage(Cosmetic.class, getCosmeticImagePath(ID)), 30, 30, true)));
				}
			}
		}

		HashMap<String, ImageIcon> images = new HashMap<String, ImageIcon>();

		/*
		 * This method finds the image and text corresponding to the selected value and
		 * returns the label, set up to display the text and image.
		 */
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
//Get the selected index. (The index param isn't
//always valid, so just use the value.)

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			if (value == null) {
				list.remove(this);
				setVisible(false);
				return this;
			}

			ImageIcon icon = images.get(value);

			setIcon(icon);

			setText(value.toString());
			setFont(list.getFont());

			return this;
		}
	}
}
