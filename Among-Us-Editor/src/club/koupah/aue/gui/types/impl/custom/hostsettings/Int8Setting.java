package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;

public class Int8Setting extends HostSetting {

	public Int8Setting(JLabel label, JSpinner component, int index) {
		super(label, component, index, 8);
		
		component.setValue(getInt());
		
	}

	
	
}
