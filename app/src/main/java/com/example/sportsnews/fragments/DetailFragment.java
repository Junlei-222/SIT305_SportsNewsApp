package com.example.sportsnews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.sportsnews.R;
import com.example.sportsnews.adapters.RelatedNewsAdapter;
import com.example.sportsnews.data.AppDatabase;
import com.example.sportsnews.data.Bookmark;
import com.example.sportsnews.data.DataProvider;
import com.example.sportsnews.data.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    private NewsItem currentItem;
    private Button bookmarkButton;
    private AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        db = AppDatabase.getInstance(getContext());

        int newsId = getArguments().getInt("news_id", -1);
        currentItem = findNewsById(newsId);
        if (currentItem == null) return view;

        ImageView imageView = view.findViewById(R.id.detail_image);
        TextView titleView = view.findViewById(R.id.detail_title);
        TextView descView = view.findViewById(R.id.detail_description);
        bookmarkButton = view.findViewById(R.id.bookmark_button);
        RecyclerView relatedRecycler = view.findViewById(R.id.related_recycler_view);

        Glide.with(this).load(currentItem.getImageUrl()).into(imageView);
        titleView.setText(currentItem.getTitle());
        descView.setText(currentItem.getDescription());

        List<NewsItem> related = new ArrayList<>();
        for (NewsItem item : DataProvider.getNewsList()) {
            if (item.getCategory().equals(currentItem.getCategory()) && item.getId() != currentItem.getId()) {
                related.add(item);
            }
        }
        RelatedNewsAdapter relatedAdapter = new RelatedNewsAdapter(related, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("news_id", item.getId());
            DetailFragment newDetail = new DetailFragment();
            newDetail.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newDetail)
                    .addToBackStack(null).commit();
        });
        relatedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        relatedRecycler.setAdapter(relatedAdapter);

        updateBookmarkButton();

        bookmarkButton.setOnClickListener(v -> {
            if (db.bookmarkDao().isBookmarked(currentItem.getId()) > 0) {
                db.bookmarkDao().deleteByNewsId(currentItem.getId());
                Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
            } else {
                Bookmark bookmark = new Bookmark(currentItem.getId(), currentItem.getTitle(),
                        currentItem.getDescription(), currentItem.getImageUrl(), currentItem.getCategory());
                db.bookmarkDao().insert(bookmark);
                Toast.makeText(getContext(), "Bookmarked", Toast.LENGTH_SHORT).show();
            }
            updateBookmarkButton();
        });

        return view;
    }

    private void updateBookmarkButton() {
        boolean isBookmarked = db.bookmarkDao().isBookmarked(currentItem.getId()) > 0;
        bookmarkButton.setText(isBookmarked ? "Remove Bookmark" : "Bookmark");
    }

    private NewsItem findNewsById(int id) {
        for (NewsItem item : DataProvider.getNewsList()) {
            if (item.getId() == id) return item;
        }
        return null;
    }
}
