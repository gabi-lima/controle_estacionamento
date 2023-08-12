package com.estacionamento;

import javafx.scene.control.TableColumn;

public class Estacionamento {
    private String nome;
    private String carro;
    private String placa;
    private String vaga;
    private String dataHoraAtual;
    private String tempo_vaga;

    public Estacionamento(String nome, String carro, String placa, String vaga, String tempo_vaga) {
        this.nome = nome;
        this.carro = carro;
        this.placa = placa;
        this.vaga = vaga;
        this.tempo_vaga = tempo_vaga;
    }

    public String getTempo_vaga() {
        return tempo_vaga;
    }

    public void setTempo_vaga(String tempo_vaga) {
        this.tempo_vaga = tempo_vaga;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

}
