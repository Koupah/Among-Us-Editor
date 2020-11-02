package club.koupah.aue.utility.config;

import java.util.ArrayList;

import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.playerprefs.Indexes;

public class Outfit {

	public static ArrayList<Outfit> profiles = new ArrayList<Outfit>();

	String profileName;

	String username;
	String hat;
	String color;
	String skin;
	String pet;

	public Outfit(String config) {
		// Config should be:
		// Profile Name,username,hat,color,skin,pet
		// Example: Troll,Koupah,4,2,6,3

		String[] settings = config.split(",");

		if (settings.length < 6) {
			new PopUp("A profile wasn't configured correctly, skipping it!", false);
			return;
		} else {

			profileName = settings[0];
			username = settings[1];
			hat = settings[2];
			color = settings[3];
			skin = settings[4];
			pet = settings[5];

			Outfit.profiles.add(this);
		}
	}

	public Outfit(String[] settings) {
		this(String.join(",", settings));
	}

	public String getOutfitName() {
		return this.profileName;
	}

	public static boolean outfitExists(String name) {
		for (Outfit profile : Outfit.profiles) {
			if (profile.getOutfitName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	public static boolean deleteOutfit(String name) {
		for (Outfit profile : Outfit.profiles) {
			if (profile.getOutfitName().equalsIgnoreCase(name)) {
				profiles.remove(profile);
				return true;
			}
		}
		return false;
	}

	public String getConfigLine() {
		return combine(profileName, username, hat, color, skin, pet);
	}

	public String combine(String... combination) {
		return String.join(",", combination);
	}

	public static String[] getAllOutfitNames() {
		String[] names = new String[profiles.size()];
		int index = 0;
		for (Outfit profile : Outfit.profiles) {
			names[index] = profile.getOutfitName();
			index++;
		}
		return names;
	}

	public static String[] newSettingsArray() {
		return new String[6];
	}
	
	public static Outfit getOutfitByConfig(String[] settings) {
	 profile:
		for (Outfit profile : Outfit.profiles) {
			int index = 0;
			for (String string : profile.getSettingsArray()) {
				//Skip 0th index, that's the profile name :p
				if (index != 0 && !string.equals(settings[index]))
					continue profile;
				
				index++;
			}
			return profile;
		}	
		return null;
	}
	
	public String[] getSettingsArray() {
		return array(profileName, username, hat, color, skin, pet);
	}
	
	String[] array(String... inputs) {
		return inputs;
	}
	
	public static boolean isOutfitSetting(int settingIndex) {
		return settingIndex == Indexes.name.index() || settingIndex == Indexes.hat.index() || settingIndex == Indexes.color.index() || settingIndex == Indexes.skin.index() || settingIndex == Indexes.pet.index(); 
	}
	
	//Returns the profile index this setting goes into
	public static int getOutfitIndex(int settingIndex) {
		//Wanted to do a switch here, cbf figuring out how to turn it into one
		if (settingIndex == Indexes.name.index()) {
			return 1;
		} else if (settingIndex == Indexes.hat.index()) {
			return 2;
		} else if (settingIndex == Indexes.color.index()) {
			return 3;
		} else if (settingIndex == Indexes.skin.index()) {
			return 4;
		} else if (settingIndex == Indexes.pet.index()) {
			return 5;
		} else return -1;
	}

	public static Outfit getOutfit(String name) {
			for (Outfit profile : Outfit.profiles) {
				if (profile.getOutfitName().equalsIgnoreCase(name))
					return profile;
			}
			return null;
	}

	public void updateSettings(String[] settings) {
		profileName = settings[0];
		username = settings[1];
		hat = settings[2];
		color = settings[3];
		skin = settings[4];
		pet = settings[5];
	}

	public void delete() {
		Outfit.profiles.remove(this);
	}

}
