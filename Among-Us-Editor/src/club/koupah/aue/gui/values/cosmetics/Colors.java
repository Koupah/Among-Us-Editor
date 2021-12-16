package club.koupah.aue.gui.values.cosmetics;

import static club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory.*;

import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticCategory;
import club.koupah.aue.gui.values.cosmetics.Cosmetic.CosmeticType;

/*
 * 
 * Images sourced from https://among-us.fandom.com/wiki/Category:Colors
 * 
 */

public enum Colors {

	Random_Every_Launch(-1, Free),
	Red(0, Free),
	Dark_Blue(1, Free),
	Dark_Green(2, Free),
	Pink(3, Free),
	Orange(4, Free),
	Yellow(5, Free),
	Black(6, Free),
	White(7, Free),
	Purple(8, Free),
	Brown(9, Free),
	Cyan(10, Free),
	Lime(11, Free),
	Maroon(12, Free),
	Rose(13, Free);

	int id;

	Colors(int ID, CosmeticCategory category) {
		new Cosmetic(this, ID, CosmeticType.Color, category);
		this.id = ID;
	}

	public int getID() {
		return this.id;
	}
}
