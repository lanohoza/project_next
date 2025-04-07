package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.services.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/FTP")
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile) throws Exception {
        return ftpService.storeImage(imageFile);
    }

    @PostMapping("/delete")
    public boolean deleteImage(@RequestBody String imageName) {
        return ftpService.deleteImage(imageName);
    }
}
