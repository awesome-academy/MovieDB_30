package com.framgia.moviedb.screen.home;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter implements View.OnClickListener {
    private static final int SIZE_VIEW_FLIPPER = 500;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnPagerClickListener mListener;
    private List<Movie> mMovies;
    private Movie mMovie;

    public ImagePagerAdapter(Context context, List<Movie> movies, OnPagerClickListener listener) {
        mContext = context;
        mListener = listener;
        mInflater = LayoutInflater.from(context);
        mMovies = movies;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = mInflater.inflate(R.layout.item_pager, container, false);
        assert imageLayout != null;
        mMovie = mMovies.get(position);
        ImageView imageView = imageLayout.findViewById(R.id.image_pager);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(this);
        String url = APIUtils.PRE_POSTER_URL + mMovies.get(position).getBackdropPath();
        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading_icon)
                        .error(R.drawable.ic_error_icon)
                        .override(SIZE_VIEW_FLIPPER))
                .into(imageView);
        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public int getCount() {
        return mMovies != null ? mMovies.size() : 0;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void onClick(View v) {
        mListener.onPagerClick(mMovie);
    }

    interface OnPagerClickListener {
        void onPagerClick(Movie movie);
    }
}
