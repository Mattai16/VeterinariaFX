package com.codetreatise.controller;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codetreatise.bean.Pet;
import com.codetreatise.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class PetController implements Initializable{

    @FXML
    private Button btnLogout;

    @FXML
    private Label petId;

    @FXML
    private TextField firstNamePet;

    @FXML
    private TextField lastNamePet;

    @FXML
    private DatePicker dobPet;

    @FXML
    private RadioButton rbMalePet;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbFemalePet;

    @FXML
    private ComboBox<String> cbRolePet;

    @FXML
    private TextField emailPet;

    @FXML
    private PasswordField passwordPet;

    @FXML
    private Button reset;

    @FXML
    private Button savePet;

    @FXML
    private TableView<Pet> petTable;

    @FXML
    private TableColumn<User, Long> colPetId;

    @FXML
    private TableColumn<User, String> colFirstNamePet;

    @FXML
    private TableColumn<User, String> colLastNamePet;

    @FXML
    private TableColumn<User, LocalDate> colDOBPet;

    @FXML
    private TableColumn<User, String> colGenderPet;

    @FXML
    private TableColumn<User, String> colRolePet;

    @FXML
    private TableColumn<User, String> colEmailPet;

    @FXML
    private TableColumn<Pet, Boolean> colEditPet;

    @FXML
    private MenuItem deletePet;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private PetService petService;

    private ObservableList<Pet> petList = FXCollections.observableArrayList();
    private ObservableList<String> roles = FXCollections.observableArrayList("Macho", "Embra");

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void savePet(ActionEvent event){

        if(validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+") &&
                emptyValidation("DOB", dobPet.getEditor().getText().isEmpty()) &&
                emptyValidation("Role", getRole() == null) ){

            if(petId.getText() == null || petId.getText() == ""){
                if(validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                        emptyValidation("Password", getPassword().isEmpty())){

                    Pet pet = new Pet();
                    pet.setFirstName(getFirstName());
                    pet.setLastName(getLastName());
                    pet.setDob(getDob());
                    pet.setGender(getGender());
                    pet.setRole(getRole());
                    pet.setEmail(getEmail());
                    pet.setPassword(getPassword());

                    Pet newPet = petService.save(pet);

                    saveAlert(newPet);
                }

            }else{
                Pet pet = petService.find(Long.parseLong(petId.getText()));
                pet.setFirstName(getFirstName());
                pet.setLastName(getLastName());
                pet.setDob(getDob());
                pet.setGender(getGender());
                pet.setRole(getRole());
                Pet updatedPet =  petService.update(pet);
                updateAlert(updatedPet);
            }

            clearFields();
            loadPetsDetails();
        }


    }

    @FXML
    private void deletePets(ActionEvent event){
        List<Pet> pets = petTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK) petService.deleteInBatch(pets);

        loadPetsDetails();
    }

    private void clearFields() {
        petId.setText(null);
        firstNamePet.clear();
        lastNamePet.clear();
        dobPet.getEditor().clear();
        rbMalePet.setSelected(true);
        rbFemalePet.setSelected(false);
        cbRolePet.getSelectionModel().clearSelection();
        emailPet.clear();
        passwordPet.clear();
    }

    private void saveAlert(Pet user){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user "+user.getFirstName()+" "+user.getLastName() +" has been created and \n"+getGenderTitle(user.getGender())+" id is "+ user.getId() +".");
        alert.showAndWait();
    }

    private void updateAlert(Pet user){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user "+user.getFirstName()+" "+user.getLastName() +" has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender){
        return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFirstName() {
        return firstNamePet.getText();
    }

    public String getLastName() {
        return lastNamePet.getText();
    }

    public LocalDate getDob() {
        return dobPet.getValue();
    }

    public String getGender(){
        return rbMalePet.isSelected() ? "Male" : "Female";
    }

    public String getRole() {
        return cbRolePet.getSelectionModel().getSelectedItem();
    }

    public String getEmail() {
        return emailPet.getText();
    }

    public String getPassword() {
        return passwordPet.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbRolePet.setItems(roles);

        petTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadPetsDetails();
    }



    /*
     *  Set All userTable column properties
     */
    private void setColumnProperties(){
		/* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/


            colPetId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colFirstNamePet.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colLastNamePet.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colDOBPet.setCellValueFactory(new PropertyValueFactory<>("dob"));
            colGenderPet.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colRolePet.setCellValueFactory(new PropertyValueFactory<>("role"));
            colEmailPet.setCellValueFactory(new PropertyValueFactory<>("email"));
            colEditPet.setCellFactory(cellFactory);


    }

    Callback<TableColumn<Pet, Boolean>, TableCell<Pet, Boolean>> cellFactory =
            new Callback<TableColumn<Pet, Boolean>, TableCell<Pet, Boolean>>()
            {
                @Override
                public TableCell<Pet, Boolean> call( final TableColumn<Pet, Boolean> param)
                {
                    final TableCell<Pet, Boolean> cell = new TableCell<Pet, Boolean>()
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty)
                        {
                            super.updateItem(check, empty);
                            if(empty)
                            {
                                setGraphic(null);
                                setText(null);
                            }
                            else{
                                btnEdit.setOnAction(e ->{
                                    Pet pet = getTableView().getItems().get(getIndex());
                                    updatePet(pet);
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void updatePet(Pet pet) {
                            petId.setText(Long.toString(pet.getId()));
                            firstNamePet.setText(pet.getFirstName());
                            lastNamePet.setText(pet.getLastName());
                            dobPet.setValue(pet.getDob());
                            if(pet.getGender().equals("Male")) rbMalePet.setSelected(true);
                            else rbFemalePet.setSelected(true);
                            cbRolePet.getSelectionModel().select(pet.getRole());
                        }
                    };
                    return cell;
                }
            };



    /*
     *  Add All users to observable list and update table
     */
    private void loadPetsDetails(){
        petList.clear();
        petList.addAll(petService.findAll());

        petTable.setItems(petList);
    }

    /*
     * Validations
     */
    private boolean validate(String field, String value, String pattern){
        if(!value.isEmpty()){
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if(m.find() && m.group().equals(value)){
                return true;
            }else{
                validationAlert(field, false);
                return false;
            }
        }else{
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty){
        if(!empty){
            return true;
        }else{
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if(field.equals("Role")) alert.setContentText("Please Select "+ field);
        else{
            if(empty) alert.setContentText("Please Enter "+ field);
            else alert.setContentText("Please Enter Valid "+ field);
        }
        alert.showAndWait();
    }

    @FXML
    private void onClickReturn(ActionEvent event) throws IOException{

        stageManager.switchScene(FxmlView.USER);


    }
}
