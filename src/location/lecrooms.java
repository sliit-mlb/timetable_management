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
public class lecrooms {

    String lec;
    String roomid;

    public lecrooms(String lec, String roomid) {
        this.lec = lec;
        this.roomid = roomid;
    }

    public String getlec() {
        return this.lec;
    }

    public String getroomid() {
        return this.roomid;
    }

}
