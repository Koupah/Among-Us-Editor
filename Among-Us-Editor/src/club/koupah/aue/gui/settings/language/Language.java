package club.koupah.aue.gui.settings.language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Language {

	Error_None(-1), 
	English(0),
	Spanish(1), 
	Portuguese(2), 
	Korean(3), 
	Russian(4);

	int id;
	String name;

	Language(int ID) {
		this.id = ID;
		this.name = this.name().replace("_", " ");
	}

	public static Language getLanguage(int id) {
		for (Language a : values()) {
			if (a.id == id)
				return a;
		}
		return Error_None;
	}

	public static List<Language> getAllLanguages() {
		// Return all languages except the error language :p
		return new ArrayList<Language>(Arrays.asList(English, Spanish, Portuguese, Korean, Russian));
	}

	public String getName() {
		return this.name;
	}

	public static List<String> getAllLanguagesString() {
		// Return all languages except the error language :p
		return new ArrayList<String>(Arrays.asList("English", "Spanish", "Portuguese", "Korean", "Russian"));
	}

	public String getID() {
		return String.valueOf(this.id);
	}

	public static int getLanguageIDbyName(String name) {
		for (Language lang : values()) {
			if (lang.getName().equals(name))
				return lang.id;
		}
		//Hopefully this doesn't happen l0l
		return 69;
	}

}
