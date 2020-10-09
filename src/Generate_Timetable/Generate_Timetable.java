/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generate_Timetable;

import Common.DBConnection;
import Common.IndexPage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ahmad Ahkam
 */
public class Generate_Timetable extends javax.swing.JFrame {

    Connection con = null;

    private static String batch = null;
    private String studentGroup, session = null;

    /**
     * Creates new form Generate_Timetable
     */
    public Generate_Timetable() {
        initComponents();

        loadStudentGroup();
    }

    private void loadStudentGroup() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT groupID FROM studentgroup";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> studentGroup = new ArrayList<String>();
            studentGroup.add("Select");
            while (rs.next()) {
                studentGroup.add(rs.getString("groupID"));
            }

            comboStudentGroup.setModel(new DefaultComboBoxModel<String>(studentGroup.toArray(new String[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSession() {
        studentGroup = comboStudentGroup.getSelectedItem().toString();
        ArrayList<String> sessions = new ArrayList<String>();

        if (studentGroup.equals("Select")) {
            sessions.add(null);
            session = null;

            DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();

            ArrayList<String> dayandtimenull = new ArrayList<String>();
            dayandtimenull.add(null);
            dtm.setColumnIdentifiers(dayandtimenull.toArray(new String[0]));
            dtm.setRowCount(0);

            comboSession.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));
            comboRoom.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));
            comboBatch.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));
            comboDay.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));
            comboTimeSlot.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));

        } else {
            PreparedStatement stmt, stmt2 = null;
            ResultSet rs, rs2 = null;

            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String query = "SELECT * FROM sessions WHERE groupID='" + studentGroup + "'";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    StringBuilder session = new StringBuilder();

                    session.append(rs.getString("sessionID") + "-");

                    String query2 = "select lecturerName from lecturers where empID='" + rs.getString("lecturer") + "'";
                    stmt2 = con.prepareStatement(query2);
                    rs2 = stmt2.executeQuery();

                    while (rs2.next()) {
                        session.append(rs2.getString("lecturerName") + "-");
                    }

                    session.append(rs.getString("subjectCode") + "-");
                    session.append(rs.getString("subject") + "-");
                    session.append(rs.getString("tag") + "-");
                    session.append(rs.getString("duration"));

                    sessions.add(session.toString());
                }

                comboSession.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));

                //loadDataToTable();
            } catch (Exception e) {
                e.printStackTrace();
            }

            loadRoom();
        }
    }

    private void loadBatch() {
        PreparedStatement stmt, stmt1 = null;
        ResultSet rs, rs1 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select batch from timetable where studentgroup='" + comboStudentGroup.getSelectedItem().toString() + "'";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> batchList = new ArrayList<String>();
            while (rs.next()) {
                batchList.add(rs.getString("batch"));
            }

            String query1 = null;

            if (batchList.isEmpty()) {
                query1 = "SELECT batch FROM workingdaysandhours";
            } else {
                query1 = "SELECT batch FROM workingdaysandhours WHERE batch='" + batchList.get(0) + "'";
            }

            stmt1 = con.prepareStatement(query1);
            rs1 = stmt1.executeQuery();

            ArrayList<String> batches = new ArrayList<String>();
            while (rs1.next()) {
                batches.add(rs1.getString("batch"));
            }

            comboBatch.setModel(new DefaultComboBoxModel<String>(batches.toArray(new String[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadDataToTable();

        loadDayAndTimeslot(comboBatch.getSelectedItem().toString());
    }

    private void loadDayAndTimeslot(String batch) {
        PreparedStatement stmt, stmt1 = null;
        ResultSet rs, rs1 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT day\n"
                    + "FROM days\n"
                    + "WHERE workingID=(SELECT workingID FROM workingdaysandhours WHERE batch='" + batch + "')";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> days = new ArrayList<String>();
            while (rs.next()) {
                days.add(rs.getString("day"));
            }

            String query1 = "SELECT startTime\n"
                    + "FROM timeslots\n"
                    + "WHERE workingID=(SELECT workingID FROM workingdaysandhours WHERE batch='" + batch + "')";
            stmt1 = con.prepareStatement(query1);
            rs1 = stmt1.executeQuery();

            ArrayList<String> timeSlots = new ArrayList<String>();
            while (rs1.next()) {
                timeSlots.add(rs1.getString("startTime"));
            }

            comboDay.setModel(new DefaultComboBoxModel<String>(days.toArray(new String[0])));

            comboTimeSlot.setModel(new DefaultComboBoxModel<String>(timeSlots.toArray(new String[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRoom() {
        if (comboSession.getItemCount() != 0) {
            String selSession;
            selSession = comboSession.getSelectedItem().toString();
            String[] selSessionID = selSession.split("-");

            try {
                PreparedStatement stmt, stmt2 = null;
                ResultSet rs, rs2 = null;

                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                ArrayList<String> tag = new ArrayList<String>();

                String query = "select tag from sessions where sessionID='" + selSessionID[0] + "'";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    tag.add(rs.getString("tag"));
                }

                String query2 = null;

                if (tag.get(0).equalsIgnoreCase("Lab")) {
                    query2 = "select roomID from room where tag='Lab'";
                } else {
                    query2 = "select roomID from room where tag='Lecture' or tag='Tute'";
                }

                stmt2 = con.prepareStatement(query2);
                rs2 = stmt2.executeQuery();

                ArrayList<String> rooms = new ArrayList<String>();
                while (rs2.next()) {
                    rooms.add(rs2.getString("roomID"));
                }

                comboRoom.setModel(new DefaultComboBoxModel<String>(rooms.toArray(new String[0])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<String> sessions = new ArrayList<String>();
            sessions.add(null);
            comboRoom.setModel(new DefaultComboBoxModel<String>(sessions.toArray(new String[0])));
        }
    }

    public void loadDataToTable() {
        DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();

        PreparedStatement stmt, stmt2, stmt3 = null;
        ResultSet rs, rs2, rs3 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select * from timetable where studentGroup='" + comboStudentGroup.getSelectedItem().toString() + "'";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> timetableList = new ArrayList<String>();
            while (rs.next()) {
                timetableList.add(rs.getString("batch"));
            }

            if (!timetableList.isEmpty()) {

                String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + timetableList.get(0) + "')";
                stmt2 = con.prepareStatement(query2);
                rs2 = stmt2.executeQuery();

                int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0};

                ArrayList<String> days = new ArrayList<String>();
                while (rs2.next()) {
                    days.add(rs2.getString("day"));
                    if (rs2.getString("day").equalsIgnoreCase("Monday")) {
                        arr[0] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Tuesday")) {
                        arr[1] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Wednesday")) {
                        arr[2] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Thursday")) {
                        arr[3] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Friday")) {
                        arr[4] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Saturday")) {
                        arr[5] = 1;
                    } else if (rs2.getString("day").equalsIgnoreCase("Sunday")) {
                        arr[6] = 1;
                    }
                }

                ArrayList<String> dayandtime = new ArrayList<String>();
                dayandtime.add("Time");

                if (arr[0] == 1) {
                    dayandtime.add("Monday");
                }

                if (arr[1] == 1) {
                    dayandtime.add("Tuesday");
                }

                if (arr[2] == 1) {
                    dayandtime.add("Wednesday");
                }

                if (arr[3] == 1) {
                    dayandtime.add("Thursday");
                }

                if (arr[4] == 1) {
                    dayandtime.add("Friday");
                }

                if (arr[5] == 1) {
                    dayandtime.add("Saturday");
                }

                if (arr[6] == 1) {
                    dayandtime.add("Sunday");
                }

                dtm.setColumnIdentifiers(dayandtime.toArray(new String[0]));
                dtm.setRowCount(0);

                String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + timetableList.get(0) + "')";
                stmt3 = con.prepareStatement(query3);
                rs3 = stmt3.executeQuery();

                //ArrayList<String> timeslots = new ArrayList<String>();
                while (rs3.next()) {
                    Vector vector = new Vector();
                    vector.add(rs3.getString("startTime"));

                    if (arr[0] == 1) {
                        String monData = getTableData(studentGroup, timetableList.get(0), "Monday", rs3.getString("startTime"));
                        vector.add(monData);
                    }

                    if (arr[1] == 1) {
                        String tueData = getTableData(studentGroup, timetableList.get(0), "Tuesday", rs3.getString("startTime"));
                        vector.add(tueData);
                    }

                    if (arr[2] == 1) {
                        String wedData = getTableData(studentGroup, timetableList.get(0), "Wednesday", rs3.getString("startTime"));
                        vector.add(wedData);
                    }

                    if (arr[3] == 1) {
                        String thurData = getTableData(studentGroup, timetableList.get(0), "Thursday", rs3.getString("startTime"));
                        vector.add(thurData);
                    }

                    if (arr[4] == 1) {
                        String friData = getTableData(studentGroup, timetableList.get(0), "Friday", rs3.getString("startTime"));
                        vector.add(friData);
                    }

                    if (arr[5] == 1) {
                        String satData = getTableData(studentGroup, timetableList.get(0), "Saturday", rs3.getString("startTime"));
                        vector.add(satData);
                    }

                    if (arr[6] == 1) {
                        String sunData = getTableData(studentGroup, timetableList.get(0), "Sunday", rs3.getString("startTime"));
                        vector.add(sunData);
                    }

                    dtm.addRow(vector);
                }
            } else {
                ArrayList<String> dayandtimenull = new ArrayList<String>();
                dayandtimenull.add(null);
                dtm.setColumnIdentifiers(dayandtimenull.toArray(new String[0]));
                dtm.setRowCount(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getTableData(String studentGroup, String batch, String day, String timeslot) {
        ArrayList<String> data = new ArrayList<String>();

        PreparedStatement stmt, stmt2, stmt3 = null;
        ResultSet rs, rs2, rs3 = null;

        StringBuffer retVal = new StringBuffer();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select session,room,studentSubGroup from timetable where studentGroup=? and batch=? and day=? and timeSlot=?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, studentGroup);
            stmt.setString(2, batch);
            stmt.setString(3, day);
            stmt.setString(4, timeslot);

            rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(rs.getString("session"));
                data.add(rs.getString("room"));
                data.add(rs.getString("studentSubGroup"));
            }

            if (!data.isEmpty()) {
                int count = data.size() / 3;
                int itemCount = 0;
                for (int i = 0; i < count; i++) {
                    if (data.get(itemCount) != null) {
                        String query2 = "select s.subjectCode, s.subject, s.tag, l.lecturerName from sessions s, lecturers l where s.sessionID='" + data.get(itemCount) + "' and l.empID=s.lecturer";

                        stmt2 = con.prepareStatement(query2);
                        rs2 = stmt2.executeQuery();

                        if (data.get(2) != null) {
                            retVal.append(data.get(2) + "-");
                        }

                        while (rs2.next()) {
                            retVal.append(rs2.getString("subjectCode") + "-");
                            retVal.append(rs2.getString("subject") + "-");
                            retVal.append(rs2.getString("tag") + "-");
                            retVal.append(rs2.getString("lecturerName") + "-");
                        }
                        if ((count - i) == 1) {
                            retVal.append(data.get(1));
                        } else {
                            retVal.append(data.get(1) + " & ");
                        }

                        itemCount += 3;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal.toString();
    }

    private void loadSubGroup() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<String> subGroups = new ArrayList<String>();

        if (comboStudentGroup.getSelectedItem().toString().equals("Select")) {
            subGroups.add(null);
        } else {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String query = "select subGroupID from studentsubgroup where allid='" + comboStudentGroup.getSelectedItem().toString() + "'";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                subGroups.add("No need");
                while (rs.next()) {
                    subGroups.add(rs.getString("subGroupID"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        comboSubGroup.setModel(new DefaultComboBoxModel<String>(subGroups.toArray(new String[0])));
    }

    private boolean validateAvailable(String batch, String day, String timeslot, String room) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<String> sessions = new ArrayList<>();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select session from timetable where batch=? and day=? and timeSlot=? and room=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, batch);
            stmt.setString(2, day);
            stmt.setString(3, timeslot);
            stmt.setString(4, room);
            rs = stmt.executeQuery();

            while (rs.next()) {
                sessions.add(rs.getString("session"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (sessions.isEmpty());
    }

    private boolean checkParallelSession(String session, String batch, String day, String timeslot) {
        PreparedStatement stmt, stmt2 = null;
        ResultSet rs, rs2 = null;
        boolean retVal = false;
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT * FROM parallelsessions p WHERE p.session1=? OR p.session2=? OR p.session3=? OR p.session4=? OR p.session5=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, session);
            stmt.setString(2, session);
            stmt.setString(3, session);
            stmt.setString(4, session);
            stmt.setString(5, session);
            rs = stmt.executeQuery();

            ArrayList<String> sessions = new ArrayList<>();
            while (rs.next()) {
                sessions.add(rs.getString("session1"));
                sessions.add(rs.getString("session2"));
                sessions.add(rs.getString("session3"));
                sessions.add(rs.getString("session4"));
                sessions.add(rs.getString("session5"));
            }

            if (sessions.isEmpty()) {
                return true;
            } else {
                String query2 = null;

                sessions.removeAll(Collections.singleton(null));
                if (sessions.size() == 2) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                } else if (sessions.size() == 3) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                } else if (sessions.size() == 4) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                    stmt2.setString(4, sessions.get(3));
                } else if (sessions.size() == 5) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                    stmt2.setString(4, sessions.get(3));
                    stmt2.setString(4, sessions.get(4));
                }
                rs2 = stmt2.executeQuery();

                ArrayList<String> list = new ArrayList<>();
                while (rs2.next()) {
                    if (rs2.getString("session") != null) {
                        if (rs2.getString("studentGroup").equals(comboStudentGroup.getSelectedItem().toString())) {
                            list.add(rs2.getString("batch"));
                            list.add(rs2.getString("day"));
                            list.add(rs2.getString("timeslot"));
                        }
                    }
                }

                list.removeAll(Collections.singleton(null));

                System.out.println(list.size());
                if (list.size() == 0) {
                    retVal = true;
                } else {
                    if ((list.get(0).equals(batch)) && (list.get(1).equals(day)) && (list.get(2).equals(timeslot))) {
                        retVal = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private boolean checkNotOverlapSession(String session, String batch, String day, String timeslot) {
        boolean retVal = false;

        PreparedStatement stmt, stmt2 = null;
        ResultSet rs, rs2 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT * FROM notoverlapsessions p WHERE p.session1=? OR p.session2=? OR p.session3=? OR p.session4=? OR p.session5=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, session);
            stmt.setString(2, session);
            stmt.setString(3, session);
            stmt.setString(4, session);
            stmt.setString(5, session);
            rs = stmt.executeQuery();

            ArrayList<String> sessions = new ArrayList<>();
            while (rs.next()) {
                sessions.add(rs.getString("session1"));
                sessions.add(rs.getString("session2"));
                sessions.add(rs.getString("session3"));
                sessions.add(rs.getString("session4"));
                sessions.add(rs.getString("session5"));
            }

            if (sessions.isEmpty()) {
                return true;
            } else {
                String query2 = null;

                sessions.removeAll(Collections.singleton(null));
                if (sessions.size() == 2) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                } else if (sessions.size() == 3) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                } else if (sessions.size() == 4) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                    stmt2.setString(4, sessions.get(3));
                } else if (sessions.size() == 5) {
                    query2 = "select batch,day,timeSlot,session,studentGroup from timetable where session=? or session=? or session=? or session=? or session=? order by timeSlot asc";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sessions.get(0));
                    stmt2.setString(2, sessions.get(1));
                    stmt2.setString(3, sessions.get(2));
                    stmt2.setString(4, sessions.get(3));
                    stmt2.setString(4, sessions.get(4));
                }
                rs2 = stmt2.executeQuery();

                ArrayList<String> list = new ArrayList<>();
                while (rs2.next()) {
                    if (rs2.getString("session") != null) {
                        if (rs2.getString("studentGroup").equals(comboStudentGroup.getSelectedItem().toString())) {
                            list.add(rs2.getString("batch"));
                            list.add(rs2.getString("day"));
                            list.add(rs2.getString("timeslot"));
                        }
                    }
                }

                list.removeAll(Collections.singleton(null));

                //System.out.println(list.size());
                if (list.size() == 0) {
                    retVal = true;
                } else {
                    int count = list.size() / 3;
                    for (int i = 0; i < count; i++) {
                        if (!((list.get(i).equals(batch)) && (list.get(i + 1).equals(day)) && (list.get(i + 2).equals(timeslot)))) {
                            retVal = true;
                        } else {
                            retVal = false;
                            break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private boolean checkTimeslotAvailable(String batch, String timeslot, int duration) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean retVal = false;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select count(startTime) as cnt from timeslots where startTime>? and workingID=(select workingID from workingdaysandhours where batch=?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, timeslot);
            stmt.setString(2, batch);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (Integer.parseInt(rs.getString("cnt")) >= (duration - 1)) {
                    retVal = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private String setNewRoom(String val, String room, String type, String sessionType) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String newRoom = null;

        if (sessionType.equals("Tute")) {
            sessionType = "Lecture";
        }

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = null;

            if (type.equals("lecturer")) {
                query = "SELECT roomID,tag FROM room WHERE lecturer=(SELECT empID from lecturers WHERE lecturerName=?)";
            } else if (type.equals("session")) {
                query = "SELECT roomID,tag FROM room WHERE session=?";
            } else if (type.equals("studentGroup")) {
                query = "SELECT roomID,tag FROM room WHERE studentGroup=?";
            } else if (type.equals("studentSubGroup")) {
                query = "SELECT roomID,tag FROM room WHERE studentSubGroup=?";
            }

            stmt = con.prepareStatement(query);
            stmt.setString(1, val);
            rs = stmt.executeQuery();

            ArrayList<String> rooms = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString("roomID") != null) {
                    rooms.add(rs.getString("roomID"));
                    rooms.add(rs.getString("tag"));
                }
            }

            rooms.removeAll(Collections.singleton(null));

            if (rooms.size() != 0) {
                if (rooms.get(1).equals(sessionType)) {
                    if (!rooms.get(0).equals(room)) {
                        if (JOptionPane.showConfirmDialog(null, "Confirm to Change room to " + rooms.get(0) + ". Because, " + type + " suitable room is that", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                            newRoom = rooms.get(0);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newRoom;
    }

    //String batch, String day, String timslot, String room
    private boolean addConSessions(String session) {
        boolean retVal = false;

        PreparedStatement stmt, stmt2, stmt3, stmt4, stmt5 = null;
        ResultSet rs, rs2, rs3, rs4, rs5 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select * from consecutivesessions where session1=? or session2=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, session);
            stmt.setString(2, session);
            rs = stmt.executeQuery();

            ArrayList<String> conSessions = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString(1) != null) {
                    conSessions.add(rs.getString("session1"));
                    conSessions.add(rs.getString("session2"));
                    conSessions.add(rs.getString("roomID"));
                    retVal = true;
                }
            }

            //System.out.println(conSessions.size());
            if (retVal) {
                conSessions.removeAll(Collections.singleton(session));
                //System.out.println(conSessions.size());

                String newRoom = null;

                System.out.println(conSessions.get(0));
                if (conSessions.get(1) != null) {
                    if (!conSessions.get(1).equals(comboRoom.getSelectedItem().toString())) {
                        if (JOptionPane.showConfirmDialog(null, "Confirm to Change room to " + conSessions.get(1) + ". Because, suitable room is that", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                            newRoom = conSessions.get(1);
                        }
                    }
                } else {
                    newRoom = comboRoom.getSelectedItem().toString();
                }

                ArrayList<String> sesList = new ArrayList<>();
                int count = 0;
                String sess = session;
                for (int i = 0; i < 2; i++) {
                    //System.out.println(sess);
                    String query2 = "select duration,sessionID from sessions where sessionID=?";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, sess);
                    rs2 = stmt2.executeQuery();
                    //System.out.println(sess);
                    while (rs2.next()) {
                        //System.out.println(rs.getString(1));
                        count += Integer.parseInt(rs2.getString(1));
                        sesList.add(rs2.getString("sessionID"));
                        sesList.add(rs2.getString("duration"));
                    }
                    sess = conSessions.get(0);
                    //System.out.println("After");
                }

                String query3 = "select count(startTime) as cnt from timeslots where startTime>=? and workingID=(select workingID from workingdaysandhours where batch=?)";
                stmt3 = con.prepareStatement(query3);
                stmt3.setString(1, comboTimeSlot.getSelectedItem().toString());
                stmt3.setString(2, comboBatch.getSelectedItem().toString());
                rs3 = stmt3.executeQuery();

                int cnt = 0;
                while (rs3.next()) {
                    cnt = rs3.getInt("cnt");
                }

                if (cnt >= count) {
                    String query4 = "select startTime from timeslots where startTime>=? and workingID=(select workingID from workingdaysandhours where batch=?) order by startTime asc";
                    stmt4 = con.prepareStatement(query4);
                    stmt4.setString(1, comboTimeSlot.getSelectedItem().toString());
                    stmt4.setString(2, comboBatch.getSelectedItem().toString());
                    rs4 = stmt4.executeQuery();

                    ArrayList<String> timeslots = new ArrayList<>();
                    while (rs4.next()) {
                        timeslots.add(rs4.getString(1));
                    }

                    int durCount = Integer.parseInt(sesList.get(1));
                    String addSes = sesList.get(0);
                    for (int i = 0; i < count; i++) {
                        if (durCount == 0) {
                            durCount = Integer.parseInt(sesList.get(3));
                            addSes = sesList.get(2);
                        }
                        durCount--;

                        String query5 = "insert into timetable values(?,?,?,?,?,?,?)";
                        stmt5 = con.prepareStatement(query5);
                        stmt5.setString(1, comboStudentGroup.getSelectedItem().toString());
                        if (comboSubGroup.getSelectedItem().toString().equals("No need")) {
                            stmt5.setString(2, null);
                        } else {
                            stmt5.setString(2, comboSubGroup.getSelectedItem().toString());
                        }
                        stmt5.setString(3, comboBatch.getSelectedItem().toString());
                        stmt5.setString(4, comboDay.getSelectedItem().toString());
                        stmt5.setString(5, timeslots.get(i));
                        stmt5.setString(6, newRoom);
                        stmt5.setString(7, addSes);
                        stmt5.execute();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough sequence timeslots for consecutvie sessions. Choose another timeslot", "Error message", JOptionPane.ERROR_MESSAGE);
                }
                loadDataToTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private boolean checkNotAvailable() {
        boolean retVal = false;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String[] session = comboSession.getSelectedItem().toString().split("-");

            String query = "select startTime,endTime,day from notavailable where (lecturer=(select empID from lecturers where lecturerName=?) or session=? or studentGroup=? or room=?) and (startTime<=? and endTime>? and day=?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, session[1]);
            stmt.setString(2, session[0]);
            stmt.setString(3, comboStudentGroup.getSelectedItem().toString());
            stmt.setString(4, comboRoom.getSelectedItem().toString());
            stmt.setString(5, comboTimeSlot.getSelectedItem().toString());
            stmt.setString(6, comboTimeSlot.getSelectedItem().toString());
            stmt.setString(7, comboDay.getSelectedItem().toString());
            rs = stmt.executeQuery();

            ArrayList<String> times = new ArrayList<>();
            while (rs.next()) {
                times.add(rs.getString("startTime"));
                times.add(rs.getString("endTime"));
                times.add(rs.getString("day"));
            }

            times.removeAll(Collections.singleton(null));

            if (times.size() != 0) {
                retVal = false;
            } else {
                retVal = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnHome = new javax.swing.JButton();
        btnGenerateTimetable = new javax.swing.JButton();
        btnViewTimetable = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        comboStudentGroup = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        comboSession = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        comboRoom = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboDay = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        comboTimeSlot = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        comboBatch = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTimetable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        comboSubGroup = new javax.swing.JComboBox<>();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("Generate Timetable");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 480, 50));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1000, 10));

        btnHome.setBackground(new java.awt.Color(0, 153, 153));
        btnHome.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnHome.setText("Home");
        btnHome.setMaximumSize(new java.awt.Dimension(75, 33));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel1.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 250, 50));

        btnGenerateTimetable.setBackground(new java.awt.Color(0, 153, 153));
        btnGenerateTimetable.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnGenerateTimetable.setText("Generate Timetable");
        btnGenerateTimetable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateTimetableActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerateTimetable, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 250, 50));

        btnViewTimetable.setBackground(new java.awt.Color(0, 153, 153));
        btnViewTimetable.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnViewTimetable.setText("View Timetable");
        btnViewTimetable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTimetableActionPerformed(evt);
            }
        });
        jPanel1.add(btnViewTimetable, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 250, 50));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1000, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Select Student Group");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, -1, -1));

        comboStudentGroup.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboStudentGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboStudentGroupActionPerformed(evt);
            }
        });
        jPanel1.add(comboStudentGroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 200, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Session");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        comboSession.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSessionActionPerformed(evt);
            }
        });
        jPanel1.add(comboSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Room");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        comboRoom.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRoomActionPerformed(evt);
            }
        });
        jPanel1.add(comboRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Day");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        comboDay.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jPanel1.add(comboDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Time Slot");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, -1));

        comboTimeSlot.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jPanel1.add(comboTimeSlot, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Batch");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        comboBatch.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBatchActionPerformed(evt);
            }
        });
        jPanel1.add(comboBatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        btnAdd.setBackground(new java.awt.Color(0, 153, 153));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 180, 60));

        tblTimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblTimetable.setToolTipText("");
        tblTimetable.setRowHeight(30);
        jScrollPane2.setViewportView(tblTimetable);

        jScrollPane1.setViewportView(jScrollPane2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 240, 600, 340));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Sub Group ID");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, -1, -1));

        comboSubGroup.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jPanel1.add(comboSubGroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, -1, -1));

        btnDelete.setBackground(new java.awt.Color(0, 153, 153));
        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 519, 140, 60));

        jPanel2.setBackground(new java.awt.Color(102, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new IndexPage().setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnGenerateTimetableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateTimetableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerateTimetableActionPerformed

    private void comboStudentGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboStudentGroupActionPerformed
        // TODO add your handling code here:

        loadSession();
        if (comboStudentGroup.getSelectedItem().toString() != "Select") {
            loadBatch();
        }
        loadSubGroup();
    }//GEN-LAST:event_comboStudentGroupActionPerformed

    private void comboSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSessionActionPerformed
        // TODO add your handling code here:
        loadRoom();
    }//GEN-LAST:event_comboSessionActionPerformed

    private void comboBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBatchActionPerformed
        // TODO add your handling code here:
        loadDayAndTimeslot(comboBatch.getSelectedItem().toString());
    }//GEN-LAST:event_comboBatchActionPerformed

    private void comboRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRoomActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

        String studentGroup = comboStudentGroup.getSelectedItem().toString();
        String subGroup = comboSubGroup.getSelectedItem().toString();
        String room = comboRoom.getSelectedItem().toString();
        String batch = comboBatch.getSelectedItem().toString();
        String day = comboDay.getSelectedItem().toString();
        String timeslot = comboTimeSlot.getSelectedItem().toString();
        String session = comboSession.getSelectedItem().toString();
        String[] sessionID = session.split("-");
        String newRoom = null;

        //addConSessions(sessionID[0]);
        newRoom = setNewRoom(sessionID[1], room, "lecturer", sessionID[4]);

        if (newRoom == null) {
            newRoom = setNewRoom(sessionID[1], room, "session", sessionID[4]);
            if (newRoom == null) {
                newRoom = setNewRoom(sessionID[1], room, "studentGroup", sessionID[4]);
                if (newRoom == null && !subGroup.equals("No need")) {
                    newRoom = setNewRoom(sessionID[1], room, "studentGroup", sessionID[4]);
                }
            }
        }

        if (newRoom != null) {
            room = newRoom;
        }

        if (addConSessions(sessionID[0])) {
        } else if (!checkTimeslotAvailable(batch, timeslot, Integer.parseInt(sessionID[5]))) {
            JOptionPane.showMessageDialog(this, "Enough sequence timeslots. Choose another timeslot", "Error message", JOptionPane.ERROR_MESSAGE);
        } else if (!checkNotAvailable()) {
            JOptionPane.showMessageDialog(this, "This is not Available Timseslot.", "Error message", JOptionPane.ERROR_MESSAGE);
        } else if (!validateAvailable(batch, day, timeslot, room)) {
            JOptionPane.showMessageDialog(this, "Already Timeslot and room booked for another sesssion.", "Error message", JOptionPane.ERROR_MESSAGE);
        } else if (!checkParallelSession(sessionID[0], batch, day, timeslot)) {
            JOptionPane.showMessageDialog(this, "This is parallel session.", "Error message", JOptionPane.ERROR_MESSAGE);
        } else if (!checkNotOverlapSession(sessionID[0], batch, day, timeslot)) {
            JOptionPane.showMessageDialog(this, "This is not overlap session. Change the timeslot or date", "Error message", JOptionPane.ERROR_MESSAGE);
        } else {
            PreparedStatement stmt, stmt2 = null;
            ResultSet rs2 = null;

            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                int count = Integer.parseInt(sessionID[5]);
                for (int i = 0; i < count; i++) {
                    String query = "insert into timetable values(?,?,?,?,?,?,?)";
                    stmt = con.prepareStatement(query);
                    stmt.setString(1, studentGroup);
                    if (subGroup.equals("No need")) {
                        stmt.setString(2, null);
                    } else {
                        stmt.setString(2, subGroup);
                    }
                    stmt.setString(3, batch);
                    stmt.setString(4, day);
                    stmt.setString(5, timeslot);
                    stmt.setString(6, room);
                    stmt.setString(7, sessionID[0]);
                    stmt.execute();

                    String query2 = "select startTime from timeslots where startTime>? AND workingID=(select workingID from workingdaysandhours where batch=?) ORDER BY startTime ASC";
                    stmt2 = con.prepareStatement(query2);
                    stmt2.setString(1, timeslot);
                    stmt2.setString(2, batch);
                    rs2 = stmt2.executeQuery();

                    ArrayList<String> time = new ArrayList<>();
                    while (rs2.next()) {
                        time.add(rs2.getString("startTime"));
                    }
                    if (time.size() != .0) {
                        timeslot = time.get(0);
                    }
                }

                JOptionPane.showMessageDialog(this, "Successfully Inserted.");

                loadDataToTable();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnViewTimetableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTimetableActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new View_Timetable().setVisible(true);
    }//GEN-LAST:event_btnViewTimetableActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:

        String studentGroup = comboStudentGroup.getSelectedItem().toString();
        String subGroup = comboSubGroup.getSelectedItem().toString();
        String room = comboRoom.getSelectedItem().toString();
        String batch = comboBatch.getSelectedItem().toString();
        String day = comboDay.getSelectedItem().toString();
        String timeslot = comboTimeSlot.getSelectedItem().toString();
        String session = comboSession.getSelectedItem().toString();
        String[] sessionID = session.split("-");

        PreparedStatement stmt = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            if (JOptionPane.showConfirmDialog(null, "Confirm to delete", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                String query = null;

                if (subGroup.equals("No need")) {
                    query = "delete from timetable where studentGroup=? and batch=? and day=? and timeslot=? and room=? and session=?";
                } else {
                    query = "delete from timetable where studentGroup=? and batch=? and day=? and timeslot=? and room=? and session=? and studentSubGroup=?";
                }
                stmt = con.prepareStatement(query);
                stmt.setString(1, studentGroup);
                stmt.setString(2, batch);
                stmt.setString(3, day);
                stmt.setString(4, timeslot);
                stmt.setString(5, room);
                stmt.setString(6, sessionID[0]);
                if (!subGroup.equals("No need")) {
                    stmt.setString(7, subGroup);
                }
                stmt.execute();

                JOptionPane.showMessageDialog(this, "Successfully Deleted");

                loadDataToTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Generate_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Generate_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Generate_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Generate_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Generate_Timetable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGenerateTimetable;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnViewTimetable;
    private javax.swing.JComboBox<String> comboBatch;
    private javax.swing.JComboBox<String> comboDay;
    private javax.swing.JComboBox<String> comboRoom;
    private javax.swing.JComboBox<String> comboSession;
    private javax.swing.JComboBox<String> comboStudentGroup;
    private javax.swing.JComboBox<String> comboSubGroup;
    private javax.swing.JComboBox<String> comboTimeSlot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblTimetable;
    // End of variables declaration//GEN-END:variables
}
