package com.example.spart.recupmynews.Api;



import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesSearchArticles {


    public static Observable<ArticleSearchResponse> getArticleSearch(String keyWords, String category, String beginDate, String endDate){
        SectionsAPI sectionsAPI = ApiClient.getClient().create( SectionsAPI.class);
        return sectionsAPI.SearchArticles(keyWords, category, beginDate, endDate)
                .subscribeOn( Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


}
