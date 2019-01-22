package depreciation.backend.service;

import depreciation.backend.dao.EquipmentDAO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.Equipment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class EquipmentService {
    private static final Logger logger = LogManager.getLogger(EquipmentService.class);

    private static final int SCALE = 2;

    private EquipmentDAO equipmentDAO;

    public EquipmentWithCurrentPriceDTO calculateDepreciationByDate(Date date, Equipment equipment) {

        Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                equipment.getExploitationStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        logger.debug("start date: " + equipment.getExploitationStartDate());
//        logger.debug("now       : " + date);
//        logger.debug("period days " + period.getDays());
//        logger.debug("period month " + period.getMonths());

        int monthDelta = period.getMonths();

        BigDecimal currentPrice = equipment.getPrice();
        if (monthDelta < 0) {
            if (Math.abs(monthDelta) >= equipment.getExploitationPeriodInMonth()) {
                currentPrice = new BigDecimal(0);
            } else {
                logger.debug("price per month = " + equipment.getPrice()
                        .divide(new BigDecimal(equipment.getExploitationPeriodInMonth()), SCALE, RoundingMode.DOWN));
                currentPrice = equipment.getPrice()
                        .divide(new BigDecimal(equipment.getExploitationPeriodInMonth()), SCALE, RoundingMode.DOWN)
                        .multiply(new BigDecimal(equipment.getExploitationPeriodInMonth() - Math.abs(monthDelta)));
            }
        }

        EquipmentWithCurrentPriceDTO equipmentDTO = new EquipmentWithCurrentPriceDTO();
        equipmentDTO.setEquipment(equipment);
        equipmentDTO.setCurrentPrice(currentPrice);
        return equipmentDTO;
    }
}
