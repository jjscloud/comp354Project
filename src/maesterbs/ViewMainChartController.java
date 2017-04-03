/**
 * The class ViewMainChartController is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
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
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ViewMainChartController implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private LineChart<?, ?> mainChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

