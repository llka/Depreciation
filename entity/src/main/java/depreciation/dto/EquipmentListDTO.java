package depreciation.dto;

import depreciation.entity.Equipment;

import java.util.List;
import java.util.Objects;

public class EquipmentListDTO {
    private List<Equipment> equipment;

    public EquipmentListDTO() {
    }

    public EquipmentListDTO(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentListDTO that = (EquipmentListDTO) o;
        return Objects.equals(equipment, that.equipment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(equipment);
    }

    @Override
    public String toString() {
        return "EquipmentListDTO{" +
                "equipment=" + equipment +
                '}';
    }
}
