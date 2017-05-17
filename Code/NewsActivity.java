package com.example.sanketnawle.whatshappening;

import android.os.Bundle;
import android.provider.Browser;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_topstories) {

            //identifies the webview
            WebView webView1 = (WebView) findViewById(R.id.newsWebView);
            //loads the url
            webView1.loadUrl("http://news.google.com/news?cf=all&hl=en&ned=us&output=rss");

        } else */ if (id == R.id.nav_national) {


            //identifies the webview
            WebView webView2 = (WebView) findViewById(R.id.newsWebView);
            WebSettings webSettings = webView2.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView2.setInitialScale(140);
            //loads the url
            webView2.loadUrl("http://feeds.reuters.com/reuters/INtopNews");

        } else if (id == R.id.nav_international) {


            //identifies the webview
            WebView webView3 = (WebView) findViewById(R.id.newsWebView);
            webView3.setInitialScale(140);
            //loads the url
            webView3.loadUrl("http://feeds.reuters.com/reuters/INworldNews");

        } else if (id == R.id.nav_technology) {


            //identifies the webview
            WebView webView4 = (WebView) findViewById(R.id.newsWebView);
            webView4.setInitialScale(140);
            //loads the url
            webView4.loadUrl("http://feeds.reuters.com/reuters/INtechnologyNews");

        } else if (id == R.id.nav_sports) {


            //identifies the webview
            WebView webView = (WebView) findViewById(R.id.newsWebView);
            webView.setInitialScale(140);
            //loads the url
            webView.loadUrl("http://feeds.reuters.com/reuters/INsportsNews");

        } else if (id == R.id.nav_entertainment) {


            //identifies the webview
            WebView webView = (WebView) findViewById(R.id.newsWebView);
            webView.setInitialScale(140);
            //loads the url
            webView.loadUrl("http://feeds.reuters.com/reuters/INentertainmentNews");

        } else {
            if (id == R.id.nav_business) {


                //identifies the webview
                WebView webView = (WebView) findViewById(R.id.newsWebView);
                //loads the url
                webView.setInitialScale(140);
               /* webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.setScrollbarFadingEnabled(false); */

                webView.loadUrl("http://feeds.reuters.com/reuters/INtopNews");

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
