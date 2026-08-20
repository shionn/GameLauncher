package shionn.game.launcher;

import java.io.File;
import java.util.Arrays;

import shionn.game.games.Game;

public class TryToFindRunFile {

	public String searchExe(Game game) {
		String filename = search(game.getInstalledFolder() + "/drive_c/GOG Games/");
		if (filename == null) {
			filename = search(game.getInstalledFolder() + "/drive_c/Steam Games/");
		}
		if (filename == null) {
			filename = search(game.getInstalledFolder() + "/drive_c/Epic Games/");
		}
		return filename;
	}

	private String search(String path) {
		File folder = new File(path);
		if (folder.exists()) {
			folder = Arrays.stream(folder.listFiles(f -> f.isDirectory())).findAny().orElse(null);
			if (folder != null) {
				File[] exes = folder.listFiles((dir, name) -> name.endsWith(".exe"));
				if (exes.length == 1) {
					return exes[0].getAbsolutePath();
				}
			}
		}
		return null;
	}


}
