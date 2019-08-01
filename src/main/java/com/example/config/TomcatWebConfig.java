package com.example.config;


import com.example.config.log.TomcatAccessLogFilter;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;

@Configuration
@ConditionalOnClass({ org.apache.catalina.startup.Tomcat.class })
public class TomcatWebConfig implements WebFluxConfigurer {

    @Bean
    @Order(FilterOrder.TOMCAT_ACCESS_LOG)
    public WebFilter tomcatAccessLogFilter() {
        return new TomcatAccessLogFilter();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public WebFilter responseWrapperFilter() {
        return new ResponseWrapperFilter();
    }



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
            // customizing max http header size accepted
            assert factory instanceof TomcatReactiveWebServerFactory;
            TomcatReactiveWebServerFactory tomcatFactory = (TomcatReactiveWebServerFactory) factory;
//            tomcatFactory.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
            tomcatFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
                ProtocolHandler handler = connector.getProtocolHandler();
                assert handler instanceof AbstractHttp11Protocol;
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol) handler;
                protocol.setMaxHttpHeaderSize(Math.toIntExact(this.serverProperties.getMaxHttpHeaderSize().toBytes()));
                protocol.setUseSendfile(true);
            });
        }

    }

}
