package com.example.nutricode_;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController  {
    @FXML
    private TextField nomeNovoUsuario,cpfNovoUsuario,pesoNovoUsuario,alturaNovoUsuario,loginNovoUsuario;
    @FXML
    private PasswordField senhaNovoUsuario;

    @FXML
    private AnchorPane loginPage,registroPage;
    @FXML
    private Label label;

    @FXML
    private Button close;

    @FXML
    private TextField login;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField senha;

    @FXML
    public Label nomeUsuario;


    // Interaçoes com o Banco De Dados -- interactions with the database

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private  double x=0;
    private double y=0;
    private static Usuario user;


    public static Usuario getUser(){
        return user;
    }

    public void loginUsuario() {
        String sql = "SELECT * FROM logins WHERE usuario = ? and senha = ?";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, login.getText());
            prepare.setString(2, senha.getText());
            result = prepare.executeQuery();
            Alert alert;
            if (login.getText().isEmpty() || senha.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Campos obrigatórios");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    user= new Usuario(result.getInt("id_usuario"),result.getString("nome"),result.getString("usuario"),result.getString("senha"),
                            result.getString("cpf"),result.getFloat("peso"),result.getDouble("altura") );
                    loginButton.getScene().getWindow().hide();
                    System.out.println(user.getAltura());
                    System.out.println(user.getCpf());
                    Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                    Stage stage= new Stage();
                    Scene scene= new Scene(root);

                    root.setOnMousePressed((MouseEvent event)-> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });
                    root.setOnMouseDragged((MouseEvent event)-> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuário ou Senha incorretos");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registra(){
        String sql = "INSERT INTO logins "
                + "(usuario,senha,nome,peso,altura,cpf)"
                + "VALUES(?,?,?,?,?,?)";
        connect = Database.connectDb();
        Alert alert;
        try{
            if(existeUsuario(loginNovoUsuario.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de registro");
                alert.setHeaderText(null);
                alert.setContentText("O usuario: "+loginNovoUsuario.getText()+" já existe");
                alert.showAndWait();
                return;
            }
            if(loginNovoUsuario.getText().isEmpty() || senhaNovoUsuario.getText().isEmpty() || nomeNovoUsuario.getText().isEmpty() ||
                alturaNovoUsuario.getText().isEmpty() || cpfNovoUsuario.getText().isEmpty()){
                alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campos em branco");
                alert.setHeaderText(null);
                alert.setContentText("Todos os campos são obrigatórios");
                alert.showAndWait();
                return;
            }
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,loginNovoUsuario.getText());
            prepare.setString(2,senhaNovoUsuario.getText());
            prepare.setString(3,nomeNovoUsuario.getText());
            prepare.setString(4,pesoNovoUsuario.getText());
            prepare.setString(5,alturaNovoUsuario.getText());
            prepare.setString(6,cpfNovoUsuario.getText());
            prepare.executeUpdate();
            registroPage.setVisible(false);
            loginPage.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getToRegistraPage(){
        loginPage.setVisible(false);
        registroPage.setVisible(true);
    }

    public void getToLoginPage(){
        registroPage.setVisible(false);
        loginPage.setVisible(true);
    }

    public boolean existeUsuario(String usuario){
        boolean existe=false;
        String sql = "SELECT * FROM logins";
        connect= Database.connectDb();
        ArrayList<String> usuarios = new ArrayList<>();
        try{
            statement= connect.createStatement();
            result= statement.executeQuery(sql);
            while(result.next()){
                usuarios.add(result.getString("usuario"));
            }
            if(usuarios.contains(usuario)){
                existe= true;
            }
            else {
                existe= false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return existe;
    }


    public void close(){
        System.exit(0);
    }
    @FXML
    protected void onHelloButtonClick() {
        label.setText("Welcome to JavaFX Application!");
    }
}