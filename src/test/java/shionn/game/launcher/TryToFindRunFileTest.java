package shionn.game.launcher;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

import org.junit.jupiter.api.Test;

import shionn.game.games.Game;

public class TryToFindRunFileTest {

	private final TryToFindRunFile findRunFile = new TryToFindRunFile();

	@Test
	public void testSearchLinuxExecutableDetectsSingleElf() throws Exception {
		// GIVEN un dossier extrait contenant un seul fichier exécutable
		Path root = Files.createTempDirectory("recomp");
		Path extracted = root.resolve("game");
		Files.createDirectories(extracted);
		Path elf = extracted.resolve("game");
		Files.write(elf, new byte[] { 0x7f, 'E', 'L', 'F' });
		Files.setPosixFilePermissions(elf, PosixFilePermissions.fromString("rwxr-xr-x"));
		Game game = Game.builder().store("recomp-game").installedFolder(root.toAbsolutePath().toString()).build();

		// WHEN
		String runfile = findRunFile.searchLinuxExecutable(game);

		// THEN le chemin absolu du fichier ELF est retourné
		assertThat(runfile).as("le runfile unique doit être retourné").isEqualTo(elf.toAbsolutePath().toString());
	}

	@Test
	public void testSearchLinuxExecutableReturnsNullWhenNoExecutable() throws Exception {
		// GIVEN un dossier extrait sans fichier exécutable
		Path root = Files.createTempDirectory("recomp");
		Path extracted = root.resolve("game");
		Files.createDirectories(extracted);
		Path note = extracted.resolve("readme.txt");
		Files.write(note, "hello".getBytes());
		Game game = Game.builder().store("recomp-game").installedFolder(root.toAbsolutePath().toString()).build();

		// WHEN
		String runfile = findRunFile.searchLinuxExecutable(game);

		// THEN null : aucun exécutable présent
		assertThat(runfile).as("aucun exécutable présent").isNull();
	}

	@Test
	public void testSearchLinuxExecutableReturnsNullWhenMultipleExecutables() throws Exception {
		// GIVEN deux fichiers exécutables (ambiguïté)
		Path root = Files.createTempDirectory("recomp");
		Path extracted = root.resolve("game");
		Files.createDirectories(extracted);
		Path a = extracted.resolve("gameA");
		Path b = extracted.resolve("gameB");
		Files.write(a, new byte[] { 0x7f, 'E', 'L', 'F' });
		Files.write(b, new byte[] { 0x7f, 'E', 'L', 'F' });
		Files.setPosixFilePermissions(a, PosixFilePermissions.fromString("rwxr-xr-x"));
		Files.setPosixFilePermissions(b, PosixFilePermissions.fromString("rwxr-xr-x"));
		Game game = Game.builder().store("recomp-game").installedFolder(root.toAbsolutePath().toString()).build();

		// WHEN
		String runfile = findRunFile.searchLinuxExecutable(game);

		// THEN null : ambigu, pas de runfile déterministe
		assertThat(runfile).as("deux exécutables = ambigu").isNull();
	}

}
