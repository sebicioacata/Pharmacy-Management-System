package farmacie;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class MainWindowController implements Initializable {
    
    private String tableName;
    private String newQuery;
    private Connection con = dba.DBConnection.farmacieConnection();
    private PreparedStatement stmt = null;
    
    ObservableList<ObservableList> tableData = FXCollections.observableArrayList();
   
    //Elementele formularului
    @FXML
    private TextField text7;

    @FXML
    private Label label7;

    @FXML
    private TextField text6;

    @FXML
    private TextField text5;

    @FXML
    private Label label5;

    @FXML
    private TextField text4;

    @FXML
    private Label label6;

    @FXML
    private TextField text3;

    @FXML
    private Label label3;

    @FXML
    private TextField text2;

    @FXML
    private Label label4;

    @FXML
    private TextField text1;

    @FXML
    private Label label1;

    @FXML
    private Label label2;
    
    @FXML
    private Label warningLabel;

    @FXML
    private TableView<ObservableList> tableViewTables;

    @FXML
    private ListView<String> listViewTables;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        hideFields();
        
        listViewTables.getItems().addAll("Medicamente", "Categorii", "Clienti", "Comenzi", "Farmacisti", "Interogare simpla 1",
                                        "Interogare simpla 2", "Interogare simpla 3", "Interogare simpla 4", "Interogare simpla 5", "Interogare simpla 6",
                                         "Interogare complexa 1", "Interogare complexa 2", "Interogare complexa 3", "Interogare complexa 4");
        //Implementarea selectiei din lista
        listViewTables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                hideWarning();
                hideFields();
                tableName = newValue;
                showFields();
                updateTableView(getNewQuery());
            }
        });
    }
    
    @FXML
    void handleButtonAdauga(ActionEvent event) throws SQLException {
        System.out.println(text2.getText());
        System.out.println(categNumeToID(text2.getText().toLowerCase()));
        switch(tableName.toLowerCase()) {
            case "medicamente":
                 newQuery = "INSERT INTO " + tableName + " VALUES('" + categNumeToID(text2.getText().toLowerCase()) + "', '" +
                                                                       text1.getText() + "', '" +
                                                                       text3.getText() + "', '" +
                                                                       text4.getText() + "', '" +
                                                                       text5.getText() + "', '" +
                                                                       text6.getText() + "', '" +
                                                                       text7.getText() + "');";
                 break;
            case "clienti":
                newQuery = "INSERT INTO " + tableName + " VALUES('" + text1.getText() + "', '" +
                                                                      text2.getText() + "', '" +
                                                                      text3.getText() + "');";
                break;
        }
      
        stmt = con.prepareStatement(newQuery);
        stmt.execute();
        
        resetFields();
        updateTableView(getNewQuery());
    }
    
    @FXML
    void handleButtonModifica(ActionEvent event) throws SQLException {
        
        String SET = null;
        switch(tableName.toLowerCase()) {
            case "medicamente":
                 if (!text1.getText().isEmpty()) {
                    SET = " SET Denumire = '" + text1.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Denumire='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(0).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text2.getText().isEmpty()) {
                    setWarning();
                }
                if (!text3.getText().isEmpty()) {
                    SET = " SET Compus = '" + text3.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Compus='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(2).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text4.getText().isEmpty()) {
                    SET = " SET Detalii = '" + text4.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Detalii LIKE '" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(3).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text5.getText().isEmpty()) {
                    SET = " SET Stoc = '" + text5.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Stoc='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(4).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text6.getText().isEmpty()) {
                    SET = " SET Pret = '" + text6.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Pret='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(5).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text7.getText().isEmpty()) {
                    SET = " SET Reteta = '" + text7.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Reteta='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(6).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                 break;
            case "clienti":
                if (!text1.getText().isEmpty()) {
                    SET = " SET Nume = '" + text1.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Nume='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(0).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text2.getText().isEmpty()) {
                    SET = " SET Prenume = '" + text2.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Prenume='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(1).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                if (!text3.getText().isEmpty()) {
                    SET = " SET Telefon = '" + text3.getText() + "'";
                    newQuery = "UPDATE " + tableName + SET + " WHERE Telefon='" + 
                                                                 tableViewTables.getSelectionModel().getSelectedItem().get(2).toString() +
                                                                 "';";
                    stmt = con.prepareStatement(newQuery);
                    stmt.execute();
                }
                break;
        }
        
        //resetFields();
        updateTableView(getNewQuery());
    }

    @FXML
    void handleButtonSterge(ActionEvent event) throws SQLException {
        
        switch(tableName.toLowerCase()) {
            case "medicamente":
                newQuery = "DELETE FROM " + tableName + " WHERE Denumire='" + 
                                                          tableViewTables.getSelectionModel().getSelectedItem().get(0).toString() +
                                                          "' AND Compus='" +
                                                          tableViewTables.getSelectionModel().getSelectedItem().get(2).toString() +
                                                          "';";
                 break;
            case "clienti":
                newQuery = "DELETE FROM " + tableName + " WHERE Nume='" + 
                                                          tableViewTables.getSelectionModel().getSelectedItem().get(0).toString() +
                                                          "' AND Prenume='" +
                                                          tableViewTables.getSelectionModel().getSelectedItem().get(1).toString() +
                                                          "' AND Telefon='" +
                                                          tableViewTables.getSelectionModel().getSelectedItem().get(2).toString() +
                                                          "';";
                break;
        }
        stmt = con.prepareStatement(newQuery);
        stmt.execute();
        
        resetFields();
        updateTableView(getNewQuery());
    }
    
    private void hideFields() {
        
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        
        text1.setVisible(false);
        text2.setVisible(false);
        text3.setVisible(false);
        text4.setVisible(false);
        text5.setVisible(false);
        text6.setVisible(false);
        text7.setVisible(false);
        
        resetFields();
    }
    
    private void resetFields() {
        text1.clear();
        text2.clear();
        text3.clear();
        text4.clear();
        text5.clear();
        text6.clear();
        text7.clear();
    }
    
    private void showFields() {
        switch (tableName.toLowerCase()) {
            case "medicamente": 
                label1.setVisible(true);
                label2.setVisible(true);
                label3.setVisible(true);
                label4.setVisible(true);
                label5.setVisible(true);
                label6.setVisible(true);
                label7.setVisible(true);
                
                label1.setText("Denumire");
                label2.setText("Categorie");
                label3.setText("Compus");
                label4.setText("Detalii");
                label5.setText("Stoc");
                label6.setText("Pret");
                label7.setText("Reteta");
                
                text1.setVisible(true);
                text2.setVisible(true);
                text3.setVisible(true);
                text4.setVisible(true);
                text5.setVisible(true);
                text6.setVisible(true);
                text7.setVisible(true);
                break;
            case "categorii": 
                break;
            case "clienti": 
                label1.setVisible(true);
                label2.setVisible(true);
                label3.setVisible(true);
                
                label1.setText("Nume");
                label2.setText("Prenume");
                label3.setText("Telefon");
               
                text1.setVisible(true);
                text2.setVisible(true);
                text3.setVisible(true);
                break;
            case "comenzi": 
                hideFields();
                break;
            case "farmacisti": 
                hideFields();
                break;
            case "interogare complexa 4": 
                label1.setVisible(true);
                label1.setText("Alege categoria");
                text1.setVisible(true);
                break;
            case "interogare simpla 5": 
                label1.setVisible(true);
                label1.setText("Alege medicamentul");
                text1.setVisible(true);
                break;
        };
        
    }
    
    private String getNewQuery() {
        switch (tableName.toLowerCase()) {
            case "medicamente": 
                newQuery = "SELECT Denumire, Categorie, Compus, Detalii, Stoc, Pret, Reteta FROM Medicamente INNER JOIN Categorii ON Medicamente.CategorieID = Categorii.CategorieID";
                break;
            case "categorii": 
                newQuery = "SELECT Categorie FROM Categorii";
                break;
            case "clienti": 
                newQuery = "SELECT Nume, Prenume, Telefon FROM Clienti";
                break;
            case "comenzi": 
                newQuery = "SELECT ComandaID AS 'Numar Comanda', FORMAT(DataComanda,'dd/MM/yyyy') AS 'Data Comenzii' FROM Comenzi";
                break;
            case "farmacisti": 
                newQuery = "SELECT Nume, Prenume FROM Farmacisti";
                break;
            case "interogare simpla 1": 
                //coincide cu tabela medicamente
                newQuery = "SELECT Denumire, Categorie, Compus, Detalii, Stoc, Pret, Reteta FROM Medicamente INNER JOIN Categorii ON Medicamente.CategorieID = Categorii.CategorieID";
        
                break;
            case "interogare simpla 2": 
                newQuery = "SELECT Cl.Nume + ' ' + Cl.Prenume as 'Client', M.Denumire as 'Denumire Medicament', MC.Cantitate, " + 
                    "FORMAT(C.DataComanda,'dd/MM/yyyy') as 'Data Comenzii' FROM Medicamente M INNER JOIN MedicamenteComenzi MC " + 
                    "ON M.MedicamentID = MC.MedicamentID INNER JOIN Comenzi C ON C.ComandaID = MC.ComandaID INNER JOIN Clienti Cl ON" + 
                    " Cl.ClientID = C.ComandaID";
                break;
            case "interogare simpla 3": 
                newQuery = "SELECT Cl.Nume, M.Denumire, SUM(MC.Cantitate)\n" +
                        "FROM Clienti Cl INNER JOIN Comenzi C\n" +
                        "ON Cl.ClientID = C.ClientID\n" +
                        "INNER JOIN MedicamenteComenzi MC\n" +
                        "ON MC.ComandaID	= C.ComandaID\n" +
                        "INNER JOIN Medicamente M \n" +
                        "ON M.MedicamentID = MC.MedicamentID\n" +
                        "GROUP BY Cl.Nume, M.Denumire\n" +
                        "ORDER BY Cl.Nume"; 
                break;
            case "interogare simpla 4": 
            newQuery = "SELECT Cat.Categorie, COUNT(M.MedicamentID)\n" +
                        "FROM Medicamente M \n" +
                        "INNER JOIN Categorii Cat\n" +
                        "ON Cat.CategorieID = M.CategorieID\n" +
                        "GROUP BY Cat.Categorie";            
            break;
            case "interogare simpla 5": 
            newQuery = "SELECT F.Nume + ' ' + F.Prenume as 'Nume Farmacist', M.Denumire as 'Medicament', SUM(MC.Cantitate) as 'Cantitate Vanduta'\n" +
                        "FROM MedicamenteComenzi MC\n" +
                        "INNER JOIN Comenzi C\n" +
                        "ON MC.ComandaID = C.ComandaID\n" +
                        "INNER JOIN Farmacisti F\n" +
                        "ON F.FarmacistID = C.FarmacistID\n" +
                        "INNER JOIN Medicamente M\n" +
                        "ON MC.MedicamentID = M.MedicamentID\n" +
                        "WHERE F.FarmacistID = 2 AND M.Denumire = '" + text1.getText() +"'" +
                        "GROUP BY F.Nume, F.Prenume, M.Denumire";            
            break;
            case "interogare simpla 6": 
            newQuery = "SELECT COUNT(C.ComandaID) AS 'Numar Comenzi'\n" +
                        "FROM Comenzi C\n" +
                        "INNER JOIN Farmacisti F\n" +
                        "ON F.FarmacistID = C.FarmacistID\n" +
                        "WHERE F.FarmacistID = 2 AND YEAR(C.DataComanda) = 2017;";            
            break;
            case "interogare complexa 1": 
            newQuery = "SELECT Cl.Nume FROM Clienti Cl WHERE Cl.ClientID IN (SELECT C.ClientID" +
"						FROM Comenzi C WHERE C.FarmacistID = 1)";            
            break;
            case "interogare complexa 2": 
            newQuery = "SELECT MAX(MC.Cantitate)\n AS 'Cantitate Maxima'" +
                        "FROM MedicamenteComenzi MC \n" +
                        "INNER JOIN Comenzi C on MC.ComandaID = C.ComandaID\n" +
                        "WHERE C.FarmacistID = 2 AND DAY(C.DataComanda) IN (SELECT DAY(C.DataComanda)"
                    + " FROM Comenzi C GROUP BY DAY(C.DataComanda)" + " HAVING DAY(C.DataComanda) = 13)";          
            break;
            case "interogare complexa 3": 
            newQuery = "SELECT Cl.Nume, C.DataComanda\n" +
                        "FROM Clienti Cl INNER JOIN Comenzi C ON Cl.ClientID = c.ClientID\n" +
                        "WHERE C.DataComanda in (SELECT MIN(C.DataComanda)"
                    + "FROM Comenzi C)";            
            break;
            case "interogare complexa 4":
                newQuery = "SELECT DISTINCT Cl.Nume, Cl.Prenume, Cl.Telefon from Clienti Cl \n" +
                        "INNER JOIN Comenzi C on C.ClientID = Cl.ClientID\n" +
                        "INNER JOIN MedicamenteComenzi MC on mc.ComandaID = c.ComandaID\n" +
                        "WHERE MONTH(C.DataComanda) in (SELECT MONTH(C.DataComanda)\n" +
                        "FROM Comenzi C\n" +
                        "GROUP BY MONTH(C.DataComanda)\n" +
                        "HAVING MONTH(C.DataComanda) = 3 or MONTH(C.DataComanda) = 4 or MONTH(C.DataComanda) = 5)\n" +
                        "\n" +
                        "AND MC.MedicamentID in (SELECT M.MedicamentID from Medicamente M\n" +
                        "WHERE M.CategorieID = " + categNumeToID(text1.getText()) + ")";  
                break;
                
        };
        return newQuery;
    }
    
    private void updateTableView(String newQuery) {
               
        clearTableView();
        buildData(con, newQuery);
    }
    
    private void clearTableView() {
        
            tableViewTables.getItems().clear();
            tableViewTables.getColumns().clear();
    }
   
    public void buildData(Connection con, String newQuery){
          ObservableList<ObservableList> data = FXCollections.observableArrayList();
          try{
            stmt = con.prepareStatement(newQuery);
            ResultSet rs = stmt.executeQuery();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
               
                tableViewTables.getColumns().addAll(col); 
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                
                data.add(row);
                
                tableData = data;
            }

            tableViewTables.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
          
          
          
      }

    private int categNumeToID(String nume) {
        int id = 0;
        switch(nume.toLowerCase()) {
            case "antidepresive":
                id = 1;
                break;
            case "antihistamine": 
                id = 2;
                break;
            case "antibiotice":
                id = 3;
                break;
            case "colesterol":
                id = 4;
                break;
            case "dermatologice": 
                id = 5;
                break;
            case "diabet":
                id = 6;
                break;
            case "migrene":
                id = 7;
                break;
        }
        return id;
    }

    private void setWarning() {
        warningLabel.setStyle("-fx-text-inner-color: red;");
        warningLabel.setText("Nu se poate modifica\nStergeti produsul si introduceti din nou");
        warningLabel.setVisible(true);
    }
    
    private void hideWarning() {
        warningLabel.setVisible(false);
    }
}
    
