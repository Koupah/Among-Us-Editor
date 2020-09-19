package club.koupah.amongus.editor.guisettings.types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import club.koupah.amongus.editor.Editor;
import club.koupah.amongus.editor.cosmetics.Cosmetic;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticType;
import club.koupah.amongus.editor.cosmetics.Cosmetic.CosmeticCategory;
import club.koupah.amongus.editor.guisettings.GUIComponent;

public class CosmeticFilter extends GUIComponent {

	public CosmeticFilter(JLabel label, JComboBox<String> component) {
		super(label, component);
		
		component.addItem("All");
		

		
		for (CosmeticCategory category : Cosmetic.CosmeticCategory.values()) {
			component.addItem(category.name());
			
			//Put Non-Free after free
			if (category.name().equals("Free"))
				//Temporary hard coded solution, although lots of this code is :p
				component.addItem("Non-Free");
		}
		
		label.setText(label.getText() + "All");
		
		component.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) ((JComboBox<String>) CosmeticFilter.this.component).getSelectedItem();
				CosmeticFilter.this.label.setText(getLabelText() + selected);
				for (GUIComponent guicomp : Editor.allGUIComponents) 
					if (guicomp instanceof MultiSetting) {
						MultiSetting multi = (MultiSetting) guicomp;
						CosmeticType type;
						
						//Essentially checking if the MultiSetting is one for cosmetics
						if ((type = CosmeticType.findType(multi.getLabelText().split(":")[0])) != null) {
							
							//If it's a cosmetic MultiSetting, set the values to match the filter
							
							if (selected.equals("All")) 
								multi.originalValues();
							else if (selected.equals("Non-Free")) 
								multi.setValues(Cosmetic.getItems(type, CosmeticCategory.Paid, true));
							else 
								//TODO: Add ability to choose "and above", essentially filter to Paid & Above
								//EDIT: After I wrote the above, I decided I'd hard code a "Non-Free" to show all except free
								multi.setValues(Cosmetic.getItems(type, CosmeticCategory.findCategory(selected), false));
							
							System.out.println(multi.getLabelText());
							
							multi.updateComponent();
						}
					}
				
			}
		});
	}

	


}
