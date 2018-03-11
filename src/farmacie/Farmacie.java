package farmacie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

public class Farmacie extends Application {
    
    //Clasa principila a aplicatiei. Start porneste fereastra de login
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.resizableProperty().setValue(Boolean.FALSE);
        
        Image icon = new Image(Farmacie.class.getResourceAsStream("icons8-menu-40.png"));
        stage.getIcons().add(icon);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
