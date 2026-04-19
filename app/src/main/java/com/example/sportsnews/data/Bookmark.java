package com.example.sportsnews.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int newsId;
    public String title;
    public String description;
    public String imageUrl;
    public String category;

    public Bookmark(int newsId, String title, String description, String imageUrl, String category) {
        this.newsId = newsId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
