package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Float32Setting extends HostSetting {

	public Float32Setting(JLabel label, JSpinner component, int index, String zeroes) {
		super(label, component, index, 32);
		/*
		 * These min and max values are broken, the game can take negative values but I
		 * swear it's only float32 so this is temp :p
		 * 
		 * Besides it doesn't matter how big it is because I squash it all into the
		 * appropriate hex size
		 */
		component.setModel(new SpinnerNumberModel(1.0f, -(Float.MAX_VALUE), (Float.MAX_VALUE), 0.05f));
		component.setEditor(new JSpinner.NumberEditor(component, "0." + zeroes));
		decimalPlaces = zeroes.length();
		
		update();
	}

	@Override
	public void update() {
		if (super.shouldUpdate())
			((JSpinner) component).setValue(((Float)getFloat()).doubleValue());
	}

}
