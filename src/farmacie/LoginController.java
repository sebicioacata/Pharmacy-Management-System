package farmacie;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    
    //Definirea obiectelor FXML
    @FXML
    private Button ContNou;
    @FXML
    private PasswordField textPassword;
    @FXML
    private TextField textUser;
    @FXML
    private Label labelError1;
    @FXML
    private Label labelSucces;
    
    //Variabila ce retine conexiunea cu baza de date
    private Connection con = null;
    private PreparedStatement stmt = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Apel catre clasa ce porneste conexiunea
        con = dba.DBConnection.farmacieConnection();
        labelError1.setVisible(false);
        labelSucces.setVisible(false);
    }    

    @FXML
    void handleButtonLogin(ActionEvent event) throws Exception {
        //Construiste un nou 'stage' pentru a incarca fereastra principala
        Stage stage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        //Elimina butonul de fullscreen
        stage.resizableProperty().setValue(Boolean.FALSE);
        
        //Schimba icon-ul aplicatiei
        Image icon = new Image(Farmacie.class.getResourceAsStream("icons8-menu-40.png"));
        stage.getIcons().add(icon);
        
        //Lanseaza un nou 'scene' noua pentru 'stage'-ul creat
        stage.setScene(new Scene(root));
        stage.show();
        
        //Inchide fereastra de login
        final Node source = (Node) event.getSource();
        final Stage stageSource = (Stage) source.getScene().getWindow();
        stageSource.close();
    }

    @FXML
    void handleButtonContNou(ActionEvent event) throws SQLException, InterruptedException  {
        
        String newQuery = "insert into Utilizatori values(?, ?)";
        
        if (textUser.getText().length() > 8 || textPassword.getText().length() > 8) {
            textUser.clear();
            textPassword.clear();
            labelSucces.setVisible(false);
            labelError1.setVisible(true); 
        }
        
        else {
            labelError1.setVisible(false);
            
            try {
                stmt = con.prepareStatement(newQuery);
                stmt.setString(1, textUser.getText());
                stmt.setString(2, textPassword.getText());

                int verifyUpdate = stmt.executeUpdate();
                
                if(verifyUpdate == 1) labelSucces.setVisible(true); 
               
            } catch (SQLException ex) {};
    
        }
        
        
        stmt.close();
    }
    
}
