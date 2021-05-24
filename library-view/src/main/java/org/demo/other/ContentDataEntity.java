package org.demo.other;

public class ContentDataEntity {

    private String title;
    private String imgUrl;
    private String imgUrlRes;

    public ContentDataEntity() {
    }

    public ContentDataEntity(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public ContentDataEntity(String title, String imgUrl, String imgUrlRes) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgUrlRes = imgUrlRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrlRes() {
        return imgUrlRes;
    }

    public void setImgUrlRes(String imgUrlRes) {
        this.imgUrlRes = imgUrlRes;
    }
}
