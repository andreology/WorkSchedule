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
 * Class used to help use functionality of a recycler view. This includes the layout view and
 * the listeners for when an item is selected.
 * @author Andre Barajas
 */
public class GroupRVAdapter extends RecyclerView.Adapter<GroupRVAdapter.GroupViewHolder>{
    /**
     * Helper class to display cardview.
     */
    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView groupTitle;
        TextView groupMembers;
        TextView groupStatus;
        ConstraintLayout constraintLayout;

        GroupViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            groupTitle = (TextView)itemView.findViewById(R.id.cardViewTitle);
            groupMembers = (TextView)itemView.findViewById(R.id.cardViewNumberOfUser);
            groupStatus = (TextView)itemView.findViewById(R.id.cardViewStatus);
            constraintLayout = itemView.findViewById(R.id.card_layout);
        }
    }

    private List<Group> groups;
    private String TAG = "GROUPrvaADAPTER";
    private Context mContext;

    /**
     * Constructor
     * @param groups List of groups.
     * @param context The activity that is calling the class.
     */
    GroupRVAdapter(List<Group> groups, Context context){
        this.groups = groups;
        mContext = context;
    }

    /**
     * Default function when extending recyclerview adapter.
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Inflates the cardview.
     * @param viewGroup the view to inflate
     * @param i the position in the list
     * @return
     */
    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_card_view, viewGroup, false);
        GroupViewHolder gvh = new GroupViewHolder(v);
        return gvh;
    }

    /**
     * Functionality for when a cardview is clicked. If a user is a member of the group
     * it will open a member layout. If a member is a manager of the group
     * @param holder The cardview that is clicked.
     * @param position The position in the groups list.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.groupTitle.setText(groups.get(position).getTitle());
        String status = "Status: " + groups.get(position).getStatus();
        holder.groupStatus.setText(status);
        String members = "Members: " + groups.get(position).getMembers();
        holder.groupMembers.setText(members);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, groups.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                if(groups.get(position).getStatus().equals("Manager")){//IF YOU ARE A MANAGER OF THE APP GO LOOK AT THE SCHEDULE
                    Intent intent = new Intent(mContext, ManagerScheduleActivity.class);
                    intent.putExtra("ID", groups.get(position).getId());
                    intent.putExtra("TITLE", groups.get(position).getTitle());
                    mContext.startActivity(intent);
                }else{//YOU ARE NOT A MANAGER OF THE GROUP SO YOUR HAVE TO BE A MEMBER
                    Log.d(TAG, "NOT MANAGER:");
                    Intent intent = new Intent(mContext, MemberScheduleActivity.class);
                    intent.putExtra("ID", groups.get(position).getId());
                    intent.putExtra("TITLE", groups.get(position).getTitle());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    /**
     * Returns size of group list.
     * @return
     */
    @Override
    public int getItemCount(){
        return groups.size();
    }
}
