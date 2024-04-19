package com.example.nutricode_;

import java.util.ArrayList;

public class RefeicaoData {
    private int idRefeiçao;
    private String nome;

    private ArrayList<String> alimentos;
    private float calorias;
    private float carboidratos;
    private float proteinas;
    private float fibras;
    private float gorduras;

    public RefeicaoData(int idRefeiçao, String nome, ArrayList<String> listaAlimentos, float calorias, float carboidratos,
                        float proteinas, float fibras, float gorduras) {
        this.idRefeiçao = idRefeiçao;
        this.nome = nome;
        this.alimentos= listaAlimentos;
        this.calorias = calorias;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.fibras = fibras;
        this.gorduras = gorduras;
    }


    public int getIdRefeiçao() {
        return idRefeiçao;
    }

    public String getNome() {
        return nome;
    }

    public String getAlimentoNome(int pos){
        String alimento = alimentos.get(pos-1);
        if(alimento.equals("|")){
            return "";
        }
        else{
            String[] alimentoDecomposto = alimento.split("\\|");
            String alimentoNome = alimentoDecomposto[0];
            return alimentoNome;
        }
    }
    public String getAlimentoPeso(int pos){
        String alimento = alimentos.get(pos-1);
        String[] alimentoDecomposto = alimento.split("\\|");
        if(alimentoDecomposto[0].isEmpty()){
            return "";
        }
        else{
            String peso = alimentoDecomposto[1];
            return peso;
        }
    }

    public String getAlimentoCompleto(int pos){
        String alimento = alimentos.get(pos-1);
        String[] alimentoDecomposto = alimento.split("\\|");
        if (alimentoDecomposto[0].isEmpty()){
            return "";
        }
        else{
            String nome= alimentoDecomposto[0];
            String peso =alimentoDecomposto[1];
            return nome+"("+peso+"g"+")";
        }
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCalorias() {
        return calorias;
    }

    public float getCarboidratos() {
        return carboidratos;
    }

    public float getProteinas() {
        return proteinas;
    }

    public float getFibras() {
        return fibras;
    }

    public float getGorduras() {
        return gorduras;
    }
}
