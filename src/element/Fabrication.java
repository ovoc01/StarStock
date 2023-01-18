package element;

import com.ovoc01.dao.annotation.Column;
import com.ovoc01.dao.annotation.ForeignKey;
import com.ovoc01.dao.annotation.Nummer;
import com.ovoc01.dao.annotation.Tables;
import com.ovoc01.dao.java.ObjectDAO;

@Tables
public class Fabrication extends ObjectDAO {
    @Column
    @ForeignKey(classReference = Composition.class)
    String id_produit;
    @Column
    @ForeignKey(classReference = Composition.class)
    String id_composant;
    @Column(name = "quantity")
    @Nummer
    Double quantite;

    public String getId_produit() {
        return id_produit;
    }

    public void setId_produit(String id_produit) {
        this.id_produit = id_produit;
    }

    public String getId_composant() {
        return id_composant;
    }

    public void setId_composant(String id_composant) {
        this.id_composant = id_composant;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Fabrication() {

    }
}
