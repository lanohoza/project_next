package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.PopUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.PopUp;
import com.pajiniweb.oriachad_backend.services.PopUpService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pop-ups")
public class PopUpController {

    @Autowired
    private PopUpService popUpService;

    // Get all PopUps
    @GetMapping("/popups")
    public ResponseEntity<Page<PopUpDto>> getAllPopUps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, String search) throws IOException {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        Page<PopUpDto> popUpPage = popUpService.findAll(pageable, search);
        return ResponseEntity.ok(popUpPage);
    }


    // Get PopUp by ID
    @GetMapping("/findById/{id}")
    public PopUpDto findById(@PathVariable Long id) throws IOException {
        return popUpService.findById(id).get();
    }

    // Create a new PopUp
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public PopUpDto save(@RequestPart("popUp") PopUpDto popUpDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return popUpService.save(popUpDto, file);
    }


    // Update an existing PopUp
    @PutMapping("/update/{id}")
    public PopUpDto update(@PathVariable Long id, @RequestBody PopUpDto PopUpDto) throws IOException {
        return popUpService.update(id, PopUpDto);
    }

    // Delete a PopUp by ID
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) throws IOException {
        return popUpService.deleteById(id);
    }

    @GetMapping("/publish/{id}/{publish}")
    public Boolean publish(
            @PathVariable Long id,
            @PathVariable Boolean publish) throws Exception {
        return popUpService.publish(id, publish);
    }

    @GetMapping("/getPublishedPopUpForNotification")
    public PopUpDto getPublishedPopUp() {
        return popUpService.getPublishedPopUp();
    }
}
