package club.koupah.amongus.editor.gui.types.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.gui.GUIComponent;
import club.koupah.amongus.editor.utility.PopUp;

public class LookAndFeelChooser extends GUIComponent {

	LookAndFeelInfo[] allLookAndFeels;
	
	public LookAndFeelChooser(JLabel label, JComboBox<String> component) {
		super(label, component);
		
		allLookAndFeels = UIManager.getInstalledLookAndFeels();
		
		for (LookAndFeelInfo lnf : allLookAndFeels) {
			component.addItem(lnf.getName());
		}

		component.setSelectedItem(UIManager.getLookAndFeel().getName());
		
		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) LookAndFeelChooser.this.component).getSelectedItem();
				setLookAndFeel(selected);
			}
		});
	}
	
	public void setLookAndFeel(String name) {
		for (LookAndFeelInfo lnf : allLookAndFeels) {
			if (lnf.getName().equals(name)) {
				try {
					UIManager.setLookAndFeel(lnf.getClassName());
					Editor.currentLookAndFeel = lnf.getClassName();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					new PopUp("Look and Feel error?\n" + e.getMessage());
					e.printStackTrace();
				}
				
				SwingUtilities.updateComponentTreeUI(Editor.getInstance());
				
				//Hacky way to force the config file to store the current l&f preference
				Editor.getInstance().prefsFinder.saveToConfig(Editor.getInstance().playerPrefs.getAbsolutePath());
				
				break;
			}
		}
	}

}
