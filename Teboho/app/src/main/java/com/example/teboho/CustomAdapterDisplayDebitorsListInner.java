package com.example.teboho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDisplayDebitorsListInner extends RecyclerView.Adapter<CustomAdapterDisplayDebitorsListInner.MyViewHolder>{

    Context context;
    Activity activity;
    Colors colors = new Colors();
    private ArrayList client_id, client_name, client_surname, client_amountA,
            client_day, client_month, client_year, debit_status;
    double interest = 0.5 + 1;

    Animation translate_anim;

    CustomAdapterDisplayDebitorsListInner(Activity activity, Context context, ArrayList client_id, ArrayList client_name, ArrayList client_surname, ArrayList client_amountA,
                                          ArrayList client_day, ArrayList client_month, ArrayList client_year, ArrayList debit_status) {
        this.activity = activity;
        this.context = context;
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_amountA = client_amountA;
        this.client_day = client_day;
        this.client_month = client_month;
        this.client_year = client_year;
        this.debit_status = debit_status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.client_id_txt.setText(String.valueOf(client_id.get(position)));
        holder.client_name_txt.setText(String.valueOf(client_name.get(position)));
        holder.client_surname_txt.setText(String.valueOf(client_surname.get(position)));
        holder.client_amount_A_txt.setText(String.valueOf(client_amountA.get(position)));
        holder.client_day_txt.setText(String.valueOf(client_day.get(position)));
        holder.client_month_txt.setText(String.valueOf(client_month.get(position)));
        holder.client_year_txt.setText(String.valueOf(client_year.get(position)));
        holder.debit_status_txt.setText(String.valueOf(debit_status.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(position);
            }
        });

        if (String.valueOf(debit_status.get(position)).equals("unpaid"))
            holder.innerLayout.setBackgroundColor(Color.parseColor(colors.getColor("International orange")));
        else
            holder.innerLayout.setBackgroundColor(Color.parseColor(colors.getColor("Dark lemon lime")));
    }

    @Override
    public int getItemCount() {
        return client_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView client_id_txt, client_name_txt, client_surname_txt, client_amount_A_txt,
                client_day_txt, client_month_txt, client_year_txt, debit_status_txt;
        LinearLayout mainLayout;
        ConstraintLayout innerLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            client_id_txt = itemView.findViewById(R.id.client_id_txt);
            client_name_txt = itemView.findViewById(R.id.client_name_txt);
            client_surname_txt = itemView.findViewById(R.id.client_surname_txt);
            client_amount_A_txt = itemView.findViewById(R.id.client_amount_A_txt);
            client_day_txt = itemView.findViewById(R.id.client_day_txt);
            client_month_txt = itemView.findViewById(R.id.client_month_txt);
            client_year_txt = itemView.findViewById(R.id.client_year_txt);
            debit_status_txt = itemView.findViewById(R.id.client_amount_B_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            innerLayout = itemView.findViewById(R.id.innerLayout);

            //Animate the recycle viewer
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

    void confirmDialog(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + client_name.get(position) + " " + client_surname.get(position) + " debit?");
        builder.setMessage("Reverse debit order of " + client_name.get(position) + " " + client_surname.get(position) + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDBHandler myDBHandler = new MyDBHandler(context);
                int dynamicListID = Integer.parseInt(myDBHandler.getID(String.valueOf(client_name.get(position)), String.valueOf(client_surname.get(position))).getItem1());
                int location = Integer.parseInt(myDBHandler.getID(String.valueOf(client_name.get(position)), String.valueOf(client_surname.get(position))).getItem2());

                if (dynamicListID != -1){
                    Cursor cursor = myDBHandler.readAllData();
                    if (cursor.moveToPosition(location))
                    {
                        double currentDebit = cursor.getDouble(3)-Double.parseDouble(String.valueOf(client_amountA.get(position)));
                        double currentExpected = cursor.getDouble(4)-Double.parseDouble(String.valueOf(client_amountA.get(position)))*interest;
                        myDBHandler.updateData(String.valueOf(dynamicListID), currentDebit, currentExpected);
                        myDBHandler.deleteOneRowDebitRecord(String.valueOf(client_id.get(position)));
                    }
                    else
                        Toast.makeText(context, "Requested client dynamic list position error", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Client data unavailable on dynamic list", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
