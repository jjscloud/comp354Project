/**
 * The class MainController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/


package maesterbs;

import javafx.event.ActionEvent;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MainController {
	

    @FXML
    private ChoiceBox<?> chooseStock;

    @FXML
    private ChoiceBox<?> chooseMA;

    @FXML
    private ChoiceBox<?> chooseHD;

    @FXML
    private JFXButton myProfileBtn;

    @FXML
    private JFXButton signOutBtn;

    @FXML
    void handleMyProfileBtn(ActionEvent event) throws Exception {
    	if(event.getSource() == myProfileBtn) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("myProfile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
        }
         
    }

    @FXML
    void handleSignOutBtn(ActionEvent event) throws Exception {
    	if(event.getSource() == signOutBtn) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
        }

    }


}
