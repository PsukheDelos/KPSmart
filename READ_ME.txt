READ ME: KPSmart

0.  Running the System

	1.  Navigate to KPS.main
	2.  run BeginServer.java
	3.  run BeginClient.java
	4.  Username: admin, Password: Root

1.  Navigating the System Tabs

	A.  Dashboard
	Once you have logged in (in this case as a manager/admin) you are presented with the Dashboard. Displayed here is: 

		1.  Total Revenue
		2.  Total Expenditure
		3.  Total Profit
		4.  Trends: A line graph of Profit by Month
		5.  Domestic: Pie graph of Revenue/Expense making up all Domestic Mail Events
		6.  International: Pie graph of Revenue/Expense making up all International Mail Events
		7.  Events: A table of all events logged in the log.xml file

	B.  New Mail Event 
	Here you can create a New Mail Event

		Source: Will only display Cities that appear as origins in Prices
		Destination: Will only display Destinations that appear as destinations in Prices
		Priority: Will only display available priorities depending on Origin and Desintation selection
		Weight: Enter the weight of the product
		Volume: Enter the volume of the product 

		Total Price: Displays Price of shipping using Pricing info

		Submit: Submit your mail event (currently non functional)

	C.  Routes
	Here you can edit the Routes

		+ : 	Add a Route
		Edit: 	Edit a Route
		-: 		Remove a Route

		Clicking on + or edit will display the Edit Route Box

		Here you can add or change an existing route.  

		WARNING: If you are adding a Route the Origin and Destination MUST be cities contained within the locations.  If you add cities not in there it will throw the database for a loop the next time you load the program.  Please see Section 2 for a list of approved Cities at present. 


	D.  Prices

		+: 		Add a Price
		Edit: 	Edit a Price
		-: 		Remove a Price

		Click on + or edit will display the Edit Price Box

		Here you can add or change an existing Price. 


	E.  Locations

		This is a map of the world that displays all the Cities in the City database.  It also maps out Routes as per the Routes tab.  

		NOTE:  These will not update in real time.  So any routes you add or remove, the changes will not appear until you start the program again.  Coming in a future update. 

	F.  Users

		+: Add a User
		-: Remove a User

		Clicking on + will display the Add User Box.  

		Here you can add new users and specify their permissions as either a manager or clerk.  If you attempt to add a user that already exists an Error appears. 

2.  Approved Cities

		"Dublin"
		"Berlin"
		"London"
		"Bruxelles"
		"Kiev"
		"Dhaka"
		"Hanoi"
		"Mexico City"
		"Lusaka"
		"Malabo"
		"Manila"
		"Masqat"
		"Monrovia"
		"Montevideo"
		"Moskva"
		"Nairobi"
		"Nouakchott"
		"Oslo"
		"Ouagadouou"
		"Panama"
		"Paramaribo"
		"Port Moresby"
		"Rabat"
		"Reykjavik"
		"Riga"
		"Rome"
		"Stockholm"
		"Tel Aviv-Yafo"
		"Toshkent"
		"Windhoek"
		"Wellington"
		"Abu Zaby"
		"Hong Kong"
		"Auckland"
		"Hamilton"
		"Rotorua"
		"Palmerston North"
		"Christchurch"
		"Dunedin"

