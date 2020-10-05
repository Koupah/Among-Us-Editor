package club.koupah.aue.gui.types;

import javax.swing.JComponent;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;

public class GUIComponent {

	protected JLabel label;
	protected String labelText;

	protected JComponent component;
	
	protected int index;
	
	public static int componentHeight = 20;
	
	public GUIComponent(JLabel label, JComponent component) {
		this.label = label;
		this.labelText = label.getText();
		this.component = component;
	}
	
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();
		
		label.setBounds(10, 15 + (index * Editor.guiSpacing), 250, 30);
		component.setBounds(260, 20 + (index * Editor.guiSpacing), 160, componentHeight);
		
		//I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);
		
		contentPane.add(this.component);
	}

	public String getLabelText() {
		return labelText;
	}
}
