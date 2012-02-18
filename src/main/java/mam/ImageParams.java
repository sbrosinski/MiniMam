package mam;

import com.mortennobel.imagescaling.DimensionConstrain;

public class ImageParams {

	public int width, height, cropX, cropY, cropWidth, cropHeight;
	public float relativeScale;
	public String fileName;
	
	public void validate() {
		
	}
	
	public void setWidth(String width) {
		this.width = Integer.parseInt(width);
	}
	
	public void setHeight(String height) {
		this.height = Integer.parseInt(height);
	}	

	public void setCropHeight(String height) {
		this.cropHeight = Integer.parseInt(height);
	}	

	public void setCropWidth(String width) {
		this.cropWidth = Integer.parseInt(width);
	}	
	
	public void setCropX(String cropX) {
		this.cropX = Integer.parseInt(cropX);
	}	

	public void setCropY(String cropY) {
		this.cropY = Integer.parseInt(cropY);
	}
	
	public void setRelativeScale(String relativeScale) {
		this.relativeScale = Float.parseFloat(relativeScale);
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public DimensionConstrain getAbsolutionDimensionConstrain() {
		return DimensionConstrain.createAbsolutionDimension(width, height);
	}

	public DimensionConstrain getRelativeDimensionConstrain() {
		return DimensionConstrain.createRelativeDimension(relativeScale);
	}
}
