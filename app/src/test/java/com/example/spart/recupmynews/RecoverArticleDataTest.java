package com.example.spart.recupmynews;




import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchArticles;
import com.example.spart.recupmynews.MyBusinessAPI.ArticleBusiness;
import com.example.spart.recupmynews.MyMostPopularAPI.ArticleMostPopular;
import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RecoverArticleDataTest {

    private ArrayList<ArticleTopStories>articlesTS;
    private ArrayList<ArticleBusiness> articlesBU;
    private ArrayList<ArticleMostPopular>articlesMP;
    private ArrayList<ArticleSearchArticles> articlesSC;

    @Before
    public void ArticleTopStories() {
        articlesTS = new ArrayList<>();
        ArticleTopStories articleTopStories = new ArticleTopStories();
        articleTopStories.setTitle( "TopStories Title" );
        articleTopStories.setSection( "TopStories" );
        articleTopStories.setUrl( "http://www.articlesTS.com" );
        articleTopStories.setUpdatedDate( "2019-01-01T00:00:00W" );

        articlesTS.add( articleTopStories );
    }
    @Before
    public void ArticleBusiness() {
        articlesBU = new ArrayList<>();
        ArticleBusiness articleBusiness = new ArticleBusiness();
        articleBusiness.setTitle( "Business Title" );
        articleBusiness.setSection( "Business" );
        articleBusiness.setUrl( "http://www.articlesBU.com" );
        articleBusiness.setPublishedDate( "2019-01-01T00:00:00W" );

        articlesBU.add( articleBusiness );
    }


    @Before
    public void ArticleMostPopular() {
        articlesMP = new ArrayList<>();
        ArticleMostPopular articleMostPopular = new ArticleMostPopular();
        articleMostPopular.setTitle( "MostPopular Title" );
        articleMostPopular.setSection( "MostPopular" );
        articleMostPopular.setUrl( "http://www.articlesMP.com" );
        articleMostPopular.setPublishedDate( "2019-01-01T00:00:00W" );

        articlesMP.add( articleMostPopular );

    }
    @Before
    public void ArticleSearch(){

        articlesSC = new ArrayList<>();
        ArticleSearchArticles articleSearch = new ArticleSearchArticles();
        articleSearch.setSnippet("Search Title");
        articleSearch.setSectionName("Search");
        articleSearch.setWebUrl("http://www.articlesSearch.com");
        articleSearch.setPubDate("2019-01-01T00:00:00W");

        articlesSC.add(articleSearch);
    }
    @Test
    public void getTitle() {
        assertEquals("TopStories Title", articlesTS.get().getTitle());
        assertEquals("MostPopular Title", articlesMP.get(0).getTitle());
        assertEquals("Search Title", articlesSC.get(0).getSnippet());
        assertEquals( "Business Title",articlesBU.get( 0 ).getTitle() );
    }

    @Test
    public void getSection() {
        assertEquals("TopStories", articlesTS.get(0).getSection());
        assertEquals("MostPopular", articlesMP.get(0).getSection());
        assertEquals("Search", articlesSC.get(0).getSectionName());
        assertEquals("Business", articlesBU.get(0).getSection());
    }

    @Test
    public void getWebUrl() {
        assertEquals("http://www.articlesTS.com", articlesTS.get(0).getUrl());
        assertEquals("http://www.articlesMP.com", articlesMP.get(0).getUrl());
        assertEquals("http://www.articlesSearch.com", articlesSC.get(0).getWebUrl());
        assertEquals("http://www.articlesBU.com", articlesBU.get(0).getUrl());
    }

    @Test
    public void getDate() {
        assertEquals("2019-01-01T00:00:00W", articlesTS.get(0).getUpdatedDate());
        assertEquals("2019-01-01T00:00:00W", articlesMP.get(0).getPublishedDate());
        assertEquals("2019-01-01T00:00:00W", articlesSC.get(0).getPubDate());
        assertEquals("2019-01-01T00:00:00W", articlesBU.get(0).getPublishedDate());
    }
}