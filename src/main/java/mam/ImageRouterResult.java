package mam;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import router.RouterResult;

public class ImageRouterResult extends RouterResult {

	private static final long serialVersionUID = 1L;

	public ImageRouterResult(Object result) {
		super(result);
	}

	@Override
	public void write(HttpServletResponse response) throws IOException {
		MamImage image = (MamImage) getResult();
		response.setContentType(image.getSrcImageType().mimeType());
		setCacheHeaders(response);
		ImageManager.get().writeImage(image, response.getOutputStream());
		
	}
	
}
