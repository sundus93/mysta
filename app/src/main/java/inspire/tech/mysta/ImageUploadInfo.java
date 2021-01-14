package inspire.tech.mysta;

public class ImageUploadInfo {

    public String imageName;

    public String imageURL;
    public int imageLevel;

    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String name, String url, int imglevel) {

        this.imageName = name;
        this.imageURL = url;
        this.imageLevel = imglevel;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getImageLevel() { return imageLevel; }
}
