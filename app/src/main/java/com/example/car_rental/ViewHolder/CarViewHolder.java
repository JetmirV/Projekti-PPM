package com.example.car_rental.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rental.Interface.ItemClickListener;
import com.example.car_rental.R;

public class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView car_name;
    public ImageView car_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);

        car_name = ( TextView)itemView.findViewById(R.id.car_name);
        car_image = (ImageView)itemView.findViewById(R.id.car_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
