package club.koupah.aue.gui.types.impl.custom.hostsettings;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.PopUp;

public class HostSetting extends GUIComponent {

	int index;

	int length;

	String currentHex;

	public HostSetting(JLabel label, JSpinner component, int index, int length) {
		super(label, component);

		this.index = index;
		this.length = length;
		this.currentHex = Editor.getInstance().hostSettingsManager.getHex(index, length);
	}

	public int getInt() {
		this.currentHex = Editor.getInstance().hostSettingsManager.getHex(index, length); //This is here so we always have the most up to date value
		return Integer.parseInt(currentHex, 16);
	}

	public float getFloat() {
		this.currentHex = Editor.getInstance().hostSettingsManager.getHex(index, length); //Read above comment
		
		Long i = Long.parseLong(currentHex, 16); //Long process to convert Hexadecimal to a float value, it's dumb
		float f = Float.intBitsToFloat(i.intValue());
		return f;
	}

	public String getSaveValue() {

		try {
			((JSpinner) component).commitEdit();
		} catch (java.text.ParseException e) {
		}

		String value = ((JSpinner) component).getValue().toString();

		for (Character c : value.toCharArray()) {
			if (!Character.isDigit(c) && !c.equals('.') && !c.equals('-')) { //TODO, count how many '-' are in the String, if it's more than 1 then it should be an error
				System.out.println("Invalid character: " + c);
				new PopUp("The value for " + this.getLabelText().split(":")[0]
						+ " wasn't valid!\nUsing the previous value instead.", false);
				return this.currentHex; //Return the current hex value, instead of our custom one
			}
		}

		if (length == 32)
			return String.format("%0" + (this.length / 4) + "X", (Integer) Float.floatToIntBits(Float.valueOf(value)))
					.substring(0, (this.length / 4));

		return String.format("%0" + (this.length / 4) + "X", (Integer) ((JSpinner) component).getValue()).substring(0,
				(this.length / 4));
	}

	public int getIndex() {
		return this.index;
	}

	public void update() {
		System.out.println(this.getLabelText().split(":")[0] + " didn't override update()");
	}
	
	public boolean shouldUpdate() {
		return Editor.getInstance().hostSettingsManager.exists();
	}

}
