package com.example.manga.System;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.manga.Home.Item;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MangaDB";
    private static final int DATABASE_VERSION = 1;

    // Bảng danh sách truyện
    private static final String TABLE_STORY = "story";
    private static final String COLUMN_STORY_ID = "id";
    private static final String COLUMN_STORY_TITLE = "title";
    private static final String COLUMN_STORY_AUTHOR = "author";
    private static final String COLUMN_STORY_COVER = "cover_image";
    private static final String COLUMN_STORY_DESCRIPTION = "description";

    // Bảng danh sách chương
    private static final String TABLE_CHAPTER = "chapter";
    private static final String COLUMN_CHAPTER_ID = "id";
    private static final String COLUMN_CHAPTER_TITLE = "title";
    private static final String COLUMN_CHAPTER_CONTENT = "content";
    private static final String COLUMN_CHAPTER_STORY_ID = "story_id";

    // Bảng người dùng
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_EMAIL = "email";
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng danh sách truyện
        String createStoryTable = "CREATE TABLE " + TABLE_STORY + " (" +
                COLUMN_STORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STORY_TITLE + " TEXT, " +
                COLUMN_STORY_AUTHOR + " TEXT, " +
                COLUMN_STORY_COVER + " TEXT, " +
                COLUMN_STORY_DESCRIPTION + " TEXT)";
        db.execSQL(createStoryTable);

        // Tạo bảng danh sách chương
        String createChapterTable = "CREATE TABLE " + TABLE_CHAPTER + " (" +
                COLUMN_CHAPTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CHAPTER_TITLE + " TEXT, " +
                COLUMN_CHAPTER_CONTENT + " TEXT, " +
                COLUMN_CHAPTER_STORY_ID + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_CHAPTER_STORY_ID + ") REFERENCES " + TABLE_STORY + "(" + COLUMN_STORY_ID + "))";
        db.execSQL(createChapterTable);

        // Tạo bảng người dùng
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT UNIQUE, " +
                COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public boolean addStory(String title, String author, String coverImage, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STORY_TITLE, title);
        values.put(COLUMN_STORY_AUTHOR, author);
        values.put(COLUMN_STORY_COVER, coverImage);
        values.put(COLUMN_STORY_DESCRIPTION, description);

        long result = db.insert(TABLE_STORY, null, values);
        db.close();
        return result != -1;
    }
    public List<String> getAllStories() {
        List<String> storyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_STORY_TITLE + " FROM " + TABLE_STORY, null);

        if (cursor.moveToFirst()) {
            do {
                storyList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return storyList;
    }
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " +
                COLUMN_USER_NAME + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{username, password});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

}

