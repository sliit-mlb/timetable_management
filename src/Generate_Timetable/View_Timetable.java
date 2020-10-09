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
import java.util.Vector;
import java.util.function.Consumer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Ahmad Ahkam
 */
public class View_Timetable extends javax.swing.JFrame {

    Connection con = null;

    /**
     * Creates new form View_Timetable
     */
    public View_Timetable() {
        initComponents();

        loadCategory();
    }

    private void loadCategory() {
        ArrayList<String> categories = new ArrayList<String>();

        categories.add("Select");
        categories.add("Student Group");
        categories.add("Lecturer");
        categories.add("Room");

        comboCategory.setModel(new DefaultComboBoxModel<String>(categories.toArray(new String[0])));
    }

    private void loadSelectOne(String category) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<String> categoryList = new ArrayList<String>();
        String query = null;
        String query2 = null;

        if (category.equals("Student Group")) {
            query = "SELECT DISTINCT(studentGroup) from timetable";
        } else if (category.equals("Lecturer")) {
            query2 = "SELECT DISTINCT(l.empID),l.lecturerName\n"
                    + "FROM sessions s, timetable t, lecturers l\n"
                    + "WHERE s.sessionID=t.session AND l.empID=s.lecturer";
        } else if (category.equals("Room")) {
            query = "SELECT DISTINCT(room) from timetable";
        } else {
            categoryList.add(null);

            DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();
            ArrayList<String> dayandtimenull = new ArrayList<String>();
            dayandtimenull.add(null);
            dtm.setColumnIdentifiers(dayandtimenull.toArray(new String[0]));
            dtm.setRowCount(0);
        }

