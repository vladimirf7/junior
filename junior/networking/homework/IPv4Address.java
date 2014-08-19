package junior.networking.homework;

import junior.networking.homework.exceptions.*;

// your imports here

public class IPv4Address {
    public static final long MAX_LONG_VALUE = 4294967295L;
    public static final int MIN_OCTET_VALUE = 0;
    public static final int MAX_OCTET_VALUE = 255;
    private long longAddress;
    private String stringAddress;

    public IPv4Address(String address) throws InvalidIpException {
        stringAddress = address;
        longAddress = stringToLong(address);
    }

    public IPv4Address(long address) throws InvalidIpException {
        if (address > MAX_LONG_VALUE) {
            throw new InvalidIpException();
        }
        longAddress = address;
        stringAddress = longToString(address);
    }

    private static long stringToLong(String address) throws InvalidIpException {
        String[] octets = address.split("\\.");
        long result = 0;

        if(octets.length != 4) {
            throw new InvalidIpException();
        }
        for (String octet : octets) {
        	int intOctet;
        	
        	try {
        		intOctet = Integer.parseInt(octet);
        	} catch (NumberFormatException e) {
                throw new InvalidIpException();
            }        	
        	if (intOctet < MIN_OCTET_VALUE || intOctet > MAX_OCTET_VALUE)
        		throw new InvalidIpException();
        }
    	result += (long)(Integer.parseInt(octets[0])) * (256 << 16);
        result += (long)(Integer.parseInt(octets[1])) * (256 << 8);
        result += (long)(Integer.parseInt(octets[2])) * 256;
        result += (long)(Integer.parseInt(octets[3])) * 1;

        return result;
    }

    private static String longToString(long address) {
        StringBuilder sb = new StringBuilder();

        sb.append((address >> 24) & 255);
        sb.append(".");
        sb.append((address >> 16) & 255);
        sb.append(".");
        sb.append((address >> 8) & 255);
        sb.append(".");
        sb.append((address >> 0) & 255);

        return sb.toString();
    }
    

    public long toLong() {
        return longAddress;
    }
    

    @Override
    public String toString() {
        return stringAddress;
    }
    

    public boolean lessThan(IPv4Address ip) {
        return this.longAddress < ip.toLong();
    }
    

    public boolean greaterThan(IPv4Address ip) {
        return this.longAddress > ip.toLong();
    }
    

    public boolean equals(IPv4Address ip) {
        return this.longAddress == ip.toLong();
    }

	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (longAddress ^ (longAddress >>> 32));
		return result;
	}
    

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (getClass() != other.getClass()) return false;

        IPv4Address otherIp = (IPv4Address) other;
        return this.longAddress == otherIp.toLong();
    }
}
