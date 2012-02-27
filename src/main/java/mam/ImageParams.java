package mam;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import router.RouteParams;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.mortennobel.imagescaling.DimensionConstrain;

public class ImageParams {

	public int width, height, cropX, cropY, cropWidth, cropHeight;
	public float relativeScale;
	public String fileName;
	private RouteParams routeParams;
	
	
	public ImageParams(RouteParams routeParams) {
		this.routeParams = routeParams;
	}
	
	public List<BufferedImageOp> getFilters() {
		List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();
		
		if (StringUtils.isNotEmpty(routeParams.get("gray"))) {
			filters.add(new GrayscaleFilter());
		}
		if (StringUtils.isNotEmpty(routeParams.get("blur"))) {
			float radius = Float.parseFloat(routeParams.get("blur"));
			filters.add(new GaussianFilter(radius));
		}
		
		return filters;
	}
	
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
