package rezk.mprog.jiglife;

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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;

public class MainActivity extends Activity {

    int movesAmount;
    private final int MILLIS_PER_SECOND = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);


        LinearLayout linearMaster = (LinearLayout) findViewById(R.id.linearMaster);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        int[] myImageIds = new int[]{
                R.drawable.jig1,
                R.drawable.jig2,
                R.drawable.jig3,
                R.drawable.jig4,
                R.drawable.jig5,
                R.drawable.jig6,
                R.drawable.jig7,
                R.drawable.jig8,
                R.drawable.jig9
        };

        int k = 0;

        for(int i = 0; i < 3; i++) {
            LinearLayout linearChild = new LinearLayout(this);
            linearChild.setOrientation(LinearLayout.HORIZONTAL);
            linearChild.setLayoutParams(params);
            linearMaster.addView(linearChild);

            for(int j = 0; j < 3; j++) {
                LinearLayout linear = new LinearLayout(this);
                linear.setLayoutParams(params2);
                linear.setBackground(getResources().getDrawable(R.drawable.shape));
                linear.setOnDragListener(new MyDragListener());
                linearChild.addView(linear);

                ImageView view = new ImageView(this);
                view.setLayoutParams(params);
                view.setId(k);
                view.setImageDrawable(getResources().getDrawable(myImageIds[k]));
                view.setOnTouchListener(new MyTouchListener());
                linear.addView(view);

                k += 1;
            }
        }
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
