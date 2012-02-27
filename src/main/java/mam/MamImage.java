package mam;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.List;

import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.ImageUtils;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;

public class MamImage {

	private BufferedImage img;
	private String fileName;
	private MamImageType srcImageType;
	private MamImageType dstImageType;
	
	public MamImage(BufferedImage img, String fileName) {
		this.img = img;
		this.fileName = fileName;
		
		determineImageType(fileName);
	}

	private void determineImageType(String fileName) {
		if (fileName.endsWith(".jpg.png")) {
			srcImageType = MamImageType.JPG;
			dstImageType = MamImageType.PNG;			
		} else if (fileName.endsWith(".png.jpg")) {
			srcImageType = MamImageType.PNG;
			dstImageType = MamImageType.JPG;
		} else if (fileName.endsWith(".jpg")) {
			srcImageType = MamImageType.JPG;
			dstImageType = MamImageType.JPG;
		} else if (fileName.endsWith(".png")) {
			srcImageType = MamImageType.JPG;
			dstImageType = MamImageType.JPG;
		} else {
			srcImageType = MamImageType.UNDEFINED;
			dstImageType = MamImageType.UNDEFINED;
		}
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
	
	public void grayscale() {
		GrayscaleFilter filter = new GrayscaleFilter();
		this.img = filter.filter(img, ImageUtils.backgroundImage);
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	public MamImageType getSrcImageType() {
		return this.srcImageType;
	}

	public void applyFilters(List<BufferedImageOp> filters) {
		for (BufferedImageOp filter : filters) {
			this.img = filter.filter(img, ImageUtils.backgroundImage);
		}		
	}

	public MamImageType getDstImageType() {
		return this.dstImageType;
	}
	
}
