package org.damcode.damecom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;

/**
 *
 * @author dm
 */
public class DamECustomer {

    private final ObjectId id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String weblogin;
    private final String address;
    private final Date lastlogin;
    private ArrayList<HashMap> basket;

    public DamECustomer(HashMap c) {
        id = (ObjectId) c.get("_id");
        firstname = (String) c.get("name_first");
        lastname = (String) c.get("name_last");
        email = (String) c.get("email");
        weblogin = (String) c.get("web_login");
        address = (String) c.get("address");
        lastlogin = (Date) c.get("last_login");
        basket = (ArrayList) c.get("saved_basket");
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the weblogin
     */
    public String getWeblogin() {
        return weblogin;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the lastlogin
     */
    public Date getLastlogin() {
        return lastlogin;
    }

    @Override
    public String toString() {
        return "DamECustomer{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", weblogin=" + weblogin + ", address=" + address + ", lastlogin=" + lastlogin + '}';
    }

    /**
     * @return the savedbasket
     */
    public ArrayList<HashMap> getBasket() {
        return basket;
    }

    public void setBasket(ArrayList<HashMap> array) {
        this.basket = array;
    }

}
