package me.jessyan.mvparms.demo.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xing on 2016/12/5.
 */
public class ImageDetailEntity {


    /**
     * count : 2581
     * fcount : 0
     * galleryclass : 7
     * id : 399
     * img : /ext/151103/b63460c699d4fd2f019e162fba30cf32.jpg
     * list : [{"gallery":399,"id":6046,"src":"/ext/151103/b63460c699d4fd2f019e162fba30cf32.jpg"},{"gallery":399,"id":6047,"src":"/ext/151103/b99a15d2af793e448bb71a0f7696fe3d.jpg"},{"gallery":399,"id":6048,"src":"/ext/151103/2ce588db6f0f9ab918a706987077b25e.jpg"},{"gallery":399,"id":6049,"src":"/ext/151103/af6e64415b2d237e563c78cf0c54a876.jpg"},{"gallery":399,"id":6050,"src":"/ext/151103/a64582b51ad439efbd9c84b4f4a1e637.jpg"},{"gallery":399,"id":6051,"src":"/ext/151103/b4ab7b555139482f6f9547f5a50aa7da.jpg"}]
     * rcount : 0
     * size : 6
     * status : true
     * time : 1446510484000
     * title : 黄色兰博基尼与尤物
     * url : http://www.tngou.net/tnfs/show/399
     */

    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String img;
    private int rcount;
    private int size;
    private boolean status;
    private long time;
    private String title;
    private String url;
    /**
     * gallery : 399
     * id : 6046
     * src : /ext/151103/b63460c699d4fd2f019e162fba30cf32.jpg
     */

    private ArrayList<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<ListBean> getList() {
        return list;
    }

    public void setList(ArrayList<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int gallery;
        private int id;
        private String src;

        public int getGallery() {
            return gallery;
        }

        public void setGallery(int gallery) {
            this.gallery = gallery;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
