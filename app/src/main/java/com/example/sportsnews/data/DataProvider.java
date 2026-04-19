package com.example.sportsnews.data;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static List<NewsItem> getNewsList() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(1, "Champions League Final Highlights", "Real Madrid wins 2-0", "https://picsum.photos/id/1/200/150", "Football", true));
        list.add(new NewsItem(2, "NBA Playoffs: Lakers advance", "LeBron scores 30", "https://picsum.photos/id/2/200/150", "Basketball", true));
        list.add(new NewsItem(3, "Cricket World Cup 2026", "Australia vs India final", "https://picsum.photos/id/3/200/150", "Cricket", true));
        list.add(new NewsItem(4, "Transfer Window: Messi signs", "New club announced", "https://picsum.photos/id/4/200/150", "Football", false));
        list.add(new NewsItem(5, "Basketball: Rising stars", "Young players shine", "https://picsum.photos/id/5/200/150", "Basketball", false));
        list.add(new NewsItem(6, "Cricket: New T20 league", "Exciting matches ahead", "https://picsum.photos/id/6/200/150", "Cricket", false));
        return list;
    }
}
