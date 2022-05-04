package com.example.spendingmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private SQLHelper sqlHelper;

    public Category currentAccount;
    public String startDate = "2000-01-01", endDate= "2025-01-01";
    public Boolean isAllTime = true;
    public Boolean isAllAccount;

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


        sqlHelper = new SQLHelper(MainActivity.this);

        Category cardAccount = sqlHelper.getCategoryByName("Card");
        if (cardAccount == null) {
            cardAccount = new Category("Card", "ACCOUNT", Util.colorId[0], Util.colorCodeId[0], Util.iconId[0]);
            sqlHelper.addCategory(cardAccount);
        }
        currentAccount = cardAccount;
        isAllAccount = true;
        bindHeader(getWindow().getDecorView());

    }

    public void bindHeader(View view) {
        TextView txtHeaderAccount = view.findViewById(R.id.txtHeaderAccount);
        TextView txtHeaderAmount = view.findViewById(R.id.txtHeaderAmount);

        if(isAllAccount == null) return;

        String accountName="", amount = "";
        if (isAllAccount) {
            accountName = "All Account";
            amount = sqlHelper.getAccountAmount(true, 0) + "";
        } else {
            accountName = currentAccount.getName();
            amount = sqlHelper.getAccountAmount(false, currentAccount.getId()) + "";
        }
        txtHeaderAccount.setText(accountName);
        txtHeaderAmount.setText("₫ " + amount);


        TextView txtDate = view.findViewById(R.id.txtDate);

        if(txtDate == null) return;

        if(isAllTime) {
            txtDate.setText("All Time");
        }else{
            txtDate.setText(Util.getMonthByStartDate(startDate));
        }
    }

}