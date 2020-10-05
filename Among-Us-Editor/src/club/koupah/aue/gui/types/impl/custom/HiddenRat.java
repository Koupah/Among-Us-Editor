package club.koupah.aue.gui.types.impl.custom;

import java.awt.Font;

import javax.swing.JLabel;

import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.GUITabbedPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.ImageUtil;

public class HiddenRat extends GUIComponent {

	public HiddenRat() {
		super(new JLabel("u fonud the rat."), new JLabel("rat"));

	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);

		int width = 450;
		int height = 300;
		component.setBounds(0, 0, width, height);
		((JLabel) component).setIcon(ImageUtil.makeIcon(
				ImageUtil.scaleImage(ImageUtil.getImage(GUITabbedPanel.class, "tabicons/rat.jpg"), width, height)));

		JLabel remy = new JLabel("other rat guy");
		remy.setBounds(280, 150, 150, 150);
		remy.setIcon(ImageUtil
				.makeIcon(ImageUtil.scaleImage(ImageUtil.getImage(GUITabbedPanel.class, "tabicons/remy.jpg"), 150, 150)));
		contentPane.add(remy, 1); // Put it above
	
		JLabel bt = new JLabel("Top Text");
		bt.setBounds(0,100, 500, 300);
		bt.setHorizontalAlignment(JLabel.CENTER);
		Font f = new Font("Tahoma", Font.PLAIN, 42);
		bt.setFont(f);
		contentPane.add(bt,1);
	}

}
