package mam;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp;

public class MamImage {

	private BufferedImage img;
	private String fileName;
	private MamImageType imageType;
	
	public MamImage(BufferedImage img, String fileName) {
		this.img = img;
		this.fileName = fileName;
		this.imageType = determineImageType(fileName);
	}

	public MamImageType determineImageType(String fileName) {
		MamImageType imageType;
		if (fileName.endsWith(".jpg")) {
			imageType = MamImageType.JPG;
		} else if (fileName.endsWith(".png")) {
			imageType = MamImageType.PNG;
		} else {
			imageType = MamImageType.UNDEFINED;
		}
		return imageType;
	}

	public void scale(DimensionConstrain dimensionConstrain) {
		ResampleOp resampleOp = new ResampleOp(dimensionConstrain);
    	resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
    	BufferedImage rescaled = resampleOp.filter(img, null);
    	this.img = rescaled;
	}

	public void crop(int x, int y, int width, int height) {
		if (x < 0 || img.getWidth() < x + width || y < 0 || img.getHeight() < y + height) 
			throw new IllegalArgumentException("Crop frame is outside of image bounds");
		
		this.img = img.getSubimage(x, y, width, height);
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	public MamImageType getImageType() {
		return this.imageType;
	}
	
}
