package com.example.workout.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.workout.Fragment.HistoryFragment;
import com.example.workout.Fragment.LogDetailFragment;
import com.example.workout.Fragment.LogListFragment;
import com.example.workout.R;

import java.util.ArrayList;

public class TrainingHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_history);

        ViewPager viewPager = findViewById(R.id.viewPager);

        HistoryPagerAdapter pagerAdapter = new HistoryPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HistoryFragment());
        pagerAdapter.addFragment(new LogListFragment());
        pagerAdapter.addFragment(new LogDetailFragment());
        viewPager.setAdapter(pagerAdapter);
    }

    class HistoryPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public HistoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}