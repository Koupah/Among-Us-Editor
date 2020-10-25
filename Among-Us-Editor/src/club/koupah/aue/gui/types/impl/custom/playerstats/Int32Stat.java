package club.koupah.aue.gui.types.impl.custom.playerstats;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Int32Stat extends PlayerStat {

	public Int32Stat(JLabel label, JSpinner component, int index) {
		super(label, component, index, 32);

		component.setModel(new SpinnerNumberModel(1, 0, 4294967295D, 1));
	
		update();
	}
	
	@Override
	public void update() {
		if (shouldUpdate())
		((JSpinner)component).setValue(getInt());
	}
	

}
