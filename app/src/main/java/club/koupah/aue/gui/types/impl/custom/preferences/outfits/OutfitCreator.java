package club.koupah.aue.gui.types.impl.custom.preferences.outfits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Outfit;

public class OutfitCreator extends GUIComponent {

	String[] allOutfitNames;

	JButton create;

	// Going to use this to load and delete profiles, saving/sharing will be another
	// class
	public OutfitCreator(final JLabel label, JTextField component) {
		super(label, component);
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();

		create = new JButton("Create Outfit");
		create.setToolTipText("Creates an Outfit with the current cosmetic settings!");

		label.setBounds(10, 15 + (index * Editor.guiSpacing), 100, 30);
		component.setBounds(260, 20 + (index * Editor.guiSpacing), 160, 20);

		create.setBounds(110, 20 + (index * Editor.guiSpacing), 140, 20);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = (String) ((JTextField) OutfitCreator.this.component).getText();

				if (Outfit.outfitExists(name)) {
					((JTextField) OutfitCreator.this.component).setText("");
					new PopUp("That outfit already exists!", false);
					return;
				} else {
					// the name but with spaces replaced to check true length

					if (Editor.getOutfitManager().outfitNameChecks(name, false)) {

						((JTextField) OutfitCreator.this.component).setText("");
						String[] settings = Editor.getInstance().outfitManager.makeOutfitConfig(name);
						new Outfit(settings);

						Editor.getInstance().outfitManager.updateOutfits(name);
						Editor.getInstance().configManager.saveConfig();
					}
				}
			}

		});

		// I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);

		contentPane.add(this.component);
		contentPane.add(create);
	}


}
