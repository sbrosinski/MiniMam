package router;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Route {

	private Pattern pattern;
	private RouterOp op;
	private String[] paramNames;
	
	public Route(RouterOp op, String pattern, String... paramNames) {
		this.op = op;
		this.pattern = Pattern.compile(pattern);
		this.paramNames = paramNames;
	}
	
	public boolean match(String path) {
		return pattern.matcher(path).matches();
	}

	public RouterResult execute(String path, Map<String, String[]> requestParams) throws Throwable {
		Matcher matcher = pattern.matcher(path);
		if (matcher.matches()) {
			
			RouteParams params = new RouteParams();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				params.putRouteParam(paramNames[i-1],  matcher.group(i));
			}
			
			for (String requestParamKey : requestParams.keySet()) {
				params.putQueryParam(requestParamKey, StringUtils.join(requestParams.get(requestParamKey)));
			}
			
			RouterResult result = RouterResult.noResult();
			
			try {
				result = op.execute(params);
			} catch (Throwable t) {
				result = RouterResult.errorResult(t);
			}
							
			return result;
		}
		return RouterResult.noResult();
	}
	
	public RouterOp getOp() {
		return op;
	}
	
	@Override
	public String toString() {
		return op + " - " + pattern.toString();
	}
	
}
