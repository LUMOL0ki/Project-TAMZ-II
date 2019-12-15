package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.spaceshooter.Game.Leaderboard;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private UIManager UIManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();

        setContentView(R.layout.activity_leaderboard);

        List<String> list = new ArrayList<String>();
        int i = 1;
        try {
            FileInputStream fileInputStream = openFileInput("leaderboard.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line+ ", ");
                String[] split = line.split(" ");
                list.add(String.valueOf(i) + " | " + split[0] + "\t\t\t" + split[1].trim());
                line = reader.readLine();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView listview = findViewById(R.id.leaderBoardListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list );

        listview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
    }

    public void onBackClick(View view){
        finish();
    }
}
