package org.damcode.damecom.dbao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dm
 */
public enum DamEcomDbSingleton {

    INSTANCE;

    private MongoClient dbClient;

    private DamEcomDbSingleton() {
        System.out.println(Thread.currentThread().getStackTrace()[1] +" @ "+ new Date());
        try {
            dbClient = new MongoClient();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ProdDbao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return instance of MongoClient("server").getDB("database")
     */
    public DB getDb() {
        DB db = null;
        if (dbClient == null) {
            try {
                dbClient = new MongoClient();
                db = dbClient.getDB("damecom");                
            } catch (UnknownHostException ex) {
                Logger.getLogger(ProdDbao.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            db = dbClient.getDB("damecom");
        }
        
        return db;
    }

    /*
     @param shutdown connection
     */
    public void shutdown() {
        System.out.println(Thread.currentThread().getStackTrace()[1] +" @ "+ new Date());
        dbClient.close();
        dbClient = null;
    }

}
