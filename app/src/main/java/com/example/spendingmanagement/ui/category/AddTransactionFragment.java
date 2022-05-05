package com.example.spendingmanagement.ui.category;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Transaction;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class AddTransactionFragment extends BottomSheetDialogFragment {

    private CategoryFragment categoryFragment;
    private Category categorySelected;
    private SQLHelper sqlHelper;
    private Spinner spnFrom, spnTo;
    private TextView txtFromLabel, txtToLabel, txtLabel, txtAmount;
    private LinearLayout txtFromColor, txtToColor;
    private ImageView txtFromIcon, txtToIcon;
    private EditText inpAmount;
    private Button btnConfirm;
    private Category currentAccount;
    private String currentCategoryType;

    public AddTransactionFragment(CategoryFragment categoryFragment, Category categorySelected) {
        this.categoryFragment = categoryFragment;
        this.currentCategoryType = categoryFragment.currentCategoryType;
        this.categorySelected = categorySelected;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlHelper = new SQLHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);

        spnFrom = view.findViewById(R.id.spnFrom);
        spnTo = view.findViewById(R.id.spnTo);
        txtFromColor = view.findViewById(R.id.txtFromColor);
        txtFromIcon = view.findViewById(R.id.txtFromIcon);
        txtFromLabel = view.findViewById(R.id.txtFromLabel);
        txtToColor = view.findViewById(R.id.txtToColor);
        txtToIcon = view.findViewById(R.id.txtToIcon);
        txtToLabel = view.findViewById(R.id.txtToLabel);
        txtLabel = view.findViewById(R.id.txtLabel);
        txtAmount = view.findViewById(R.id.txtAmount);
        inpAmount = view.findViewById(R.id.inpAmount);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        ArrayList<Category> listAccount = sqlHelper.getCategoryByType("ACCOUNT");
        ArrayList<String> listAccountName = Util.getListName(listAccount);

        currentAccount = ((MainActivity) getActivity()).currentAccount;

        if (currentCategoryType.equals("EXPENSES")) {
            txtLabel.setText("Expenses");
            txtFromLabel.setText("From account");
            txtToLabel.setText("To category");
            txtAmount.setTextColor(getResources().getColor(R.color.red_500));
            inpAmount.setTextColor(getResources().getColor(R.color.red_500));

            txtFromColor.setBackgroundResource(currentAccount.getColorCode());
            txtFromIcon.setImageResource(currentAccount.getIcon());
            txtFromIcon.setColorFilter(getResources().getColor(currentAccount.getColorCode()));

            txtToColor.setBackgroundResource(categorySelected.getColorCode());
            txtToIcon.setImageResource(categorySelected.getIcon());
            txtToIcon.setColorFilter(getResources().getColor(categorySelected.getColorCode()));

            ArrayList<Category> listCategoryExpenses = sqlHelper.getCategoryByType("EXPENSES");
            ArrayList<String> listCategoryExpensesName = Util.getListName(listCategoryExpenses);

            ArrayAdapter adapterFrom = new ArrayAdapter(getActivity(), R.layout.item_spinner, listAccountName);
            adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFrom.setAdapter(adapterFrom);
            spnFrom.setSelection(adapterFrom.getPosition(currentAccount.getName()));

            ArrayAdapter adapterTo = new ArrayAdapter(getActivity(), R.layout.item_spinner, listCategoryExpensesName);
            adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTo.setAdapter(adapterTo);
            spnTo.setSelection(adapterTo.getPosition(categorySelected.getName()));


            spnFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spnFrom.setSelection(i);
                    txtFromColor.setBackgroundResource(listAccount.get(i).getColorCode());
                    txtFromIcon.setImageResource(listAccount.get(i).getIcon());
                    txtFromIcon.setColorFilter(getResources().getColor(listAccount.get(i).getColorCode()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spnTo.setSelection(i);
                    txtToColor.setBackgroundResource(listCategoryExpenses.get(i).getColorCode());
                    txtToIcon.setImageResource(listCategoryExpenses.get(i).getIcon());
                    txtToIcon.setColorFilter(getResources().getColor(listCategoryExpenses.get(i).getColorCode()));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inpAmount.getText().toString().equals("")) return;
                    int amount = Integer.parseInt(inpAmount.getText().toString());

                    Category fromCategory = listAccount.get(Integer.parseInt(String.valueOf(spnFrom.getSelectedItemId())));
                    Category toCategory = listCategoryExpenses.get(Integer.parseInt(String.valueOf(spnTo.getSelectedItemId())));
                    String date = Util.getDateNow();

                    Transaction transactionNew = new Transaction();
                    transactionNew.setFromCategory(fromCategory);
                    transactionNew.setToCategory(toCategory);
                    transactionNew.setFromType(fromCategory.getType());
                    transactionNew.setToType(toCategory.getType());
                    transactionNew.setDate(date);
                    transactionNew.setAmount(amount);

                    sqlHelper.addTransaction(transactionNew);

                    dismiss();
                }
            });

        }

        if (currentCategoryType.equals("INCOME")) {
            txtLabel.setText("Income");
            txtFromLabel.setText("From category");
            txtToLabel.setText("To account");
            txtAmount.setTextColor(getResources().getColor(R.color.green_500));
            inpAmount.setTextColor(getResources().getColor(R.color.green_500));

            txtFromColor.setBackgroundResource(categorySelected.getColorCode());
            txtFromIcon.setImageResource(categorySelected.getIcon());
            txtFromIcon.setColorFilter(getResources().getColor(categorySelected.getColorCode()));

            txtToColor.setBackgroundResource(currentAccount.getColorCode());
            txtToIcon.setImageResource(currentAccount.getIcon());
            txtToIcon.setColorFilter(getResources().getColor(currentAccount.getColorCode()));

            ArrayList<Category> listCategoryIncome = sqlHelper.getCategoryByType("INCOME");
            ArrayList<String> listCategoryIncomeName = Util.getListName(listCategoryIncome);

            ArrayAdapter adapterFrom = new ArrayAdapter(getActivity(), R.layout.item_spinner, listCategoryIncomeName);
            adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFrom.setAdapter(adapterFrom);
            spnFrom.setSelection(adapterFrom.getPosition(categorySelected.getName()));

            ArrayAdapter adapterTo = new ArrayAdapter(getActivity(), R.layout.item_spinner, listAccountName);
            adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTo.setAdapter(adapterTo);
            spnTo.setSelection(adapterTo.getPosition(currentAccount.getName()));


            spnFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spnFrom.setSelection(i);
                    txtFromColor.setBackgroundResource(listCategoryIncome.get(i).getColorCode());
                    txtFromIcon.setImageResource(listCategoryIncome.get(i).getIcon());
                    txtFromIcon.setColorFilter(getResources().getColor(listCategoryIncome.get(i).getColorCode()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spnTo.setSelection(i);
                    txtToColor.setBackgroundResource(listAccount.get(i).getColorCode());
                    txtToIcon.setImageResource(listAccount.get(i).getIcon());
                    txtToIcon.setColorFilter(getResources().getColor(listAccount.get(i).getColorCode()));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inpAmount.getText().toString().equals("")) return;
                    int amount = Integer.parseInt(inpAmount.getText().toString());

                    Category fromCategory = listCategoryIncome.get(Integer.parseInt(String.valueOf(spnFrom.getSelectedItemId())));
                    Category toCategory = listAccount.get(Integer.parseInt(String.valueOf(spnTo.getSelectedItemId())));
                    String date = Util.getDateNow();

                    Transaction transactionNew = new Transaction();
                    transactionNew.setFromCategory(fromCategory);
                    transactionNew.setToCategory(toCategory);
                    transactionNew.setFromType(fromCategory.getType());
                    transactionNew.setToType(toCategory.getType());
                    transactionNew.setDate(date);
                    transactionNew.setAmount(amount);

                    sqlHelper.addTransaction(transactionNew);

                    dismiss();
                }
            });
        }

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        System.out.println("AddTransactionFragment dismiss");
        categoryFragment.renderCategory();
    }
}
