package com.gadre.spotify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.ModelClass.InOutDataClass;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;
import com.gadre.spotify.R;

import java.io.File;
import java.util.List;

public class SwayamApiAdapter extends RecyclerView.Adapter<SwayamApiAdapter.SwayamViewHolder> {
    private final List<InOutDataClass>inOutDataClassesList;

    public SwayamApiAdapter(List<InOutDataClass> inOutDataClasses) {
        this.inOutDataClassesList = inOutDataClasses != null ? inOutDataClasses : List.of();
    }


    @NonNull
    @Override
    public SwayamApiAdapter.SwayamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_checkin_checkout, parent, false);
        return new SwayamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwayamApiAdapter.SwayamViewHolder holder, int position) {
        InOutDataClass data = inOutDataClassesList.get(position);
        holder.checkIn.setText(data.getInTime());
        holder.checkOut.setText(data.getOutTime());
////        holder.checkindate.setText();
////        holder.checkoutdate.setText();



    }

    @Override
    public int getItemCount() {
        return inOutDataClassesList.size();
    }

    public class SwayamViewHolder extends RecyclerView.ViewHolder {
        TextView checkIn;
        TextView checkOut;
        TextView status;
        TextView checkindate;
        TextView checkoutdate;
        public SwayamViewHolder(@NonNull View itemView) {
            super(itemView);
            checkIn=itemView.findViewById(R.id.itemview_checkin_Am_textview);
            checkOut=itemView.findViewById(R.id.itemview_checkout_PM_textview);
            checkindate=itemView.findViewById(R.id.itemview_checkin_Date_textview);
            checkoutdate=itemView.findViewById(R.id.itemview_checkout_Date_textview);
        }
    }
}
