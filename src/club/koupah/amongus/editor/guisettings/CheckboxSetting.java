package club.koupah.amongus.editor.guisettings;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class CheckboxSetting extends Setting {
	
	public CheckboxSetting(JLabel label, JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setText("Active?");
	}

	@Override
	public void updateLabel() {
		label.setText(labelText + getSettingValue());
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(this.getValue(true).equals("On"));
	}

	@Override
	public String getProperValue() {
		return capitalFirst(String.valueOf(((JCheckBox)component).isSelected()));
	}
	
	String capitalFirst(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

}
