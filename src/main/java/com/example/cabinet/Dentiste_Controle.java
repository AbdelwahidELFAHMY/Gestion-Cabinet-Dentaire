    package com.example.cabinet;
    
    import com.itextpdf.text.Font;
    import com.itextpdf.text.Image;
    import javafx.collections.FXCollections;
    import com.itextpdf.text.*;
    import com.itextpdf.text.pdf.*;
    import com.itextpdf.text.Document;
    import com.itextpdf.text.DocumentException;
    import com.itextpdf.text.Paragraph;
    import com.itextpdf.text.pdf.PdfWriter;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.Button;
    import javafx.scene.control.TextField;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.stage.Stage;

    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.sql.*;
    
    public class Dentiste_Controle {
    
        @FXML
        private Button Accueil;
    
        @FXML
        private Button buttonCherche;
    
        @FXML
        private Button btnClose;
    
        @FXML
        private Button consultation;
    
        @FXML
        private Button ordonnance;
    
        @FXML
        private Button consultation_enregistrer;

        @FXML
        private Button consultation_reset;

        @FXML
        private Button deconnecter;
    
        @FXML
        private TextField cherche_patient;
    
        @FXML
        private DatePicker consultation_date;
    
        @FXML
        private TextField consultation_description;
    
        @FXML
        private TextField consultation_idRV;
    
        @FXML
        private TextField consultation_idmaladie;
    
        @FXML
        private TextField consultation_idpatient;
    
        @FXML
        private TextField consultation_nompatient;
    
        @FXML
        private TextField consultation_prenompatient;
    
        @FXML
        private TextField consultation_traitemrnt;
    
    
        @FXML
        private TableColumn<Patient, Integer> table_id_rendez_vous;
    
        @FXML
        private TableColumn<Patient, String> table_nom;
    
        @FXML
        private TableColumn<Patient, Integer> table_numeroSecuriteSociale;
    
        @FXML
        private TableView<Patient> table_patient;
        @FXML
        private TableColumn<Patient, String> table_CIN;
    
        @FXML
        private TableColumn<Patient, Integer> table_id_Patient;
    
        @FXML
        private TableColumn<Patient,String> table_prenom;
    
    
         // ******************************************** Tableau de consulatation :
        @FXML
        private Button afficherConsultation;
    
        @FXML
        private TableView<Consultation> table_consultation;
    
        @FXML
        private TableColumn<Consultation, Integer> table__idmaladie;
    
    
        @FXML
        private TableColumn<Patient, String> table_prenomPatient;
    
        @FXML
        private TableColumn<Patient, String> table_nomPatient;
    
        @FXML
        private TableColumn<?, ?> table_action;
    
        @FXML
        private TableColumn<Consultation, Date> table_dateconsultation;
    
        @FXML
        private TableColumn<Consultation, Integer> table_idRDV;
    
        @FXML
        private TableColumn<Consultation, Integer> table_idconsultation;
    
        @FXML
        private TableColumn<Consultation, Integer> table_idpatient;
    
        @FXML
        private TableColumn<Consultation,String> table_traitement;
        //***********************************************************************
    
    
        AlertMessage alertMessage = new AlertMessage();
    
    
    
        private Patient searchPatient() {
            String patientCIN = cherche_patient.getText();
            String sql = "SELECT * FROM patient WHERE CIN LIKE ?";
    
            try (Connection connect = DataBase.connect_DB();
                 PreparedStatement statement = connect.prepareStatement(sql)) {
                statement.setString(1, "%" + patientCIN + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id_patient");
                        String nom = resultSet.getString("nom");
                        String prenom = resultSet.getString("prenom");
                        String adresse = resultSet.getString("adresse");
                        String numeroSecuriteSociale = resultSet.getString("numeroSecuriteSociale");
                        String telephone = resultSet.getString("telephone");
                        String CIN = resultSet.getString("CIN");
                        return new Patient(id, nom, prenom, numeroSecuriteSociale, adresse, telephone, CIN);
                    } else {
                        alertMessage.errorMessage("oups le patient n'est pas la");
                        return null;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // À améliorer pour une gestion plus précise des erreurs
            }
            return null;
        }
    
    
    
        public void ChercheButton() {
            if (cherche_patient.getText().isEmpty()) {
                alertMessage.errorMessage("Le champ de recherche est vide");
            }
            else {
                Patient patient = searchPatient();
                if (patient != null) {
                    ObservableList<Patient> data = FXCollections.observableArrayList(patient);
                    table_id_Patient.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id_patient"));
                    table_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    table_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                    table_CIN.setCellValueFactory(new PropertyValueFactory<>("CIN"));
                    table_numeroSecuriteSociale.setCellValueFactory(new PropertyValueFactory<>("numeroSecuriteSociale"));
                    table_patient.setItems(data);
                } else {
                    alertMessage.errorMessage("Le patient n'est pas enregistré dans votre base de données !");
                }
            }
        }
    
    
    
        public void setConsultation() {
            if (consultation_description.getText().isEmpty() || consultation_idRV.getText().isEmpty() || consultation_traitemrnt.getText().isEmpty() || consultation_idmaladie.getText().isEmpty() || consultation_nompatient.getText().isEmpty() || consultation_prenompatient.getText().isEmpty() || consultation_idpatient.getText().isEmpty()) {
                alertMessage.errorMessage("Remplir tous les champs c'est necessaire");
            }
            String consultationDescription = consultation_description.getText();
            String consultationTraitemrnt=consultation_traitemrnt.getText();
            String consultationIdmaladie =consultation_idmaladie.getText();
            String consultationIdRV = consultation_idRV.getText();
            String consultationPrenompatient =consultation_prenompatient.getText();
            String consultationNompatient =consultation_nompatient.getText();
            String consultationIdpatient=consultation_idpatient.getText();
            Date consultationDate= Date.valueOf(consultation_date.getValue());
    
            String insertQuery = "INSERT INTO consultation (description, traitementPrescrit, id_maladie, id_rendez_vous , id_patient, dateConsultation,id_dentiste) VALUES (?, ?, ?, ?, ?, ?,1)";
            try (Connection conn = DataBase.connect_DB();
                 PreparedStatement statement = conn.prepareStatement(insertQuery)) {
    
                statement.setString(1, consultationDescription);
                statement.setString(2, consultationTraitemrnt);
                statement.setString(3, consultationIdmaladie);
                statement.setString(4, consultationIdRV);
                statement.setString(5, consultationIdpatient);
                statement.setDate(6, consultationDate);
    
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    alertMessage.successMessage("La consultation a été ajoutée avec succès !");
                    consultation_description.setText("");
                    consultation_traitemrnt.setText("");
                    consultation_idmaladie.setText("");
                    consultation_idRV.setText("");
                    consultation_idpatient.setText("");
                    consultation_date.setValue(null);
                    consultation_prenompatient.setText("");
                    consultation_nompatient.setText("");
                    
                    
                } else {
                    alertMessage.errorMessage("Échec de l'ajout de la consultation.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                alertMessage.errorMessage("Erreur de base de données : " + e.getMessage());
            }
    
    
        }


        public void resetForm() {
            consultation_description.setText("");
            consultation_traitemrnt.setText("");
            consultation_idmaladie.setText("");
            consultation_idRV.setText("");
            consultation_idpatient.setText("");
            consultation_nompatient.setText("");
            consultation_prenompatient.setText("");
            consultation_date.setValue(null);
        }

        public void loadDataFromDatabase() {
            Connection connection = DataBase.connect_DB();
            try {
               // String sql = "SELECT * FROM consultation";
                String sql2 = "SELECT c.*, p.nom, p.prenom FROM consultation c JOIN patient p ON c.id_patient = p.id_patient";
    
                PreparedStatement statement = connection.prepareStatement(sql2);
                ResultSet resultSet = statement.executeQuery();
                table_consultation.getItems().clear();
                ObservableList<Consultation> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    Consultation consultation = new Consultation(
                            resultSet.getInt("id_consultation"),
                            resultSet.getDate("dateConsultation"),
                            resultSet.getString("traitementPrescrit"),
                            resultSet.getString("description"),
                            resultSet.getInt("id_maladie"),
                            resultSet.getInt("id_patient"),
                            resultSet.getInt("id_rendez_vous"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom")
    
                    );
                    data.add(consultation);
    
                }
                table_idconsultation.setCellValueFactory(new PropertyValueFactory<>("id_consultation"));
                table_nomPatient.setCellValueFactory(new PropertyValueFactory<>("nom"));
                table_prenomPatient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                table_dateconsultation.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
                table_traitement.setCellValueFactory(new PropertyValueFactory<>("traitementPrescrit"));
                table__idmaladie.setCellValueFactory(new PropertyValueFactory<>("id_maladie"));
                table_idpatient.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
                table_idRDV.setCellValueFactory(new PropertyValueFactory<>("id_rendez_vous"));
                table_consultation.setItems(data);
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    
        public void DeleteConsultation() {
            Consultation selectedConsultation = table_consultation.getSelectionModel().getSelectedItem();
            if (selectedConsultation != null) {
                String deleteQuery = "DELETE FROM consultation WHERE id_consultation = ?";
                try (Connection conn = DataBase.connect_DB();
                     PreparedStatement statement = conn.prepareStatement(deleteQuery)) {
                    statement.setInt(1, selectedConsultation.getId_consultation());
    
                    int affectedRows = statement.executeUpdate();
                    if (affectedRows > 0) {
                        alertMessage.successMessage("Consultation deleted successfully!");
                        loadDataFromDatabase(); // Refresh the table view
                    } else {
                        alertMessage.errorMessage("Failed to delete the consultation.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    alertMessage.errorMessage("Database error: " + e.getMessage());
                }
            } else {
                alertMessage.errorMessage("No consultation selected for deletion.");
            }
        }
    
    
    
        public void DownloadConsultation() {
            Consultation selectedConsultation = table_consultation.getSelectionModel().getSelectedItem();
            if (selectedConsultation != null) {
                Document document = new Document();
                try {
                    PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("com/example/cabinet/image/Consultation de " +selectedConsultation.getNom()+".pdf"));
                    document.open();
    
                    Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                    Paragraph title = new Paragraph("Consultation Details", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
    
                    PdfContentByte canvas = writer.getDirectContent();
                    canvas.moveTo(document.left(), document.top());
                    canvas.lineTo(document.right(), document.top());
                    canvas.stroke();
    
                    Image logo = Image.getInstance("com/example/cabinet/image/logo.jpg");
                    logo.scaleToFit(document.getPageSize().getWidth(), 50); // Ajuster la largeur à la largeur de la page et la hauteur à 50
                    logo.setAbsolutePosition(0, document.top() - logo.getScaledHeight()); // Positionner en haut à gauche de la page
                    logo.setAlignment(Element.ALIGN_CENTER);
                    document.add(logo);
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
    
                    Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
                    Paragraph dentistInfo = new Paragraph("Dentist Information", sectionFont);
                    document.add(dentistInfo);
                    document.add(new Paragraph("Dentist Name: [MR Anas LAHOUB]"));
                    document.add(new Paragraph("Dentist TEL: [0682582461]"));
                    document.add(Chunk.NEWLINE);
    
                    Paragraph patientInfo = new Paragraph("Patient Information", sectionFont);
                    document.add(patientInfo);
                    document.add(new Paragraph("Patient Name: " + selectedConsultation.getNom() + " " + selectedConsultation.getPrenom()));
                    document.add(new Paragraph("Patient ID: " + selectedConsultation.getId_patient()));
                    document.add(Chunk.NEWLINE);
    
                    Paragraph consultationInfo = new Paragraph("Consultation Information", sectionFont);
                    document.add(consultationInfo);
                    document.add(new Paragraph("Consultation ID: " + selectedConsultation.getId_consultation()));
                    document.add(new Paragraph("Date: " + selectedConsultation.getDateConsultation()));
                    document.add(new Paragraph("Treatment: " + selectedConsultation.getTraitementPrescrit()));
                    document.add(new Paragraph("Description: " + selectedConsultation.getDescription()));
                    document.add(Chunk.NEWLINE);
    
                    Paragraph cabinetInfo = new Paragraph("Cabinet Information and Dentist Signature", sectionFont);
                    document.add(cabinetInfo);
                    document.add(new Paragraph("Cabinet Name: [Cabinet founti]"));
                    document.add(new Paragraph("Cabinet Address: [Adresse de votre cabinet]"));
                    document.add(new Paragraph("Cabinet Contact: [Contact de votre cabinet]"));
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);
    
                    Paragraph signature = new Paragraph("Signature", sectionFont);
                    document.add(signature);
                    document.add(new Paragraph("Dentist Signature: ____________________________"));
    
                    document.close();
                    alertMessage.successMessage("Consultation details downloaded successfully!");
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                    alertMessage.errorMessage("Error while downloading consultation details: " + e.getMessage());
                }
            } else {
                alertMessage.errorMessage("No consultation selected for downloading.");
            }
        }



        public void deconnecter() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pageLogin.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) deconnecter.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                alertMessage.errorMessage("Erreur lors du chargement de la page de login : " + e.getMessage());
            }
        }


        public void consultation() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dentiste.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) consultation.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                alertMessage.errorMessage("Erreur lors du chargement de la page de Consultation : " + e.getMessage());
            }
        }
    
    
    
    }
    
