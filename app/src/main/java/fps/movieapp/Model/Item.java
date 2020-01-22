package fps.movieapp.Model;

import org.json.JSONArray;

public class Item {

    private Integer id;
    private String vote_average;
    private String title;
    private String poster_url;
    private JSONArray genres;
    private String release_date;

    public Item(Integer id, String vote_average, String title, String poster_url, JSONArray genres, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_url = poster_url;
        this.genres = genres;
        this.release_date = release_date;
    }

    public Integer getId() {
        return id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public JSONArray getGenres() {
        return genres;
    }

    public String getRelease_date() {
        return release_date;
    }
}
