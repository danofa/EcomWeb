package org.damcode.damecom.dbao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.damcode.damecom.DamECustomer;

/**
 *
 * @author dm
 */
public class CustomerDbao {

    private final DB db;
    private final DBCollection dbCollectionCust;

    public CustomerDbao() {
        System.out.println(Thread.currentThread().getStackTrace()[1] + " @ " + new Date());
        db = DamEcomDbSingleton.INSTANCE.getDb();
        dbCollectionCust = db.getCollection("customers");
    }

    /**
     * Takes basket information from the customer object and writes it to their
     * database.
     *
     * @param customer
     * @param sessionBasket
     * @return true on success, false if not.
     */
    public boolean saveBasketToDb(DamECustomer customer, ArrayList<HashMap> sessionBasket) {

        BasicDBObject upd = new BasicDBObject("saved_basket", sessionBasket);
        BasicDBObject qry = new BasicDBObject("_id", customer.getId());
        BasicDBObject set = new BasicDBObject("$set", upd);
        try {
            WriteResult update = dbCollectionCust.update(qry, set);
            System.out.println("customer session update result: " + update);

        } catch (MongoException ex) {
            return false;
        }
        return true;
    }

    /**
     * updates customer last login in database;
     *
     * @param customerId
     */
    public void updateLastLogin(ObjectId customerId) {
        BasicDBObject lastLoginUpdate = new BasicDBObject("$set", new BasicDBObject("last_login", new Date()));
        WriteResult update = dbCollectionCust.update(new BasicDBObject("_id", customerId), lastLoginUpdate);
        System.out.println(update);
    }

    /**
     * Fetches customer data from database and fills customer POJO suppresses
     * password information being retrieved from database.
     *
     * @param customerId
     * @return DamECustomer POJO with data filled if customer is found, null if
     * not found.
     */
    public DamECustomer getCustomerData(ObjectId customerId) {
        System.out.println(Thread.currentThread().getStackTrace()[1] + " @ " + new Date());
        BasicDBObject proj = new BasicDBObject("web_salt", 0).
                append("web_pass", 0);

        DBObject findOne = dbCollectionCust.findOne(customerId, proj);

        if (findOne == null) {
            System.out.println("user not found or not web enabled");
        } else {
            return new DamECustomer((HashMap) findOne);
        }

        return null;
    }
} //eof

