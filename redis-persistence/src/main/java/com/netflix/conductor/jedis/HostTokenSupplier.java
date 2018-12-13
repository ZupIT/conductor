package com.netflix.conductor.jedis;

import com.netflix.dyno.connectionpool.impl.lb.HostToken;

import java.util.List;

public interface HostTokenSupplier {
    List<HostToken> getHostsTokens();
}
