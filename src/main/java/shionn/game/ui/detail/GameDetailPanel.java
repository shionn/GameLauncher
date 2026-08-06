package shionn.game.ui.detail;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.ui.ConditionVisibleLabel;

public class GameDetailPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -5616354546807861821L;

	public GameDetailPanel(Engine engine, Game game) {
		addMouseListener(this);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(buildImageTitle(game), buildHorizontalConstraint(0, 0));
		add(buildTitle(game), buildHorizontalConstraint(0, 1));

		addSeparator(2);

		add(new JLabel("Version Proton"), constraint(0, 3, GridBagConstraints.WEST));
		add(new GameProtonComboBox(engine, game), constraint(1, 3, GridBagConstraints.WEST));
		add(new ConditionVisibleLabel("Executable", game, Game::isInstalled),
				constraint(2, 3, GridBagConstraints.WEST));
		add(new GameRunfileButton(engine, game), constraint(3, 3, GridBagConstraints.WEST));
		add(new GameInstalButton(engine, game), constraint(4, 3, GridBagConstraints.EAST));
		add(new GameRunButton(engine, game), constraint(4, 3, GridBagConstraints.EAST));

		addSeparator(4);
		add(new JLabel(game.getStore() + Optional.ofNullable(game.getGameId()).map(id -> " " + id).orElse("")),
				constraint(0, 5, GridBagConstraints.SOUTHWEST));

		// TODO gamemode / gamescope / mangohud

	}

	private JLabel buildImageTitle(Game game) {
		try {
			BufferedImage image = ImageIO.read(new File(game.getInstalersImgs().get(0)));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(1200, -1, Image.SCALE_SMOOTH));
			return new JLabel(icon);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private JLabel buildTitle(Game game) {
		JLabel label = new JLabel(game.getName(), SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
		label.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	private void addSeparator(int line) {
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		GridBagConstraints gbc = buildHorizontalConstraint(0, line);
		gbc.insets = new Insets(0, 0, 5, 0);
		add(separator, gbc);
	}

	private GridBagConstraints buildHorizontalConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		return gbc;
	}

	private GridBagConstraints constraint(int x, int y, int anchor) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = anchor;
		gbc.fill = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 5;
		return gbc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.consume();
	}

}
