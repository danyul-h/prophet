Create a program that helps students manage their personal finances by tracking account balances, income and expenses. The program should allow users to input details about their income sources and expenses, including the amount, category, and date of each transaction. It should provide features to view the current balance, generate summaries of income and expenses over specified periods (e.g., weekly, monthly), and categorize expenses to show spending patterns. Additionally, the program should include functionality to update or delete existing entries and offer search and filter options to easily find specific transactions.

dependencies/utilities:
- eclipse ide
- java 21
- windowbuilder
- jcalendar

colors:
- dark blue - 10,46,127,255
- yellow - 255,168,0,255
- light blue - 29,82,188,255

current plans:
- login page
	- mysql stuff
- top nav: 
	- "<img (scholash logo or user)> welcome <username>"
	- time at the other side
- add transactions
	- add name to transaction
	- add calendar page
		- jcalendar (.getDate -> returns java.util.Date)
	- add editing them / deleting
	- expense or income button
	- save button
	- select category type
		- salary
		- entertainment
		- dining
		- education
		- insurance
		- health
		- groceries
		- transportation
		- home
		- travel
	- add images
	- ability to add description
- implement recurring transactions (bills, salaries) etc
	- select start date
	- select end date
	- mention its recurring in description or add a symbol
	- repeat every x days
	- repeat every x months on day x of the month
	- repeat every x years on <month> <day>
- add monthly, weekly, yearly, year to date, last 30 months, last 7 days, trends/avgs/totals, since start of tracking
	- cashflow
	- income
	- expenses
- add custom categories
- filter transactions by...
	- expense
	- income
	- value
	- recently updated
	- recent calendar