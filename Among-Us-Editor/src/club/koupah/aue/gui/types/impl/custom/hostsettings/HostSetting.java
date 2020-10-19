package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.GUIComponent;

public class HostSetting extends GUIComponent {

	int index;

	int length;

	String currentHex;

	public HostSetting(JLabel label, JSpinner component, int index, int length) {
		super(label, component);

		this.index = index;
		this.length = length;

		this.currentHex = Editor.getInstance().hostSettingsManager.getHex(index, length);
		System.out.println("Current Hex: " + this.currentHex);
	}

	public int getInt() {
		return Integer.parseInt(currentHex, 16);
	}

	public float getFloat() {

		Long i = Long.parseLong(currentHex, 16);
      float f = Float.intBitsToFloat(i.intValue());
		System.out.println(f);
		return f;
	}

	public String getSaveValue() {

		try {
			((JSpinner) component).commitEdit();
		} catch (java.text.ParseException e) {
		}

		String value = ((JSpinner) component).getValue().toString();

		for (Character c : value.toCharArray()) {
			if (!Character.isDigit(c) && !c.equals('.')) {
				System.out.println(value + " is invalid, char: " + c);
			}
		}
		
		if (length == 32) { 
			
			
			System.out.println("The float value is: " + String.format("%0" + (this.length / 4) + "X", (Integer) Float.floatToIntBits(Float.valueOf(value))).substring(0, (this.length/4)));
			return String.format("%0" + (this.length / 4) + "X", (Integer) Float.floatToIntBits(Float.valueOf(value))).substring(0, (this.length/4));
		}
		System.out.println("Length of non 32 is: " + String.format("%0" + (this.length / 4) + "X", (Integer) ((JSpinner) component).getValue()).length());
		return String.format("%0" + (this.length / 4) + "X", (Integer) ((JSpinner) component).getValue()).substring(0, (this.length/4));
	}

	public int getIndex() {
		return this.index;
	}

}
