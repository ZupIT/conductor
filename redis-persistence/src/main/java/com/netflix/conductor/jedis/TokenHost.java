package com.netflix.conductor.jedis;

public class TokenHost {
    private String token;
    private String hostname;
    private String zone;
    private String dc;

    public TokenHost() {
    }

    public TokenHost(String token, String hostname, String zone, String dc) {
        this.token = token;
        this.hostname = hostname;
        this.zone = zone;
        this.dc = dc;
    }

    public String getToken() {
        return token;
    }

    public String getHostname() {
        return hostname;
    }

    public String getZone() {
        return zone;
    }

    public String getDc() {
        return dc;
    }
}
