package depreciation.client.controller;

import depreciation.client.Main;
import depreciation.client.client.ContextHolder;
import depreciation.client.exception.ClientException;
import depreciation.client.util.JsonUtil;
import depreciation.dto.CompanyDTO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.Equipment;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static depreciation.client.util.AlertUtil.alert;
import static depreciation.client.util.AlertUtil.alertError;

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

        getAndSetCompany();

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
        currentPriceColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCurrentPrice() != null) {
                return new SimpleStringProperty(cellData.getValue().getCurrentPrice().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });

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
        if (company == null || firstOpened) {
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
        Dialog<Equipment> dialog = new Dialog<>();
        dialog.setTitle("New Equipment Dialog");

        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField title = new TextField();
        title.setPromptText("Title");
        TextField exploitationPeriodInMonth = new TextField();
        exploitationPeriodInMonth.setPromptText("Exploitation Period In Month");
        DatePicker exploitationStartDate = new DatePicker();
        TextField price = new TextField();
        price.setPromptText("Price");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("Exploitation Period In Month:"), 0, 1);
        grid.add(exploitationPeriodInMonth, 1, 1);
        grid.add(new Label("Exploitation Start Date:"), 0, 2);
        grid.add(exploitationStartDate, 1, 2);
        grid.add(new Label("Price :"), 0, 3);
        grid.add(price, 1, 3);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
            if (!newValue.trim().isEmpty()) {
                try {
                    Integer.parseInt(newValue.trim());
                } catch (NumberFormatException e) {
                    okButton.setDisable(true);
                }
            }

        });
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> title.requestFocus());

        dialog.setResultConverter(new Callback<ButtonType, Equipment>() {
                                      @Override
                                      public Equipment call(ButtonType buttonType) {
                                          if (buttonType == okButtonType) {
                                              Equipment equipment = new Equipment();
                                              equipment.setTitle(title.getText());
                                              LocalDate startDate = exploitationStartDate.getValue();
                                              if (startDate != null) {
                                                  Instant instant = Instant.from(startDate.atStartOfDay(ZoneId.systemDefault()));
                                                  Date date = Date.from(instant);
                                                  equipment.setExploitationStartDate(date);
                                              } else {
                                                  logger.warn("Exploitation start date not set, current date will be used!");
                                                  equipment.setExploitationStartDate(new Date());
                                              }
                                              equipment.setExploitationPeriodInMonth(Integer.parseInt(exploitationPeriodInMonth.getText()));
                                              equipment.setPrice(new BigDecimal(price.getText()));
                                              return equipment;
                                          }
                                          return null;
                                      }
                                  }

        );
        Optional<Equipment> result = dialog.showAndWait();
        if (result.isPresent()) {
            logger.debug("new Equipment Result is present!");
            logger.debug(result.get());

            company.addEquipment(new EquipmentWithCurrentPriceDTO(result.get()));
            addEquipmentToTable(new EquipmentWithCurrentPriceDTO(result.get(), result.get().getPrice()));
        } else {
            alertError("Wrong input!");
        }
    }

    @FXML
    void saveProject(ActionEvent event) {
        company.setTitle(titleTextField.getText().trim());
        company.setBusinessScope(scopeTextField.getText().trim());

        logger.debug("update company " + company);
        try {
            ContextHolder.getClient().sendRequest(new CommandRequest("UPDATE_COMPANY", JsonUtil.serialize(company)));
            CommandResponse response = ContextHolder.getLastResponse();
            if (response.getStatus().is2xxSuccessful()) {
                alert("Successfully updated the company, you can find it in 'Companies' tab!");
                openMyCompaniesView();
            } else {
                alert(Alert.AlertType.ERROR, "Cannot update company!", response.getBody());
            }
        } catch (ClientException e) {
            logger.error(e);
            alert(Alert.AlertType.ERROR, "Cannot update company!", e.getMessage());
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        openMyCompaniesView();
    }


    private void openMyCompaniesView() {
        MyCompaniesController.setFirstOpened(true);
        MyCompaniesController.setMain(main);
        main.showView("/view/myCompaniesView.fxml");
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
