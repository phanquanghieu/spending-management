package com.example.spendingmanagement.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.spendingmanagement.sql.SQLHelper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.ui.category.EditCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    Spinner languageSpinner;
    Spinner monneySpinner;
    Spinner initScreenSpinner;
    LinearLayout deleteAll ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.bindHeader(view);
        final String optionLanguage[] = {"Tiếng Việt","English"};
        final String optionMoney[] ={"VND","$"};
        final String optionScreen[] ={"Acount","Category","Transaction","Setting"};
        languageSpinner = view.findViewById(R.id.languageSpinner);
        monneySpinner = view.findViewById(R.id.moneySpinner);
        initScreenSpinner = view.findViewById(R.id.screenSpinner);
        deleteAll = view.findViewById(R.id.btnDeleteAll);
        SQLHelper sqlHelper = new SQLHelper(getActivity());
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("delete");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Warning");
                alertDialog.setIcon(R.drawable.ic_warning);
                alertDialog.setMessage("Do you want to delete all data?");
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlHelper.deleteAllData();
                        Toast.makeText(getActivity(), "Delete Success", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.show();
            }
        });
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, optionLanguage);
        ArrayAdapter<String> adapterMoney = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, optionMoney);
        ArrayAdapter<String> adapterScreen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, optionScreen);

        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMoney.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterScreen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapterLanguage);
        monneySpinner.setAdapter(adapterMoney);
        initScreenSpinner.setAdapter(adapterScreen);
        return view;
    }
}