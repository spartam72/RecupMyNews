package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spart.recupmynews.Api.NewYorkTimesSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchResponse;
import com.example.spart.recupmynews.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchActivity extends AppCompatActivity {


    private EditText searchQueryTerm;
    private CheckBox checkBoxArts;
    private CheckBox checkBoxPolitics;
    private CheckBox checkBoxBusiness;
    private CheckBox checkBoxSports;
    private CheckBox checkBoxEntrepreneurs;
    private CheckBox checkBoxTravel;
    private TextView beginDateUserChoice;
    private  TextView endDateUserChoice;
    private ImageView beginDateButton;
    private ImageView endDateButton;
    private Button searchButton;

    private int day;
    private int month;
    private int year;
    private Disposable disposable;

    private String queryTerm = "";
    private String newsDesk = "";
    private String arts = "";
    private String politics = "";
    private String business = "";
    private String sports = "";
    private String entrepreneurs = "";
    private String travel = "";
    private String beginDate = null;
    private String endDate = null;

    private SharedPreferences mPreferences;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );

        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Searh Articles" );
        toolbar.setTitleTextColor(getResources().getColor( R.color.colorWhite ));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchQueryTerm = findViewById( R.id.search_query_term_text_input_edit_text );
        checkBoxArts = findViewById( R.id.check_box_arts );
        checkBoxPolitics = findViewById( R.id.check_box_politics );
        checkBoxBusiness = findViewById( R.id.check_box_business );
        checkBoxSports = findViewById( R.id.check_box_sports );
        checkBoxEntrepreneurs = findViewById( R.id.check_box_entrepreneurs );
        checkBoxTravel = findViewById( R.id.check_box_travel );
        beginDateUserChoice = findViewById( R.id.begin_date_user_choice_text_view);
        endDateUserChoice = findViewById( R.id.end_date_user_choice_text_view );
        beginDateButton = findViewById(R.id.begin_date_button);
        endDateButton = findViewById(R.id.end_date_button);
        RelativeLayout switchRelativeLayout = findViewById( R.id.switch_relative_layout );
        View borderView = findViewById( R.id.linear_layout_border );
        searchButton = findViewById( R.id.search_button );

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.configureDatePicker();
        this.configureListener();

        switchRelativeLayout.setVisibility( View.GONE );
        borderView.setVisibility( View.GONE );


    }
    private void configureDatePicker(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get( Calendar.DAY_OF_MONTH);
        year = calendar.get( Calendar.YEAR);
        month = calendar.get( Calendar.MONTH);

        beginDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });
    }

    private void DateDialog(final View view){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String sDay = String.valueOf(day);
                String sMonth = String.valueOf(month+1);
                String sYear = String.valueOf(year);

                if (day < 10)
                    sDay = "0" + sDay;
                if (month < 10)
                    sMonth = "0" + sMonth;

                if (view.equals(endDateButton))
                    endDateUserChoice.setText(sDay + "/" + sMonth + "/" + sYear);
                if (view.equals(beginDateButton))
                    beginDateUserChoice.setText(sDay + "/" + sMonth + "/" + sYear);
            }
        };
        DatePickerDialog dpDialog = new DatePickerDialog( Objects.requireNonNull( this ),R.style.DatePickerTheme, listener, year, month, day);
        dpDialog.show();
    }

    private void configureListener(){
        searchQueryTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                queryTerm = searchQueryTerm.getText().toString();
            }
        });

        checkBoxArts.setOnClickListener(new checkBoxListener());
        checkBoxPolitics.setOnClickListener(new checkBoxListener());
        checkBoxBusiness.setOnClickListener(new checkBoxListener());
        checkBoxSports.setOnClickListener(new checkBoxListener());
        checkBoxEntrepreneurs.setOnClickListener(new checkBoxListener());
        checkBoxTravel.setOnClickListener(new checkBoxListener());
        searchButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        } );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    public void executeHttpRequestWithRetrofit(){
        newsDesk = arts + politics + business + sports + entrepreneurs + travel;
        if(queryTerm.isEmpty()){
            Toast.makeText(getBaseContext(), R.string.error_query, Toast.LENGTH_SHORT).show();
        }
        else if(newsDesk.equals( "" ))
            Toast.makeText(getBaseContext(), R.string.error_category, Toast.LENGTH_SHORT).show();
        else {
            if(!beginDateUserChoice.getText().toString().equals( "" ))
                beginDate = adjustDateForRequest(beginDateUserChoice.getText().toString());
            if(!endDateUserChoice.getText().toString().equals( "" ))
                endDate =  adjustDateForRequest(endDateUserChoice.getText().toString());

            this.disposable = NewYorkTimesSearchArticles.getArticleSearch(queryTerm,"news_desk: (" + newsDesk + ")", beginDate , endDate).subscribeWith( new DisposableObserver<ArticleSearchResponse>() {
                @Override
                public void onNext(ArticleSearchResponse response) {
                    testingResponse(response);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getBaseContext(), R.string.on_error, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                }
            });
        }
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void testingResponse(ArticleSearchResponse resp) {
        if (resp.getResult().getArticleSearchArticles().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogTheme);
            builder.setPositiveButton(R.string.button_2_help, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            mPreferences.edit().putString("query", queryTerm).apply();
            mPreferences.edit().putString("newsDesk", "news_desk: (" + newsDesk + ")").apply();
            mPreferences.edit().putString("beginDate", beginDate).apply();
            mPreferences.edit().putString("endDate", endDate).apply();

            List<ArticleSearchArticles> articles = resp.getResult().getArticleSearchArticles();
            String listArticlesSerializedToJson = new Gson().toJson(articles);
            mPreferences.edit().putString("listArticles", listArticlesSerializedToJson).apply();

            Intent resultIntent = new Intent(this, SearchResultActivity.class);
            startActivity(resultIntent);

            int articlesFound = resp.getResult().getArticleSearchArticles().size();
            Toast.makeText( this,articlesFound  + " Article(s) found !", Toast.LENGTH_LONG).show();
        }
    }

    private String adjustDateForRequest(String date){
        String rDay = date.substring(0, 2);
        String rMonth = date.substring(3, 5);
        String rYear = date.substring(6, 10);
        return rYear + rMonth + rDay;
    }

    class checkBoxListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.check_box_arts:
                    if(checkBoxArts.isChecked())
                        arts = "\"Arts\"";
                    else
                        arts = "";
                    break;
                case R.id.check_box_politics:
                    if(checkBoxPolitics.isChecked())
                        politics = "\"Politics\"";
                    else
                        politics = "";
                    break;
                case R.id.check_box_business:
                    if(checkBoxBusiness.isChecked())
                        business = "\"Business\"";
                    else
                        business = "";
                    break;
                case R.id.check_box_sports:
                    if(checkBoxSports.isChecked())
                        sports = "\"Sports\"";
                    else
                        sports = "";
                    break;
                case R.id.check_box_entrepreneurs:
                    if(checkBoxEntrepreneurs.isChecked())
                        entrepreneurs = "\"Entrepreneurs\"";
                    else
                        entrepreneurs = "";
                    break;
                case R.id.check_box_travel:
                    if(checkBoxTravel.isChecked())
                        travel = "\"Travel\"";
                    else
                        travel = "";
                    break;
            }
        }
    }
}




