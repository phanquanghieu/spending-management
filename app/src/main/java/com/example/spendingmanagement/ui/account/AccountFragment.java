package com.example.spendingmanagement.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.ListAccountAdapter;
import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;
import com.example.spendingmanagement.ui.category.AddCategoryActivity;
import com.example.spendingmanagement.ui.category.ListCategoryActivity;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    private SQLHelper sqlHelper;
    private TextView txtTotalAmount;
    private Button btnAddAccount;
    private RecyclerView rcvListAccount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);
        sqlHelper = new SQLHelper(getActivity());
        mainActivity = (MainActivity) getActivity();
        mainActivity.bindHeader(view);

        rcvListAccount = view.findViewById(R.id.rcvListAccount);
        rcvListAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
        renderAccount();

        txtTotalAmount = view.findViewById(R.id.txtTotalAmount);
        int totalAmount = sqlHelper.getAccountAmount(true, 0);
        txtTotalAmount.setText("₫ " + Util.convertMoney(totalAmount));
        if(totalAmount < 0){
            txtTotalAmount.setTextColor(getResources().getColor(R.color.red_500));
        }else{
            txtTotalAmount.setTextColor(getResources().getColor(R.color.green_500));        }

        btnAddAccount = view.findViewById(R.id.btnAddAccount);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                intent.putExtra("categoryType", "ACCOUNT");
                intent.putExtra("title", "New Account");
                startActivity(intent);
            }
        });
        bindAccount();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderAccount();
    }

    private void bindAccount(){
        Spinner spnAccount = view.findViewById(R.id.spnAccount);
        TextView txtHeaderAmount = view.findViewById(R.id.txtHeaderAmount);

        if(mainActivity.isAllAccount == null) return;
        ArrayList<Category> listAccount = sqlHelper.getCategoryByType("ACCOUNT");
        ArrayList<String> listAccountName = Util.getListName(listAccount);
        listAccountName.add(0, "All Account");
        ArrayAdapter adapterAccount = new ArrayAdapter(getActivity(), R.layout.item_spinner_account, listAccountName);
        adapterAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAccount.setAdapter(adapterAccount);

        int amount = 0;
        if (mainActivity.isAllAccount) {
            spnAccount.setSelection(adapterAccount.getPosition("All Account"));
            amount = sqlHelper.getAccountAmount(true, 0);
        } else {
            spnAccount.setSelection(adapterAccount.getPosition(mainActivity.currentAccount.getName()));
            amount = sqlHelper.getAccountAmount(false, mainActivity.currentAccount.getId());
        }
        txtHeaderAmount.setText("₫ " + Util.convertMoney(amount));

        spnAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnAccount.setSelection(i);
                if (i == 0){
                    mainActivity.isAllAccount = true;
                    mainActivity.currentAccount = listAccount.get(0);
                }else{
                    mainActivity.isAllAccount = false;
                    mainActivity.currentAccount = listAccount.get(i-1);
                }

                int amount = 0;
                if (mainActivity.isAllAccount) {
                    amount = sqlHelper.getAccountAmount(true, 0);
                } else {
                    amount = sqlHelper.getAccountAmount(false, mainActivity.currentAccount.getId());
                }
                txtHeaderAmount.setText("₫ " + Util.convertMoney(amount));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void renderAccount(){
        rcvListAccount.setAdapter(new ListAccountAdapter(getActivity(), sqlHelper.getListAccountWithAmount()));
    }
}