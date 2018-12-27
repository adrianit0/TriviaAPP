package com.garcia.adrian.triviaapp.model.menu;

import com.garcia.adrian.triviaapp.model.menu.EstiloJuegoBase;

public class ModoCategoria extends EstiloJuegoBase {

    private String maximaPuntuacion;    // Para categoria
    private String totalAcertadas;      // Para categoria


    public ModoCategoria(String titulo, String maximaPuntuacion, String totalAcertadas) {
        super(titulo);
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
}
