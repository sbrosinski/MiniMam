package router;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import mam.config.MamConfigManager;

import org.apache.commons.lang.exception.ExceptionUtils;

public class ErrorResult extends RouterResult {

	private static final long serialVersionUID = 1L;
	
	public ErrorResult(Object result) {
		super(result);
	}

	@Override
	public void write(HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		response.setStatus(400); // client error, bad request
		setNoCacheHeaders(response);
		
		response.getWriter().append(getErrorText());
	}

	private String getErrorText() {
		
		Throwable t = (Throwable) getResult();
		
		StringBuilder out = new StringBuilder();
		out.append("An error occured: " + t.getMessage() + "\n\n");
		
		if (MamConfigManager.get().getConfig().isDebug()) {
			out.append(ExceptionUtils.getFullStackTrace(t));
		}
			
		return out.toString();
	}
	
}
