package com.example.spaceshooter.Game;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    public ArrayList<String> nicks;
    public ArrayList<Integer> scores;

    public Leaderboard(){
        nicks = new ArrayList<>();
        scores = new ArrayList<>();
    }
/*
    public void Load(){
        try {
            FileInputStream fileInputStream = openFileInput("leaderboard.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line+ ", ");
                String[] split = line.split(" ");
                nicks.add(split[0]);
                scores.add(Integer.parseInt(split[1]));
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Save(Context context){

        try {
            String content = "";
            for (int i = 0; i <nicks.size(); i++){
                content += nicks.get(i) + " " + scores.get(i).toString() + "\n";
            }
            FileOutputStream outputStream;
            outputStream = openFileOutput("leaderboard.txt", context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
