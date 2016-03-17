package com.craftsvilla.volley.interfaces;

import com.craftsvilla.volley.model.Movie;

/**
 * Created by venkatesh on 1/13/16.
 */
public interface GetJsonData {
   public void get(Movie movie);
   public String networkError(String ntwkError);
   public boolean urlError(String URL);
}
