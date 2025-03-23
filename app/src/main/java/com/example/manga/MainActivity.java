package com.example.manga;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.manga.Home.BannerAdapter;
import com.example.manga.Home.DetailActivity;
import com.example.manga.Home.Item;
import com.example.manga.Home.ItemAdapter;
import com.example.manga.Home.UserActivity;
import com.example.manga.Home.SearchActivity;
import com.example.manga.System.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SearchView search_view;
    private ImageButton btnUser;
    private ViewPager2 viewPager;
    private BannerAdapter bannerAdapter;
    final int[] images = {R.drawable.sample_image, R.drawable.sample_image2, R.drawable.sample_image3}; // Thay ảnh
    int currentPage = 0;
    Handler handler = new Handler();
    private ListView listView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(this);
        boolean success = dbHelper.addStory("Onepiece", "Eiichiro Oda","one_pice.png","Bộ truyện tập trung vào Monkey D. Luffy—một chàng trai trẻ có sức mạnh từ cao su sau khi vô tình ăn phải Trái Ác Quỷ Gomu-Gomu—người đã lên đường từ East Blue để tìm kho báu cuối cùng của Vua Hải Tặc Gol D. Roger đã khuất được gọi là One Piece, và tiếp quản danh hiệu trước đây của ông");
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang màn hình tìm kiếm (SearchActivity)
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }


        });
        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
        viewPager = findViewById(R.id.viewPager);
        BannerAdapter adapter = new BannerAdapter(this, images);

        viewPager.setAdapter(adapter);
        setupViewPager();
        startAutoSlide();
        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();
        itemList.add(new Item("One Piece", R.drawable.sample_image));
        itemList.add(new Item("Naruto", R.drawable.sample_image));
        itemList.add(new Item("Dragon Ball", R.drawable.sample_image));
        itemList.add(new Item("Conan", R.drawable.sample_image));
        itemList.add(new Item("Conan", R.drawable.sample_image));
        itemList.add(new Item("Conan", R.drawable.sample_image));

        itemAdapter = new ItemAdapter(this, itemList);
        listView.setAdapter(itemAdapter);
        // Bắt sự kiện click vào item trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = itemList.get(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_NAME", selectedItem.getName());
                intent.putExtra("ITEM_IMAGE", selectedItem.getImageResId());
                intent.putExtra("ITEM_DESCRIPTION", "Đây là nội dung mô tả về manga " + selectedItem.getName());
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = itemList.get(position);

                // Danh sách chương giả lập
                ArrayList<String> chapterList = new ArrayList<>();
                chapterList.add("Chương 1: Khởi đầu");
                chapterList.add("Chương 2: Bí ẩn");
                chapterList.add("Chương 3: Trận chiến");
                chapterList.add("Chương 4: Sự thật");
                chapterList.add("Chương 5: Hành trình mới");

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_NAME", selectedItem.getName());
                intent.putExtra("ITEM_IMAGE", selectedItem.getImageResId());
                intent.putExtra("ITEM_DESCRIPTION", "Đây là nội dung mô tả về manga " + selectedItem.getName());
                intent.putStringArrayListExtra("ITEM_CHAPTERS", chapterList);
                startActivity(intent);
            }
        });
    }

    private void startAutoSlide() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 3 giây tự động đổi ảnh
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void setupViewPager() {
        bannerAdapter = new BannerAdapter(this, images);
        viewPager.setAdapter(bannerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        });
        viewPager.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Xử lý sự kiện chạm vào ViewPager để ngăn NestedScrollView can thiệp
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Ngăn NestedScrollView xử lý sự kiện chạm
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Cho phép NestedScrollView xử lý lại các sự kiện chạm
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    ;

    public void goToLogin(View view) {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(intent);
    }
}