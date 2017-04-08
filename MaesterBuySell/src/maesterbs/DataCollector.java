/**
 * The class DataCollector is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 * DataCollector builds a url based on user provided criteria to create a url, then uses that url to obtain information
 * about a stock on Yahoo Finance. That data is then saved as an array and is used to calculate the moving average,
 * and can be used by the Chart Generator (to be generated later).
 *
 * For: COMP 354 project (second deliverable), April 12, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/

package maesterbs;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class DataCollector {

    private class DefaultSettings
    {
        private ArrayList<String> componentsOfDow;
        private ArrayList<String> hDRanges;
        private ArrayList<Integer> mARanges;

        private DefaultSettings()
        {
            AddDowComponentSymbols();
            AddHDRanges();
            AddMARanges();
        }

        private void AddDowComponentSymbols()
        {
            componentsOfDow = new ArrayList<String>();

            componentsOfDow.add("MMM"); // 1
            componentsOfDow.add("HD"); // 2
            componentsOfDow.add("JNJ"); // 3
            componentsOfDow.add("AXP"); // 4
            componentsOfDow.add("MRK"); // 5
            componentsOfDow.add("V"); // 6
            componentsOfDow.add("IBM"); // 7
            componentsOfDow.add("CSCO"); // 8
            componentsOfDow.add("DIS"); // 9
            componentsOfDow.add("AAPL"); // 10
            componentsOfDow.add("UTX"); // 11
            componentsOfDow.add("MCD"); // 12
            componentsOfDow.add("MSFT"); // 13
            componentsOfDow.add("GE"); // 14
            componentsOfDow.add("KO"); // 15
            componentsOfDow.add("CVX"); // 16
            componentsOfDow.add("PG"); // 17
            componentsOfDow.add("TRV"); // 18
            componentsOfDow.add("PFE"); // 19
            componentsOfDow.add("CAT"); // 20
            componentsOfDow.add("NKE"); // 21
            componentsOfDow.add("UNH"); // 22
            componentsOfDow.add("BA"); // 23
            componentsOfDow.add("VZ"); // 24
            componentsOfDow.add("GS"); // 25
            componentsOfDow.add("WMT"); // 26
            componentsOfDow.add("INTC"); // 27
            componentsOfDow.add("JPM"); // 28
            componentsOfDow.add("DD"); // 29
            componentsOfDow.add("XOM"); // 30
        }

        private void AddMARanges()
        {
            mARanges = new ArrayList<Integer>();
            mARanges.add(20);
            mARanges.add(50);
            mARanges.add(100);
            mARanges.add(200);
        }

        private void  AddHDRanges()
        {
            hDRanges = new ArrayList<String>();
            hDRanges.add("1");
            hDRanges.add("2");
            hDRanges.add("5");
            hDRanges.add("All Data");
        }
    }
    //Private Inner class StockDataDownloader
    private class StockDataDownloader {

        private int dateIndex = 0;
        //private int OPEN=1; // unused, but useful to know
        //private int HIGH=2; // unused, but useful to know
        //private int LOW=3; // unused, but useful to know
        private int closeIndex = 4;
        //private int VOLUME=5; // unused, but useful to know
        private int adjcloseIndex = 6;

        private ArrayList<GregorianCalendar> dates;
        private ArrayList<Double> closes;
        private ArrayList<Double> adjcloses;

        private String urlString;

        private  StockDataDownloader (String symbol, GregorianCalendar start, GregorianCalendar end)
        {
            dates=new ArrayList<GregorianCalendar>();
            closes = new ArrayList<Double> ();
            adjcloses= new ArrayList<Double> ();

            //= http://chart.finance.yahoo.com/table.csv?s=IBM&a=1&b=13&c=2017&d=2&e=13&f=2017&g=d&ignore=.csv

            urlString="http://chart.finance.yahoo.com/table.csv?s="
                    +symbol+
                    "&a="+start.get(Calendar.MONTH)+
                    "&b="+start.get(Calendar.DAY_OF_MONTH)+
                    "&c="+start.get(Calendar.YEAR)+
                    "&d="+end.get(Calendar.MONTH)+
                    "&e="+end.get(Calendar.DAY_OF_MONTH)+
                    "&f="+end.get(Calendar.YEAR)+
                    "&g=d&ignore=.csv";

            try
            {
                URL yahooFinance= new URL(urlString);
                URLConnection data= yahooFinance.openConnection();
                Scanner input= new Scanner(data.getInputStream());
                if (input.hasNext())//skip first line of the csv file
                    input.nextLine();

                //start reading data
                while(input.hasNextLine())
                {
                    String line=input.nextLine();

                    // splitting values in each CSV line. Each value is separated by a comma
                    String[] numbers = line.split(",");

                    //System.out.println("currentLine: " + " " + line); // useful check

                    // need to parse it because we read the lines as string

                    //Dates
                    int dateYear = Integer.parseInt(numbers[dateIndex].substring(0,4));
                    int dateMonth = Integer.parseInt(numbers[dateIndex].substring(5,7));
                    int dateDay = Integer.parseInt(numbers[dateIndex].substring(8,10));
                    GregorianCalendar newDate = new GregorianCalendar(dateYear, dateMonth, dateDay);
                    dates.add(newDate);

                    // creating an array list with all the closing prices in it
                    closes.add(Double.parseDouble(numbers[closeIndex]));

                    // creating an array list with all the adjusted closing prices in it
                    adjcloses.add(Double.parseDouble(numbers[adjcloseIndex]));
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }

    }
    // End of StockDataDownloader


    public enum Indicators{BUY, SELL, NONE}
    public int LONG_TERM_MOVING_AVERAGE_RANGE = 300;

    private ArrayList<Double> closingPrices;
    private ArrayList<Double> shortTermMAs; //short term moving averages
    private ArrayList<Double> longTermMAs; //long term moving averages
    private ArrayList<Indicators> indicators; //indicators: buy, sell or none
    private ArrayList<GregorianCalendar> dates;
    private ArrayList<String> stockComponentsOfDow;
    private ArrayList<String> historicalDataRanges;
    private ArrayList<Integer> movingAverageRanges;

    private double maxClosingPrice;

    public DataCollector()
    {
        closingPrices = null;
        shortTermMAs = null;
        longTermMAs = null;
        indicators = null;
        dates = null;

        stockComponentsOfDow = null;
        historicalDataRanges = null;
        movingAverageRanges = null;

        maxClosingPrice = 0;

        DefaultSettings ds = new DefaultSettings();
        stockComponentsOfDow = ds.componentsOfDow;
        historicalDataRanges = ds.hDRanges;
        movingAverageRanges = ds.mARanges;

    }

    public ArrayList<Double> getClosingPrices(){return closingPrices;}
    public ArrayList<Double> getShortTermMAs(){return shortTermMAs;}
    public ArrayList<Double> getLongTermMAs(){return longTermMAs;}
    public ArrayList<Indicators> getIndicators(){return indicators;}
    public ArrayList<GregorianCalendar> getDates(){return dates;}
    public ArrayList<String> getStocks(){return stockComponentsOfDow;}
    public ArrayList<String> getHistoricalDataRanges(){return historicalDataRanges;}
    public ArrayList<Integer> getMovingAverageRanges(){return movingAverageRanges;}
    public double getMaxClosingPrice(){return maxClosingPrice;}


    public void UpdateData(String symbol, int rangeMA, int rangeHD)
    {
        //when update is called, clear past data
        closingPrices = new ArrayList<Double>();
        maxClosingPrice = 0;

        //calculate dates
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar startDay = calculateStartDate(rangeHD);

        //access stock information
        StockDataDownloader currentStockData= new StockDataDownloader(symbol, startDay, today);

        currentStockData.closes.trimToSize(); // trimming the array list of closing prices
        currentStockData.dates.trimToSize();

        //updating closingPrices and dates
        closingPrices = currentStockData.closes;
        dates = currentStockData.dates;

        //updating Short and Long Term Moving Averages
        UpdateMAs(rangeMA);

        //trim the closing prices and MAs and dates
        trimArrays();

        //updating indicators
        UpdateIndicators();

    }// end of Update

    // internal method to update indicators
    private void UpdateMAs(int rangeMA)
    {
        shortTermMAs = new ArrayList<Double>();
        longTermMAs = new ArrayList<Double>();
        double shortTermMASum = 0;
        double longTermMASum = 0;

        // calculating the short term (within a user-defined window) and long term moving average (fixed), as well as
        // updating the maximum closing price
        for (int counter = 0, j=0, i=0; counter <  closingPrices.size(); counter++){

            double currentClosePrice = closingPrices.get(counter);

            //updating the maximum closing price
            if (currentClosePrice > maxClosingPrice)
            {
                maxClosingPrice = currentClosePrice;
            }

            longTermMASum =  currentClosePrice + longTermMASum;
            shortTermMASum = currentClosePrice + shortTermMASum;

            if (counter < (rangeMA-1))//using rangeMA-1 because the counter starts at 0
            {
                shortTermMAs.add(-1.0); // add -1.0 to indicate an empty value
                longTermMAs.add(-1.0);
            }
            else // counter >= to rangeMA-1
            {
                if(counter == (rangeMA-1))
                {
                    shortTermMAs.add( shortTermMASum / rangeMA );
                    longTermMAs.add(-1.0);
                }
                if (counter > (rangeMA-1))
                {
                    shortTermMASum = shortTermMASum - closingPrices.get(j); //removing the oldest closing price
                    j++;
                    shortTermMAs.add( shortTermMASum / rangeMA );

                    if (counter < (LONG_TERM_MOVING_AVERAGE_RANGE-1))
                    {
                        longTermMAs.add(-1.0);
                    }
                    else// counter >= to LONG_TERM_MOVING_AVERAGE_RANGE-1
                    {
                        if (counter == (LONG_TERM_MOVING_AVERAGE_RANGE-1))
                        {
                            longTermMAs.add( longTermMASum / LONG_TERM_MOVING_AVERAGE_RANGE );
                        }
                        if (counter > (LONG_TERM_MOVING_AVERAGE_RANGE-1))
                        {
                            longTermMASum = longTermMASum - closingPrices.get(i); //removing the oldest closing price from sum
                            i++;
                            longTermMAs.add( longTermMASum / LONG_TERM_MOVING_AVERAGE_RANGE );
                        }
                    }
                }
            }

            longTermMAs.trimToSize();
            shortTermMAs.trimToSize();
            // Done calculating the short term MAs and long term MAs
        }
    }

    //internal method to calculate end date based on hd range
    private GregorianCalendar calculateStartDate(int historicalDataRange) {
        GregorianCalendar newDate = new GregorianCalendar();

        if (historicalDataRange == 1000)
        {
            newDate.add(newDate.YEAR, -historicalDataRange);
        }
        else
        {
            newDate.add(newDate.YEAR, -historicalDataRange);
            newDate.add(newDate.DATE, -300);
        }

        return newDate;
    }

    //internal method to trim the closing prices and MAs
    private void trimArrays()
    {
        for (int counter =0; counter<LONG_TERM_MOVING_AVERAGE_RANGE; counter++)
        {
            closingPrices.remove(0);
            shortTermMAs.remove(0);
            longTermMAs.remove(0);
        }

        closingPrices.trimToSize();
        shortTermMAs.trimToSize();
        longTermMAs.trimToSize();
    }

    // internal method to update indicators
    private void UpdateIndicators()
    {
        indicators = new ArrayList<Indicators>();
        boolean shortOnTop =false;

        //filling the indicator array
        if (shortTermMAs.size() == longTermMAs.size()) //they really should be the same size...
        {
            for (int counter = 0; counter <  longTermMAs.size(); counter++)
            {

                if (shortTermMAs.get(counter) > longTermMAs.get(counter)) //short term on top
                {
                    if (counter != 0 && shortOnTop == false)//if this isn't the first entry and short term was not previously on top
                    {
                        indicators.add(Indicators.BUY);
                    } else //either first entry or short was previously on top
                    {
                        indicators.add(Indicators.NONE);
                    }
                    shortOnTop = true;
                } else //either current entries are == or <
                {
                    if (shortTermMAs.get(counter) == longTermMAs.get(counter)) //equal
                    {
                        indicators.add(Indicators.NONE);
                    } else // short on bottom
                    {
                        if (counter != 0 && shortOnTop == true)//if this isn't the first entry and short term was previously on top
                        {
                            indicators.add(Indicators.SELL);
                        } else //either first entry or short was previously on bottom
                        {
                            indicators.add(Indicators.NONE);
                        }
                        shortOnTop = false;
                    }
                }

            }
        } //done filling the indicator array

        indicators.trimToSize();
    }
}