package com.craftsvilla.volley.presenter;

import android.content.Context;

import com.craftsvilla.volley.interactor.GetJsonInteractor;
import com.craftsvilla.volley.interfaces.GetJsonData;
import com.craftsvilla.volley.interfaces.GetJsonPresenterInterface;
import com.craftsvilla.volley.model.Movie;

/**
 * Created by naresh on 17/3/16.
 */
public class GetJsonPresenter implements GetJsonPresenterInterface
{
    GetJsonInteractor getJsonInteractor;
    GetJsonData getJsonData;

    public GetJsonPresenter(GetJsonData getJsonData) {
         this.getJsonData=getJsonData;
         getJsonInteractor =  new GetJsonInteractor();

    }

    public void sendUrl(Context context,String url){
        getJsonInteractor.isJsonValid(url,this);
        getJsonInteractor.getJsonFromUrl(url, this);
    }


    @Override
    public void set(Movie movie) {
        getJsonData.get(movie);
    }

    @Override
    public String networkError(String ntwkError) {
       String i =  getJsonData.networkError(ntwkError);
        return i;
    }

    @Override
    public boolean urlError(String URL) {
        getJsonData.urlError(URL);
        return true;
    }

}
