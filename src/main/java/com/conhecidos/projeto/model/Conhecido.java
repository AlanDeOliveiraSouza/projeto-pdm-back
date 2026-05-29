package com.conhecidos.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import com.conhecidos.projeto.model.Coordenada;

public class Conhecido {

    private Integer id;
    private String nome;
    private Integer idade;

    // Garante que a conversão para json torne a data uma string "yyyy-MM-dd" em vez de array ['yyyy', 'MM', 'dd']
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataConheceu;

    private String imagem;
    private String ocasiao;
    private String genero;
    private Coordenada coordenada;

    public Conhecido(){}

    public Conhecido(Integer id, String nome, Integer idade, LocalDate dataConheceu, String imagem, String ocasiao, String genero, Coordenada coordenada){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.dataConheceu = dataConheceu;
        this.imagem = imagem;
        this.ocasiao = ocasiao;
        this.genero = genero;
        this.coordenada = coordenada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public LocalDate getDataConheceu() {
        return dataConheceu;
    }

    public void setDataConheceu(LocalDate dataConheceu) {
        this.dataConheceu = dataConheceu;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getOcasiao() {
        return ocasiao;
    }

    public void setOcasiao(String ocasiao) {
        this.ocasiao = ocasiao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    @Override
    public String toString() {
        String resultado = "Conhecido: " + id + " - " + nome + " - " + idade + " - " + dataConheceu + " - " + ocasiao + " - " + genero + "\nCoordenadas Imagem: " + coordenada.toString();
        return resultado;
    }

    public static boolean[] compararConhecido(Conhecido conhecido, Conhecido conhecidoBanco) {
        boolean[] comparacao = {false, false, false, false, false, false};

        if(conhecido.getNome().equals(conhecidoBanco.getNome()) && conhecido.getNome() != null) { comparacao[0] = true; }
        if(conhecido.getIdade().equals(conhecidoBanco.getIdade()) && conhecido.getIdade() != null) { comparacao[1] = true; }
        if(conhecido.getDataConheceu().isEqual(conhecidoBanco.getDataConheceu()) && conhecido.getDataConheceu() != null) { comparacao[2] = true; }
        if(conhecido.getImagem().equals(conhecidoBanco.getImagem()) && conhecido.getImagem() != null) { comparacao[3] = true; }
        if(conhecido.getOcasiao().equals(conhecidoBanco.getOcasiao()) && conhecido.getOcasiao() != null) { comparacao[4] = true; }
        if(conhecido.getGenero().equals(conhecidoBanco.getGenero()) && conhecido.getGenero() != null) { comparacao[5] = true; }

        return comparacao;
    }

}
