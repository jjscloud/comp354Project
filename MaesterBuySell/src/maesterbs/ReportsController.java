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

import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.property.SimpleStringProperty;

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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    // table objects
    @FXML
    private TableView<TableRow> tableReport;
    
    @FXML
    private TableColumn<TableRow, String> userCol;
    
    @FXML
    private TableColumn<TableRow, String> entryCol;
    
    @FXML
    private TableColumn<TableRow, String> stockCol;
    
    @FXML
    private TableColumn<TableRow, String> maCol;
    
    @FXML
    private TableColumn<TableRow, String> hdCol;

    // check boxes
    @FXML
    private JFXCheckBox anyStock;
    
    @FXML
    private JFXCheckBox anyMA;
    
    @FXML
    private JFXCheckBox anyHD;
    
   //data is stored in this
    ObservableList<TableRow> data = FXCollections.observableArrayList();
    
    @FXML
    void handleSearchBtn(ActionEvent event) {

    		//IMPLEMENT REPORTS
    	if (event.getSource() == searchBtn)
    	{
    		
    		
    		Logger logger = new Logger();
    		
    		String userSearch = userName.getText();
    		
    		// set as wildcard if no user name typed, returns everything
    		if (userSearch.equals(""))
    			userSearch = "%";
    			
    		String stockSearch = stockChoice.getValue();
    		String maSearch = movingAverageRangeBox.getValue().toString();
    		String hdSearch = historicalDataRangeBox.getValue();
    		
    		//set as wildcard if checked
    		if (anyStock.isSelected())
    			stockSearch = "%";
    		
    		if (anyMA.isSelected())
    			maSearch = "%";
    		
    		if (anyHD.isSelected())
    			hdSearch = "%";
    		
    		//fetch report data
    		ArrayList<String> searchResults = logger.getReport(userSearch, stockSearch, maSearch, hdSearch);

    		// get number of rows
    		int numberOfRows = searchResults.size() / 5;
    		
    		if (numberOfRows != 0)
    		{
    			//create new table data
    			data = FXCollections.observableArrayList();
    			
    			for (int i = 0; i < searchResults.size(); i = i + 5)
    			{
    				//add each row from search results
    				data.add(new TableRow(searchResults.get(i), searchResults.get(i + 1), 
    						searchResults.get(i+2), searchResults.get(i+3), searchResults.get(i+4)));
    			}
    			
    			//set table data
    			tableReport.setItems(data);
    		}
		else
    		{
    			data = FXCollections.observableArrayList();
    			
    			//set table data
    			tableReport.setItems(data);
    		}
    		
    	}
    }


    @FXML
    void handleHomeBtn(ActionEvent event) throws IOException {
    	if (event.getSource() == homeBtn) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("MainChartView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("MainChartView.css").toExternalForm());

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

        tableReport.setEditable(true);

        userCol.setCellValueFactory(new PropertyValueFactory<TableRow, String>("username"));
        entryCol.setCellValueFactory(new PropertyValueFactory<TableRow, String>("entryDate"));
        stockCol.setCellValueFactory(new PropertyValueFactory<TableRow, String>("stockRow"));
        maCol.setCellValueFactory(new PropertyValueFactory<TableRow, String>("maRow"));
        hdCol.setCellValueFactory(new PropertyValueFactory<TableRow, String>("hdRow"));
        
        
        
    }
    
    // class used to represent a row in the table
    public class TableRow
    {
    	private SimpleStringProperty username;
    	private SimpleStringProperty entryDate;
    	private SimpleStringProperty stockRow;
    	private SimpleStringProperty maRow;
    	private SimpleStringProperty hdRow;
    	
    	private TableRow(String un, String ed, String sr, String mr, String hr)
    	{
    		this.username = new SimpleStringProperty(un);
    		this.entryDate = new SimpleStringProperty(ed);
    		this.stockRow = new SimpleStringProperty(sr);
    		this.maRow = new SimpleStringProperty(mr);
    		this.hdRow = new SimpleStringProperty(hr);
    	}
    	
    	public String getUsername()
    	{
    		return username.get();
    	}
    	
    	public String getEntryDate()
    	{
    		return entryDate.get();
    	}
    	
    	public String getStockRow()
    	{
    		return stockRow.get();
    	}
    	
    	public String getMaRow()
    	{
    		return maRow.get();
    	}
    	
    	public String getHdRow()
    	{
    		return hdRow.get();
    	}
    	
    	public void setUsername(String usern)
    	{
    		username.set(usern);
    	}
    	
    	public void setEntryDate(String ndate)
    	{
    		entryDate.set(ndate);
    	}
    	
    	public void setStockRow(String nstock)
    	{
    		stockRow.set(nstock);
    	}
    	
    	public void setMaRow(String nMA)
    	{
    		maRow.set(nMA);
    	}
    	
    	public void setHdRow(String nHD)
    	{
    		hdRow.set(nHD);
    	}
    }
}
