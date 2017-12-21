package com.nkpdqz.dao;


import com.nkpdqz.domain.Image;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ImageDao {

    List<Image> getImagesByUserId(int id);
    void addImage(String name, String url, Date date,int user_id);
    void deleteImageById(int id);
}
