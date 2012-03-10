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

public class CropOp extends RouterOp implements CachableRouterOp {

	public RouterResult execute(RouteParams params) throws IOException {
		
		ImageParams ip = new ImageParams(params);
		ip.setCropX(params.get("cropX"));
		ip.setCropY(params.get("cropY"));
		ip.setCropWidth(params.get("cropWidth"));
		ip.setCropHeight(params.get("cropHeight"));
		ip.setFileName(params.get("fileName")); 
		
		MamImage img = ImageManager.get().loadImage(ip.getFileName());
		img.crop(ip.getCropX(), ip.getCropY(), ip.getCropWidth(), ip.getCropHeight());
		img.applyFilters(ip.getFilters());
		
		return new ImageRouterResult(img);
	}
	
}
