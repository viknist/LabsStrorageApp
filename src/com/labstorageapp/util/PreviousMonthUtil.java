package com.labstorageapp.util;

import com.labstorageapp.entity.MonthYearEntity;

import java.util.Calendar;

public class PreviousMonthUtil {

    public static MonthYearEntity getPreviousMonth(){

        Calendar calendar = Calendar.getInstance();

        String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String month;


        switch (calendar.get(Calendar.MONTH)) {
            case (0):
                month = "Декабрь";
                year = String.valueOf(Integer.parseInt(year) - 1);
                break;
            case(1):
                month = "Январь";
                break;
            case(2):
                month = "Февраль";
                break;
            case (3):
                month = "Март";
                break;
            case (4):
                month = "Апрель";
                break;
            case (5):
                month = "Май";
                break;
            case (6):
                month = "Июнь";
                break;
            case (7):
                month = "Июль";
                break;
            case (8):
                month = "Август";
                break;
            case (9):
                month = "Сентябрь";
                break;
            case (10):
                month = "Октябрь";
                break;
            case (11):
                month = "Ноябрь";
                break;
            default:
                month = "Error";
                break;
        }
        System.out.println("Month :" + month);
    return new MonthYearEntity(month, year);
    }

}
