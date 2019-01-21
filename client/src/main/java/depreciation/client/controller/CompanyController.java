package depreciation.client.controller;

import depreciation.client.Main;
import depreciation.dto.CompanyDTO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CompanyController {
    private static final Logger logger = LogManager.getLogger(CompanyController.class);

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
    private TableColumn<EquipmentWithCurrentPriceDTO, String> exploitationPeriodInMonthColumn;
    @FXML
    private TableColumn<EquipmentWithCurrentPriceDTO, Integer> idColumn;


    @FXML
    void onMouseEntered(ActionEvent event) {

    }

    @FXML
    void addNewJob(ActionEvent event) {

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
