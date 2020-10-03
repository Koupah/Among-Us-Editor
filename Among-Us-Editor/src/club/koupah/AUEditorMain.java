package club.koupah;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.cosmetics.Colors;
import club.koupah.aue.gui.settings.cosmetics.Hats;
import club.koupah.aue.gui.settings.cosmetics.Pets;
import club.koupah.aue.gui.settings.cosmetics.Skins;
import club.koupah.aue.utility.PopUp;

import javax.swing.UnsupportedLookAndFeelException;

public class AUEditorMain {

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	public static String desiredLookAndFeel = "WindowsLookAndFeel";
	
	static double version = 1.452;

	public static String discordLink = "https://www.koupah.club/aueditor";
	
	public static void main(String[] args) {
		
		try {
		// Idk how to get them to initialize their values cause am big noob
		Hats.values();
		Pets.values();
		Skins.values();
		Colors.values();
		
		//Local variable, I'm not going to use it again from outside this class
		Editor editor = new Editor(version);
		
		//Default look and feel
		editor.lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		
			// Run this first, before me do any GUI stuff
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getClassName().contains(desiredLookAndFeel)) {
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
							+ e1.getMessage(), false);
				}
				
				//Because JPanels in tabbed panels are fucked and don't let me change the background color,
				//Set the panels to not be opaque that way we use the original background color
				//UIManager.put("TabbedPane.contentOpaque", false);
				
			} else {
				/*
				 * 
				 * Don't set any look & feel, Sorry non-windows users.
				 * You'll have to deal with Java's ugly L&F until I write up my own.
				 * (Unless someone wants to provide a nice Linux/Mac L&F :P)
				 * 
				 */
					
				
				 //UIManager.put("ToolTip.background", Editor.background);
				 //UIManager.put("ToolTip.border", new BorderUIResource(new LineBorder(Color.BLACK)));
			}
			
			//Setup the config and other stuff
			editor.setupFiles();
		   
			//Finish setting the window up
			editor.setupWindow();
						
			//Show the frame! Where the magic happens
			editor.setVisible(true);
			
			//Run update check, this is a seperate thread so it won't interrupt the main thread
			editor.runUpdateCheck(null);
			
			//Catch any exception that, for whatever reason wasn't already caught
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fatal error, send this to Koupah#5129 on discord!\nMessage: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
