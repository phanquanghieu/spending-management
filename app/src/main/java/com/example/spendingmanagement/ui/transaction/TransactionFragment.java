package com.example.spendingmanagement.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.ListTransactionAdapter;
import com.example.spendingmanagement.sql.SQLHelper;

public class TransactionFragment extends Fragment {
    private View view;
    private SQLHelper sqlHelper;
    private RecyclerView rcvListTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transaction, container, false);
        sqlHelper = new SQLHelper(getActivity());
        ((MainActivity)getActivity()).bindHeader(view);
        rcvListTransaction = view.findViewById(R.id.rcvListTransaction);

        rcvListTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvListTransaction.setAdapter(new ListTransactionAdapter(getActivity(), sqlHelper.getTransaction()));


        return view;
    }
}