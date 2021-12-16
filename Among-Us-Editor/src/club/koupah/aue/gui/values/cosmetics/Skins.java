package club.koupah.aue.gui.values.cosmetics;

import static club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory.*;

import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory;
import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticType;

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
	Police(skin_Police,Free),
	Scientist(6,Free),
	Black_Suit(7,Free),
	White_Suit(8,Free),
	Wall_Guard_Suit(9,Free),
	MIRA_Hazmat(10,Paid),
	MIRA_Security_Guard(11,Paid),
	Tarmac(12,Paid),
	Underminer(skin_Miner,Paid),
	Winter(14,Paid),
	Archaeologist(15,Paid),
	CCC(16, Paid),
	Prisoner(17, Paid),
	Bling_Bling(skin_Bling, Paid),
	Right_Hand_Man_Reborn(18, Paid);
	
	Skins(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Skin, category);
	}
}
