package shionn.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.games.Proton;
import shionn.game.launcher.GameRunner;

public class GameDetailPanel extends JPanel implements MouseListener, PropertyChangeListener {

	private static final long serialVersionUID = -5616354546807861821L;
	private Game game;
	private Engine engine;
	private JButton runButton;
	private JButton installButton;
	private JButton runfileButtonChooser;

	public GameDetailPanel(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
		game.getPcs().addPropertyChangeListener(this);
		addMouseListener(this);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buildImageTitle();
		buildTitle();
		buildSeparator();
		buildRunButton();
		buildProtonSelect();
		buildExecutableSelect();
		refresh();
	}

	private void refresh() {
		runButton.setVisible(game.isRunnable());
		installButton.setVisible(game.isInstallable());
		installButton.setEnabled(true);
		revalidate();
		repaint();
	}

	private void buildImageTitle() {
		ImageIcon icon = new ImageIcon(game.getInstalersImgs().get(0));
		JLabel label = new JLabel(icon);
		add(label, buildHorizontalConstraint(0, 0));
	}

	private void buildTitle() {
		JLabel label = new JLabel(game.getName(), SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
		label.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, buildHorizontalConstraint(0, 1));
	}

	private void buildSeparator() {
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		GridBagConstraints gbc = buildHorizontalConstraint(0, 2);
		gbc.insets = new Insets(0, 0, 5, 0);
		add(separator, gbc);
	}

	private void buildRunButton() {
		runButton = new JButton("Lancer");
		runButton.setFont(getFont().deriveFont(Font.BOLD, 24));
		runButton.setBackground(Color.GREEN);
		runButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		runButton.addActionListener(e -> new GameRunner(engine, game).start());
		add(runButton, buildLeftConstraint(0, 3));

		installButton = new JButton("Installer");
		installButton.setFont(getFont().deriveFont(Font.BOLD, 24));
		installButton.setBackground(Color.RED);
		installButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		installButton.addActionListener(e -> new GameRunner(engine, game).startInstall());
		add(installButton, buildLeftConstraint(1, 3));
	}

	private void buildProtonSelect() {
		JLabel jLabel = new JLabel("Version Proton");
		add(jLabel, buildLeftConstraint(0, 4));

		JComboBox<Proton> box = new JComboBox<Proton>(engine.getProtons().toArray(s -> new Proton[s]));
		box
				.setRenderer((JList<? extends Proton> list, Proton value, int index, boolean isSelected,
						boolean cellHasFocus) -> new JLabel(value == null ? "--" : value.getName()));
		box.setSelectedItem(engine.proton(game.getProton()));
		box.addActionListener(e -> game.setProton(((Proton) box.getSelectedItem()).getName()));
		add(box, buildLeftConstraint(1, 4));
	}

	private void buildExecutableSelect() {
		JLabel jLabel = new JLabel("Executable");
		add(jLabel, buildLeftConstraint(0, 5));

		runfileButtonChooser = new JButton("Selectionner");
		runfileButtonChooser.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(game.getInstalledFolder()));
			fileChooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.isFile() && f.getName().endsWith(".exe");
				}

				@Override
				public String getDescription() {
					return "Executable (.exe)";
				}
			});
			int result = fileChooser.showOpenDialog(getTopLevelAncestor());
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				game.setRunFile(selectedFile.getAbsolutePath());
				runfileButtonChooser.setText(selectedFile.getAbsolutePath());
			}
		});

		add(runfileButtonChooser, buildLeftConstraint(1, 5));
	}

	private GridBagConstraints buildHorizontalConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		return gbc;
	}

	private GridBagConstraints buildLeftConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}

}
