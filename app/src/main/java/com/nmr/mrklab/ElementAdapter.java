package com.nmr.mrklab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.elementViewHolder> {
    private final ArrayList<Element> elements; // Replace String with your Model class
    private int bgColor;
    private int bgAltColor;
    // Constructor to pass the ArrayList from the Fragment
    public ElementAdapter(ArrayList<Element> data) {
        this.elements = data;
    }

    @NonNull
    @Override
    public elementViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your custom row layout (e.g., res/layout/item_row.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new elementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull elementViewHolder holder, int position) {
        // Bind the data from the ArrayList to the specific row
        Element element = elements.get(position);
        // Green bar effect: Alternating colors
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(bgColor); // Light Green
        } else {
            holder.itemView.setBackgroundColor(bgAltColor);
        }
        holder.nameView.setText(element.getName());
        holder.descView.setText(element.getDesc());
        holder.amtView.setText(element.getAmt());
        holder.progressBarView.setProgress(element.getIntAmt());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    // ViewHolder class to cache view references
    public static class elementViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView descView;
        TextView amtView;
        ProgressBar progressBarView;
        public elementViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            descView = itemView.findViewById(R.id.desc);
            amtView = itemView.findViewById(R.id.amt);
            progressBarView = itemView.findViewById(R.id.progressBar);
        }
    }

    public void setColors(int bgColor, int bgAltColor) {
        this.bgColor = bgColor;
        this.bgAltColor = bgAltColor;
    }


}