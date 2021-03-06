/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package location;

import Common.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ishma
 */
public class UIroom2 extends javax.swing.JFrame {

    Connection con = null;
    Statement st;
    ArrayList<building> rooms = new ArrayList<>();
    private static int auto = 0;

    /**
     * Creates new form UIroom2
     */
    public UIroom2() {
        initComponents();
        fetch();
        loadbuilding();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        roomtxt = new javax.swing.JTextField();
        building = new javax.swing.JComboBox<>();
        hall = new javax.swing.JComboBox<>();
        lab = new javax.swing.JComboBox<>();
        type = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        floor = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomtable = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setText("BUILDING - LOCATION");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 255, 255));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("HOME");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("ROOMS - LOCATION");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("3 . FLOOR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("1 . ROOM NAME ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("2 . BUILDING NAME");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("5 . HALL SIZE");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("4 . ROOM TYPE");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("6 . LAB SIZE");

        roomtxt.setEditable(false);
        roomtxt.setBackground(new java.awt.Color(204, 204, 204));
        roomtxt.setForeground(new java.awt.Color(102, 255, 255));
        roomtxt.setToolTipText("THE ROOM ID WILL BE GENERATED");
        roomtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomtxtActionPerformed(evt);
            }
        });

        building.setBackground(new java.awt.Color(204, 204, 204));
        building.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingActionPerformed(evt);
            }
        });

        hall.setBackground(new java.awt.Color(204, 204, 204));
        hall.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LARGE ", "MEDIUM ", "SMALL", "NULL", " ", " " }));

        lab.setBackground(new java.awt.Color(204, 204, 204));
        lab.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LARGE", "SMALL", "NULL", " " }));

        type.setBackground(new java.awt.Color(204, 204, 204));
        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HALL", "LAB" }));
        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("ADD");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roomtxt)
                    .addComponent(building, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hall, 0, 181, Short.MAX_VALUE)
                    .addComponent(lab, 0, 181, Short.MAX_VALUE)
                    .addComponent(type, 0, 181, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(floor))
                .addGap(41, 41, 41))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(265, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(roomtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(building, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(floor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hall, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(389, Short.MAX_VALUE)))
        );

        roomtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "build", "floor", "type", "hall", "lab"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        roomtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomtableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(roomtable);

        jButton3.setBackground(new java.awt.Color(0, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setText("UPDATE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomtxtActionPerformed

    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        int i = roomtable.getSelectedRow();
        if (i >= 0) {
            int option = JOptionPane.showConfirmDialog(rootPane,
                    "Are you sure you want to Delete?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                TableModel model = roomtable.getModel();

                String name = model.getValueAt(i, 0).toString();
                if (roomtable.getSelectedRows().length == 1) {
                    delete(name);
                    DefaultTableModel model1 = (DefaultTableModel) roomtable.getModel();
                    model1.setRowCount(0);
                    fetch();
                    clear();
                }
            }
        } else {
            alert("Please select a row to delete");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        //text field
        String id = roomtxt.getText().trim();
        Integer floors = (Integer) floor.getValue();

        //drop down 			
        String build = building.getSelectedItem().toString().trim();
        String types = type.getSelectedItem().toString().trim();
        String halls = hall.getSelectedItem().toString().trim();
        String labs = lab.getSelectedItem().toString().trim();

        if (floors > 0 && !build.isEmpty() && !types.isEmpty() && !halls.isEmpty()
                && !labs.isEmpty()) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String sql = "select * from room where roomID='" + id + "'";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (!rs.next()) {
                    String ty;
                    if (types.matches("HALL")) {
                        ty = "H";
                    } else {
                        ty = "L";
                    }

                    auto++;

                    char bui = build.charAt(0);
                    int flr = floors;
                    int uni = auto;

                    id = bui + ty + flr + uni;

                    save(id, build, floors, types, halls, labs);
                    DefaultTableModel model = (DefaultTableModel) roomtable.getModel();

                    Vector vv = new Vector();
                    vv.add(id);
                    vv.add(build);
                    vv.add(floors);
                    vv.add(types);
                    vv.add(halls);
                    vv.add(labs);

                    model.addRow(vv);

                    /* Object[] row = new Object[10];
                    row[0] = name;
                    row[1] = dep;
                    row[2] = fac;
                    row[3] = floor;
                    row[4] = hlval;
                    row[5] = hmval;
                    row[6] = hsval;
                    row[7] = llval;
                    row[8] = lsval;
                    
                    model.addRow(row);
                     */
                } else {
                    alert("Please provide a different name number", "Similar name used");
                }

                clear();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    //con.close();
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } else {
            alert("please fill in all the details");
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        //text field
        String id = roomtxt.getText().trim();
        Integer floors = (Integer) floor.getValue();

        //drop down 			
        String build = building.getSelectedItem().toString().trim();
        String types = type.getSelectedItem().toString().trim();
        String halls = hall.getSelectedItem().toString().trim();
        String labs = lab.getSelectedItem().toString().trim();

        if (floors > 0 && !build.isEmpty() && !types.isEmpty() && !halls.isEmpty()
                && !labs.isEmpty()) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String sql = "select * from room where roomID='" + id + "'";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    update(id, build, floors, types, halls, labs);
                    DefaultTableModel model = (DefaultTableModel) roomtable.getModel();
                    model.setRowCount(0);
                    fetch();
                    alert("Update was successful");

                } else {
                    alert("There is no such student", "Update error");
                    clear();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert("There is nothing to update :(", "No row selected");
        }
    }//GEN-LAST:event_jButton5ActionPerformed


    private void roomtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomtableMouseClicked
        // TODO add your handling code here:
        int i = roomtable.getSelectedRow();
        TableModel model = roomtable.getModel();

        roomtxt.setText(model.getValueAt(i, 0).toString());
        building.setSelectedItem(model.getValueAt(i, 1).toString());
        floor.setValue(model.getValueAt(i, 2));
        type.setSelectedItem(model.getValueAt(i, 3).toString());
        hall.setSelectedItem(model.getValueAt(i, 4).toString());
        lab.setSelectedItem(model.getValueAt(i, 5).toString());

    }//GEN-LAST:event_roomtableMouseClicked

    private void buildingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buildingActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new newsubhomepage().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    //method to show an info alert
    public void alert(String msg) {
        JOptionPane.showMessageDialog(rootPane, msg);
    }

    //method to show an error alert
    public void alert(String msg, String title) {
        JOptionPane.showMessageDialog(rootPane, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    //delete details in the db
    public void delete(String id) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "DELETE FROM `room` WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //update the db
    public void update(String id, String build, int floor, String type, String hall, String lab) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "UPDATE `room`SET buildingName='" + build + "',floor='" + floor + "',type='" + type + "',hall='" + hall + "',lab='" + lab + "'WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //method to save building to the db
    public void save(String id, String build, int floor, String type, String hall, String lab) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "INSERT INTO `room`(`roomID`, `buildingName`, `floor` , `type` ,`hall` ,`lab`)"
                    + "VALUES ('" + id + "','" + build + "','" + floor + "','" + type + "','" + hall + "','" + lab + "')";
            st = con.createStatement();
            st.execute(sql);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UIroom2.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    public void doMethod(int auto) {

        auto = 0;

        auto++;

    }

    //clear
    private void clear() {
        roomtxt.setText("");
        building.setSelectedItem("A");
        type.setSelectedItem("L");
        floor.setValue(0);
        hall.setSelectedItem("L");
        lab.setSelectedItem("L");

    }

    //fetch
    private void fetch() {
        rooms.clear();

        try {
            DefaultTableModel dtm = (DefaultTableModel) roomtable.getModel();
            dtm.setRowCount(0);

            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "select roomID,buildingName,floor,type,hall,lab from room";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Vector vv = new Vector();

                String gette = rs.getString("roomID");

                String gette3 = rs.getString("buildingName");
                int gette4 = rs.getInt("floor");
                String gette5 = rs.getString("type");
                String gette6 = rs.getString("hall");
                String gette7 = rs.getString("lab");

                vv.add(gette);
                vv.add(gette3);
                vv.add(gette4);
                vv.add(gette5);
                vv.add(gette6);
                vv.add(gette7);

                dtm.addRow(vv);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadbuilding() {
        try {

            if (con == null) {
                con = DBConnection.getDBConnection();
            }
            String sql = "select * from building";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            Vector vv = new Vector();

            while (rs.next()) {

                String gette = rs.getString("buildingName");

                vv.add(gette);

            }

            building.setModel(new DefaultComboBoxModel<>(vv));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            java.util.logging.Logger.getLogger(UIroom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIroom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIroom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIroom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIroom2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> building;
    private javax.swing.JSpinner floor;
    private javax.swing.JComboBox<String> hall;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> lab;
    private javax.swing.JTable roomtable;
    private javax.swing.JTextField roomtxt;
    private javax.swing.JComboBox<String> type;
    // End of variables declaration//GEN-END:variables
}
