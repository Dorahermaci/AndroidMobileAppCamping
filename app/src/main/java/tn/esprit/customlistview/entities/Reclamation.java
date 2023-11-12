package tn.esprit.customlistview.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity


public class Reclamation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String objet;
    private String contenu;
    private String date;

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Reclamation(String objet, String contenu, String date) {
        this.objet = objet;
        this.contenu = contenu;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
