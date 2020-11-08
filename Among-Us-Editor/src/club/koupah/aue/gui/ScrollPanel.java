package club.koupah.aue.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollPane;
import javax.swing.Timer;

import club.koupah.aue.Editor;

public class ScrollPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;
	int scrollSpeed = 5;
	GUIPanel guipanel;

	public ScrollPanel(final GUIPanel panel) {
		this.guipanel = panel;
		this.setViewportView(panel);
		current = panel.getVisibleRect();

		setWheelScrollingEnabled(true);
		getVerticalScrollBar().setUnitIncrement(0);
		getVerticalScrollBar().setBlockIncrement(0);

		panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				if (Editor.getInstance().configManager.getSmoothScroll()) {
					smoothScroll(panel, arg0);
				} else {
					scroll(panel, arg0);
				}
			}
		});
	}

	Rectangle current;

	public void scroll(final GUIPanel component, MouseWheelEvent arg0) {
		current = component.getVisibleRect();
		final int target = Math.min(current.y + (arg0.getWheelRotation() > 0 ? (scrollSpeed * 2) : -(scrollSpeed * 2)),
				component.getHeight());

		current.y = target;

		component.scrollRectToVisible(current);

	}

	/*
	 * Shoutout to the stack overflow post that helped me
	 */
	public void smoothScroll(final GUIPanel component, MouseWheelEvent arg0) {
		final int target = current.y + (arg0.getWheelRotation() > 0 ? scrollSpeed : -scrollSpeed);
		final int start = current.y;
		final int delta = target - start;
		final int msBetweenIterations = 10;

		Timer scrollTimer = new Timer(msBetweenIterations, new ActionListener() {

			final long animationTime = 150; // milliseconds
			final long nsBetweenIterations = msBetweenIterations * 1000000; // nanoseconds
			final long startTime = System.nanoTime() - nsBetweenIterations; // Make the animation move on the first
																									// iteration
			final long targetCompletionTime = startTime + animationTime * 1000000;
			final long targetElapsedTime = targetCompletionTime - startTime;

			@Override
			public void actionPerformed(ActionEvent e) {
				current = component.getVisibleRect();

				long timeSinceStart = System.nanoTime() - startTime;
				double percentComplete = Math.min(1.0, (double) timeSinceStart / targetElapsedTime);

				current.y += (int) Math.round(delta * percentComplete);

				component.scrollRectToVisible(current);

				if (timeSinceStart >= targetElapsedTime) {
					((Timer) e.getSource()).stop();
				}
			}
		});

		scrollTimer.setInitialDelay(0);
		scrollTimer.start();
	}

	public GUIPanel getGUIPanel() {
		return this.guipanel;
	}
}
