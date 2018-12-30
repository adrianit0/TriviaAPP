package com.garcia.adrian.triviaapp.model.menu;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.model.menu.EstiloJuegoBase;

public class ModoCategoria extends EstiloJuegoBase {

    private String maximaPuntuacion;    // Para categoria
    private String totalAcertadas;      // Para categoria
    private CATEGORIA categoria;


    public ModoCategoria(String titulo, CATEGORIA categoria, String maximaPuntuacion, String totalAcertadas) {
        super(titulo);
        this.categoria = categoria;
        this.maximaPuntuacion = maximaPuntuacion;
        this.totalAcertadas = totalAcertadas;
    }

    public String getMaximaPuntuacion() {
        return maximaPuntuacion;
    }

    public void setMaximaPuntuacion(String maximaPuntuacion) {
        this.maximaPuntuacion = maximaPuntuacion;
    }

    public String getTotalAcertadas() {
        return totalAcertadas;
    }

    public void setTotalAcertadas(String totalAcertadas) {
        this.totalAcertadas = totalAcertadas;
    }

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }
}
