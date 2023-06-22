package com.codetreatise.controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



@Controller
public class TiendaController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label tiendaId;

    @FXML
    private TextField cantidad;

    @FXML
    private TextField precio;

    @FXML
    private Button reset;

    @FXML
    private Button savePet;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private TiendaService tiendaService;

    // Atributos para la tienda
    @FXML
    private TextField nombre;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField cantidad;

    @FXML
    private TextField precio;

    @FXML
    private Button eliminar;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveTienda(ActionEvent event) {

        String descripcion = this.descripcion.getText();
        int cantidad = Integer.parseInt(this.cantidad.getText());
        double precio = Double.parseDouble(this.precio.getText());


        clearFields();

        loadTiendaDetails();
    }

    @FXML
    private void deleteTiendas(ActionEvent event) {

        loadTiendaDetails();
    }

    @FXML
    private void onClickProduct(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.SHOP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbRole.setItems(roles);

        setTiendaFields();


        loadTiendaDetails();
    }

    private void clearFields() {
        tiendaId.setText(null);
        descripcion.clear();
        cantidad.clear();
        precio.clear();
    }

    private void setTiendaFields() {

    }

    private void loadTiendaDetails() {

    }

}

