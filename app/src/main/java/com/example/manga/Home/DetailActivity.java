package com.example.manga.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manga.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewDetail;
    private TextView textViewTitle, textViewDescription;
    private Button btnBack;
    private ListView listViewChapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ view
        imageViewDetail = findViewById(R.id.imageViewDetail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        btnBack = findViewById(R.id.btnBack);
        listViewChapters = findViewById(R.id.listViewChapters);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String receivedTitle = intent.getStringExtra("ITEM_NAME");
        int receivedImage = intent.getIntExtra("ITEM_IMAGE", 0);
        String receivedDescription = intent.getStringExtra("ITEM_DESCRIPTION");
        ArrayList<String> receivedChapters = intent.getStringArrayListExtra("ITEM_CHAPTERS");

        // Cập nhật giao diện
        textViewTitle.setText(receivedTitle);
        imageViewDetail.setImageResource(receivedImage);
        textViewDescription.setText(receivedDescription);
        if (receivedChapters != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, receivedChapters);
            listViewChapters.setAdapter(adapter);
        }

        // Xử lý khi click vào chương
        listViewChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedChapter = receivedChapters.get(position);
                Toast.makeText(DetailActivity.this, "Mở " + selectedChapter, Toast.LENGTH_SHORT).show();

                // Chuyển sang màn hình đọc nội dung chương (nếu có)
                // Intent intent = new Intent(DetailActivity.this, ChapterDetailActivity.class);
                // intent.putExtra("CHAPTER_NAME", selectedChapter);
                // startActivity(intent);
            }
        });
        // Xử lý sự kiện nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng activity và quay về màn hình trước
            }
        });
    }
}
