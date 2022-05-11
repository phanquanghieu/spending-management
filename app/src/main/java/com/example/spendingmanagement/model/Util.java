package com.example.spendingmanagement.model;

import com.example.spendingmanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static String[] colorName = {"Sky", "Green", "Red", "Teal", "Yellow"};
    public static Integer[] colorId = {R.drawable.circle_sky, R.drawable.circle_green, R.drawable.circle_red, R.drawable.circle_teal, R.drawable.circle_yellow};
    public static Integer[] colorCodeId = {R.color.sky_500, R.color.green_500, R.color.red_500, R.color.teal_500, R.color.yellow_500};

    public static String[] iconName = {"Card", "Bus", "Diamond","Education","Health","Shopping","Groceries","Restaurant","Family","Gifts","Internet","Electric", "Water","Entertainment","Games","Travel","Insurance","Technology","Movie","Salary"};
    public static Integer[] iconId = {R.drawable.ic_account_24, R.drawable.ic_bus_24, R.drawable.ic_diamond_24, R.drawable.ic_education,R.drawable.ic_baseline_health_24,R.drawable.ic_shopping_24, R.drawable.ic_groceries_24,R.drawable.ic_restaurant,
            R.drawable.ic_family_24,R.drawable.ic_gift_icon4,R.drawable.ic_internet_24, R.drawable.ic_electric_24, R.drawable.ic_water,R.drawable.ic_entertainment, R.drawable.ic_games_24 ,R.drawable.ic_travel_24, R.drawable.ic_insurance, R.drawable.ic_technology, R.drawable.ic_movie_24 ,R.drawable.ic_curr};


    public static int getColorIdByName(String name) {
        return colorId[Arrays.asList(colorName).indexOf(name)];
    }

    public static int getColorCodeIdByName(String name) {
        return colorCodeId[Arrays.asList(colorName).indexOf(name)];
    }

    public static int getIconIdByName(String name) {
        return iconId[Arrays.asList(iconName).indexOf(name)];
    }
    public static String getNameColorByColorId(int id) {
        return colorName[Arrays.asList(colorId).indexOf(id)];
    }
    public static String getNameIconByIconId(int id) {
        return iconName[Arrays.asList(iconId).indexOf(id)];
    }
    public static String getDateNow(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
    public static String getMonthByStartDate(String startDate){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            return  new SimpleDateFormat("MM - yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getStartDateByDateNow(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String getStartDateByStartDate(String startDate ,int m){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
            c.add(Calendar.MONTH, m);
            return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEndDateByStartDate(String startDate){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ArrayList<String> getListName(ArrayList<Category> listCategory){
        ArrayList<String> listName = new ArrayList<>();
        for (Category category : listCategory
        ) {
            listName.add(category.getName());
        }
        return listName;
    }

    public static String convertMoney(int amount){
        String s = "";
        String a;

        if(amount <0) {
            s = "- ";
            a = String.valueOf(-amount);
        }
        else a = String.valueOf(amount);
        String r = "";
        int i = a.length() - 1;
        for (; i>=3 ; i-=3){
            r = "," + a.charAt(i-2) + a.charAt(i-1) + a.charAt(i) + r;
        }
        for (int j = i; j>= 0; j--){
            r = a.charAt(j) + r;
        }
        return s+r;
    }
}
