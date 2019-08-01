package com.example.config.context;

import reactor.util.context.Context;

public interface ThreadLocalContextListener {

    void push(Context context);

    void clear();

}
