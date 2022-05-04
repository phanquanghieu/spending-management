package com.example.spendingmanagement.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.HomeCategoryAdapter;
import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;
    private Button btnEditCategory;
    private LinearLayout btnExpenses, btnIncome;
    private TextView txtExpensesAmount, txtIncomeAmount;
    private RecyclerView rcvHomeCategory;
    public String currentCategoryType;

    private SQLHelper sqlHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        sqlHelper = new SQLHelper(getActivity());
        mainActivity = (MainActivity) getActivity();
        mainActivity.bindHeader(view);

        btnEditCategory = view.findViewById(R.id.btnEditCategory);
        btnExpenses = view.findViewById(R.id.btnExpenses);
        btnIncome = view.findViewById(R.id.btnIncome);
        txtExpensesAmount = view.findViewById(R.id.txtExpensesAmount);
        txtIncomeAmount = view.findViewById(R.id.txtIncomeAmount);
        rcvHomeCategory = view.findViewById(R.id.rcvHomeCategory);

        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListCategoryActivity.class));
            }
        });

        rcvHomeCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        currentCategoryType = "EXPENSES";
        renderCategory();

        btnExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategoryType = "EXPENSES";
                renderCategory();
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategoryType = "INCOME";
                renderCategory();
            }
        });

        bindDate();

        return view;
    }

    private void  bindDate(){
        TextView txtDate = view.findViewById(R.id.txtDate);
        Button btnDateLeft = view.findViewById(R.id.btnDateLeft);
        Button btnDateRight = view.findViewById(R.id.btnDateRight);

        if(mainActivity.isAllTime) {
            txtDate.setText("All Time");
        }else{
            txtDate.setText(Util.getMonthByStartDate(mainActivity.startDate));
        }

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.isAllTime = true;
                mainActivity.startDate = "2000-01-01";
                mainActivity.endDate = "2025-01-01";
                renderCategory();
            }
        });

        btnDateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = "";

                if(mainActivity.isAllTime){
                    startDate = Util.getStartDateByDateNow();
                }else{
                    startDate = Util.getStartDateByStartDate(mainActivity.startDate, -1);
                }

                mainActivity.isAllTime = false;
                mainActivity.startDate = startDate;
                mainActivity.endDate = Util.getEndDateByStartDate(startDate);
                renderCategory();
            }
        });

        btnDateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = "";

                if(mainActivity.isAllTime){
                    startDate = Util.getStartDateByDateNow();
                }else{
                    startDate = Util.getStartDateByStartDate(mainActivity.startDate, 1);
                }

                mainActivity.isAllTime = false;
                mainActivity.startDate = startDate;
                mainActivity.endDate = Util.getEndDateByStartDate(startDate);
                renderCategory();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        renderCategory();
    }

    public void renderCategory() {
        System.out.println("renderCategory");
        ArrayList<Category> listCategory = sqlHelper.getCategoryWithAmountByType(currentCategoryType, mainActivity.startDate, mainActivity.endDate);
        rcvHomeCategory.setAdapter(new HomeCategoryAdapter(getActivity(), listCategory, this));
        txtExpensesAmount.setText("₫ " + sqlHelper.getCategoryAmountOfType("EXPENSES", mainActivity.startDate, mainActivity.endDate));
        txtIncomeAmount.setText("₫ " + sqlHelper.getCategoryAmountOfType("INCOME", mainActivity.startDate, mainActivity.endDate));
        mainActivity.bindHeader(view);
    }
}