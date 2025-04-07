package com.pajiniweb.oriachad_backend.services;


import com.jcraft.jsch.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FtpService {

    Logger log = LoggerFactory.getLogger(FtpService.class);

    private static final String SFTP_SERVER = "161.97.124.154";
    private static final int SFTP_PORT = 22;
    private static final String SFTP_USERNAME = "root";
    private static final String SFTP_PASSWORD = "Abc@/*321";

    private static final String SFTP_BASE_PATH = "/opt/lampp/htdocs/stokage/oriachad_attestation";

    public String storeImage(MultipartFile imageFile) throws Exception {
        JSch jSch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            log.info(Messages.START_FUNCTION, "save image in server");
            session = jSch.getSession(SFTP_USERNAME, SFTP_SERVER, SFTP_PORT);
            session.setPassword(SFTP_PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Change to the base directory
            channelSftp.cd(SFTP_BASE_PATH);

            // Generate a unique filename for the image
            String filename = generateUniqueFilename(imageFile.getOriginalFilename());

            // Store the file on the server
            channelSftp.put(imageFile.getInputStream(), filename);

            // Construct and return the URL of the stored image
            String imageUrl = constructImageUrl(filename);
            log.info(Messages.PROCESS_SUCCESSFULLY, "save image " + imageUrl + " in server");
            return imageUrl;
        } finally {
            // Disconnect the SFTP session and channel
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        // Logic to generate a unique filename
        // You can use a combination of timestamp, random number, or other techniques
        return originalFilename;
    }

    private String constructImageUrl(String filename) {
        return "https://www.pajiniweb-dev.com/stokage/oriachad_attestation/" + filename;
    }

    public boolean deleteImage(String imageUrl) {
        JSch jSch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            log.info(Messages.START_FUNCTION, "delete image from server");

            // Extract the filename from the imageUrl
            String filename = extractFilenameFromUrl(imageUrl);


            session = jSch.getSession(SFTP_USERNAME, SFTP_SERVER, SFTP_PORT);
            session.setPassword(SFTP_PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Change to the base directory
            channelSftp.cd(SFTP_BASE_PATH);

            // Delete the file from the server
            channelSftp.rm(filename);

            log.info(Messages.PROCESS_SUCCESSFULLY, "deleted image " + filename + " from server");
            return true;
        } catch (SftpException e) {
            log.error(e.getMessage());
            return false;
        } catch (JSchException e) {
            log.error(e.getMessage());
            return false;
        } finally {
            // Disconnect the SFTP session and channel
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    private String extractFilenameFromUrl(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        if (lastSlashIndex >= 0 && lastSlashIndex < imageUrl.length() - 1) {
            return imageUrl.substring(lastSlashIndex + 1);
        }
        return null;
    }

}