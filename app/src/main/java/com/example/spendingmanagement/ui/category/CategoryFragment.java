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
import com.example.spendingmanagement.sql.SQLHelper;

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

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        renderCategory();
    }

    public void renderCategory() {
        System.out.println("renderCategory");
        rcvHomeCategory.setAdapter(new HomeCategoryAdapter(getActivity(), sqlHelper.getCategoryWithAmountByType(currentCategoryType), this));
        txtExpensesAmount.setText("₫ " + sqlHelper.getCategoryAmountOfType("EXPENSES"));
        txtIncomeAmount.setText("₫ " + sqlHelper.getCategoryAmountOfType("INCOME"));
        mainActivity.bindHeader(view);
    }
}