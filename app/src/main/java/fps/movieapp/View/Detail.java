package fps.movieapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fps.movieapp.R;

import static fps.movieapp.View.MovieList.EXTRA_DATE;
import static fps.movieapp.View.MovieList.EXTRA_GENRE;
import static fps.movieapp.View.MovieList.EXTRA_TITLE;
import static fps.movieapp.View.MovieList.EXTRA_URL;
import static fps.movieapp.View.MovieList.EXTRA_VOTE;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            Intent intent = getIntent();

            String vote = intent.getStringExtra(EXTRA_VOTE);
            String posterUrl = intent.getStringExtra(EXTRA_URL);
            String title = intent.getStringExtra(EXTRA_TITLE);

            String genre = intent.getStringExtra(EXTRA_GENRE);

            String date = intent.getStringExtra(EXTRA_DATE);

            ImageView imageView = findViewById(R.id.img_details);
            TextView textView = findViewById(R.id.title_details);
            TextView textView1 = findViewById(R.id.vote_details);
            TextView textView2 = findViewById(R.id.genre_details);
            TextView textView3 = findViewById(R.id.date_details);

            Picasso.get().load(posterUrl).fit().centerInside().into(imageView);

            textView.setText(title);
            textView1.setText(vote);
            textView2.setText(genre);
            textView3.setText(date);

        }catch (Exception e){
            e.printStackTrace();

        }

    }


}
