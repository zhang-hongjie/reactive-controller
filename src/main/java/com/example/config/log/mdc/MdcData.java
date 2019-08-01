package com.example.config.log.mdc;

import com.example.config.reactive.JwtUserWithToken;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.Collectors;

@ToString
public class MdcData {

    private static Logger logger = LoggerFactory.getLogger(MdcData.class);

    private EnumMap<MdcKey, String> data = new EnumMap<>(MdcKey.class);

    public void pushToMdc() {
        data.keySet().forEach(key -> MDC.put(key.getLabel(), data.get(key)));
    }

    public void clear() {
        data.keySet().forEach(key -> MDC.remove(key.getLabel()));
    }

    public void putEnvironment(@Nullable String[] activeProfiles) {
        if (activeProfiles != null && activeProfiles.length > 0) {
            String environment = Arrays.stream(activeProfiles).collect(Collectors.joining(" - "));
            logger.trace("Environment {}", environment);
            this.data.put(MdcKey.ENVIRONMENT, environment);
        } else {
            logger.debug("No environment available");
        }
    }

    public void putRequestUrl(@Nullable URI uri) {
        if (uri != null) {
            String requestUrl = uri.toString();
            logger.trace("Request URL {}", requestUrl);
            this.data.put(MdcKey.REQUEST_URL, requestUrl);
        } else {
            logger.debug("No request URL available");
        }
    }

    public void putRequestMethod(@Nullable HttpMethod method) {
        if (method != null) {
            String value = method.toString();
            logger.trace("Request method {}", value);
            this.data.put(MdcKey.REQUEST_METHOD, value);
        } else {
            logger.debug("No request method available");
        }
    }

    public void putRemoteAddress(@Nullable InetSocketAddress remoteAddress) {
        if (remoteAddress != null && remoteAddress.getAddress() != null) {
            String hostAddress = remoteAddress.getAddress().getHostAddress();
            logger.trace("Remote address {}", hostAddress);
            this.data.put(MdcKey.REMOTE_ADDRESS, hostAddress);
        } else {
            logger.debug("No remote address available");
        }
    }

    public void putUsername(@Nullable SecurityContext securityContext) {
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                JwtUserWithToken user = (JwtUserWithToken) authentication.getPrincipal();
                String auditUsername = user.toAuditString();
                logger.trace("Username {}", auditUsername);
                this.data.put(MdcKey.USERNAME, user.getUsername());
                this.data.put(MdcKey.AUDIT_USERNAME, auditUsername);
            } else {
                logger.debug("No authentication available or not authenticated");
            }
        } else {
            logger.debug("No security context available");
        }
    }

    public void putResponseTime(@Nullable Instant start, @Nullable Instant end) {
        if (start != null && end != null) {
            this.data.put(MdcKey.RESPONSE_TIME, String.valueOf(end.getLong(ChronoField.MILLI_OF_SECOND) - start.getLong(ChronoField.MILLI_OF_SECOND)));
        } else {
            logger.debug("No start date or end date available");
        }
    }

    public void putStatusCode(@Nullable HttpStatus httpStatus) {
        if (httpStatus != null) {
            this.data.put(MdcKey.STATUS_CODE, httpStatus.getReasonPhrase());
        } else {
            logger.debug("No http status available");
        }
    }

    public void putStatusHttpCode(int statusCode) {
        this.data.put(MdcKey.STATUS_HTTP_CODE, String.valueOf(statusCode));
    }

}
