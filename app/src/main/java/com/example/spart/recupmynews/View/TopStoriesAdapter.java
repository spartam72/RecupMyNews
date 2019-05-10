package com.example.spart.recupmynews.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.spart.recupmynews.Controller.DetailActivityTopStories;
import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.DateConvertUtils;

import java.util.ArrayList;


public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<ArticleTopStories> results;

    public TopStoriesAdapter(Context context, ArrayList<ArticleTopStories> results) {

        this.context = context;
        this.results = results;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;
        private final TextView mPublishedDate;
        private final TextView mAbstractDesc;
        private final TextView section;
        private final ImageView mImageUrl;
        private final CardView articleItemWindow;



        MyViewHolder(View view) {
            super(view);

            mAbstractDesc  = view.findViewById( R.id.abstract_desc);
            articleItemWindow = view.findViewById( R.id.article_item_window);
            mTitle = view.findViewById( R.id.title );
            mPublishedDate= view.findViewById( R.id.published_date );
            mImageUrl = view.findViewById( R.id.image_url );
            section = view.findViewById( R.id.my_section );
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.article_item_categories_modif_sub_section,parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {



        holder.section.setTextColor( ContextCompat.getColor(context,R.color.colorStatusBarHome ) );

        if(results.get( position ).getSubsection()!=null) {
            holder.section.setText( results.get( position ).getSection() + " > " + results.get( position ).getSubsection() );
        }else {
            holder.section.setText( results.get( position ).getSection() + " > ");
        }
        holder.mPublishedDate.setText( DateConvertUtils.getPublished_date_converted(results.get( position ).getPublishedDate() ));
        holder.mTitle.setText(results.get( position ).getTitle() );
        holder.mAbstractDesc.setText( results.get( position ).get_abstract());

        try {
            Glide.with( context ).applyDefaultRequestOptions( new RequestOptions().placeholder( R.drawable.ic_news_logo )
                    .error( R.drawable.ic_news_logo ) )
                    .load( results.get( position ).getMultimedia().get( 1 ).getUrl() )
                    .centerCrop()
                    .apply( RequestOptions.centerCropTransform())
                    .into( holder.mImageUrl );

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        holder.articleItemWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivityTopStories.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ArticleTopStories newsData=results.get(position);
                intent.putExtra("result",newsData);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

            return results.size();
    }



}
