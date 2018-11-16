/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.imatge;

/**
 *
 * @author annagarcia-nieto
 */
public class Imatge {
    private int id;
    private String titol;
    private String autor;
    private String descripcio;
    private String dataCreacio;
    private String keywords;
    
    
    public Imatge() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setDataCreacio(String dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public String getTitol() {
        return titol;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getDataCreacio() {
        return dataCreacio;
    }
    
    public String getKeywords() {
        return keywords;
    }   
}
