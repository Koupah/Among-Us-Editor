package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

public enum Skins {

	None(0),
	
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
