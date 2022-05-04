package com.example.spendingmanagement.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.ListTransactionAdapter;
import com.example.spendingmanagement.helper.ItemTouchHelperCallback;
import com.example.spendingmanagement.model.Transaction;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    private SQLHelper sqlHelper;
    private RecyclerView rcvListTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transaction, container, false);
        sqlHelper = new SQLHelper(getActivity());
        mainActivity = ((MainActivity)getActivity());
        mainActivity.bindHeader(view);

        rcvListTransaction = view.findViewById(R.id.rcvListTransaction);
        rcvListTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));


        renderTransaction();
        bindDate();

        return view;
    }

    private void  bindDate(){
        TextView txtDate = view.findViewById(R.id.txtDate);
        Button btnDateLeft = view.findViewById(R.id.btnDateLeft);
        Button btnDateRight = view.findViewById(R.id.btnDateRight);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.isAllTime = true;
                mainActivity.startDate = "2000-01-01";
                mainActivity.endDate = "2025-01-01";
                renderTransaction();
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
                renderTransaction();
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
                renderTransaction();
            }
        });

    }

    private void renderTransaction(){
        ArrayList<Transaction> listTransaction = sqlHelper.getTransaction(mainActivity.startDate, mainActivity.endDate);

        ListTransactionAdapter transactionAdapter = new ListTransactionAdapter(getActivity(), listTransaction, this);
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(transactionAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rcvListTransaction);

        rcvListTransaction.setAdapter(transactionAdapter);
        mainActivity.bindHeader(view);
    }

    public void deleteTransaction(int transactionId){
        sqlHelper.deleteTransaction(transactionId);
        renderTransaction();
    }
}