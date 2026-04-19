package com.example.sportsnews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportsnews.R;
import com.example.sportsnews.adapters.VerticalNewsAdapter;
import com.example.sportsnews.data.AppDatabase;
import com.example.sportsnews.data.Bookmark;
import com.example.sportsnews.data.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.bookmarks_recycler_view);
        AppDatabase db = AppDatabase.getInstance(getContext());

        List<Bookmark> bookmarks = db.bookmarkDao().getAllBookmarks();
        List<NewsItem> newsItems = new ArrayList<>();
        for (Bookmark b : bookmarks) {
            newsItems.add(new NewsItem(b.newsId, b.title, b.description, b.imageUrl, b.category, false));
        }
        VerticalNewsAdapter adapter = new VerticalNewsAdapter(newsItems, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("news_id", item.getId());
            DetailFragment detail = new DetailFragment();
            detail.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detail)
                    .addToBackStack(null).commit();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
