# Yellow Bus
Android Project
Completely made using JAVA
App Name: Yellow Bus

Problem Statement:
The current process of booking bus tickets is often time-consuming and inconvenient for passengers, with long queues at bus stations and the need to physically visit a ticketing counter. Additionally, managing the bus schedules, seat availability, and passenger information can be challenging for bus operators.

Module Description:

A.Bus Tickets - MainActivity.java
  - Directly this page opens up without any login page because I wanted the user to get straight to ticket booking and once he has selected his booking, then the login page appears.
  - Source, Destination and Date of travel are being asked from the user.
  - If Source and Destination are both selected as the same, a Toast message appears - “Both Source and Destination cannot be same”.
  - If the date is entered wrong or a past date before 2023 is entered, a Toast message appears - "Enter Correct Date!"
  - Also, all of this information is shared with options_activity.java using the Intent object.

B.Options Page - options_activity.java
  - From <Source> to <Destination> is displayed on top using the Intent object and fetching data from the previous activity. Also, the date.
  - Now, depending upon the Source and Destination, different prices and timings are offered by the two companies our app has paired with. Thus, all the info is changed.
  - Radio Buttons are used with action listeners so that only one is clicked at a time, and if none of them is selected and the user tries to press confirm button, a Toast message appears - "Select an option!"
  - If correctly an option is selected, an Alert box pops up with the title - "Confirm about your selection?"
  - When “Yes” is pressed, Shared Preferences("MyPref") is used to store the selected route details and the timings.
  - It also checks for a string stored under Shared Preference - "Preff" by the name of “active”, and if the value stored is
  - “yes” - means the user has already logged in and he wants to change his booking option, so it leads the user to my_bookings.java.
  - “no” - means that the user is selecting the option for the first time and has not logged in to the app, thus diverted to login_activity.java.
  
C.Login Page - login_activity.java
  - Name, Date of Birth and Mobile Number are asked from the user.
  - After Clicking the NEXT Button, DOB is validated for the correct date, and if the format of the mobile number is correct, human verification starts.
  - After successful human verification, OTP is sent to the user's mobile phone.
  - If the correct OTP is entered by the user, all his information is stored in a database - “User” under the table - “user” and profile_activity.java opens up.
  
D.Profile Settings - profile_activity.java
  - Data is fetched using the “User” database and set to appropriate places.
  - There is an option for feedback, which sets to “Feedback Submitted” once the feedback is submitted.
  
E. My Bookings - my_bookings.java
  - Using Shared Preferences - "MyPref" all the information about the selected route is retrieved and set to its appropriate text boxes.
  - There is an option to get the number of seats from the user, which has an addTextChangedListner attached to it, thus the price displayed changes as the number of seats is increased.
  - After clicking the PAY button, the Status displayed changes to "Status: Booking Confimed."
  
At last, Bookings, Home Page and Profile page, all have three Image buttons at the bottom interconnecting all of them.

