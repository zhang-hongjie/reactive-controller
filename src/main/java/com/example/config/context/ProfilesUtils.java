package com.example.config.context;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.example.config.Eval.nullIfEx;

@Component
public class ProfilesUtils {
    @Autowired
    private Environment environment;

    public boolean isEnabledProfiles(Set<String> enabledProfiles){
        String profile = nullIfEx(()->environment.getActiveProfiles()[0]);
        return enabledProfiles.contains(profile);
    }
}
