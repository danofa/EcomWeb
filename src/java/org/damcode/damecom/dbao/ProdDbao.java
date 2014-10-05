package org.damcode.damecom.dbao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;

/**
 *
 * @author dm
 */
public class ProdDbao {

    private final DB db;
    private final DBCollection dbCollection;

    @Override
    protected void finalize() throws Throwable {
        try {
            DamEcomDbSingleton.INSTANCE.shutdown();
        } finally {
            super.finalize();
        }
    }

    public ProdDbao() {
        db = DamEcomDbSingleton.INSTANCE.getDb();
        dbCollection = db.getCollection("products");
    }

    public void closeAll() {
        DamEcomDbSingleton.INSTANCE.shutdown();
    }

    public HashMap getProductById(String id) {
        BasicDBObject dbo = null;
        HashMap prod = null;

        try {
            dbo = new BasicDBObject("_id", new ObjectId(id));
        } catch (IllegalArgumentException e) {
            System.out.println("ObjectID not valid: " + e);
        }

        DBCursor curs = dbCollection.find(dbo);
        try {
            while (curs.hasNext()) {
                prod = new HashMap((Map) curs.next());//.toMap());
            }
        } finally {
            curs.close();
        }

        return prod;
    }

    public List getProductTypes() {
        return new ArrayList();
    }

    public List<HashMap> getProductsByType(String type) {
        BasicDBObject dbo = new BasicDBObject("type", type);
        List<HashMap> prodList = new ArrayList<HashMap>();
        DBCursor curs = dbCollection.find(dbo);
        while (curs.hasNext()) {
            prodList.add(new HashMap(curs.next().toMap()));
        }

        curs.close();
        return prodList;
    }

    private List<HashMap> getProducts(BasicDBObject dbo) {
        List<HashMap> prodList = new ArrayList<HashMap>();
        DBCursor curs = dbCollection.find(dbo);
        while (curs.hasNext()) {
            prodList.add(new HashMap(curs.next().toMap()));
        }

        curs.close();
        return prodList;

    }

    public List<HashMap> getProducts() {
        DBCursor curs = dbCollection.find();
        List<HashMap> prodList = new ArrayList<HashMap>();

        while (curs.hasNext()) {
            prodList.add(new HashMap(curs.next().toMap()));
        }

        curs.close();
        return prodList;
    }
}
