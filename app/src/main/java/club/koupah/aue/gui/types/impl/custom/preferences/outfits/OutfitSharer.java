package club.koupah.aue.gui.types.impl.custom.preferences.outfits;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Base64;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Outfit;

public class OutfitSharer extends GUIComponent {

	String[] allOutfitNames;

	JButton share;

	// Going to use this to load and delete outfits, saving/sharing will be another
	// component
	public OutfitSharer(final JLabel label, JButton component) {
		super(label, component);

		component.setToolTipText("Let's you import and use other peoples outfits!");
		// component is the import outfit button
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				String config = showImport();

				if (config != null) {
					String importName = config.split(",")[0];
					if (Outfit.outfitExists(importName)) {
						new PopUp("You already have a outfit with the name \"" + importName
								+ "\"\nDelete it in order to import this outfit!", false);
						return;
					}

					Outfit imported = new Outfit(config);
					if (imported.getOutfitName() != null) {
						if (Editor.getOutfitManager().outfitNameChecks(imported.getOutfitName(), true)) { //Separate if statement, as this check shows it's own popups
							Editor.getInstance().outfitManager.updateOutfits(imported.getOutfitName());
							Editor.getInstance().configManager.saveConfig();
							new PopUp("Successfully imported the outfit \"" + imported.getOutfitName() + "\"!", false);
						} else {
							imported.delete(); //Delete it from existence 
						}
					} else {
						new PopUp(
								"The outfit you tried importing seemed to be corrupted!\nIf this is a mistake:\ntry again and make sure you copied the share code properly!",
								false);
					}
				}
			}
		});
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();

		share = new JButton("Share Outfit");
		share.setToolTipText("Share the current selected outfit!");

		label.setBounds(10, 15 + (index * Editor.guiSpacing), 100, 30);
		component.setBounds(260, 20 + (index * Editor.guiSpacing), 160, 20);

		share.setBounds(110, 20 + (index * Editor.guiSpacing), 140, 20);
		share.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Outfit outfit;
				if ((outfit = Editor.getInstance().outfitManager.current) != null) {
					showShareCode(outfit.getConfigLine(), outfit.getOutfitName());
				} else {
					new PopUp("This outfit isn't shareable!", false);
				}
			}
		});

		// I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);

		contentPane.add(this.component);
		contentPane.add(share);
	}

	static void showShareCode(String share, String outfitName) {

		share = Base64.getEncoder().encodeToString(share.getBytes()).replaceAll("=", "AUEQL").replaceAll("/", "AUESLASH");

		Object[] options = { "Done" };

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		final JTextField shareText = new JTextField("aue" + share);
		shareText.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (arg0.getButton() == 3) // 3 is right click
					try {
						StringSelection sel = new StringSelection(shareText.getText());
						Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
						clip.setContents(sel, null);
					} catch (HeadlessException e1) {
						// Basically, if theres an error just don't copy
					}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		shareText.setHorizontalAlignment(JTextField.CENTER);
		shareText.setEditable(false);
		panel.add(new JLabel("Sharing Outfit: " + outfitName));
		panel.add(new JLabel("Copy the text below, anyone can import it to get your outfit!"));

		if (Editor.getInstance().windowsOS)
			panel.add(new JLabel("You can also right click the text box to copy the share code"));

		panel.add(shareText);

		JOptionPane.showOptionDialog(null, panel, "Sharing " + outfitName, JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);
	}

	static String showImport() {

		Object[] options = { "Done" };

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		final JTextField shareText = new JTextField("");

		// Not removing this incase it does work on non windowsOS, but we catch all
		// exceptions so if it doesn't, oh well
		shareText.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (arg0.getButton() == 3) // 3 is right click
					try {
						String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
								.getData(DataFlavor.stringFlavor);
						shareText.setText(clipboard); // To prevent accidentally pasting multiple times, we don't append
					} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
						// Basically, if theres an error just don't paste
					}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		shareText.setHorizontalAlignment(JTextField.CENTER);
		shareText.setEditable(true);
		panel.add(new JLabel("Put the share code below to import it!"));

		if (Editor.getInstance().windowsOS) // Not sure if pasting works on
			// non-windows, just to be safe :P
			panel.add(new JLabel("You can also right click the text box to paste your clipboard!"));

		panel.add(shareText);

		JOptionPane.showOptionDialog(null, panel, "Importing Outfit", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);

		String input = shareText.getText().replaceAll(" ", "").replaceAll("\n", ""); // sanitize the string
		
		if (input.length() < 1) //Just ignore it if the user inputs nothing
			return null;
		
		if (!input.startsWith("aue") || input.length() < 12) {
			new PopUp("That isn't a valid share code!", false);
			return null;
		}

		String toDecode = input.split("aue")[1].replaceAll("AUEQL", "=").replaceAll("AUESLASH", "/");

		try {
			input = new String(Base64.getDecoder().decode(toDecode));
		} catch (Exception e) {
			new PopUp("That isn't a valid share code!", false);
			return null;
		}

		if (isCorrupted(toDecode)) {
			new PopUp("That share code is corrupted!\nPlease make sure you copied it correctly.", false);
			return null;
		}

		if (input.split(",").length < 6 || input.split(",")[0].length() > 32) {
			new PopUp("That share code is corrupted!\nPlease make sure you copied it correctly.", false);
			return null;
		}

		System.out.println("Imported: " + input);
		return input;
	}

	private static boolean isCorrupted(String input) {
		return !(input.replace(" ", "").length() % 4 == 0);
	}
}
