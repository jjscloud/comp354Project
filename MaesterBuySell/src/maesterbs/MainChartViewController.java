/**
 * The class MainChartViewController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
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
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class MainChartViewController implements Initializable {

    XYChart.Series closingPrice;
    XYChart.Series shortTermMA; //short term moving averages
    XYChart.Series longTermMA; //long term moving averages
    XYChart.Series indicatorsBUY; //indicators: buy
    XYChart.Series indicatorsSELL; //indicators: sell
    XYChart.Series closingPriceMax;

    ArrayList<String> stocks;
    ArrayList<Integer> hDRanges;
    ArrayList<Integer> mARanges;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private ChoiceBox<Integer> historicalDataRangeBox;

    @FXML
    private ChoiceBox<Integer> movingAverageRangeBox;

    @FXML
    private ChoiceBox<String> stockChoice;

    @FXML
    private ScatterChart<?, ?> mainChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button calculate;

    @FXML
    private JFXDrawer drawer;

    @FXML
    void showMovingAverage(ActionEvent event) {


        GregorianCalendar end = new GregorianCalendar();
        DataCollector obj = new DataCollector();

        obj.UpdateData(stockChoice.getValue(), movingAverageRangeBox.getValue(),
                calculateStartDate(historicalDataRangeBox.getValue()), end);

        //clear old chart data
        mainChart.getData().clear();

        prepareToDrawCharts(obj);

        mainChart.getData().addAll(closingPrice, shortTermMA, longTermMA, indicatorsSELL, indicatorsBUY); //actually draw chart
    }

    private GregorianCalendar calculateStartDate(int HistoricalDataRange) {
        GregorianCalendar newDate = new GregorianCalendar();

        newDate.add(newDate.YEAR, -HistoricalDataRange);

        return newDate;
    }

    private void prepareToDrawCharts(DataCollector obj)
    {
        closingPrice = new XYChart.Series<>();
        shortTermMA = new XYChart.Series<>();
        longTermMA = new XYChart.Series<>();
        indicatorsBUY = new XYChart.Series<>();
        indicatorsSELL = new XYChart.Series<>();

        closingPrice.setName("Closing Prices");
        shortTermMA.setName("Moving Average-" + movingAverageRangeBox.getValue() + " days");
        longTermMA.setName("Moving Average- 300 days");
        indicatorsBUY.setName("BUY");
        indicatorsSELL.setName("SELL");

        for (int counter = 0; counter < obj.getClosingPrices().size(); counter++) {

            if (obj.getClosingPrices().get(counter) == -1) {
            } else {
                closingPrice.getData()
                        .add(new XYChart.Data(Integer.toString(counter), obj.getClosingPrices().get(counter)));
            }
            if (obj.getLongTermMAs().get(counter) == -1) {

            } else {
                longTermMA.getData()
                        .add(new XYChart.Data(Integer.toString(counter), obj.getLongTermMAs().get(counter)));
            }
            if (obj.getShortTermMAs().get(counter) == -1) {

            } else {
                shortTermMA.getData()
                        .add(new XYChart.Data(Integer.toString(counter), obj.getShortTermMAs().get(counter)));
            }
            if(obj.getIndicators().get(counter) == DataCollector.Indicators.SELL)
            {
                indicatorsSELL.getData().add(new XYChart.Data(Integer.toString(counter), obj.getMaxClosingPrice() - 0.2 *obj.getMaxClosingPrice()));
            }
            if(obj.getIndicators().get(counter) == DataCollector.Indicators.BUY)
            {
                indicatorsBUY.getData().add(new XYChart.Data(Integer.toString(counter), obj.getMaxClosingPrice() - 0.2 *obj.getMaxClosingPrice()));
            }
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
        ObservableList<Integer> historicalDataRanges = FXCollections.observableArrayList(hDRanges);
        ObservableList<Integer> movingAverageRanges = FXCollections.observableArrayList(mARanges);

        stockChoice.setItems(stock);
        stockChoice.setValue(stocks.get(0));
        historicalDataRangeBox.setItems(historicalDataRanges);
        historicalDataRangeBox.setValue(hDRanges.get(0));
        movingAverageRangeBox.setItems(movingAverageRanges);
        movingAverageRangeBox.setValue(mARanges.get(0));

        try {

            VBox box = FXMLLoader.load(getClass().getResource("SidePanel.fxml"));

            drawer.setSidePane(box);
            HamburgerBackArrowBasicTransition burger = new HamburgerBackArrowBasicTransition(hamburger);
            burger.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                burger.setRate(burger.getRate() * -1);
                burger.play();

                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}