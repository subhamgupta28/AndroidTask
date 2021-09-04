package com.subhamgupta.androidtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.subhamgupta.androidtask.R;
import com.subhamgupta.androidtask.adapters.ViewPagerAdapter;
import com.subhamgupta.androidtask.fragments.AudioRecorder;
import com.subhamgupta.androidtask.fragments.SavedRecordings;

public class MainActivity extends AppCompatActivity implements AudioRecorder.goToFiles{
    ViewPager viewPager2;
    TabLayout tabLayout;
    AudioRecorder audioRecorder;
    SavedRecordings savedRecordings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        audioRecorder = new AudioRecorder();
        savedRecordings = new SavedRecordings();
        tabLayout.setupWithViewPager(viewPager2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragments(audioRecorder, "Record Audio");
        viewPagerAdapter.addFragments(savedRecordings, "History");


        viewPager2.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onGo(String msg) {
        viewPager2.setCurrentItem(2, true);
    }
}