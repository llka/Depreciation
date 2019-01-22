package depreciation.client.controller;

import depreciation.client.Main;
import depreciation.client.client.ContextHolder;
import depreciation.client.exception.ClientException;
import depreciation.client.util.JsonUtil;
import depreciation.dto.CompanyDTO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static depreciation.client.util.AlertUtil.alert;

public class CompanyController {
    private static final Logger logger = LogManager.getLogger(CompanyController.class);
    private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd";
    private static boolean firstOpened = true;

    private static int companyId;
    private static CompanyDTO company;
    private static Main main;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField foundationDateTextField;
    @FXML
    private TextField scopeTextField;
    @FXML
    private TextField titleTextField;

    @FXML
    private Button addNewEquipmentBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    @FXML
    private TableView equipmentTable;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, String> currentPriceColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, String> exploitationStartDateColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, String> titleColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, String> initialPriceColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, Integer> exploitationPeriodInMonthColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, Integer> idColumn;


    @FXML
    private void initialize() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        logger.debug("initialize");
        logger.debug(companyId);

        if (company == null) {
            getAndSetCompany();
        }

        idTextField.setText(String.valueOf(company.getId()));
        foundationDateTextField.setText(dateFormat.format(company.getFoundationDate()));
        scopeTextField.setText(company.getBusinessScope());
        titleTextField.setText(company.getTitle());


        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEquipment().getId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipment().getTitle()));
        initialPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipment().getPrice().toString()));
        exploitationPeriodInMonthColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEquipment().getExploitationPeriodInMonth()).asObject());
        exploitationStartDateColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getEquipment().getExploitationStartDate() != null) {
                return new SimpleStringProperty(dateFormat.format(cellData.getValue().getEquipment().getExploitationStartDate()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        currentPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentPrice().toString()));

        if (firstOpened) {
            fillEquipmentTable();
            firstOpened = false;
        }
    }

    private void fillEquipmentTable() {
        equipmentTable.setItems(FXCollections.observableArrayList(company.getEquipmentList()));
    }

    private void addEquipmentToTable(EquipmentWithCurrentPriceDTO equipment) {
        ObservableList<EquipmentWithCurrentPriceDTO> currentList = equipmentTable.getItems();
        currentList.add(equipment);
        equipmentTable.setItems(currentList);
    }

    private void getAndSetCompany() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("companyId", String.valueOf(companyId));
            ContextHolder.getClient().sendRequest(new CommandRequest("GET_COMPANY", params));
            CommandResponse response = ContextHolder.getLastResponse();
            if (response.getStatus().is2xxSuccessful()) {
                CompanyDTO companyDTO = JsonUtil.deserialize(response.getBody(), CompanyDTO.class);
                setCompany(companyDTO);
            } else {
                alert(Alert.AlertType.ERROR, "Cannot get company DTO!", response.getBody());
            }
        } catch (ClientException e) {
            logger.error(e);
            alert(Alert.AlertType.ERROR, "Cannot get company DTO!", e.getMessage());
        }
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);

        logger.debug("initialize-onMouseEntered");
        if (company == null) {
            getAndSetCompany();
        }
        logger.debug(company);

        idTextField.setText(String.valueOf(company.getId()));
        foundationDateTextField.setText(dateFormat.format(company.getFoundationDate()));
        scopeTextField.setText(company.getBusinessScope());
        titleTextField.setText(company.getTitle());

        fillEquipmentTable();
    }

    @FXML
    void addNewEquipment(ActionEvent event) {

    }

    @FXML
    void saveProject(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }


    public static boolean isFirstOpened() {
        return firstOpened;
    }

    public static void setFirstOpened(boolean firstOpened) {
        CompanyController.firstOpened = firstOpened;
    }

    public static int getCompanyId() {
        return companyId;
    }

    public static void setCompanyId(int companyId) {
        CompanyController.companyId = companyId;
    }

    public static CompanyDTO getCompany() {
        return company;
    }

    public static void setCompany(CompanyDTO company) {
        CompanyController.company = company;
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        CompanyController.main = main;
    }
}
