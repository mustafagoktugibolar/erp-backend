package com.archproj.erp_backend.config;

public class ConfigManager {
    private static ConfigManager instance;
    private String configValue;

    private ConfigManager() {
        this.configValue = "Default Config";
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
