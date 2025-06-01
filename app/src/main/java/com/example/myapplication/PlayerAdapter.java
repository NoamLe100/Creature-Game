package com.example.myapplication;
import  android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Playrs> playerList;

    public PlayerAdapter(List<Playrs> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playrs_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Playrs player = playerList.get(position);
        holder.nameTextView.setText(player.getName());
        holder.levelTextView.setText("" + player.getLevel());
        holder.iconImageView.setImageResource(holder.nameTextView.getResources().getIdentifier(player.getIcon(), "drawable", holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView iconImageView;
        TextView levelTextView;


        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.Name);
            iconImageView = itemView.findViewById(R.id.Icon);
            levelTextView= itemView.findViewById(R.id.level);
        }
    }
}