import com.ovoc01.dao.java.MyConnection;
import element.Composition;
import element.Fabrication;
import element.Product;
import stock.Transaction;

import java.sql.Connection;
import java.util.Vector;

public class Main {
    static String host="localhost";
    static String port="5432";
    static String usr="rakharrs";
    static String dbname="star";
    static String pwd="pixel";
    public static void main(String[] args) throws Exception {
        Connection c = MyConnection.createPostGresConnection(host,port,usr,pwd,dbname);

        //maka ny produit rehetra
        //Vector<Product>productVector = new Product().select(c);
        //System.out.println(productVector.get(4).calculPrix(c));
        //Transaction transaction1 = new Transaction("C000001",true,"2022-01-18",200,4120.0);
        //transaction1.insert(c);
        //c.commit();

        Transaction transaction = new Transaction();
        transaction.setIdProduit("C000001");
        transaction.setIsEntering(true);
        Vector<Transaction> transactionVector = transaction.select(c);
        transaction.setIsEntering(false);
        Vector<Transaction> transactionVector1 = transaction.select(c);

        System.out.println(transactionVector.get(0).getPriceUnitaire());
        System.out.println(transactionVector1.get(0).getPriceUnitaire());

        double cumpEntry = 0;
        double quantity =0;
        for (int i = 0; i <transactionVector.size() ; i++) {
            cumpEntry+=transactionVector.get(i).getPriceUnitaire()*transactionVector.get(i).getQuantity();
            quantity+=transactionVector.get(i).getQuantity();
        }
        System.out.println(cumpEntry/quantity);
        c.close();
    }
}