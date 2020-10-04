package club.koupah.aue.gui.types.impl.custom;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class InvisibleName extends CheckboxSetting {
	
	//Should be a char, made a string for easy use
	String invisibleName = "\u3164";
	
	public InvisibleName(JLabel label, JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setText("Off");
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + this.getCurrentSettingValue().equals(invisibleName));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(this.getCurrentSettingValue().equals(invisibleName));
	}

	@Override
	public String getProperValue() {
		return ((JCheckBox)component).isSelected() ? invisibleName : null;
	}

}
