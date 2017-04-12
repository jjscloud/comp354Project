/**
 * The class LoginController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 * 
 * Admin Test account credentials:
 * -user name : testAdmin
 * -password: adminpass
 *
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/


package maesterbs;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Inn4o
 */
public class LoginController implements Initializable {

    private static boolean rpt;

    public boolean getRpt() {
        return rpt;
    }

    public void setRpt(boolean rpt) {
        this.rpt = rpt;
    }

	@FXML
	private Label failedLogin;
	
    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton signup; 
    
    @FXML
    private void makeLogin(ActionEvent event) throws Exception{
               
        if(event.getSource() == login) {
        	verify();
        }
        
    }
    @FXML
    private void signUp(ActionEvent event) throws Exception{
    	if (event.getSource() == signup) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
      }
    	
    }
    	
    public void verify() throws IOException{
    	String userName = user.getText();
    	String pwrd = password.getText();
    	
    	Logger logger = new Logger();
    	
    	//validate username and pass given
    	boolean valid = logger.validateUser(userName, pwrd);
    	//check if user is admin
    	boolean priv = logger.isAdmin(userName);
    	
    	CurrentAccount current = new CurrentAccount();
    	
    	// if user is admin
        if (valid && priv)
        {
        	
        	current.setCurrentName(userName);
        	
            rpt=true;
            setRpt(rpt);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainChartView.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("MainChartView.css").toExternalForm());
	    
            stage.setScene(scene);
            stage.show();
            login.getScene().getWindow().hide();
        }
        else if (valid) // if normal user
        {
        	current.setCurrentName(userName);
        	
            rpt=false;
            setRpt(rpt);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainChartView.fxml"));
            Scene scene = new Scene(root);
	scene.getStylesheets().add(getClass().getResource("MainChartView.css").toExternalForm());    
	    
            stage.setScene(scene);
            stage.show();
            login.getScene().getWindow().hide();
        }
    	else {
    		 failedLogin.setText("Username or Password invalid!");
    	}
    }
    public void closeLogin(){
    	
    }
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
       user.setOnKeyPressed(e ->{
			if (e.getCode()==KeyCode.ENTER){
				try {
					verify();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		password.setOnKeyPressed(e ->{
			if (e.getCode()==KeyCode.ENTER){
				try {
					verify();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
    }    
    
}
