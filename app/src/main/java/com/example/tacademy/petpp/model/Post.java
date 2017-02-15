package com.example.tacademy.petpp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tacademy on 2017-02-10.
 */

public class Post {

    String kakaoId, name, content, image;
    // heart 카운트(좋아요 개수)
    int heart_count;
    Map<String, Boolean> hearts = new HashMap<>();

    public Post() {
    }

    public Post(String kakaoId, String name, String content, String image) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.content = content;
        this.image = image;
    }

    public Map<String, Object> toPostMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("kakaoId", kakaoId);
        map.put("name", name);
        map.put("content", content);
        map.put("image", image);
        map.put("hearts", hearts);
        return map;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHeart_count() {
        return heart_count;
    }

    public void setHeart_count(int heart_count) {
        this.heart_count = heart_count;
    }

    public Map<String, Boolean> getHearts() {
        return hearts;
    }

    public void setHearts(Map<String, Boolean> hearts) {
        this.hearts = hearts;
    }
}
