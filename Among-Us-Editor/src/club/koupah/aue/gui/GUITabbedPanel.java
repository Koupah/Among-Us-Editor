package club.koupah.aue.gui;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class GUITabbedPanel extends JTabbedPane {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = -1214414762924116340L;

	public GUITabbedPanel() {
		this.setOpaque(true);
	}

	public void updateUI(final Color foreground) {

		setUI(new BasicTabbedPaneUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				highlight = foreground;
				lightHighlight = foreground;
				shadow = foreground;
				darkShadow = foreground;
				focus = foreground;
				
				//Nice 3 pixel border, although it uses ugly blue ):
				this.contentBorderInsets.top = 3;
				this.contentBorderInsets.left = 3;
				this.contentBorderInsets.right = 3;
				this.contentBorderInsets.bottom = 3;
			}
		});

	}
}
