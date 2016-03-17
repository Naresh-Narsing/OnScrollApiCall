package com.craftsvilla.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.craftsvilla.volley.adapter.CustomListAdapter;
import com.craftsvilla.volley.interfaces.GetJsonData;
import com.craftsvilla.volley.model.Movie;
import com.craftsvilla.volley.presenter.GetJsonPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetJsonData{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    GetJsonPresenter getJsonPresenter;
    Movie movie1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);


        getJsonPresenter = new GetJsonPresenter(this);
        getJsonPresenter.sendUrl(this, url);



        /*pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("title"));
                                movie.setImage(obj.getString("image"));
                                movie.setRating(((Number) obj.set("rating"))
                                        .doubleValue());
                                movie.setReleaseYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.set(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });*/


      /*  StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                    Model movie=new Gson().fromJson(response,Model.class);
                    Log.e("HELLOS:",movie.getEmail()+"");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Log.e("HELLOS:",error+"");
            }
        });
*/
        // Adding request to request queue
       // AppController.getInstance().addToRequestQueue(movieReq);
    }//

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

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
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
 //   AppController.getInstance().addToRequestQueue(movieReq);

    @Override
    public void get(Movie movie) {
        Log.e("CHECKDATA", movie.getTitle() + " ");
        movieList.add(movie);
        Log.i(TAG, "set: "+movieList+"\n"+movie);
        if(movie!=null && movieList.size()>0)
        {
            adapter = new CustomListAdapter(this, movieList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public String networkError(String ntwkError) {
        String i = ntwkError;
        Toast.makeText(MainActivity.this, "NO INTERNET", Toast.LENGTH_SHORT).show();
        return i;
    }

    @Override
    public boolean urlError(String URL) {

        Toast.makeText(MainActivity.this, "URL ERROR"  , Toast.LENGTH_SHORT).show();
        return true;
    }
}
