package club.koupah.aue.gui.types.impl;

import javax.swing.JLabel;
import javax.swing.JTextField;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;

public class TextSetting extends Setting {

	int maxLength = Integer.MAX_VALUE;
	boolean warn = true;
	String warning;

	public TextSetting(JLabel label, final JTextField component, int maxLength, final String warning, int settingIndex) {
		super(label, component, settingIndex);
		this.maxLength = maxLength;
		this.warning = warning;
	}

	public TextSetting(JLabel label, JTextField component, int settingIndex) {
		super(label, component, settingIndex);
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + getCurrentSettingValue());
	}

	@Override
	public void updateComponent() {
		((JTextField) component).setText(getCurrentSettingValue());
	}

	@Override
	public String getProperValue() {
		System.out.println(((JTextField) TextSetting.this.component).getText().length());
		if (Editor.getInstance().isVisible()
				&& ((JTextField) TextSetting.this.component).getText().length() > TextSetting.this.maxLength) {
			if (warn) {
				warn = false;
				new PopUp(warning, false);
			}
		} else {
			warn = true;
		}
		return ((JTextField) component).getText();
	}

}
