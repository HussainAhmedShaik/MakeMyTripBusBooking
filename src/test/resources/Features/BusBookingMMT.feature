Feature: Bus Ticket Booking on MakeMyTrip

  Background:
    Given The user launches the MakeMyTrip Website
    And The user closes any popup window if present
    And The user navigates to the Buses tab

  @BusBooking
  Scenario Outline: Complete bus reservation flow including filters, seat selection, traveller details, and payment navigation
    When The user selects From location "<fromCity>" and To location "<toCity>"
    And User click on travel date and chooses the travel date as next day from default
    And The user clicks on the Search button

    And The user applies bus type filters "<ACorNonAC>" and "<seatType>"
    And The user selects the checkbox Single
    And The user selects Pick up point "<pickupPoint>"
    And The user selects Pick up time range "<pickupTimeRange>"
    And The user selects Drop point "<dropPoint>"
    And The user selects Drop time range "<dropTimeRange>"

    Then The user fetches all bus prices and prints them in the console
    And The user chooses sleeper or seat based on available

    And The user confirms Pick up point "<pickupPoint>" and Drop point "<dropPoint>"
    And The user clicks the Continue button

    And The user enters traveller details like "<name>","<age>" and "<gender>"
    And The user enters contact details like "<email>" and "<mobile>"
    And The user confirms billing details checkbox
    And The user click on radio button of No,I don't need it of Trip assured
    And The user selects any available coupon code if present
    And The user clicks the Continue button to proceed to payment

    Then The user verifies the Payment page contains the text Payment options
    And The user takes a screenshot of the Payment Page and saves as "<screenshotFile>"

    Examples:
      | fromCity          | toCity             | ACorNonAC | seatType | pickupPoint | pickupTimeRange  | dropPoint       | dropTimeRange       | name          | age | gender | email        | mobile| screenshotFile           |
      |Chennai, Tamil Nadu|Bangalore, Karnataka| AC        | Sleeper  |Sholinganallur|6 PM - 12 AM     |Electronic City| 12 AM - 6 AM        | Hussain Ahmed | 29  | Male   | hussain@gmail.com   | 9876543210| Payment_Page_Screenshot  |
