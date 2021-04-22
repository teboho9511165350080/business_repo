package com.example.teboho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDisplayInvestmentWithdrawal extends RecyclerView.Adapter<CustomAdapterDisplayInvestmentWithdrawal.MyViewHolder>{

    Context context;
    Activity activity;
    private ArrayList transaction_date, transaction_reason, transaction_amount;

    Animation translate_anim;

    CustomAdapterDisplayInvestmentWithdrawal (Activity activity, Context context, ArrayList transaction_amount, ArrayList transaction_reason, ArrayList transaction_date) {
        this.activity = activity;
        this.context = context;
        this.transaction_amount = transaction_amount;
        this.transaction_reason = transaction_reason;
        this.transaction_date = transaction_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.in_out, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.transaction_amount_txt.setText(String.valueOf(transaction_amount.get(position)));

        if (String.valueOf(transaction_amount.get(position)).charAt(0) != '-')
          holder.transaction_amount_txt.setTextColor(Color.GREEN);

        holder.transaction_date_txt.setText(String.valueOf(transaction_date.get(position)));
        holder.transaction_reason_txt.setText(String.valueOf(transaction_reason.get(position)));
    }

    @Override
    public int getItemCount() {
        return transaction_amount.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction_amount_txt, transaction_date_txt, transaction_reason_txt;
        LinearLayout mainLayout;
        ConstraintLayout innerLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_amount_txt = itemView.findViewById(R.id.transaction_amount);
            transaction_reason_txt = itemView.findViewById(R.id.transaction_reason);
            transaction_date_txt = itemView.findViewById(R.id.transaction_date);
            mainLayout = itemView.findViewById(R.id.mainLayout2);

            //Animate the recycle viewer
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
