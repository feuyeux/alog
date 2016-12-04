package org.feuyeux.alog.service;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
public class NetworkManager {

    private static final String  localIp;
    static {
        localIp = getLocalIp0();
    }
    public static String getLocalIp(){
        return localIp;
    }

    public static String getLocalIp0() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while(e.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface)e.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    ip = (InetAddress)addresses.nextElement();
                    if(ip != null && ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
            return null;
        } catch (SocketException var4) {
            log.error("", var4);
            return null;
        }
    }
}
