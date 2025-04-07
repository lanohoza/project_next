package com.pajiniweb.oriachad_backend.settings.repositories;

import com.pajiniweb.oriachad_backend.settings.entites.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
}
