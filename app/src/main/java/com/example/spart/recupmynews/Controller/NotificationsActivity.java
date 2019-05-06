package com.example.spart.recupmynews.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.Constant;
import com.example.spart.recupmynews.Utils.InternetConnectionState;

import java.util.Calendar;
import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private SharedPreferences mSharedPreferences;

    private EditText searchQueryTerm;
    private CheckBox checkBoxArts;
    private CheckBox checkBoxPolitics;
    private CheckBox checkBoxBusiness;
    private CheckBox checkBoxSports;
    private CheckBox checkBoxEntrepreneurs;
    private CheckBox checkBoxTravel;
    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notifications );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Notifications" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getWindow().setStatusBarColor( ContextCompat.getColor(this, R.color.colorStatusBarHome ));

         searchQueryTerm = findViewById( R.id.search_query_term_text_input_edit_text );
         checkBoxArts = findViewById( R.id.check_box_arts );
         checkBoxPolitics = findViewById( R.id.check_box_politics );
         checkBoxBusiness = findViewById( R.id.check_box_business );
         checkBoxSports = findViewById( R.id.check_box_sports );
         checkBoxEntrepreneurs = findViewById( R.id.check_box_entrepreneurs );
         checkBoxTravel = findViewById( R.id.check_box_travel );
         mySwitch = findViewById( R.id.notications_switch );
        LinearLayout dateLinearLayout = findViewById( R.id.linear_layout_date );
        Button searchButton = findViewById( R.id.search_button );

        dateLinearLayout.setVisibility( View.GONE );
        searchButton.setVisibility( View.GONE );

        if( InternetConnectionState.noInternetAccess( Objects.requireNonNull( getApplicationContext() ))){
            // If no internet,disable searchButton
            mySwitch.setClickable( false );
        }else {
            mySwitch.setClickable( true );
        }

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        this.recoverUserPreferences();

        mySwitch.setOnCheckedChangeListener(this);
        checkBoxArts.setOnCheckedChangeListener(this);
        checkBoxBusiness.setOnCheckedChangeListener(this);
        checkBoxPolitics.setOnCheckedChangeListener(this);
        checkBoxTravel.setOnCheckedChangeListener(this);
        checkBoxSports.setOnCheckedChangeListener(this);
        checkBoxEntrepreneurs.setOnCheckedChangeListener(this);

        searchQueryTerm.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                cancelAlarm();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView.getId() == R.id.notications_switch){
            // Click on switch button
            if(isChecked){
                // User has enabled notifications
                if(checkAllParameters(getBaseContext(), searchQueryTerm,checkBoxArts,checkBoxBusiness,checkBoxPolitics,checkBoxTravel,checkBoxSports,checkBoxEntrepreneurs)){
                    startAlarm();
                }else{
                    // Some parameters are missing, disable switch
                    mySwitch.setChecked(false);
                }
            }else{
                // User has disabled notifications
                cancelAlarm();
                Toast.makeText( this,getApplicationContext().getResources().getString(R.string.cancel_notif), Toast.LENGTH_SHORT).show();
            }
        }else {
            // Click on a checkbox: user changes the criteria, disable the alarm
            cancelAlarm();
        }

    }
    public  boolean checkAllParameters(Context context, EditText searchQueryTerm, CheckBox cbArts, CheckBox cbBusiness, CheckBox cbPolitics, CheckBox cbTravel,
                                       CheckBox cbSports, CheckBox cbEntrepreneurs){

        if(searchQueryTerm.getText().toString().trim().isEmpty()){
            Toast.makeText(context, context.getResources().getString( R.string.error_query), Toast.LENGTH_LONG).show();
            searchQueryTerm.requestFocus();
            return false;
        }
        if(!cbArts.isChecked() && !cbBusiness.isChecked() &&
                !cbPolitics.isChecked() && !cbTravel.isChecked() && !cbSports.isChecked() && !cbEntrepreneurs.isChecked()){
            Toast.makeText(context, context.getResources().getString(R.string.error_category), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void startAlarm(){

        String query = searchQueryTerm.getText().toString().trim();
        String category = "news_desk:(";
        if (checkBoxArts.isChecked())
            category += "\"Arts\" ";
        if (checkBoxBusiness.isChecked())
            category += "\"Business\" ";
        if (checkBoxPolitics.isChecked())
            category += "\"Politics\" ";
        if (checkBoxTravel.isChecked())
            category += "\"Travel\"";
        if (checkBoxSports.isChecked())
            category += "\"Sports\"";
        if (checkBoxEntrepreneurs.isChecked())
            category += "\"Entrepreneurs\"";
        category += ")";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set( Calendar.MINUTE, 0);
        calendar.set( Calendar.SECOND, 0 );
        // Pass parameters to the intent
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(Constant.QUERY, query);
        intent.putExtra( Constant.CATEGORY, category);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
       // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000 , pendingIntent);

        Toast.makeText( this, R.string.set_notif, Toast.LENGTH_SHORT ).show();

        }

    private void cancelAlarm(){
        Intent intent = new Intent(getApplicationContext()  , AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        mySwitch.setChecked(false);
    }

    @Override
    protected void onStop() {
        // Save the notification data
        saveUserPreferences();
        super.onStop();
    }

    private void saveUserPreferences(){
        mSharedPreferences.edit().putString(Constant.SEARCH_FIELD, searchQueryTerm.getText().toString()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_ARTS, checkBoxArts.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_BUSINESS, checkBoxBusiness.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_POLITICS, checkBoxPolitics.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_TRAVEL, checkBoxTravel.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_SPORTS, checkBoxSports.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(Constant.CHECKBOX_ENTREPRENEURS, checkBoxEntrepreneurs.isChecked()).apply();
        mSharedPreferences.edit().putBoolean( Constant.SWITCH, mySwitch.isChecked()).apply();
    }

    private void recoverUserPreferences(){
        searchQueryTerm.setText(mSharedPreferences.getString(Constant.SEARCH_FIELD, ""));
        checkBoxArts.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_ARTS, false));
        checkBoxBusiness.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_BUSINESS, false));
        checkBoxPolitics.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_POLITICS, false));
        checkBoxTravel.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_TRAVEL, false));
        checkBoxSports.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_SPORTS, false));
        checkBoxEntrepreneurs.setChecked(mSharedPreferences.getBoolean(Constant.CHECKBOX_ENTREPRENEURS, false));
        mySwitch.setChecked(mSharedPreferences.getBoolean(Constant.SWITCH, false));
    }
}


