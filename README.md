# 🚌 MakeMyTrip Bus Booking Automation

Automated testing project for MakeMyTrip Bus Booking functionality using **Selenium WebDriver**, **Cucumber BDD**, **Java**, and **TestNG** in **IntelliJ IDEA**.

## 📌 Repository
GitHub: [MakeMyTripBusBooking](https://github.com/HussainAhmedShaik/MakeMyTripBusBooking)

Branch: `CucumberPOC`

---

## 🛠️ Technologies Used

- **Java 17+**
- **Maven**
- **Selenium WebDriver**
- **Cucumber BDD**
- **TestNG**
- **Extent Reports / Cucumber Reports**
- **Page Object Model (POM) Design Pattern**
- **IntelliJ IDEA**
- **Git & GitHub**

---

## 📂 Project Structure
<img width="670" height="826" alt="image" src="https://github.com/user-attachments/assets/2cc556d5-6d5d-4495-965c-1d2b447554ff" />
---

## 🧪 Test Scenario (Feature File Summary)
The feature file `BusBookingMMT.feature` includes:

- Search for buses from a source to destination on a specific date.
- Choose the best available seat (priority: Upper Single > Lower Single > Upper Combined > Lower Combined > Seater).
- Fill in passenger and payment details.
---
## 🚀 How to Run the Project
### Pre-requisites
- Java 17+
- Maven
- IntelliJ IDEA
- Git
### Steps
1. Clone the repository:

```bash
git clone https://github.com/HussainAhmedShaik/MakeMyTripBusBooking.git
cd MakeMyTripBusBooking

Checkout the branch:
git checkout CucumberPOC

Run from IntelliJ:
Open project in IntelliJ
Open TestRunner.java
Right-click > Run

Or run using Maven:
mvn clean verify

🧾 Reporting
After execution, reports are generated under:
target/CucumberReports/

Includes:
Step-wise pass/fail results
Screenshots for failed steps (under /screenshots/)

🧠 Seat Selection Logic
Seat selection follows a priority:
Upper Sleeper Single Berth
Lower Sleeper Single Berth
Upper Sleeper Combined Berth
Lower Sleeper Combined Berth
Seater
XPath logic identifies and interacts with these based on seat availability.

### 👤 Author  
*Hussain Ahmed Shaik*

🔗 **[View GitHub Profile](https://github.com/HussainAhmedShaik)**
