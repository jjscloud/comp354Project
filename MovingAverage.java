/**
 * Created first deliverable of the software application to help the customers of ProfitsRUS make a decision
 * on buying/selling shares of a stock from the DOW 30.
 *
 * For: COMP 354 project (first deliverable), March 15, 2017
 * By: Jennifer Sunahara (27590628)
 *     Inna Atanasova (27876947)
 *     Krasimir Kanev (27848056)
 *     Amirali Shirkhodaei (26255906)
 *     Charles Boudreau (27717679)
 *     Jordan Senosiain (26638538)
 *     Claudiu Bacisor(27735332)
 **/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MovingAverage {

    //adjClose = adjusted Closing price, as per Yahoo Finance terminology
    private static ArrayList <Double> adjClose = new ArrayList<Double>();

    // this method reads records from the CSV file
    public static void readRecord()
    {
        //Make sure file can be read
        String fileName = "E:\\Work\\JS- Concordia\\2017 Winter\\COMP 354\\Project\\MA_Edited\\src\\Sampledata.csv";

        File toBeRead = new File(fileName);
        BufferedReader read;

        // reader created
        try
        {
            read = new BufferedReader(new FileReader(toBeRead));
            String currentLine;
            currentLine = read.readLine();

            // reading all the lines printing them out to console and collecting closing prices
            while((currentLine = read.readLine()) != null)
            {
                // splitting values in each CSV line. Each value is separated by a comma
                String[] numbers = currentLine.split(",");

                System.out.println("currentLine: " + " " + currentLine);
                // creating an array list with all the closing prices in it
                // need to parse it because we read the lines as string
                adjClose.add(Double.parseDouble(numbers[6]));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("ERROR: BufferedReader could not be created!");
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Problem with read operation of BufferedReader!");
        }

    }

    // this method calculates the moving average, where the parameter window represents the range over
    // which the moving average is calculated
    public static ArrayList<Double> movingAverageCalculator (int window){

        // trimming the array list of closing prices
        adjClose.trimToSize();
        double sum = 0;
        double movingAverage = 0;
        int i = 0;

        // an array list to store the
        ArrayList<Double> results = new ArrayList<Double>();

        // calculating the moving average with the given window
        for (int counter = 0; counter < adjClose.size(); counter++){
            sum = adjClose.get(counter) + sum;
            if ( ( counter+1 ) >= window )
            {
                if (( counter+1 ) == window )
                {
                    results.add( sum / window );
                }
                else
                {
                    sum=sum-adjClose.get(i);
                    i++;
                    results.add( sum / window );
                }
            }
        }
        return results;
    }

    // This method validates input
    public static boolean validateInput(String validateMe) {
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

    public static void main(String[] args) {

        // Welcome Message
        System.out.println("**********************************************************************");
        System.out.println("       WELCOME TO OUR MOVING AVERAGE CALCULATOR MODULE!               ");
        System.out.println("               Made Specially for: ProfitsRUS                         ");
        System.out.println("**********************************************************************");
        System.out.println();

        System.out.println("In this demonstration, we will be reading from a default file.");
        System.out.println("... Reading from a default file....");
        readRecord();
        System.out.println("... Finished reading from a default file....\n");

        System.out.println("Please enter an integer as the desired Moving Average Range: ");


        Scanner keyIn = new Scanner(System.in);
        String userString = keyIn.next();

        while(validateInput(userString) != true)// making sure user entered an integer
        {
            System.out.println("Invalid input. Please enter an integer as the desired Moving Average Range: ");
            userString = keyIn.next();
        }
        System.out.println("Printing the sequence of Moving Averages (range: " + userString +")....");

        System.out.println( movingAverageCalculator(Integer.parseInt(userString)) );
        //create();

    }
    //
}
