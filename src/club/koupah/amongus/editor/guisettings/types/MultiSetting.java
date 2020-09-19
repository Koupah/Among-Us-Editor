package club.koupah.amongus.editor.guisettings.types;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import club.koupah.amongus.editor.guisettings.Setting;

public class MultiSetting extends Setting {
	
	List<String> values;
	boolean keepCurrent;
	
	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent, int settingIndex) {
		super(label, component, settingIndex);
		this.keepCurrent = addKeepCurrent;
		this.values = values;
		
		//Updated to show more items to make it easier to choose
		component.setMaximumRowCount(12);
		
		//Essentially, if it's cosmetic then add keep current
		if (addKeepCurrent)
			component.addItem("Keep Current");

		for (String value : values)
			component.addItem(value);
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getSettingValue());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateComponent() {
		((JComboBox<String>)component).setSelectedItem(getSettingValue());
	}
	
	@Override
	public String getProperValue() {
		final String saveValue = getSaveValue();
		if (saveValue.equals("ErrorInSaveValue"))
			System.out.println("Error in save value for: " + label.getText());
		return saveValue;
	}

	@SuppressWarnings("unchecked")
	public void setValues(List<String> items) {
		System.out.println(this.labelText + " removing items");
		((JComboBox<String>)component).removeAllItems();
		if (keepCurrent || items.size() == 0)
			((JComboBox<String>)component).addItem("Keep Current");
		
		for (String newitems : items) {
			((JComboBox<String>)component).addItem(newitems);
			System.out.println(newitems);
		}
	}
	@SuppressWarnings("unchecked")
	public void originalValues() {
		((JComboBox<String>)component).removeAllItems();
		for (String original : values) {
			((JComboBox<String>)component).addItem(original);
		}
	}

}
