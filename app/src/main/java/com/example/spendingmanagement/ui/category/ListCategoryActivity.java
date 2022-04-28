package com.example.spendingmanagement.ui.category;

import android.content.Intent;
import android.os.Bundle;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.databinding.ActivityListCategoryBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.spendingmanagement.ui.category.ui.main.SectionsPagerAdapter;

public class ListCategoryActivity extends AppCompatActivity {

    private ActivityListCategoryBinding binding;
    Button btnAddCategory;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTabs();

        btnAddCategory = findViewById(R.id.btn_add_category);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryType = tabs.getSelectedTabPosition() == 0 ? "EXPENSES" : "INCOME";

                Intent intent = new Intent(ListCategoryActivity.this, AddCategoryActivity.class);
                intent.putExtra("categoryType", categoryType);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTabs();
    }

    private void initTabs(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}