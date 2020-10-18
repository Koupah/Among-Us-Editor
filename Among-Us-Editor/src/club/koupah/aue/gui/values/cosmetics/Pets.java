package club.koupah.aue.gui.values.cosmetics;

import static club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory.*;

import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory;
import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticType;

/*
 * 
 * Images sourced from https://among-us.fandom.com/wiki/Skins%2C_Hats%2C_and_Pets
 * (With the exclusive of the UFO, Ellie, Dog and Bedcrab)
 * 
 */

public enum Pets {

	None(0, Paid),
	Brain_Slug(1, Paid),
	Mini_Crewmate(2, Paid),
	Dog(3, Paid),
	Henry(4, Paid),
	Hamster(5, Paid),
	Robot(6, Paid),
	UFO(7, Paid),
	Ellie(8, Paid),
	Squiq(9, Paid),
	Bedcrab(10, Paid);
	
	Pets(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Pet, category);
	}
}

