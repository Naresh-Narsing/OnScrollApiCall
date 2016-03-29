package com.craftsvilla.volley.interactor;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.craftsvilla.volley.app.AppController;
import com.craftsvilla.volley.interfaces.MainActivityInterface;
import com.craftsvilla.volley.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naresh on 17/3/16.
 */
public class MainActivityInterActor {

    private static final String TAG = "MainActivityInterActor";
    private List<Movie> movieList;

    public void getJsonFromUrl(final String URL, final int page_id,final boolean firstLoad,
                               final MainActivityInterface
                                       mainActivityInterface) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(firstLoad){
                        mainActivityInterface.showProgressDialog();
                    }else{
                        mainActivityInterface.loadingMoreItems();
                    }
                        JsonArrayRequest movieReq = new JsonArrayRequest(URL,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        if(firstLoad) {
                                            movieList = new ArrayList<Movie>();
                                            GsonBuilder gsonBuilder = new GsonBuilder();
                                            Gson gson = gsonBuilder.create();
                                            Type listType = new TypeToken<List<Movie>>() {}.getType();
                                            movieList = gson.fromJson(response.toString(), listType);
                                            mainActivityInterface.hideProgressDialog();
                                            mainActivityInterface.updatingAdapterViewsForFirstTime(movieList);
                                        }else {
                                            GsonBuilder gsonBuilder = new GsonBuilder();
                                            Gson gson = gsonBuilder.create();
                                            Type listType = new TypeToken<List<Movie>>() {}.getType();
                                            List<Movie> currentMovieList = gson.fromJson(response.toString(), listType);
                                            movieList.addAll(currentMovieList);
                                            mainActivityInterface.loadingMoreItemsInAdapter();
                                            mainActivityInterface.hideLoadingMore();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                            }
                        });
                        AppController.getInstance().addToRequestQueue(movieReq);
                    }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
