package com.pajiniweb.oriachad_backend.settings.configuration;

import com.pajiniweb.oriachad_backend.settings.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppSettings {

    @Autowired
    private SettingService settingService;

    @Bean
    public String appDefaultName() {
        return settingService.getSetting(ConfigConstants.appDefaultName, "Default Name", String.class);
    }

    @Bean
    public Boolean appDefaultDataInit() {
        return settingService.getSetting(ConfigConstants.appDefaultDataInit, false, Boolean.class);
    }

    public void saveSetting(String name, Object value) {
        settingService.updateSetting(name, value.toString());
    }
}
