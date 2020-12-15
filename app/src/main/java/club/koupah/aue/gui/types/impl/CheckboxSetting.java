package club.koupah.aue.gui.types.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.gui.types.Setting;

public class CheckboxSetting extends Setting {
	
	public CheckboxSetting(JLabel label, JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		
		component.setText("Off");
		
		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Separate function to allow for future overrides
				checkboxPressed(arg0);
			}
		});
	}

	public void checkboxPressed(ActionEvent arg0) {
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}
	
	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (getCurrentSettingValue().equals("Doesn't Exist") ? "Off" : getCurrentSettingValue()));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(this.getComponentValue(true).equals("On"));
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return capitalFirst(String.valueOf(((JCheckBox)component).isSelected()));
	}
	
	String capitalFirst(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public String isCheckboxSelected() {
		return capitalFirst(String.valueOf(((JCheckBox)component).isSelected()));
	}

}
