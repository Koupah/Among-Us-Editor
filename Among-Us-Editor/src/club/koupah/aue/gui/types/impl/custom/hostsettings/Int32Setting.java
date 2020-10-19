package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Int32Setting extends HostSetting {

	public Int32Setting(JLabel label, JSpinner component, int index) {
		super(label, component, index, 32);

		SpinnerNumberModel model = new SpinnerNumberModel(1.0, -2147483647, 2147483646, 0.05); 
		component.setModel(model);
		component.setValue(getFloat());
		
	}

	
	
}
