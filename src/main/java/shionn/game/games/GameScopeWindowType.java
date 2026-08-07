package shionn.game.games;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameScopeWindowType {

	fullscreen("Plein Écran", "--fullscreen"),
	borderless("San Bordure", "--borderless"),
	windowed("Fenêtrè", null);

	private final String label;
	private final String option;

}
