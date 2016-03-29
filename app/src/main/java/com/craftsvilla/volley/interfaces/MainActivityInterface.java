package com.craftsvilla.volley.interfaces;

import com.craftsvilla.volley.model.Movie;

import java.util.List;

/**
 * Created by naresh on 28/3/16.
 */
public interface MainActivityInterface {

    void updatingAdapterViewsForFirstTime(List<Movie> movies);

    void showProgressDialog();

    void hideProgressDialog();

    void loadingMoreItems();

    void hideLoadingMore();

    void loadingMoreItemsInAdapter();

}
