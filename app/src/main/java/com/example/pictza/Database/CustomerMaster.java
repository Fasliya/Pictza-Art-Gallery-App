package com.example.pictza.Database;

import android.provider.BaseColumns;

public class CustomerMaster {

    private CustomerMaster() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

}
