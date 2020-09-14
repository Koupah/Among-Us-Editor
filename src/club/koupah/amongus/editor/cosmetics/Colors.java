package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

public enum Colors {

	Random_Every_Launch(-1),
	Red(0),
	Dark_Blue(1),
	Dark_Green(2),
	Pink(3),
	Orange(4),
	Yellow(5),
	Black(6),
	White(7),
	Purple(8),
	Brown(9),
	Cyan(10),
	Lime(11),
	Glitched_Green_Red(12);
	
	
	Colors(int ID) {
		new Cosmetic(this,ID,CosmeticType.Color);
	}
}
