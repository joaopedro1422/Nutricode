package com.example.nutricode_;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;


/**
 * Classe controller para a interface principal do aplicativo
 */
public class DashboardController implements Initializable {
    //Definição dos botões que definem a vizualização dos dias da semana
    @FXML
    private Button segundaButton,terçaButton,quartaButton,quintaButton,sextaButton,sabadoButton,domingoButton;
    // CheckBox de seleção dos dias em que as refeições serão adicionadas ou excluidas
    @FXML
    private CheckBox checkDomingo,checkQuarta,checkQuinta,checkSabado,checkSegunda,checkSexta,checkTerça;
    @FXML
    private TextField nomeRefeiçaoAdc;
    @FXML
    private Label qntRefeiçoesDia;
    @FXML
    private Button indicadorCarboidratos,indicadorCalorias,indicadorProteinas;
    // Botões do menu de páginas
    @FXML
    private Button homeButton,paginaRefeiçoesButton,catalogoAlimentosButton;
    @FXML
    private AnchorPane homePage, refeiçoesPage;
    @FXML
    private AnchorPane cenaPrincipal;
    // Labels para as informaçoes de cada refeição no painel de exibição
    @FXML
    private Label caloriasPane,fibrasPane, carboPane,proteinasPane,GordurasPane;
    @FXML
    private ImageView imagemAlimento;
    @FXML
    private AnchorPane alimentosPage;
    @FXML
    private TextField filtraAlimentos;
    @FXML
    private TextField filtraRefeiçoes;
    @FXML
    private Label carboidratosDia,fibrasDia,proteinasDia,gordurasDia,caloriasDia;
    @FXML
    private AnchorPane cardFeedbackCalorias, cardFeedbackCarbo,cardFeedbackProteinas;
    @FXML
    private AnchorPane cardProteinas,cardCalorias,cardCarboidratos;
    @FXML
    private Label descriçaoFeedbackCalorias,descriçaoFeedbackCarbo,descriçaoFeedbackProteinas;
    @FXML
    private Label quantidadeRecCalorias,quantidadeRecCarbo,quantidadeRecProteinas;

    private float carboidratosDiaF=0,caloriasDiaF=0,fibrasDiaF=0,gordurasDiaF=0,proteinasDiaF=0;
    @FXML
    private FontAwesomeIconView logout;
    @FXML
    private Label mediaCal,mediaCarbo, mediaProt,nomeUsuario;
    @FXML
    private Button logoutButton;
    @FXML
    private PieChart graficoNutrientes;
    private Usuario user;
    @FXML
    private TableView<RefeicaoData> tabelaRefeiçoesDia;
    //Definições para a tabela do catálogo de alimentos
    @FXML
    private TableView<AlimentoData> tabelaAlimentos;
    @FXML
    private TableColumn<?, ?> nomeAlimento,carboAlimento,proteinasAlimento,gordurasAlimento,fibrasAlimento,caloriasAlimento;
    private ObservableList<RefeicaoData> listaRefeiçoesDia;
    private ObservableList<AlimentoData> listaAlimentos;
    @FXML
    private TableColumn<RefeicaoData, String> nomeRefeiçaoDia;
    // colunas dos alimentos de cada refeição da tabela
    @FXML
    private TableColumn<RefeicaoData, String> a1Dia,a2Dia,a3Dia,a4Dia,a5Dia,a6Dia,a7Dia,a8Dia;
    //icones de Lista que serão adicionados antes do nome de cada alimento, caso existam
    @FXML
    private FontAwesomeIconView a1icon,a2icon,a3icon,a4icon,a5icon,a6icon,a7icon,a8icon;
    //TextFields para os alimentos que serão inseridos na refeição
    @FXML
    private TextField a1add,a2add,a3add,a4add,a5add,a6add,a7add,a8add;
    //TextFields para os pesos dos alimentos que serão inseridos na refeiçao
    @FXML
    private TextField a1peso,a2peso,a3peso,a4peso,a5peso,a6peso,a7peso,a8peso;
    @FXML
    private TextField nomeNovaRefeiçao;
    @FXML
    private Label a1Pane,a2Pane,a3Pane,a4Pane,a5Pane,a6Pane,a7Pane,a8Pane;
    // Definições dos mecanismos para a interação com o banco de dados
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private JasperReportGenerate jasperReportGenerate;
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

