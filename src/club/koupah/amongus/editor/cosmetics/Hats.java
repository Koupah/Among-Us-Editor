package club.koupah.amongus.editor.cosmetics;

import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory;
import static club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory.*;

public enum Hats {
	
	None(0, Free),
	Astronaut(1, Free),
	Cap(2, Free),
	Brain_Slug(3, Free),
	Bush_Hat(4, Free),
	Captain_Hat(5, Free),
	Double_Top_Hat(6, Free),
	Flowerpot_Hat(7, Free),
	Goggles(8, Free),
	Hard_Hat(9, Free),
	Military_Hat(10, Free),
	Paper_Hat(11, Free),
	Party_Hat(12, Free),
	Police_Hat(13, Free),
	Stethoscope(14, Free),
	Top_Hat(15, Free),
	Towel_Wizard(16, Free),
	Ushanka(17, Free),
	Viking(18, Free),
	Wall_Cap(19, Free),

	Snowman(20,Holiday),
	Antlers(21,Holiday),
	Christmas_Lights(22,Holiday),
	Santa_Hat(23,Holiday),
	Christmas_Tree(24,Holiday),
	Present_Box(25,Holiday),
	Candy_Canes(26,Holiday),
	Elf_Hat_and_Ears(27,Holiday),

	Yellow_Party_Hat(28, Free),
	White_Hat(29, Free),
	Crown(30, Free),
	Eyebrows(31, Free),
	Angel_Halo(32, Free),
	Elf_Cap(33, Free),
	Flat_Cap(34, Free),
	Plunger(35, Free),
	Snorkel(36, Free),
	Stickmin(37, Free),
	Straw_Hat(38, Free),
	Sheriff_Hat(39, Free),
	Eyeball_Lamp(40, Free),
	Toilet_Paper(41, Free),
	Toppat_Clan_Leader_Hat(42, Free),
	Black_Fedora(43, Free),
	Ski_Goggles(44, Free),

	Headphones(45,Paid),
	Gas_Mask(46,Paid),
	Medical_Mask(47, Free),
	Hat_and_Glasses(48,Paid),

	Safari_Hat(49, Free),
	Banana_Peel(50, Free),
	Beanie(51, Free),
	Bear_Ears(52, Free),
	Cheese(53, Free),
	Cherry(54, Free),
	Egg(55, Free),
	Green_Fedora(56, Free),
	Flamingo(57, Free),
	Flower(58, Free),
	Knight_Helmet(59, Free),
	Plant_Sprout(60, Free),
	
	Cat_Hat(61,Halloween),
	Bat_Wings(62,Halloween),
	Devil_Horns(63,Halloween),
	Mohawk(64,Halloween),
	Pumpkin(65,Halloween),
	Spooky_Paper_Bag(66,Halloween),
	Witch_Hat(67,Halloween),
	Wolf_Ears(68,Halloween),
	Pirate_Hat(69,Halloween),
	Plague_Mask(70,Halloween),
	Sword_in_Head(71,Halloween),
	Jason_Mask(72,Halloween),

	Miner_Hat(73,Paid),
	Blue_Beanie(74,Paid),
	Icy_Cowboy_Hat(75,Paid),

	Antenna(76, Free),
	Balloon(77, Free),
	Bird_Nest(78, Free),
	Black_Belt(79, Free),
	Caution_Sign(80, Free),
	Chefs_Hat(81, Free),
	Hat(82, Free),
	Dorag(83, Free),
	Dum_Note(84, Free),
	Fez(85, Free),
	Generals_Hat(86, Free),
	Black_Hair(87, Free),
	Detectives_Hat(88, Free),
	Military_Helmet(89, Free),
	Mini_Crewmate(90, Free),
	Ninja_Mask(91, Free),
	Horns(92, Free),
	Mini_Snow_Crewmate(93, Free);

	
	Hats(int ID, CosmeticCategory category) {
		new Cosmetic(this,ID,CosmeticType.Hat, category);
	}

}
