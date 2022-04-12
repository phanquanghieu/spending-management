package com.example.spendingmanagement.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spendingmanagement.R;

public class CategoryFragment extends Fragment {
    private View view;
    private Button btnEditCategory;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        btnEditCategory = view.findViewById(R.id.btn_edit_category);

        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"ssss", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}