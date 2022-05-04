package com.example.spendingmanagement.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.ListAccountAdapter;
import com.example.spendingmanagement.sql.SQLHelper;
import com.example.spendingmanagement.ui.category.AddCategoryActivity;
import com.example.spendingmanagement.ui.category.ListCategoryActivity;

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
        txtTotalAmount.setText("₫ " + totalAmount);
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderAccount();
    }

    private void renderAccount(){
        rcvListAccount.setAdapter(new ListAccountAdapter(getActivity(), sqlHelper.getListAccountWithAmount()));
    }
}