package com.example.spendingmanagement;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.spendingmanagement.sql.SQLHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spendingmanagement.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public String currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.navigation_account:
//                        navController.navigate(R.id.navigation_account);
//                        break;
//                    case R.id.navigation_category:
//                        navController.navigate(R.id.navigation_category);
//                        break;
//                    case R.id.navigation_transaction:
//                        navController.navigate(R.id.navigation_transaction);
//                        break;
//                }
//
//                btnAllAccount = findViewById(R.id.btn_all_account);
//                if (btnAllAccount != null)
//                    btnAllAccount.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(MainActivity.this, "ssssssss", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                return true;
//            }
//        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
//        btnAllAccount = findViewById(R.id.btn_all_account);
//        btnAllAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "ssssssss", Toast.LENGTH_LONG).show();
//            }
//        });

        SQLHelper sqlHelper = new SQLHelper(MainActivity.this);

    }

}