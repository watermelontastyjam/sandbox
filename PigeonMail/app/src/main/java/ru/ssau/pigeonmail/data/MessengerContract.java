package ru.ssau.pigeonmail.data;

import android.provider.BaseColumns;

import ru.ssau.pigeonmail.message.MessagesAdapter;

public class MessengerContract {
    private MessengerContract(){

    }
    public static final class Users implements BaseColumns{
        public final static String TABLE_NAME = "Users";

        public final static String USER_ID = BaseColumns._ID;
        public final static String EMAIL = "email";
        public final static String USER_NAME = "userName";
        public final static String PROFILE_IMAGE = "profileImage";
    }
    public static final class UserChats implements BaseColumns{
        public final static String TABLE_NAME = "User_chats";

        public final static String _ID = BaseColumns._ID;
        public final static String USER_ID = "userId";
        public final static String CHAT_ID = "chatId";
    }
    public static final class Chats implements BaseColumns{
        public final static String TABLE_NAME = "Chats";

        public final static String CHAT_ID = BaseColumns._ID;
        public final static String USER_1 = "user1";
        public final static String USER_2 = "user2";

    }
    public static final class Messages implements BaseColumns{
        public final static String TABLE_NAME = "Messages";

        public final static String MESSAGE_ID = BaseColumns._ID;
        public final static String CHAT_ID = "chatId";
        public final static String DATE = "date";
        public final static String OWNER_ID = "ownerId";
        public final static String TEXT = "text";
    }
}
