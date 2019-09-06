package com.example.config;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.FixedHttp11NioProtocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@ConditionalOnClass({ org.apache.catalina.startup.Tomcat.class })
public class TomcatWebConfig implements WebFluxConfigurer {

    @Bean
    public ReactiveWebServerFactoryCustomizer defaultReactiveWebServerCustomizer(ServerProperties serverProperties) {
        return new ConfigurableReactiveWebServerCustomizer(serverProperties);
    }

    public static class ConfigurableReactiveWebServerCustomizer extends ReactiveWebServerFactoryCustomizer {

        private final ServerProperties serverProperties;

        ConfigurableReactiveWebServerCustomizer(ServerProperties serverProperties) {
            super(serverProperties);
            this.serverProperties = serverProperties;
        }

        @Override
        public void customize(ConfigurableReactiveWebServerFactory factory) {
            super.customize(factory);
            assert factory instanceof TomcatReactiveWebServerFactory;
            TomcatReactiveWebServerFactory tomcatFactory = (TomcatReactiveWebServerFactory) factory;
            // fix the bug of response content-length + transfer-encoding
            tomcatFactory.setProtocol(FixedHttp11NioProtocol.class.getName());
            tomcatFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
                ProtocolHandler handler = connector.getProtocolHandler();
                assert handler instanceof AbstractHttp11Protocol;
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol) handler;
                // customizing max http header size accepted
                protocol.setMaxHttpHeaderSize(Math.toIntExact(this.serverProperties.getMaxHttpHeaderSize().toBytes()));
            });
        }

    }

}
