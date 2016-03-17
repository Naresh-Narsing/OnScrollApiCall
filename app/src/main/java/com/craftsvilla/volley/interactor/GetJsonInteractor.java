package com.craftsvilla.volley.interactor;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.craftsvilla.volley.adapter.CustomListAdapter;
import com.craftsvilla.volley.app.AppController;
import com.craftsvilla.volley.interfaces.GetJsonPresenterInterface;
import com.craftsvilla.volley.model.Movie;
import com.craftsvilla.volley.presenter.GetJsonPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naresh on 17/3/16.
 */
public class GetJsonInteractor {

    private static final String TAG = "GetJsonInteractor";
    private List<Movie> movieList = new ArrayList<Movie>();


    public void getJsonFromUrl(final String URL,final GetJsonPresenterInterface
                                                        getJsonPresenterInterface) {
        JsonArrayRequest movieReq = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("title"));
                                movie.setImage(obj.getString("image"));
                                movie.setRating(((Number) obj.get("rating"))
                                        .doubleValue());
                                movie.setReleaseYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
                                movieList.add(movie);
                                getJsonPresenterInterface.set(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isJsonValid(URL,getJsonPresenterInterface) == false){
                    Log.i(TAG, "onErrorResponse: " + "INVALID URL");
                    getJsonPresenterInterface.urlError("URL ERROR");
                }else {
                    String i = getJsonPresenterInterface.networkError("No Internet Access");
                    Log.i(TAG, "onErrorResponse: " + i);
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }

            }
        });
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    public boolean isJsonValid(String url, final GetJsonPresenterInterface getJsonPresenterInterface) {
        try {
            new JSONObject(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            new JSONArray(url);
        } catch (JSONException e) {
            return false;
        }
        getJsonPresenterInterface.urlError("INVALID URL");
        return true;
    }
}
