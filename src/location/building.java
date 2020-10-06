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
public class building {

    //sttributes of builidng 
    String buildingname;
    String department;
    String faculty;
    int floors;
    int l_hall;
    int m_hall;
    int s_hall;
    int l_lab;
    int s_lab;

    //contructor 
    public building(String buildingname, String department, String faculty, int floors,
            int l_hall, int m_hall, int s_hall, int l_lab, int s_lab) {
        this.buildingname = buildingname;
        this.department = department;
        this.faculty = faculty;
        this.floors = floors;
        this.l_hall = l_hall;
        this.m_hall = m_hall;
        this.s_hall = s_hall;
        this.l_lab = l_lab;
        this.s_lab = s_lab;
    }

    public String getname() {
        return this.buildingname;
    }

    public String getdep() {
        return this.department;
    }

    public String getfac() {
        return this.faculty;
    }

    public int getfloors() {
        return this.floors;
    }

    public int getlhall() {
        return this.l_hall;
    }

    public int getmhall() {
        return this.m_hall;
    }

    public int getshall() {
        return this.s_hall;
    }

    public int getllab() {
        return this.l_lab;
    }

    public int getslab() {
        return this.s_lab;
    }

    public int totalhall() {
        return this.l_hall + this.m_hall + this.s_hall;
    }

    public int totallab() {
        return this.l_lab + this.s_lab;
    }

}
