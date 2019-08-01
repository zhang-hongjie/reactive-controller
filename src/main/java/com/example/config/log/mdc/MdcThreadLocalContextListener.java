package com.example.config.log.mdc;

import com.example.config.context.ThreadLocalContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.util.context.Context;

import java.util.Optional;

@Component
public class MdcThreadLocalContextListener implements ThreadLocalContextListener {

    private static Logger logger = LoggerFactory.getLogger(MdcThreadLocalContextListener.class);

    @Override
    public void push(Context context) {
        if (!context.isEmpty()) {
            Optional<MdcData> mdcData = MdcDataHolder.fromContext(context);
            if (mdcData.isPresent()) {
                logger.trace("Pushing mdc data to MDC {}", mdcData);
                mdcData.get().pushToMdc();
            } else {
                logger.trace("No mdc data available");
            }
        } else {
            logger.trace("Empty context");
        }
    }

    @Override
    public void clear() {
        MDC.clear();
    }

}
