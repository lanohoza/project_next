package com.pajiniweb.oriachad_backend.security.services;


import com.pajiniweb.oriachad_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountInformationsService {

    @Autowired
    private UserService userService;


    //@Autowired
    //private IdsEmployeService idsEmployeService;


    public ResponseEntity<String> getAccountTypeString(String email) throws Exception {
//        var enterprise = this.enterpriseClientService.findByEmail(email);
//        if (enterprise != null) return ResponseEntity.ok(enterprise.getEnterpriseName());
//        else {
//            var particulier = this.particulierClientService.findByEmail(email);
//            if (particulier != null) return ResponseEntity.ok(particulier.getEmail());
//            /*else {
//                var admin=this.idsEmployeService.findByEmail(email);
//                if (admin != null) return ResponseEntity.ok(admin.getFonction());
//                else throw new Exception("the username ("+email+") have not any type");
//            }*/
//
//        }
//        return null;
//    }
        return null;
    }
}