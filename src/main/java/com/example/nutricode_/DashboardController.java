package com.example.nutricode_;


import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Line;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.property.TextAlignment;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;



import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import static com.itextpdf.kernel.PdfException.PdfEncodings;

public class DashboardController implements Initializable {

    @FXML
    private Button segundaButton,terçaButton,quartaButton,quintaButton,sextaButton,sabadoButton,domingoButton;

    @FXML
    private CheckBox checkDomingo,checkQuarta,checkQuinta,checkSabado,checkSegunda,checkSexta,checkTerça;
    @FXML
    private TextField nomeRefeiçaoAdc;

    @FXML
    private Label qntRefeiçoesDia;
    @FXML
    private Button homeButton,paginaRefeiçoesButton,catalogoAlimentosButton;
    @FXML
    private AnchorPane homePage, refeiçoesPage;

    @FXML
    private AnchorPane cenaPrincipal;

    @FXML
    private Label caloriasPane;

    @FXML
    private Label caloriasDia;
    @FXML
    private Label fibrasPane;

    @FXML
    private Label carboPane;
    @FXML
    private Label proteinasPane;
    @FXML
    private Label GordurasPane;
    @FXML
    private ImageView imagemAlimento;

    @FXML
    private AnchorPane alimentosPage;
    @FXML
    private TextField filtraAlimentos;


    @FXML
    private TextField filtraRefeiçoes;

    @FXML
    private Label carboidratosDia;

    @FXML
    private Label fibrasDia;
    @FXML
    private Label proteinasDia;

    @FXML
    private Label gordurasDia;

    private float carboidratosDiaF=0;
    private float caloriasDiaF=0;
    private float fibrasDiaF=0;
    private float gordurasDiaF=0;
    private float proteinasDiaF=0;

    @FXML
    private FontAwesomeIconView logout;

    @FXML
    private Label mediaCal;

    @FXML
    private Label mediaCarbo;

    @FXML
    private Label mediaProt;

    @FXML
    private Label nomeUsuario;


    @FXML
    private Button logoutButton;

    @FXML
    private PieChart graficoNutrientes;

    private Usuario user;

    @FXML
    private TableView<RefeicaoData> tabelaRefeiçoesDia;
    @FXML
    private TableView<AlimentoData> tabelaAlimentos;
    @FXML
    private TableColumn<?, ?> nomeAlimento,carboAlimento,proteinasAlimento,gordurasAlimento,fibrasAlimento,caloriasAlimento;
    private ObservableList<RefeicaoData> listaRefeiçoesDia;
    private ObservableList<AlimentoData> listaAlimentos;
    @FXML
    private TableColumn<RefeicaoData, String> nomeRefeiçaoDia;

    @FXML
    private TableColumn<RefeicaoData, String> a1Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a2Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a3Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a4Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a5Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a6Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a7Dia;

    @FXML
    private TableColumn<RefeicaoData, String> a8Dia;
    @FXML
    private FontAwesomeIconView a1icon,a2icon,a3icon,a4icon,a5icon,a6icon,a7icon,a8icon;

    @FXML
    private TextField a1add,a2add,a3add,a4add,a5add,a6add,a7add,a8add;


    @FXML
    private TextField a1peso,a2peso,a3peso,a4peso,a5peso,a6peso,a7peso,a8peso;

    @FXML
    private TextField nomeNovaRefeiçao;

    @FXML
    private Label a1Pane;
    @FXML
    private Label a2Pane;
    @FXML
    private Label a3Pane;

    @FXML
    private Label a4Pane;
    @FXML
    private Label a5Pane;
    @FXML
    private Label a6Pane;
    @FXML
    private Label a7Pane;
    @FXML
    private Label a8Pane;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    @FXML
    private Label tituloPane;

    @FXML
    private TableView<RefeicaoData> tableRefeiçoes;
    private ObservableList<RefeicaoData> listaRefeiçoes;
    @FXML
    private TableColumn<RefeicaoData, String> nomeRefeiçao;
    @FXML
    private Label nomeAlimentoDescriçao;
    @FXML
    private TextField nomeAlimentoSolicitado;

