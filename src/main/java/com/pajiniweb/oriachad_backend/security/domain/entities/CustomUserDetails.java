package com.pajiniweb.oriachad_backend.security.domain.entities;

import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Getter
@Setter
public class CustomUserDetails extends User {

    private OriachadUser oriachadUser;

    public CustomUserDetails(OriachadUser oriachadUser) {
        super(oriachadUser.getEmail(), oriachadUser.getPassword(), new ArrayList<>());
        this.oriachadUser = oriachadUser;
    }

    public Long getIdUser() {
        return oriachadUser.getId();
    }
}
