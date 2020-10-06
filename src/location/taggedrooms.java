/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package location;

/**
 *
 * @author ishma
 */
public class taggedrooms {

    String id;
    String tag;

    public taggedrooms(String id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public String getid() {
        return id;
    }

    public String gettag() {
        return tag;
    }
}
