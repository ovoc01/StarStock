package element;

import com.ovoc01.dao.annotation.*;
import com.ovoc01.dao.java.ObjectDAO;

import java.sql.Connection;
import java.util.Vector;

@Tables(name = "produits")
public class Product extends ObjectDAO {
    @Column
    @PrimaryKey(prefix = "P",seqComp = "productSeq")
    String idProduit;

    @Column
    @ForeignKey(classReference = Composition.class)
    String reference;
    @Column
    @Nummer
    Double quantite;

    Composition self;

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Composition getSelf() {
        return self;
    }

    public void setSelf(Composition self) {
        this.self = self;
    }

    public Double calculPrix(Connection c) throws Exception {
        Double price = 0.0;
        Fabrication fabrication = new Fabrication();
        fabrication.setId_produit(getReference());
        Vector<Fabrication> fabricationVector = fabrication.select(c);
        for (Fabrication fabrication1: fabricationVector) {
            Composition comp = new Composition();comp.setIdComposant(fabrication1.getId_composant());
            Vector<Composition> compositions = comp.select(c);
            Vector<Composition> t = new Vector<>();
            compositions.get(0).setQuantity(fabrication1.getQuantite());
            compositions.get(0).getPrimaryComposition(c,t);
            compositions.get(0).setCompositions(t);
            for (int j = 0; j < t.size(); j++) {
                price+=(compositions.get(0).getCompositions().get(j).getPrix());
            }
        }
        return price;
    }
}
