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
public class stugp extends javax.swing.JFrame {

    /**
     * Creates new form Stugp
     */
    Connection con = null;
    Statement st;
    ArrayList<group> groups = new ArrayList<>();

    public stugp() {
        initComponents();
        fetch();
        loadgroupid();
        loadsubgroupid();

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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        maingroup = new javax.swing.JComboBox<>();
        main = new javax.swing.JButton();
        roomid1 = new javax.swing.JTextField();
        delallo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        subgroup = new javax.swing.JComboBox<>();
        subbutton = new javax.swing.JButton();
        roomid2 = new javax.swing.JTextField();
        delsub = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        grouptable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("HOME");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("ALLOCATE ROOMS FOR GROUPS ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("ROOM ID ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("STUDENT GROUP");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("STUDENT GROUP ");

        maingroup.setBackground(new java.awt.Color(153, 255, 255));
        maingroup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        maingroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        main.setBackground(new java.awt.Color(102, 102, 102));
        main.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        main.setForeground(new java.awt.Color(255, 255, 255));
        main.setText("ALLOCATION ");
        main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainActionPerformed(evt);
            }
        });

        roomid1.setEditable(false);

        delallo.setBackground(new java.awt.Color(102, 102, 102));
        delallo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        delallo.setForeground(new java.awt.Color(255, 255, 255));
        delallo.setText(" CANCEL ALLOCATION");
        delallo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delalloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(delallo, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(111, 111, 111)
                                        .addComponent(jLabel4)))
                                .addGap(40, 40, 40))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(roomid1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(maingroup, javax.swing.GroupLayout.Alignment.TRAILING, 0, 209, Short.MAX_VALUE)))
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roomid1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(maingroup, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(delallo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("SUB - STUDENT GROUP");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("ROOM ID ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("SUB- STUDENT GROUP");

        subgroup.setBackground(new java.awt.Color(153, 255, 255));
        subgroup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        subgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        subbutton.setBackground(new java.awt.Color(102, 102, 102));
        subbutton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        subbutton.setForeground(new java.awt.Color(255, 255, 255));
        subbutton.setText("ALLOCATE");
        subbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subbuttonActionPerformed(evt);
            }
        });

        roomid2.setEditable(false);

        delsub.setBackground(new java.awt.Color(102, 102, 102));
        delsub.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        delsub.setForeground(new java.awt.Color(255, 255, 255));
        delsub.setText("CANCEL ALLOCATION ");
        delsub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delsubActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(65, 65, 65)
                                    .addComponent(jLabel5))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(roomid2))))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(delsub, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(roomid2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(subbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(delsub, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        grouptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ROOMID", "GROUP ID ", "SUB GROUP ID "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grouptable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grouptableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grouptable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new newsubhomepage().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void mainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainActionPerformed
        // TODO add your handling code here:
        String roomid = roomid1.getText().trim();
        String maingp = maingroup.getSelectedItem().toString().trim();

        if (!roomid.isEmpty() && (!maingp.isEmpty() || maingp.isEmpty())) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String sql = "select * from room where roomID='" + roomid + "'";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.first()) {
                    update1(roomid, maingp);
                    DefaultTableModel model = (DefaultTableModel) grouptable.getModel();
                    model.setRowCount(0);
                    fetch();
                    alert("Update was successful");

                } else {
                    alert("There is no such ROOM ID ", "Update error");
                    clear();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert("There is nothing to update :(", "No row selected");
        }


    }//GEN-LAST:event_mainActionPerformed

    private void subbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subbuttonActionPerformed
        // TODO add your handling code here:
        String roomid = roomid1.getText().trim();
        String subgp = subgroup.getSelectedItem().toString().trim();

        if (!roomid.isEmpty() && (!subgp.isEmpty() || subgp.isEmpty())) {
            try {
                if (con == null) {
                    con = DBConnection.getDBConnection();
                }

                String sql = "select * from room where roomID='" + roomid + "'";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.first()) {
                    update2(roomid, subgp);
                    DefaultTableModel model = (DefaultTableModel) grouptable.getModel();
                    model.setRowCount(0);
                    fetch();
                    alert("Update was successful");

                } else {
                    alert("There is no such ROOM ID ", "Update error");
                    clear();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert("There is nothing to update :(", "No row selected");
        }


    }//GEN-LAST:event_subbuttonActionPerformed

    private void grouptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grouptableMouseClicked
        // TODO add your handling code here:
        int i = grouptable.getSelectedRow();
        TableModel model = grouptable.getModel();

        roomid1.setText(model.getValueAt(i, 0).toString());
        roomid2.setText(model.getValueAt(i, 0).toString());

        maingroup.setSelectedItem(model.getValueAt(i, 1).toString());
        subgroup.setSelectedItem(model.getValueAt(i, 2).toString());


    }//GEN-LAST:event_grouptableMouseClicked

    private void delalloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delalloActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int i = grouptable.getSelectedRow();
        if (i >= 0) {
            int option = JOptionPane.showConfirmDialog(rootPane,
                    "Are you sure you want to Delete?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                TableModel model = grouptable.getModel();

                String id = model.getValueAt(i, 0).toString();
                if (grouptable.getSelectedRows().length == 1) {
                    delete1(id);
                    DefaultTableModel model1 = (DefaultTableModel) grouptable.getModel();
                    model1.setRowCount(0);
                    fetch();
                    clear();
                }
            }
        } else {
            alert("Please select a row to delete");
        }


    }//GEN-LAST:event_delalloActionPerformed

    private void delsubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delsubActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int i = grouptable.getSelectedRow();
        if (i >= 0) {
            int option = JOptionPane.showConfirmDialog(rootPane,
                    "Are you sure you want to Delete?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                TableModel model = grouptable.getModel();

                String id = model.getValueAt(i, 0).toString();
                if (grouptable.getSelectedRows().length == 1) {
                    delete2(id);
                    DefaultTableModel model1 = (DefaultTableModel) grouptable.getModel();
                    model1.setRowCount(0);
                    fetch();
                    clear();
                }
            }
        } else {
            alert("Please select a row to delete");
        }


    }//GEN-LAST:event_delsubActionPerformed

    //delete details in the db
    public void delete1(String id) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "UPDATE `room`SET studentGroup=null WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //delete details in the db
    public void delete2(String id) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "UPDATE `room`SET studentSubGroup=null WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //update the db
    public void update2(String id, String sub) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "UPDATE `room`SET studentSubGroup='" + sub + "'WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //update the db
    public void update1(String id, String main) {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "UPDATE `room`SET studentGroup='" + main + "'WHERE roomID='" + id + "'";
            st = con.createStatement();
            st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(stugp.class.getName()).log(Level.SEVERE, null, ex);
        }
