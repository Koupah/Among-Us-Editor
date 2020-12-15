package club.koupah.aue.gui.values;

import java.awt.Color;

public enum GUIScheme {
	
	Normal(Color.BLACK,  new Color(238, 238, 238)),
	Light(Color.BLACK,  Color.WHITE),
	Dark(Color.WHITE,  Color.BLACK),
	Hackerman(Color.GREEN,  Color.BLACK),
	Dull(Color.WHITE, new Color(173, 165, 171)),
	Neon(Color.CYAN, Color.BLACK),
	Ocean(Color.WHITE, new Color(0,130,255)),
	RGB_Black(Color.RED, Color.BLACK, true),
	RGB_White(Color.RED, Color.WHITE, true),
	Custom(Color.WHITE, Color.BLACK);
	
	Color foreground;
	Color background;
	
	String name;
	boolean rgb = false;
	GUIScheme(Color fore, Color back) {
		this.foreground = fore;
		this.background = back;
		this.name = name().replace("_", " ");
	}
	GUIScheme(Color fore, Color back, boolean RGB) {
		this.foreground = fore;
		this.background = back;
		this.name = name().replace("_", " ");
		this.rgb = RGB;
	}
	public Color getForeground() {
		return this.foreground;
	}
	
	public Color getBackground() {
		return this.background;
	}
	
	public static String[] getNames() {
		String[] names = new String[values().length];
		int i = 0;
		for (GUIScheme scheme : values()) {
			names[i++] = scheme.getName();
		}
		return names;
	}
	
	public static GUIScheme findByName(String name) {
		for (GUIScheme scheme : values()) {
			if (scheme.name().equalsIgnoreCase(name.replace(" ", "_")))
				return scheme;
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public void setForeground(Color newForeground) {
		this.foreground = newForeground;
	}
	public void setBackground(Color newBackground) {
		this.background = newBackground;
	}

	public boolean isRGB() {
		return this.rgb;
	}
}
