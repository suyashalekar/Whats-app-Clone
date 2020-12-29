package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Fragments.FragmentAdaptor;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
      FirebaseUser firebaseUser ;
      FirebaseAuth firebaseAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.setting:
                startActivity(new Intent(MainActivity.this,Setting_Activity.class));

                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                return true;
            case  R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Login_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;

            default:
                return true;
        }

    }
    /**
     *On Create
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.toolbar);
        // setiSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Whats app");
/**
 *TAB Layout ,View Pager
 */
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentAdaptor(getSupportFragmentManager()));

        tabLayout =findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
          startActivity(new Intent(MainActivity.this,Login_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
           // startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }
}