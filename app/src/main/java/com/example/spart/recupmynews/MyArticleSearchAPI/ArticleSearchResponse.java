package com.example.spart.recupmynews.MyArticleSearchAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResponse {

    @SerializedName("response")
    @Expose
    private ArticleSearchResult result;

    public ArticleSearchResult getResult() {
        return result;
    }

    public void setResult(ArticleSearchResult response) {
        this.result = response;
    }

}