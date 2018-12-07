package com.framgia.moviedb.screen.movie_detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Cast;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
    private Context mContext;
    private List<Cast> mCasts;
    private OnActorClickListener mListener;

    public ActorAdapter(Context context, List<Cast> casts, OnActorClickListener listener) {
        mContext = context;
        mCasts = casts;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_actor, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCasts == null ? 0 : mCasts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextActorName;
        private OnActorClickListener mListener;
        private int mActorId;

        public ViewHolder(@NonNull View itemView, OnActorClickListener listener) {
            super(itemView);
            mListener = listener;
            mTextActorName = itemView.findViewById(R.id.text_actor_name);
            mTextActorName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onActorClick(mActorId);
        }

        private void bindData(Cast cast) {
            mActorId = cast.getId();
            mTextActorName.setText(cast.getName());
        }
    }

    interface OnActorClickListener {
        void onActorClick(int personId);
    }
}
