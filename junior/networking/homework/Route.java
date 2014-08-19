package junior.networking.homework;

import junior.networking.homework.IPv4Address;
import junior.networking.homework.Network;

// your imports here

public class Route {
	private Network network;
	private IPv4Address gateway;
	private String interfaceName;
	private int metric;
	
    public Route(Network network, IPv4Address gateway, String interfaceName, int metric) {
    	this.network = network;
		this.gateway = gateway;
		this.interfaceName = interfaceName;
		this.metric = metric;
    }

    public Network getNetwork() {
		return network;
    }

    public IPv4Address getGateway() {
		return gateway;

    }

    public String getInterfaceName() {
		return interfaceName;

    }

    public int getMetric() {
		return metric;
    }

    @Override
    public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("net: " + network.toString() + ", ");
		if (gateway != null)
			str.append("gate: " + gateway.toString() + ", ");
		str.append("interface: " + interfaceName + ", ");
		str.append("metric: " + metric);
		return str.toString();
    }

    @Override
    public boolean equals(Object other) {
    	if (other == null) return false;
        if (other == this) return true;
        if (getClass() != other.getClass()) return false;
        
        Route otherRoute = (Route) other;
        if (network != otherRoute.getNetwork()) return false;
        if (gateway != otherRoute.getGateway()) return false;
        if (interfaceName != otherRoute.getInterfaceName()) return false;
        if (metric != otherRoute.getMetric()) return false;
        return true;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gateway == null) ? 0 : gateway.hashCode());
		result = prime * result
				+ ((interfaceName == null) ? 0 : interfaceName.hashCode());
		result = prime * result + metric;
		result = prime * result + ((network == null) ? 0 : network.hashCode());
		return result;
	}
}
