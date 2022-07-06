package com.labstorageapp.entity;

import java.sql.Date;
import java.util.Objects;

public class SupplyEntity {
    private int idSupply;
    private int idConsumable;
    private int amount;
    private String provider;
    private Date date;

    public SupplyEntity(int idSupply, int idConsumable, int amount, String provider, Date date) {
        this.idSupply = idSupply;
        this.idConsumable = idConsumable;
        this.amount = amount;
        this.provider = provider;
        this.date = date;
    }

    public SupplyEntity(int idConsumable, int amount, String provider, Date date) {
        this.idSupply = -1;
        this.idConsumable = idConsumable;
        this.amount = amount;
        this.provider = provider;
        this.date = date;
    }

    public int getIdSupply() {
        return idSupply;
    }

    public void setIdSupply(int idSupply) {
        this.idSupply = idSupply;
    }

    public int getIdConsumable() {
        return idConsumable;
    }

    public void setIdConsumable(int idConsumable) {
        this.idConsumable = idConsumable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyEntity that = (SupplyEntity) o;
        return idSupply == that.idSupply && idConsumable == that.idConsumable && amount == that.amount && Objects.equals(provider, that.provider) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSupply, idConsumable, amount, provider, date);
    }

    @Override
    public String toString() {
        return "SupplyEntity{" +
                "idSupply=" + idSupply +
                ", idConsumable=" + idConsumable +
                ", amount=" + amount +
                ", provider='" + provider + '\'' +
                ", date=" + date +
                '}';
    }
}
