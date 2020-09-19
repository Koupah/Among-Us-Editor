package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory;
import static club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory.*;

public enum Skins {

	None(0, Free),
	Astronaut(1,Free),
	Captain(2,Free),
	Mechanic(3,Free),
	Military(4,Free),
	Police(5,Free),
	Scientist(6,Free),
	Black_Suit(7,Free),
	White_Suit(8,Free),
	Wall_Guard(9,Free),
	Orange_Karate(10,Paid),
	Weird_Blue_Suit(11,Paid),
	Construction_Worker(12,Paid),
	Overalls(13,Paid),
	Puffer_Jacket(14,Paid),
	Indiana_Jones(15,Paid);
	
	Skins(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Skin, category);
	}
}
