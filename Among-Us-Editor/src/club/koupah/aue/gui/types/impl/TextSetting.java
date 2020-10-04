package club.koupah.aue.gui.types.impl;

import javax.swing.JLabel;
import javax.swing.JTextField;

import club.koupah.aue.gui.types.Setting;

public class TextSetting extends Setting {
	
	public TextSetting(JLabel label, JTextField component, int settingIndex) {
		super(label, component, settingIndex);
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getCurrentSettingValue());
	}
	
	@Override
	public void updateComponent() {
		((JTextField)component).setText(getCurrentSettingValue());
	}

	@Override
	public String getProperValue() {
		return ((JTextField)component).getText();
	}
	
}
