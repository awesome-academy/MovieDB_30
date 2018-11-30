package com.framgia.moviedb.screen.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.screen.favorite.FavoriteActivity;
import com.framgia.moviedb.screen.more.MoreMovieActivity;
import com.framgia.moviedb.screen.search.SearchActivity;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View,
        MovieGroupAdapter.OnGroupItemClickListener, View.OnClickListener {
    private static final int INTERVAL_TIME = 200;
    private static final int ID_NAVIGATION_ICON = -1;
    private static final int SIZE_VIEW_FLIPPER = 500;

    private ViewFlipper mViewFlipper;
    private MovieGroupAdapter mGroupAdapter;
    private HomePresenter mPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        customizeActionBar();
        loadMovieGroup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                startActivity(FavoriteActivity.getIntent(this));
                break;
            case R.id.menu_search:
                startActivity(SearchActivity.getIntent(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovies(List<GroupMovie> groupMovies) {
        mGroupAdapter = new MovieGroupAdapter(this, groupMovies, this);
        mRecyclerView.setAdapter(mGroupAdapter);
        mGroupAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoviesError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, getString(R.string.error_show_image_home),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showViewFlipper(List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = APIUtils.PRE_POSTER_URL + movies.get(i).getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_icon)
                            .error(R.drawable.ic_error_icon)
                            .override(SIZE_VIEW_FLIPPER))
                    .into(imageView);
            mViewFlipper.addView(imageView);
        }
        mViewFlipper.setFlipInterval(INTERVAL_TIME);
        mViewFlipper.setAutoStart(true);
    }

    @Override
    public void showViewFlipperError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, getString(R.string.error_show_image_home),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(int groupId) {
        startActivity(MoreMovieActivity.getIntent(this, groupId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ID_NAVIGATION_ICON:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycle_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mViewFlipper = findViewById(R.id.view_flipper);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigation);
    }

    private void loadMovieGroup() {
        mPresenter = new HomePresenter(this,
                MoviesRepository.getInstance(MovieRemoteDataSource.getInstance()));
        mPresenter.loadMovies();
    }

    private void customizeActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_button);
        mToolbar.setNavigationOnClickListener(this);
    }
}
