package com.pajiniweb.oriachad_backend.settings.services;

import com.pajiniweb.oriachad_backend.settings.entites.Setting;
import com.pajiniweb.oriachad_backend.settings.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public String getSetting(String key) {
        Optional<Setting> setting = settingRepository.findById(key);
        return setting.map(Setting::getValue).orElse(null);
    }

    public <T> T getSetting(String key, T defaultValue, Class<T> tClass) {
        return convertInstanceOfObject(getSetting(key) != null ? getSetting(key) : defaultValue, tClass);
    }

    public void updateSetting(String name, String value) {
        Setting setting = new Setting();
        setting.setName(name);
        setting.setValue(value);
        settingRepository.save(setting);
    }

    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
            return null;
        }
    }
}

