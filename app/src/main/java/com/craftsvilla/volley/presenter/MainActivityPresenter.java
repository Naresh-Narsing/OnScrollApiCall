package com.craftsvilla.volley.presenter;

import android.content.Context;

import com.craftsvilla.volley.interactor.MainActivityInterActor;
import com.craftsvilla.volley.interfaces.MainActivityInterface;
import com.craftsvilla.volley.model.Movie;

import java.util.List;

/**
 * Created by naresh on 17/3/16.
 */
public class MainActivityPresenter implements MainActivityInterface {
    MainActivityInterActor mainActivityInterActor;
    MainActivityInterface mainActivityInterface;

    public MainActivityPresenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
        mainActivityInterActor = new MainActivityInterActor();
    }

    public void sendUrl(String url, int page_id, boolean firstLoad, Context context) {
        mainActivityInterActor.getJsonFromUrl(url, page_id, firstLoad, this);
    }

    @Override
    public void updatingAdapterViewsForFirstTime(List<Movie> movies) {
        mainActivityInterface.updatingAdapterViewsForFirstTime(movies);
    }

    @Override
    public void showProgressDialog() {
        mainActivityInterface.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        mainActivityInterface.hideProgressDialog();
    }

    @Override
    public void loadingMoreItems() {
        mainActivityInterface.loadingMoreItems();
    }

    @Override
    public void hideLoadingMore() {
        mainActivityInterface.hideLoadingMore();
    }

    @Override
    public void loadingMoreItemsInAdapter() {
        mainActivityInterface.loadingMoreItemsInAdapter();
    }
}
