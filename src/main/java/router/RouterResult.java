package router;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

public class RouterResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object result;

	public RouterResult(Object result) {
		this.result = result;
	}

	public static RouterResult noResult() {
		return new RouterResult("");
	}

	public void write(HttpServletResponse response) throws IOException {

	}

	protected void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("cache-control", "no-cache");
		response.setHeader("pragma", "no-cache");
	}

	protected void setCacheHeaders(HttpServletResponse response) {
		response.setHeader("cache-control", "max-age=315360000");
		response.setHeader("expires", "Thu, 31 Dec 2037 23:55:55 GMT");
	}

	public Object getResult() {
		return result;
	}

	public static RouterResult errorResult(Throwable t) {
		return new ErrorResult(t);
	}

	public static RouterResult errorResult(String msg) {
		return new ErrorResult(new IllegalStateException(msg));
	}
}
