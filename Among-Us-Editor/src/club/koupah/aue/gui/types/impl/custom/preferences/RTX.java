package club.koupah.aue.gui.types.impl.custom.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class RTX extends CheckboxSetting {
	public RTX(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setText("Off");
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RTX.this.updateLabel();
			}
			
		});
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox)component).isSelected() ? "On" : "Off"));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
