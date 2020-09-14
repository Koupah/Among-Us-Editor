package club.koupah.amongus.editor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopUp {
	
public PopUp(String message) {
	new PopUp(message, true);
}

public PopUp(String message, boolean exit) {
	 JFrame jf=new JFrame();
    jf.setAlwaysOnTop(true);
    jf.setLocation(-700, 900);
    JOptionPane.showMessageDialog(jf, message, "Among Us Character Editor", exit ? 0 : 2);
    
    if (exit)
    	System.exit(0);
}
}
