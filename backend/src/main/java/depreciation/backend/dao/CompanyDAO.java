package depreciation.backend.dao;

import depreciation.backend.database.ConnectionPool;
import depreciation.backend.exception.ApplicationException;
import depreciation.entity.Company;
import depreciation.entity.Contact;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    private static final Logger logger = LogManager.getLogger(CompanyDAO.class);
    private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String SAVE = "INSERT INTO `company`(`title`, `foundation_date`, `business_scope`, `contact_contact_id`) " +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE `company` SET `title` = ?, `foundation_date` = ?, `business_scope` = ?, " +
            " WHERE `company_id` = ?";

    private static final String GET_BY_ID = "SELECT `company_id`, `title`, `foundation_date`, `business_scope`, `contact_contact_id` " +
            " FROM `company` WHERE `company_id` = ?";
    private static final String GET_ALL = "SELECT `company_id`, `title`, `foundation_date`, `business_scope`, `contact_contact_id` " +
            " FROM `company` ";
    private static final String GET_CONTACT_COMPANIES = "SELECT `company_id`, `title`, `foundation_date`, `business_scope`, `contact_contact_id` " +
            " FROM `company` WHERE `contact_id` = ?";
    private static final String DELETE = "DELETE FROM `company` WHERE `company_id`= ?";

    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_FOUDATION_DATE = "foundation_date";
    private static final String COLUMN_CONTACT_ID = "contact_contact_id";
    private static final String COLUMN_BUSINESS_SCOPE = "business_scope";

    private EquipmentDAO equipmentDAO;


    public CompanyDAO() {
        this.equipmentDAO = new EquipmentDAO();
    }

    public Company save(@Valid Company company, @Valid Contact contact) throws ApplicationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.getTitle());
            if (company.getFoundationDate() != null) {
                preparedStatement.setString(2, dateFormat.format(company.getFoundationDate()));
            }
            preparedStatement.setString(3, company.getBusinessScope());
            if (contact != null) {
                preparedStatement.setInt(4, contact.getId());
            } else {
                throw new ApplicationException("Cannot create company without contact!", ResponseStatus.BAD_REQUEST);
            }

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new ApplicationException("Cannot save company. " + company, ResponseStatus.NOT_FOUND);
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    company.setId(generatedKeys.getInt(1));
                } else {
                    throw new ApplicationException("Cannot save company, no ID obtained. " + company, ResponseStatus.NOT_FOUND);
                }
            }
            return company;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot save company. " + company + ". " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public Company update(@Valid Company company) throws ApplicationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, company.getTitle());
            if (company.getFoundationDate() != null) {
                preparedStatement.setString(2, dateFormat.format(company.getFoundationDate()));
            }
            preparedStatement.setString(3, company.getBusinessScope());
            preparedStatement.setInt(4, company.getId());

            preparedStatement.execute();
            return company;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot update company. " + company + ". " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public Company getById(@Positive int id) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildCompany(resultSet);
            } else {
                throw new ApplicationException("Company with id = " + id + " not found.", ResponseStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ApplicationException("Cannot find company with id = " + id + " in database." + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public List<Company> getAll() throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(buildCompany(resultSet));
            }
            return companies;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot get all companies. " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public List<Company> getContactCompanies(@Positive int contactId) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CONTACT_COMPANIES)) {
            preparedStatement.setInt(1, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(buildCompany(resultSet));
            }
            return companies;
        } catch (SQLException e) {
            throw new ApplicationException("Cannot get contact companies. " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    public void deleteById(@Positive int id) throws ApplicationException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ApplicationException("Cannot delete company with id = " + id + " " + e, ResponseStatus.BAD_REQUEST);
        }
    }

    private Company buildCompany(ResultSet resultSet) throws ApplicationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        try {
            Company company = new Company();
            company.setId(resultSet.getInt(COLUMN_COMPANY_ID));
            company.setTitle(resultSet.getString(COLUMN_TITLE));
            company.setBusinessScope(resultSet.getString(COLUMN_BUSINESS_SCOPE));
            try {
                if (resultSet.getDate(COLUMN_FOUDATION_DATE) != null) {
                    company.setFoundationDate(dateFormat.parse(resultSet.getString(COLUMN_FOUDATION_DATE)));
                }
            } catch (ParseException exception) {
                logger.error(exception);
            }
            company.setEquipmentList(equipmentDAO.getCompanyEquipment(company.getId()));
            return company;
        } catch (SQLException e) {
            throw new ApplicationException("Error while building company! " + e, ResponseStatus.BAD_REQUEST);
        }
    }
}
