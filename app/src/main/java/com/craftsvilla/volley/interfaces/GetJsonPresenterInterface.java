package com.craftsvilla.volley.interfaces;

import com.craftsvilla.volley.model.Movie;

/**
 * Created by naresh on 17/3/16.
 */

public interface GetJsonPresenterInterface {
    public void set(Movie movie);
    public String networkError(String ntwkError);
    public boolean urlError(String URL);
}
