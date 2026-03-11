package com.example.roomrental.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1_layout.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<com.example.roomrental.model.Room> roomList;
    private com.example.roomrental.controller.RoomAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddRoom = findViewById(R.id.btnAddRoom);

        // Khởi tạo List lưu trữ tạm thời
        roomList = new ArrayList<>();

        // Thiết lập RecyclerView [cite: 27]
        adapter = new com.example.roomrental.controller.RoomAdapter(roomList, new com.example.roomrental.controller.RoomAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                showRoomDialog(position); // Mở form sửa [cite: 39]
            }

            @Override
            public void onDeleteLongClick(int position) {
                showDeleteConfirmDialog(position); // Gọi AlertDialog xác nhận xóa [cite: 43]
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Nút Thêm phòng [cite: 21]
        btnAddRoom.setOnClickListener(v -> showRoomDialog(-1));
    }

    // Hiển thị Dialog Thêm/Sửa phòng [cite: 22, 38]
    private void showRoomDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_room, null);
        builder.setView(view);

        EditText edtId = view.findViewById(R.id.edtId);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPrice = view.findViewById(R.id.edtPrice);
        EditText edtStatus = view.findViewById(R.id.edtStatus); // Gợi ý: Nên dùng Spinner trên thực tế
        EditText edtRenterName = view.findViewById(R.id.edtRenterName);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        Button btnSave = view.findViewById(R.id.btnSave);

        com.example.roomrental.model.Room currentRoom = null;
        if (position >= 0) {
            // Load dữ liệu cũ nếu là Sửa [cite: 38]
            currentRoom = roomList.get(position);
            edtId.setText(currentRoom.getId());
            edtName.setText(currentRoom.getName());
            edtPrice.setText(String.valueOf(currentRoom.getPrice()));
            edtStatus.setText(currentRoom.getStatus());
            edtRenterName.setText(currentRoom.getRenterName());
            edtPhone.setText(currentRoom.getPhone());
        }

        AlertDialog dialog = builder.create();

        com.example.roomrental.model.Room finalCurrentRoom = currentRoom;
        btnSave.setOnClickListener(v -> {
            String id = edtId.getText().toString();
            String name = edtName.getText().toString();
            String priceStr = edtPrice.getText().toString();
            String status = edtStatus.getText().toString();

            // Validate dữ liệu cơ bản [cite: 23]
            if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin bắt buộc!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            String renter = edtRenterName.getText().toString();
            String phone = edtPhone.getText().toString();

            if (position >= 0) {
                // Sửa thông tin và cập nhật List [cite: 40]
                finalCurrentRoom.setId(id);
                finalCurrentRoom.setName(name);
                finalCurrentRoom.setPrice(price);
                finalCurrentRoom.setStatus(status);
                finalCurrentRoom.setRenterName(renter);
                finalCurrentRoom.setPhone(phone);
                adapter.notifyItemChanged(position);
            } else {
                // Thêm vào List và cập nhật RecyclerView [cite: 24, 25]
                roomList.add(new com.example.roomrental.model.Room(id, name, price, status, renter, phone));
                adapter.notifyItemInserted(roomList.size() - 1);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    // Hiển thị AlertDialog xác nhận xóa [cite: 43]
    private void showDeleteConfirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa phòng")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position); // Xóa khỏi List [cite: 44]
                    adapter.notifyItemRemoved(position); // Cập nhật lại RecyclerView [cite: 45]
                    Toast.makeText(MainActivity.this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}