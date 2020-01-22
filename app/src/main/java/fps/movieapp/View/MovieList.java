package fps.movieapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import fps.movieapp.Controller.Adapter;
import fps.movieapp.Model.Item;
import fps.movieapp.R;

public class MovieList extends AppCompatActivity implements Adapter.OnItemClickListener {

    public static final String EXTRA_VOTE = "vote_average";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_URL = "poster_url";
    public static final String EXTRA_GENRE = "genres";
    public static final String EXTRA_DATE = "release_date";


    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Item> mList;
    private RequestQueue mRequestQueue;

    RelativeLayout pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);

        pb = findViewById(R.id.progress);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON(){

        pb.setVisibility(View.VISIBLE);

        String url="https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for(int i=0; i<response.length(); i++) {

                                JSONObject movie = response.getJSONObject(i);

                                Integer id = movie.getInt("id");
                                String vote = movie.getString("vote_average");
                                String name = movie.getString("title");
                                String imageUrl = movie.getString("poster_url");
                                JSONArray genre = movie.getJSONArray("genres");
                                String date = movie.getString("release_date");

                                mList.add(new Item(id, vote, name, imageUrl, genre, date));
                            }

                            mAdapter = new Adapter(MovieList.this, mList);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(MovieList.this);

                            pb.setVisibility(View.INVISIBLE);

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pb.setVisibility(View.INVISIBLE);
            }

        }){
            //Armazenamento Cache
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try{
                    Cache.Entry  cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if(cacheEntry == null){
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000;
                    final long cacheExpired = 24 * 60 * 60 * 1000;
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if(headerValue != null){
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);

                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null){
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString), cacheEntry);

                }catch (UnsupportedEncodingException | JSONException e){
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        mRequestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {

        try {
            Intent detailIntent = new Intent(MovieList.this, Detail.class);
            Item clickedItem = mList.get(position);

            detailIntent.putExtra(EXTRA_VOTE, clickedItem.getVote_average());
            detailIntent.putExtra(EXTRA_URL, clickedItem.getPoster_url());
            detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
            detailIntent.putExtra(EXTRA_GENRE, clickedItem.getGenres().toString());
            detailIntent.putExtra(EXTRA_DATE, clickedItem.getRelease_date());

            startActivity(detailIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
