package element;

import com.ovoc01.dao.annotation.Column;
import com.ovoc01.dao.annotation.Nummer;
import com.ovoc01.dao.annotation.PrimaryKey;
import com.ovoc01.dao.annotation.Tables;
import com.ovoc01.dao.java.ObjectDAO;

import java.sql.Connection;
import java.util.Vector;

@Tables(name = "composant")
public class Composition extends ObjectDAO {

    @PrimaryKey(prefix = "C",seqComp = "compSeq")
    @Column
    String idComposant;
    @Column
    String name;
    @Column
    String unite;
    @Column
    Boolean isPrimary;
    @Column
    @Nummer
    Double prix;

    Double quantity;
    Vector<Composition> compositions;

    public String getIdComposant() {
        return idComposant;
    }

    public void setIdComposant(String idComposant) {
        this.idComposant = idComposant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Vector<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(Vector<Composition> compositions) {
        this.compositions = compositions;
    }

    public Composition(){

    }

    public void getPrimaryComposition(Connection c,Vector<Composition> comp) throws Exception {
        if(getIsPrimary()){
            setPrix(getPrix()*getQuantity());
            comp.add(this);
        //  System.out.println(this.getPrix());
            return ;
        }
        Fabrication fab = new Fabrication(); fab.setId_produit(getIdComposant());
        Vector<Fabrication> fabricationVector = fab.select(c);
        for (Fabrication fabrication: fabricationVector) {
            fabrication.setQuantite(fabrication.getQuantite()*getQuantity());
            Composition temp = new Composition();temp.setIdComposant(fabrication.getId_composant());
            Vector<Composition>compositionVector = temp.select(c);
            for (Composition com: compositionVector) {
                com.setQuantity(fabrication.getQuantite());
                com.getPrimaryComposition(c,comp);
            }
        }
    }

}
