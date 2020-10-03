package club.koupah.aue.gui.types.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIComponent;
import club.koupah.aue.gui.settings.GUIScheme;

public class SchemeChooser extends GUIComponent {

	String[] allSchemes;
	
	public SchemeChooser(final JLabel label, JComboBox<String> component) {
		super(label, component);
		
		allSchemes = GUIScheme.getNames();
		
		for (String scheme : allSchemes) {
			component.addItem(scheme);
		}

		component.setSelectedItem(Editor.getInstance().configManager.getScheme().getName());
		
		label.setText(SchemeChooser.this.labelText + component.getSelectedItem());
		
		component.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) SchemeChooser.this.component).getSelectedItem();
				Editor.getInstance().configManager.setScheme(GUIScheme.findByName(selected));
				label.setText(SchemeChooser.this.labelText + selected);
				Editor.getInstance().guiManager.updateColorScheme();
			}
		});
	}
	

}
