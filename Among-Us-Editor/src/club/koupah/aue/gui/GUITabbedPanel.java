package club.koupah.aue.gui;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class GUITabbedPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1214414762924116340L;

	
	public GUITabbedPanel() {
		this.setOpaque(true);
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
	
	public void updateUI(final Color foreground) {
		System.out.println("Update UI");
		/*setUI(new BasicTabbedPaneUI() {
		   @Override
		   protected void installDefaults() {
	
		       highlight = Color.BLACK;
		       lightHighlight = Color.BLACK;
		       shadow = Color.BLACK;
		       darkShadow = Color.BLACK;
		       focus = Color.BLACK;
		   }
		});*/
		
		setUI(new BasicTabbedPaneUI() {
		   @Override
		   protected void installDefaults() {
		       super.installDefaults();
		       highlight = foreground;
		       lightHighlight = foreground;
		       shadow = foreground;
		       darkShadow = foreground;
		       focus = foreground;
		   }
		});

	}
}
