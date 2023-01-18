package com.ovoc01.dao.java;

import com.ovoc01.dao.annotation.ForeignKey;
import com.ovoc01.dao.annotation.Nummer;
import com.ovoc01.dao.annotation.PrimaryKey;

import com.ovoc01.dao.excetpion.NullValue;
import com.ovoc01.dao.utilities.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
@SuppressWarnings("unused")
public class ObjectDAO {

    String host;
    String port;
    String user;
    String pwd;
    String dbName;
    String table;

    Vector foreignKey;

    public Vector getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Vector foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public ObjectDAO(){
        table= Utilities.setTable(this);
    }


    /**
     * insert in case of a transaction
     * @param c Connexion
     * */
    public void insert(Connection c) throws Exception{
        Utilities.prepareSql(c);
        Statement statement = c.createStatement();
        statement.execute(insertQuery());
        System.out.println("insert done");
    }

    /**
     * function how create a query for insertion of this object in database
     * */
    @SuppressWarnings("ReassignedVariable")
    String insertQuery() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NullValue {
        table = Utilities.setTable(this);
        StringBuilder query = new StringBuilder(String.format("insert into %s values(", table));
        Vector<Field> list = Utilities.getFieldToInsert(this);
        //Field primaryKey = Intermediate.getPrimaryKeyIndex(this);
        for (int i = 0; i < list.size() ; i++) {
            if(list.get(i).isAnnotationPresent(PrimaryKey.class)){
                PrimaryKey primaryKey1 = list.get(i).getAnnotation(PrimaryKey.class);
                query.append(String.format("getiddb('%s','%s')", primaryKey1.seqComp(), primaryKey1.prefix()));
            }
           else if(list.get(i).isAnnotationPresent(Nummer.class)){
               query.append(Utilities.getterValue(this, list.get(i).getName()));
           }else {
               query.append("'").append(Utilities.getterValue(this, list.get(i).getName())).append("'");
           }
           if(i+1< list.size()) query.append(',');
        }
        query.append(")");
         System.out.println(query);
        return query.toString();
    }

    public void update(Connection c) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NullValue, SQLException {
        String query = updateQuery();
        Statement statement = c.createStatement();
        //System.out.println(updateQuery());
        statement.execute(updateQuery());
        System.out.println("update done");
    }



    String updateQuery() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullValue {
        StringBuilder query = new StringBuilder(String.format("update  %s set ", table));
        Vector<Field> fields = Utilities.getNotNullField(this);
        Field primaryKey = Utilities.getPrimaryKeyIndex(this);
        for (int i =0;i< fields.size();i++) {
            String column = Utilities.getColmunName(fields.get(i));
            String method = Utilities.createGetter(fields.get(i).getName());
            Object object = getClass().getDeclaredMethod(method).invoke(this);
            if (fields.get(i).isAnnotationPresent(Nummer.class)) {
                query.append(column).append("=").append(object);
            }else{
                query.append(column).append("='").append(object).append("'");
            }
            if(i+1< fields.size()) query.append(',');
        }
        query.append(" where ").
                append(Utilities.getColmunName(primaryKey)).append("=").append("'").
                append(Utilities.getterValue(this, Utilities.getColmunName(primaryKey))).append("'");
         return query.toString();
    }

    /**
     * function how create a vector of object based on the table appropriate to this object
     * @return <h1>Vector of object</h1>
     * */
    @SuppressWarnings({"rawused","rawtypes"})
    public Vector select(Connection c) throws Exception{
        Statement statement = c.createStatement();
       // System.out.println(selectQuery());
        ResultSet rs = statement.executeQuery(selectQuery());
        Vector list = new Vector();
        while(rs.next()){
             list.add(Utilities.createObject(this,rs));
        }
        return list;
    }

    public Vector select(Connection c, String predicat) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Statement statement = c.createStatement();
        System.out.println(predicat);
        ResultSet rs = statement.executeQuery(predicat);
        Vector list = new Vector();
        while(rs.next()){
            list.add(Utilities.createObject(this,rs));
        }
        return list;
    }

    private String selectQuery() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        StringBuilder query = new StringBuilder(String.format("select * from %s ", table));
        Vector<Field> listNotNull = Utilities.getNotNullField(this);
        int i = 0;
        if(listNotNull.size()>0){
            query.append("where ");
            for (Field field: listNotNull) {
                String column = Utilities.getColmunName(field);
                String method = Utilities.createGetter(field.getName());
                Object object = getClass().getDeclaredMethod(method).invoke(this);
                if(field.isAnnotationPresent(Nummer.class)){
                    query.append(column).append("=").append(object);
                }else{
                    query.append(column).append("='").append(object+"'");
                }

                if(i+1< listNotNull.size()) query.append(" and ");
                i++;
            }
        }

        return query.toString();
    }


    public void delete(Connection c) throws SQLException, NullValue, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Statement statement = c.createStatement();
        System.out.println(deleteQuery());
        statement.execute(deleteQuery());
        System.out.println("delete from database");
    }



   public String deleteQuery() throws NullValue, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        StringBuilder query = new StringBuilder(String.format("delete from  %s  ", table));
        Field primaryKey = Utilities.getPrimaryKeyIndex(this);
        String column = Utilities.getColmunName(primaryKey);
        String method = Utilities.createGetter(primaryKey.getName());
        Object object = getClass().getDeclaredMethod(method).invoke(this);
        query.append("where").append(" ").append(column+"=").append("'"+object+"'");
        return query.toString();
    }
    public Vector fkToObject(Connection c) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NullValue, SQLException {
        Vector<Field> listFk = Utilities.findAllForeignKey(this);
        Vector fk = new Vector();
        for (Field field : listFk) {
            ForeignKey foreignKey1 = field.getAnnotation(ForeignKey.class);
            String temp = Utilities.getColmunName(field);
            String val = String.valueOf(Utilities.getterValue(this,temp));
            Object t = foreignKey1.classReference().newInstance();
            System.out.println(Utilities.queryFind(t,val));
            ResultSet rs = c.createStatement().executeQuery(Utilities.queryFind(t,val));
            while (rs.next()){
                fk.add(Utilities.createObject(t,rs));
            }
        }
        return fk;
    }
}
