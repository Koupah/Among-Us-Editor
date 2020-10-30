package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Float32Setting extends HostSetting {

	public Float32Setting(JLabel label, JSpinner component, int index, String zeroes) {
		super(label, component, index, 32);

		component.setModel(new SpinnerNumberModel(1.0, -2147483647, 2147483646, 0.05));
		component.setEditor(new JSpinner.NumberEditor(component, "0." + zeroes));
		
		update();
	}
	
	@Override
	public void update() {
		if (super.shouldUpdate())
		((JSpinner)component).setValue(getFloat());
	}
	

}
