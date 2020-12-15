package club.koupah.aue.gui.types.impl.custom.preferences;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class AlwaysOnTop extends CheckboxSetting {
	
	
	public AlwaysOnTop(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		Editor.getInstance().setAlwaysOnTop(Editor.getInstance().configManager.getAOT());
		component.setSelected(Editor.getInstance().configManager.getAOT());
	}
	
	@Override
	public void checkboxPressed(ActionEvent arg0) {
		Editor.getInstance().setAlwaysOnTop(((JCheckBox)component).isSelected());
		Editor.getInstance().configManager.setAOT(Editor.getInstance().isAlwaysOnTop());
		Editor.getInstance().configManager.saveConfig();
		super.checkboxPressed(arg0);
		updateLabel();
	}
	
	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox)component).isSelected() ? "On" : "Off"));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(Editor.getInstance().isAlwaysOnTop());
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
