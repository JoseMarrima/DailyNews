package com.example.dailynews.listnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.R;
import com.example.dailynews.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ListNewsAdapterViewHolder> {
    private List<NewsArticle> newsArticles = new ArrayList<>();

    @NonNull
    @Override
    public ListNewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.article_item_layout, parent, false);
        return new ListNewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsAdapterViewHolder holder, int position) {
        holder.onBind(newsArticles.get(position));
    }

    @Override
    public int getItemCount() {
        if (newsArticles != null) {
            return newsArticles.size();
        }
        return 0;
    }

    public class ListNewsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public ListNewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textView);
            description  = itemView.findViewById(R.id.description_textView);
        }

        public void onBind(NewsArticle newsArticle) {
            title.setText(newsArticle.getTitle());
            description.setText(newsArticle.getDescription());
        }
    }
}
