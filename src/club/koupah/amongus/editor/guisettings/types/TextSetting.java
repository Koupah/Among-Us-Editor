package club.koupah.amongus.editor.guisettings.types;

import javax.swing.JLabel;
import javax.swing.JTextField;

import club.koupah.amongus.editor.guisettings.Setting;

public class TextSetting extends Setting {
	
	public TextSetting(JLabel label, JTextField component, int settingIndex) {
		super(label, component, settingIndex);
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getSettingValue());
	}
	
	@Override
	public void updateComponent() {
		((JTextField)component).setText(getSettingValue());
	}

	@Override
	public String getProperValue() {
		return ((JTextField)component).getText();
	}
	
}
