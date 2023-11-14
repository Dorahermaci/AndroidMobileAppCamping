package com.example.camping.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Evenement {
    private long id;
    private String nom;
    private Date debut;
    private Date fin;
    private String imagePath; // Add this line for storing the image path

    public Evenement() {
    }

    public Evenement(String nom, Date debut, Date fin, String imagePath) {
        this.nom = nom;
        this.debut = debut;
        this.fin = fin;
        this.imagePath = imagePath;
    }

    public Evenement(long id, String nom, Date debut, Date fin, String imagePath) {
        this.id = id;
        this.nom = nom;
        this.debut = debut;
        this.fin = fin;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error appropriately in your application
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy", Locale.US);
        return dateFormat.format(date);
    }
}
