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
	
	String disallowedMessage;
	String[] disallowed;
	
	public TextSetting(JLabel label, final JTextField component, int maxLength, final String warning, int settingIndex) {
		this(label, component, settingIndex);
		this.maxLength = maxLength;
		this.warning = warning;
	}

	public TextSetting(JLabel label, JTextField component, int settingIndex) {
		super(label, component, settingIndex);
		this.disallowed = new String[0];
	}

	public TextSetting(JLabel label, final JTextField component, int maxLength, final String warning, int settingIndex, String message, String... symbols) {
		this(label, component, settingIndex, warning, settingIndex);
		this.disallowed = symbols;
		this.disallowedMessage = message;
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
		String text = ((JTextField) component).getText();
		if (Editor.getInstance().isVisible()
				&& text.length() > TextSetting.this.maxLength) {
			if (warn) {
				warn = false;
				new PopUp(warning, false);
			}
		} else {
			warn = true;
		}
	
		for (String symbol : disallowed) {
			if (text.contains(symbol)) {
				((JTextField) component).setText(text.replaceAll(symbol, ""));
				new PopUp(disallowedMessage + " \"" + symbol + "\"\nUsing it will cause issues.", false);
			}
		}
		return ((JTextField) component).getText();
	}

}
