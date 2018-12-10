package com.framgia.moviedb.screen.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> mMovies;
    private OnItemClickListener mListener;

    public ResultsAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        mContext = context;
        mMovies = movies;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (mMovies.get(position).getBackdropPath() != null){
            viewHolder.bindData(mMovies.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int SIZE_BACKDROP_IMAGE = 400;
        private Context mContext;
        private ImageView mImageBackdrop;
        private TextView mTextName;
        private TextView mTextVote;
        private TextView mTextReleaseDate;
        private OnItemClickListener mListener;
        private Movie mMovie;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mContext = itemView.getContext();
            mListener = listener;
            mTextName = itemView.findViewById(R.id.text_name);
            mTextReleaseDate = itemView.findViewById(R.id.text_release_date);
            mTextVote = itemView.findViewById(R.id.text_vote_average);
            mImageBackdrop = itemView.findViewById(R.id.image_search_backdrop);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(mMovie);
        }

        void bindData(Movie movie) {
            mMovie = movie;
            mTextName.setText(movie.getTitle());
            mTextVote.setText("IDBM: " + movie.getVoteAverage());
            mTextReleaseDate.setText(movie.getReleaseDate());
            String url = APIUtils.PRE_POSTER_URL + movie.getBackdropPath();
            Glide.with(mContext)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_icon)
                            .error(R.drawable.ic_error_icon)
                            .override(SIZE_BACKDROP_IMAGE))
                    .into(mImageBackdrop);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
