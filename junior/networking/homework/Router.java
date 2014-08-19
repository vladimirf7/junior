package junior.networking.homework;

import junior.networking.homework.IPv4Address;
import junior.networking.homework.Route;

import java.util.*;

// your imports here

public class Router {
	private HashSet<Route> routes;

    public Router(HashSet<Route> routes) {
    	this.routes = routes;
    }

    public void addRoute(Route route) {
    	routes.add(route);
    }

    public HashSet<Route> getRoutes() {
    	return routes;
    }

    public void removeRoute(Route route) {
    	routes.remove(route);
    }

    public Route getRouteForAddress(IPv4Address address) {
    	for (Route route : routes) {
			if (route.getNetwork().contains(address)) {
				return route;
			}	
		}
		return null;
    }
}
