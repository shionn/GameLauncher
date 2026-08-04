package shionn.game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameThumbnailPanel extends JPanel {

	private static final long serialVersionUID = 9057355729795660772L;
	private Game game;
	private Engine engine;

	public GameThumbnailPanel(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;

		setLayout(new BorderLayout());
		ImageBackgroundButton image = new ImageBackgroundButton(game);
		image.addActionListener(e -> openGame());
		add(image, BorderLayout.CENTER);

		JButton button = new JButton(game.getName());
		button.setFont(button.getFont().deriveFont(Font.BOLD, 18));
		button.addActionListener(e -> openGame());
		add(button, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(200, 180));
		setMinimumSize(new Dimension(200, 180));

	}

	private void openGame() {
		JPanel overlay = new JPanel();

		overlay.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		overlay.setBackground(new Color(0, 0, 0, 120));
		overlay.setOpaque(true);
//		overlay.setVisible(true);
		overlay.setFocusable(true);
		overlay.requestFocusInWindow();
//		overlay.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		overlay.add(new GameDetailPanel(engine, game), BorderLayout.CENTER);
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

		JFrame root = (JFrame) getTopLevelAncestor();
		JLayeredPane contentPane = (JLayeredPane) root.getContentPane();
		contentPane.add(overlay, JLayeredPane.PALETTE_LAYER);

		overlay.revalidate();
		overlay.repaint();

	}

}
