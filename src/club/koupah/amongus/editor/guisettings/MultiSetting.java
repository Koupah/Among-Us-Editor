package club.koupah.amongus.editor.guisettings;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class MultiSetting extends Setting {

	public MultiSetting(JLabel label, JComboBox<String> component, List<String> values, boolean addKeepCurrent, int settingIndex) {
		super(label, component, settingIndex);

		//Essentially, if it's cosmetic then add keep current
		if (addKeepCurrent)
			component.addItem("Keep Current");

		for (String value : values)
			component.addItem(value);
	}

	@Override
	public void updateLabel() {
		label.setText(labelText + getSettingValue());
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

}
