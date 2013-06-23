package mprog.rezk.jiglife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.preference.PreferenceManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    int movesAmount;
    private final int MILLIS_PER_SECOND = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nine);

        int[] myImageIds = {
                R.id.myimage1,
                R.id.myimage2,
                R.id.myimage3,
                R.id.myimage4,
                R.id.myimage5,
                R.id.myimage6,
                R.id.myimage7,
                R.id.myimage8,
                R.id.myimage9
        };

        int getCount = myImageIds.length;

        for(int i= 0; i < getCount; i++)
            findViewById(myImageIds[i]).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topmid).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        findViewById(R.id.midleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.midmid).setOnDragListener(new MyDragListener());
        findViewById(R.id.midright).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottommid).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String amountPieces = sharedPref.getString("listPref", "four");
        int intPieces = Integer.parseInt(amountPieces);


    }

    /* Settings */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                newGame();
                return true;
            case R.id.highscores:
                highscores();
                return true;
            case R.id.settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called when the user clicks the settings menu button */
    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /* Called when the user clicks the highscores menu button */
    public void highscores() {
        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    /* Called when the user clicks the new game menu button */
    public void newGame() {

    }



    /* Gameplay met touchgebeuren begint hier */
    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState(); // view die gedragt wordt
                    LinearLayout container = (LinearLayout) v; // nieuwe container
                    View viewOld = container.getChildAt(0);
                    ViewGroup owner = (ViewGroup) view.getParent(); // oude container van de view
                    owner.removeView(view); // view wordt verwijderd van oude container
                    container.removeView(viewOld); // view die verplaatst wordt verwijderen van container
                    container.addView(view); // gedragde view wordt toegevoegd aan nieuwe container

                    int childCount = owner.getChildCount();
                        if (childCount == 0) {
                        owner.addView(viewOld); // oude view plaatsen bij container van gedragde view
                        view.setVisibility(View.VISIBLE);
                        /*
                        movesAmount += 1;
                        String text = getString(R.string.amountMoves) + movesAmount;
                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(text);
                        checkWin();
                        */
                        }
                    else
                        view.setVisibility(View.VISIBLE);

                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    public void checkWin() {

    }
}
