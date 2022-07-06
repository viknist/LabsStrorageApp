package com.labstorageapp.entity;

import java.util.Objects;

public class ConsumptionEntity {
    private int id;
    private String name;
    private String unitType;
    private String purpose;
    private String month;
    private String year;
    private int balance;
    private int income;
    private int outcome;


    public ConsumptionEntity(int id, String name, String unitType, String purpose, String month, String year, int balance, int income, int outcome) {
        this.id = id;
        this.name = name;
        this.unitType = unitType;
        this.purpose = purpose;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.income = income;
        this.outcome = outcome;
    }

    public ConsumptionEntity(String name, String unitType, String purpose, String month, String year, int balance, int income, int outcome) {
        this.name = name;
        this.unitType = unitType;
        this.purpose = purpose;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.income = income;
        this.outcome = outcome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
        return "ConsumptionEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitType='" + unitType + '\'' +
                ", purpose='" + purpose + '\'' +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", balance=" + balance +
                ", income=" + income +
                ", outcome=" + outcome +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumptionEntity that = (ConsumptionEntity) o;
        return id == that.id && year == that.year && balance == that.balance && income == that.income && outcome == that.outcome && Objects.equals(name, that.name) && Objects.equals(unitType, that.unitType) && Objects.equals(purpose, that.purpose) && Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitType, purpose, month, year, balance, income, outcome);
    }


}
