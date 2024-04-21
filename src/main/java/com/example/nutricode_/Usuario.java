package com.example.nutricode_;

public class Usuario {
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private double altura;
    private float peso;

    public String getCpf() {
        return cpf;
    }

    public double getAltura() {
        return altura;
    }

    public float getPeso() {
        return peso;
    }

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf ="0440303030";
        this.peso = 91;
        this.altura= 1.82;
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
