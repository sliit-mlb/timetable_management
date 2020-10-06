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
public class room {

    String name;
    String building;
    String type;
    int floor;
    String hallsize;
    String labsize;
    int auto;

    public void Team() {
        this.auto = auto++;
    }

    public room(String name, String buiilding, String type, int floor, String hallsize, String labsize) {
        this.name = name;
        this.building = building;
        this.type = type;
        this.floor = floor;
        this.hallsize = hallsize;
        this.labsize = labsize;
    }

    public void setname(String id, char bui, String ty, int flr, int uni) {
        int autoId;

        if (this.type.matches("HALL")) {
            ty = "H";
        } else {
            ty = "L";
        }

        bui = this.building.charAt(0);
        flr = this.floor;
        uni = this.auto;

        id = bui + ty + flr + auto;

        id = this.name;

    }

    public String getname() {
        return this.name;
    }

}
