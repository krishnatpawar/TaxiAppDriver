package com.taxi.driver.taxiappdriver.activity;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.taxi.driver.taxiappdriver.R;
import com.taxi.driver.taxiappdriver.fragment.MyRidesFragment;
import com.taxi.driver.taxiappdriver.fragment.NavigationDrawerFragment;
import com.taxi.driver.taxiappdriver.fragment.NewRidesFragment;
import com.taxi.driver.taxiappdriver.fragment.ProfileFragment;
import com.taxi.driver.taxiappdriver.fragment.TransactionsFragment;
import com.taxi.driver.taxiappdriver.utils.Preferences;

public class MainActivity extends AbstractTaxiDriverActivity
        implements NavigationDrawerFragment.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private CharSequence mTitle;
    private Toolbar mToolbar;
    private NavigationDrawerFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }


        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new MyRidesFragment();
                title = getString(R.string.title_myrides);
                break;
            case 1:
                fragment = new NewRidesFragment();
                title = getString(R.string.title_newrides);
                break;
            case 2:
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                break;
            case 3:
                fragment = new TransactionsFragment();
                title = getString(R.string.title_transactions);
                break;
            case 4:
                Preferences.setUserId(getApplicationContext(), "");
                finish();
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            mToolbar.setTitle(title);
            mToolbar.setTitleTextColor(Color.rgb(232, 116,32));
        }
    }


}
