package com.nkpdqz.service;

import com.nkpdqz.domain.Image;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface ImageService {

    List<Image> getImageListById(int id);
    void addImage(Image image, InputStream inputStream);
    void deleteImageById(String id,String url);

}
