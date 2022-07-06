package com.labstorageapp.entity;

import java.sql.Date;
import java.util.Objects;

public class JournalEntity {
    private int idTake;
    private int idConsumable;
    private String nameConsumable;
    private String nameTaker;
    private String orgTaker;
    private String unitType;
    private int amount;
    private Date dateTaken;

    public JournalEntity(int idTake, int idConsumable, String nameConsumable, String nameTaker, String orgTaker, String unitType, int amount, Date dateTaken) {
        this.idTake = idTake;
        this.idConsumable = idConsumable;
        this.nameConsumable = nameConsumable;
        this.nameTaker = nameTaker;
        this.orgTaker = orgTaker;
        this.unitType = unitType;
        this.amount = amount;
        this.dateTaken = dateTaken;
    }

    public JournalEntity(int idConsumable, String nameConsumable, String nameTaker, String orgTaker, String unitType, int amount, Date dateTaken) {
        this.idConsumable = idConsumable;
        this.nameConsumable = nameConsumable;
        this.nameTaker = nameTaker;
        this.orgTaker = orgTaker;
        this.unitType = unitType;
        this.amount = amount;
        this.dateTaken = dateTaken;
    }

    public int getIdTake() {
        return idTake;
    }

    public void setIdTake(int idTake) {
        this.idTake = idTake;
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

    public String getNameTaker() {
        return nameTaker;
    }

    public void setNameTaker(String nameTaker) {
        this.nameTaker = nameTaker;
    }

    public String getOrgTaker() {
        return orgTaker;
    }

    public void setOrgTaker(String orgTaker) {
        this.orgTaker = orgTaker;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntity that = (JournalEntity) o;
        return idTake == that.idTake && idConsumable == that.idConsumable && amount == that.amount && Objects.equals(nameConsumable, that.nameConsumable) && Objects.equals(nameTaker, that.nameTaker) && Objects.equals(orgTaker, that.orgTaker) && Objects.equals(unitType, that.unitType) && Objects.equals(dateTaken, that.dateTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTake, idConsumable, nameConsumable, nameTaker, orgTaker, unitType, amount, dateTaken);
    }

    @Override
    public String toString() {
        return "JournalEntity{" +
                "idTake=" + idTake +
                ", idConsumable=" + idConsumable +
                ", nameConsumable='" + nameConsumable + '\'' +
                ", nameTaker='" + nameTaker + '\'' +
                ", orgTaker='" + orgTaker + '\'' +
                ", unitType='" + unitType + '\'' +
                ", amount=" + amount +
                ", dateTaken=" + dateTaken +
                '}';
    }
}
