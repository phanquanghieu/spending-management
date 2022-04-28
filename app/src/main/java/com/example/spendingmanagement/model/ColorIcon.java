package com.example.spendingmanagement.model;

import com.example.spendingmanagement.R;
import java.util.Arrays;

public class ColorIcon {
    public static String[] colorName = {"Green", "Red", "Teal", "Yellow"};
    public static int[] colorId = {R.drawable.circle_green, R.drawable.circle_red, R.drawable.circle_teal, R.drawable.circle_yellow};

    public static String[] iconName = {"Bus", "Diamond"};
    public static int[] iconId = {R.drawable.ic_bus_24, R.drawable.ic_diamond_24};

    public static int getColorIdByName(String name) {
        return colorId[Arrays.asList(colorName).indexOf(name)];
    }

    public static int getIconIdByName(String name) {
        return iconId[Arrays.asList(iconName).indexOf(name)];
    }
}
