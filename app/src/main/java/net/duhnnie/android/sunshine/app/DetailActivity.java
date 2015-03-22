package net.duhnnie.android.sunshine.app;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private String mForecastString = null;
        public static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();
        public static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent detailIntent = getActivity().getIntent();
            TextView detailText = (TextView) rootView.findViewById(R.id.detail_text);
            mForecastString = detailIntent.getStringExtra(Intent.EXTRA_TEXT);
            detailText.setText(mForecastString);
            return rootView;
        }

        private Intent getShareIntent() {
            CharSequence textToShare = mForecastString + " " + FORECAST_SHARE_HASHTAG;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setType("text/plain");
            //intent.putExtra("subject", "The weather bring it to you by Sunshine app!");
            intent.putExtra(Intent.EXTRA_TEXT, textToShare);
            return  intent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailfragment, menu);

            MenuItem shareMenuItem = menu.findItem(R.id.action_share);

            ShareActionProvider provider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);

            if (provider != null) {
                provider.setShareIntent(getShareIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }
    }
}
