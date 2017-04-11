/**
 * The class RegisterController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/
package maesterbs;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class RegisterController implements Initializable {

    @FXML
    private Button registerBtn;

    @FXML
    private Button cancelBtn;
    
    @FXML
    private JFXTextField newUser;
    
    @FXML
    private JFXPasswordField newpass;
    
    @FXML
    private JFXPasswordField confirmpass;
    
    @FXML
    private Label failedSignup;

    @FXML
    private void handleRegisterBtn(ActionEvent event) throws Exception {

        if (event.getSource() == registerBtn) {
        	// take new user name and pass
        	String userName = newUser.getText();
        	
        	String passw = newpass.getText();
        	
        	String passcon = confirmpass.getText();
        	// logger class
        	Logger logger = new Logger();
        	
        	if (logger.userExists(userName))
        	{
        		failedSignup.setText("Username not available. Please choose another.");
        	}
        	else if (!passw.equals(passcon))
        	{
        		failedSignup.setText("Please make sure passwords match.");
        	}
        	else
        	{
        		//register new name and password to db
        		logger.registerUser(userName, passw, "User");
        	
        		//return to login
        		Stage stage = new Stage();
        		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        		Scene scene = new Scene(root);
        		stage.setScene(scene);
        		stage.show();
        		((Node) (event.getSource())).getScene().getWindow().hide();
        	}

        }
    }

    @FXML
    private void handleCancelBtn(ActionEvent event) throws Exception {

        if (event.getSource() == cancelBtn) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}