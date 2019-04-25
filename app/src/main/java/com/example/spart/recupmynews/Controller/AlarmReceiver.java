package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.example.spart.recupmynews.Api.NewYorkTimesSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchResponse;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.NotificationHelper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;



public class AlarmReceiver extends BroadcastReceiver {

    private static final String QUERY = "query";
    private static final String CATEGORY = "category";
    private Disposable mDisposable;
    private SharedPreferences mPreferences;
    int numberOfArticles;

    NotificationResultActivity notificationResultActivity;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {

        String query = intent.getStringExtra(QUERY);
        String category = intent.getStringExtra(CATEGORY);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(calendar.getTime());

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        this.mDisposable = NewYorkTimesSearchArticles.getArticleSearch(query,category,date,date).subscribeWith( new DisposableObserver<ArticleSearchResponse>() {
            @Override
            public void onNext(ArticleSearchResponse response) {

                List<ArticleSearchArticles> articles = response.getResult().getArticleSearchArticles();
                String listArticlesSerializedToJson = new Gson().toJson(articles);
                mPreferences.edit().putString("listArticles", listArticlesSerializedToJson).apply();
                numberOfArticles = articles.size();
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

                if(numberOfArticles!= 0){

                    NotificationHelper notificationHelper = new NotificationHelper( context );
                    NotificationCompat.Builder nb = notificationHelper.getChannelArticlesFound(numberOfArticles);
                    notificationHelper.getManager().notify( 1, nb.build() );

                }else {

                    NotificationHelper notificationHelper = new NotificationHelper( context );
                    NotificationCompat.Builder nb = notificationHelper.getChannelNoArticle();
                    notificationHelper.getManager().notify( 1, nb.build() );

                }
                disposeWhenDestroy();

            }
        } );

    }
    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()){
            this.mDisposable.dispose();
        }
    }
}


