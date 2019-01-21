package depreciation.dto;

import depreciation.entity.Company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CompanyDTO {
    private int id;
    private String title;
    private Date foundationDate;
    private String businessScope;
    private List<EquipmentWithCurrentPriceDTO> equipmentList;

    public CompanyDTO() {
        equipmentList = new ArrayList<>();
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.title = company.getTitle();
        this.foundationDate = company.getFoundationDate();
        this.businessScope = company.getBusinessScope();
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

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public List<EquipmentWithCurrentPriceDTO> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentWithCurrentPriceDTO> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void addEquipment(EquipmentWithCurrentPriceDTO equipment) {
        equipmentList.add(equipment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDTO that = (CompanyDTO) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(foundationDate, that.foundationDate) &&
                Objects.equals(businessScope, that.businessScope) &&
                Objects.equals(equipmentList, that.equipmentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, foundationDate, businessScope, equipmentList);
    }
}
