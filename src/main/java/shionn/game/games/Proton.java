package shionn.game.games;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Proton {

	private String name;
	private String path;

	public String ToString() {
		return name;
	}
}

