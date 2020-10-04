package club.koupah.aue.gui.types.impl.custom.schemes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.settings.GUIScheme;
import club.koupah.aue.gui.types.GUIComponent;

public class CustomSchemeEditor extends GUIComponent {

	JButton foreground;
	
	public CustomSchemeEditor(final JLabel label, JButton component) {
		super(label, component);
		
		component.setToolTipText("Set the Background color for the Custom theme!");
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color currentBackground = GUIScheme.Custom.getBackground();
				
				final JColorChooser colorChooser = getColorChooser();
				colorChooser.setColor(currentBackground);
				colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent arg0) {
						GUIScheme.Custom.setBackground(colorChooser.getColor());
						Editor.getInstance().guiManager.updateColorScheme(true);
					}
					});
		        
				JFrame jf = new JFrame("Background Color");
				jf.setPreferredSize(new Dimension(300,300));
				jf.setResizable(false);
		  		jf.setLocationRelativeTo(Editor.getInstance());
		  		jf.setAlwaysOnTop(true);
		  		jf.add(colorChooser);
		  		jf.pack();
		  		jf.setVisible(true);
			}
		});
		
		foreground = new JButton("Foreground");
		foreground.setToolTipText("Set the Foreground color for the Custom theme!");
		foreground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color currentForeground = GUIScheme.Custom.getForeground();
				
				final JColorChooser colorChooser = getColorChooser();
				colorChooser.setColor(currentForeground);
				colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent arg0) {
						GUIScheme.Custom.setForeground(colorChooser.getColor());
						Editor.getInstance().guiManager.updateColorScheme(true);
					}
					});
		        
		  		JFrame jf = new JFrame("Foreground Color");
				jf.setPreferredSize(new Dimension(300,300));
				jf.setResizable(false);
		  		jf.setLocationRelativeTo(Editor.getInstance());
		  		jf.setAlwaysOnTop(true);
		  		jf.add(colorChooser);
		  		jf.pack();
		  		jf.setVisible(true);
			}
		});
		
	}
	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);

		foreground.setBounds(110, 20 + (index * Editor.guiSpacing), 140, 20);
		contentPane.add(foreground);
	}
	
	//this only shows the HSV
	public JColorChooser getColorChooser() {
		final JColorChooser colorChooser = new JColorChooser();
		colorChooser.setPreviewPanel(new JPanel());
		
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            if(!accp.getDisplayName().equals("HSV")) {
                colorChooser.removeChooserPanel(accp);
            } 
        }
        JComponent current = (JComponent) colorChooser.getComponents()[0];
        while( !current.getClass().toString().equals( "class javax.swing.colorchooser.ColorChooserPanel" ) ){
            current = (JComponent) current.getComponents()[0]; 
        }

        for(Component jc : current.getComponents()) {
            if(!jc.getClass().toString().equals( "class javax.swing.colorchooser.DiagramComponent" )){
            	jc.setVisible(false);
            }
      		jc.setBackground(Editor.getInstance().configManager.getScheme().getBackground());
        }  
        
        return colorChooser;
	}
	
}
