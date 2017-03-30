package maesterbs;

/**
 * The class Driver is created as part of Maester Buy/Sell, the Share Buy/Sell indicator software application
 * that helps the customers of ProfitsRUS choose a stock from the DOW 30 and get access to advice and charts that help
 * visualize whether or not they wish to buy/sell their shares in this stock.
 *
 * Driver asks the user for input to pass information on to the DataCollector, which will calculate the moving average
 * from a stock on Yahoo Finance.
 *
 * For: COMP 354 project (first deliverable), March 15, 2017
 * By: Jennifer Sunahara (27590628), Inna Atanasova (27876947), Krasimir Kanev (27848056), Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679), Jordan Senosiain (26638538), Claudiu Bacisor(27735332)
 **/

import java.util.GregorianCalendar;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args)
    {
        GregorianCalendar end = new GregorianCalendar(); //presumably current date, local timezone
        end.add(end.DATE, - 1); // because closing prices will be of the day before the current day

        GregorianCalendar start;
        DataCollector aDataCollector = new DataCollector();

         // Welcome Message
        System.out.println("**********************************************************************");
        System.out.println("       WELCOME TO OUR MOVING AVERAGE CALCULATOR MODULE!               ");
        System.out.println("               Made Specially for: ProfitsRUS                         ");
        System.out.println("**********************************************************************");
        System.out.println();

        Scanner keyIn = new Scanner(System.in);

        // User chooses stock (out of 4 choices for now- WMT, JNJ, MMM or UTX)
        System.out.println("Please enter a stock choice:  \"WMT\" (WalMartStores),  \"JNJ\"(JohnsonJohnson), \"MMM\" (MMM) or \"UTX\" (UnitedTechnologiesr)): ");
        String userString =  keyIn.next();

        while(validateStockInput(userString) != true)// making sure user entered a valid stock
        {
            System.out.println("Invalid input. Please enter stock choice:  \"WMT\" (WalMartStores),  \"JNJ\"(JohnsonJohnson), \"MMM\" (MMM) or \"UTX\" (UnitedTechnologiesr)):  ");
            userString = keyIn.next();
        }
        String userStock = userString;

        // User chooses Historical Data Range (5, 2, 1) in years
        System.out.println();
        System.out.println("Please enter an integer (5, 2, 1) as the desired Historical Data Range in years: ");

        userString = keyIn.next();

        while(validateHDRangeInput(userString) != true)// making sure user entered a valid HD Range
        {
            System.out.println("Invalid input. Please enter an integer (5, 2, 1) as the desired Historical Data Range in years: ");
            userString = keyIn.next();
        }
        int userHDRange = Integer.parseInt(userString);
        start = calculateStartDate(userHDRange);
        // System.out.println("Start: " + start.get(start.YEAR) + "mm: " + start.get(start.MONTH) + " dd: " + start.get(start.DATE));
        // System.out.println("End: " + end.get(end.YEAR) + "mm: " + end.get(end.MONTH) + " dd: " + end.get(end.DATE));

        // User chooses Moving Average Range (20, 50, 100 or 200)
        System.out.println();
        System.out.println("Please enter an integer (20, 50, 100 or 200) as the desired Moving Average Range in days: ");

        userString = keyIn.next();

        while(validateMARangeInput(userString) != true)// making sure user entered a valid MA Range
        {
            System.out.println("Invalid input. Please enter an integer (20, 50, 100 or 200) as the desired Moving Average Rangein days: ");
            userString = keyIn.next();
        }
        int userMARange = Integer.parseInt(userString);
        System.out.println("Printing the sequence of Moving Averages (range in days: " + userMARange +")....");


        aDataCollector.UpdateData(userStock, userMARange, start, end);

        System.out.println("Closing Prices:" + aDataCollector.getClosingPrices());
        System.out.println("Max Closing Price:" + aDataCollector.getMaxClosingPrice());
        System.out.println("Short Term MAs:" + aDataCollector.getShortTermMAs());
        System.out.println("Long Term MAs:" + aDataCollector.getLongTermMAs());
        System.out.println("Indicators:" + aDataCollector.getIndicators());
    }

    // This internal method validates the Moving Average input
    private static boolean validateMARangeInput(String validateMe)
    {
        if (validateInputAsInt(validateMe)) //make sure it is a number
        {
            int userChoice = Integer.parseInt(validateMe);

            if(userChoice == 20 || userChoice == 50 ||userChoice == 100 ||userChoice == 200) //limited choices
            {
                return true;
            }
        }
        return false;
    }

    // This internal method validates the Moving Average input
    private static boolean validateHDRangeInput(String validateMe)
    {
        if (validateInputAsInt(validateMe)) //make sure it is a number
        {
            int userChoice = Integer.parseInt(validateMe);

            if(userChoice == 5 || userChoice == 2 ||userChoice == 1) //limited choices
            {
                return true;
            }
        }
        return false;
    }

    // This internal method validates the Moving Average input
    private static boolean validateStockInput (String validateMe)
    {
        if (validateMe.equalsIgnoreCase("WMT") || validateMe.equalsIgnoreCase("JNJ") ||validateMe.equalsIgnoreCase("MMM") ||validateMe.equalsIgnoreCase("UTX") )
        {
           return true;
        }
        return false;
    }

    // This internal method validates input
    private static boolean validateInputAsInt(String validateMe)
    {
        String text = validateMe;

        // validate if a number was entered
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '0' && text.charAt(i) != '1' && text.charAt(i) != '2' && text.charAt(i) != '3' && text.charAt(i) != '4' &&
                    text.charAt(i) != '5' && text.charAt(i) != '6' && text.charAt(i) != '7' && text.charAt(i) != '8' && text.charAt(i) != '9') {
                return false;
            }
        }
        return true;
    }

    //This method returns a date based on the range entered
    private static GregorianCalendar calculateStartDate(int range)
    {
        GregorianCalendar newDate = new GregorianCalendar(); //presumably current date, local timezone

        newDate.add(newDate.YEAR, - range);

        return newDate;
    }
}
