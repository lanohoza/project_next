package com.pajiniweb.oriachad_backend.settings.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component()
@Order(1)
public class DataSettingsInitializer implements ApplicationRunner {

    @Autowired
    private AppSettings appSettings;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        appSettings.saveSetting(ConfigConstants.appDefaultDataInit,false);
        appSettings.saveSetting(ConfigConstants.appDefaultDataInit,"Oriachad");
        appSettings.saveSetting(ConfigConstants.appDefaultLang,"ar");
    }
}