package com.example.spendingmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spendingmanagement.ui.category.EditCategoryActivity;
import com.example.spendingmanagement.R;
import com.example.spendingmanagement.model.Category;

import java.util.ArrayList;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> listCategory;

    public ListCategoryAdapter(Context context, ArrayList<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;

    }

    @NonNull
    @Override
    public ListCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_category,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(listCategory.get(position));
        holder.txtCategoryName.setText(listCategory.get(position).getName());
        holder.categoryBgIc.setBackgroundResource(listCategory.get(position).getColor());
        holder.categoryBgIc.setImageResource(listCategory.get(position).getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCategoryActivity.class);
                intent.putExtra("category",listCategory.get(position) );
                intent.putExtra("categoryType", "CATEGORY");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtCategoryName;
        ImageView categoryBgIc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            categoryBgIc = itemView.findViewById(R.id.categoryBgIc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
