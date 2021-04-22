package com.example.teboho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<Client> clients_list;
    List<Client> clients_listFull;
    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, List<Client> clients_list) {
        this.activity = activity;
        this.context = context;
        this.clients_list = clients_list;
        clients_listFull = new ArrayList<>(clients_list);
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

        holder.client_id_txt.setText(clients_list.get(position).getId());
        holder.client_name_txt.setText(clients_list.get(position).getName());
        holder.client_surname_txt.setText(clients_list.get(position).getSurname());
        holder.client_amount_A_txt.setText(clients_list.get(position).getAmountA());
        holder.client_amount_B_txt.setText(clients_list.get(position).getAmountB());
        holder.client_day_txt.setText(clients_list.get(position).getDay());
        holder.client_month_txt.setText(clients_list.get(position).getMonth());
        holder.client_year_txt.setText(clients_list.get(position).getYear());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, AddCreditorInner.class);
                intent.putExtra("id", clients_list.get(position).getId());
                intent.putExtra("name",clients_list.get(position).getName());
                intent.putExtra("surname", clients_list.get(position).getSurname());
                intent.putExtra("amountA", clients_list.get(position).getAmountA());
                intent.putExtra("amountB", clients_list.get(position).getAmountB());
                intent.putExtra("payDay", clients_list.get(position).getDay());
                intent.putExtra("payMonth", clients_list.get(position).getMonth());
                intent.putExtra("payYear", clients_list.get(position).getYear());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients_list.size();
    }

    @Override
    public Filter getFilter() {
        return filterClients;
    }

    private Filter filterClients = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String searchText = constraint.toString().toLowerCase();
            List<Client> tempList = new ArrayList<>();

            if (searchText.length() == 0 || searchText.isEmpty())
            {
                tempList.addAll(clients_listFull);
            }
            else
            {
                for (Client client: clients_listFull)
                {
                    if(client.getName().toLowerCase().contains(searchText))
                    {
                        tempList.add(client);
                    }
                }
            }

            FilterResults  filter = new FilterResults();
            filter.values = tempList;
            return filter;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filter) {

            clients_list.clear();
            clients_list.addAll((Collection<? extends Client>) filter.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView client_id_txt, client_name_txt, client_surname_txt, client_amount_A_txt,
        client_amount_B_txt, client_day_txt, client_month_txt, client_year_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            client_id_txt = itemView.findViewById(R.id.client_id_txt);
            client_name_txt = itemView.findViewById(R.id.client_name_txt);
            client_surname_txt = itemView.findViewById(R.id.client_surname_txt);
            client_amount_A_txt = itemView.findViewById(R.id.client_amount_A_txt);
            client_amount_B_txt = itemView.findViewById(R.id.client_amount_B_txt);
            client_day_txt = itemView.findViewById(R.id.client_day_txt);
            client_month_txt = itemView.findViewById(R.id.client_month_txt);
            client_year_txt = itemView.findViewById(R.id.client_year_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate the recycle viewer
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
