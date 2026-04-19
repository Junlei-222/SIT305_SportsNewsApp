package com.example.sportsnews.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insert(Bookmark bookmark);

    @Query("DELETE FROM bookmarks WHERE newsId = :newsId")
    void deleteByNewsId(int newsId);

    @Query("SELECT * FROM bookmarks")
    List<Bookmark> getAllBookmarks();

    @Query("SELECT COUNT(*) FROM bookmarks WHERE newsId = :newsId")
    int isBookmarked(int newsId);
}
