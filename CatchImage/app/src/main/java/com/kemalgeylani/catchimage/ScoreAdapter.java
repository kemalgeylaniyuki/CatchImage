package com.kemalgeylani.catchimage;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kemalgeylani.catchimage.databinding.RecyclerviewScoreItemBinding;
import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {

    ArrayList<Score> scoreArrayList;

    public ScoreAdapter(ArrayList<Score> scoreArrayList){
        this.scoreArrayList = scoreArrayList;
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewScoreItemBinding recyclerviewScoreItemBinding = RecyclerviewScoreItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ScoreHolder(recyclerviewScoreItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {

        holder.binding.recyclerViewGamerText.setText(scoreArrayList.get(position).gamerName + " : ");
        holder.binding.recyclerViewScoreText.setText(String.valueOf(scoreArrayList.get(position).gamerScore));

    }

    @Override
    public int getItemCount() {
        return scoreArrayList.size();
    }

    public class ScoreHolder extends RecyclerView.ViewHolder{

        private RecyclerviewScoreItemBinding binding;

        public ScoreHolder(RecyclerviewScoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
