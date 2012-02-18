package router;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public RouterResult execute(String path) throws Throwable {
		Matcher matcher = pattern.matcher(path);
		if (matcher.matches()) {
			
			Map<String, String> params = new HashMap<String, String>();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				params.put(paramNames[i-1],  matcher.group(i));
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
