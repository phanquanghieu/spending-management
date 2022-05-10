package com.example.spendingmanagement.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    Spinner languageSpinner;
    Spinner monneySpinner;
    Spinner initScreenSpinner;
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