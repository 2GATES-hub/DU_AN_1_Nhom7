package com.example.roomrental.controller;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomrental.model.Room;
import com.example.test1_layout.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteLongClick(int position);
    }

    public RoomAdapter(List<Room> roomList, OnItemClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvName.setText("Phòng: " + room.getName()); // [cite: 29]
        holder.tvPrice.setText("Giá: " + room.getPrice()); // [cite: 32]
        holder.tvStatus.setText(room.getStatus()); // [cite: 33]

        // Tô màu theo tình trạng [cite: 34]
        if (room.getStatus().equals("Còn trống")) {
            holder.tvStatus.setTextColor(Color.GREEN); // [cite: 35]
        } else {
            holder.tvStatus.setTextColor(Color.RED); // [cite: 37]
        }

        // Bắt sự kiện Click để Sửa [cite: 39]
        holder.itemView.setOnClickListener(v -> listener.onEditClick(position));

        // Bắt sự kiện Nhấn giữ để Xóa [cite: 42]
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDeleteLongClick(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvStatus = itemView.findViewById(R.id.tvRoomStatus);
        }
    }
}