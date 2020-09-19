package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;

public enum Hats {
	
	None(0),

	Brain_Slug(3),
	Bush_Hat(4),
	Captain_Hat(5),
	Double_Top_Hat(6),
	Flowerpot_Hat(7),
	Goggles(8),
	Hard_Hat(9),
	Military_Hat(10),
	Paper_Hat(11),
	Police_Hat(13),
	Stethoscope(14),
	Towel_Wizard(16),
	Ushanka(17),
	Viking(18),
	Wall_Cap(19),

	Snowman(20),
	Antlers(21),
	Christmas_Lights(22),
	Santa_Hat(23),
	Christmas_Tree(24),
	Present_Box(25),
	Candy_Canes(26),
	Elf_Hat_and_Ears(27),

	White_Hat(29),

	Headphones(45),
	Gas_Mask(46),
	Hat_and_Glasses(48),
	
	Cat_Hat(61),
	Bat_Wings(62),
	Devil_Horns(63),
	Mohawk(64),
	Pumpkin(65),
	Spooky_Paper_Bag(66),
	Witch_Hat(67),
	Wolf_Ears(68),
	Pirate_Hat(69),
	Plague_Mask(70),
	Sword_in_Head(71),
	Jason_Mask(72),

	Miner_Hat(73),
	Blue_Beanie(74),
	Icy_Cowboy_Hat(75);	
	Hats(int ID) {
		new Cosmetic(this,ID,CosmeticType.Hat);
	}

}
