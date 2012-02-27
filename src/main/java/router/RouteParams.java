package router;

import java.util.HashMap;
import java.util.Map;

public class RouteParams {

	private Map<String, String> routeParams;
	private Map<String, String> queryParams;
	
	public RouteParams() {
		this.routeParams = new HashMap<String, String>();
		this.queryParams = new HashMap<String, String>();
	}
	
	public String get(String name) {
		String routeValue = routeParams.get(name);
		String queryValue = queryParams.get(name);		
		return routeValue != null ? routeValue : (queryValue != null ? queryValue : "");
	}
	
	public void putRouteParam(String key, String value) {
		routeParams.put(key, value);
	}

	public void putQueryParam(String key, String value) {
		queryParams.put(key, value);
	}

}
