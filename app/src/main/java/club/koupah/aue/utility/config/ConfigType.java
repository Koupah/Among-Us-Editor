package club.koupah.aue.utility.config;

import java.io.BufferedWriter;
import java.io.IOException;

public enum ConfigType {
	
	PlayerPrefs(0),
	LookAndFeel(1),
	Scheme(2),
	CustomColors("aueCC:", 3),
	RGBSpeed("aueRGBS:", 4),
	AOT("aueAOT:", 5),
	CustomResolution("aueCR:",6), //rip this stuff, gotta get around to it
	Resolution("aueResolution:",7), //read above
	DiscordRP("aueDiscordRP:", 8),
	SmoothScroll("aueSmoothScroll:", 9),
	ConfigProfile(10);
	
	
	String lineStart;
	int lineNumber;
	
	ConfigType(int lineNumber) {
		this("",lineNumber);
	}
	
	ConfigType(String lineStart, int lineNumber) {
		this.lineStart = lineStart;
		this.lineNumber = lineNumber;
	}
	
	public static boolean isSetting(ConfigType type, String line, int lineNum) {
		return (line.startsWith(type.lineStart) && lineNum == type.lineNumber);
	}
	
	public void write(BufferedWriter br, String value) throws IOException {
		br.write(this.lineStart + value);
		br.newLine();
	}
	
}
