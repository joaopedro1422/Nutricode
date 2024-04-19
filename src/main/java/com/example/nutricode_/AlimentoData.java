package com.example.nutricode_;

public class AlimentoData {
    private int id;
    private String nome;
    private float carboidratos;
    private float proteinas;
    private float gorduras;
    private float fibras;
    private float calorias;

    private String imagem;

    public String getImagem() {
        return imagem;
    }

    public AlimentoData(int id, String nome, float carboidratos, float proteinas, float gorduras, float fibras, float calorias, String urlImagem) {
        this.id = id;
        this.nome = nome;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.gorduras = gorduras;
        this.fibras = fibras;
        this.calorias = calorias;
        this.imagem=urlImagem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getCarboidratos() {
        return carboidratos;
    }

    public float getProteinas() {
        return proteinas;
    }

    public float getGorduras() {
        return gorduras;
    }

    public float getFibras() {
        return fibras;
    }

    public float getCalorias() {
        return calorias;
    }
}
