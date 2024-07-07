package com.example.cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;


public class controle_login1 {

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private ComboBox<?> login_comboBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_loginbrtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private Hyperlink login_registreHere;

    @FXML
    private TextField login_username;

    @FXML
    private CheckBox registre_checkBox;

    @FXML
    private TextField registre_email;

    @FXML
    private AnchorPane registre_form;

    @FXML
    private Hyperlink registre_loginHere;

    @FXML
    private PasswordField registre_password;

    @FXML
    private Button registre_singUpBtn;

    @FXML
    private TextField registre_username;

    @FXML
    private Label titre;

    @FXML
    private Label titre1;

    @FXML
    private TextField login_showPassword;

    //@FXML
   // void login_checkBox(ActionEvent event) { }

    @FXML
  //  void registreAccount(ActionEvent event) {}


    //---------------------------switchentre page login et page registre ----------------------------------
    
    public void switchForm(ActionEvent event){
        if(event.getSource()==login_registreHere){
            login_form.setVisible(false);
            registre_form.setVisible(true);
        }
        else if (event.getSource()==registre_loginHere){
            login_form.setVisible(true);
            registre_form.setVisible(false);
        }
    }

    //*******************************************************************************************************




    





    //Data BAse toools -----------------------------------------
    private Connection connect ;
    private PreparedStatement prepare;
    private ResultSet result ;
    //------------------------------------------------------------


    private AlertMessage alert =new AlertMessage();

    public void signUp() throws SQLException {
        //String sql="INSERT INTO user (username,email,password) VALUES ( ?, ?, ?)";
        String sql="INSERT INTO `javaapp`.`user` (`username`, `email`, `password`) VALUES (?, ?, ?);";
        connect=DataBase.connect_DB();
        try {
            if(        registre_email.getText().isEmpty()
                    || registre_password.getText().isEmpty()
                    || registre_username.getText().isEmpty() ) {
                alert.errorMessage("anformation manquées");
            }
            else {
                if (connect == null) {
                    System.out.println("Connection is null. Please establish the connection first.");
                    return;
                }
                prepare=connect.prepareStatement(sql);
                prepare.setString(1,registre_email.getText());
                prepare.setString(2,registre_email.getText());
                prepare.setString(3,registre_password.getText());
                alert.successMessage("registre suceess");
                prepare.executeUpdate();

            }
        }
        catch (Exception e){e.printStackTrace(); }
    }

    public void login() throws SQLException{
        if(login_username.getText().isEmpty() || login_password.getText().isEmpty()){
            alert.errorMessage("username/password incorrect");
        }
        else {
            String sql="SELECT * FROM user WHERE username=? AND password=?";
            connect=DataBase.connect_DB();
            try{
                prepare=connect.prepareStatement(sql);
                prepare.setString(1,login_username.getText());
                prepare.setString(2,login_password.getText());
                result=prepare.executeQuery();
                if(result.next()){
                    alert.successMessage("login successfully");
                }
                else {
                    alert.errorMessage("username/password incorrect");
                }
            }
            catch (Exception e){e.printStackTrace();}

        }

    }


    public void  show_password() throws SQLException{
        if(login_checkBox.isSelected()){
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        }else{
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }
    }