    /**
     *Método padrão de inicialização do controlador
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        segundaButton.requestFocus(); // Define o foco no botão "segundaButton"
        jasperReportGenerate= new JasperReportGenerate();
        setUser();
        defineNomeUser();
        addAlimentosTable();
        addRefeiçoesTable();
        resetNutrientesDia();
        homeButton.fire();
        pressElementTableAlimentos();
        pressElementTable();
        listaRefeiçoesFiltrada();
        listaAlimentosFiltrado();
        initGrafico();
        initInfosCard();
        segundaButton.fire();
        segundaButton.setStyle("-fx-background-color: #6b0101");
    }

    /**
     * Método que gera um relatório em pdf com os principais dados nutricionais do usuário
     */
    public void geraRelatorio(){
        try{
            HashMap<String,Object> dadosUsuario= new HashMap<>();
            HashMap<String, Object> mediasDia  = new HashMap<>();
            HashMap<String, Object> nutrientesSegunda = new HashMap<>();
            HashMap<String, Object> nutrientesTerça = new HashMap<>();
            HashMap<String, Object> nutrientesQuarta = new HashMap<>();
            HashMap<String, Object> nutrientesQuinta = new HashMap<>();
            HashMap<String, Object> nutrientesSexta = new HashMap<>();
            HashMap<String, Object> nutrientesSabado = new HashMap<>();
            HashMap<String, Object> nutrientesDomingo = new HashMap<>();
            mediasDia= initInfosCard();
            nutrientesSegunda= getNutrientesDia(1);
            nutrientesTerça= getNutrientesDia(2);
            nutrientesQuarta= getNutrientesDia(3);
            nutrientesQuinta= getNutrientesDia(4);
            nutrientesSexta= getNutrientesDia(5);
            nutrientesSabado= getNutrientesDia(6);
            nutrientesDomingo= getNutrientesDia(7);
            dadosUsuario.put("carboidratosDia", String.format("%.1f",mediasDia.get("carboidratosDia")));
            dadosUsuario.put("caloriasDia",String.format("%.1f",mediasDia.get("caloriasDia")));
            dadosUsuario.put("proteinasDia", String.format("%.1f",mediasDia.get("proteinasDia")));
            dadosUsuario.put("fibrasDia",String.format("%.1f",mediasDia.get("fibrasDia")));
            dadosUsuario.put("gordurasDia", String.format("%.1f",mediasDia.get("gordurasDia")));
            dadosUsuario.put("qntRefeiçoes", String.format("%.1f",mediasDia.get("qntRefeiçoes")));
            dadosUsuario.put("caloriasSegunda", String.format("%.1f", nutrientesSegunda.get("calorias")));
            dadosUsuario.put("gordurasSegunda", String.format("%.1f",nutrientesSegunda.get("gorduras")));
            dadosUsuario.put("caloriasTerça", String.format("%.1f", nutrientesTerça.get("calorias")));
            dadosUsuario.put("gordurasTerça", String.format("%.1f",nutrientesTerça.get("gorduras")));
            dadosUsuario.put("caloriasQuarta", String.format("%.1f", nutrientesQuarta.get("calorias")));
            dadosUsuario.put("gordurasQuarta", String.format("%.1f", nutrientesQuarta.get("gorduras")));
            dadosUsuario.put("caloriasQuinta", String.format("%.1f", nutrientesQuinta.get("calorias")));
            dadosUsuario.put("gordurasQuinta", String.format("%.1f", nutrientesQuinta.get("gorduras")));
            dadosUsuario.put("caloriasSexta", String.format("%.1f", nutrientesSexta.get("calorias")));
            dadosUsuario.put("gordurasSexta", String.format("%.1f", nutrientesSexta.get("gorduras")));
            dadosUsuario.put("caloriasSabado", String.format("%.1f", nutrientesSabado.get("calorias")));
            dadosUsuario.put("gordurasSabado", String.format("%.1f", nutrientesSabado.get("gorduras")));
            dadosUsuario.put("caloriasDomingo", String.format("%.1f", nutrientesDomingo.get("calorias")));
            dadosUsuario.put("gordurasDomingo", String.format("%.1f", nutrientesDomingo.get("gorduras")));
            jasperReportGenerate.gerarRelatorio(user,dadosUsuario);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método que obtém as quantidades totais de nutrientes de cada dia da semana, de acordo com o dia passado
     * como argumento
     * @param dia
     * @return
     */
    public HashMap<String,Object> getNutrientesDia(int dia){
        HashMap<String,Object> nutrientesDia= new HashMap<>();
        try{
            ObservableList<RefeicaoData> refeiçoesDia= FXCollections.observableArrayList();
            String sql = "SELECT nome_refeiçao FROM refeiçao_dia WHERE id_usuario=? AND Dia_semana = ?";
            connect= Database.connectDb();
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(user.getId()));
            prepare.setString(2,String.valueOf(dia));
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
            float totalCalorias=0;
            float totalGorduras=0;
            //iterando sobre a lista de refeiçoes do dia para obter os nutrientes totais
            for(RefeicaoData r:refeiçoesDia){
                totalCalorias+= r.getCalorias();
                totalGorduras+= r.getGorduras();
            }
             // chama o metodo para inserir os dados na tabela do dia selecionado
            nutrientesDia.put("calorias",totalCalorias);
            nutrientesDia.put("gorduras",totalGorduras);
        }catch (Exception e){
            e.printStackTrace();
        }
        return nutrientesDia;
    }

    /**
     * Método que permite ao usuário solicitar um alimento de seu gosto que ainda não esteja no banco de dados do aplicativo
     */
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

    /**
     * Conjunto de métodos para a alternância entre os cards da página inicial
     */
    public void getTofeedbackProteinas(){
        cardProteinas.setVisible(false);
        cardFeedbackProteinas.setVisible(true);
    }
    public void getToCardProteinas(){
        cardProteinas.setVisible(true);
        cardFeedbackProteinas.setVisible(false);
    }

    public void getTofeedbackCarbo(){
        cardCarboidratos.setVisible(false);
        cardFeedbackCarbo.setVisible(true);
    }
    public void getToCardCarbo(){
        cardCarboidratos.setVisible(true);
        cardFeedbackCarbo.setVisible(false);
    }
    public void getTofeedbackCalorias(){
        cardCalorias.setVisible(false);
        cardFeedbackCalorias.setVisible(true);
    }
    public void getToCardCalorias(){
        cardCalorias.setVisible(true);
        cardFeedbackCalorias.setVisible(false);
    }

    /**
     * Método de inicialização dos dados presentes nos Cards da pagina inicial, obtendo a média dos nutrientes
     * e gerando os respectivos feedBacks
     * @return
     */
    public HashMap<String,Object> initInfosCard(){
        HashMap<String, Object> medias= new HashMap<>();
        try{
            String sql= "SELECT nome_refeiçao FROM refeiçao_dia WHERE id_usuario= '"+ user.getId()+"'";
            ObservableList<RefeicaoData> todasRefeiçoesEmUso= FXCollections.observableArrayList();
            connect= Database.connectDb();
            prepare= connect.prepareStatement(sql);
            result= prepare.executeQuery();
            float carboidratosTotais= 0;
            float caloriasTotais=0;
            float proteinasTotais=0;
            float fibrasTotais=0;
            float gordurasTotais=0;
            float qntRefeiçoes=0;
            String nomeRefeiçao;
            while(result.next()){
                nomeRefeiçao= result.getString("nome_refeiçao");
                for(RefeicaoData r: listaRefeiçoes){
                    if(r.getNome().equalsIgnoreCase(nomeRefeiçao)){
                        carboidratosTotais+= r.getCarboidratos();
                        caloriasTotais+= r.getCalorias();
                        proteinasTotais+= r.getProteinas();
                        fibrasTotais+= r.getFibras();
                        gordurasTotais+= r.getGorduras();
                        qntRefeiçoes++;
                        todasRefeiçoesEmUso.add(r);
                    }
                }
            }
            float mediaProteinas = proteinasTotais/7;
            float mediaCalorias= caloriasTotais/7;
            float mediaCarboidratos = carboidratosTotais/7;
            float mediaFibras = fibrasTotais/7;
            float mediaGorduras = gordurasTotais/7;
            float mediaQntRefeiçoes = qntRefeiçoes/7;
            medias.put("carboidratosDia",mediaCarboidratos);
            medias.put("proteinasDia", mediaProteinas);
            medias.put("caloriasDia",mediaCalorias);
            medias.put("fibrasDia", mediaFibras);
            medias.put("gordurasDia",mediaGorduras);
            medias.put("qntRefeiçoes",mediaQntRefeiçoes);
            mediaCarbo.setText(String.format("%.1f", mediaCarboidratos));
            mediaProt.setText(String.format("%.1f", mediaProteinas));
            mediaCal.setText(String.format("%.1f", mediaCalorias));
            String feedbackProteinas="",feedbackCarbo="",feedbackCalorias="";
            if(mediaCarboidratos<140){
                indicadorCarboidratos.setStyle("-fx-background-color: #ab0404");
                feedbackCarbo="! Quantidades de carboidratos abaixo das recomendações mímimas podem causar: impactos cognitivos -como dificuldades de concentração-  e falta de energia para as atividades diárias. Recomenda-se uma maior ingestão de frutas, massas e grãos.";
                quantidadeRecCarbo.setText("140g");
                descriçaoFeedbackCarbo.setText(feedbackCarbo);
            }
            else{
                indicadorCarboidratos.setStyle("-fx-background-color: #00a900");
                quantidadeRecCarbo.setText("140g");
                feedbackCarbo=" Parabéns, você está consumindo uma quantidade de carboidratos adequada e suficiente para o funcionamento do seu corpo. No entanto, caso pratique atividades físicas com frequência, é recomendada uma ingestão de pelo menos 300g por dia.";
                descriçaoFeedbackCarbo.setText(feedbackCarbo);
            }
            if(mediaCalorias<1500){
                indicadorCalorias.setStyle("-fx-background-color: #a40606");
                feedbackCalorias="! A quantidade calórica, quando não ingerida adequadamente pode causar: perda de peso excessiva, fraqueza, fadiga e impactos nega- tivos nos hormônios naturais. Recomenda-se maior ingestão de nozes , sementes , produtos lácteos e massas (seguindo possível dieta).";
                quantidadeRecCalorias.setText("1400Kcal");
                descriçaoFeedbackCalorias.setText(feedbackCalorias);
            }
            else{
                indicadorCalorias.setStyle("-fx-background-color: #00a900");
                feedbackCalorias= " Parabéns, sua ingestão de calorias por dia está adequada para um bom funcionamento corporal. Porém, caso pratique atividades físicas com frequencia, os valores mínimos podem chegar até a 2000Kcal. Se for o seu caso busque alimentos calóricos para sua dieta";
                quantidadeRecCalorias.setText("1400Kcal");
                descriçaoFeedbackCalorias.setText(feedbackCalorias);
            }
            if(mediaProteinas<(0.9*user.getPeso())){
                indicadorProteinas.setStyle("-fx-background-color: #a60404");
                feedbackProteinas= "! Quantidades de proteínas abaixo das reco- mendações mínimas podem acarretar em: perda muscular, Problemas de pele / cabelo e unhas , retardo na cicatrização de feridas e etc. Recomenda-se uma maior ingestão de Carnes, peixes, Ovos e leguminosas.";
                quantidadeRecProteinas.setText(String.format("%.1f",user.getPeso()*0.9)+"g");
                descriçaoFeedbackProteinas.setText(feedbackProteinas);
            }
            else{
                indicadorProteinas.setStyle("-fx-background-color: #00a900");
                feedbackProteinas= "Parabéns, você está consumindo uma quan- tidade de proteínas adequada para o seu corpo. No entanto, caso possua objetivos acerca de crescimento muscular, a quanti- dade indicada para melhores desempenhos é de 1.5*Kg corporal. No seu caso: "+
                        String.valueOf(user.getPeso()*1.5)+"g";
                quantidadeRecProteinas.setText(String.format("%.1f",user.getPeso()*0.9)+"g");
                descriçaoFeedbackProteinas.setText(feedbackProteinas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return medias;
    }

    /**
     * Método que verifica se o textField que captura os pesos de cada alimento possui apenas dígitos
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

    /**
     * Método que exibe as refeições cadastradas conforme o usuário digita no textFields
     * @param textField
     */
    public void initListaDeSugestoesRefeiçoes(TextField textField){
        ArrayList<String> refeiçoesAutoComp= new ArrayList<>();
        for(RefeicaoData r: listaRefeiçoes) {
            refeiçoesAutoComp.add(r.getNome().toUpperCase());
        }
            TextFields.bindAutoCompletion(textField,refeiçoesAutoComp);
            textField.focusedProperty().addListener((observable,oldValue,newValue)-> {
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

    /**
     * Filtro para a tabela de alimentos, de acordo com o nome escrito pelo usuário no textField
     */
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

    /**
     * Filtro para a tabela de refeições, de acordo com o alimento informado pelo usuário no TextField
     * Exibe apenas as refeições que contiverem o alimento requerido
     */
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

    /**
     * Método que realiza o update da refeição no banco de dados, passando os novos dados
     */
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
                nomeNovaRefeiçao.getText()+"' AND id_usuario = '"+
                user.getId()+"'";
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

    /**
     * verificação para a existência de refeições com o nome passado como argumento
     * @param nome
     * @return
     */
    public boolean existeRefeiçao(String nome){
        boolean existe = false;
        for(RefeicaoData r: addRefeiçaoList()){
            if (r.getNome().toUpperCase().equals(nome.toUpperCase())){
                existe = true;
            }
        }
        return existe;
    }

    /**
     * Método que remove a refeição especificada do banco de dados de refeições cadastradas
     */
    public void deletaRefeiçaoListaGeral(){
        String sql = "DELETE FROM refeiçoes WHERE nome = '"+ nomeNovaRefeiçao.getText()+"'AND id_usuario = '"
                        +user.getId()+"'";
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

    /**
     * Método que exclui a refeição especificada do dias da semana
     * @param nome
     */
    public void deletaRefeiçaoDosDias(String nome){
        String sql = "DELETE FROM refeiçao_dia WHERE nome_refeiçao = '"+nome+"' AND id_usuario = '" +user.getId()+"'";
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

    /**
     * Método que realiza uma consulta Sql para obter todos os alimentos do banco de dados e transforma-los
     * em entidades do tipo AlimentoData
     * @return
     */
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

    /**
     * Método que a partir da lista de alimentos gerada pela consulta sql, faz a inserção de todos na tableView de alimentos
     */
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

    /**
     * Método que adiciona uma refeição a um dia da semana, Relacionando-os através de uma tabela no banco de dados
     * que possui um Inteiro para o dia da semana e o nome da refeição (M:N)
     * @param dia
     */
    private void adicionaBd(int dia){
        String sql ="INSERT INTO refeiçao_dia"
                + "(id_usuario,Dia_semana,nome_refeiçao)"
                + "VALUES(?,?,?)";
        connect= Database.connectDb();
        Alert alert;
        try{
            for(RefeicaoData r:listaRefeiçoes){
                if(r.getNome().equalsIgnoreCase(nomeRefeiçaoAdc.getText())){
                    prepare= connect.prepareStatement(sql);
                    prepare.setString(1,String.valueOf(user.getId()));
                    prepare.setString(2,String.valueOf(dia));
                    prepare.setString(3,nomeRefeiçaoAdc.getText());
                    prepare.executeUpdate();
                    return;

                }
            }
            alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Refeição inexistente");
            alert.setHeaderText(null);
            alert.setContentText("Refeição não encontrada.");
            alert.showAndWait();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBd(int dia){
        String sql= "DELETE FROM refeiçao_dia WHERE id_usuario =? AND Dia_semana = ? AND nome_refeiçao = ?";
        connect=Database.connectDb();
        try{
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(user.getId()));
            prepare.setString(2, String.valueOf(dia));
            prepare.setString(3, nomeRefeiçaoAdc.getText());
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
    
    /**
     * Método que define os dados do dashboard de acordo com o button do dia da semana selecionado pelo usuário.
     * Chama o método que realiza os cálculos e inserções passando um inteiro correspondente ao dia da semana
     * 1 = segunda-feira ; 2 = Terça-feira; 3= quarta-feira ... 7 = domingo
     */
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
    /**
     * Método complementar ao método acima. Recebe o dia como parâmetro e realiza a consulta Sql para  obter as informações
     * do banco de dados
     */
    public void configureTableDia(int dia){
        try{
            ObservableList<RefeicaoData> refeiçoesDia= FXCollections.observableArrayList();
            String sql = "SELECT nome_refeiçao FROM refeiçao_dia WHERE id_usuario=? AND Dia_semana = ?";
            connect= Database.connectDb();
            prepare= connect.prepareStatement(sql);
            prepare.setString(1,String.valueOf(user.getId()));
            prepare.setString(2,String.valueOf(dia));
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
    /**
     * Método responsável por receber a lista de refeições presentes no dia selecionado e inseri-los na TableView
     */
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
    /**
     * Método responsável por inicializar a tabela de dados nutricionais do dia selecionado pelo usuário
     */
    public void initNutrientesDia(ObservableList<RefeicaoData> lista){
        if(!lista.isEmpty()) {
            for (RefeicaoData r : lista) {
                caloriasDiaF += r.getCalorias();
                carboidratosDiaF += r.getCarboidratos();
                proteinasDiaF += r.getProteinas();
                gordurasDiaF += r.getGorduras();
                fibrasDiaF += r.getFibras();
            }
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
    /**
     * Método que realiza uma consulta Sql para recuperar todas as refeições criadas pelo usuário e transforma-las em entidades RefeiçãoData
     */
    public ObservableList<RefeicaoData> addRefeiçaoList(){
        ObservableList<RefeicaoData> listaRefeiçoes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM refeiçoes WHERE id_usuario= '"
                        + user.getId()+"'";
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
                + "(id_usuario,nome,a1,a2,a3,a4,a5,a6,a7,a8) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
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
                        + nomeNovaRefeiçao.getText() + "' AND id_usuario = '"
                        + user.getId()+"'";

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
                    prepare.setString(1,String.valueOf(user.getId()));
                    prepare.setString(2,nomeNovaRefeiçao.getText());
                    prepare.setString(3, a1add.getText()+"|"+a1peso.getText());
                    prepare.setString(4, a2add.getText()+"|"+a2peso.getText());
                    prepare.setString(5, a3add.getText()+"|"+a3peso.getText());
                    prepare.setString(6, a4add.getText()+"|"+a4peso.getText());
                    prepare.setString(7, a5add.getText()+"|"+a5peso.getText());
                    prepare.setString(8, a6add.getText()+"|"+a6peso.getText());
                    prepare.setString(9, a7add.getText()+"|"+a7peso.getText());
                    prepare.setString(10, a8add.getText()+"|"+a8peso.getText());
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

    /**
     * Método que remove a seleção dos checkBox
     */
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

    /**
     * Método para a inserção dos dados da refeição, passada como argumento, no gráfico.
     * @param selectedRow
     */
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

    /**
     * Método responsável pela troca de páginas, a partir do botão lateral selecionado pelo usuário
     * @param event -- click do usuário
     */
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
            addRefeiçoesTable();
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
