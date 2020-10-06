/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package location;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ishma
 */
public class session {

    static void setModel(DefaultComboBoxModel defaultComboBoxModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String session1;
    private String session2;
    private String roomid;

    public session(String session1, String session2, String roomid) {
        this.session1 = session1;
        this.session2 = session2;
        this.roomid = roomid;
    }

    public String getsession() {
        return this.session1;
    }

    public String getsession2() {
        return this.session2;
    }

    public String getroom() {
        return this.roomid;
    }
}
