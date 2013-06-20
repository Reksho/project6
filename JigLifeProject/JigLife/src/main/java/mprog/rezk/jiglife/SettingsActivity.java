package mprog.rezk.jiglife;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by 6331408 on 19-6-13.
 */
public class SettingsActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings);
    }
}