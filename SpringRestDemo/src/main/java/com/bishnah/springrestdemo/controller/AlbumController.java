package com.bishnah.springrestdemo.controller;

import com.bishnah.springrestdemo.model.Account;
import com.bishnah.springrestdemo.model.Album;
import com.bishnah.springrestdemo.model.Photo;
import com.bishnah.springrestdemo.payload.auth.album.AlbumPayloadDTO;
import com.bishnah.springrestdemo.payload.auth.album.AlbumViewDTO;
import com.bishnah.springrestdemo.service.AccountService;
import com.bishnah.springrestdemo.service.AlbumService;
import com.bishnah.springrestdemo.service.PhotoService;
import com.bishnah.springrestdemo.util.AppUtils.AppUtil;
import com.bishnah.springrestdemo.util.constants.AlbumError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Album Controller", description = "Controller for Album and Photo Management")
@Slf4j
public class AlbumController {

    static final String PHOTOS_FOLDER_NAME = "photos";
    static final String THUMBNAIL_FOLDER_NAME = "thumbnails";
    static final int THUMBNAIL_MAX_SIZE = 300;

    private final AccountService accountService;
    private final AlbumService albumService;
    private final PhotoService photoService;

    public AlbumController(AccountService accountService, AlbumService albumService, PhotoService photoService) {
        this.accountService = accountService;
        this.albumService = albumService;
        this.photoService = photoService;
    }


    @PostMapping(value = "/albums/add", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED) //optional
    @ApiResponse(responseCode = "400", description = "Please enter a valid name and description")
    @ApiResponse(responseCode = "200", description = "Album added")
    @Operation(summary = "Add an Album")
    @SecurityRequirement(name = "Spring-Demo-Api")
    public ResponseEntity<AlbumViewDTO> addAlbum(@Valid @RequestBody AlbumPayloadDTO albumPayloadDTO, Authentication authentication) {
        try {
            Album album = new Album();
            album.setTitle(albumPayloadDTO.getTitle());
            album.setDescription(albumPayloadDTO.getDescription());
            String email = authentication.getName();
            Optional<Account> optionalAccount = accountService.findByEmail(email);
            Account account = optionalAccount.get();
            album.setAccount(account);
            album = albumService.createAlbum(album);
            AlbumViewDTO albumViewDTO = new AlbumViewDTO(album.getId(), album.getTitle(), album.getDescription());
            return ResponseEntity.ok(albumViewDTO);

        } catch (Exception e) {
            log.debug(AlbumError.ADD_ALBUM_ERROR + " " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/albums", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "List of Albums")
    @ApiResponse(responseCode = "401", description = "Please check Access Token")
    @ApiResponse(responseCode = "403", description = "Token/Scope Error")
    @Operation(summary = "List album Api")
    @SecurityRequirement(name = "Spring-Demo-Api")
    public List<AlbumViewDTO> albums(Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();

        List<AlbumViewDTO> albumViewDTOList = new ArrayList<>();
        for (Album album : albumService.findByAccount_id(account.getId())) {
            albumViewDTOList.add(new AlbumViewDTO(album.getId(), album.getTitle(), album.getDescription()));
        }
        return albumViewDTOList;
    }

    @PostMapping(value = "/albums/{album_id}/upload-photos", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload Photo in Album")
    @ApiResponse(responseCode = "400", description = "Please check payload or token")
    @SecurityRequirement(name = "Spring-Demo-Api")
    public ResponseEntity<List<HashMap<String,List<String>>>> photos(@RequestPart() MultipartFile[] files, @PathVariable Long album_id, Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();

        Optional<Album> optionalAlbum = albumService.findById(album_id);
        Album album;
        if (optionalAlbum.isPresent()) {
            album = optionalAlbum.get();
            if (account.getId() != album.getAccount().getId()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


        List<String> fileNamesWithSuccess = new ArrayList<>();
        List<String> fileNamesWithError = new ArrayList<>();

        Arrays.asList(files).forEach(file -> {
            String contentType = file.getContentType();
            if (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jped")) {
                fileNamesWithSuccess.add(file.getOriginalFilename());

                int length = 10;
                boolean useLetters = true;
                boolean useNumbers = true;

                try {
                    String fileName = file.getOriginalFilename();
                    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
                    String finalPhotoName = generatedString + fileName;
                    String absolute_fileLocation = AppUtil.get_photo_upload_path(finalPhotoName, PHOTOS_FOLDER_NAME,album_id);
                    Path path = Paths.get(absolute_fileLocation);
                    Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                    Photo photo = new Photo();
                    photo.setTitle(fileName);
//                    photo.setDescription();
                    photo.setFileName(finalPhotoName);
                    photo.setOriginalFileName(fileName);
                    photo.setAlbum(album);
                    photoService.save(photo);

                    BufferedImage thumbImg = AppUtil.getThumbnail(file,THUMBNAIL_MAX_SIZE);
                    File thumbnail_location = new File(AppUtil.get_photo_upload_path(finalPhotoName,
                            THUMBNAIL_FOLDER_NAME,album_id));

                    ImageIO.write(thumbImg,file.getContentType().split("/")[1],thumbnail_location);



                } catch (Exception e) {
                    log.debug(AlbumError.PHOTO_UPLOAD_ERROR.toString() + " " + e.getMessage());
                    fileNamesWithError.add(file.getOriginalFilename());

                }
            }else {
                fileNamesWithError.add(file.getOriginalFilename());
            }
        });

        HashMap<String, List<String>> result = new HashMap<>();
        result.put("Success", fileNamesWithSuccess);
        result.put("Error", fileNamesWithError);

        List<HashMap<String,List<String>>> response = new ArrayList<>();
        response.add(result);

        return ResponseEntity.ok(response);

    }


}






















