package mam.op;

import java.io.IOException;

import mam.ImageManager;
import mam.ImageParams;
import mam.ImageRouterResult;
import mam.MamImage;
import router.CachableRouterOp;
import router.RouteParams;
import router.RouterOp;
import router.RouterResult;

public class AbsoluteScaleOp extends RouterOp implements CachableRouterOp  {

	public RouterResult execute(RouteParams params) throws IOException {
		
		ImageParams ip = new ImageParams(params);
		ip.setWidth(params.get("width"));
		ip.setHeight(params.get("height"));
		ip.setFileName(params.get("fileName")); 
		
		MamImage img = ImageManager.get().loadImage(ip.fileName);
		img.scale(ip.getAbsolutionDimensionConstrain());
		img.applyFilters(ip.getFilters());
		
		ImageRouterResult result = new ImageRouterResult(img);
		return result;
	}
	
}
