package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory;
import static club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory.*;

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
	Fortegreen(12, Free);
	
	
	Colors(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Color, category);
	}
}
