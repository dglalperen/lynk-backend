package com.lynk.lynk_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "lynk")
public class LynkProperties {
    private List<String> allowedEmailDomains = new ArrayList<>();

    public List<String> getAllowedEmailDomains() {
        return allowedEmailDomains;
    }

    public void setAllowedEmailDomains(List<String> allowedEmailDomains){
        this.allowedEmailDomains = allowedEmailDomains;
    }
}
