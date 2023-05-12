package com.kemalgeylani.catchimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.kemalgeylani.catchimage.databinding.ActivityScoreTableBinding;

import java.util.ArrayList;

public class ScoreTableActivity extends AppCompatActivity {

    private ActivityScoreTableBinding binding;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Score> scoreArrayList;
    ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreTableBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sqLiteDatabase = this.openOrCreateDatabase("Score",MODE_PRIVATE,null);

        scoreArrayList = new ArrayList<>();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(scoreArrayList);
        binding.recyclerView.setAdapter(scoreAdapter);

    }

    private void getData(){

        try {

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM score",null);

            int gamerNameIx = cursor.getColumnIndex("gamerName");
            int gamerScoreIx = cursor.getColumnIndex("gamerScore");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()){

                String gamerName = cursor.getString(gamerNameIx);
                int gamerScore = cursor.getInt(gamerScoreIx);
                int id = cursor.getInt(idIx);

                Score score = new Score(gamerName,gamerScore,id);
                scoreArrayList.add(score);

            }

            cursor.close();

            scoreAdapter.notifyDataSetChanged();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}