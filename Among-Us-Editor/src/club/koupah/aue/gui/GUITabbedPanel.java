package club.koupah.aue.gui;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import club.koupah.AUEditorMain;

public class GUITabbedPanel extends JTabbedPane {

	private static final long serialVersionUID = -1214414762924116340L;

	public GUITabbedPanel() {
		this.setOpaque(true);
		this.setDoubleBuffered(true);
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (AUEditorMain.usingRichPresence) {
					AUEditorMain.presence.smallImageKey = ((ScrollPanel) getComponentAt(getSelectedIndex())).getGUIPanel()
							.getDiscordImageKey();
					AUEditorMain.presence.smallImageText = "Editing "
							+ ((ScrollPanel) getComponentAt(getSelectedIndex())).getGUIPanel().name;
					AUEditorMain.updatePresence();
				}
			}
		});
	}

	public void updateUI(final Color foreground) {
		UIManager.getDefaults().put("TabbedPane.tabRunOverlay", -1);

		setUI(new BasicTabbedPaneUI() {
			@Override
			protected boolean shouldRotateTabRuns(int i) {
				return false;
			}

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

}
