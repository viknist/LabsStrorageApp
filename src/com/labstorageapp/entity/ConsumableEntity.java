package com.labstorageapp.entity;

import java.util.Objects;

public class ConsumableEntity {
    private int id;
    private String name;
    private String unitType;
    private String purpose;

    public ConsumableEntity(int id, String name, String unitType, String purpose) {
        this.id = id;
        this.name = name;
        this.unitType = unitType;
        this.purpose = purpose;
    }

    public ConsumableEntity(String name, String unitType, String purpose) {
        this.id = -1;
        this.name = name;
        this.unitType = unitType;
        this.purpose = purpose;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumableEntity that = (ConsumableEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(unitType, that.unitType) && Objects.equals(purpose, that.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitType, purpose);
    }

    @Override
    public String toString() {
        return "ConsumableEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitType='" + unitType + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
