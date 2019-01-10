package depreciation.dto;

import depreciation.entity.Company;

import java.util.List;
import java.util.Objects;

public class CompanyListDTO {
    private List<Company> companyList;

    public CompanyListDTO() {
    }

    public CompanyListDTO(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyListDTO that = (CompanyListDTO) o;
        return Objects.equals(companyList, that.companyList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(companyList);
    }

    @Override
    public String toString() {
        return "CompanyListDTO{" +
                "companyList=" + companyList +
                '}';
    }
}
