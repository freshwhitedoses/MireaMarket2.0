package com.example.mireamarket.model;

import android.provider.BaseColumns;

public class FoodContract {

    public FoodContract(){}

    public static class QuestionTable implements BaseColumns {

        public static final String TABLE_NAME = "food";
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String CATEGORY = "category";

    }
}
