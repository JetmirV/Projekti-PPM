package com.example.car_rental.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rental.Interface.ItemClickListener;
import com.example.car_rental.Model.Reservation;
import com.example.car_rental.R;

public class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtReservationId,txtReservationStatus,txtReservationPhone,txtReservationAddress;

    private ItemClickListener itemClickListener;

    public ReservationViewHolder(View itemView) {
        super(itemView);
        txtReservationAddress =(TextView)itemView.findViewById(R.id.reservation_address);
        txtReservationId=(TextView)itemView.findViewById(R.id.reservation_id);
        txtReservationStatus =(TextView)itemView.findViewById(R.id.reservation_status);
        txtReservationPhone =(TextView)itemView.findViewById(R.id.reservation_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
