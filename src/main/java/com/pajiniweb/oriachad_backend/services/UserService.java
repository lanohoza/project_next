package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditUserDto;
import com.pajiniweb.oriachad_backend.domains.dtos.CommuneDto;
import com.pajiniweb.oriachad_backend.domains.dtos.EstablishmentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Wilaya;
import com.pajiniweb.oriachad_backend.exceptions.LoginFailedException;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.UserRepository;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginDto;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import com.pajiniweb.oriachad_backend.security.services.JwtService;
import com.pajiniweb.oriachad_backend.services.fileStorage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommuneService communeService;

    @Autowired
    private EstablishmentService establishmentService;

    @Value("${ms.sendMailWithoutSendGrid.endPoint}")
    private String originalSendMailURL;

    //@Autowired
    //RestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FtpService ftpService;

    @Autowired
    private InitializeService initializeService;

    @Autowired
    private FileStorageService fileStorageService;

    // Create
    @Transactional
    public UserDto createUser(AddEditUserDto userDTO, MultipartFile files) throws Exception {
        log.info("Creating user: {}", userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new Exception("error.user.exist");
        }

        /* This part for check the file Type and Size */
        String contentType = files.getContentType();
        if (contentType == null ||
                (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            throw new Exception("Only image and PDF files are allowed");
        }

        if (files.getSize() > 1 * 1024 * 1024) { // 1 MB limit
            throw new Exception("File size exceeds the limit (1 MB)");
        }
        /*----------------------------------------------*/
        userDTO.setActive(false);
        OriachadUser user = mapAddEditUserDtoToEntity(userDTO);

        // Generate code based on commune, phone number, and random number
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        user.setCode(Long.valueOf(user.getIdCommune() + user.getPhoneNumber() + randomNumber));

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            String attestationPath = fileStorageService.saveFile(files);
            user.setUserAttestationPath(attestationPath);
            // Save user entity
            OriachadUser createdUser = userRepository.save(user);

            initializeService.InitializeUserEnvironment(createdUser);
            // Send email to administration
            sendCertificateToAdministration(createdUser, files);

            log.info("User created successfully: {}", createdUser);
            return mapUserEntityToDto(createdUser);
        } catch (Exception e) {
            log.error("Failed to create user: {}", e.getMessage());
            throw new RuntimeException("error.signup.fail");
        }
    }


    public OriachadUser findByEmailAndPassword(UserLoginDto userLogInDto) {
        try {
            Optional<OriachadUser> oriachadUser = userRepository.findByEmail(userLogInDto.getEmail());

            if (oriachadUser.isPresent()) {
                if (passwordEncoder.matches(userLogInDto.getPassword(), oriachadUser.get().getPassword())) {
                    return oriachadUser.get();
                } else {
                    throw new LoginFailedException("error.login.fail");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error during login process: {}", e.getMessage());
            return null;
        }
    }

    public Page<UserDto> getAllUsers(Pageable pageable, String search) {
        log.info("Fetching all users with pagination");
        try {
            Page<OriachadUser> usersPage = userRepository.search(pageable, search);
            log.info("Found {} users", usersPage.getTotalElements());

            List<UserDto> userDtos = usersPage.getContent().stream()
                    .map(UserService.this::mapUserEntityToDto)
                    .collect(Collectors.toList());


            return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
        } catch (Exception e) {
            log.error("Failed to fetch all users: {}", e.getMessage());
            return Page.empty(); // or consider throwing a custom exception
        }
    }

    // Read by ID
    public UserDto getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        try {
            Optional<OriachadUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                OriachadUser user = userOptional.get();
                log.info("User found: {}", user);
                return mapUserEntityToDto(user);
            } else {
                log.warn("User not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to fetch user by ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    // Update
    public UserDto updateUser(Long id, UserDto userDetails) {
        log.info("Updating user with ID {}: {}", id, userDetails);
        try {
            Optional<OriachadUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                OriachadUser user = userOptional.get();
                updateUserDataFromDto(user, userDetails);
                OriachadUser updatedUser = userRepository.save(user);
                log.info("User updated successfully: {}", updatedUser);
                return mapUserEntityToDto(updatedUser);
            } else {
                log.warn("User not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to update user with ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        try {
            Optional<OriachadUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                fileStorageService.deleteFile(userOptional.get().getUserAttestationPath());
                userRepository.deleteById(id);
                log.info("User deleted successfully with ID: {}", id);
                return true;
            } else {
                log.warn("User not found with ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete user with ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    // Find by email
    public UserDto findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        try {
            Optional<OriachadUser> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                OriachadUser user = userOptional.get();
                log.info("User found with email {}: {}", email, user);
                return mapUserEntityToDto(user);
            } else {
                log.warn("User not found with email: {}", email);
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to find user by email {}: {}", email, e.getMessage());
            return null;
        }
    }

    // Log in by username (email)
    public OriachadUser logInByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new Exception("not Found User"));
    }

    public boolean editPassword(String currentPassword, String newPassword) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
            log.info("Start function editPassword for email: {}", userDetails.getUsername());

            if (userDetails == null) {
                log.info("User with email {} not found", userDetails.getUsername());
                return false;
            }

            String storedPasswordEncoded = userDetails.getPassword();

            if (new BCryptPasswordEncoder().matches(currentPassword, storedPasswordEncoded)) {
                String newPasswordEncoded = new BCryptPasswordEncoder().encode(newPassword);
                userDetails.getOriachadUser().setPassword(newPasswordEncoded);
                userRepository.save(userDetails.getOriachadUser());

                log.info("Password updated successfully for user with email: {}", userDetails.getUsername());
                return true;
            } else {
                log.info("Current password provided does not match the stored password for user with email: {}", userDetails.getUsername());
                return false;
            }
        } catch (Exception e) {
            log.error("Error while updating password for user with email because : " + e.getMessage());
            return false;
        }
    }

    public OriachadUser mapAddEditUserDtoToEntity(AddEditUserDto userDTO) {
        CommuneDto communeDTO = communeService.findById(userDTO.getIdCommune());
        EstablishmentDto establishmentDTO = establishmentService.findById(userDTO.getIdEstablishment());
        return OriachadUser.builder().id(userDTO.getId()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName()).username(userDTO.getUsername()).image(userDTO.getImage()).addresse(userDTO.getAddresse()).email(userDTO.getEmail()).faxNumber(userDTO.getFaxNumber()).phoneNumber(userDTO.getPhoneNumber()).webSite(userDTO.getWebSite()).password(userDTO.getPassword()).commune(communeService.convertToEntity(communeDTO)).idCommune(userDTO.getIdCommune()).code(userDTO.getCode()).establishment(establishmentService.convertToEntity(establishmentDTO)).idEstablishment(userDTO.getIdEstablishment()).active(userDTO.getActive()).userAttestationPath(userDTO.getUserAttestationPath()).build();
    }

    // Helper methods for mapping between Entity and DTO
    public OriachadUser mapUserDtoToEntity(UserDto userDTO) {
        CommuneDto communeDTO = communeService.findById(userDTO.getIdCommune().getId());
        EstablishmentDto establishmentDTO = establishmentService.findById(userDTO.getIdEstablishment().getId());
        return OriachadUser.builder().id(userDTO.getId()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName()).username(userDTO.getUsername()).image(userDTO.getImage()).addresse(userDTO.getAddresse()).email(userDTO.getEmail()).faxNumber(userDTO.getFaxNumber()).phoneNumber(userDTO.getPhoneNumber()).webSite(userDTO.getWebSite()).password(userDTO.getPassword()).commune(communeService.convertToEntity(communeDTO)).idCommune(userDTO.getIdCommune().getId()).code(userDTO.getCode()).establishment(establishmentService.convertToEntity(establishmentDTO)).idEstablishment(userDTO.getIdEstablishment().getId()).active(userDTO.getActive()).userAttestationPath(userDTO.getUserAttestationPath()).build();
    }

    public UserDto mapUserEntityToDto(OriachadUser user) {
        CommuneDto communeDTO = communeService.findById(user.getCommune().getId());
        EstablishmentDto establishmentDTO = establishmentService.findById(user.getEstablishment().getId());
        return UserDto.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).username(user.getUsername()).image(user.getImage()).addresse(user.getAddresse()).email(user.getEmail()).faxNumber(user.getFaxNumber()).phoneNumber(user.getPhoneNumber()).webSite(user.getWebSite()).password(user.getPassword()).idCommune(communeDTO).code(user.getCode()).idEstablishment(establishmentDTO).active(user.getActive()).userAttestationPath(user.getUserAttestationPath()).build();
    }

    private void updateUserDataFromDto(OriachadUser user, UserDto userDetails) {
        CommuneDto communeDTO = communeService.findById(userDetails.getIdCommune().getId());
        EstablishmentDto establishmentDTO = establishmentService.findById(userDetails.getIdEstablishment().getId());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUsername(userDetails.getUsername());
        user.setImage(userDetails.getImage());
        user.setAddresse(userDetails.getAddresse());
        user.setEmail(userDetails.getEmail());
        user.setFaxNumber(userDetails.getFaxNumber());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setWebSite(userDetails.getWebSite());
        user.setPassword(userDetails.getPassword());
        user.setCommune(communeService.convertToEntity(communeDTO));
        user.setIdCommune(userDetails.getIdCommune().getId());
        user.setCode(userDetails.getCode());
        user.setEstablishment(establishmentService.convertToEntity(establishmentDTO));
        user.setIdEstablishment(userDetails.getIdEstablishment().getId());
        user.setActive(userDetails.getActive());
        user.setUserAttestationPath(userDetails.getUserAttestationPath());
    }

    public Boolean sendCertificateToAdministration(OriachadUser user, MultipartFile file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (file == null) {
                throw new Exception("file is null");
            }
            Wilaya wilaya = user.getCommune().getWilaya();

            if (user != null) {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                body.add("files", file.getResource());

                body.add("to", "pajiniweb@gmail.com");
                body.add("cc", null);
                body.add("subject", "Attestation de travail de " + user.getFirstName() + " " + user.getLastName());
                body.add("body", "Voici les informations de client :<br><br>" +
                        "Nom d'utilisateur : " + user.getUsername() + "<br>" +
                        "Prénom : " + user.getFirstName() + "<br>" +
                        "Nom : " + user.getLastName() + "<br>" +
                        "Email : " + user.getEmail() + "<br>" +
                        "Numéro de téléphone : " + user.getPhoneNumber() + "<br>" +
                        "Adresse : " + user.getAddresse() + "<br>" +
                        "Commune : " + user.getCommune().getName() + "<br>" +
                        "Wilaya : " + (wilaya.getName() != null ? wilaya.getName() : "") + "<br>" +
                        "Fax : " + (user.getFaxNumber() != null ? user.getFaxNumber() : "") + "<br>" +
                        "Site Web : " + (user.getWebSite() != null ? user.getWebSite() : "") + "<br>" +
                        "Attestation :" + user.getUserAttestationPath() + "<br>");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        originalSendMailURL,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    return true;
                } else {
                    throw new Exception("Failed to send email, status code: " + responseEntity.getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error("Failed to send certificate to administration: " + e.getMessage());
        }
        return false;
    }


    public void updatePassword(String email, String password) {
        try {
            if (email != null && password != null) {
                userRepository.updatePassword(email, password);
            } else {
                log.warn(Messages.VALUE_IS_BLANK, "email or Password");
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "updatePassword because : " + e.getMessage());
        }
    }


    public Boolean activeUser(UserDto user, Boolean state, String reasonDesactivate) {
        Boolean isSended;
        try {
            log.info(Messages.START_FUNCTION, "active User");
            if (user != null) {
                user.setActive(state);
                OriachadUser oriachadUser = userRepository.save(mapUserDtoToEntity(user));
                if (state) {
                    isSended = sendActivateEmailToUser(oriachadUser);
                } else {
                    isSended = sendUnActivateEmailToUser(oriachadUser, reasonDesactivate);
                }
                if (isSended) {
                    log.info(Messages.PROCESS_SUCCESSFULLY, "active/UnActive Message Send it to the User");
                } else {
                    log.error(Messages.PROCESS_SUCCESSFULLY, "active/UnActive Message not Send it to the User");
                }
                log.info(Messages.PROCESS_SUCCESSFULLY, "active User");
                return true;
            } else {
                log.error(Messages.ENTITY_IS_NOT_FOUND, "user");
                return false;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "active the User because :" + e.getMessage());
        }
        return false;
    }

    public Boolean sendActivateEmailToUser(OriachadUser user) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info(Messages.START_FUNCTION, "send Activate Email To User");
            if (user != null) {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                body.add("to", user.getEmail());
                body.add("cc", null);
                body.add("subject", "Félicitation " + user.getFirstName() + " " + user.getLastName());

                // HTML content with embedded image
                String emailBody = "<html><body>"
                        + "<h3>Bienvenue sur la plateforme Oriachad!</h3>"
                        + "<p>Nous vous informons que votre compte a été activé pour accéder à l'application Oriachad.</p>"
                        + "<p>Email : " + user.getEmail() + "</p>"
                        + "<p>Oriachad Teams.</p>"
                        + "<img src='https://www.pajiniweb-dev.com/stokage/oriachad_message_active/active%20account%20message.jpeg' alt='Activation Message' style='width:100%; max-width:600px;' />"
                        + "</body></html>";

                body.add("body", emailBody);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        originalSendMailURL,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    return true;
                } else {
                    throw new Exception("Failed to send email, status code: " + responseEntity.getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "send Activate Email To User because : " + e.getMessage());
        }
        return false;
    }


    public Boolean sendUnActivateEmailToUser(OriachadUser user, String reasonDesactivate) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info(Messages.START_FUNCTION, "send UnActivate Email To User");
            if (user != null) {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                body.add("to", user.getEmail());
                body.add("cc", null);
                body.add("subject", "Alerte " + user.getFirstName() + " " + user.getLastName());
                body.add("body", "Nous vous informons que votre compte sur l'application Oriachad a été désactivé avec:<br><br>" +
                        "Email : " + user.getEmail() + "<br><br>" +
                        "Raison : " + reasonDesactivate + "<br><br>" +

                        "Oriachad Teams. <br>");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        originalSendMailURL,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    return true;
                } else {
                    throw new Exception("Failed to send email, status code: " + responseEntity.getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "send UnActivate Email To User because : " + e.getMessage());
        }
        return false;
    }
}
