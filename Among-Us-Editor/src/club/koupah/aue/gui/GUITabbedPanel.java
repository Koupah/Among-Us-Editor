package club.koupah.aue.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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

				// Nice 3 pixel border, although it uses ugly blue ):
				this.contentBorderInsets.top = 3;
				this.contentBorderInsets.left = 3;
				this.contentBorderInsets.right = 3;
				this.contentBorderInsets.bottom = 3;
			}
		});

	}

	public int findTabByName(String title) {
		int tabCount = getTabCount();
		for (int i = 0; i < tabCount; i++) {
			String tabTitle = getTitleAt(i);
			if (tabTitle.equals(title))
				return i;
		}
		return -1;
	}

	@Override
	public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      super.paint(g2d);
	}
}
