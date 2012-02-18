package mam.op;

import java.io.IOException;
import java.util.Map;

import mam.ImageManager;
import mam.ImageParams;
import mam.ImageRouterResult;
import mam.MamImage;
import router.CachableRouterOp;
import router.RouterOp;
import router.RouterResult;

public class ShowImageOp extends RouterOp implements CachableRouterOp {

	public RouterResult execute(Map<String, String> params) throws IOException {
		
		ImageParams ip = new ImageParams();
		ip.setFileName(params.get("fileName")); 
	
		MamImage img = ImageManager.get().loadImage(ip.fileName);
	
		ImageRouterResult result = new ImageRouterResult(img);
		return result;
	}
	
}
