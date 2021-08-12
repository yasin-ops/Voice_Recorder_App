package com.muhammadyaseenfatimamazharsarfarz.voicerecorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
        setUpViewpager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setUpViewpager(ViewPager viewpager){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new RecorderFragment(),"Recorder");
        viewPagerAdapter.addFragment(new RecordingFragment(),"Recording");
        viewpager.setAdapter(viewPagerAdapter);
    }

}