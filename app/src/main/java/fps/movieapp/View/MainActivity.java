package fps.movieapp.View;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fps.movieapp.R;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    Intent intent = new Intent(MainActivity.this, MovieList.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, MovieList.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Você está off-line", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
