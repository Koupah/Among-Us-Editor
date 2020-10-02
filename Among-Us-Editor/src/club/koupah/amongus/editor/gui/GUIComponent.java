package club.koupah.amongus.editor.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;

import club.koupah.amongus.editor.Editor;

public class GUIComponent {

	protected JLabel label;
	protected String labelText;

	protected JComponent component;
	
	protected int index;
	
	public GUIComponent(JLabel label, JComponent component) {
		this.label = label;
		this.labelText = label.getText();
		this.component = component;
	}
	
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.settingCount;
		
		label.setBounds(10, 15 + (index * Editor.scale), 250, 30);
		component.setBounds(260, 20 + (index * Editor.scale), 160, 20);
		
		//I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);
		
		contentPane.add(this.component);
	}

	public String getLabelText() {
		return labelText;
	}
}
