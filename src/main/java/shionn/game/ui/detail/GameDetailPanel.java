package shionn.game.ui.detail;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.games.GamescopeUpscaleFilterMode;
import shionn.game.games.GamescopeUpscaleScalerMode;
import shionn.game.games.GamescopeWindowMode;
import shionn.game.launcher.GameRunner;
import shionn.game.ui.detail.gamescope.GameScopeResolutionComboBoxBox;
import shionn.game.ui.detail.gamescope.GamescopeEnumComboBox;
import shionn.game.ui.generic.ConditionVisibleLabel;
import shionn.game.ui.generic.SimpleCheckBox;

public class GameDetailPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -5616354546807861821L;

	public GameDetailPanel(Engine engine, Game game) {
		addMouseListener(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(buildImageTitle(game));
		add(buildTitle(game));
		add(buildSeparator());
		add(buildMainControlPanel(engine, game));
		add(buildSeparator());
		add(buildMangohudPanel(engine, game));
		add(buildSeparator());
		add(buildGamescopePanel1(game));
		add(buildGamescopePanel2(game));
		add(buildSeparator());
		add(buildGameArgumentsPanel(game));
		add(buildSeparator());
		add(buildLog(game));
		// TODO gamemode /

		add(new JLabel(game.getStore() + Optional.ofNullable(game.getGameId()).map(id -> " - " + id).orElse("")));
	}


	private JLabel buildImageTitle(Game game) {
		try {
			String filename = game.getInstalersImgs().get(0);
			BufferedImage image = ImageIO.read(new File(filename));
			ImageIcon icon = new ImageIcon(image);
			JLabel label = new JLabel(icon);
			label.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					int w = label.getWidth();
					int h = label.getHeight();
					if (image.getWidth() / (float) image.getHeight() >= w / (float) h) {
						label.setIcon(new ImageIcon(image.getScaledInstance(-1, h, Image.SCALE_SMOOTH)));
					} else {
						label.setIcon(new ImageIcon(image.getScaledInstance(w, -1, Image.SCALE_SMOOTH)));
					}
				}
			});
			label.setAlignmentX(.5f);
			label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
			label.setPreferredSize(new Dimension(1000, 300));
			return label;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private JLabel buildTitle(Game game) {
		JLabel label = new JLabel(game.getName(), SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
		label.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
//		label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(.5f);
		return label;
	}

	private Component buildMainControlPanel(Engine engine, Game game) {
		JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		left.add(new JLabel("Version Proton"));
		left.add(new GameProtonComboBox(engine, game));
		left.add(new ConditionVisibleLabel("Executable", game, Game::isInstalled));
		left.add(new GameRunfileButton(engine, game));
		JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		right.add(new GameInstalButton(engine, game));
		right.add(new GameKillButton(game));
		right.add(new GameRunButton(engine, game, "Wintricks", () -> new GameRunner(engine, game).wintricksGui()));
		right.add(new GameRunButton(engine, game, "Lancer", () -> new GameRunner(engine, game).start()));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(left, BorderLayout.WEST);
		panel.add(right, BorderLayout.EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}

	private Component buildMangohudPanel(Engine engine, Game game) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel.add(new SimpleCheckBox("Activer Mangohud", game, game::isMangohudEnabled, game::setMangohudEnabled));
		panel
				.add(new SimpleCheckBox("Activer Feral Gamemode", game, game::isFeralGamemodeEnabled,
						game::setFeralGamemodeEnabled));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}
	private Component buildGamescopePanel1(Game game) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel.add(new SimpleCheckBox("Gamescope", game, game::isGamescopeEnabled, game::setGamescopeEnabled));
		panel.add(new ConditionVisibleLabel("Fenêtre", game, Game::isGamescopeEnabled));
		panel
				.add(new GamescopeEnumComboBox<>(game, GamescopeWindowMode.values(), game::getGamescopeWindowMode,
						game::setGamescopeWindowMode));
		panel
				.add(new SimpleCheckBox("Forcer le curseur relatif", game, game::isGamescopeForceGrapCursor,
						game::setGamescopeForceGrapCursor, game::isGamescopeEnabled));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}

	private Component buildGamescopePanel2(Game game) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel.add(new ConditionVisibleLabel("Mise à l'échelle", game, Game::isGamescopeEnabled));
		panel
				.add(new GameScopeResolutionComboBoxBox(game, game::getGamescopeInResolution,
						game::setGamescopeInResolution));
		panel.add(new ConditionVisibleLabel("=>", game, Game::isGamescopeEnabled));
		panel
				.add(new GameScopeResolutionComboBoxBox(game, game::getGamescopeOutResolution,
						game::setGamescopeOutResolution));
		panel.add(new ConditionVisibleLabel("Filtre", game, Game::isGamescopeEnabled));
		panel
				.add(new GamescopeEnumComboBox<>(game, GamescopeUpscaleFilterMode.values(),
						game::getGamescopeUpscaleFilterMode, game::setGamescopeUpscaleFilterMode));
		panel.add(new ConditionVisibleLabel("Ratio", game, Game::isGamescopeEnabled));
		panel
				.add(new GamescopeEnumComboBox<>(game, GamescopeUpscaleScalerMode.values(),
						game::getGamescopeUpscaleScalerMode, game::setGamescopeUpscaleScalerMode));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}

	private Component buildGameArgumentsPanel(Game game) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel.add(new JLabel("Autre options"));
		panel.add(new GameArgCheckBox(game, "-locale=fr"));
		panel.add(new GameArgCheckBox(game, "-nosteam"));
		panel.add(new GameArgCheckBox(game, "-EpicPortal"));
		panel.add(new GameEnvCheckBox(game, "PROTON_FSR4_UPGRADE=1"));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}

	private JPanel buildLog(Game game) {

//		JScrollPane scrollPane = new JScrollPane(textArea);
//		scrollPane.setAutoscrolls(true);
//		textArea.setScrollPane(scrollPane);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new ProcessLogTextArea(game), BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return panel;
	}

	private JSeparator buildSeparator() {
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return separator;
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
