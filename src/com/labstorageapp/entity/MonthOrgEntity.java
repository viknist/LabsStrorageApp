package com.labstorageapp.entity;

import java.util.Objects;

public class MonthOrgEntity {
    private int idMonthOrg;
    private int idOrg;
    private String nameOrg;
    private int idConsumable;
    private String nameConsumable;
    private String month;
    private String year;
    private int balance;
    private int income;
    private int outcome;


    public MonthOrgEntity(int idMonthOrg, int idOrg, String nameOrg, int idConsumable, String nameConsumable, String month, String year, int balance, int income, int outcome) {
        this.idMonthOrg = idMonthOrg;
        this.idOrg = idOrg;
        this.nameOrg = nameOrg;
        this.idConsumable = idConsumable;
        this.nameConsumable = nameConsumable;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.income = income;
        this.outcome = outcome;
    }

    public MonthOrgEntity(int idOrg, String nameOrg, int idConsumable, String nameConsumable, String month, String year, int balance, int income, int outcome) {
        this.idMonthOrg = -1;
        this.idOrg = idOrg;
        this.nameOrg = nameOrg;
        this.idConsumable = idConsumable;
        this.nameConsumable = nameConsumable;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.income = income;
        this.outcome = outcome;
    }

    public int getIdMonthOrg() {
        return idMonthOrg;
    }

    public void setIdMonthOrg(int idMonthOrg) {
        this.idMonthOrg = idMonthOrg;
    }

    public int getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(int idOrg) {
        this.idOrg = idOrg;
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public int getIdConsumable() {
        return idConsumable;
    }

    public void setIdConsumable(int idConsumable) {
        this.idConsumable = idConsumable;
    }

    public String getNameConsumable() {
        return nameConsumable;
    }

    public void setNameConsumable(String nameConsumable) {
        this.nameConsumable = nameConsumable;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "monthOrgEntity{" +
                "idMonthOrg=" + idMonthOrg +
                ", idOrg=" + idOrg +
                ", nameOrg='" + nameOrg + '\'' +
                ", idConsumable=" + idConsumable +
                ", nameConsumable='" + nameConsumable + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", balance=" + balance +
                ", income=" + income +
                ", outcome=" + outcome +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthOrgEntity that = (MonthOrgEntity) o;
        return idMonthOrg == that.idMonthOrg && idOrg == that.idOrg && idConsumable == that.idConsumable && balance == that.balance && income == that.income && outcome == that.outcome && Objects.equals(nameOrg, that.nameOrg) && Objects.equals(nameConsumable, that.nameConsumable) && Objects.equals(month, that.month) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMonthOrg, idOrg, nameOrg, idConsumable, nameConsumable, month, year, balance, income, outcome);
    }
}
