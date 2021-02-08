package org.app.webclienttest;

import reactor.blockhound.BlockHound.Builder;
import reactor.blockhound.integration.BlockHoundIntegration;

public class MyBlockHoundIntegration implements BlockHoundIntegration {
	@Override
	public void applyTo(Builder builder) {

        builder.allowBlockingCallsInside(
        		"io.netty.handler.ssl.SslContext",
        		"newClientContextInternal");
        
        builder.allowBlockingCallsInside(
                "io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider",
                "parseEtcResolverSearchDomains");

        builder.allowBlockingCallsInside(
                "io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider",
                "parseEtcResolverOptions");
      
        builder.allowBlockingCallsInside(
                "io.netty.resolver.HostsFileParser",
        		"parse");

        //this one below should be in included with netty 4.1.59
        //https://github.com/netty/netty/pull/10935
        builder.allowBlockingCallsInside(
                "io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider",
                "parse");
	}
}
