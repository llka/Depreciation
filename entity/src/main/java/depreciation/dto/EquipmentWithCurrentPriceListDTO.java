package depreciation.dto;

import java.util.ArrayList;
import java.util.List;

public class EquipmentWithCurrentPriceListDTO {
    private List<EquipmentWithCurrentPriceDTO> equipmentList;

    public EquipmentWithCurrentPriceListDTO() {
        equipmentList = new ArrayList<>();
    }

    public EquipmentWithCurrentPriceListDTO(List<EquipmentWithCurrentPriceDTO> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<EquipmentWithCurrentPriceDTO> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentWithCurrentPriceDTO> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void addEquipment(EquipmentWithCurrentPriceDTO equipmentWithCurrentPriceDTO) {
        equipmentList.add(equipmentWithCurrentPriceDTO);
    }
}