//        fetch();
    }

    //method to show an info alert
    public void alert(String msg) {
        JOptionPane.showMessageDialog(rootPane, msg);
    }

    //method to show an error alert
    public void alert(String msg, String title) {
        JOptionPane.showMessageDialog(rootPane, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    private void clear() {
        roomid1.setText("");
        roomid2.setText("");
        maingroup.setSelectedItem("SELECT");
        subgroup.setSelectedItem("SELECT");
    }

    private void fetch() {
        groups.clear();

        try {
            DefaultTableModel dtm = (DefaultTableModel) grouptable.getModel();
            dtm.setRowCount(0);

            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "select * from room";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Vector vv = new Vector();

                String gette = rs.getString("roomID");
                String gette2 = rs.getString("studentGroup");
                String gette3 = rs.getString("studentSubGroup");

                vv.add(gette);
                vv.add(gette2);
                vv.add(gette3);

                dtm.addRow(vv);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadsubgroupid() {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "select * from studentsubgroup";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            ArrayList<String> subidlist = new ArrayList<String>();

            subidlist.add("SELECT");
            while (rs.next()) {
                subidlist.add(rs.getString("subGroupID"));
            }
            subgroup.setModel(new DefaultComboBoxModel<String>(subidlist.toArray(new String[0])));

            /*  Vector vv = new Vector();

            while (rs.next()) {
                String gette = rs.getString("subGroupID");
                
                 vv.add(gette);

            }
            
            subgroup.setModel(new DefaultComboBoxModel<>(vv));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadgroupid() {
        try {
            if (con == null) {
                con = DBConnection.getDBConnection();
            }

            String sql = "select * from studentgroup";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            ArrayList<String> idlist = new ArrayList<String>();

            idlist.add("SELECT");
            while (rs.next()) {
                idlist.add(rs.getString("groupID"));
            }
            maingroup.setModel(new DefaultComboBoxModel<String>(idlist.toArray(new String[0])));

            /*     Vector vv = new Vector();

            while (rs.next()) {
                String gette = rs.getString("groupID");
                
                 vv.add(gette);

            }
            
            maingroup.setModel(new DefaultComboBoxModel<>(vv));*/
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
            java.util.logging.Logger.getLogger(stugp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stugp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stugp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stugp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stugp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delallo;
    private javax.swing.JButton delsub;
    private javax.swing.JTable grouptable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton main;
    private javax.swing.JComboBox<String> maingroup;
    private javax.swing.JTextField roomid1;
    private javax.swing.JTextField roomid2;
    private javax.swing.JButton subbutton;
    private javax.swing.JComboBox<String> subgroup;
    // End of variables declaration//GEN-END:variables
}