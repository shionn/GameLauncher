package shionn.game.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import shionn.game.games.Game;

public class ImageBackgroundButton extends JButton {

	private static final long serialVersionUID = 1529915562041308259L;

	private BufferedImage image;


	public ImageBackgroundButton(Game game) {
		try {
			this.image = ImageIO.read(new File(game.getInstalersImgs().get(0)));
//			setOpaque(true);
			setContentAreaFilled(false);
//			setBorderPainted(false);
			setPreferredSize(new Dimension(200, 130));
			setMinimumSize(new Dimension(200, 130));
		} catch (IOException e) {
			setMaximumSize(new Dimension(200, 130));
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		double scaleX = (double) width / image.getWidth(null);
		double scaleY = (double) height / image.getHeight(null);
		double scale = Math.max(scaleX, scaleY);

		int scaledWidth = (int) (image.getWidth(null) * scale);
		int scaledHeight = (int) (image.getHeight(null) * scale);
		int x = (width - scaledWidth) / 2;
		int y = (height - scaledHeight) / 2;

		g.drawImage(image, x, y, scaledWidth, scaledHeight, this);

		super.paintComponent(g);
	}


}
