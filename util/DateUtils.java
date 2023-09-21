package com.alphacmc.alphatraining.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    // TODO: Properties設定
    /**
     * 元号記号
     */
    private static final String[] ERA_SYMBOL = {"R", "H", "S"};
    /**
     * 元号開始日
     */
    private static final Date[] START_ERA = {
                            getDateType(2019, 5, 1),
                            getDateType(1989, 1, 8),
                            getDateType(1926, 12, 25)
        };

    /**
     * Date型を元号表記GYY/MM/DD方式に変換
     * @param date
     * @return GYY/MM/DD 形式（昭和以降）
     */
    public static String convertToEraDateString(Date date) {
        for (int i = 0; i < START_ERA.length; i++) {
            // 元号開始年月と比較
            if (!date.before(START_ERA[i])) {
                // 元号年月日を取得
                int eraYear = getDateInt(Calendar.YEAR, date) - getDateInt(Calendar.YEAR, START_ERA[i]) + 1;
                int month = getDateInt(Calendar.MONTH, date) + 1;
                int day = getDateInt(Calendar.DAY_OF_MONTH, date);
                // 元号表記文字列編集
                String eraYmd = ERA_SYMBOL[i] + String.format("%02d", eraYear) + "/" + String.format("%02d", month) + "/" + String.format("%02d", day);
                return eraYmd;
            }
        }
        return null;
    }

    /**
     * 元号表記文字列を Date 型に変換
     * @param jDateString　GYY/MM/DD 形式（昭和以降）
     * @return
     */
    public static Date convertFromJdate(String jDateString) {
        // 元号記号を抽出
        String eraString = jDateString.substring(0, 1);
        // 年月日を分離
        String[] ymd = jDateString.substring(1).split("/");

        // 該当元号のを見つけ開始年月日を取得し、年月日をDate型に変換する
        for (int i = 0; i < ERA_SYMBOL.length; ++i) {
            // 元号記号一致
            if (ERA_SYMBOL[i].equals(eraString)) {
                // 開始年月日を取得
                Calendar cal = Calendar.getInstance();
                cal.setTime(START_ERA[i]);
                // 西暦年月日を設定
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + Integer.parseInt(ymd[0]) - 1);
                cal.set(Calendar.MONTH, Integer.parseInt(ymd[1]) - 1);
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ymd[2]));
                // Date型で返却
                return cal.getTime();
            }
        }
        // 昭和以前はnullを返却
        return null;
    }

    /**
     * 数値年月日をDate型に変換
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDateType(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 年齢算出
     * @param birthYear
     * @param birthMonth
     * @param birthDay
     * @return
     */
    public static int getAge(int birthYear, int birthMonth, int birthDay) {
        // 現在日時取得
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH) + 1;
        int nowDay = now.get(Calendar.DAY_OF_MONTH);
        // 今年の誕生日は経過しているか？
        if ((birthMonth > nowMonth) || (birthMonth == nowMonth && birthDay <= nowDay)) {
            return (nowYear - birthYear);
        }
        return (nowYear - birthYear - 1);
    }

    /**
     * Date型から年月日を抽出
     * @param type
     * @param date
     * @return
     */
    private static int getDateInt(int type, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(type);
    }


    public static void main(String[] args) {




    }

}