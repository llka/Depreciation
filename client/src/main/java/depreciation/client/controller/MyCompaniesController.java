package depreciation.client.controller;

import depreciation.client.Main;
import depreciation.client.client.ContextHolder;
import depreciation.client.exception.ClientException;
import depreciation.client.util.JsonUtil;
import depreciation.dto.CompanyListDTO;
import depreciation.entity.Company;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import static depreciation.client.util.AlertUtil.alert;

public class MyCompaniesController {
    private static final Logger logger = LogManager.getLogger(MyCompaniesController.class);

    private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd";
    private static boolean firstOpened = true;
    private static Main main;

    @FXML
    private TableView companiesTable;
    @FXML
    private TableColumn<Company, String> titleColumn;
    @FXML
    private TableColumn<Company, String> businessScopeColumn;
    @FXML
    private TableColumn<Company, String> equipmentDetailsColumn;
    @FXML
    private TableColumn<Company, String> foundationDateColumn;
    @FXML
    private TableColumn<Company, Integer> idColumn;


    @FXML
    private void initialize() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        logger.debug("initialize");

        equipmentDetailsColumn.setCellFactory(column -> new TableCell<Company, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                Button editButton = new Button("Equipment");
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    editButton.setOnAction(event -> {
                        TableRow row = getTableRow();
                        Company company = (Company) row.itemProperty().getValue();
                        logger.debug(company);
                        openEquipmentView(company);
                    });
                    setGraphic(editButton);
                    setText(null);
                }
            }
        });


        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        businessScopeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBusinessScope()));
        foundationDateColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFoundationDate() != null) {
                return new SimpleStringProperty(dateFormat.format(cellData.getValue().getFoundationDate()));
            } else {
                return new SimpleStringProperty("");
            }
        });

        if (firstOpened) {
            fillTable();
            firstOpened = false;
        }
    }

    private void fillTable() {
        try {
            ContextHolder.getClient().sendRequest(new CommandRequest("GET_CONTACT_COMPANIES"));
            CommandResponse response = ContextHolder.getLastResponse();
            if (response.getStatus().is2xxSuccessful()) {
                CompanyListDTO companyListDTO = JsonUtil.deserialize(response.getBody(), CompanyListDTO.class);
                companiesTable.setItems(FXCollections.observableArrayList(companyListDTO.getCompanyList()));
            } else {
                alert(Alert.AlertType.ERROR, "Cannot fill in table!", response.getBody());
            }
        } catch (ClientException e) {
            logger.error(e);
            alert(Alert.AlertType.ERROR, "Cannot get my companies!", e.getMessage());
        }
    }


    private void openEquipmentView(Company company) {
        CompanyController.setMain(main);
        CompanyController.setCompanyId(company.getId());
        CompanyController.setFirstOpened(true);
        main.showView("/view/companyView.fxml");
    }

    public static boolean isFirstOpened() {
        return firstOpened;
    }

    public static void setFirstOpened(boolean firstOpened) {
        MyCompaniesController.firstOpened = firstOpened;
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        MyCompaniesController.main = main;
    }
}
