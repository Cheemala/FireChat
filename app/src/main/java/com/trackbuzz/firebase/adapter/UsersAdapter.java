package com.trackbuzz.firebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trackbuzz.firebase.R;
import com.trackbuzz.firebase.Utils.RecyclerviewCallbacks;
import com.trackbuzz.firebase.model.UsersData;

import java.util.List;

/**
 * Created by CheemalaCh on 7/12/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context context;
    private List<UsersData> usrDetails;
    private RecyclerviewCallbacks recyclerviewCallback;

    public UsersAdapter(Context context, List<UsersData> usrDetails, RecyclerviewCallbacks recyclerviewCallbacks) {
        this.context = context;
        this.usrDetails = usrDetails;
        this.recyclerviewCallback = recyclerviewCallbacks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View usrItemVw = LayoutInflater.from(context).inflate(R.layout.usr_list_item, parent, false);
        final MyViewHolder myVwHolder = new MyViewHolder(usrItemVw);
        usrItemVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerviewCallback.OnRecyclerViewItemClicked(v, myVwHolder.getAdapterPosition());
            }
        });
        return myVwHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.getUsrId().setText(usrDetails.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        Log.d("data", "_size_" + usrDetails.size());
        return usrDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView usrId;

        public MyViewHolder(final View itemView) {
            super(itemView);
            usrId = itemView.findViewById(R.id.usr_id_txt);
        }

        public TextView getUsrId() {
            return usrId;
        }

        public void setUsrId(TextView usrId) {
            this.usrId = usrId;
        }
    }
}
