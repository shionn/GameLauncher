package shionn.game.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class ScalableIcon implements Icon {

//	private String filename;
	private BufferedImage image;


	public ScalableIcon(String filename) throws IOException {
//		this.filename = filename;
		this.image = ImageIO.read(new File(filename));
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int width = c.getWidth();
		int height = c.getHeight();
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		g.drawImage(scaledImage, x, y, null);

	}

	@Override
	public int getIconWidth() {
		return 0;
	}

	@Override
	public int getIconHeight() {
		return 0;
	}

}
