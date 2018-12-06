package com.schedulesmadeeasy.schedular;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Class for helping generate a card
 */
public class ShiftRVAdapter extends RecyclerView.Adapter<ShiftRVAdapter.ShiftViewHolder>{
    public static class ShiftViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView companyName;
        TextView dayOfWeek;
        TextView time;
        ConstraintLayout constraintLayout;

        ShiftViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            companyName = (TextView)itemView.findViewById(R.id.cardViewTitle);
            dayOfWeek = (TextView)itemView.findViewById(R.id.cardViewNumberOfUser);
            time = (TextView)itemView.findViewById(R.id.cardViewStatus);
            constraintLayout = itemView.findViewById(R.id.card_layout);
        }
    }

    private List<Shift> shifts;
    private String TAG = "ShiftsrvaADAPTER";
    private Context mContext;

    ShiftRVAdapter(List<Shift> shifts, Context context){
        this.shifts = shifts;
        mContext = context;
    }

    /**
     * Setting up the recycler view
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Upon creation of view holder
     * @param viewShift
     * @param i
     * @return
     */
    @Override
    public ShiftViewHolder onCreateViewHolder(ViewGroup viewShift, int i ){
        View v = LayoutInflater.from(viewShift.getContext()).inflate(R.layout.shift_card_view, viewShift, false);
        ShiftViewHolder svh = new ShiftViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.companyName.setText(shifts.get(position).getCompany());
        String dayOfWeek = "Day: " + shifts.get(position).getDay();
        holder.dayOfWeek.setText(dayOfWeek);
        String time = "Time: " + shifts.get(position).getTime();
        holder.time.setText(time);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, shifts.get(position).getCompany(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ManagerScheduleActivity.class);
                    intent.putExtra("Day", shifts.get(position).getDay());
                    intent.putExtra("Time", shifts.get(position).getTime());
                    mContext.startActivity(intent);
            }
        });
    }

    /**
     * Returns the item count
     * @return
     */
    @Override
    public int getItemCount(){
        return shifts.size();
    }
}
