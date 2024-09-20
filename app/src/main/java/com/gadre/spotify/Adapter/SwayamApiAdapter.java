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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SwayamApiAdapter extends RecyclerView.Adapter<SwayamApiAdapter.SwayamViewHolder> {
    private final List<InOutDataClass> inOutDataClassesList;

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


        String inTime = data.getInTime();
        String outTime = data.getOutTime();


        String[] inTimeParts = inTime.split("\\|");
        String[] outTimeParts = outTime.split("\\|");

        String checkInTime = inTimeParts[1];
        String checkInDate =inTimeParts[0].trim();
        String checkOutTime =outTimeParts[1];
        String checkOutDate=outTimeParts[0].trim();

        //check in date and time
        holder.checkInTime.setText(checkInTime);
        holder.checkindate.setText(checkInDate);

        //check out date and time
        holder.checkOutTime.setText(checkOutTime);
        holder.checkoutdate.setText(checkOutDate);

    }

    @Override
    public int getItemCount() {
        return inOutDataClassesList.size();
    }

    public class SwayamViewHolder extends RecyclerView.ViewHolder {
        TextView checkInTime;
        TextView checkOutTime;
        TextView status;
        TextView checkindate;
        TextView checkoutdate;

        public SwayamViewHolder(@NonNull View itemView) {
            super(itemView);
            checkInTime = itemView.findViewById(R.id.itemview_checkin_Am_textview);
            checkOutTime = itemView.findViewById(R.id.itemview_checkout_PM_textview);
            checkindate = itemView.findViewById(R.id.itemview_checkin_Date_textview);
            checkoutdate = itemView.findViewById(R.id.itemview_checkout_Date_textview);
        }
    }
}
