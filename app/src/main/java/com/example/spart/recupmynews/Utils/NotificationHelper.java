package com.example.spart.recupmynews.Utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.example.spart.recupmynews.Controller.NotificationResultActivity;
import com.example.spart.recupmynews.R;

public class NotificationHelper extends ContextWrapper {


    private static final String channelID = "channelID";
    private static final String channelName = "Channel Name";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super( base );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel( channelID, channelName, NotificationManager.IMPORTANCE_HIGH );

        getManager().createNotificationChannel( channel );
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        }

        return mManager;
    }

    /**
     * build Notification if News are found
     * @return notification
     */
    public NotificationCompat.Builder getChannelArticlesFound(int numberOfArticles) {

            RemoteViews collapsedView = new RemoteViews( getPackageName(), R.layout.notification_collapsed );
            RemoteViews expandedView = new RemoteViews( getPackageName(), R.layout.notification_expanded );

            collapsedView.setTextViewText( R.id.text_view_collapsed_1, "Hello, dear reader!" );
            collapsedView.setTextViewText( R.id.text_view_collapsed_2, numberOfArticles + " Article(s) found with your selected settings !" );
            expandedView.setTextViewText( R.id.text_view_expanded, numberOfArticles + " Article(s) found with your selected settings !" );
            expandedView.setTextViewText( R.id.text_view_expanded_1, "Click on image to see result(s)." );

        Intent resultIntent = new Intent(getBaseContext(), NotificationResultActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);

            return new NotificationCompat.Builder( getApplicationContext(), channelID ).setColor( ContextCompat.getColor( getApplicationContext(), R.color.colorPistache ) )
                    .setCustomContentView( collapsedView )
                    .setCustomBigContentView( expandedView )
                    .setStyle( new NotificationCompat.DecoratedCustomViewStyle() )
                    .setPriority( NotificationCompat.PRIORITY_HIGH )
                    .setSound( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION ) )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
                    .setSmallIcon( R.drawable.ic_news_logo );

    }

    /**
     * build Notification if News aren't found
     * @return notification
     */

    public NotificationCompat.Builder getChannelNoArticle() {

        RemoteViews collapsedView = new RemoteViews( getPackageName(),R.layout.notification_collapsed );
        RemoteViews expandedView = new RemoteViews( getPackageName(),R.layout.notification_expanded );

        collapsedView.setTextViewText( R.id.text_view_collapsed_1,"Hello, dear reader!" );
        collapsedView.setTextViewText( R.id.text_view_collapsed_2,"Sorry, any article found with your selected settings ." );
        expandedView.setTextViewText(  R.id.text_view_expanded,"Sorry, any article found with your selected settings ." );
        expandedView.setTextViewText( R.id.text_view_expanded_1,"See you soon !"  );

        return new NotificationCompat.Builder( getApplicationContext(), channelID ).setColor( ContextCompat.getColor( getApplicationContext(), R.color.colorAccent ) )
                .setCustomContentView( collapsedView )
                .setCustomBigContentView( expandedView )
                .setStyle( new NotificationCompat.DecoratedCustomViewStyle() )
                .setPriority( NotificationCompat.PRIORITY_HIGH )
                .setSound( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_ALARM ) )
                .setAutoCancel( true )
                .setSmallIcon( R.drawable.ic_news_logo );
    }
}






