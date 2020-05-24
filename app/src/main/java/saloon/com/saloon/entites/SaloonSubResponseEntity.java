package saloon.com.saloon.entites;

import java.io.Serializable;

/**
 * Created by mukesh.thakur on 03/30/2018.
 */

public class SaloonSubResponseEntity implements Serializable {

    String name = "";
    String desc = "";
    String sId = "";
    String originalimg = "";
    String img = "";
    String thumbImage = "";
    String smallImage = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getOriginalimg() {
        return originalimg;
    }

    public void setOriginalimg(String originalimg) {
        this.originalimg = originalimg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}