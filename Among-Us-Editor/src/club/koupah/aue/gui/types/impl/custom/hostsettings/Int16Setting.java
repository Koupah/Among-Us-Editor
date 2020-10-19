package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;

public class Int16Setting extends HostSetting {

	public Int16Setting(JLabel label, JSpinner component, int index) {
		super(label, component, index, 16);
		
		component.setValue(getInt());
		
	}

	
	
}
