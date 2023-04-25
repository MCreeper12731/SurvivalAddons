package com.github.mcreeper12731.survivaladdons.util;

public class CusMath {

    public static double round(double num, double rounding) {
        num += 180;
        double a = (int)(num / rounding) * rounding;
        double b = a + rounding;
        return (num - a > b - num) ? b - 180 : a - 180;
    }

}
