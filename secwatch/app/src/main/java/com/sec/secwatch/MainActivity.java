package com.sec.secwatch;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.sec.secwatch.fragment.FavoriteFragment;
import com.sec.secwatch.fragment.SearchableFragment;
import com.sec.secwatch.fragment.StaticsFragment;
import com.sec.secwatch.widgets.UserProfilePlaceHolder;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    Observer,
                    UserProfilePlaceHolder.UserProfileClickListener {

    public static final int SEC_WATCH_ACTIVITY_REQUEST_CODE = 1;
    public static final String SEC_WATCH_ACTIVITY_TAG_EXTRA_ID = "secwatch_user";
    public static final String SEC_WATCH_ACTIVITY_TAG_EXTRA_PIN = "secwatch_user_pin";

    private UserProfilePlaceHolder mUserProfilePlaceHolder = null;

    private Fragment mCurrentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initNavigationHeader(navigationView);
        navigationView.setCheckedItem(R.id.nav_statics);

        mCurrentFragment = new StaticsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_fragment_container, mCurrentFragment);
        fragmentTransaction.commit();

        mUserProfilePlaceHolder.setDataSet( App.registerCurrentUserObserver(this) );
    }

    private void showFragment(Fragment fragment) {
        mCurrentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initNavigationHeader(NavigationView container) {
        ViewGroup header = (ViewGroup) container.getHeaderView(0);
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(header, iconFont);

        mUserProfilePlaceHolder = (UserProfilePlaceHolder) header.findViewById(R.id.profile_place_holder);
        mUserProfilePlaceHolder.setUserProfileClickListener(this);
        View setting = header.findViewById(R.id.navigation_setting);
        setting.setOnClickListener(mProfileOnClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == SEC_WATCH_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK ) {
            String id = data.getStringExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID);
            String pin = data.getStringExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_PIN);

            App.setCurrentUserInfo(id, pin);
        }
    }

    private View.OnClickListener mProfileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), ProfileActivity.class);

            if(App.getCurrentUserInfo() == null ) {
                intent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, "");
                startActivityForResult(intent, SEC_WATCH_ACTIVITY_REQUEST_CODE);
            } else {
                String userID = App.getCurrentUserInfo().getId();
                intent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, userID);
                startActivityForResult(intent, SEC_WATCH_ACTIVITY_REQUEST_CODE);
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if( mCurrentFragment instanceof SearchableFragment ) {
                    ((SearchableFragment)mCurrentFragment).setSearchText(s);
                }

                Log.d(App.TAG, s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if( mCurrentFragment instanceof SearchableFragment ) {
                    ((SearchableFragment)mCurrentFragment).setSearchText(s);
                }

                Log.d(App.TAG, s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_statics) {
            // Handle the camera action
            showFragment(new StaticsFragment());
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_favorite) {
            showFragment(new FavoriteFragment());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if ( o instanceof UserObservable ) {
            if( arg instanceof UserInfoAllWrapper) {
                UserInfoAllWrapper data = (UserInfoAllWrapper) arg;
                mUserProfilePlaceHolder.setDataSet(data);
                Log.d(App.TAG , "notified : " + data.getId());
            }
        }
    }

    @Override
    public void onBlankProfileClick() {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), ProfileActivity.class);
        intent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, "");
        startActivityForResult(intent, SEC_WATCH_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onProfileClick() {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), ProfileActivity.class);

        String userID = App.getCurrentUserInfo().getId();
        intent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, userID);
        startActivityForResult(intent, SEC_WATCH_ACTIVITY_REQUEST_CODE);

    }
}
