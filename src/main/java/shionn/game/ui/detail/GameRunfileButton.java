package shionn.game.ui.detail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameRunfileButton extends JButton implements PropertyChangeListener {

	private static final long serialVersionUID = 3995095899292714775L;
	private Game game;

	public GameRunfileButton(Engine engine, Game game) {
		super(Optional
				.ofNullable(game.getRunfile())
				.map(f -> f.substring(f.lastIndexOf('/') + 1))
				.orElse("Séléctionner"));
		this.game = game;
		addActionListener(e -> {
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
				game.setRunfile(selectedFile.getAbsolutePath());
				setText(selectedFile.getName());
			}
		});

		setVisible(game.isInstalled());
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(game.isInstalled());
		revalidate();
	}

}
