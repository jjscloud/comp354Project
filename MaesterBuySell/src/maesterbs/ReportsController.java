/**
 * The class ReportsController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/

package maesterbs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ReportsController implements Initializable {

    // public ArrayList<Double> closingPrices;
    // public ArrayList<Double> shortTermMAs; //short term moving averages
    // public ArrayList<Double> longTermMAs; //long term moving averages
    // public ArrayList<Indicators> indicators; //indicators: buy, sell or none


    ArrayList<String> stocks;
    ArrayList<String> hDRanges;
    ArrayList<Integer> mARanges;

    //ObservableList<Integer> historicalRange = FXCollections.observableArrayList(5, 2, 1);
    //ObservableList<Integer> movingArverages = FXCollections.observableArrayList(20, 50, 100, 200);

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private ChoiceBox<String> historicalDataRangeBox;

    @FXML
    private ChoiceBox<Integer> movingAverageRangeBox;
    
    @FXML
    private JFXTextField userName;

    @FXML
    private ChoiceBox<String> stockChoice;

    @FXML
    private LineChart<?, ?> mainChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button calculate;


    @FXML
    private Button searchBtn;
    
    @FXML
    private Button homeBtn;

   
    @FXML
    void handleSearchBtn(ActionEvent event) {

    		//IMPLEMENT REPORTS
        
    }


    @FXML
    void handleHomeBtn(ActionEvent event) throws IOException {
    	if (event.getSource() == homeBtn) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("MainChartView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		}
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //GregorianCalendar end = new GregorianCalendar();
        DataCollector obj = new DataCollector();
        stocks = obj.getStocks();
        hDRanges = obj.getHistoricalDataRanges();
        mARanges = obj.getMovingAverageRanges();

        ObservableList<String> stock = FXCollections.observableArrayList(stocks);
        ObservableList<String> historicalDataRanges = FXCollections.observableArrayList(hDRanges);
        ObservableList<Integer> movingAverageRanges = FXCollections.observableArrayList(mARanges);

        stockChoice.setItems(stock);
        stockChoice.setValue(stocks.get(0));
        historicalDataRangeBox.setItems(historicalDataRanges);
        historicalDataRangeBox.setValue(hDRanges.get(0));
        movingAverageRangeBox.setItems(movingAverageRanges);
        movingAverageRangeBox.setValue(mARanges.get(0));

 

    }
}
