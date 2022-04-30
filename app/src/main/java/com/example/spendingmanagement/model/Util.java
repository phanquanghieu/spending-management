package com.example.spendingmanagement.model;

import com.example.spendingmanagement.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Util {
    public static String[] colorName = {"Sky", "Green", "Red", "Teal", "Yellow"};
    public static int[] colorId = {R.drawable.circle_sky, R.drawable.circle_green, R.drawable.circle_red, R.drawable.circle_teal, R.drawable.circle_yellow};
    public static int[] colorCodeId = {R.color.sky_500, R.color.green_500, R.color.red_500, R.color.teal_500, R.color.yellow_500};

    public static String[] iconName = {"Card", "Bus", "Diamond"};
    public static int[] iconId = {R.drawable.ic_account_24, R.drawable.ic_bus_24, R.drawable.ic_diamond_24};

    public static int getColorIdByName(String name) {
        return colorId[Arrays.asList(colorName).indexOf(name)];
    }

    public static int getColorCodeIdByName(String name) {
        return colorCodeId[Arrays.asList(colorName).indexOf(name)];
    }

    public static int getIconIdByName(String name) {
        return iconId[Arrays.asList(iconName).indexOf(name)];
    }

    public static String getDateNow(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
