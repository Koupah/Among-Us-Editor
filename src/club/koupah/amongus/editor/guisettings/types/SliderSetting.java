package club.koupah.amongus.editor.guisettings.types;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JSlider;

import club.koupah.amongus.editor.guisettings.Setting;

public class SliderSetting extends Setting {
	
	int max;
	DecimalFormat df = new DecimalFormat("#.##");
	
	public SliderSetting(JLabel label, JSlider component, int min, int max, int settingIndex) {
		super(label, component, settingIndex);
		component.setMinimum(min);
		component.setMaximum(max);
		this.max = max;
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + df.format(((double) ((double) Integer.parseInt(getCurrentSettingValue()) / (double) max) * 100)) + "%");
	}
	
	@Override
	public void updateComponent() {
		((JSlider)component).setValue(Integer.parseInt(getCurrentSettingValue()));
	}
	
	@Override
	public String getProperValue() {
		return String.valueOf(((JSlider)component).getValue());
	}


}
