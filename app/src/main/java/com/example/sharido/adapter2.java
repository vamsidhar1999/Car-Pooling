package com.example.sharido;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapter2 extends RecyclerView.Adapter<adapter2.viewHolder> {

    List<String> time1 = new ArrayList<String>();
    List<String> cost1 = new ArrayList<String>();
    List<String> seats1 = new ArrayList<String>();
    List<String> model1 = new ArrayList<String>();
    List<String> vehicle1 = new ArrayList<String>();
    List<String> source1 = new ArrayList<String>();
    List<String> destination1 = new ArrayList<String>();
    List<String> date1 = new ArrayList<String>();

    String from,to,date;
    Context ct;
    public adapter2(Context ct,List<String> time, List<String> cost, List<String> seats, List<String> model,List<String> vehicle,List<String> source,List<String> destination,List<String> date)
    {
        this.ct = ct;
        this.time1 = time;
        this.cost1 = cost;
        this.seats1 = seats;
        this.model1 = model;
        this.source1 = source;
        this.destination1 =  destination;
        this.date1 = date;
        this.vehicle1 = vehicle;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.recyclerview2, parent, false);
        return new viewHolder(view);
    }

    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.source.setText(source1.get(position));
        holder.destination.setText(destination1.get(position));
        holder.date.setText(date1.get(position));
        holder.time.setText(time1.get(position));
        holder.cost.setText(cost1.get(position));
        holder.model.setText(model1.get(position));
        holder.seats.setText(seats1.get(position)+" seats");


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct,booked_ride.class);
                intent.putExtra("time", time1.get(position));
                intent.putExtra("cost", cost1.get(position));
                intent.putExtra("model", model1.get(position));
                intent.putExtra("seats", seats1.get(position));
                intent.putExtra("vehicle",vehicle1.get(position));
                intent.putExtra("from", source1.get(position));
                intent.putExtra("to", destination1.get(position));
                intent.putExtra("date", date1.get(position));
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return time1.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView time,cost,model,seats,rating,source,destination,date,vehicle;
        LinearLayout linearLayout;

        public viewHolder(@NonNull View itemView) {

            super(itemView);
            source = itemView.findViewById(R.id.from);
            destination = itemView.findViewById(R.id.to);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            cost = itemView.findViewById(R.id.cost);
            model = itemView.findViewById(R.id.model);
            seats = itemView.findViewById(R.id.seats);
            //rating = itemView.findViewById(R.id.rating);
            linearLayout = itemView.findViewById(R.id.linear);

        }
    }
}
