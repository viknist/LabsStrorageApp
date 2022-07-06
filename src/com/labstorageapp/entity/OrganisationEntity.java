package com.labstorageapp.entity;

import java.util.Objects;

public class OrganisationEntity {
    private int idOrg;
    private String nameOrg;

    public OrganisationEntity(int idOrg, String nameOrg) {
        this.idOrg = idOrg;
        this.nameOrg = nameOrg;
    }

    public OrganisationEntity(String nameOrg) {
        this.idOrg = -1;
        this.nameOrg = nameOrg;
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

    @Override
    public String toString() {
        return "OrganisationEntity{" +
                "idOrg=" + idOrg +
                ", nameOrg='" + nameOrg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationEntity that = (OrganisationEntity) o;
        return idOrg == that.idOrg && Objects.equals(nameOrg, that.nameOrg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrg, nameOrg);
    }
}
