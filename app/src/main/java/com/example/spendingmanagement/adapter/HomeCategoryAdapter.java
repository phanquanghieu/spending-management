package com.example.spendingmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.MainActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Util;
import com.example.spendingmanagement.ui.category.AddTransactionFragment;
import com.example.spendingmanagement.ui.category.CategoryFragment;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Category> homeCategory;
    private CategoryFragment categoryFragment;

    public HomeCategoryAdapter(Context context, ArrayList<Category> homeCategory, CategoryFragment categoryFragment){
        this.context = context;
        this.homeCategory = homeCategory;
        this.categoryFragment = categoryFragment;
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(homeCategory.get(position));
        holder.categoryBgIc.setImageResource(homeCategory.get(position).getIcon());
        holder.categoryBgIc.setBackgroundResource(homeCategory.get(position).getColor());
        holder.txtCategoryName.setText(homeCategory.get(position).getName());
        holder.txtCategoryAmount.setText("₫ " + Util.convertMoney(homeCategory.get(position).getAmount()));
        holder.txtCategoryAmount.setTextColor(context.getResources().getColor(homeCategory.get(position).getColorCode()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category categorySelected = homeCategory.get(holder.getAdapterPosition());
                AddTransactionFragment addTransactionFragment = new AddTransactionFragment(categoryFragment, categorySelected);
                addTransactionFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                        addTransactionFragment.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategory.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryBgIc;
        TextView txtCategoryName, txtCategoryAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBgIc = itemView.findViewById(R.id.categoryBgIc);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCategoryAmount = itemView.findViewById(R.id.txtCategoryAmount);

        }
    }
}
