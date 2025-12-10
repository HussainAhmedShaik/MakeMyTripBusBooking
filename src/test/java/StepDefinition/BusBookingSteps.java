package StepDefinition;

import BaseClass.BaseClass;
import PageObjectModel.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class BusBookingSteps extends BaseClass {

    HomePage home = new HomePage(driver);
    BusSearchPage BusSearch = new BusSearchPage(driver);
    BusResultsPage BusResults = new BusResultsPage(driver);
    PassengerDetailsPage passenger = new PassengerDetailsPage(driver);
    PaymentPage payment = new PaymentPage(driver);



    @Given("The user launches the MakeMyTrip Website")
    public void the_user_launches_the_make_my_trip_website() {
        System.out.println("Make My Trip Launched");

    }

    @Given("The user closes any popup window if present")
    public void the_user_closes_any_popup_window_if_present() {
        home.closePopupIfAvailable();
        home.closeCountryPopupIfVisible();

    }

    @Given("The user navigates to the Buses tab")
    public void the_user_navigates_to_the_buses_tab() {
        home.clickBusTab();

    }

    @When("The user selects From location {string} and To location {string}")
    public void the_user_selects_from_location_and_to_location(String fromCity, String toCity) {
        BusSearch.enterFromCity(fromCity);
        sleep(300);
        BusSearch.enterToCity(toCity);
        sleep(300);

    }
    @And("User click on travel date and chooses the travel date as next day from default")
    public void userClickOnTravelDateAndChoosesTheTravelDateAsNextDayFromDefault(){
        sleep(1000);
        BusSearch.clickTravelDateField();
        sleep(750);
        BusSearch.selectNextDay();
        sleep(300);

    }

    @Then("The user clicks on the Search button")
    public void the_user_clicks_on_the_search_button() {
        BusSearch.clickSearchButton();
        sleep(300);
    }

    @Then("The user applies bus type filters {string} and {string}")
    public void the_user_applies_bus_type_filters_and(String ACorNonAC, String seatType) {
        // Apply AC or Non-AC based on input
        if (ACorNonAC.equalsIgnoreCase("AC")) {
            BusResults.applyACFilter();
            sleep(300);
        } else if (ACorNonAC.equalsIgnoreCase("Non AC")) {
            BusResults.applyNonACFilter();// Define this in your POM
            sleep(300);
        }

        // Apply Seater or Sleeper based on input
        if (seatType.equalsIgnoreCase("Sleeper")) {
            BusResults.selectSleeperOptionIfAvailable();
            sleep(300);
        } else if (seatType.equalsIgnoreCase("Seater")) {
            BusResults.selectSeaterOptionIfAvailable();// Define this in your POM
            sleep(300);
        }
    }


    @Then("The user selects the checkbox Single")
    public void the_user_selects_the_checkbox_single() {
        BusResults.selectSingleSeatOption();
        sleep(300);

    }

    @Then("The user selects Pick up point {string}")
    public void the_user_selects_pick_up_point(String pickupPoint){
        BusResults.selectPickupPoint(pickupPoint);
        sleep(300);

    }

    @Then("The user selects Pick up time range {string}")
    public void the_user_selects_pick_up_time_range(String pickupTimeRange) {
        BusResults.selectPickupTime(pickupTimeRange);
        sleep(300);
    }

    @Then("The user selects Drop point {string}")
    public void the_user_selects_drop_point(String dropPoint){
        BusResults.selectDropPoint(dropPoint);
        sleep(300);

    }

    @Then("The user selects Drop time range {string}")
    public void the_user_selects_drop_time_range(String dropTimeRange) {
        BusResults.selectDropTime(dropTimeRange);
        sleep(300);
    }

    @Then("The user fetches all bus prices and prints them in the console")
    public void the_user_fetches_all_bus_prices_and_prints_them_in_the_console() {
        BusResults.verifyBusResultsAvailable();
        sleep(3000);
        BusResults.printAllBusPricesWithOperators();
        sleep(3000);

    }
    @And("The user chooses sleeper or seat based on available")
    public void theUserChoosesSleeperOrSeatBasedOnAvailable() {
        BusResults.clickSelectSeatWithLowestPrice();
        sleep(300);
        BusResults.chooseAvailableSeat();
        sleep(500);

    }

    @Then("The user confirms Pick up point {string} and Drop point {string}")
    public void the_user_confirms_pick_up_point_and_drop_point(String pickupPoint, String dropPoint) {
        BusResults.selectPickupPointAfterSeatSelect(pickupPoint);
        sleep(300);
        BusResults.selectDropPointAfterSeatSelect(dropPoint);
        sleep(300);
    }

    @Then("The user clicks the Continue button")
    public void the_user_clicks_the_continue_button() {
        BusResults.proceedToPassengerDetails();
        sleep(500);

    }

    @Then("The user enters traveller details like {string},{string} and {string}")
    public void the_user_enters_traveller_details_like_and(String name, String age, String gender) {
        passenger.verifyPassengerDetailsPageDisplayed();
        sleep(300);
        passenger.enterPassengerName(name);
        sleep(300);
        passenger.enterPassengerAge(age);
        sleep(200);
        passenger.selectGender(gender);

    }

    @Then("The user enters contact details like {string} and {string}")
    public void the_user_enters_contact_details_like_and(String email, String mobile) {
        passenger.enterEmail(email);
        sleep(300);
        passenger.enterMobileNumber(mobile);
        sleep(300);

    }


    @Then("The user confirms billing details checkbox")
    public void the_user_confirms_billing_details_checkbox() {
        passenger.enableBillingCheckBox();
        sleep(300);

    }

    @Then("The user click on radio button of No,I don't need it of Trip assured")
    public void the_user_click_on_radio_button_of_no_i_don_t_need_it_of_trip_assured() {
        passenger.selectNoInsuranceOption();
        sleep(300);
    }

    @Then("The user selects any available coupon code if present")
    public void the_user_selects_any_available_coupon_code_if_present() {
        passenger.applyCouponAutomatically();
        sleep(300);

    }

    @Then("The user clicks the Continue button to proceed to payment")
    public void the_user_clicks_the_continue_button_to_proceed_to_payment() {
        passenger.clickContinueButton();
        sleep(1000);

    }

    @Then("The user verifies the Payment page contains the text Payment options")
    public void theUserVerifiesThePaymentPageContainsTheTextPaymentOptions() {

        payment.verifyPaymentPageLoaded();
        sleep(300);

    }

    @Then("The user takes a screenshot of the Payment Page and saves as {string}")
    public void the_user_takes_a_screenshot_of_the_payment_page_and_saves_as(String screenshotFile) {
        payment.capturePaymentScreenshot(screenshotFile);
        sleep(500);

    }


}
