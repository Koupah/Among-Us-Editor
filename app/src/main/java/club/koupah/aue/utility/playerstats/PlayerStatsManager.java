package club.koupah.aue.utility.playerstats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import club.koupah.aue.utility.PopUp;

public class PlayerStatsManager {

	File playerStats;

	boolean exists;

	String[] currentHex;

	String[] newHex;

	public PlayerStatsManager(File playerStats) {
		this.playerStats = playerStats;
		this.exists = playerStats != null && playerStats.exists();

		if (exists) {
			try {
				currentHex = readHex();
				
				if (currentHex == null || currentHex.length < 130) { //Mine was 149
					exists = false;
					new PopUp("Your 'playerStats2' file is invalid,\ntry playing a game then relaunch Among Us Editor\notherwise you won't be able to edit stats!", false);
					return;
				}
				
				newHex = currentHex.clone();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Current playerStats Hex: ");

			int index = 0;
			for (String hex : currentHex) {
				System.out.print(hex + ' ');
				index++;
				if (index % 16 == 0) {
					System.out.println("");
				}
			}

			System.out.println("\n");
		} else {
			System.out.println("playerStats file doesn't exist");
			new PopUp("Your 'playerStats2' file doesn't exist,\nYou won't be able to change your stats!\n\nTo create it, try playing a game of Among Us.", false);
			
		}
	}

	public void setIndex(int index, String value) {
		newHex[index] = value;
	}

	public String[] readHex() throws IOException {
		ArrayList<String> hexValues = new ArrayList<String>();
		FileInputStream fin = new FileInputStream(playerStats);

		int len;
		byte data[] = new byte[16];
		int index = 0;

		while ((len = fin.read(data)) != -1) {
			for (int j = 0; j < len; j++) {
				hexValues.add(String.format("%02X", data[j]));
				index++;
			}
		}
		fin.close();
		String[] hex = new String[index];
		index = 0;
		for (String h : hexValues) {
			hex[index] = h;
			index++;
		}

		return hex;
	}

	public String[] getHex() {
		return this.currentHex;
	}

	public String getHex(int index, int length) {
		if (currentHex == null) {
			return null;
		}
		String toreturn = "";
		for (int i = (length / 8); i > 0; i--) {
			toreturn += this.currentHex[index + i - 1];
		}
		return toreturn;
	}

	public String[] getHexArray(int index, int length) {
		String[] toreturn = new String[length / 8];
		for (int i = 0; i < (0 + (length / 8)); i++)
			toreturn[i] = this.currentHex[index + i];

		return toreturn;
	}

	public void savePlayerStats() {

		try (OutputStream os = new FileOutputStream(playerStats)) {
			int index = 0;
			for (String hex : newHex) {

				if (index % 16 == 0) {
					System.out.println("");
				}
				System.out.print(hex + ' ');
				index++;

				os.write((char) Integer.parseInt(hex, 16));
			}
			os.close();
		} catch (IOException e) {
			System.out.println(String.format("There was an error writing to the file, error message %s", e.getMessage()));
			new PopUp("Error writing to file!\n" + e.getMessage(), true);
		}
		
		this.currentHex = newHex;
		
	}

	public boolean exists() {
		return this.exists;
	}

	public void updateNewHex(String[] hex) {
		this.newHex = hex;
	}
}
