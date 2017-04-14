# comp354Project
Software Engineering Project

 Technical analysis software for the stock market to guide users on whether to buy or sell a stock, based on the changes of moving averages of the closing stock prices.
 
 (The following is a User Guide Manual that is also available through the tool’s Help menu)

The Share Buy/Sell Indicator Tool (or simply the tool from now on) is intended to help professionals working on the Stock Market to make informed decisions for a trade with a particular stock from the DOW 30 list.
The Main View buttons:
Drop-down menu Choose Stock – choose a company from the current DOW 30 component list (the stock data of this company will be displayed, according user specifications, as explained below)
Drop-down menu Historical Data – select a time frame for the data of the chosen stock:
All data – selects all available trade data since the company (stock) of choice entered DOW 30 index;
5 - Last five years – selects the available trade data for the company of choice in last five year period, starting from the current date and time;
2 - Last two years – selects the available trade data for the company of choice in last two year period, starting from the current date and time;
1 - Last one year   – selects the available trade data for the company of choice in last one year period, starting from the current date and time;
Drop-down menu Moving Average – select the range of the calculated Moving Average, used to calculate the BUY/SELL indicators.
The choices here are 20, 50,100, and 200 days.
Note about the drop-down menus: 
-	The tool allows any combination of the selections from the three drop-down menus

After the desired combination, the data can be prompted to be displayed:
Button Submit – pressing the button Submit will do the following:
-	displays updated charts representing the stock data for the company of interest;
-	based on the Maester highly respected algorithm the indicators BUY and SELL will be displayed on the charts providing advice for a market decision;
-	on the bottom a legend for used charts, indicators colors and symbols will be displayed


Notes about the Submit button:
-	If this is the first use of the tool and nothing has been selected from the drop-down menus upon click on the Submit button. The default values will be load as follow:
	for Choose Stock      - the current “top on the list” company in DOW 30;
	for Historical Data   -“1” – The last one year;
	for Moving Average - “20” – The last twenty days;
-	If this is Not the first use of the tool and nothing has been selected from the drop-down menus upon click on the Submit button. The last used values will be used for creating and displaying updated charts and indicators.
-	If for some reason the tool could not receive the requested stock data after pressing the Submit button the following message will be displayed:
        			“Unable to acquire data from Yahoo Finance!
				Please try again later or ask Administrator for assistance"

List Menu button - will display an additional list menu on the left side of the main window

Side Menu options
After clicking on the List Menu button the following additional options will be displayed:
	Change Password – will open a window for changing the user password. It is necessary when there are security concerns or when it is recommended to change the password. For example it is recommended to change your password after first login for which you will use the password given to you by the administrator who created an account for you!
	Help – You are here! (This is where you are reading the current user manual);
	Contact – will display useful contact info for questions and help for solving any problem related to the use of this tool;
	Reports – this function is available for the users with certain privileges, as administrators and supervisors for example;
	Log Out – to safely log out from the tool.



