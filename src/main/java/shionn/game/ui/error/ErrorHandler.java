package shionn.game.ui.error;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ErrorHandler implements UncaughtExceptionHandler {

	private JFrame frame;

	public ErrorHandler(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		JPanel overlay = buildOverlay(e);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JLayeredPane contentPane = (JLayeredPane) frame.getContentPane();
				contentPane.add(overlay, JLayeredPane.MODAL_LAYER);

				overlay.revalidate();
				overlay.repaint();
			}
		});
	}

	private JPanel buildOverlay(Throwable e) {
		JPanel overlay = new JPanel() {
			private static final long serialVersionUID = 572030084409517744L;

			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0, 0, 0, 120));
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};

		overlay.setLayout(new BorderLayout());
		overlay.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
		overlay.setOpaque(false);
		overlay.setFocusable(true);
		overlay.requestFocusInWindow();
		overlay.add(new JScrollPane(new JTextArea(printStackTrace(e))), BorderLayout.CENTER);
		overlay.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				e.consume();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Container parent = overlay.getParent();
				parent.remove(overlay);
				parent.revalidate();
				parent.repaint();
				e.consume();
			}
		});
		return overlay;
	}

	private String printStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
