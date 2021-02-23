package com.example.android.hw2;

public class Brew {
    private String name;
    private String description;
    private String img_url;
    private boolean favorite;

    public Brew(String name, String description, String img_url){
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.favorite = false;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getImg_url() { return img_url; }

    public void setImg_url(String img_url) { this.img_url = img_url; }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}
