package mam;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mam.config.MamConfigManager;
import mam.op.AbsoluteScaleOp;
import mam.op.CropOp;
import mam.op.RelativeScaleOp;
import mam.op.ScaleAndCropOp;
import mam.op.ShowImageOp;
import router.Router;
import router.RouterConfig;
import router.RouterResult;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Router router;
	
	public void init() {

		this.router = new Router(getRouterConfig());

		router.route(new ScaleAndCropOp(), 
				"/([0-9]+)x([0-9]+)/([0-9]+),([0-9]+)-([0-9]+)x([0-9]+)/(.*)",
				"width", "height", "cropX", "cropY", "cropWidth", "cropHeight", "fileName");		

		router.route(new CropOp(), 
				"/([0-9]+),([0-9]+)-([0-9]+)x([0-9]+)/(.*)",
				"cropX", "cropY", "cropWidth", "cropHeight", "fileName");				
		
		router.route(new AbsoluteScaleOp(), 
				"/([0-9]+)x([0-9]+)/(.*)",
				"width", "height", "fileName");
		
		router.route(new RelativeScaleOp(), 
				"/([0-9]+\\.[0-9]+)/(.*)",
				"scale", "fileName");
		
		router.route(new ShowImageOp(), 
				"/(.*)",
				"fileName");

	}

	private RouterConfig getRouterConfig() {
		RouterConfig routerConf = new RouterConfig();
		routerConf.cacheOperations = MamConfigManager.get().getConfig().isCacheOperations();
		return routerConf;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RouterResult result = router.execute(request, response);
			result.write(response);
	}


}
