package shionn.game.launcher;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameProcessBuilder {
	private Configuration configuration = new Configuration();
	private Map<String, String> env = new HashMap<>();
	private List<String> args = new ArrayList<>();
	private Engine engine;
	private Game game;
	private boolean directory = false;

	public GameProcessBuilder(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	public GameProcessBuilder wineprefix() {
		return env("WINEPREFIX", configuration.gamesFolder() + game.getName());
	}

	public GameProcessBuilder protonPath() {
		return env("PROTONPATH", engine.proton(game.getProton()).getPath());
	}

	public GameProcessBuilder logs() {
		return env("UMU_LOG", "1").env("PROTON_LOG", "1").env("PROTON_LOG_DIR", game.getInstalledFolder());
	}

	public GameProcessBuilder store() {
		return env("STORE", "gog");
	}

	public GameProcessBuilder gameId() {
		return Optional.ofNullable(game.getGameId()).map(id -> env("GAMEID", id)).orElse(this);
	}

	public GameProcessBuilder directory() {
		this.directory = true;
		return this;
	}

	public GameProcessBuilder umuRun() {
		return arg("umu-run");
	}

	public GameProcessBuilder instaler() {
		return arg(game.getInstalers().get(0));
	}

	public GameProcessBuilder runFile() {
		return arg(game.getRunfile());
	}

	public ProcessBuilder build() {
		ProcessBuilder processBuilder = new ProcessBuilder(args.toArray(s -> new String[s]));
		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(Redirect.PIPE);
		processBuilder.environment().putAll(this.env);
		if (directory) {
			processBuilder.directory(new File(game.getRunfile()).getParentFile());
			System.out.println("cd " + new File(game.getRunfile()).getParentFile());
		}

		StringBuilder command = new StringBuilder();
		this.env
				.entrySet()
				.stream()
				.forEach(e -> command.append(e.getKey()).append("=\"").append(e.getValue()).append("\" "));
		this.args.stream().map(a -> a.contains(" ") ? "\"" + a + "\"" : a).forEach(a -> command.append(a).append(" "));
		System.out.println(command);
		return processBuilder;
	}

	public GameProcessBuilder env(String variable, String value) {
		env.put(variable, value);
		return this;
	}

	public GameProcessBuilder arg(String arg) {
		args.add(arg);
		return this;
	}

}
