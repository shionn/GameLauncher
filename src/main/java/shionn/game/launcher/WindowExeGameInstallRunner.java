package shionn.game.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class WindowExeGameInstallRunner implements Runnable {
	private Configuration configuration = new Configuration();
	private Engine engine;
	private Game game;

	public WindowExeGameInstallRunner(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			ProcessBuilder processBuilder = new GameProcessBuilder(engine, game)
					.wineprefix()
					.protonPath()
					.store()
					.gameId()
					.umuRun()
					.instaler()
					.build();
			Process process = processBuilder.start();
			game.setProcess(process);
			int exitCode = process.waitFor();
			process.destroy();
			if (exitCode == 0) {
				game.setInstalledFolder(configuration.gamesFolder() + game.getName());
				game.setInstalled(true);
				game.setRunfile(new TryToFindRunFile().searchExe(game));
			} else {
				deleteIncompleteInstal();
			}
			game.setProcess(null);
		} catch (InterruptedException | RuntimeException | IOException e) {
			deleteIncompleteInstal();
			throw new IllegalStateException(e);
		}
	}

	private void deleteIncompleteInstal() {
		game.setProcess(null);
		try {
			FileUtils.deleteDirectory(new File(configuration.gamesFolder() + game.getName()));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
