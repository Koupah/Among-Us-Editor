package club.koupah.amongus.editor.gui;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class GUITabbedPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1214414762924116340L;

	
	public GUITabbedPanel() {
		
		//Just basically do all the black outlines for all look & feels
		setUI(new BasicTabbedPaneUI() {
			   @Override
			   protected void installDefaults() {
			       super.installDefaults();
			       highlight = Color.BLACK;
			       lightHighlight = Color.BLACK;
			       shadow = Color.BLACK;
			       darkShadow = Color.BLACK;
			       focus = Color.BLACK;
			   }
			});
		
		
	}
}
