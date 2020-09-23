package club.koupah.amongus.editor.guisettings.types;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class InvisibleName extends CheckboxSetting {
	
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
	
	String capitalFirst(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

}
