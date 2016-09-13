package c.y.s.pangu.app.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import c.y.s.pangu.app.BR;


/**
 * Created by Administrator on 2016/9/10.
 */
public class VModel extends BaseObservable{


    private String thumbnail;

    private String publisherImg;

    private String publisherName;

    private String profiles;

    private boolean isShowLogo;

    public VModel(String thumbnail, String publisherImg, String publisherName, String profiles) {
        this.thumbnail = thumbnail;
        this.publisherImg = publisherImg;
        this.publisherName = publisherName;
        this.profiles = profiles;
    }

    @Bindable
    public String getProfiles() {
        return profiles;
    }

    @Bindable
    public String getPublisherName() {
        return publisherName;
    }

    @Bindable
    public boolean isShowLogo() {
        return isShowLogo;
    }

    public void setShowLogo(boolean showLogo) {
        isShowLogo = showLogo;
        notifyPropertyChanged(BR.showLogo);
    }

    public String getPublisherImg() {
        return publisherImg;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;

    }

    public void setPublisherImg(String publisherImg) {
        this.publisherImg = publisherImg;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
        notifyPropertyChanged(BR.publisherName);
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
        notifyPropertyChanged(BR.profiles);
    }
}
