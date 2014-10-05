package org.damcode.damecom.dbao;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.bson.types.ObjectId;

/**
 *
 * @author dm
 *
 * helper object to retrieve and insert customer orders
 *
 */
public class OrderDbao {

    private final DB db;
    private final DBCollection dbCollectionCust;

    public OrderDbao() {
        db = DamEcomDbSingleton.INSTANCE.getDb();
        dbCollectionCust = db.getCollection("customers");
    }

    public ObjectId placeOrder(ArrayList<HashMap> orderItems, ObjectId customerId) {
        ObjectId orderId = new ObjectId();
        BasicDBObject ins = new BasicDBObject("$push", new BasicDBObject("purchases",
                new BasicDBObject("orderid", orderId).
                append("orderdate", new Date()).
                append("order", orderItems)
        ));
        BasicDBObject qry = new BasicDBObject("_id", customerId);
        try {
            WriteResult update = dbCollectionCust.update(qry, ins);
            System.out.println("placeOrder(): " + update);
            return orderId;
        } catch (MongoException ex) {
            System.out.println("Write failed: " + ex);
        }
        return null;
    }

    public DBObject getSpecificOrder(ObjectId orderId, ObjectId customerId) {

        BasicDBObject qry = new BasicDBObject("_id", customerId);
        BasicDBObject proj = new BasicDBObject("purchases",
                new BasicDBObject("$elemMatch",
                        new BasicDBObject("orderid", orderId))).
                append("_id",0);
        
        return dbCollectionCust.findOne(qry, proj);

// db.customers.find({_id:ObjectId("53720d7d3d555e1c131ba4a1")},{"purchases":{ $elemMatch : { "orderid":ObjectId("537c6f433d557ddca740bc22")}}, "_id":0
//}).pretty()
    }

    public ArrayList<HashMap> getOrderList(ObjectId customerId) {
        ArrayList<HashMap> orderList = null;

        try {
            BasicDBObject qry = new BasicDBObject("_id", customerId);
            BasicDBObject proj = new BasicDBObject("purchases.orderid", 1).
                    append("purchases.orderdate", 1).
                    append("_id", 0);

            DBObject result = dbCollectionCust.findOne(qry, proj);

            BasicDBList res = (BasicDBList) result.get("purchases");
            orderList = new ArrayList<HashMap>();

            for (Iterator<Object> it = res.iterator(); it.hasNext();) {
                orderList.add((HashMap) it.next());
            }

        } catch (MongoException ex) {
            System.out.println("order query exception: " + ex);
        }

        return orderList;

    }

//eof
}
