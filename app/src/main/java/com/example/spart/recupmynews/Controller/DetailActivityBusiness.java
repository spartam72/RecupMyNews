package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


import com.example.spart.recupmynews.MyBusinessAPI.ArticleBusiness;
import com.example.spart.recupmynews.R;

import java.util.Objects;


public class DetailActivityBusiness extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail_business);

        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Details from Business Article" );
        toolbar.setTitleTextColor(getResources().getColor( R.color.colorWhite ));
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        WebView webViewDetail = findViewById( R.id.webViewDescription );
        ArticleBusiness result = (ArticleBusiness) Objects.requireNonNull( getIntent().getExtras() ).getSerializable( "result" );
        webViewDetail.loadUrl( result.getUrl());



    }
    
}
