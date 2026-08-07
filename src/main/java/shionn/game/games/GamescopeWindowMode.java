package shionn.game.games;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GamescopeWindowMode implements Labeled {
	fullscreen("Plein écran"),
	borderless("Sans bordure");

	private final String label;
}
