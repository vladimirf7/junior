package junior.networking.homework;

import junior.networking.homework.exceptions.*;
import junior.networking.homework.IPv4Address;

// your imports here

public class Network {
    public static final int MIN_MASK_LENGTH = 0;
    public static final int MAX_MASK_LENGTH = 32;
    public static final long PRIVATE_CLASS_A_MIN = 167772159;
    public static final long PRIVATE_CLASS_A_MAX = 184549376;
    public static final long PRIVATE_CLASS_B_MIN = 2886729727L;
    public static final long PRIVATE_CLASS_B_MAX = 2887778304L;
    public static final long PRIVATE_CLASS_C_MIN = 3232235519L;
    public static final long PRIVATE_CLASS_C_MAX = 3232301056L;

    private IPv4Address ipAddress;
    private int maskLength;
    private long longMask;
    private long firstAddress;
	private long lastAddress;


    public Network(IPv4Address address, int maskLength) throws InvalidMaskException {
        if (maskLength < MIN_MASK_LENGTH || maskLength > MAX_MASK_LENGTH) {
            throw new InvalidMaskException();
        }
        this.maskLength = maskLength;
        longMask = (0 == maskLength) ? maskLength : 0xFFFFFFFF << MAX_MASK_LENGTH - maskLength;
        ipAddress = new IPv4Address(address.toLong() & longMask);
        firstAddress = (ipAddress.toLong() & longMask) + 1;
		lastAddress = (ipAddress.toLong() & longMask) | ~(longMask) - 1;
    }

    public boolean contains(IPv4Address address) {
    	if (address.toLong() >= firstAddress && address.toLong() <= lastAddress)
			return true;
		return false;
    }

    public IPv4Address getAddress() {
        return ipAddress;
    }

    public IPv4Address getBroadcastAddress() {
    	return new IPv4Address((ipAddress.toLong() & longMask) | ~(longMask));
    }

    public IPv4Address getFirstUsableAddress() {
    	if (32 == maskLength) return new IPv4Address(ipAddress.toLong());
    	if (31 == maskLength) return new IPv4Address((ipAddress.toLong() & longMask) | ~(longMask) - 1);
    	return new IPv4Address((ipAddress.toLong() & longMask) + 1);
    }

    public IPv4Address getLastUsableAddress() {
    	if (32 == maskLength) return new IPv4Address(ipAddress.toLong());
    	if (31 == maskLength) return new IPv4Address((ipAddress.toLong() & longMask) + 1);
    	return new IPv4Address((ipAddress.toLong() & longMask) | ~(longMask) - 1);
    }

    public long getMask() {
        return longMask;
    }

    public String getMaskString() {
    	StringBuilder sb = new StringBuilder();

        sb.append((longMask >> 24) & 255);
        sb.append(".");
        sb.append((longMask >> 16) & 255);
        sb.append(".");
        sb.append((longMask >> 8) & 255);
        sb.append(".");
        sb.append((longMask >> 0) & 255);

        return sb.toString();
    }

    public int getMaskLength() {
        return maskLength;
    }

    public Network[] getSubnets() {
    	int newMaskLength = maskLength + 1;
		Network[] subnets = new Network[2];
		long newIpAddress = firstAddress + getTotalHosts() / 2;
		
		subnets[0] = new Network(new IPv4Address(firstAddress - 1), newMaskLength);
		subnets[1] = new Network(new IPv4Address(newIpAddress), newMaskLength);
		return subnets;
    }

    public long getTotalHosts() {
    	if ( 0 == maskLength ) return 0xFFFFFFFFL + 1;
    	if ( 32 == maskLength ) return 0;
		if ( 31 == maskLength )	return 0;
		return ~longMask - 1;
    }

    public boolean isPublic() {
        if (ipAddress.toLong() > PRIVATE_CLASS_A_MIN &&
        		ipAddress.toLong() < PRIVATE_CLASS_A_MAX) return false;
        if (ipAddress.toLong() > PRIVATE_CLASS_B_MIN &&
        		ipAddress.toLong() < PRIVATE_CLASS_B_MAX) return false;
        if (ipAddress.toLong() > PRIVATE_CLASS_C_MIN &&
        		ipAddress.toLong() < PRIVATE_CLASS_C_MAX) return false;
        return true;
    }

    @Override
    public String toString() {
    	return ipAddress.toString() + "/" + maskLength;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + maskLength;
		return result;
	}
    
    @Override
    public boolean equals(Object other) {
    	if (other == null) return false;
        if (other == this) return true;
        if (getClass() != other.getClass()) return false;
        
        Network otherNetwork = (Network) other;
        return otherNetwork.getAddress() == this.ipAddress && otherNetwork.getMaskLength() == this.maskLength;
    }
}
