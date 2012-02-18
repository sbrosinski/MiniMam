package router;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;



public class Router {

	private static Logger logger = Logger.getLogger(Router.class.getName());
	List<Route> routes = new ArrayList<Route>();
	RouterConfig config;

	public Router(RouterConfig config) {
		this.config = config;
	}
	
	public void add(Route route) {
		routes.add(route);
	}
	
	public void route(RouterOp op, String pattern, String... params) {
		routes.add(new Route(op, pattern, params));
	}
	
	public RouterResult execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String path = request.getPathInfo();
		for (Route route : routes) {
			
			if (route.match(path)) {
				logger.info("Found matching route: " + route);				

				RouterResult result = RouterResult.noResult();
				if (route.getOp() instanceof CachableRouterOp) {
				
					Cache cache = CacheManager.getInstance().getCache("routerResult");
					Element cacheElement = cache.get(path);
										
					if (cacheElement == null || !config.cacheOperations) {
						result = route.execute(path);
						cache.put(new Element(path, result));
					} else {
						result = (RouterResult) cacheElement.getObjectValue();
					}
				
				
				} else {
					result = route.execute(path);
				}
				
				return result;
			}
		}
		return RouterResult.errorResult("Not a valid request: " + path);
	}
	
}
