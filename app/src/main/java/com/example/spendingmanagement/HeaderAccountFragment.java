package com.example.spendingmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HeaderAccountFragment extends Fragment {
    private View view;
    private LinearLayout btnAllAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_header_account, container, false);
        btnAllAccount = view.findViewById(R.id.btn_all_account);
        if (btnAllAccount != null)
            btnAllAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "ssss", Toast.LENGTH_LONG).show();
                }
            });
        return view;
    }
}