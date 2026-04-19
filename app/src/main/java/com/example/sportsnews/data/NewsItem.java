package com.example.sportsnews.data;

public class NewsItem {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private boolean isFeatured;

    public NewsItem(int id, String title, String description, String imageUrl, String category, boolean isFeatured) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isFeatured = isFeatured;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public boolean isFeatured() { return isFeatured; }
}
