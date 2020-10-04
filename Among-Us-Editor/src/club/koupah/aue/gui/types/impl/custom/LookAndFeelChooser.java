package club.koupah.aue.gui.types.impl.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.GUIComponent;

public class LookAndFeelChooser extends GUIComponent {

	LookAndFeelInfo[] allLookAndFeels;
	
	public LookAndFeelChooser(final JLabel label, JComboBox<String> component) {
		super(label, component);
		
		allLookAndFeels = UIManager.getInstalledLookAndFeels();
		
		for (LookAndFeelInfo lnf : allLookAndFeels) {
			component.addItem(lnf.getName());
		}
		
		String currentName = null;
		for (LookAndFeelInfo lnf : allLookAndFeels) {
			if (lnf.getClassName().equals(Editor.getInstance().configManager.getLookAndFeel())) {
				currentName = lnf.getName();
			}
		}

		if (currentName != null)
		component.setSelectedItem(currentName);
		
		label.setText(LookAndFeelChooser.this.labelText + component.getSelectedItem());
		
		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) LookAndFeelChooser.this.component).getSelectedItem();
				setLookAndFeel(selected);
				label.setText(LookAndFeelChooser.this.labelText + selected);
			}
		});
	}
	
	public void setLookAndFeel(String name) {
		for (LookAndFeelInfo lnf : allLookAndFeels) {
			if (lnf.getName().equals(name)) {
					Editor.getInstance().configManager.setLookAndFeel(lnf.getClassName());
					Editor.getInstance().guiManager.updateLookAndFeel();
				break;
			}
		}
	}

}
