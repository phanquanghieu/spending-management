package com.example.spendingmanagement.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.HeaderAccountFragment;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.HomeCategoryAdapter;
import com.example.spendingmanagement.sql.SQLHelper;

public class CategoryFragment extends Fragment {

    private View view;
    private Button btnEditCategory;
    private LinearLayout btnExpenses, btnIncome;
    private RecyclerView rcvHomeCategory;

    private SQLHelper sqlHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        sqlHelper = new SQLHelper(getActivity());

        btnEditCategory = view.findViewById(R.id.btnEditCategory);
        btnExpenses = view.findViewById(R.id.btnExpenses);
        btnIncome = view.findViewById(R.id.btnIncome);
        rcvHomeCategory = view.findViewById(R.id.rcvHomeCategory);

        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListCategoryActivity.class));
            }
        });

        rcvHomeCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        renderCategory();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        Fragment headerAccountFragment = new HeaderAccountFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_header_account_container, headerAccountFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("category fragment resume");
    }

    private void renderCategory(){
        System.out.println("renderCategory");
        rcvHomeCategory.setAdapter(new HomeCategoryAdapter(getActivity(), sqlHelper.getCategoryByType("EXPENSES")));
    }
}