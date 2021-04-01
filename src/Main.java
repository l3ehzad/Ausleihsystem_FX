import java.sql.*;
import java.util.*;

public class Main {

    static Connection con = ConnectionFactory.getConnection();

    boolean exit;

    //MENU related methods:
    private void printHeader() {
        System.out.println("--------------------------------");
        System.out.println("|      Ausleihsystemprogram    |");
        System.out.println("|        Menu Application      |");
        System.out.println("--------------------------------");
    }

    private void printMenu(){
        System.out.println("---------- Main Menu:-----------");
        System.out.println("[1]  Borrow device             |");
        System.out.println("[2]  Return device             |");
        System.out.println("[3]  Add device                |");
        System.out.println("[4]  List of borrowed items    |");
        System.out.println("[5]  List of available items   |");
        System.out.println("[6]  Search (Device or Person) |");
        System.out.println("[7]  Add inventory item        |");
        System.out.println("[0]  Exit                      |");
        System.out.println("--------------------------------");

    }

    protected void runMenu() throws Exception {
        printHeader();
        while (!exit){
            printMenu();
            int choice = getInput();
            performAction (choice);
        }
    }

    private int getInput(){
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        while (choice< 0 || choice>7)
            try {
                System.out.println("\nPlease select a number between 0-7:");
                choice = Integer.parseInt(sc.nextLine());
                if (choice>7 || choice<0){
                    System.out.println("Wrong number! Your selected number should be between 1-6.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid selection: Please try again.");
            }
        return choice;}

    private void performAction(int choice) throws Exception {
        switch (choice){
            case 1:
                borrowDevice();
                break;
            case 2:
                returnDevice();
                break;
            case 3:
                addDevice();
                break;
            case 4:
                listOfBorrowed();
                break;
            case 5:
                listOfAvailable();
                break;
            case 6:
                search();
                break;
            case 7:
                addInventory();
                break;
            case 0:
                exit = true;
                System.out.println("Goodbye and have a nice day!");
                break;
            default:
                System.out.println("Sorry! an unknown error has occured.");
        }
    }

    //#1
    private void borrowDevice() throws SQLException {
        System.out.println("\nADD NEW ITEM TO BORROW LIST:");
        String lastName="", firstName="", dshsID="", reason="";
        int deviceID=0, personID=0;
        String borrowDate = DateStamp.printTime();

        //GETTING PERSON INFORMATION
        System.out.println("Enter last name:");
        Scanner sc1 = new Scanner(System.in);
        try {
            lastName = sc1.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering last Name.");
        }

        System.out.println("Enter first name:");
        Scanner sc2 = new Scanner(System.in);
        try {
            firstName = sc2.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering first Name.");
        }

        System.out.println("Enter DSHS ID:");
        Scanner sc3 = new Scanner(System.in);
        try {
            dshsID = sc3.nextLine().toLowerCase();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering DSHS ID.");
        }

        //ENTER PERSON INFORMATION TO MySQL
        Person person = new Person(dshsID, lastName, firstName);
        try {
            person.addPersonToSQL(dshsID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //GETTING DEVICE ID AND CHECK IF IT MATCHES THE DEVICE LIST
        System.out.println("Enter device ID from device list:");
        Scanner sc4 = new Scanner(System.in);
        try {
            deviceID = sc4.nextInt();
            while (!BorrowedItem.checkDeviceIdValidity(deviceID) || !Device.checkDeviceAvailByDevID(deviceID)){
                if (!BorrowedItem.checkDeviceIdValidity(deviceID)) System.out.println("Your entered device ID is invalid. Please check device list and choose an available device ID from the list.");
                if (BorrowedItem.checkDeviceIdValidity(deviceID)&&!Device.checkDeviceAvailByDevID(deviceID)) System.out.println("Sorry! Entered device-ID is not available at the moment. Please choose another item from the list.");

                System.out.println("Enter device ID from device list:");
                Scanner sc5 = new Scanner(System.in);
                deviceID = sc5.nextInt();
            }
        } catch (InputMismatchException e) {
            System.out.println("Your device-ID doesn't match correct Format. Please start again.");
            borrowDevice();
            System.out.println("---------------------------------------------------");

            // HERE WE SHOULD GET OUT OF METHOD: otherwise it re-type "borrowing reason"
            return; // to get out of void method we use "return;"
        }

        //GET BORROWING REASON
        System.out.println("Enter borrowing reason:");
        Scanner sc5 = new Scanner(System.in);
        try {
            reason = sc5.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering Borrowing reason.");
        }

        //CREATE OBJECT OF BorrowedItem class:
        BorrowedItem borrowedItem = new BorrowedItem(deviceID, dshsID, borrowDate, reason);

        //POST BorrowedItem OBJECT TO SQL TABLE
        try {
            borrowedItem.addBorrowedItemToSQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //#2
    private void returnDevice() throws Exception {
        char reply='\0';
        int devID = 0;
        System.out.println("Enter device ID: ");
        Scanner sc1 = new Scanner(System.in);

        try{
            devID = sc1.nextInt();
            while (!BorrowedItem.checkDevAvailInBorrList(devID)){
                System.out.println("Your number is not a valid device ID or is not in borrowed list.\nRe-enter device ID:");
                Scanner sc2 = new Scanner(System.in);
                devID = sc2.nextInt();
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            returnDevice();
        }

        System.out.println("Are you sure you want to delete following item from borrowed list?");
        Device.getDeviceInfoByDevID(devID);
        System.out.println("[Y]es    [N]o");

        Scanner sc8 = new Scanner(System.in);
        try {
            reply = sc8.next().toLowerCase().charAt(0);
            while ( reply != 'n' && reply!='y') {
                System.out.println("Wrong Input! Please only enter Y or N: ");
                Scanner sc9 = new Scanner(System.in);
                reply = sc9.next().toLowerCase().charAt(0);
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input. Please only enter Y or N.");
        }

/*        if (reply == "y") {
            //change device availablity to available 0->1
            BorrowedItem.changeToAvailable(devID);
            //remove item from BorrowedItem table
            BorrowedItem.deleteDeviceFromBorr(devID);
        } else {
            System.out.println("Item will not be removed. Returning to main menu ...");
            runMenu();
        }*/
        switch (reply) {
            case 'n':
                System.out.println("Item will not be removed. Returning to main menu ...");
                runMenu();
                break;
            case 'y':
                //change device availablity to available 0->1
                BorrowedItem.changeToAvailable(devID);
                //remove item from BorrowedItem table
                BorrowedItem.deleteDeviceFromBorr(devID);
                System.out.println("Item deleted.");
                break;
        }
    }

    //#3
    private void addDevice () {
        System.out.println("\nADD NEW ITEM TO DEVICE LIST:");

        String deviceName = null;
        String labelName = null;
        int inventID = 0;
        boolean available;

        System.out.println("Enter device name:");
        Scanner sc1 = new Scanner(System.in);
        try {
            deviceName = sc1.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering Device Name.");
        }

        System.out.println("Enter label name on device (if available):");
        Scanner sc2 = new Scanner(System.in);
        try {
            labelName = sc2.nextLine().toLowerCase();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering Label Name.");
        }

//HOW TO CHEECK IF DATA TYPE IS NOT INTEGER AND REJECT IT? XXXXXXX

        System.out.println("Enter inventory ID for category of device:");
        Scanner sc3 = new Scanner(System.in);

        try {
            inventID = sc3.nextInt();
            while (!Inventory.checkInventIdAvailablity(inventID)){
                System.out.println("Invalid input. Please try again.");
                System.out.println("Enter inventory ID for category of device:");
                Scanner sc4 = new Scanner(System.in);
                inventID = sc4.nextInt();
            }

        } catch (InputMismatchException | SQLException e) {
            System.out.println("Error with entering Label Name.");
        }
        Device device1 = new Device(deviceName, labelName, inventID);
        try {
            Device.postDevice(device1);
            Inventory.incInvNr(inventID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //#4
    private void listOfBorrowed() throws SQLException {
        System.out.println("Printing list of borrowed items ...\n");
        BorrowedItem.listOfAllBorrItems();
    }

    //#5
    private void listOfAvailable() throws SQLException {
        System.out.println("Printing list of available items ...\n");
        BorrowedItem.listOfAvailItems();
    }

    //#6
    private void search() throws Exception {
        int choice = 0;
        int deviceID = 0;
        String dshsID = null;
        System.out.println("------- Search Menu:--------");
        System.out.println("[1] Search item            |");
        System.out.println("[2] Search DSHS-ID         |");
        System.out.println("[3] List of all devices    |");
        System.out.println("[4] Back to menu           |");
        System.out.println("----------------------------");
        System.out.println("Select an option: ");

        Scanner sc = new Scanner(System.in);
        try {
            choice = sc.nextInt();
            while (choice < 1 || choice > 4) {
                System.out.println("invalid number! select a number from menu:");
                Scanner sc2 = new Scanner(System.in);
                choice = sc2.nextInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("invalid input. You should enter a number from menu: ");
            search();
        }

        switch (choice) {
            case 1:
                System.out.println("Enter device-ID:");
                Scanner sc3 = new Scanner(System.in);
                try {
                    deviceID = sc3.nextInt();
                    while (!BorrowedItem.checkDeviceIdValidity(deviceID)) {
                        System.out.println("Invalid device-ID. Please check your device ID if it's available and try again:");
                        System.out.println("Enter device-ID:");
                        Scanner sc4 = new Scanner(System.in);
                        deviceID = sc4.nextInt();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Your device-ID should be a number. Please check your device ID if it's available and try again:\n");
                    search();
                }
                Device.getDeviceInfoByDevID(deviceID);
                break;

            case 2:
                System.out.println("Enter DSHS-ID:");
                Scanner sc5 = new Scanner(System.in);
                try {
                    dshsID = sc5.next().toLowerCase();
                    while (!Person.checkDshsIDAvailablity(dshsID)) {
                        System.out.println("Invalid DSHS-ID. It contains 2 letters and 4 numbers: (e.g.: ab1234)");
                        System.out.println("Re-enter DSHS-ID:");
                        Scanner sc6 = new Scanner(System.in);
                        dshsID = sc6.next().toLowerCase();
                    }
                    BorrowedItem.listOfBorrItemsBasedOnDshsID(dshsID);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid DSHS-ID! It contains 2 letters and 4 numbers: (e.g.: ab1234)\n");
                    search();
                }
                break;

            case 3:
                System.out.println("------ List of all device in the organisation ------");
                Device.listOfAllDevices();
                break;

            case 4:
                runMenu();
                break;
        }
    }

    //#7
    //SQL Befehl zum INSERT Inventory
    public static void addInventory() throws Exception {
        String invType=null;
        int inventSum=0;
        System.out.println("ADD INVENTORY ITEM:");
        System.out.println("Enter category of inventory (laptop, cable, etc.):");
        Scanner sc1 = new Scanner(System.in);
        try {
            invType = sc1.nextLine().toLowerCase();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering category type.");
        }

        if (!Inventory.checkInvTypeAva(invType)){
            Inventory inventory = new Inventory(invType, inventSum);
            Inventory.postInventory(inventory);
            inventory.toString();
        } else {
            System.out.println("Inventory Type: "+invType+ " already exists. Check inventory list or try another inventory type.");

        }

       /* System.out.println("Enter number of available items of the category:");
        Scanner sc2 = new Scanner(System.in);
        try {
            inventSum = sc2.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error with entering number of category.");
        }*/



    }

    //Main Programm
    public static void main (String [] args) throws Exception {

        //run main menu of programm
        Main main = new Main();
        main.runMenu();

        //close connection
        ConnectionFactory.close();
    }
}
