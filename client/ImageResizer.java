package client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Resizes a image and saves it as a new file.
 * 
 * @author www.codejava.net, Oscar, Olof
 */
public class ImageResizer {

	/**
	 * Checks if the picture is valid
	 * 
	 * @author Olof
	 */
	public boolean validPicture(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {
		try {
			File inputFile = new File(inputImagePath);
			BufferedImage inputImage = ImageIO.read(inputFile);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Resizes an image to a absolute width and height (the image may not be
	 * proportional)
	 * 
	 * @author www.codejava.net, Oscar
	 * @param inputImagePath
	 *            Path of the original image
	 * @param outputImagePath
	 *            Path to save the resized image
	 * @param scaledWidth
	 *            absolute width in pixels
	 * @param scaledHeight
	 *            absolute height in pixels
	 * @throws IOException
	 */
	public void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

		// writes to output file
		ImageIO.write(outputImage, formatName, new File(outputImagePath));
	}
}