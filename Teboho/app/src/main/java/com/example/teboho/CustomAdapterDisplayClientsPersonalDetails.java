package com.example.teboho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapterDisplayClientsPersonalDetails extends RecyclerView.Adapter<CustomAdapterDisplayClientsPersonalDetails.MyViewHolder> implements Filterable {

    Colors colors = new Colors();
    Context context;
    Activity activity;
    List<Client> clients_list;
    List<Client> clients_listFull;

    Animation translate_anim;

    CustomAdapterDisplayClientsPersonalDetails(Activity activity, Context context,List<Client> clients_list) {
        this.activity = activity;
        this.context = context;
        this.clients_list = clients_list;
        clients_listFull = new ArrayList<>(clients_list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_clients_personal_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.client_name_txt.setText(clients_list.get(position).getName() + " " + clients_list.get(position).getSurname());
        holder.client_gender_txt.setText(clients_list.get(position).getGender());
        holder.client_contact_txt.setText(clients_list.get(position).getContact());
        holder.client_address_txt.setText(clients_list.get(position).getAddress());

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (holder.expandableView.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.VISIBLE);
                    holder.arrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.GONE);
                    holder.arrow.setBackgroundResource(R.drawable.ic_arrow_down);
                }
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

            if (searchText.isEmpty())
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

        TextView client_name_txt, client_gender_txt,
                client_contact_txt, client_address_txt;
        LinearLayout mainLayout;
        ConstraintLayout expandableView;
        Button arrow;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            client_name_txt = itemView.findViewById(R.id.client_name_display);
            client_gender_txt = itemView.findViewById(R.id.client_gender_display);
            client_contact_txt = itemView.findViewById(R.id.textViewNumberDisplay);
            client_address_txt = itemView.findViewById(R.id.textViewHomeAddress);

            mainLayout = itemView.findViewById(R.id.mainLayout3);
            expandableView = itemView.findViewById(R.id.expandable_cardView);
            arrow = itemView.findViewById(R.id.expand_button);
            cardView = itemView.findViewById(R.id.cardViewBig);


            //Animate the recycle viewer
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);

        }
    }
}
