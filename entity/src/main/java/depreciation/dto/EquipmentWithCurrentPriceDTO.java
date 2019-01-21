package depreciation.dto;

import depreciation.entity.Equipment;

import java.math.BigDecimal;

public class EquipmentWithCurrentPriceDTO{
    private Equipment equipment;
    private BigDecimal currentPrice;

    public EquipmentWithCurrentPriceDTO() {
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
}
