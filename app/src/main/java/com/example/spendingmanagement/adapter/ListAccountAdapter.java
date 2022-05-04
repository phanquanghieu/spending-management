package com.example.spendingmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.model.Category;

import java.util.ArrayList;

public class ListAccountAdapter extends RecyclerView.Adapter<ListAccountAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> listAccount;

    public ListAccountAdapter(Context context, ArrayList<Category> listAccount) {
        this.context = context;
        this.listAccount = listAccount;
    }

    @NonNull
    @Override
    public ListAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAccountAdapter.ViewHolder holder, int position) {
        Category account = listAccount.get(position);
        holder.itemView.setTag(account);
        holder.accountBgIc.setBackgroundResource(account.getColor());
        holder.accountBgIc.setImageResource(account.getIcon());
        holder.txtAccountName.setText(account.getName());
        holder.txtAccountAmount.setText("₫ " + account.getAmount());
        if(account.getAmount() < 0){
            holder.txtAccountAmount.setTextColor(context.getResources().getColor(R.color.red_500));
        }else{
            holder.txtAccountAmount.setTextColor(context.getResources().getColor(R.color.green_500));        }
    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView accountBgIc;
        TextView txtAccountName, txtAccountAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountBgIc = itemView.findViewById(R.id.accountBgIc);
            txtAccountName = itemView.findViewById(R.id.txtAccountName);
            txtAccountAmount = itemView.findViewById(R.id.txtAccountAmount);
        }
    }
}
