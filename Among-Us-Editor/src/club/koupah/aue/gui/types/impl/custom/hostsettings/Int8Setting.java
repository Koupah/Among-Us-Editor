package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Int8Setting extends HostSetting {

	public Int8Setting(JLabel label, JSpinner component, int index) {
		super(label, component, index, 8);
		
		component.setModel(new SpinnerNumberModel(1, -127, 126, 1));
		
		update();

	}
	
	@Override
	public void update() {
		if (super.shouldUpdate())
		((JSpinner)component).setValue(getInt());
	}
	
}
