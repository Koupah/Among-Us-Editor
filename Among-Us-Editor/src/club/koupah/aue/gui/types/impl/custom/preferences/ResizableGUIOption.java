package club.koupah.aue.gui.types.impl.custom.preferences;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class ResizableGUIOption extends CheckboxSetting {

	public ResizableGUIOption(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setText("Off");
		Editor.getInstance().setResizable(Editor.getInstance().configManager.getIsCustomResolution());
		component.setSelected(Editor.getInstance().configManager.getIsCustomResolution());
	}

	@Override
	public void checkboxPressed(ActionEvent ae) {
		Editor.getInstance().setResizable(((JCheckBox) component).isSelected());
		Editor.getInstance().configManager.setCustomResolution(Editor.getInstance().isResizable());

		if (((JCheckBox) component).isSelected())
			Editor.getInstance().updateWidth(Editor.getInstance().configManager.getCustomWidth(),
					Editor.getInstance().configManager.getCustomHeight());

		else Editor.getInstance().updateWidth(465, 555);
		Editor.getInstance().configManager.saveConfig();

		super.checkboxPressed(ae);
		updateLabel();
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox) component).isSelected() ? "On" : "Off"));
	}

	@Override
	public void updateComponent() {
		((JCheckBox) component).setSelected(Editor.getInstance().isResizable());
		((JCheckBox) component).setText(((JCheckBox) component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
