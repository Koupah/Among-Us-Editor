package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

public enum Pets {

	None(0),
	Green_Alien(1),
	Mini_Crewmate(2),
	Blue_Alien_Dog(3),
	Male_Stickmin(4),
	Hamster_Pet(5),
	Robot(6),
	UFO(7),
	Female_Stickmin(8),
	Bed_Crab_1(9),
	Bed_Crab_2(10);
	
	Pets(int ID) {
		new Cosmetic(this,ID,CosmeticType.Pet);
	}
}

