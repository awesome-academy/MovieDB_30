package com.framgia.moviedb.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.screen.movie_detail.MovieDetailActivity;

import java.util.List;

public class MovieGroupAdapter
        extends RecyclerView.Adapter<MovieGroupAdapter.ViewHolder> {
    private final Context mContext;
    private List<GroupMovie> mGroupMovies;
    private OnGroupItemClickListener mListener;

    public MovieGroupAdapter(Context context, List<GroupMovie> list, OnGroupItemClickListener listener) {
        if (list != null) {
            mGroupMovies = list;
        }
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public MovieGroupAdapter.ViewHolder onCreateViewHolder(@NonNull
                                                                   ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_group_movie, viewGroup, false);
        return new MovieGroupAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull
                                         MovieGroupAdapter.ViewHolder movieGroupViewHolder, int position) {
        movieGroupViewHolder.bindData(mGroupMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroupMovies != null ? mGroupMovies.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, MovieAdapter.OnItemClickListener {
        private final RecyclerView mRecyclerView;
        private TextView mTextGroupLabel;
        private MovieAdapter mMovieAdapter;
        private OnGroupItemClickListener mOnGroupItemClick;
        private Context mContext;
        private int mGroupId;

        public ViewHolder(@NonNull View itemView, OnGroupItemClickListener listener) {
            super(itemView);
            mContext = itemView.getContext();
            mOnGroupItemClick = listener;
            mTextGroupLabel = itemView.findViewById(R.id.text_label_group);
            mRecyclerView = itemView.findViewById(R.id.recycle_movie);
            mRecyclerView.setLayoutManager(
                    new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setHasFixedSize(true);
            mMovieAdapter = new MovieAdapter(mContext, this);
            mRecyclerView.setAdapter(mMovieAdapter);
            mTextGroupLabel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_label_group:
                    mOnGroupItemClick.onItemClick(mGroupId);
                    break;
            }
        }

        @Override
        public void onItemClick(Movie movie) {
            mContext.startActivity(MovieDetailActivity.getIntent(mContext, movie));
        }

        public void bindData(GroupMovie groupMovie) {
            mTextGroupLabel.setText(groupMovie.getTitle());
            mMovieAdapter.setMovieData(groupMovie.getMovies());
            mGroupId = groupMovie.getId();
        }
    }

    interface OnGroupItemClickListener {
        void onItemClick(int groupId);
    }

}
