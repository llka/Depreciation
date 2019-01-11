package depreciation.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class Equipment implements DatabaseEntity {
    private int id;
    @NotBlank
    private String title;
    @NotNull
    @Positive
    private int exploitationPeriodInMonth;
    @NotNull
    private BigDecimal price;

    public Equipment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExploitationPeriodInMonth() {
        return exploitationPeriodInMonth;
    }

    public void setExploitationPeriodInMonth(int exploitationPeriodInMonth) {
        this.exploitationPeriodInMonth = exploitationPeriodInMonth;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id &&
                exploitationPeriodInMonth == equipment.exploitationPeriodInMonth &&
                Objects.equals(title, equipment.title) &&
                Objects.equals(price, equipment.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, exploitationPeriodInMonth, price);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", exploitationPeriodInMonth=" + exploitationPeriodInMonth +
                ", price=" + price +
                '}';
    }
}
