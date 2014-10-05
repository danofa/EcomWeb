package org.damcode.damecom.dbao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.damcode.damecom.AuthUser;

/**
 *
 * @author dm
 */
public class AuthDbao {

    private final DB db;
    private final DBCollection dbCollectionCust;

    public AuthDbao() {
        System.out.println(Thread.currentThread().getStackTrace()[1] + " @ " + new Date());
        db = DamEcomDbSingleton.INSTANCE.getDb();
        dbCollectionCust = db.getCollection("customers");
    }
    /**
     * 
     * @param username database 'web_login'
     * @param password database 'web_pass' (hashed)
     * @return returns user id of customer if successful password/login match, null if not.
     */
    public ObjectId authUser(String username, String password) {
        DBObject findOneResult = dbCollectionCust.findOne(new BasicDBObject("web_login", username).append("webaccess", true));

        if (findOneResult == null) {
            System.out.println("_user not found or not web enabled");

        } else {
            HashMap customerData = (HashMap) findOneResult;

            boolean result = Arrays.equals(
                    AuthUser.hashPassword(password, (byte[]) customerData.get("web_salt")), // hash POST'ed password with db salt;
                    (byte[]) customerData.get("web_pass"));                                 // db hashed password
            if (result) {                                                        // hashed passwords match;

                return (ObjectId) customerData.get("_id");

            }
            System.out.println("hash password fail");
        }

        return null;
    }

} //eof
