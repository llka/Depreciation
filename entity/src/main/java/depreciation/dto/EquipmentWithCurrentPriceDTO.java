package depreciation.dto;

import depreciation.entity.Equipment;

import java.math.BigDecimal;
import java.util.Objects;

public class EquipmentWithCurrentPriceDTO{
    private Equipment equipment;
    private BigDecimal currentPrice;

    public EquipmentWithCurrentPriceDTO() {
    }

    public EquipmentWithCurrentPriceDTO(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentWithCurrentPriceDTO(Equipment equipment, BigDecimal currentPrice) {
        this.equipment = equipment;
        this.currentPrice = currentPrice;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentWithCurrentPriceDTO that = (EquipmentWithCurrentPriceDTO) o;
        return Objects.equals(equipment, that.equipment) &&
                Objects.equals(currentPrice, that.currentPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(equipment, currentPrice);
    }
}
