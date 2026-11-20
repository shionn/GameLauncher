package shionn.game.launcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class LinuxArchivedGameInstallRunnerTest {

	private static final String GAMES_FOLDER = "target/Games";
	private static final String GAME_NAME = "MyGame";

	private LinuxArchivedGameInstallRunner runner(Engine engine, Game game) throws Exception {
		// Configuration lit ~/.config/game-launcher.conf ; on y force folder.games=target/Games/
		// (aucun @Mock impossible ici : @Mock field-init = null, @InjectMocks ne remplace pas les champs init)
		Path conf = Paths.get(Configuration.CONF_FILE);
		String backup = Files.readString(conf);
		Files.writeString(conf, "folder.games=" + GAMES_FOLDER + "/\n",
				java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
		try {
			return new LinuxArchivedGameInstallRunner(engine, game);
		} finally {
			Files.writeString(conf, backup, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
		}
	}

	@Test
	public void testRunExtractsAndDetectsElf() throws Exception {
		Engine engine = Engine.builder().build();
		Game game = Game.builder()
				.store("recomp-game")
				.platform("linux")
				.name(GAME_NAME)
				.installedFolder(GAMES_FOLDER + "/" + GAME_NAME)
				.archives(Arrays.asList("test-archive.tar.gz"))
				.instalersImgs(Arrays.asList(imagePath().toString()))
				.proton(null)
				.installed(false)
				.runfile(null)
				.build();
		game.setArchives(Arrays.asList(archivePath("test-archive.tar.gz").toString()));
		runner(engine, game).run();
		File tg = new File(GAMES_FOLDER + "/" + GAME_NAME);
		assertThat(tg).as("dossier").exists();
		assertThat(new File(tg, "recomp-game.store")).as(".store").exists();
		assertThat(new File(tg, "linux.platform")).as(".platform").exists();
		assertThat(game.isInstalled()).as("installé").isTrue();
		assertThat(game.getRunfile()).as("runfile").isEqualTo(Paths.get(GAMES_FOLDER, GAME_NAME, "test-archive", "start.sh").toAbsolutePath().toString());
		new File(GAMES_FOLDER + "/" + GAME_NAME).delete();
	}

	@Test
	public void testRunMarksInstalledEvenWithoutElf() throws Exception {
		Engine engine = Engine.builder().build();
		Game game = Game.builder()
				.store("recomp-game")
				.platform("linux")
				.name(GAME_NAME)
				.installedFolder(GAMES_FOLDER + "/" + GAME_NAME)
				.archives(Arrays.asList("archive-without-linux-executable.tar.gz"))
				.instalersImgs(Arrays.asList(imagePath().toString()))
				.proton(null)
				.installed(false)
				.runfile(null)
				.build();
		game.setArchives(Arrays.asList(archivePath("archive-without-linux-executable.tar.gz").toString()));
		runner(engine, game).run();
		assertThat(game.isInstalled()).as("installé").isTrue();
		assertThat(game.getRunfile()).as("aucun ELF").isNull();
		assertThat(new File(GAMES_FOLDER + "/" + GAME_NAME)).as("dossier").exists();
		new File(GAMES_FOLDER + "/" + GAME_NAME).delete();
	}

	@Test
	public void testRunDeletesOnTarFailure() throws Exception {
		Engine engine = Engine.builder().build();
		Game game = Game.builder()
				.store("recomp-game")
				.platform("linux")
				.name(GAME_NAME)
				.installedFolder(GAMES_FOLDER + "/" + GAME_NAME)
				.archives(Arrays.asList("/nonexistent/MyGame.tar.gz"))
				.instalersImgs(Arrays.asList(imagePath().toString()))
				.proton(null)
				.installed(false)
				.runfile(null)
				.build();
		IllegalStateException error = assertThrows(IllegalStateException.class, () -> runner(engine, game).run());
		assertThat(error).isInstanceOf(IllegalStateException.class);
		assertThat(new File(GAMES_FOLDER + "/" + GAME_NAME)).as("dossier supprimé").doesNotExist();
	}

	@Test
	public void testRunDeletesOnNoElfOnTarFailure() throws Exception {
		Engine engine = Engine.builder().build();
		Game game = Game.builder()
				.store("recomp-game")
				.platform("linux")
				.name(GAME_NAME)
				.installedFolder(GAMES_FOLDER + "/" + GAME_NAME)
				.archives(Arrays.asList("/nonexistent/MyGame.tar.gz"))
				.instalersImgs(Arrays.asList(imagePath().toString()))
				.proton(null)
				.installed(false)
				.runfile(null)
				.build();
		IllegalStateException error = assertThrows(IllegalStateException.class, () -> runner(engine, game).run());
		assertThat(error).isInstanceOf(IllegalStateException.class);
		assertThat(new File(GAMES_FOLDER + "/" + GAME_NAME)).as("dossier supprimé").doesNotExist();
	}

	private Path archivePath(String name) {
		if ("test-archive.tar.gz".equals(name)) {
			return Paths.get("target/test-classes/instalers/G/The Game/" + name);
		}
		return Paths.get("target/test-classes/instalers/B/Bag Archived Game/" + name);
	}

	private Path imagePath() throws Exception {
		Path image = Files.createTempFile("instaler", ".png");
		Files.write(image, "fake-image".getBytes());
		return image.toAbsolutePath();
	}
}
