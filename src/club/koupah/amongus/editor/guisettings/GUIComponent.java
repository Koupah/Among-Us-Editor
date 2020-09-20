package club.koupah.amongus.editor.guisettings;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		index = Editor.allGUIComponents.size();
		label.setBounds(10, 75 + (index * Editor.scale), 250, 30);
		component.setBounds(270, 80 + (index * Editor.scale), 160, 20);
	}
	
	public void addToPane(JPanel contentPane) {
		contentPane.add(this.label);
		contentPane.add(this.component);
	}

	public String getLabelText() {
		return labelText;
	}
}
