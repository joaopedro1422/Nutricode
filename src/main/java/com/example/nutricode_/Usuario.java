package com.example.nutricode_;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private double altura;
    private float peso;
    public Usuario(int id, String nome, String login, String senha, String cpf, float peso, double altura) {
        this.id=id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf =cpf;
        this.peso = peso;
        this.altura= altura;
    }

    public String getCpf() {
        return cpf;
    }

    public double getAltura() {
        return altura;
    }

    public float getPeso() {
        return peso;
    }

    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
