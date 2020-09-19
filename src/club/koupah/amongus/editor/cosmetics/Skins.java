package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

public enum Skins {

	None(0),
	Astronaut(1),
	Captain(2),
	Mechanic(3),
	Military(4),
	Police(5),
	Scientist(6),
	Black_Suit(7),
	White_Suit(8),
	Wall_Guard(9),
	Orange_Karate(10),
	Weird_Blue_Suit(11),
	Construction_Worker(12),
	Overalls(13),
	Puffer_Jacket(14),
	Indiana_Jones(15);
	
	Skins(int ID) {
		new Cosmetic(this,ID,CosmeticType.Skin);
	}
}
