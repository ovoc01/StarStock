package stock;

import com.ovoc01.dao.annotation.Column;
import com.ovoc01.dao.annotation.ForeignKey;
import com.ovoc01.dao.annotation.Nummer;
import com.ovoc01.dao.annotation.Tables;
import com.ovoc01.dao.java.ObjectDAO;
import element.Product;

@Tables
public class Stock extends ObjectDAO {
    @Column
    String dateStock;

    @Column
    @Nummer
    Integer quantity;

    @Column
    @Nummer
    Double priceUnitaire;
    @Column
    @ForeignKey(classReference = Product.class)
    String idProduit;

    public String getDateStock() {
        return dateStock;
    }

    public void setDateStock(String dateStock) {
        this.dateStock = dateStock;
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

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

}
