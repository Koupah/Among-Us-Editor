package club.koupah.amongus.editor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopUp {

	public PopUp(String message) {
		new PopUp(message, true);
	}

	public PopUp(String message, boolean exit) {
		JFrame jf = new JFrame();

		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, message, Editor.name, exit ? 0 : 2);

		if (exit)
			System.exit(0);
	}
}
