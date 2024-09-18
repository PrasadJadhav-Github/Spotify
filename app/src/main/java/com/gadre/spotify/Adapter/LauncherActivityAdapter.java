package com.gadre.spotify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.ModelClass.LauncherActivityDataClass;
import com.gadre.spotify.R;

import java.util.List;

public class LauncherActivityAdapter extends RecyclerView.Adapter<LauncherActivityAdapter.LauncherActivityViewHolder> {

    private List<LauncherActivityDataClass>launcherActivityDataClassList;

    public LauncherActivityAdapter(List<LauncherActivityDataClass> launcherActivityDataClassList) {
        this.launcherActivityDataClassList = launcherActivityDataClassList;
    }

    @NonNull
    @Override
    public LauncherActivityAdapter.LauncherActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_launcher, parent, false);
        return new LauncherActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LauncherActivityAdapter.LauncherActivityViewHolder holder, int position) {
    LauncherActivityDataClass launcherActivityDataClass=launcherActivityDataClassList.get(position);
    holder.imageIcon.setImageResource(launcherActivityDataClass.getImageResId());
    holder.launcherActivityTextView.setText(launcherActivityDataClass.getName());
    }

    @Override
    public int getItemCount() {
        return launcherActivityDataClassList.size();
    }

    public class LauncherActivityViewHolder extends  RecyclerView.ViewHolder {
        ImageView imageIcon;
        TextView launcherActivityTextView;

        public LauncherActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIcon=itemView.findViewById(R.id.imageViewItem);
            launcherActivityTextView=itemView.findViewById(R.id.textViewItemName);
        }
    }
}
