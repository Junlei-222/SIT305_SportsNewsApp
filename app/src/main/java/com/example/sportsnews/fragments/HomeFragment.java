package com.example.sportsnews.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportsnews.R;
import com.example.sportsnews.adapters.HorizontalNewsAdapter;
import com.example.sportsnews.adapters.VerticalNewsAdapter;
import com.example.sportsnews.data.DataProvider;
import com.example.sportsnews.data.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView featuredRecycler, verticalRecycler;
    private EditText searchEditText;
    private List<NewsItem> fullList;
    private VerticalNewsAdapter verticalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        featuredRecycler = view.findViewById(R.id.featured_recycler_view);
        verticalRecycler = view.findViewById(R.id.vertical_recycler_view);
        searchEditText = view.findViewById(R.id.search_edit_text);

        fullList = DataProvider.getNewsList();
        List<NewsItem> featuredList = new ArrayList<>();
        for (NewsItem item : fullList) {
            if (item.isFeatured()) featuredList.add(item);
        }

        HorizontalNewsAdapter horizontalAdapter = new HorizontalNewsAdapter(featuredList, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("news_id", item.getId());
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null).commit();
        });
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        featuredRecycler.setAdapter(horizontalAdapter);

        verticalAdapter = new VerticalNewsAdapter(fullList, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("news_id", item.getId());
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null).commit();
        });
        verticalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        verticalRecycler.setAdapter(verticalAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filter(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filter(String text) {
        List<NewsItem> filtered = new ArrayList<>();
        for (NewsItem item : fullList) {
            if (item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(item);
            }
        }
        verticalAdapter = new VerticalNewsAdapter(filtered, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("news_id", item.getId());
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null).commit();
        });
        verticalRecycler.setAdapter(verticalAdapter);
    }
}
