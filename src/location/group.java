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
public class group {

    private String group;
    private String subgroup;
    private String roomid;

    public group(String group, String subgroup, String roomid) {
        this.group = group;
        this.subgroup = subgroup;
        this.roomid = roomid;
    }

    public String getgp() {
        return this.group;
    }

    public String getsubgp() {
        return this.subgroup;
    }

    public String getroomid() {
        return this.roomid;
    }

}
