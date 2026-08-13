package shionn.game.launcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import shionn.game.games.Game;

public class PostInstalCommonOperation {

	public void doPostInstall(Game game) {
		try {
			Path target = Paths
					.get(game.getInstalledFolder() + "/"
							+ new File(game.getInstalersImgs().get(0)).getName());
			Files.copy(Paths.get(game.getInstalersImgs().get(0)), target, StandardCopyOption.COPY_ATTRIBUTES);
			if (game.getStore() != null) {
				new File(game.getInstalledFolder() + "/" + game.getStore() + ".store").createNewFile();
			}
			if (game.getGameId() != null) {
				new File(game.getInstalledFolder() + "/" + game.getGameId() + ".gameid").createNewFile();
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
