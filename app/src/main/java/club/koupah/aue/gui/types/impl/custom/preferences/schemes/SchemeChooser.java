package club.koupah.aue.gui.types.impl.custom.preferences.schemes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIManager;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.gui.values.GUIScheme;
import club.koupah.aue.utility.PopUp;

public class SchemeChooser extends GUIComponent {

	String[] allSchemes;

	Thread RGB;

	JSlider rgbSpeed;

	GUIScheme current;

	public SchemeChooser(final JLabel label, JComboBox<String> component) {
		super(label, component);

		component.setMaximumRowCount(14);

		rgbSpeed = new JSlider();
		allSchemes = GUIScheme.getNames();

		for (String scheme : allSchemes) {
			component.addItem(scheme);
		}

		current = Editor.getInstance().configManager.getScheme();

		if (current.isRGB()) {
			startRGB();
			rgbSpeed.setVisible(true);
		} else {
			rgbSpeed.setVisible(false);
		}

		component.setSelectedItem(current.getName());

		label.setText(SchemeChooser.this.labelText + current.getName());

		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) SchemeChooser.this.component).getSelectedItem();
				GUIScheme scheme = GUIScheme.findByName(selected);
				if (!scheme.isRGB() && RGB != null && RGB.isAlive()) {
					RGB.interrupt();
					rgbSpeed.setVisible(false);
				}
				current = scheme;
				//Have to put this after assigning scheme to current
				if (current.isRGB()) {
					if (RGB == null || !RGB.isAlive()) {
						startRGB();
					}
					rgbSpeed.setVisible(true);
				}

				Editor.getInstance().configManager.setScheme(current);
				label.setText(SchemeChooser.this.labelText + selected);
				Editor.getInstance().guiManager.updateColorScheme(true);
			}
		});
	}

	public void startRGB() {
		//Make sure we get rid of the previous RGB thread
		if (RGB != null && RGB.isAlive()) {
			RGB.interrupt();
			RGB = null;
		}
		RGB = new Thread() {
			@Override
			public void run() {
				float index = 0;
				GUIManager guiManager = Editor.getInstance().guiManager;
				while (current.isRGB()) {
					try {
						// Prevent the changing of non rgb schemes
						if (!current.isRGB())
							new PopUp("The current scheme isn't RGB, this shouldn't happen!\n" + current.getName()
									+ " is the current scheme!");

						index = (float) ((index + (float) ((float) rgbSpeed.getValue() / (float) 1000)) % 1);
						current.setForeground(Color.getHSBColor(index, 1.0f, 1.0f));
						guiManager.updateColorScheme(false);
						Thread.sleep(50 - (rgbSpeed.getValue() / 2)); // I believe 50 to be a reasonable number, 20 colors a
																						// second. Give faster speeds more colors
					} catch (Exception e) { // ignore sleep interrupted exception
						// e.printStackTrace();
					}
				}
			}
		};
		RGB.start();
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);

		rgbSpeed.setMinimum(1);
		rgbSpeed.setMaximum(50);
		rgbSpeed.setValue(Editor.getInstance().configManager.getRGBSpeed());
		rgbSpeed.setBounds(130, 20 + (index * Editor.guiSpacing), 120, 20);
		rgbSpeed.setToolTipText("RGB Speed");
		rgbSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rgbSpeed != null) {
					Editor.getInstance().configManager.setRGBSpeed(rgbSpeed.getValue()); //update the config value
				}
			}
		});

		contentPane.add(rgbSpeed);
	}

}
