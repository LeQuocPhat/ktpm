/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.pojo.All;
import com.team.pojo.Chuyenbay;
import com.team.pojo.Ghe;
import com.team.pojo.Vechuyenbay;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class AdminController implements Initializable {

    @FXML private TableView tbCb;
    @FXML private Button btClose;
    @FXML private TextField txtMa;
    @FXML private TextField txtArrive;
    @FXML private TextField txtDepart;
    @FXML private TextField txtDaytime;
    @FXML private TextField txtTimeflight;
    @FXML private Label lbmess;
    @FXML private Button btUpdate;
    @FXML private Button btADatve;
    @FXML private Button btXem;
    @FXML private Button btXCb;
    @FXML private Button btAddghe;
    String p;
    
    @FXML private void btLogout (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btClose.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML public void btDatve (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            
            PrimaryController prc = loader.getController();
            prc.getName("admin");
            prc.getPass(p);
            prc.Back();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show(); 

            Stage stage1 = (Stage) this.btADatve.getScene().getWindow();
            stage1.close();
            
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getP(String text) {
        this.p = text;
    }
    
    private int addArrive() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement sta = conn.prepareStatement("SELECT count(1) FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        sta.setString(1, this.txtArrive.getText());
        ResultSet rsa = sta.executeQuery();
        
        int a = 0;
        while(rsa.next()) {
            if(rsa.getInt(1) == 1) {
                PreparedStatement stga = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stga.setString(1, this.txtArrive.getText());
                ResultSet rsga = stga.executeQuery();
                while(rsga.next()) {
                    a = rsga.getInt("id_sanbay");
                }
                stga.close();
            } else {
                Statement sti = conn.createStatement();
                sti.executeUpdate("INSERT INTO sanbay (sanbay) VALUES ('" + this.txtArrive.getText() + "')"); 
                
                PreparedStatement stga = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stga.setString(1, this.txtArrive.getText());
                ResultSet rsga = stga.executeQuery();
                while(rsga.next()) {
                    a = rsga.getInt("id_sanbay");
                }
                stga.close();
            } 
        }
        sta.close();
        conn.close();
        
        return a;
    }
    
    private int addDepart() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement std = conn.prepareStatement("SELECT count(1) FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        std.setString(1, this.txtDepart.getText());
        ResultSet rsd = std.executeQuery();
        
        int d = 0;
        while(rsd.next()) {
            if(rsd.getInt(1) == 1) {
                PreparedStatement stgd = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stgd.setString(1, this.txtDepart.getText());
                ResultSet rsga = stgd.executeQuery();
                while(rsga.next()) {
                    d = rsga.getInt("id_sanbay");
                }
                stgd.close();
            } else {
                Statement sti = conn.createStatement();
                sti.executeUpdate("INSERT INTO sanbay (sanbay) VALUES ('" + this.txtDepart.getText() + "')");  
                
                PreparedStatement stgd = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stgd.setString(1, this.txtDepart.getText());
                ResultSet rsga = stgd.executeQuery();
                while(rsga.next()) {
                    d = rsga.getInt("id_sanbay");
                }
                stgd.close();
            } 
        }
        
        std.close();
        conn.close();
        
        return d;
    }
    
    private void addcb(Chuyenbay cb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement sta = conn.prepareStatement("INSERT INTO chuyenbay (ma, arrive_id, depart_id, daytime, timeflight) VALUES "
                        + "('" + cb.getMa() + "', " + this.addArrive() + ", " + this.addDepart()
                        + ", '" + cb.getDaytime() + "', '" + cb.getTimeflight() + "')");
        sta.executeUpdate();
        conn.close();
    }
    
    @FXML private void btAdd (ActionEvent Event) throws SQLException {
        if(this.txtMa.getText() == "" || this.txtDepart.getText() == "" || 
                this.txtArrive.getText() == "" || this.txtDaytime.getText() == "" 
                || this.txtTimeflight.getText() == "") {
            this.lbmess.setText("Information Empty!!");
            this.txtMa.setStyle("-fx-border-color: red;");
            this.txtArrive.setStyle("-fx-border-color: red;");
            this.txtDepart.setStyle("-fx-border-color: red;");
            this.txtDaytime.setStyle("-fx-border-color: red;");
            this.txtTimeflight.setStyle("-fx-border-color: red;");
            
        } else {
            try {
                Chuyenbay cb = new Chuyenbay(this.txtMa.getText(), this.txtArrive.getText(), this.txtDepart.getText(), this.txtDaytime.getText(), this.txtTimeflight.getText());
                addcb(cb);
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Addghe.fxml"));
                Parent root = (Parent) loader.load();
                
                AddgheController agc = loader.getController();
                agc.getCB(this.txtMa.getText(), this.txtDaytime.getText(), this.txtTimeflight.getText());
                agc.getP(this.p);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
                Stage stage1 = (Stage) this.btAddghe.getScene().getWindow();
                stage1.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void updatecb(Chuyenbay cb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stu = conn.createStatement();
        stu.executeUpdate("UPDATE chuyenbay SET arrive_id = " + this.addArrive() + ", depart_id = " + this.addDepart() +
                            ", daytime = '" + cb.getDaytime() + "', timeflight = '" + cb.getTimeflight() + 
                            "', ma = '" + cb.getMa() + "' WHERE ma = '" + cb.getMa() + "'");
    }
    
    @FXML private void btUdate (ActionEvent Event) throws SQLException {
        try {
            Chuyenbay cb = (Chuyenbay) this.tbCb.getSelectionModel().getSelectedItem();
            updatecb(cb);

            this.tbCb.getItems().clear();
            this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update !!!");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void btXemcb (ActionEvent Event) {
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.btXem.setVisible(true);
        this.btXCb.setVisible(false);
    }
    
    @FXML private void btXemve (ActionEvent Event) {
        try {
            if("Xem vé".equals(this.btXem.getText())) {
                Connection conn = JdbcUtils.getConn();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM vechuyenbay");
                List lvcb = new ArrayList<>();
                while(rs.next()) {
                    All a = new All();
                    Statement stcb = conn.createStatement();
                    ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE id_chuyenbay = " + rs.getString("chuyenbay_id"));
                    while(rscb.next()) {
                        a.setMa(rscb.getString("ma"));
                        Statement stm1 = conn.createStatement();
                        ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("arrive_id"));
                        while (rs1.next()) {
                            a.setArrive(rs1.getString("sanbay"));
                        }
                        rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("depart_id"));
                        while (rs1.next()) {
                            a.setDepart(rs1.getString("sanbay"));
                        }
                        stm1.close();
                        a.setDaytime(rscb.getString("daytime"));
                        a.setTimeflight(rscb.getString("timeflight"));
                    }
                    Statement stkh = conn.createStatement();
                    ResultSet rskh = stkh.executeQuery("SELECT name FROM khachhang WHERE id_khachhang = " + rs.getString("khachhang_id"));
                    while(rskh.next()) {
                        a.setName(rskh.getString(1));
                    }
                    Statement stg = conn.createStatement();
                    ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + rs.getString("ghe_id"));
                    while(rsg.next()) {
                        a.setGia(String.format("%,d", Integer.parseInt(rsg.getString("gia"))) + " VND");
                        if("1".equals(rsg.getString("loai"))) {
                            a.setLoai("Primary Class");
                        } else {
                            a.setLoai("Secondary Class");
                        }
                    }
                    lvcb.add(a);
                }

                TableColumn colMa = new TableColumn("Ma");
                colMa.setCellValueFactory(new PropertyValueFactory("ma"));

                TableColumn colArrive = new TableColumn("Arrive");
                colArrive.setCellValueFactory(new PropertyValueFactory("arrive"));

                TableColumn colDepart = new TableColumn("Depart");
                colDepart.setCellValueFactory(new PropertyValueFactory("depart"));

                TableColumn colDayTime = new TableColumn("DayTime");
                colDayTime.setCellValueFactory(new PropertyValueFactory("daytime"));

                TableColumn colTimeFlight = new TableColumn("TimeFlight");
                colTimeFlight.setCellValueFactory(new PropertyValueFactory("timeflight"));

                TableColumn colName = new TableColumn("Customers's Name");
                colName.setCellValueFactory(new PropertyValueFactory("name"));

                TableColumn colLoai = new TableColumn("Classes");
                colLoai.setCellValueFactory(new PropertyValueFactory("loai"));

                TableColumn colPrice = new TableColumn("Price");
                colPrice.setCellValueFactory(new PropertyValueFactory("gia"));

                this.tbCb.getItems().clear();
                this.tbCb.getColumns().clear();
                this.tbCb.setItems(FXCollections.observableArrayList(lvcb));
                this.tbCb.getColumns().addAll(colMa, colArrive, colDepart, colDayTime, colTimeFlight, colName, colLoai, colPrice);
                
                this.btXem.setVisible(false);
                this.btXCb.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    private List<Chuyenbay> getChuyenbays() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM chuyenbay");
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Chuyenbay cb = new Chuyenbay();
            cb.setId_chuyenbay(rs.getString("id_chuyenbay"));
            cb.setMa(rs.getString("ma"));
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("arrive_id"));
            while (rs1.next()) {
                cb.setArrive(rs1.getString("sanbay"));
            }
            rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("depart_id"));
            while (rs1.next()) {
                cb.setDepart(rs1.getString("sanbay"));
            }
            stm1.close();
            cb.setDaytime(rs.getString("daytime"));
            cb.setTimeflight(rs.getString("timeflight"));
            
            Statement stg1 = conn.createStatement();
            ResultSet rsg1 = stg1.executeQuery("SELECT soluong FROM ghe WHERE chuyenbay_id = " + rs.getString("id_chuyenbay") + " AND loai = 1");
            while (rsg1.next()) {
                cb.setSoghe1(rsg1.getString(1));
            }
            
            Statement stg2 = conn.createStatement();
            ResultSet rsg2 = stg2.executeQuery("SELECT soluong FROM ghe WHERE chuyenbay_id = " + rs.getString("id_chuyenbay") + " AND loai = 2");
            while (rsg2.next()) {
                cb.setSoghe2(rsg2.getString(1));
            }
            
            lcb.add(cb);
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);
    }
    
    private void deleteChuyenbay(String id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        conn.setAutoCommit(false);
        Statement ps = conn.createStatement();
        ps.executeUpdate("DELETE FROM ghe WHERE chuyenbay_id = " + id);
        
        Statement psg = conn.createStatement();
        psg.executeUpdate("DELETE FROM chuyenbay WHERE id_chuyenbay = " + id);
        
        conn.commit();
    }
    
    private void loadChuyenbay() throws SQLException {
        
        TableColumn colMa = new TableColumn("Ma"); 
        colMa.setCellValueFactory(new PropertyValueFactory("ma"));
        
        TableColumn colArrive = new TableColumn("Arrive"); 
        colArrive.setCellValueFactory(new PropertyValueFactory("arrive"));
        
        TableColumn colDepart = new TableColumn("Depart"); 
        colDepart.setCellValueFactory(new PropertyValueFactory("depart"));
        
        TableColumn colDayTime = new TableColumn("DayTime"); 
        colDayTime.setCellValueFactory(new PropertyValueFactory("daytime"));
        
        TableColumn colTimeFlight = new TableColumn("TimeFlight"); 
        colTimeFlight.setCellValueFactory(new PropertyValueFactory("timeflight"));
        
        TableColumn colSoghe1 = new TableColumn("Primary's Chairs"); 
        colSoghe1.setCellValueFactory(new PropertyValueFactory("soghe1"));
        
        TableColumn colSoghe2 = new TableColumn("Secondary's Chairs"); 
        colSoghe2.setCellValueFactory(new PropertyValueFactory("soghe2"));

        
        TableColumn colDelete = new TableColumn("Delete");
        colDelete.setCellFactory(p -> {
            Button btDelete = new Button("Delete");
            btDelete.setOnAction(et -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure to delete this chuyenbay ??");
                alert.showAndWait().ifPresent(res -> {
                    if(res == ButtonType.OK) {
                        TableCell cl = (TableCell) ((Button)et.getSource()).getParent();
                        Chuyenbay cb = (Chuyenbay)cl.getTableRow().getItem();
                        try {
                            this.deleteChuyenbay(cb.getId_chuyenbay());
                            
                            this.tbCb.getItems().clear();
                            this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btDelete);
            return cell;
        });
        
        this.tbCb.getColumns().clear();
        this.tbCb.getItems().clear();
        this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        this.tbCb.getColumns().addAll(colMa, colArrive, colDepart,  colDayTime, colTimeFlight, colSoghe1, colSoghe2, colDelete); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btXem.setVisible(true);
        this.btXCb.setVisible(false);
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.btUpdate.setVisible(false);
        
        this.tbCb.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(t -> {
                this.btUpdate.setVisible(true);
                Chuyenbay q = (Chuyenbay) this.tbCb.getSelectionModel().getSelectedItem();
                this.txtMa.setText(q.getMa());
                this.txtArrive.setText(q.getArrive());
                this.txtDepart.setText(q.getDepart());
                this.txtDaytime.setText(q.getDaytime());
                this.txtTimeflight.setText(q.getTimeflight());
            });
            return row;
        });
    }    
    
}
