package club.koupah.aue.gui.settings;

import java.awt.Color;

public enum GUIScheme {
	
	Light(Color.BLACK,  new Color(238, 238, 238)),
	Dark(Color.WHITE,  Color.BLACK),
	Hackerman(Color.GREEN,  Color.BLACK),
	Candy_1(Color.CYAN, new Color(255,77,187)),
	Candy_2(new Color(255,0,170), new Color(0,255,55)),
	Dull_1(new Color(173, 165, 171), Color.white),
	Dull_2(Color.white, new Color(173, 165, 171));
	
	
	Color foreground;
	Color background;
	
	String name;
	
	GUIScheme(Color fore, Color back) {
		this.foreground = fore;
		this.background = back;
		this.name = name().replace("_", " ");
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
	
}
