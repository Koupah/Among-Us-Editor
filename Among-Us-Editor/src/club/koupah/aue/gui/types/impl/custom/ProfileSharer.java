package club.koupah.aue.gui.types.impl.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Profile;

public class ProfileSharer extends GUIComponent {

	String[] allProfileNames;

	JButton share;

	// Going to use this to load and delete profiles, saving/sharing will be another
	// component
	public ProfileSharer(final JLabel label, JButton component) {
		super(label, component);
		//component is the import profile button
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String config = showImport();
				
				if (config != null) {
					
					Profile imported = new Profile(config);
					Editor.getInstance().profileManager.updateProfiles(imported.getProfileName());
				}
			}
		});
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();

		share = new JButton("Share Profile");
		share.setToolTipText("Share the current selected profile!");
		
		
		label.setBounds(10, 15 + (index * Editor.scale), 100, 30);
		component.setBounds(260, 20 + (index * Editor.scale), 160, 20);

		share.setBounds(110, 20 + (index * Editor.scale), 140, 20);
		share.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Profile profile;
				if ((profile = Editor.getInstance().profileManager.current) != null) {
					showShareCode(profile.getConfigLine(), profile.getProfileName());
				} else {
					new PopUp("This profile isn't shareable!",false);
				}
			}
		});
		
	
		// I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);

		contentPane.add(this.component);
		contentPane.add(share);
	}
	
	static void showShareCode(String share, String profileName) {
		
		share = Base64.getEncoder().encodeToString(share.getBytes());
		
		Object[] options = { "Done!" };

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField shareText = new JTextField("aue" + share);
		shareText.setHorizontalAlignment(JTextField.CENTER);
		shareText.setEditable(false);
		panel.add(new JLabel("Sharing Profile: " + profileName));
		panel.add(new JLabel("Copy the text below, anyone can import it to get your profile!"));
		
		panel.add(shareText);
		
		JOptionPane.showOptionDialog(null, panel, "Sharing " + profileName,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, null);
	}
	static String showImport() {

		Object[] options = { "Done!" };

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField shareText = new JTextField(null);
		shareText.setHorizontalAlignment(JTextField.CENTER);
		shareText.setEditable(true);
		panel.add(new JLabel("Put the share code below to import it!"));
		
		panel.add(shareText);
		
		JOptionPane.showOptionDialog(null, panel, "Importing Profile",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, null);
		
		String input = shareText.getText();
		
		if (!input.startsWith("aue") || input.length() < 12) {
			new PopUp("That isn't a valid share code!", false);
			return null;
		}
		input = new String(Base64.getDecoder().decode(input.split("aue")[1]));
		System.out.println("Imported: " + input);
		return input;
	}
}
