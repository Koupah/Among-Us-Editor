package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory;
import static club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory.*;
public enum Pets {

	None(0, Paid),
	Green_Alien(1, Paid),
	Mini_Crewmate(2, Paid),
	Blue_Alien_Dog(3, Paid),
	Male_Stickmin(4, Paid),
	Hamster_Pet(5, Paid),
	Robot(6, Paid),
	UFO(7, Paid),
	Female_Stickmin(8, Paid),
	Bed_Crab_1(9, Paid),
	Bed_Crab_2(10, Paid);
	
	Pets(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Pet, category);
	}
}

