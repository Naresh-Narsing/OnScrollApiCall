package com.craftsvilla.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.craftsvilla.volley.adapter.CustomGridAdapter;
import com.craftsvilla.volley.interfaces.MainActivityInterface;
import com.craftsvilla.volley.model.Movie;
import com.craftsvilla.volley.presenter.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityInterface,AbsListView.OnScrollListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url = "http://api.androidhive.info/json/movies.json";
    MainActivityPresenter mainActivityPresenter;
    private GridView gridView;
    private CustomGridAdapter adapter;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private boolean isLoadingMoreItems = false;
    private int page_id = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        gridView = (GridView) findViewById(R.id.grid);

        if (gridView != null) {
            gridView.setOnScrollListener(this);
        }

        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.sendUrl(url,page_id,true,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("SCROLLING DOWN","TRUE");
        Log.d(TAG, "onScroll() called with: " + " firstVisibleItem = [" + firstVisibleItem + "], visibleItemCount = [" + visibleItemCount + "], totalItemCount = [" + totalItemCount + "]");
        if (totalItemCount >0){
            if (!isLoadingMoreItems) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (lastInScreen >= totalItemCount) {
                    isLoadingMoreItems = true;
                    mainActivityPresenter.sendUrl(url, page_id, false, this);
                    page_id++;
                }
            }
        }
    }

    @Override
    public void updatingAdapterViewsForFirstTime(final List<Movie> movies) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (movies.size() > 0) {
                        adapter = new CustomGridAdapter(MainActivity.this, movies);
                        gridView.setAdapter(adapter);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showProgressDialog() {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgressDialog() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadingMoreItems() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoadingMore() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadingMoreItemsInAdapter() {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    isLoadingMoreItems = false;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
