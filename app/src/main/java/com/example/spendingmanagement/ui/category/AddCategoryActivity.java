package com.example.spendingmanagement.ui.category;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.sql.SQLHelper;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategoryActivity extends AppCompatActivity {

    private Button btnCreateCategory;
    private TextInputLayout inpCategoryName;
    private AutoCompleteTextView actCategoryColor, actCategoryIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_500)));
        }

        SQLHelper sqlHelper = new SQLHelper(AddCategoryActivity.this);
        String categoryType = getIntent().getStringExtra("categoryType");

        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        inpCategoryName = findViewById(R.id.inpCategoryName);
        actCategoryColor = findViewById(R.id.actCategoryColor);
        actCategoryIcon = findViewById(R.id.actCategoryIcon);

        actCategoryColor.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Util.colorName));
        actCategoryIcon.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Util.iconName));

        btnCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = inpCategoryName.getEditText().getText().toString().trim();
                if(categoryName.equals("")) return;

                int categoryColorId = Util.getColorIdByName(actCategoryColor.getText().toString());
                int categoryColorCodeId = Util.getColorCodeIdByName(actCategoryColor.getText().toString());
                int categoryIconId = Util.getIconIdByName(actCategoryIcon.getText().toString());

                Category categoryNew = new Category(categoryName, categoryType, categoryColorId, categoryColorCodeId, categoryIconId);

                if(sqlHelper.addCategory(categoryNew)){
                    Toast.makeText(AddCategoryActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddCategoryActivity.this, "Add Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}