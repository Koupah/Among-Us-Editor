package club.koupah.aue.gui.types.impl.custom.preferences;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class SmoothScroll extends CheckboxSetting {
	
	
	public SmoothScroll(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setSelected(Editor.getInstance().configManager.getSmoothScroll());
	}
	
	@Override
	public void checkboxPressed(ActionEvent arg0) {
		Editor.getInstance().configManager.setSmoothScroll(((JCheckBox)component).isSelected());
		super.checkboxPressed(arg0);
		updateLabel();
		Editor.getInstance().configManager.saveConfig();
	}
	
	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox)component).isSelected() ? "On" : "Off"));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(Editor.getInstance().configManager.getSmoothScroll());
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
