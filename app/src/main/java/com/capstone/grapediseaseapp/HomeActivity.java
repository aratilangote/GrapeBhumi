package com.capstone.grapediseaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawer;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setBackgroundColor(getColor(R.color.teal_700));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getColor(R.color.teal_700));

        drawer = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navView = findViewById(R.id.nav_drawer);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        temp = new ContainerHome();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_profile:
                        temp = new FragmentProfile();
                        bottomNavigationView.setVisibility(View.GONE);
                        break;
                    case R.id.nav_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "http://javatpoint.com");
                        intent.setType("text/plain");
                        startActivity(Intent.createChooser(intent,"Share via"));
                        break;
                    case R.id.nav_privacypolicy:
                        temp = new FragmentPrivacyPolicy();
                        bottomNavigationView.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case R.id.nav_termsconditions:
                        temp = new FragmentTermsConditions();
                        bottomNavigationView.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case R.id.nav_aboutUs:
                        temp = new FragmentAboutus();
                        bottomNavigationView.setVisibility(View.GONE);
                        break;
                    case R.id.nav_Logout:
                        Toast.makeText(getApplicationContext(), "Logout Successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, temp).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment temp2;
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        temp2 = new ContainerHome();
                        navView.setVisibility(View.GONE);
                        break;
                    case R.id.nav_crop_Care:
                        temp2 = new BottomFragmentCropCare();
                        navView.setVisibility(View.GONE);
                        break;
                    case R.id.nav_video:
                        temp2 = new BottomFragmentVideos();
                        navView.setVisibility(View.GONE);
                        break;
                    case R.id.nav_weather:
                        temp2 = new BottomFragmentWeather();
                        navView.setVisibility(View.GONE);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,temp2).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                super.onResume();
                this.recreate();
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Logout Successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}