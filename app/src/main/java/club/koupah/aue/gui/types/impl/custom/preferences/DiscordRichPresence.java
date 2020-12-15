package club.koupah.aue.gui.types.impl.custom.preferences;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.AUEditorMain;
import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class DiscordRichPresence extends CheckboxSetting {

	public DiscordRichPresence(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setSelected(Editor.getInstance().configManager.getDiscordRP());
	}

	@Override
	public void checkboxPressed(ActionEvent ae) {
		Editor.getInstance().configManager.setDiscordRP(((JCheckBox) component).isSelected());

		if (((JCheckBox) component).isSelected())
			AUEditorMain.setupRichPresence();
		else if (AUEditorMain.usingRichPresence)
			AUEditorMain.removeRichPresence();

		super.checkboxPressed(ae);
		updateLabel();
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox) component).isSelected() ? "On" : "Off"));
	}

	@Override
	public void updateComponent() {
		((JCheckBox) component).setSelected(Editor.getInstance().configManager.getDiscordRP());
		((JCheckBox) component).setText(((JCheckBox) component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
