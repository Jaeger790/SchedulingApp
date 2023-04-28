Title: CrumbTracker
Purpose: To provide scheduling functions in a desktop application format.  The app is able to pull from a mySQL database for a company to have persisting data collection.
Author: Bradley Rott 
Contact: brott1@crumbTracker.edu
Version: 1.0
Date: 04/07/2023

IDE Version: IntelliJ IDEA 2022.3.1 (Community Edition) Build #IC-223.8214.52, built on December 20, 2022
Java Version: Java SE 19
JavaFX Version: JavaFX-SDK-19

Directions to run application:
Login screen:
    Username: CrumbAdmin
    password: dataCrum6s!
    Click login to open main screen.  Click Exit to exit application
Main Screen:
    Appointments scheduled for current day will be displayed on main table.  If there is an appointment within 15 minutes of login, a label will display text showing the apt id and
     type of appointment.
     Appointments Button: Opens appointments screen where you can add, modify, and remove appointments.
     Reports Button: Opens reports screen where you can view different reports about appointments.
     Customers Button: Opens customers screen where you can add, modify, and delete customers.

Appointments Screen:
    Table displays all appointments by default.  Radio buttons on top will filter that list to appointments scheduled in the current month, or appointments
    scheduled in the next 7 days.
    Home button returns user to main screen
    Form fields are used to input or modify data about a selected appointment record from the appointment table.
    Clear button clears from fields of data and resets them to null.
    Add appointment button will add an appointment record utilizing the information provided in the form fields.
    Modify appointment button will change the data in an appointment record to the data inputted in the form fields.
    Delete button will delete a selected appointment record from the appointment table.
Reports Screen:
    Two tabs on screen will display different reports.
    Home button returns user to main screen
    The Total Reports tab displays report information about appointment and customer records.
    The Contact Schedule tab uses a combo box to select a contact and display all appointments associated with that contact on the table in the tab.
Customers Screen:
    Table displays all customer records.
    Home button returns user to main screen
    Clear button clears all form field data and sets them to null.
    Add customer button will add a customer using data inputted in the form fields.
    Modify customer button will change the data in an customer record to the data inputted in the form fields.
    Delete button will delete a selected customer record from the appointment table.
            Warning: All appointments associated with a customer will be deleted when deleting a customer record.



Description of Total appointments by type report and customers by division report:
Using the appointment by type report allows user to identify how many appointments of each type are currently scheduled.
Using the customers by division report allows users to identify which division has how many customers.

MySQL Connector driver version: mysql-connector-j-8.0.31