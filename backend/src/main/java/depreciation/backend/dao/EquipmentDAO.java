package depreciation.backend.dao;

import depreciation.backend.database.ConnectionPool;
import depreciation.backend.exception.ApplicationException;
import depreciation.entity.Equipment;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {
    private static final Logger logger = LogManager.getLogger(EquipmentDAO.class);

    private static final String SAVE = "INSERT INTO `equipment` (`title`, `price`, `exploitation_period_in_month`, `company_company_id`) " +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE `equipment` SET `title` = ?, `price` = ?, " +
            " `exploitation_period_in_month` = ? " +
            " WHERE `equipment_id` = ?";
    private static final String GET_BY_ID = "SELECT `equipment_id`, `title`, `price`, `exploitation_period_in_month` " +
            " FROM `equipment` WHERE `equipment_id` = ?";
    private static final String GET_ALL = "SELECT `equipment_id`, `title`, `price`, `exploitation_period_in_month` " +
            " FROM `equipment`";
    private static final String GET_COMPANY_EQUIPMENT = "SELECT `equipment_id`, `title`, `price`, `exploitation_period_in_month` " +
            " FROM `equipment` WHERE `company_company_id` = ?";
    private static final String DELETE = "DELETE FROM `job` WHERE `job_id`= ?";

    private static final String COLUMN_EQUIPMENT_ID = "equipment_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_EXPLOITATION_PERIOD = "exploitation_period_in_month";
    private static final String COLUMN_COMPANY_ID = "company_company_id";

    public EquipmentDAO() {
    }

    public Equipment save(@Valid Equipment equipment, Integer companyId) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, equipment.getTitle());
            preparedStatement.setBigDecimal(2, equipment.getPrice());
            preparedStatement.setInt(3, equipment.getExploitationPeriodInMonth());
            preparedStatement.setInt(4, companyId != null ? companyId : null);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new ApplicationException("Cannot save equipment. " + equipment, ResponseStatus.NOT_FOUND);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    equipment.setId(generatedKeys.getInt(1));
                } else {
                    throw new ApplicationException("Cannot save equipment, no ID obtained. " + equipment, ResponseStatus.NOT_FOUND);
                }
            }
            return equipment;

        } catch (SQLException e) {
            throw new ApplicationException("Cannot save equipment. " + equipment + ". " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public Equipment update(@Valid Equipment equipment) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, equipment.getTitle());
            preparedStatement.setBigDecimal(2, equipment.getPrice());
            preparedStatement.setInt(3, equipment.getExploitationPeriodInMonth());
            preparedStatement.setInt(4, equipment.getId());
            preparedStatement.execute();
            return equipment;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot update equipment. " + equipment + ". " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public Equipment getById(@Positive int id) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildEquipment(resultSet);
            } else {
                throw new ApplicationException("Equipment with id = " + id + " not found.", ResponseStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ApplicationException("Cannot get equipment with id = " + id + " in database." + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public List<Equipment> getAll() throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Equipment> equipmentList = new ArrayList<>();
            while (resultSet.next()) {
                equipmentList.add(buildEquipment(resultSet));
            }
            return equipmentList;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot get all equipment. " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public void deleteById(@Positive int id) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ApplicationException("Cannot delete equipment with id = " + id + " " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public List<Equipment> getCompanyEquipment(@Positive int companyId) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY_EQUIPMENT)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Equipment> equipmentList = new ArrayList<>();
            while (resultSet.next()) {
                equipmentList.add(buildEquipment(resultSet));
            }
            return equipmentList;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot get equipment for company with id = " + companyId + " " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    private Equipment buildEquipment(ResultSet resultSet) throws ApplicationException {
        try {
            Equipment equipment = new Equipment();
            equipment.setId(resultSet.getInt(COLUMN_EQUIPMENT_ID));
            equipment.setTitle(resultSet.getString(COLUMN_TITLE));
            equipment.setPrice(resultSet.getBigDecimal(COLUMN_PRICE));
            equipment.setExploitationPeriodInMonth(resultSet.getInt(COLUMN_EXPLOITATION_PERIOD));
            return equipment;
        } catch (SQLException e) {
            throw new ApplicationException("Error while building equipment! " + e, ResponseStatus.BAD_REQUEST);
        }
    }
}
