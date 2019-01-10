package depreciation.entity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Company implements DatabaseEntity {
    private int id;
    @NotBlank
    private String title;
    private Date foundationDate;
    private String businessScope;
    private List<Equipment> equipmentList;

    public Company() {
        equipmentList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void addEquipment(Equipment equipment) {
        equipmentList.add(equipment);
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id &&
                Objects.equals(title, company.title) &&
                Objects.equals(foundationDate, company.foundationDate) &&
                Objects.equals(equipmentList, company.equipmentList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, foundationDate, equipmentList);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", foundationDate=" + foundationDate +
                ", businessScope='" + businessScope + '\'' +
                ", equipmentList=" + equipmentList +
                '}';
    }
}
