package edu.uw.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String TAG = "MainActivity";
    private boolean dualpane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dualpane = false;
        View dualPaneView = findViewById(R.id.container_right);
        if (dualPaneView != null && dualPaneView.getVisibility() == View.VISIBLE) {
            dualpane = true;
        }

        MoviesFragment fragment = (MoviesFragment)getSupportFragmentManager().findFragmentByTag("MoviesFragment");
        if(fragment == null) {
            fragment = new MoviesFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "MoviesFragment");
        ft.commit();
    }


    //respond to search button clicking
    public void handleSearchClick(View v){
        Log.v(TAG, "Button handled");

        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        MoviesFragment fragment = (MoviesFragment)getSupportFragmentManager().findFragmentByTag("MoviesFragment");

        if(fragment == null){
            fragment = new MoviesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, null)
                    .commit();
        }

        fragment.fetchData(searchTerm);
    }

    @Override
    public void movieSelected(Movie movie) {


        Bundle bundle = new Bundle();
        bundle.putString("title", movie.toString());
        bundle.putString("imdb", movie.imdbId);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        int fragID = R.id.container;
        if (dualpane) {
            fragID = R.id.container_right;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(fragID, detailFragment, null)
                .addToBackStack(null)
                .commit();

    }
}
