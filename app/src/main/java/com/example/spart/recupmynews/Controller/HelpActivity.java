package com.example.spart.recupmynews.Controller;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.spart.recupmynews.R;

import java.util.Objects;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.help_layout );

        Toolbar toolbar =  findViewById( R.id.toolbar );
      //  toolbar.setTitleTextAppearance( this,R.style.ToolbarTheme );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "HelpActivity" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getWindow().setStatusBarColor( ContextCompat.getColor(this, R.color.colorStatusBarHome ));
    }
}
