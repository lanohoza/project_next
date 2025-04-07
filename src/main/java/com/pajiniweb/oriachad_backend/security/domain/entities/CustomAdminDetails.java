package com.pajiniweb.oriachad_backend.security.domain.entities;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;


@Getter
@Setter
public class CustomAdminDetails extends User {

    private Admin oriachadAdmin;

    public CustomAdminDetails(Admin oriachadAdmin) {
        super(oriachadAdmin.getEmail(), oriachadAdmin.getPassword(), new ArrayList<>());
        this.oriachadAdmin = oriachadAdmin;
    }

    public Long getIdAdmin() {
        return oriachadAdmin.getId();
    }
}
