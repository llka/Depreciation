package depreciation.backend.service;

import depreciation.backend.dao.EquipmentDAO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.Equipment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class EquipmentService {
    private static final Logger logger = LogManager.getLogger(CompanyService.class);

    private EquipmentDAO equipmentDAO;

    public EquipmentWithCurrentPriceDTO calculateDepreciationByDate(Date date, Equipment equipment) {
        Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                equipment.getExploitationStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        logger.debug("period " + period);
        logger.debug("period days" + period.getDays());
        logger.debug("period month" + period.getMonths());


        EquipmentWithCurrentPriceDTO equipmentDTO = new EquipmentWithCurrentPriceDTO();
        equipmentDTO.setEquipment(equipment);
        //equipmentDTO.setCurrentPrice();
        return equipmentDTO;
    }
}
