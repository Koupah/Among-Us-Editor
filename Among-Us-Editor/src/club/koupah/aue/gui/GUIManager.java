package club.koupah.aue.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.utility.PopUp;

public class GUIManager {

	Editor instance;

	public GUIManager(Editor instance) {
		this.instance = instance;
	}

	public void updateLookAndFeel() {
		try {
			UIManager.setLookAndFeel(instance.configManager.getLookAndFeel());
			SwingUtilities.updateComponentTreeUI(instance);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			new PopUp("Look and Feel error?\n" + e.getMessage());
			e.printStackTrace();
		}

		if (instance.tabbedPanel != null)
			updateColorScheme();

		// We call update color scheme, so just put save in there
		// saveConfig();
	}

	void saveConfig() {
		instance.configManager.saveConfig();
	}

	public void updateColorScheme() {
		final GUIScheme scheme = instance.configManager.getScheme();

		instance.configManager.setScheme(scheme);
		instance.panel.setForeground(scheme.getForeground());
		instance.panel.setBackground(scheme.getBackground());

		// Bunch of UI manager stuff
		UIManager.put("TabbedPane.contentOpaque", true);
		UIManager.put("TabbedPane.selected",
				scheme.getBackground().equals(Color.BLACK) ? Color.GRAY : scheme.getBackground());
		UIManager.put("TabbedPane.selectedBackground",
				scheme.getBackground().equals(Color.BLACK) ? Color.GRAY : scheme.getBackground());
		UIManager.put("TabbedPane.unselectedForeground", scheme.getForeground());

		for (Component component : instance.panel.getComponents()) {
			String lnfName = UIManager.getLookAndFeel().getName();
			if ((component instanceof JButton || component instanceof JComboBox) && lnfName.equals("Windows")) {
				component.setForeground(Color.BLACK);

				if (component instanceof JComboBox) // Fixes windows Combo Box from having black background
					component.setBackground(Color.WHITE);
				else component.setBackground(scheme.getBackground());

			} else { // All other components
				component.setForeground(scheme.getForeground());
				component.setBackground(scheme.getBackground());
			}
			if (component instanceof Container) {
				loop((Container) component, scheme);
			} else if (component instanceof JButton) {
				((JButton) component).setOpaque(true);
			}
		}

		// Save before updating ui :p
		saveConfig();

		// Have to use invoke later to attempt to prevent a dumb exception that doesnt
		// even break anything lmao (outofbounds exception for tabbedpaneui)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Probably unnecessary but just incase
				for (int i = 0; i < instance.tabbedPanel.getTabCount(); i++) {
					instance.tabbedPanel.setForegroundAt(i, scheme.getForeground());
					instance.tabbedPanel.setBackgroundAt(i, scheme.getBackground());
				}
				// can probably add a custom border to the tabbedpanel

				SwingUtilities.updateComponentTreeUI(instance);
				// This needs to be set after the swing update
				instance.tabbedPanel.updateUI(scheme.getForeground());
			}
		});
	}

	void loop(Container container, GUIScheme scheme) {
		String lnfName = UIManager.getLookAndFeel().getName();
		for (Component c : container.getComponents()) {

			// The windows Look & Feel doesn't work with background colors on
			// JButton/JComboBox so set the text to black
			if ((c instanceof JButton || c instanceof JComboBox) && lnfName.equals("Windows")) {
				c.setForeground(Color.BLACK);

				if (c instanceof JComboBox) // Fixes windows Combo Box from having black background
					c.setBackground(Color.WHITE);
				else c.setBackground(scheme.getBackground());

			} else { // All other components
				c.setForeground(scheme.getForeground());
				c.setBackground(scheme.getBackground());
			}
			if (c instanceof Container) {
				loop((Container) c, scheme);
			} else if (c instanceof JButton) {
				((JButton) c).setOpaque(true);
			}
		}
	}
}
