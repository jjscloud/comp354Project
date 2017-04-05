/**
 * The class ChangePasswordController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/

package maesterbs;

        import java.net.URL;
        import java.util.ResourceBundle;

        import com.jfoenix.controls.JFXButton;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class ChangePasswordController implements Initializable {

    @FXML
    private JFXButton saveBtn;

    @FXML
    private void handleSaveBtn(ActionEvent event) throws Exception {

        if (event.getSource() == saveBtn) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainChartView.fxml"));
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