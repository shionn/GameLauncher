package shionn.game.games;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Game implements Comparable<Game> {

	private String letter;
	private String name;
	private List<String> instalers;
	private List<String> instalersImgs;
	private boolean installed;
	private String installedFolder;
	private String proton;
	private String runfile;

	@Override
	public int compareTo(Game o) {
		int c = letter.compareTo(o.letter);
		if (c == 0) {
			c = name.compareTo(o.name);
		}
		return c;
	}

}