        if (query != null) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    categoryList.add(rs.getString(1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (query2 != null) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                stmt = con.prepareStatement(query2);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    categoryList.add(rs.getString(1) + "-" + rs.getString(2));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        comboSelect.setModel(new DefaultComboBoxModel<String>(categoryList.toArray(new String[0])));
    }

    public void loadStudentTable() {
        DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();

        PreparedStatement stmt, stmt2, stmt3 = null;
        ResultSet rs, rs2, rs3 = null;

        String studentGroup = comboSelect.getSelectedItem().toString();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select * from timetable where studentGroup='" + studentGroup + "'";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> timetableList = new ArrayList<String>();
            while (rs.next()) {
                timetableList.add(rs.getString("batch"));
            }

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

    private void loadRoomTable(String room, String batch) {
        DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();

        PreparedStatement stmt, stmt2, stmt3 = null;
        ResultSet rs, rs2, rs3 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
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

            String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
            stmt3 = con.prepareStatement(query3);
            rs3 = stmt3.executeQuery();

            //ArrayList<String> timeslots = new ArrayList<String>();
            while (rs3.next()) {
                Vector vector = new Vector();
                vector.add(rs3.getString("startTime"));

                if (arr[0] == 1) {
                    String monData = getRoomTableData(room, batch, "Monday", rs3.getString("startTime"));
                    vector.add(monData);
                }

                if (arr[1] == 1) {
                    String tueData = getRoomTableData(room, batch, "Tuesday", rs3.getString("startTime"));
                    vector.add(tueData);
                }

                if (arr[2] == 1) {
                    String wedData = getRoomTableData(room, batch, "Wednesday", rs3.getString("startTime"));
                    vector.add(wedData);
                }

                if (arr[3] == 1) {
                    String thurData = getRoomTableData(room, batch, "Thursday", rs3.getString("startTime"));
                    vector.add(thurData);
                }

                if (arr[4] == 1) {
                    String friData = getRoomTableData(room, batch, "Friday", rs3.getString("startTime"));
                    vector.add(friData);
                }

                if (arr[5] == 1) {
                    String satData = getRoomTableData(room, batch, "Saturday", rs3.getString("startTime"));
                    vector.add(satData);
                }

                if (arr[6] == 1) {
                    String sunData = getRoomTableData(room, batch, "Sunday", rs3.getString("startTime"));
                    vector.add(sunData);
                }

                dtm.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRoomTableData(String room, String batch, String day, String timeslot) {
        ArrayList<String> data = new ArrayList<String>();

        PreparedStatement stmt, stmt2 = null;
        ResultSet rs, rs2 = null;

        StringBuffer retVal = new StringBuffer();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select session,studentSubGroup,studentGroup from timetable where room=? and batch=? and day=? and timeSlot=?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, room);
            stmt.setString(2, batch);
            stmt.setString(3, day);
            stmt.setString(4, timeslot);

            rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(rs.getString("session"));
                data.add(rs.getString("studentGroup"));
                data.add(rs.getString("studentSubGroup"));
            }
            if (!data.isEmpty()) {
                String query2 = "select s.subjectCode, s.subject, s.tag, l.lecturerName from sessions s, lecturers l where s.sessionID='" + data.get(0) + "' and l.empID=s.lecturer";

                stmt2 = con.prepareStatement(query2);
                rs2 = stmt2.executeQuery();

                retVal.append(data.get(1) + "-");

                if (data.get(2) != null) {
                    retVal.append(data.get(2) + "-");
                }

                while (rs2.next()) {
                    retVal.append(rs2.getString("subjectCode") + "-");
                    retVal.append(rs2.getString("subject") + "-");
                    retVal.append(rs2.getString("tag") + "-");
                    retVal.append(rs2.getString("lecturerName"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal.toString();
    }

    private void loadLecturerTable(String lecturer, String batch) {
        DefaultTableModel dtm = (DefaultTableModel) tblTimetable.getModel();

        PreparedStatement stmt, stmt2, stmt3 = null;
        ResultSet rs, rs2, rs3 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
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

            String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
            stmt3 = con.prepareStatement(query3);
            rs3 = stmt3.executeQuery();

            //ArrayList<String> timeslots = new ArrayList<String>();
            while (rs3.next()) {
                Vector vector = new Vector();
                vector.add(rs3.getString("startTime"));

                if (arr[0] == 1) {
                    String monData = getLecturerTableData(lecturer, batch, "Monday", rs3.getString("startTime"));
                    vector.add(monData);
                }

                if (arr[1] == 1) {
                    String tueData = getLecturerTableData(lecturer, batch, "Tuesday", rs3.getString("startTime"));
                    vector.add(tueData);
                }

                if (arr[2] == 1) {
                    String wedData = getLecturerTableData(lecturer, batch, "Wednesday", rs3.getString("startTime"));
                    vector.add(wedData);
                }

                if (arr[3] == 1) {
                    String thurData = getLecturerTableData(lecturer, batch, "Thursday", rs3.getString("startTime"));
                    vector.add(thurData);
                }

                if (arr[4] == 1) {
                    String friData = getLecturerTableData(lecturer, batch, "Friday", rs3.getString("startTime"));
                    vector.add(friData);
                }

                if (arr[5] == 1) {
                    String satData = getLecturerTableData(lecturer, batch, "Saturday", rs3.getString("startTime"));
                    vector.add(satData);
                }

                if (arr[6] == 1) {
                    String sunData = getLecturerTableData(lecturer, batch, "Sunday", rs3.getString("startTime"));
                    vector.add(sunData);
                }

                dtm.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLecturerTableData(String lecturer, String batch, String day, String timeslot) {
        ArrayList<String> data = new ArrayList<String>();

        PreparedStatement stmt, stmt2 = null;
        ResultSet rs, rs2 = null;

        StringBuffer retVal = new StringBuffer();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "select t.session,t.studentSubGroup,t.studentGroup,t.room from timetable t,sessions s where s.sessionID=t.session and s.lecturer=? and t.batch=? and t.day=? and t.timeSlot=?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, lecturer);
            stmt.setString(2, batch);
            stmt.setString(3, day);
            stmt.setString(4, timeslot);

            rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(rs.getString("session"));
                data.add(rs.getString("studentGroup"));
                data.add(rs.getString("studentSubGroup"));
                data.add(rs.getString("room"));
            }
            if (!data.isEmpty()) {
                String query2 = "select s.subjectCode, s.subject, s.tag from sessions s where s.sessionID='" + data.get(0) + "'";

                stmt2 = con.prepareStatement(query2);
                rs2 = stmt2.executeQuery();

                retVal.append(data.get(1) + "-");

                if (data.get(2) != null) {
                    retVal.append(data.get(2) + "-");
                }

                while (rs2.next()) {
                    retVal.append(rs2.getString("subjectCode") + "-");
                    retVal.append(rs2.getString("subject") + "-");
                    retVal.append(rs2.getString("tag") + "-");
                }
                retVal.append(data.get(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal.toString();
    }

    private ArrayList loadBatch(String query) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<String> batches = new ArrayList<String>();

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                batches.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return batches;
    }

    private void printStudent(Document doc) {
        PreparedStatement stmt, stmt2, stmt3, stmt4 = null;
        ResultSet rs, rs2, rs3, rs4 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            ArrayList<String> studentGroups = new ArrayList<>();
            String query4 = "select distinct(studentGroup) from timetable";
            stmt4 = con.prepareStatement(query4);
            rs4 = stmt4.executeQuery();

            while (rs4.next()) {
                studentGroups.add(rs4.getString("studentGroup"));
            }

            for (String studentGroup : studentGroups) {

                doc.add(new Paragraph("-------------" + studentGroup + "-----------------"));

                String query = "select batch from timetable where studentGroup='" + studentGroup + "'";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                ArrayList<String> timetableList = new ArrayList<String>();
                while (rs.next()) {
                    timetableList.add(rs.getString("batch"));
                }

                String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + timetableList.get(0) + "')";
                stmt2 = con.prepareStatement(query2);
                rs2 = stmt2.executeQuery();

                int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0};
                int count = 1;

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
                    count++;
                }

                PdfPTable stTable = new PdfPTable(count);

                stTable.setWidthPercentage(100);
                stTable.setSpacingBefore(10f);
                stTable.setSpacingAfter(10f);

                stTable.addCell(new PdfPCell(new Paragraph("Time")));

                if (arr[0] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Monday")));
                }

                if (arr[1] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Tuesday")));
                }

                if (arr[2] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Wednesday")));
                }

                if (arr[3] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Thursday")));
                }

                if (arr[4] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Friday")));
                }

                if (arr[5] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Saturday")));
                }

                if (arr[6] == 1) {
                    stTable.addCell(new PdfPCell(new Paragraph("Sunday")));
                }

                String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + timetableList.get(0) + "')";
                stmt3 = con.prepareStatement(query3);
                rs3 = stmt3.executeQuery();

                while (rs3.next()) {
                    stTable.addCell(new PdfPCell(new Paragraph(rs3.getString("startTime"))));

                    if (arr[0] == 1) {
                        String monData = getTableData(studentGroup, timetableList.get(0), "Monday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(monData)));
                    }

                    if (arr[1] == 1) {
                        String tueData = getTableData(studentGroup, timetableList.get(0), "Tuesday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(tueData)));
                    }

                    if (arr[2] == 1) {
                        String wedData = getTableData(studentGroup, timetableList.get(0), "Wednesday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(wedData)));
                    }

                    if (arr[3] == 1) {
                        String thurData = getTableData(studentGroup, timetableList.get(0), "Thursday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(thurData)));
                    }

                    if (arr[4] == 1) {
                        String friData = getTableData(studentGroup, timetableList.get(0), "Friday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(friData)));
                    }

                    if (arr[5] == 1) {
                        String satData = getTableData(studentGroup, timetableList.get(0), "Saturday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(satData)));
                    }

                    if (arr[6] == 1) {
                        String sunData = getTableData(studentGroup, timetableList.get(0), "Sunday", rs3.getString("startTime"));
                        stTable.addCell(new PdfPCell(new Paragraph(sunData)));
                    }
                }
                doc.add(stTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printLecturer(Document doc) {
        PreparedStatement stmt, stmt2, stmt3, stmt4, stmt5 = null;
        ResultSet rs, rs2, rs3, rs4, rs5 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT DISTINCT(s.lecturer) FROM timetable t,sessions s WHERE t.session=s.sessionID";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> lecturers = new ArrayList<>();
            while (rs.next()) {
                lecturers.add(rs.getString(1));
            }

            for (String lecturer : lecturers) {
                String query5 = "select lecturerName from lecturers where empID='" + lecturer + "'";
                stmt5 = con.prepareStatement(query5);
                rs5 = stmt5.executeQuery();

                ArrayList<String> empIDs = new ArrayList<String>();
                while (rs5.next()) {
                    empIDs.add(rs5.getString("lecturerName"));
                }

                String query4 = "SELECT DISTINCT(t.batch) FROM timetable t,sessions s WHERE t.session=s.sessionID AND s.lecturer='" + lecturer + "'";
                stmt4 = con.prepareStatement(query4);
                rs4 = stmt4.executeQuery();

                ArrayList<String> batches = new ArrayList<>();
                while (rs4.next()) {
                    batches.add(rs4.getString("batch"));
                }

                for (String batch : batches) {
                    doc.add(new Paragraph("--------------" + lecturer + "----" + empIDs.get(0) + "----" + batch + "--------------"));

                    String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
                    stmt2 = con.prepareStatement(query2);
                    rs2 = stmt2.executeQuery();

                    int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0};
                    int count = 1;

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
                        count++;
                    }

                    PdfPTable lecTable = new PdfPTable(count);

                    lecTable.setWidthPercentage(100);
                    lecTable.setSpacingBefore(10f);
                    lecTable.setSpacingAfter(10f);

                    lecTable.addCell(new PdfPCell(new Paragraph("Time")));

                    if (arr[0] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Monday")));
                    }

                    if (arr[1] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Tuesday")));
                    }

                    if (arr[2] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Wednesday")));
                    }

                    if (arr[3] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Thursday")));
                    }

                    if (arr[4] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Friday")));
                    }

                    if (arr[5] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Saturday")));
                    }

                    if (arr[6] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Sunday")));
                    }

                    String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
                    stmt3 = con.prepareStatement(query3);
                    rs3 = stmt3.executeQuery();

                    while (rs3.next()) {
                        lecTable.addCell(new PdfPCell(new Paragraph(rs3.getString("startTime"))));

                        if (arr[0] == 1) {
                            String monData = getLecturerTableData(lecturer, batch, "Monday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(monData)));
                        }

                        if (arr[1] == 1) {
                            String tueData = getLecturerTableData(lecturer, batch, "Tuesday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(tueData)));
                        }

                        if (arr[2] == 1) {
                            String wedData = getLecturerTableData(lecturer, batch, "Wednesday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(wedData)));
                        }

                        if (arr[3] == 1) {
                            String thurData = getLecturerTableData(lecturer, batch, "Thursday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(thurData)));
                        }

                        if (arr[4] == 1) {
                            String friData = getLecturerTableData(lecturer, batch, "Friday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(friData)));
                        }

                        if (arr[5] == 1) {
                            String satData = getLecturerTableData(lecturer, batch, "Saturday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(satData)));
                        }

                        if (arr[6] == 1) {
                            String sunData = getLecturerTableData(lecturer, batch, "Sunday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(sunData)));
                        }
                    }

                    doc.add(lecTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printRoom(Document doc) {
        PreparedStatement stmt, stmt2, stmt3, stmt4 = null;
        ResultSet rs, rs2, rs3, rs4 = null;

        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String query = "SELECT DISTINCT(room) FROM timetable";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            ArrayList<String> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(rs.getString("room"));
            }

            for (String room : rooms) {
                String query4 = "select DISTINCT(batch) from timetable where room='" + room + "'";
                stmt4 = con.prepareStatement(query4);
                rs4 = stmt4.executeQuery();

                ArrayList<String> batches = new ArrayList<>();
                while (rs4.next()) {
                    batches.add(rs4.getString("batch"));
                }

                for (String batch : batches) {

                    doc.add(new Paragraph("----------" + room + "-------" + batch + "----------"));

                    String query2 = "select day from days where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
                    stmt2 = con.prepareStatement(query2);
                    rs2 = stmt2.executeQuery();

                    int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0};
                    int count = 1;

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
                        count++;
                    }

                    PdfPTable lecTable = new PdfPTable(count);

                    lecTable.setWidthPercentage(100);
                    lecTable.setSpacingBefore(10f);
                    lecTable.setSpacingAfter(10f);

                    lecTable.addCell(new PdfPCell(new Paragraph("Time")));

                    if (arr[0] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Monday")));
                    }

                    if (arr[1] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Tuesday")));
                    }

                    if (arr[2] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Wednesday")));
                    }

                    if (arr[3] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Thursday")));
                    }

                    if (arr[4] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Friday")));
                    }

                    if (arr[5] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Saturday")));
                    }

                    if (arr[6] == 1) {
                        lecTable.addCell(new PdfPCell(new Paragraph("Sunday")));
                    }

                    String query3 = "select startTime from timeslots where workingID=(select workingID from workingdaysandhours where batch='" + batch + "')";
                    stmt3 = con.prepareStatement(query3);
                    rs3 = stmt3.executeQuery();

                    while (rs3.next()) {
                        lecTable.addCell(new PdfPCell(new Paragraph(rs3.getString("startTime"))));

                        if (arr[0] == 1) {
                            String monData = getRoomTableData(room, batch, "Monday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(monData)));
                        }

                        if (arr[1] == 1) {
                            String tueData = getRoomTableData(room, batch, "Tuesday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(tueData)));
                        }

                        if (arr[2] == 1) {
                            String wedData = getRoomTableData(room, batch, "Wednesday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(wedData)));
                        }

                        if (arr[3] == 1) {
                            String thurData = getRoomTableData(room, batch, "Thursday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(thurData)));
                        }

                        if (arr[4] == 1) {
                            String friData = getRoomTableData(room, batch, "Friday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(friData)));
                        }

                        if (arr[5] == 1) {
                            String satData = getRoomTableData(room, batch, "Saturday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(satData)));
                        }

                        if (arr[6] == 1) {
                            String sunData = getRoomTableData(room, batch, "Sunday", rs3.getString("startTime"));
                            lecTable.addCell(new PdfPCell(new Paragraph(sunData)));
                        }
                    }
                    doc.add(lecTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboCategory = new javax.swing.JComboBox<>();
        comboSelect = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTimetable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        comboBatch = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1000, 10));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 250, 50));

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton2.setText("Generate Timetable");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 250, 50));

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton3.setText("View Timetable");
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 250, 50));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1000, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("View Specific Timetable");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Select a Category");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Select one");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, -1, -1));

        comboCategory.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoryActionPerformed(evt);
            }
        });
        jPanel1.add(comboCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        comboSelect.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSelectActionPerformed(evt);
            }
        });
        jPanel1.add(comboSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        tblTimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblTimetable);

        jScrollPane1.setViewportView(jScrollPane2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 610, 320));

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton4.setText("Download All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 180, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Select Batch");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        comboBatch.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        comboBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBatchActionPerformed(evt);
            }
        });
        jPanel1.add(comboBatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        jPanel2.setBackground(new java.awt.Color(102, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("Generate Timetable");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(250, 250, 250))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Generate_Timetable().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void comboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoryActionPerformed
        // TODO add your handling code here:

        loadSelectOne(comboCategory.getSelectedItem().toString());

        ArrayList<String> batches = new ArrayList<String>();

        if (comboCategory.getSelectedItem().toString().equals("Student Group")) {
            loadStudentTable();
            batches.add(null);
        } else if (comboCategory.getSelectedItem().toString().equals("Room")) {
            batches = loadBatch("select distinct(batch) from timetable where room='" + comboSelect.getSelectedItem().toString() + "'");
        } else if (comboCategory.getSelectedItem().toString().equals("Lecturer")) {
            String[] lec = comboSelect.getSelectedItem().toString().split("-");
            batches = loadBatch("SELECT DISTINCT(t.batch) FROM timetable t, sessions s where s.sessionID=t.session AND s.lecturer='" + lec[0] + "'");
        }

        comboBatch.setModel(new DefaultComboBoxModel<String>(batches.toArray(new String[0])));

        if (comboCategory.getSelectedItem().toString().equals("Room"))
            loadRoomTable(comboSelect.getSelectedItem().toString(), comboBatch.getSelectedItem().toString());
        else if (comboCategory.getSelectedItem().toString().equals("Lecturer")) {
            String[] lec = comboSelect.getSelectedItem().toString().split("-");
            loadLecturerTable(lec[0], comboBatch.getSelectedItem().toString());
        }
    }//GEN-LAST:event_comboCategoryActionPerformed

    private void comboSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSelectActionPerformed
        // TODO add your handling code here:
        ArrayList<String> batches = new ArrayList<String>();

        if (comboCategory.getSelectedItem().toString().equals("Student Group")) {
            loadStudentTable();
            batches.add(null);
        } else if (comboCategory.getSelectedItem().toString().equals("Room")) {
            batches = loadBatch("select distinct(batch) from timetable where room='" + comboSelect.getSelectedItem().toString() + "'");
        } else if (comboCategory.getSelectedItem().toString().equals("Lecturer")) {
            String[] lec = comboSelect.getSelectedItem().toString().split("-");
            batches = loadBatch("SELECT DISTINCT(t.batch) FROM timetable t, sessions s where s.sessionID=t.session AND s.lecturer='" + lec[0] + "'");
        }

        comboBatch.setModel(new DefaultComboBoxModel<String>(batches.toArray(new String[0])));

        if (comboCategory.getSelectedItem().toString().equals("Room"))
            loadRoomTable(comboSelect.getSelectedItem().toString(), comboBatch.getSelectedItem().toString());
        else if (comboCategory.getSelectedItem().toString().equals("Lecturer")) {
            String[] lec = comboSelect.getSelectedItem().toString().split("-");
            loadLecturerTable(lec[0], comboBatch.getSelectedItem().toString());
        }
    }//GEN-LAST:event_comboSelectActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        File file = null;

        try {
            boolean confirm = false;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                confirm = true;
            } else {
                JOptionPane.showMessageDialog(null, "Please select a download path", "Download Failed", JOptionPane.ERROR_MESSAGE);
                confirm = false;
            }

            if (confirm) {
                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream(file + "/Student Group Timetables.pdf"));
                doc.open();
                doc.add(new Paragraph("Student Group Timetables",
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD)));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                doc.add(new Paragraph(dtf.format(now)));
                doc.add(new Paragraph("------------------------------------------------------------------"));

                printStudent(doc);

                doc.close();

                Thread.sleep(10);

                Document doc1 = new Document();
                PdfWriter.getInstance(doc1, new FileOutputStream(file + "/Lecturers Timetables.pdf"));
                doc1.open();
                doc1.add(new Paragraph("Lecturers Timetables",
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD)));
                doc1.add(new Paragraph(dtf.format(now)));
                doc1.add(new Paragraph("------------------------------------------------------------------"));

                printLecturer(doc1);

                doc1.close();

                Thread.sleep(10);

                Document doc2 = new Document();
                PdfWriter.getInstance(doc2, new FileOutputStream(file + "/Rooms Timetables.pdf"));
                doc2.open();
                doc2.add(new Paragraph("Rooms Timetables",
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD)));
                doc2.add(new Paragraph(dtf.format(now)));
                doc2.add(new Paragraph("------------------------------------------------------------------"));

                printRoom(doc2);

                doc2.close();

                Thread.sleep(10);

                JOptionPane.showMessageDialog(null, "student Group, Lecturer and Room Timetables Generated", "Success", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void comboBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBatchActionPerformed
        // TODO add your handling code here:
        if (comboCategory.getSelectedItem().toString().equals("Room"))
            loadRoomTable(comboSelect.getSelectedItem().toString(), comboBatch.getSelectedItem().toString());
        else if (comboCategory.getSelectedItem().toString().equals("Lecturer")) {
            String[] lec = comboSelect.getSelectedItem().toString().split("-");
            loadLecturerTable(lec[0], comboBatch.getSelectedItem().toString());
        }
    }//GEN-LAST:event_comboBatchActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new IndexPage().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(View_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View_Timetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View_Timetable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBatch;
    private javax.swing.JComboBox<String> comboCategory;
    private javax.swing.JComboBox<String> comboSelect;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblTimetable;
    // End of variables declaration//GEN-END:variables
}
