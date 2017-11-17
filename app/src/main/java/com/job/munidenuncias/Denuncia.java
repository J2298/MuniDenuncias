package com.job.munidenuncias;

public class Denuncia {
    private Integer id;
    private Integer usuarios_id;
    private String titulo;
    private String imagen;
    private String detalles;
    private String lat;
    private String lon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarios_id() {
        return usuarios_id;
    }

    public void setUsuarios_id(Integer usuarios_id) {
        this.usuarios_id = usuarios_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", usuarios_id=" + usuarios_id +
                ", titulo='" + titulo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", detalles='" + detalles + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}