    private   ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Proteínas",0),
            new PieChart.Data("Carboidratos", 0),
            new PieChart.Data("Gorduras", 0),
            new PieChart.Data("Fibras", 0));



    private double x,y=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        segundaButton.requestFocus(); // Define o foco no botão "segundaButton"
        setUser();
        defineNomeUser();
        addAlimentosTable();
        addRefeiçoesTable();
        resetNutrientesDia();
        initListaDeSugestoesRefeiçoes(nomeRefeiçaoAdc);
        pressElementTableAlimentos();
        pressElementTable();
        listaRefeiçoesFiltrada();
        listaAlimentosFiltrado();
        initListaDeSugestoes(a1add);
        initListaDeSugestoes(a2add);
        initListaDeSugestoes(a3add);
        initListaDeSugestoes(a4add);
        initListaDeSugestoes(a5add);
        initListaDeSugestoes(a6add);
        initListaDeSugestoes(a7add);
        initListaDeSugestoes(a8add);
        contemApenasDigitos(a1peso);
        contemApenasDigitos(a2peso);
        contemApenasDigitos(a3peso);
        contemApenasDigitos(a4peso);
        contemApenasDigitos(a5peso);
        contemApenasDigitos(a6peso);
        contemApenasDigitos(a7peso);
        contemApenasDigitos(a8peso);
        initGrafico();
        initInfosCard();
        segundaButton.fire();
        segundaButton.setStyle("-fx-background-color: #6b0101");
    }

    public void geraRelatorio(){
        try{
            Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
            String filePath = desktopPath.resolve("RelatorioNutricode.pdf").toString();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filePath));
            Document doc = new Document(pdfDoc);

            Path concertONeFontPath = Paths.get("src/main/resources/com/example/nutricode_/ConcertOne-Regular.ttf"); // Substitua pelo caminho correto
            // Criação da fonte Tahoma
            PdfFont fontTitulo = PdfFontFactory.createFont(concertONeFontPath.toString(), true);
            float fontSize = 24;
            Paragraph title = new Paragraph("Nutricode")
                    .setFont(fontTitulo)
                    .setFontSize(fontSize)
                    .setTextAlignment(TextAlignment.CENTER);
            // Adicionar conteúdo ao documento
            Path latoFontPath = Paths.get("src/main/resources/com/example/nutricode_/Lato-Regular.ttf"); // Substitua pelo caminho correto
            // Criação da fonte Tahoma
            PdfFont fontBody = PdfFontFactory.createFont(latoFontPath.toString(), true);
            doc.add(title);
            doc.add(new Paragraph("Usuário: "+user.getNome()
                            +"\nCPF: 066.300.405-05"))
                    .setFont(fontBody);
            System.out.println("Relatório PDF gerado com sucesso.");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void solicitaAlimento(){
        String sql = "INSERT INTO solicitaçoes"
                +"(nomeALimento)"
                +"VALUES(?)";
        connect= Database.connectDb();
        Alert alert;
        try{
            prepare= connect.prepareStatement(sql);
            prepare.setString(1, nomeAlimentoSolicitado.getText());
            prepare.executeUpdate();
            alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Alimento solicitado com sucesso!");
            alert.showAndWait();
            nomeAlimentoSolicitado.setText("");
        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    public void initInfosCard(){
        try{
            String sql= "SELECT nome_refeiçao FROM refeiçao_dia";
            ObservableList<RefeicaoData> todasRefeiçoesEmUso= FXCollections.observableArrayList();
            connect= Database.connectDb();
            prepare= connect.prepareStatement(sql);
            result= prepare.executeQuery();
            float carboidratosTotais= 0;
            float caloriasTotais=0;
            float proteinasTotais=0;
            String nomeRefeiçao;
            while(result.next()){
                nomeRefeiçao= result.getString("nome_refeiçao");
                for(RefeicaoData r: listaRefeiçoes){
                    if(r.getNome().equalsIgnoreCase(nomeRefeiçao)){
                        carboidratosTotais+= r.getCarboidratos();
                        caloriasTotais+= r.getCalorias();
                        proteinasTotais+= r.getProteinas();
                        todasRefeiçoesEmUso.add(r);
                    }
                }
            }
            float mediaProteinas = proteinasTotais/7;
            float mediaCalorias= caloriasTotais/7;
            float mediaCarboidratos = carboidratosTotais/7;
            mediaCarbo.setText(String.format("%.1f", mediaCarboidratos));
            mediaProt.setText(String.format("%.1f", mediaProteinas));
            mediaCal.setText(String.format("%.1f", mediaCalorias));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Meotodo que verifica se o textField que captura os pesos de cada alimento possui apenas dígitos
     * @param textField
     */
    private void contemApenasDigitos(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Se o campo de texto perdeu o foco
                if (!textField.getText().matches("[0-9]+")) { // Se o texto atual não estiver na lista de sugestões
                    textField.setText(""); // Limpar o campo de texto
                }
            }
        });
        // Verifica se o texto contém apenas dígitos de 0 a 9
    }

    public void initListaDeSugestoesRefeiçoes(TextField textField){
        ArrayList<String> refeiçoesAutoComp= new ArrayList<>();
        for(RefeicaoData r: listaRefeiçoes) {
            refeiçoesAutoComp.add(r.getNome());
        }
            TextFields.bindAutoCompletion(textField,refeiçoesAutoComp);
            textField.focusedProperty().addListener((observable,oldValue,newValue)-> {
                if(!newValue){
                    String textoAtual = textField.getText();
                    if(!refeiçoesAutoComp.contains(textoAtual)){
                        textField.setText("");
                    }
                }
            });

    }

    /**
     * Método que implementa a lista de elementos filtrada ao digitar nos texts fields de alimentos.
     * @param textField
     */
    public void initListaDeSugestoes(TextField textField){
        ArrayList<String> alimentosAutoComp = new ArrayList<>() ;
        for(AlimentoData a: listaAlimentos){
            alimentosAutoComp.add(a.getNome());
        }
        TextFields.bindAutoCompletion(textField,alimentosAutoComp);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Se o campo de texto perdeu o foco
                String textoAtual = textField.getText();
                if (!alimentosAutoComp.contains(textoAtual)) { // Se o texto atual não estiver na lista de sugestões
                    textField.setText(""); // Limpar o campo de texto
                }
            }
        });
        TextFields.bindAutoCompletion(a2add,alimentosAutoComp);
        a2add.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Se o campo de texto perdeu o foco
                String textoAtual = a1add.getText();
                if (!alimentosAutoComp.contains(textoAtual)) { // Se o texto atual não estiver na lista de sugestões
                    a1add.setText(""); // Limpar o campo de texto
                }
            }
        });
    }
    public void listaAlimentosFiltrado(){
        FilteredList<AlimentoData> filter= new FilteredList<>(listaAlimentos, e-> true);
        filtraAlimentos.textProperty().addListener((Observable, oldValue, newValue)-> {
            filter.setPredicate(predicateAlimento ->{
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateAlimento.getNome().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });
        SortedList<AlimentoData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(tabelaAlimentos.comparatorProperty());
        tabelaAlimentos.setItems(sortList);
    }
    public void listaRefeiçoesFiltrada(){
        FilteredList<RefeicaoData> filter = new FilteredList<>(listaRefeiçoes, e -> true);
        filtraRefeiçoes.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateRefeiçao -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateRefeiçao.getAlimentoNome(1).toString().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(2).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(3).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(4).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(5).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(6).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRefeiçao.getAlimentoNome(7).toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateRefeiçao.getAlimentoNome(8).toLowerCase().contains(searchKey)) {
                    return true;}
                else {
                    return false;
                }
            });
        });

        SortedList<RefeicaoData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(tableRefeiçoes.comparatorProperty());
        tableRefeiçoes.setItems(sortList);
    }


    public void atualizaRefeiçaoListaGeral(){
        verificaPesos();
        String sql = "UPDATE refeiçoes set nome = '"+ nomeNovaRefeiçao.getText()+
                "',a1 = '"+ a1add.getText()+"|"+a1peso.getText()+"'"+
                ",a2 = '"+ a2add.getText()+"|"+a2peso.getText()+"'"+
                ",a3 = '"+ a3add.getText()+"|"+a3peso.getText()+"'"+
                ",a4 = '"+ a4add.getText()+"|"+a4peso.getText()+"'"+
                ",a5 = '"+ a5add.getText()+"|"+a5peso.getText()+"'"+
                ",a6 = '"+ a6add.getText()+"|"+a6peso.getText()+"'"+
                ",a7 = '"+ a7add.getText()+"|"+a7peso.getText()+"'"+
                ",a8 = '"+ a8add.getText()+"|"+a8peso.getText()+"' WHERE nome = '"+
                nomeNovaRefeiçao.getText()+"'";
        connect= Database.connectDb();
        try{
            Alert alert;
            if(!existeRefeiçao(nomeNovaRefeiçao.getText())){
                alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Refeição inexistente");
                alert.setHeaderText(null);
                alert.setContentText("A refeição requerida não existe");
                alert.showAndWait();
            }
            else if(nomeNovaRefeiçao.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nome inválido");
                alert.setHeaderText(null);
                alert.setContentText("O nome da refeiçao a ser excluida não pode estar em branco");
                alert.showAndWait();
            }
            else{
                statement= connect.createStatement();
                statement.executeUpdate(sql);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText(null);
                alert.setContentText("Atualizado com sucesso!");
                configureTableDia(1);
                alert.showAndWait();
                addRefeiçoesTable();
                resetTextFieldsAddRefeiçao();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean existeRefeiçao(String nome){
        boolean existe = false;
        for(RefeicaoData r: addRefeiçaoList()){
            if (r.getNome().toUpperCase().equals(nome.toUpperCase())){
                existe = true;
            }
        }
        return existe;
    }
    public void deletaRefeiçaoListaGeral(){
        String sql = "DELETE FROM refeiçoes WHERE nome = '"+ nomeNovaRefeiçao.getText()+"'";
        connect= Database.connectDb();
        try{
            Alert alert;
            if(!existeRefeiçao(nomeNovaRefeiçao.getText())){
                alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Refeição inexistente");
                alert.setHeaderText(null);
                alert.setContentText("A refeição requerida não existe");
                alert.showAndWait();
            }
            else if(nomeNovaRefeiçao.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nome inválido");
                alert.setHeaderText(null);
                alert.setContentText("O nome da refeiçao a ser excluida não pode estar em branco");
                alert.showAndWait();
            }else{
                deletaRefeiçaoDosDias(nomeNovaRefeiçao.getText());
                initInfosCard();
                alert= new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText(null);
                alert.setContentText("Deseja realmente excluir: "+nomeNovaRefeiçao.getText()+"?");
                Optional<ButtonType> option =alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement= connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmação");
                    alert.setHeaderText(null);
                    alert.setContentText("Deletado com sucesso!");
                    alert.showAndWait();
                    addRefeiçoesTable();
                    resetTextFieldsAddRefeiçao();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deletaRefeiçaoDosDias(String nome){
        String sql = "DELETE FROM refeiçao_dia WHERE nome_refeiçao = '"+nome+"'";
        connect= Database.connectDb();
        try{
            statement= connect.createStatement();
            statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initGrafico(){
        graficoNutrientes.setData(pieChartData);
        graficoNutrientes.setLegendVisible(false);
    }

    public void setUser(){
        user = LoginController.getUser();
    }
    public void logout(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Você realmente quer sair ?");
        Optional<ButtonType> option = alert.showAndWait();
        try{
            if(option.get().equals(ButtonType.OK)){
                logoutButton.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AlimentoData> addalimentosList(){
        ObservableList<AlimentoData> listaAlimentos= FXCollections.observableArrayList();
        String sql = "SELECT * FROM alimento";
        connect= Database.connectDb();
        try{
            prepare= connect.prepareStatement(sql);
            result = prepare.executeQuery();
            AlimentoData alimento;
            while(result.next()){
                alimento= new AlimentoData(result.getInt("id"),
                        result.getString("nome"),
                        result.getFloat("carboidratos"),
                        result.getFloat("proteinas"),
                        result.getFloat("gorduras"),
                        result.getFloat("fibras"),
                        result.getFloat("calorias"),
                        result.getString("imagem"));
                listaAlimentos.add(alimento);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaAlimentos;
    }

    public void addAlimentosTable(){
        listaAlimentos = addalimentosList();
        nomeAlimento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carboAlimento.setCellValueFactory(new PropertyValueFactory<>("carboidratos"));
        proteinasAlimento.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
        fibrasAlimento.setCellValueFactory(new PropertyValueFactory<>("fibras"));
        caloriasAlimento.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        gordurasAlimento.setCellValueFactory(new PropertyValueFactory<>("gorduras"));
        tabelaAlimentos.setItems(listaAlimentos);
    }

    private void adicionaBd(int dia){
        String sql ="INSERT INTO refeiçao_dia"
                + "(Dia_semana,nome_refeiçao)"
                + "VALUES(?,?)";
        connect= Database.connectDb();
        Alert alert;
        try{
            if(nomeRefeiçaoAdc.getText().isEmpty()){
                alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Refeição inexistente");
                alert.setHeaderText(null);
                alert.setContentText("Refeição não encontrada.");
                alert.showAndWait();
            }
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(dia));
            prepare.setString(2,nomeRefeiçaoAdc.getText());
            prepare.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBd(int dia){
        String sql= "DELETE FROM refeiçao_dia WHERE Dia_semana = ? AND nome_refeiçao = ?";
        connect=Database.connectDb();
        try{
            prepare= connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(dia));
            prepare.setString(2, nomeRefeiçaoAdc.getText());
            prepare.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRefeiçãoDoDia(){
        if(checkSegunda.isSelected()){
            deleteBd(1);
        }
        if(checkTerça.isSelected()){
            deleteBd(2);
        }
        if(checkQuarta.isSelected()){
            deleteBd(3);
        }
        if(checkQuinta.isSelected()){
            deleteBd(4);
        }
        if(checkSexta.isSelected()){
            deleteBd(5);
        }
        if(checkSabado.isSelected()){
            deleteBd(6);
        }
        if(checkDomingo.isSelected()){
            deleteBd(7);
        }
        configureTableDia(1);
        initInfosCard();
        resetCheckBoxAndTextFieldHome();

    }

    public void addNovaRefeiçaoAoDia(){

        if(checkSegunda.isSelected()){
            adicionaBd(1);
        }
        if(checkTerça.isSelected()){
            adicionaBd(2);
        }
        if(checkQuarta.isSelected()){
            adicionaBd(3);
        }
        if(checkQuinta.isSelected()){
            adicionaBd(4);
        }
        if(checkSexta.isSelected()){
            adicionaBd(5);
        }
        if(checkSabado.isSelected()){
            adicionaBd(6);
        }
        if(checkDomingo.isSelected()){
            adicionaBd(7);
        }
        configureTableDia(1);
        initInfosCard();
        resetCheckBoxAndTextFieldHome();

    }

    public void setTableDiaSemana(ActionEvent event){
        if(event.getSource()==segundaButton){
            segundaButton.setStyle("-fx-background-color: #500505");
            configureTableDia(1);
        }
        else if(event.getSource()==terçaButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(2);
        }
        else if(event.getSource()==quartaButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(3);
        }
        else if(event.getSource()==quintaButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(4);
        }
        else if(event.getSource()==sextaButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(5);
        }
        else if(event.getSource()==sabadoButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(6);
        }
        else if(event.getSource()==domingoButton){
            segundaButton.setStyle("-fx-background-color: transparent");
            configureTableDia(7);
        }
    }
    public void configureTableDia(int dia){
        try{
            ObservableList<RefeicaoData> refeiçoesDia= FXCollections.observableArrayList();
            String sql = "SELECT nome_refeiçao FROM refeiçao_dia WHERE Dia_semana = ?";
            connect= Database.connectDb();
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(dia));
            result= prepare.executeQuery();
            RefeicaoData refeiçao = null;
            while(result.next()){
                String nome = result.getString("nome_Refeiçao");
                for(RefeicaoData r: listaRefeiçoes){
                    if(r.getNome().equalsIgnoreCase(nome)){
                        refeiçao = r;
                    }
                }
                refeiçoesDia.add(refeiçao);
            }
            addRefeiçoesTableDia(refeiçoesDia); // chama o metodo para inserir os dados na tabela do dia selecionado
            resetNutrientesDia();
            initNutrientesDia(refeiçoesDia);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addRefeiçoesTableDia(ObservableList<RefeicaoData> refeiçoesDoDia){
        listaRefeiçoesDia = refeiçoesDoDia;
        nomeRefeiçaoDia.setCellValueFactory(new PropertyValueFactory<>("nome"));
        a1Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(1)));
        a2Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(2)));
        a3Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(3)));
        a4Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(4)));
        a5Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(5)));
        a6Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(6)));
        a7Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(7)));
        a8Dia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlimentoNome(8)));
        ObservableList<RefeicaoData> listaFinalFormatada = listaRefeiçoesDia;
        tabelaRefeiçoesDia.setItems(listaRefeiçoesDia);

    }

    public void initNutrientesDia(ObservableList<RefeicaoData> lista){
        for(RefeicaoData r: lista) {
            caloriasDiaF += r.getCalorias();
            carboidratosDiaF += r.getCarboidratos();
            proteinasDiaF += r.getProteinas();
            gordurasDiaF += r.getGorduras();
            fibrasDiaF += r.getFibras();
        }
        int qntdRef = lista.size();
        qntRefeiçoesDia.setText(String.valueOf(qntdRef));
        carboidratosDia.setText(String.format("%.1f", carboidratosDiaF));
        caloriasDia.setText(String.format("%.1f", caloriasDiaF));
        proteinasDia.setText(String.format("%.1f", proteinasDiaF));
        gordurasDia.setText(String.format("%.1f", gordurasDiaF));
        fibrasDia.setText(String.format("%.1f", fibrasDiaF));
    }

    public void resetNutrientesDia(){
        caloriasDiaF =0;
        carboidratosDiaF =0;
        proteinasDiaF =0;
        gordurasDiaF =0;
        fibrasDiaF =0;
    }
    public ObservableList<RefeicaoData> addRefeiçaoList(){
        ObservableList<RefeicaoData> listaRefeiçoes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM refeiçoes";
        connect=Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result= prepare.executeQuery();
            RefeicaoData refeiçao;

            while(result.next()){
                float carboidratos=0;
                float proteinas=0;
                float calorias=0;
                float gorduras=0;
                float fibras=0;
                String[] elemA1= result.getString("a1").split("\\|");
                String a1 = "";
                String a1Peso= "";
                if(elemA1.length>1){
                    a1=elemA1[0];
                    a1Peso= elemA1[1];
                    float a1Quantidade = Float.parseFloat(elemA1[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a1)){
                            carboidratos+= (a.getCarboidratos()*(a1Quantidade/100));
                            proteinas+= (a.getProteinas()*(a1Quantidade/100));
                            calorias+= (a.getCalorias()*(a1Quantidade/100));
                            gorduras+= (a.getGorduras()*(a1Quantidade/100));
                            fibras+= (a.getFibras()*(a1Quantidade/100));
                        }
                    }
                }
                String[] elemA2= result.getString("a2").split("\\|");
                String a2 = "";
                String a2Peso= "";
                if(elemA2.length>1){
                    a2= elemA2[0];
                    a2Peso= elemA2[1];
                    float a2Quantidade =  Float.parseFloat(elemA2[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a2)){
                            carboidratos+= (a.getCarboidratos()*(a2Quantidade/100));
                            proteinas+= (a.getProteinas()*(a2Quantidade/100));
                            calorias+= (a.getCalorias()*(a2Quantidade/100));
                            gorduras+= (a.getGorduras()*(a2Quantidade/100));
                            fibras+= (a.getFibras()*(a2Quantidade/100));
                        }
                    }
                }

                String[] elemA3= result.getString("a3").split("\\|");
                String a3= "";
                String a3Peso= "";
                if(elemA3.length>1) {
                    a3 = elemA3[0];
                    a3Peso= elemA3[1];
                    float a3Quantidade = Float.parseFloat(elemA3[1]);
                    for (AlimentoData a : listaAlimentos) {
                        if (a.getNome().equals(a3)) {
                            carboidratos += (a.getCarboidratos() * (a3Quantidade / 100));
                            proteinas += (a.getProteinas() * (a3Quantidade / 100));
                            calorias += (a.getCalorias() * (a3Quantidade / 100));
                            gorduras += (a.getGorduras() * (a3Quantidade / 100));
                            fibras += (a.getFibras() * (a3Quantidade / 100));
                        }
                    }
                }
                String[] elemA4= result.getString("a4").split("\\|");
                String a4 = "";
                String a4Peso= "";
                if(elemA4.length>1) {
                    a4 = elemA4[0];
                    a4Peso= elemA4[1];
                    float a4Quantidade = Float.parseFloat(elemA4[1]);

                    for (AlimentoData a : listaAlimentos) {
                        if (a.getNome().equals(a4)) {
                            carboidratos += (a.getCarboidratos() * (a4Quantidade / 100));
                            proteinas += (a.getProteinas() * (a4Quantidade / 100));
                            calorias += (a.getCalorias() * (a4Quantidade / 100));
                            gorduras += (a.getGorduras() * (a4Quantidade / 100));
                            fibras += (a.getFibras() * (a4Quantidade / 100));
                        }
                    }
                }

                String[] elemA5= result.getString("a5").split("\\|");
                String a5 = "";
                String a5Peso= "";
                if (elemA5.length>1){
                    a5= elemA5[0];
                    a5Peso= elemA5[1];
                    float a5Quantidade =  Float.parseFloat(elemA5[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a5)){
                            carboidratos+= (a.getCarboidratos()*(a5Quantidade/100));
                            proteinas+= (a.getProteinas()*(a5Quantidade/100));
                            calorias+= (a.getCalorias()*(a5Quantidade/100));
                            gorduras+= (a.getGorduras()*(a5Quantidade/100));
                            fibras+= (a.getFibras()*(a5Quantidade/100));
                        }
                    }
                }
                String[] elemA6= result.getString("a6").split("\\|");
                String a6 = "";
                String a6Peso= "";
                if(elemA6.length>1){
                    a6=elemA6[0];
                    a6Peso= elemA6[1];
                    float a6Quantidade =  Float.parseFloat(elemA6[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a6)){
                            carboidratos+= (a.getCarboidratos()*(a6Quantidade/100));
                            proteinas+= (a.getProteinas()*(a6Quantidade/100));
                            calorias+= (a.getCalorias()*(a6Quantidade/100));
                            gorduras+= (a.getGorduras()*(a6Quantidade/100));
                            fibras+= (a.getFibras()*(a6Quantidade/100));
                        }
                    }
                }
                String[] elemA7= result.getString("a7").split("\\|");
                String a7 = "";
                String a7Peso= "";
                if(elemA7.length>1){
                    a7=elemA7[0];
                    a7Peso= elemA7[1];
                    float a7Quantidade =  Float.parseFloat(elemA7[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a7)){
                            carboidratos+= (a.getCarboidratos()*(a7Quantidade/100));
                            proteinas+= (a.getProteinas()*(a7Quantidade/100));
                            calorias+= (a.getCalorias()*(a7Quantidade/100));
                            gorduras+= (a.getGorduras()*(a7Quantidade/100));
                            fibras+= (a.getFibras()*(a7Quantidade/100));
                        }
                    }
                }
                String[] elemA8= result.getString("a8").split("\\|");
                String a8 = "";
                String a8Peso= "";
                if(elemA8.length>1){
                    a8=elemA8[0];
                    a8Peso= elemA8[1];
                    float a8Quantidade =  Float.parseFloat(elemA8[1]);
                    for (AlimentoData a:listaAlimentos){
                        if(a.getNome().equals(a8)){
                            carboidratos+= (a.getCarboidratos()*(a8Quantidade/100));
                            proteinas+= (a.getProteinas()*(a8Quantidade/100));
                            calorias+= (a.getCalorias()*(a8Quantidade/100));
                            gorduras+= (a.getGorduras()*(a8Quantidade/100));
                            fibras+= (a.getFibras()*(a8Quantidade/100));
                        }
                    }
                }
                ArrayList<String> alimentosAdc = new ArrayList<>();
                alimentosAdc.add(a1+"|"+a1Peso);
                alimentosAdc.add(a2+"|"+a2Peso);
                alimentosAdc.add(a3+"|"+a3Peso);
                alimentosAdc.add(a4+"|"+a4Peso);
                alimentosAdc.add(a5+"|"+a5Peso);
                alimentosAdc.add(a6+"|"+a6Peso);
                alimentosAdc.add(a7+"|"+a7Peso);
                alimentosAdc.add(a8+"|"+a8Peso);
                String nomeUpper = result.getString("nome").toUpperCase();
                refeiçao = new RefeicaoData(result.getInt("id"),
                        nomeUpper,
                        alimentosAdc,
                        calorias,
                        carboidratos,
                        proteinas,fibras,gorduras)   ;
                listaRefeiçoes.add(refeiçao);
            }
        }catch (Exception e){
            e.printStackTrace();;
            
        }
        return listaRefeiçoes;
    }
    public void addRefeiçoesTable(){
        listaRefeiçoes = addRefeiçaoList();
        nomeRefeiçao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableRefeiçoes.setItems(listaRefeiçoes);
    }

    public void pressElementTableAlimentos(){
        if(tabelaAlimentos!= null && tabelaAlimentos.getSelectionModel()!= null){
            tabelaAlimentos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
                if(newSelection!=null){
                    Image imagem = new Image(newSelection.getImagem());
                    nomeAlimentoDescriçao.setText(newSelection.getNome());
                    imagemAlimento.setImage(imagem);
                }
            });
        }
    }

    public void pressElementTable() {
        if (tableRefeiçoes != null && tableRefeiçoes.getSelectionModel() != null) {
            tableRefeiçoes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Mostrar os dados da linha selecionada
                    // set nos textFields, de cada elemento de refeiçao
                    nomeNovaRefeiçao.setText(newSelection.getNome());
                    a1add.setText(newSelection.getAlimentoNome(1)); a1peso.setText(newSelection.getAlimentoPeso(1));
                    a2add.setText(newSelection.getAlimentoNome(2)); a2peso.setText(newSelection.getAlimentoPeso(2));
                    a3add.setText(newSelection.getAlimentoNome(3)); a3peso.setText(newSelection.getAlimentoPeso(3));
                    a4add.setText(newSelection.getAlimentoNome(4)); a4peso.setText(newSelection.getAlimentoPeso(4));
                    a5add.setText(newSelection.getAlimentoNome(5)); a5peso.setText(newSelection.getAlimentoPeso(5));
                    a6add.setText(newSelection.getAlimentoNome(6)); a6peso.setText(newSelection.getAlimentoPeso(6));
                    a7add.setText(newSelection.getAlimentoNome(7)); a7peso.setText(newSelection.getAlimentoPeso(7));
                    a8add.setText(newSelection.getAlimentoNome(8)); a8peso.setText(newSelection.getAlimentoPeso(8));
                    tituloPane.setText(newSelection.getNome());
                    carboPane.setText(String.format("%.1f", newSelection.getCarboidratos()));
                    proteinasPane.setText(String.format("%.1f", newSelection.getProteinas()));
                    GordurasPane.setText(String.format("%.1f", newSelection.getGorduras()));
                    fibrasPane.setText(String.format("%.1f", newSelection.getFibras()));
                    caloriasPane.setText(String.format("%.1f", newSelection.getCalorias()));
                    a1Pane.setText(newSelection.getAlimentoCompleto(1));
                    if(!newSelection.getAlimentoNome(1).isEmpty())
                    {
                        a1icon.setVisible(true);
                    }else {
                        a1icon.setVisible(false);
                    }
                    a2Pane.setText(newSelection.getAlimentoCompleto(2));
                    if(!newSelection.getAlimentoNome(2).isEmpty())
                    {
                        a2icon.setVisible(true);
                    }else {
                        a2icon.setVisible(false);
                    }
                    a3Pane.setText(newSelection.getAlimentoCompleto(3));
                    if(!newSelection.getAlimentoNome(3).isEmpty())
                    {
                        a3icon.setVisible(true);
                    }else {
                        a3icon.setVisible(false);
                    }
                    a4Pane.setText(newSelection.getAlimentoCompleto(4));
                    if(!newSelection.getAlimentoNome(4).isEmpty())
                    {
                        a4icon.setVisible(true);
                    }else {
                        a4icon.setVisible(false);
                    }
                    a5Pane.setText(newSelection.getAlimentoCompleto(5));
                    if(!newSelection.getAlimentoNome(5).isEmpty())
                    {
                        a5icon.setVisible(true);
                    }else {
                        a5icon.setVisible(false);
                    }
                    a6Pane.setText(newSelection.getAlimentoCompleto(6));
                    if(!newSelection.getAlimentoNome(6).isEmpty())
                    {
                        a6icon.setVisible(true);
                    }else {
                        a6icon.setVisible(false);
                    }
                    a7Pane.setText(newSelection.getAlimentoCompleto(7));
                    if(!newSelection.getAlimentoNome(7).isEmpty())
                    {
                        a7icon.setVisible(true);
                    }else {
                        a7icon.setVisible(false);
                    }
                    a8Pane.setText(newSelection.getAlimentoCompleto(8));
                    if(!newSelection.getAlimentoNome(8).isEmpty())
                    {
                        a8icon.setVisible(true);
                    }
                    else {
                        a8icon.setVisible(false);
                    }

                }
                refeiçoesPage.setOnMouseClicked(event -> {
                    if (!tableRefeiçoes.getBoundsInParent().contains(event.getX(), event.getY())) {
                        // Limpe a seleção da TableView
                        resetTextFieldsAddRefeiçao();
                        tableRefeiçoes.getSelectionModel().clearSelection();
                    }
                });
                ;
                tableRefeiçoes.setOnMouseClicked(event -> {
                    pressElementTable();
                    if (event.getClickCount() == 1) { // Verifica se foi um clique único
                        // Recupera a linha selecionada com base no clique
                        RefeicaoData selectedRow = tableRefeiçoes.getSelectionModel().getSelectedItem();
                        if (selectedRow != null) {
                            updateChartData(selectedRow);
                        }
                    }
                });
            });
        }
    }

    public void addNovaRefeiçaoLista(){
        String sql = "INSERT INTO refeiçoes "
                + "(nome,a1,a2,a3,a4,a5,a6,a7,a8) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        connect= Database.connectDb();
        try{
            Alert alert;
            if(nomeNovaRefeiçao.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nome inválido");
                alert.setHeaderText(null);
                alert.setContentText("O nome da refeição não pode estar em branco");
                alert.showAndWait();
            }
            else{
                // confere se o nome da nova refeição já existe no Banco de dados.
                String confere = "SELECT nome FROM refeiçoes WHERE nome = '"
                        + nomeNovaRefeiçao.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(confere);
                if (result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Refeiçao existente");
                    alert.setHeaderText(null);
                    alert.setContentText("Nome de refeição: " + nomeNovaRefeiçao.getText()+ " já existe!");
                    alert.showAndWait();
                }
                else{
                    System.out.println(a1peso.getText());
                    verificaPesos();
                    prepare= connect.prepareStatement(sql);
                    prepare.setString(1,nomeNovaRefeiçao.getText());
                    prepare.setString(2, a1add.getText()+"|"+a1peso.getText());
                    prepare.setString(3, a2add.getText()+"|"+a2peso.getText());
                    prepare.setString(4, a3add.getText()+"|"+a3peso.getText());
                    prepare.setString(5, a4add.getText()+"|"+a4peso.getText());
                    prepare.setString(6, a5add.getText()+"|"+a5peso.getText());
                    prepare.setString(7, a6add.getText()+"|"+a6peso.getText());
                    prepare.setString(8, a7add.getText()+"|"+a7peso.getText());
                    prepare.setString(9, a8add.getText()+"|"+a8peso.getText());
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmação");
                    alert.setHeaderText(null);
                    alert.setContentText("Refeição adicionada com sucesso!");
                    alert.showAndWait();
                    addRefeiçoesTable();
                    resetTextFieldsAddRefeiçao();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void verificaPesos(){
        if (a1peso.getText().equals("")){
            a1peso.setText("100");
        }
        if(a2peso.getText().equals("")){
            a2peso.setText("100");
        }
        if (a3peso.getText().equals("")) {
            a3peso.setText("100");
        } if (a4peso.getText().equals("")) {
            a4peso.setText("100");
        } if (a5peso.getText().equals("")) {
            a5peso.setText("100");
        } if (a6peso.getText().equals("")) {
            a6peso.setText("100");
        } if (a7peso.getText().equals("")) {
            a7peso.setText("100");
        } if (a8peso.getText().equals("")) {
            a8peso.setText("100");
        }
    }
    public void resetTextFieldsAddRefeiçao(){
        nomeNovaRefeiçao.setText("");
        a1add.setText("");
        a2add.setText("");
        a3add.setText("");
        a4add.setText("");
        a5add.setText("");
        a6add.setText("");
        a7add.setText("");
        a8add.setText("");
        a1peso.setText("");
        a2peso.setText("");
        a3peso.setText("");
        a4peso.setText("");
        a5peso.setText("");
        a6peso.setText("");
        a7peso.setText("");
        a8peso.setText("");
    }

    public void resetCheckBoxAndTextFieldHome(){
        checkSegunda.setSelected(false);
        checkTerça.setSelected(false);
        checkQuarta.setSelected(false);
        checkQuinta.setSelected(false);
        checkSexta.setSelected(false);
        checkSabado.setSelected(false);
        checkDomingo.setSelected(false);
        nomeRefeiçaoAdc.setText("");
    }

    private void updateChartData(RefeicaoData selectedRow) {
        // Crie uma nova lista de dados para o gráfico com base na linha selecionada
        ObservableList<PieChart.Data> newData = FXCollections.observableArrayList(
                new PieChart.Data("Proteínas", selectedRow.getProteinas()),
                new PieChart.Data("Carboidratos", selectedRow.getCarboidratos()),
                new PieChart.Data("Gorduras", selectedRow.getGorduras()),
                new PieChart.Data("Fibras", selectedRow.getFibras())
        );

        // Atualize os dados do gráfico com a nova lista de dados
        graficoNutrientes.setData(newData);
        graficoNutrientes.setLegendVisible(true);
        graficoNutrientes.setTitle("Gráfico de nutrientes");
        String[] cores = {"#101093","#2ecc71","#d20606","#88439f"};
        int i=0;
        for (PieChart.Data data : graficoNutrientes.getData()) {
            // Obtendo o nó gráfico da fatia
            javafx.scene.Node node = data.getNode();
            // Definindo a cor desejada para a fatia
            node.setStyle("-fx-pie-color: "+cores[i]+";");
            i++;
        }
        for (PieChart.Data data : graficoNutrientes.getData()) {
            Tooltip tooltip = new Tooltip( data.getName());
            Tooltip.install(data.getNode(), tooltip);
            // Quando o mouse entra na fatia do gráfico
            data.getNode().setOnMouseEntered(event -> {
                data.getNode().setScaleY(1.02);
                data.getNode().setScaleX(1.02);
                data.getNode().setOpacity(0.7); // Reduz a opacidade da fatia
            });

            // Quando o mouse sai da fatia do gráfico
            data.getNode().setOnMouseExited(event -> {
                data.getNode().setScaleY(1.0);
                data.getNode().setScaleX(1.0);
                data.getNode().setOpacity(1.0); // Restaura a opacidade original da fatia
            });
        }
    }


    public void opçaoMenu(ActionEvent event){
        if(event.getSource()== homeButton){
            homePage.setVisible(true);
            refeiçoesPage.setVisible(false);
            alimentosPage.setVisible(false);
            homeButton.setStyle("-fx-background-color:linear-gradient(to bottom right, #542929, #bb1b1b);");
            paginaRefeiçoesButton.setStyle("-fx-background-color: transparent");
            catalogoAlimentosButton.setStyle("-fx-background-color: transparent");
            segundaButton.requestFocus(); // Define o foco no botão "segundaButton"
            segundaButton.fire();
            initListaDeSugestoesRefeiçoes(nomeRefeiçaoAdc);
            setUser();
            defineNomeUser();
            addalimentosList();
        }
        else if(event.getSource()== paginaRefeiçoesButton){
            refeiçoesPage.setVisible(true);
            homePage.setVisible(false);
            alimentosPage.setVisible(false);
            paginaRefeiçoesButton.setStyle("-fx-background-color:linear-gradient(to bottom right, #542929, #bb1b1b);");
            homeButton.setStyle("-fx-background-color: transparent");
            catalogoAlimentosButton.setStyle("-fx-background-color: transparent;");
            addRefeiçoesTable();
            pressElementTable();
            listaRefeiçoesFiltrada();
            initListaDeSugestoes(a1add);
            initListaDeSugestoes(a2add);
            initListaDeSugestoes(a3add);
            initListaDeSugestoes(a4add);
            initListaDeSugestoes(a5add);
            initListaDeSugestoes(a6add);
            initListaDeSugestoes(a7add);
            initListaDeSugestoes(a8add);
            contemApenasDigitos(a1peso);
            contemApenasDigitos(a2peso);
            contemApenasDigitos(a3peso);
            contemApenasDigitos(a4peso);
            contemApenasDigitos(a5peso);
            contemApenasDigitos(a6peso);
            contemApenasDigitos(a7peso);
            contemApenasDigitos(a8peso);
            initGrafico();
        }
        else if(event.getSource()==catalogoAlimentosButton){
            alimentosPage.setVisible(true);
            homePage.setVisible(false);
            refeiçoesPage.setVisible(false);
            paginaRefeiçoesButton.setStyle("-fx-background-color:transparent");
            homeButton.setStyle("-fx-background-color: transparent");
            listaAlimentosFiltrado();
            catalogoAlimentosButton.setStyle("-fx-background-color:linear-gradient(to bottom right, #542929, #bb1b1b);");
        }

    }

    public void defineNomeUser(){
        String showNome = user.getNome();

        // Dividindo o nome completo em partes usando o espaço como delimitador
        String[] partesNome = showNome.split(" ");
        if(partesNome.length>2){
            String nomeConcatenado = partesNome[0]+" "+partesNome[1];
            nomeUsuario.setText(nomeConcatenado);
        }
        else {
            nomeUsuario.setText(user.getNome());
        }
    }
    public void close(){
        System.exit(0);
    }
    public void minimize() {
        Stage stage = (Stage) cenaPrincipal.getScene().getWindow();
        stage.setIconified(true);
    }
}
