package stock;

import com.ovoc01.dao.annotation.*;
import com.ovoc01.dao.java.ObjectDAO;
import element.Composition;
import element.Fabrication;
import element.Product;

import java.lang.Boolean;
import java.sql.Connection;

@Tables
public class Transaction extends ObjectDAO {
    @Column
    @PrimaryKey(prefix = "TRS",seqComp = "transSeq")
    String idTransaction;

    @Column
    @ForeignKey(classReference = Composition.class)
    String idProduit;

    @Column
    @com.ovoc01.dao.annotation.Boolean
    Boolean isEntering;

    @Column
    String dateTransaction;

    @Column
    @Nummer
    Integer quantity;

    @Column
    @Nummer
    Double priceUnitaire;

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public Boolean getIsEntering() {
        return isEntering;
    }

    public void setIsEntering(Boolean entering) {
        isEntering = entering;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceUnitaire() {
        return priceUnitaire;
    }

    public void setPriceUnitaire(Double priceUnitaire) {
        this.priceUnitaire = priceUnitaire;
    }

    public Transaction(){

    }

    public Transaction(String idProduit, Boolean isEntering, String dateTransaction, Integer quantity, Double priceUnitaire) {
        setIdProduit(idProduit);
        setIsEntering(isEntering);
        setDateTransaction(dateTransaction);
        setQuantity(quantity);
        setPriceUnitaire(priceUnitaire);
    }

}
