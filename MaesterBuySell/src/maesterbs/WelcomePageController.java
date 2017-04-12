package maesterbs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomePageController implements Initializable {
	  @FXML
	    private AnchorPane root;

	    @FXML
	    private Pane first;

	    @FXML
	    private Pane second;

	    @FXML
	    private Pane third;

	    @FXML
	    private Pane fourth;

	    @FXML
	    private Pane fifth;

	    @FXML
	    private Pane sixth;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.getChildren().setAll(first);
		FadeTransition one = new FadeTransition(Duration.seconds(3), second);
		one.setFromValue(0);
		one.setToValue(1);
		one.setCycleCount(1);

		FadeTransition two = new FadeTransition(Duration.seconds(1.5), third);
		two.setFromValue(0);
		two.setToValue(1);
		two.setCycleCount(1);

		FadeTransition three = new FadeTransition(Duration.seconds(3), fourth);
		three.setFromValue(0);
		three.setToValue(1);
		three.setCycleCount(1);

		FadeTransition four = new FadeTransition(Duration.seconds(1.5), fifth);
		four.setFromValue(0);
		four.setToValue(1);
		four.setCycleCount(1);
		
		FadeTransition lastOne = new FadeTransition(Duration.seconds(3), sixth);
		lastOne.setFromValue(0);
		lastOne.setToValue(1);
		lastOne.setCycleCount(1);
		one.play();
		two.play();
		three.play();
		four.play();
		lastOne.play();
		root.getChildren().add(second);
		root.getChildren().add(third);
		root.getChildren().add(fourth);
		root.getChildren().add(fifth);
		root.getChildren().add(sixth);
		
		FadeTransition all2 = new FadeTransition(Duration.seconds(3),first);
		all2.setFromValue(1);
		all2.setToValue(0);
		all2.setCycleCount(1);
		FadeTransition all3 = new FadeTransition(Duration.seconds(3),second);
		all3.setFromValue(1);
		all3.setToValue(0);
		all3.setCycleCount(1);
		FadeTransition all4 = new FadeTransition(Duration.seconds(3),third);
		all4.setFromValue(1);
		all4.setToValue(0);
		all4.setCycleCount(1);
		FadeTransition all5 = new FadeTransition(Duration.seconds(3),fourth);
		all5.setFromValue(1);
		all5.setToValue(0);
		all5.setCycleCount(1);
		FadeTransition all6 = new FadeTransition(Duration.seconds(3),fifth);
		all6.setFromValue(1);
		all6.setToValue(0);
		all6.setCycleCount(1);
		FadeTransition all7 = new FadeTransition(Duration.seconds(3),sixth);
		all7.setFromValue(1);
		all7.setToValue(0);
		all7.setCycleCount(1);
		
		
		one.setOnFinished(e->{
	
			all2.play();
			all3.play();
			all4.play(); 
			all5.play(); 
			all6.play(); 
			all7.play();
		
		});

		all7.setOnFinished(e->{
			Stage stage = new Stage();
	        Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	        sixth.getScene().getWindow().hide();
		});
		
	}
	
}
