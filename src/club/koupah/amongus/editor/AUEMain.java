package club.koupah.amongus.editor;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.amongus.editor.settings.cosmetics.Colors;
import club.koupah.amongus.editor.settings.cosmetics.Hats;
import club.koupah.amongus.editor.settings.cosmetics.Pets;
import club.koupah.amongus.editor.settings.cosmetics.Skins;

public class AUEMain {
	
	//I should remove the getInstance from inside Editor and make it AUEMain.getInstance()
	//but im lazy
	static Editor editor;
	
	public static void main(String[] args) {
		
		// Idk how to get them to initialize their values cause am big noob
		Hats.values();
		Pets.values();
		Skins.values();
		Colors.values();
		
		
		editor = new Editor();
		
		//Default look and feel
		editor.lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		
			// Run this first, before me do any GUI stuff
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getClassName().contains(editor.desiredLookAndFeel)) {
					editor.lookAndFeel = info.getClassName();
					editor.windows = true;
					break;
				}
			}
		
			if (editor.windows) {
				try {
					UIManager.setLookAndFeel(editor.lookAndFeel);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					new PopUp("Your system reported itself as having the Windows Look & Feel but didn't work!\nResorting to basic java L&F\n"
							+ e1.getMessage(), true);
				}
			} else {
				/*
				 * 
				 * Don't set any look & feel, Sorry non-windows users.
				 * You'll have to deal with Java's ugly L&F until I write up my own.
				 * (Unless someone wants to provide a nice Linux/Mac L&F :P)
				 * 
				 */
			}
			
			//Finish setting the window up
			editor.setupWindow();
						
			//Show the frame! Where the magic happens
			editor.setVisible(true);
			
			//Run update check, this is a seperate thread so it won't interrupt the main thread
			editor.runUpdateCheck();
	}
	
}
