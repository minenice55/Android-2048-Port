package com.example.a2048game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    ArrayList<Player> players;
    Context mContext;
    LayoutInflater mInflator;

    public ScoreAdapter(ArrayList<Player> players, Context mContext) {
        this.players = players;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.from(parent.getContext()).inflate(R.layout.score_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        Player p = players.get(position);
        MiiStudioApi mii = new MiiStudioApi(p.getAvatarCode());

        Glide
            .with(mContext)
            .load(mii.getMiiUrl())
            .centerCrop()
            .into(holder.scoreRowAvatar);

        holder.scoreRowName.setText(p.getUsername());
        holder.scoreRowScore.setText(Integer.toString(p.getHighScore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView scoreRowName, scoreRowScore;
        ImageView scoreRowAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            scoreRowName = itemView.findViewById(R.id.scoreRowName);
            scoreRowScore = itemView.findViewById(R.id.scoreRowScore);
            scoreRowAvatar = itemView.findViewById(R.id.scoreRowAvatar);
        }
    }
}
