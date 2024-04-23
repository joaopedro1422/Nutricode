package com.example.nutricode_;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

import javafx.scene.image.Image;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRResourcesUtil;
import net.sf.jasperreports.view.JasperViewer;

public class JasperReportGenerate {

    public static final String arquivoJasper = "/src/main/resources/relatorios/RelatorioNutricode.jasper";
    public static final String logoImg = "src/main/resources/img/LogoNutricode.png";

    public String formataNome(String nome){
        String[] partes = nome.split(" ");
        return partes[0]+partes[1];
    }

    public void gerarRelatorio(Usuario usuario, HashMap<String,Object> dadosDousuario){

        double peso= usuario.getPeso();
        double altura = usuario.getAltura();
        double imc = peso / (altura*altura);
        String feedBack = analisaImc(imc);
        String nomeUsuarioFormatado = formataNome(usuario.getNome());
        HashMap<String,Object> paramsJasper = new HashMap<>();
        paramsJasper.put("nome", usuario.getNome());
        paramsJasper.put("cpf", usuario.getCpf());
        paramsJasper.put("peso", usuario.getPeso());
        paramsJasper.put("altura", usuario.getAltura());
        paramsJasper.put("imc", String.format("%.1f",imc));
        paramsJasper.put("feedback", feedBack);
        paramsJasper.put("caloriasPorDia", dadosDousuario.get("caloriasDia").toString()+"kcal");
        paramsJasper.put("carboidratosPorDia", dadosDousuario.get("carboidratosDia").toString()+"g");
        paramsJasper.put("proteinasPorDia",dadosDousuario.get("proteinasDia").toString()+"g");
        paramsJasper.put("gordurasPorDia", dadosDousuario.get("gordurasDia").toString()+"g");
        paramsJasper.put("fibrasPorDia", dadosDousuario.get("fibrasDia").toString()+"g");
        paramsJasper.put("qntRefeiçoes", dadosDousuario.get("qntRefeiçoes").toString());
        paramsJasper.put("caloriasSegunda", dadosDousuario.get("caloriasSegunda").toString());
        paramsJasper.put("caloriasTerça", dadosDousuario.get("caloriasTerça").toString());
        paramsJasper.put("caloriasQuarta", dadosDousuario.get("caloriasQuarta").toString());
        paramsJasper.put("caloriasQuinta", dadosDousuario.get("caloriasQuinta").toString());
        paramsJasper.put("caloriasSexta", dadosDousuario.get("caloriasSexta").toString());
        paramsJasper.put("caloriasSabado", dadosDousuario.get("caloriasSabado").toString());
        paramsJasper.put("caloriasDomingo", dadosDousuario.get("caloriasDomingo").toString());
        paramsJasper.put("gordurasSegunda", dadosDousuario.get("gordurasSegunda").toString());
        paramsJasper.put("gordurasTerça", dadosDousuario.get("gordurasTerça").toString());
        paramsJasper.put("gordurasQuarta", dadosDousuario.get("gordurasQuarta").toString());
        paramsJasper.put("gordurasQuinta", dadosDousuario.get("gordurasQuinta").toString());
        paramsJasper.put("gordurasSexta", dadosDousuario.get("gordurasSexta").toString());
        paramsJasper.put("gordurasSabado", dadosDousuario.get("gordurasSabado").toString());
        paramsJasper.put("gordurasDomingo", dadosDousuario.get("gordurasDomingo").toString());
        paramsJasper.put("logoNutricode", getLogoImg());

        try{
            Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
            String filePath = desktopPath.resolve("Relatorio"+nomeUsuarioFormatado+".pdf").toString();
            Path currentRelativePath = Paths.get("");
            String jasperFilePath = currentRelativePath.toAbsolutePath().toString()+arquivoJasper;
            System.out.println(jasperFilePath);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperFilePath,paramsJasper,new JREmptyDataSource());
            byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
            writeBytesToFile(filePath, report);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void writeBytesToFile(String fileOutput, byte[] bytes){
        try{
            Path path = Paths.get(fileOutput);
            Files.write(path,bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static synchronized java.awt.Image getLogoImg(){
        java.awt.Image image = null;
        InputStream inputStream=null;
        try{
            File initialFile = new File(logoImg);
            inputStream= new FileInputStream(initialFile);
            byte[] bytesImage= inputStream.readAllBytes();
            ImageIcon imageIcon= new ImageIcon(bytesImage);
            image= imageIcon.getImage();
            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }


    public String analisaImc(double imc){
        String feedBack="";
        if (imc < 18.5) {
            feedBack= "Abaixo do peso";
        } else if (imc < 25) {
            feedBack= "Peso normal";
        } else if (imc < 30) {
            feedBack= "Sobrepeso";
        } else if (imc < 35) {
            feedBack= "Obesidade Grau I";
        } else if (imc < 40) {
            feedBack ="Obesidade Grau II (severa)";
        } else {
            feedBack ="Obesidade Grau III (mórbida)";
        }
        return feedBack;
    }
}
