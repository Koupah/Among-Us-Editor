package club.koupah.amongus.editor.settings.cosmetics;

import static club.koupah.amongus.editor.settings.cosmetics.Cosmetic.CosmeticCategory.*;

import club.koupah.amongus.editor.settings.cosmetics.Cosmetic.CosmeticCategory;
import club.koupah.amongus.editor.settings.cosmetics.Cosmetic.CosmeticType;

/*
 * 
 * Images sourced from https://among-us.fandom.com/wiki/Skins%2C_Hats%2C_and_Pets
 * 
 */

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
	Wall_Guard_Suit(9,Free),
	MIRA_Hazmat(10,Paid),
	MIRA_Security_Guard(11,Paid),
	Tarmac(12,Paid),
	Miner(13,Paid),
	Winter(14,Paid),
	Archae(15,Paid);
	
	Skins(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Skin, category);
	}
}
