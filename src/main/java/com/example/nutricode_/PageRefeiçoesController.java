package com.example.nutricode_;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PageRefeiçoesController implements Initializable {

    @FXML
    private TableView<RefeicaoData> tableRefeiçoes;
    private DashboardController dashboardController= new DashboardController();
    private ObservableList<RefeicaoData> listaRefeiçoes;
    @FXML
    private TableColumn<RefeicaoData, String> nomeRefeiçao;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addRefeiçoesTable();
    }

    public void addRefeiçoesTable(){
        listaRefeiçoes = dashboardController.addRefeiçaoList();


        nomeRefeiçao.setCellValueFactory(new PropertyValueFactory<>("nome"));


        tableRefeiçoes.setItems(listaRefeiçoes);

    }

}
