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

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.helper.ItemTouchHelperAdapter;
import com.example.spendingmanagement.model.Transaction;
import com.example.spendingmanagement.ui.transaction.TransactionFragment;

import java.util.ArrayList;

public class ListTransactionAdapter extends RecyclerView.Adapter<ListTransactionAdapter.ViewHolder>
implements ItemTouchHelperAdapter {

    private Context context;
    private ArrayList<Transaction> listTransaction;
    private TransactionFragment transactionFragment;

    public ListTransactionAdapter(Context context, ArrayList<Transaction> listTransaction, TransactionFragment transactionFragment) {
        this.context = context;
        this.listTransaction = listTransaction;
        this.transactionFragment = transactionFragment;
    }


    @NonNull
    @Override
    public ListTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTransactionAdapter.ViewHolder holder, int position) {
        Transaction transaction = listTransaction.get(position);
        holder.itemView.setTag(transaction);
        holder.txtDate.setText(transaction.getDate());

        if(transaction.getFromType().equals("ACCOUNT") && transaction.getToType().equals("EXPENSES")){
            holder.transactionBgIc.setImageResource(transaction.getToCategory().getIcon());
            holder.transactionBgIc.setBackgroundResource(transaction.getToCategory().getColor());
            holder.txtTop.setText(transaction.getToCategory().getName());
            holder.txtBottom.setText(transaction.getFromCategory().getName());
            holder.txtAmount.setText("- ₫ " + transaction.getAmount());
            holder.txtAmount.setTextColor(context.getResources().getColor(R.color.red_500));
        }

        if(transaction.getFromType().equals("INCOME") && transaction.getToType().equals("ACCOUNT")){
            holder.transactionBgIc.setImageResource(transaction.getFromCategory().getIcon());
            holder.transactionBgIc.setBackgroundResource(transaction.getFromCategory().getColor());
            holder.txtTop.setText(transaction.getFromCategory().getName());
            holder.txtBottom.setText(transaction.getToCategory().getName());
            holder.txtAmount.setText("+ ₫ " + transaction.getAmount());
            holder.txtAmount.setTextColor(context.getResources().getColor(R.color.green_500));
        }

        if(transaction.getFromType().equals("ACCOUNT") && transaction.getToType().equals("ACCOUNT")){
            holder.transactionBgIc.setImageResource(transaction.getToCategory().getIcon());
            holder.transactionBgIc.setBackgroundResource(transaction.getToCategory().getColor());
            holder.txtTop.setText(transaction.getToCategory().getName());
            holder.txtBottom.setText(transaction.getFromCategory().getName());
            holder.txtAmount.setText("₫ " + transaction.getAmount());
        }

    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }


    @Override
    public void onItemDismiss(int position) {
        transactionFragment.deleteTransaction(listTransaction.get(position).getId());
        listTransaction.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView transactionBgIc;
        TextView txtTop, txtBottom, txtAmount, txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionBgIc = itemView.findViewById(R.id.transactionBgIc);
            txtTop = itemView.findViewById(R.id.txtTop);
            txtBottom = itemView.findViewById(R.id.txtBottom);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
