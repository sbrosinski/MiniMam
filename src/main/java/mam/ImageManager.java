package mam;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.media.jai.JAI;

import mam.config.MamConfig;
import mam.config.MamConfigManager;

import org.apache.log4j.Logger;

public class ImageManager {

	private static Logger logger = Logger.getLogger(ImageManager.class
			.getName());
	private static ImageManager instance;
	private MamConfig config;

	protected ImageManager() {
		this.config = MamConfigManager.get().getConfig();
	}

	public static ImageManager get() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	public MamImage loadImage(String fileName) throws IOException {
		logger.info("Reading image " + fileName);

		// ImageIO.read() throws weird exceptions with certain JPEGs. Seems to
		// be
		// related to unknown image meta data, for the mean time we load the
		// image
		// using JAI and convert it to a BufferedImage, because that's what the
		// scaler
		// works with ...
		//
		// BufferedImage img = ImageIO.read(new File(config.getSrcFolder() +
		// fileName));

		BufferedImage img = convertRenderedImage(JAI.create("fileload",
				config.getSrcFolder() + fileName));

		logger.info("Image dimensions: " + img.getWidth() + "x"
				+ img.getHeight());
		return new MamImage(img, fileName);
	}

	public void writeImage(MamImage img, OutputStream os) throws IOException {
		if (img.getImageType().equals(MamImageType.JPG)) {
			writeCompressedJpeg(img, os);
		} else if (img.getImageType().equals(MamImageType.PNG)){
			ImageIO.write(img.getImage(), "png", os);
		}
	}

	public void writeCompressedJpeg(MamImage img, OutputStream os)
			throws IOException {
		Iterator<ImageWriter> iter = ImageIO
				.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter) iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(MamConfigManager.get().getConfig()
				.getJpegQuality());

		MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream(
				os);
		writer.setOutput(output);
		IIOImage image = new IIOImage(img.getImage(), null, null);
		writer.write(null, image, iwp);
		writer.dispose();
	}

	private BufferedImage convertRenderedImage(RenderedImage img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		ColorModel cm = img.getColorModel();
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = cm
				.createCompatibleWritableRaster(width, height);
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		String[] keys = img.getPropertyNames();
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				properties.put(keys[i], img.getProperty(keys[i]));
			}
		}
		BufferedImage result = new BufferedImage(cm, raster,
				isAlphaPremultiplied, properties);
		img.copyData(raster);
		return result;
	}

}
