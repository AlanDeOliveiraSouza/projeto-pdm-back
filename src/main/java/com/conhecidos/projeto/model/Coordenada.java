package com.conhecidos.projeto.model;

public class Coordenada {

    private Integer id;
    private Integer idConhecido;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double precisao;

    public Coordenada(){}

    public Coordenada(Integer id, Integer idConhecido, Double latitude, Double longitude, Double altitude, Double precisao) {
        this.id = id;
        this.idConhecido = idConhecido;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.precisao = precisao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdConhecido() {
        return idConhecido;
    }

    public void setIdConhecido(Integer idConhecido) {
        this.idConhecido = idConhecido;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Double precisao) {
        this.precisao = precisao;
    }

    @Override
    public String toString() {
        String resultado = "Id - " + getId() + ", Id Conhecido - " + getIdConhecido() + ", Latitude - " + getLatitude() + ", Longitude - " + getLongitude() + ", Altitude - " + getAltitude() + ", Precisão - " + getPrecisao();
        return resultado;
    }

    public static boolean[] compararCoordenada(Coordenada coordenada, Coordenada coordenadaBanco) {
        boolean[] comparacao = {false, false, false, false, false};

        if(coordenada.getIdConhecido().equals(coordenadaBanco.getIdConhecido()) && coordenada.getIdConhecido() != null) { comparacao[0] = true; }
        if(coordenada.getLatitude().equals(coordenadaBanco.getLatitude()) && coordenada.getLatitude() != null) { comparacao[1] = true; }
        if(coordenada.getLongitude().equals(coordenadaBanco.getLongitude()) && coordenada.getLongitude() != null) { comparacao[2] = true; }
        if(coordenada.getAltitude().equals(coordenadaBanco.getAltitude()) && coordenada.getAltitude() != null) { comparacao[3] = true; }
        if(coordenada.getPrecisao().equals(coordenadaBanco.getPrecisao()) && coordenada.getPrecisao() != null) { comparacao[4] = true; }

        return comparacao;
    }

}
