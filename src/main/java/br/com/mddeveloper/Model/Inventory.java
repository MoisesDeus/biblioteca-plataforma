package br.com.mddeveloper.Model;

public class Inventory {
    private int id;
    private int idCatalog;
    private String status;

    public Inventory(int id, int idCatalog, String status) {
        this.id = id;
        this.idCatalog = idCatalog;
        this.status = status;
    }

    public Inventory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
