package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Int16Setting extends HostSetting {

	public Int16Setting(JLabel label, JSpinner component, int index) {
		super(label, component, index, 16);

		component.setModel(new SpinnerNumberModel(1, 0, 65535, 1));

		update();
	}

	@Override
	public void update() {
		if (super.shouldUpdate())
			((JSpinner) component).setValue(getInt());
	}

}
