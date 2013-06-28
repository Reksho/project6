package rezk.mprog.jiglife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;


/**
 * Created by 6331408 on 19-6-13.
 */
public class Highscores extends Activity {

    public static final int maxList = 10;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        ArrayList<String> scores = new ArrayList<String>(
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getStringSet("SAVEDATA", new HashSet<String>()));

        Intent intent = getIntent();
        String score = intent.getStringExtra("score");

        if (score != null) {
            /* Add to highscores list if list does not yet contain 10 entries */
            if (scores.size() <= maxList) {
                scores.add(score);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = prefs.edit();
                edit.putStringSet("SAVEDATA", new HashSet<String>(scores));
                edit.commit();
            }
            /* Add to highscores list if entry has a higher score than the other entries */
             else {
                int scorie = Integer.valueOf(score);
                for (int i=0; i < maxList; i++) {
                    String scoreLoop = scores.get(i);
                    int scoreNow = Integer.valueOf(scoreLoop);
                    if (scorie > scoreNow)
                        scores.set(i, scoreLoop);
                }
            }
        }

        /* Fill the view */
        Comparator comparator = Collections.reverseOrder();
        Collections.sort(scores, comparator);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);
    }
}