package com.framgia.moviedb.screen.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.screen.favorite.FavoriteActivity;
import com.framgia.moviedb.screen.more.MoreMovieActivity;
import com.framgia.moviedb.screen.movie_detail.MovieDetailActivity;
import com.framgia.moviedb.screen.search.SearchActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements HomeContract.View,
        MovieGroupAdapter.OnGroupItemClickListener, View.OnClickListener, ImagePagerAdapter.OnPagerClickListener {
    private static final int PERIOD_TIME = 2000;
    private static final int ID_NAVIGATION_ICON = -1;
    private static final int DELAY_TIME = 2000;

    private MovieGroupAdapter mGroupAdapter;
    private HomePresenter mPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private ImagePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

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
    public void showSliderPager(List<Movie> movies) {
        mPagerAdapter = new ImagePagerAdapter(this, movies, this);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();
        autoSlide(movies.size());
    }


    @Override
    public void showSliderPagerError(Exception e) {
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
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager, true);
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

    private void autoSlide(final int size) {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int currentPage = 0;
                mViewPager.setCurrentItem((currentPage++) % size, true);
            }
        };
        Timer swipeTimer = new Timer();


        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    @Override
    public void onPagerClick(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }
}
