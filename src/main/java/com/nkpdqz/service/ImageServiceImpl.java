package com.nkpdqz.service;

import com.nkpdqz.dao.ImageDao;
import com.nkpdqz.domain.Image;
import com.nkpdqz.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDao imageDao;

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public List<Image> getImageListById(int id) {
        List<Image> images = imageDao.getImagesByUserId(id);
        return images;
    }

    @Override
    public void addImage(Image image, InputStream inputStream) {
        imageDao.addImage(image.getName(),image.getUrl(),image.getDate(),image.getUser().getId());
        FileUtils.upload(inputStream,image.getUrl());
    }

    @Override
    public void deleteImageById(String ids,String urls) {
        String[] idArray = ids.split(",");
        String[] urlArray = urls.split(",");
        if (!"".equals(idArray[0]) && !"".equals(urlArray[0])){
            for (int i=0;i<idArray.length;i++){
                FileUtils.delete(urlArray[i]);
                imageDao.deleteImageById(Integer.parseInt(idArray[i]));
            }
        }
        //imageDao.deleteImageById(ids);
        //FileUtils.delete(urls);
    }


}
