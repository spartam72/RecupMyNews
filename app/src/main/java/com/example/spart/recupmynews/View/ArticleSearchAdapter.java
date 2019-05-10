package com.example.spart.recupmynews.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchArticles;
import com.example.spart.recupmynews.R;

import java.util.List;


public class ArticleSearchAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {

    private final List<ArticleSearchArticles> articleSearchArticles;
    private final RequestManager glide;

    public ArticleSearchAdapter(List<ArticleSearchArticles> articlesearchArticles, RequestManager glide) {
        this.articleSearchArticles = articlesearchArticles;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.article_item_search_modif_subsection, parent, false);

        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder viewHolder, int position) {
        viewHolder.updateWithArticleSearch(this.articleSearchArticles.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.articleSearchArticles.size();
    }

    public ArticleSearchArticles getArticle(int position){
        return this.articleSearchArticles.get(position);
    }
}