package shionn.game.launcher;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import shionn.game.games.Game;

public class TryToFindRunFile {

	private final List<String> IGNORED = Arrays
			.asList("UnityCrashHandler64.exe", "unins000.exe", "UnityCrashHandler32.exe");

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
				File[] exes = folder.listFiles((dir, name) -> name.endsWith(".exe") && !IGNORED.contains(name));
				if (exes.length == 1) {
					return exes[0].getAbsolutePath();
				}
			}
		}
		return null;
	}

	public String searchLinuxExecutable(Game game) {
		File folder = new File(game.getInstalledFolder());
		List<File> execs = Arrays
				.stream(folder.listFiles())
				.filter(File::isFile)
				.filter(f -> Files.isExecutable(f.toPath()))
				.toList();
		if (execs.size() == 1) {
			return execs.get(0).getAbsolutePath();
		}
		return null;
	}


}
