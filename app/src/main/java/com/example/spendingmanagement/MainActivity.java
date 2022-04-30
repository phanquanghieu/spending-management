package com.example.spendingmanagement;

import android.os.Bundle;

import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.spendingmanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public Category currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        SQLHelper sqlHelper = new SQLHelper(MainActivity.this);

        Category cardAccount = sqlHelper.getCategoryByName("Card");
        if(cardAccount == null) {
            cardAccount = new Category("Card", "ACCOUNT", Util.colorId[0], Util.colorCodeId[0], Util.iconId[0]);
            sqlHelper.addCategory(cardAccount);
        }
        currentAccount = cardAccount;

    }

}