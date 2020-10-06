package club.koupah.aue.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.gui.types.GUIComponent;
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
			updateColorScheme(true);

		// We call update color scheme, so just put save in there
		// saveConfig();
	}

	void saveConfig() {
		instance.configManager.saveConfig();
	}

	// Method for turning dark colors into gray
	Color noBlack(Color input) {
		int red = input.getRed();
		int green = input.getGreen();
		int blue = input.getBlue();

		if (red + green + blue < 140) {
			red = Math.max(red, 70);
			green = Math.max(red, 70);
			blue = Math.max(red, 70);
		}
		return new Color(red, green, blue);
	}

	public void updateColorScheme(final boolean save) {
		final GUIScheme scheme = instance.configManager.getScheme();

		instance.configManager.setScheme(scheme);
		instance.contentPanel.setForeground(scheme.getForeground());
		instance.contentPanel.setBackground(scheme.getBackground());

		Color noBlack = noBlack(scheme.getBackground());

		// Bunch of UI manager stuff
		UIManager.put("TabbedPane.contentOpaque", true);

		UIManager.put("TabbedPane.selected", noBlack); // I just don't want this stuff to be pitch black
		UIManager.put("TabbedPane.selectedBackground", noBlack);

		UIManager.put("TabbedPane.unselectedForeground", scheme.getForeground());

		for (Component component : instance.contentPanel.getComponents()) {
			String lnfName = UIManager.getLookAndFeel().getName();

			fixComponent(component, lnfName, scheme); // fix windows components

			if (component instanceof Container) {
				loop((Container) component, scheme, lnfName);
			} else if (component instanceof JButton) {
				((JButton) component).setOpaque(true);
			}
		}

		// Save before updating ui :p
		if (save)
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
				if (save) // Only need to do this if we saving (basically NON RGB) as it causes the UI to
								// reset basically
					SwingUtilities.updateComponentTreeUI(instance);
				// This needs to be set after the swing update

				instance.tabbedPanel.updateUI(scheme.getForeground());
			}
		});
	}

	void loop(Container container, GUIScheme scheme, String lnfName) {
		for (Component c : container.getComponents()) {

			fixComponent(c, lnfName, scheme);

			if (c instanceof Container) {
				loop((Container) c, scheme, lnfName);
			} else if (c instanceof JButton) {
				((JButton) c).setOpaque(true);
			}
		}
	}

	void fixComponent(Component c, String lnfName, GUIScheme scheme) {
		c.setForeground(scheme.getForeground());
		c.setBackground(scheme.getBackground());
		if ((c instanceof JButton || c instanceof JComboBox)
				&& (lnfName.equals("Windows") || lnfName.equals("Mac OS X"))) {
			c.setForeground(Color.BLACK);

			if (c instanceof JComboBox) // Fixes windows Combo Box from having black background
				c.setBackground(Color.WHITE);
			else c.setBackground(scheme.getBackground());

		} else if (c instanceof JTextField) {
			if (lnfName.equals("Nimbus")) // Fixes nimbus text fields
				c.setBounds(c.getX(), c.getY(), c.getWidth(), GUIComponent.componentHeight + 4);
			else if (c.getHeight() != GUIComponent.componentHeight)
				c.setBounds(c.getX(), c.getY(), c.getWidth(), GUIComponent.componentHeight);
		}

		// Don't use else if as this needs to apply to the windows buttons too
		if (c instanceof JButton) {
			if (lnfName.equals("CDE/Motif"))
				c.setBounds(c.getX(), c.getY(), c.getWidth(), GUIComponent.componentHeight + 13);
			else if (c.getHeight() != GUIComponent.componentHeight)
				c.setBounds(c.getX(), c.getY(), c.getWidth(), GUIComponent.componentHeight);
		}
	}

}