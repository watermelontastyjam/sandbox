package ru.ssau.pigeonmail.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessengerDbHelper extends SQLiteOpenHelper {
    public final static String LOG_TAG = MessengerDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "messenger.db";
    private static final int DATABASE_VERSION = 1;
    public MessengerDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + MessengerContract.Users.TABLE_NAME + " ("
                + MessengerContract.Users.USER_ID + " TEXT PRIMARY KEY, "
                + MessengerContract.Users.EMAIL + " TEXT NOT NULL, "
                + MessengerContract.Users.PROFILE_IMAGE + " TEXT, "
                + MessengerContract.Users.USER_NAME + " TEXT NOT NULL);";

        String SQL_CREATE_USER_CHATS_TABLE ="CREATE TABLE " + MessengerContract.UserChats.TABLE_NAME + " ("
                + MessengerContract.UserChats._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MessengerContract.UserChats.USER_ID + " TEXT NOT NULL, "
                + MessengerContract.UserChats.CHAT_ID + " TEXT);";

        String SQL_CREATE_CHATS_TABLE = "CREATE TABLE " + MessengerContract.Chats.TABLE_NAME + " ("
                + MessengerContract.Chats.CHAT_ID + " TEXT PRIMARY KEY AUTOINCREMENT, "
                + MessengerContract.Chats.USER_1 + " TEXT NOT NULL, "
                + MessengerContract.Chats.USER_2 + " TEXT NOT NULL);";
        String SQL_CREATE_MESSAGES_TABLE = "CREATE TABLE " + MessengerContract.Messages.TABLE_NAME + " ("
                + MessengerContract.Messages.MESSAGE_ID+ "I NTEGER PRIMARY KEY, "
                + MessengerContract.Messages.CHAT_ID + " TEXT NOT NULL, "
                + MessengerContract.Messages.DATE + " TEXT, "
                + MessengerContract.Messages.OWNER_ID + " TEXT NOT NULL,"
                + MessengerContract.Messages.TEXT + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_CHATS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CHATS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MESSAGES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
