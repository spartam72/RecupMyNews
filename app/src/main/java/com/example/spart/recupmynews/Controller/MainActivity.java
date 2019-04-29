package com.example.spart.recupmynews.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.View.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private final String[] pageTitle = {"TOP STORIES", "MOST POPULAR", "BUSINESS"};
    Toolbar toolbar;
    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        viewPager = findViewById( R.id.view_pager );
        toolbar = findViewById( R.id.toolbar );
        drawer = findViewById( R.id.drawerLayout );

        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor(getResources().getColor( R.color.colorWhite));
        toolbar.setTitleMarginStart( 120 );

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        //setting Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = findViewById( R.id.tab_layout );
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab( tabLayout.newTab().setText( pageTitle[i] ) );
        }

        //set gravity for tab bar
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL );

        //handling navigation view item event
        NavigationView navigationView = findViewById( R.id.nav_view );
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter( getSupportFragmentManager() );
        viewPager.setAdapter( pagerAdapter );

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );

                if (tab.getPosition() == 0) {
                    toolbar.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.colorIceBerg));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorIceBerg));
                } else if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDarkOther));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDarkOther));
                } else {
                    toolbar.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryOther ));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryOther ));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_fr1) {
            viewPager.setCurrentItem( 0 );

        }  else if (id == R.id.nav_fr2) {
            viewPager.setCurrentItem( 1 );

        } else if (id == R.id.nav_fr3) {
            viewPager.setCurrentItem( 2 );

        } else if (id == R.id.nav_notif) {
            Intent intent = new Intent( MainActivity.this, NotificationsActivity.class );
            startActivity( intent );

        } else if (id == R.id.nav_search) {
            Intent intent = new Intent( MainActivity.this, SearchActivity.class );
            startActivity( intent );

        }else if (id == R.id.nav_about) {
            Intent intent = new Intent( MainActivity.this, AboutActivity.class );
            startActivity( intent );

        }else if (id == R.id.nav_help) {
            Intent intent = new Intent( MainActivity.this, HelpActivity.class );
            startActivity( intent );

        } else if (id == R.id.nav_close) {
            MainActivity.this.finish();
        }

        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.popup_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_tool_bar:
                Intent intent = new Intent(
                        MainActivity.this, SearchActivity.class );
                startActivity( intent );
                return true;
            case R.id.notifications_popup:
                Intent intent1 = new Intent(
                        MainActivity.this, NotificationsActivity.class );
                startActivity( intent1 );
                return true;

            case R.id.help_popup:
                Intent intent2 = new Intent(
                        MainActivity.this, HelpActivity.class );
                startActivity( intent2 );
                return true;

            case R.id.about_popup:
                Intent intent3 = new Intent(
                        MainActivity.this, AboutActivity.class );
                startActivity( intent3 );
                return true;
            default:
                return false;
        }
    }
}

