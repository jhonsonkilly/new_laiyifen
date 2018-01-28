package dsshare;

import android.graphics.Bitmap;

/**
 * Created by meizivskai on 2017/10/9.
 */

public class DrawPhotoBean {

    private Bitmap logo;
    private Bitmap photo;
    private String name;
    private String description;
    private String price;
    private Bitmap QRImage;

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getQRImage() {
        return QRImage;
    }

    public void setQRImage(Bitmap QRImage) {
        this.QRImage = QRImage;
    }
}
