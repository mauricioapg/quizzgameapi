package com.quizzgameapi.dto;

public class CategoryRequestDTO {

    private String desc;
    private String image;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String base64Image) {
        this.image = base64Image;
    }
}
