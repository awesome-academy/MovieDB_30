package com.framgia.moviedb.screen.search;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.screen.movie_detail.MovieDetailActivity;

import java.util.List;

public class SearchActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SearchContract.View, ResultsAdapter.OnItemClickListener {
    private SearchContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTextNoResult;

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mPresenter = new SearchPresenter(this,
                MoviesRepository.getInstance(MovieRemoteDataSource.getInstance()));
        initView();
        initToolbar();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycle_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mTextNoResult = findViewById(R.id.text_no_result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
        searchView.requestFocus();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mPresenter.searchMovie(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
    }

    @Override
    public void showResults(List<Movie> movies) {
        mTextNoResult.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ResultsAdapter resultsAdapter = new ResultsAdapter(this, movies, this);
        mRecyclerView.setAdapter(resultsAdapter);
        resultsAdapter.notifyItemRangeChanged(0, movies.size());
    }

    @Override
    public void showNoResult() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mTextNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }
}
