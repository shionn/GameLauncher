package shionn.game.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameListPanel extends JPanel {
	private static final long serialVersionUID = -5274587669308226777L;

	public GameListPanel(Engine engine) {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		int x = 0;
		int y = 0;

		JLabel label = null;

		for (Game game : engine.getGames()) {
			if (label == null || !label.getText().equals(game.getLetter())) {
				if (x != 0) {
					x = 0;
					y++;
				}
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.weightx = 1.0;
				gbc.gridx = x;
				gbc.gridy = y;
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5, 50, 0, 10);

				label = new JLabel(game.getLetter(), SwingConstants.LEFT);
				label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
				add(label, gbc);
				y++;

				JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
				gbc.gridy = y;
				gbc.insets = new Insets(0, 10, 5, 10);
				add(separator, gbc);
				y++;

			}

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1.0;
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5, 10, 5, 10);

			add(new GameThumbnailPanel(engine, game), gbc);
			if (x++ >= 4) {
				x = 0;
				y++;
			}

		}

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				reorganize();
			}
		});
	}

	private void reorganize() {
		int colCount = Math.max(1, getWidth() / 250);
//		System.out.println("resized " + colCount);
		int x = 0;
		int y = 0;
		Component[] components = getComponents();
		GridBagLayout layout = (GridBagLayout) getLayout();
		for (Component component : components) {
			GridBagConstraints constraints = layout.getConstraints(component);
			if (component instanceof GameThumbnailPanel thumbnail) {
				constraints.gridx = x;
				constraints.gridy = y;
				layout.setConstraints(thumbnail, constraints);
				if (++x >= colCount) {
					x = 0;
					y++;
				}
			} else if (component instanceof JLabel label) {
				if (x != 0) {
					x = 0;
					y++;
				}
				constraints.gridx = x;
				constraints.gridy = y;
				layout.setConstraints(label, constraints);
				y++;
			} else if (component instanceof JSeparator separator) {
				if (x != 0) {
					x = 0;
					y++;
				}
				constraints.gridx = x;
				constraints.gridy = y;
				layout.setConstraints(separator, constraints);
				y++;
			}
		}
		revalidate();
		repaint();
	}


}
