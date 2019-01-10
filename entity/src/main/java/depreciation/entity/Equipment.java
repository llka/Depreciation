package depreciation.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Equipment implements DatabaseEntity {
    private int id;
    private int exploitationPeriodInMonth;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id &&
                exploitationPeriodInMonth == equipment.exploitationPeriodInMonth &&
                Objects.equals(price, equipment.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, exploitationPeriodInMonth, price);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", exploitationPeriodInMonth=" + exploitationPeriodInMonth +
                ", price=" + price +
                '}';
    }
}
