package club.koupah.amongus.editor.cosmetics;

import java.util.ArrayList;
import java.util.List;

public class Cosmetic {

	String friendlyName;
	String name;
	int id;

	public static ArrayList<Cosmetic> hats = new ArrayList<Cosmetic>();
	public static ArrayList<Cosmetic> pets = new ArrayList<Cosmetic>();
	public static ArrayList<Cosmetic> skins = new ArrayList<Cosmetic>();
	public static ArrayList<Cosmetic> colors = new ArrayList<Cosmetic>();

	public Cosmetic(Enum<?> cosmetic, int id, CosmeticType type) {
		this.friendlyName = cosmetic.name().replaceAll("_", " ");
		this.name = cosmetic.name();
		this.id = id;
		addCosmetic(type, this);
	}

	public void addCosmetic(CosmeticType type, Cosmetic cosmetic) {
		switch (type) {
		case Hat: {
			hats.add(cosmetic);
			break;
		}
		case Pet: {
			pets.add(cosmetic);
			break;
		}
		case Skin: {
			skins.add(cosmetic);
			break;
		}
		case Color: {
			colors.add(cosmetic);
			break;
		}
		default: {
			System.out.println(String.format("Must've been an error? Name is %s and the type is %s",
					cosmetic.getName(true), type.name()));
		}
		}
	}

	public static enum CosmeticType {
		Hat, Pet, Skin, Color;
	}

	public String getName(boolean friendlyName) {
		return friendlyName ? this.friendlyName : this.name;
	}

	public int getID() {
		return this.id;
	}

	public static List<String> getItems(CosmeticType type) {
		ArrayList<String> items = new ArrayList<String>();
		switch (type) {
		case Hat: {
			for (Cosmetic hat : hats)
				items.add(hat.friendlyName);
			return items;
		}
		case Pet: {
			for (Cosmetic pet : pets)
				items.add(pet.friendlyName);
			return items;
		}
		case Skin: {
			for (Cosmetic skin : skins)
				items.add(skin.friendlyName);
			return items;
		}
		case Color: {
			for (Cosmetic color : colors)
				items.add(color.friendlyName);
			return items;
		}

		}
		return null;
	}

	public static String getItem(CosmeticType type, String stringID) {
		int id = Integer.valueOf(stringID);
		// Wouldn't need the switch if it wasn't for overlapping IDs
		switch (type) {
		case Hat: {
			for (Cosmetic hat : hats)
				if (hat.getID() == id)
					return hat.getName(true);
			return "Some Hat (ID " + id + ")";
		}
		case Pet: {
			for (Cosmetic pet : pets)
				if (pet.getID() == id)
					return pet.getName(true);
			return "Some Pet (ID " + id + ")";
		}
		case Skin: {
			for (Cosmetic hat : skins)
				if (hat.getID() == id)
					return hat.getName(true);
			return "Some Skin (ID " + id + ")";
		}
		case Color: {
			for (Cosmetic color : colors)
				if (color.getID() == id) 
					return color.getName(true);
			return "Some Color (ID " + id + ")";
		}
		}

		return "unknown";
	}
	public static String getIDbyName(CosmeticType type, String name) {
		// Wouldn't need the switch if it wasn't for overlapping IDs
		switch (type) {
		case Hat: {
			for (Cosmetic hat : hats)
				if (hat.getName(true).equals(name))
					return String.valueOf(hat.getID());
			return "ErrorFinding";
		}
		case Pet: {
			for (Cosmetic pet : pets)
				if (pet.getName(true).equals(name))
					return String.valueOf(pet.getID());
			return "ErrorFinding";
		}
		case Skin: {
			for (Cosmetic skin : skins)
				if (skin.getName(true).equals(name))
					return String.valueOf(skin.getID());
			return "ErrorFinding";
		}
		case Color: {
			for (Cosmetic color : colors)
				if (color.getName(true).equals(name))
					return String.valueOf(color.getID());
			return "ErrorFinding";
		}
		}

		return "ErrorFinding";
	}
}
