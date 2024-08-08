package com.bishnah.springrestdemo.service;

import com.bishnah.springrestdemo.model.Photo;
import com.bishnah.springrestdemo.repository.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo save(Photo photo){
        return photoRepository.save(photo);
    }
}

