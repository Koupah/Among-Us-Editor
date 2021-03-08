package club.koupah.aue.utility.playerprefs;

public enum Indexes {
	
	 name(0),
	 control(1),
	 color(2),
	 hat(10),
	 sfx(11),
	 music(12),
	 pet(16),
	 skin(15),
	 censorChat(17),
	 language(18),
	 vsync(19),
	 chatType(27);
	
	
	int index;
	Indexes(int index) {
		this.index = index;
	}
	
	public int index() {
		return this.index;
	}
	
}
