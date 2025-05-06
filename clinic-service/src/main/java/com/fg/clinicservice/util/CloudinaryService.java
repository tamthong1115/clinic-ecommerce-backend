package com.fg.clinicservice.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.*;

@Service
public class CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    private static final String BASE_FOLDER = "ClinicMedia";

    /**
     * Uploads an image for a clinic room to Cloudinary.
     *
     * @param files The multipart file to upload
     * @return The secure URL of the uploaded image
     * @throws RuntimeException if the upload fails
     */
    public List<String> uploadClinicRoomImage(List<MultipartFile> files) {
        return uploadMuitipleImages(files, "clinic-rooms");
    }

    /**
     * Uploads an icon for a department/specialty to Cloudinary.
     *
     * @param file The multipart file to upload
     * @return The secure URL of the uploaded image
     * @throws RuntimeException if the upload fails
     */
    public String uploadDepartmentIcon(MultipartFile file) {
        return uploadImage(file, "departments");
    }

    /**
     * Uploads an icon for a service to Cloudinary.
     *
     * @param file The multipart file to upload
     * @return The secure URL of the uploaded image
     * @throws RuntimeException if the upload fails
     */
    public String uploadServiceIcon(MultipartFile file) {
        return uploadImage(file, "services");
    }

    /**
     * Deletes an image from Cloudinary using its secure URL.
     *
     * @param imageUrl The secure URL of the image to delete
     * @throws RuntimeException if deletion fails
     */
    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image: " + imageUrl, e);
        }
    }

    /**
     * Uploads an image to a specific folder in Cloudinary.
     *
     * @param file       The multipart file to upload
     * @param folderName The subfolder name within the BASE_FOLDER
     * @return The secure URL of the uploaded image
     * @throws RuntimeException if the upload fails
     */
    private String uploadImage(MultipartFile file, String folderName) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", BASE_FOLDER + "/" + folderName
            );
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed to folder: " + folderName, e);
        }
    }

    /**
     * Uploads multiple images to a specified subfolder within the Cloudinary base folder.
     *
     * @param files      The list of multipart file to upload
     * @param folderName The subfolder name within the BASE_FOLDER
     * @return a list of secure URLs pointing to the uploaded images
     * @throws RuntimeException if any file upload fails
     */

    private List<String> uploadMuitipleImages(List<MultipartFile> files, String folderName) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String url = uploadImage(file, folderName);
                urls.add(url);
            }
        }
        return urls;
    }

    /**
     * Extracts the public ID from a Cloudinary secure URL.
     *
     * @param imageUrl The secure URL of the image
     * @return The public ID used by Cloudinary
     * @throws RuntimeException if extraction fails
     */
    private String extractPublicId(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath(); // e.g., /ClinicMedia/clinic-rooms/image_id.jpg
            if (path.startsWith("/")) {
                path = path.substring(1); // remove leading slash
            }
            int extensionIndex = path.lastIndexOf('.');
            return extensionIndex != -1 ? path.substring(0, extensionIndex) : path;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract public ID from URL: " + imageUrl, e);
        }
    }
}
