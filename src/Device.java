import java.sql.*;

public class Device {
    private String deviceName;
    private String labelName;
    private int inventID;           //F.K. Inventory
    private boolean available;

    public Device(String deviceName, String labelName, int inventID) {
        this.deviceName = deviceName;
        this.labelName = labelName;
        this.inventID = inventID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getLabelName() {
        return labelName;
    }

    public int getInventID() {
        return inventID;
    }

    public boolean isAvailable() {
        return available;
    }

    public static void getDeviceInfoByDevID(int deviceID) throws SQLException {
        int availablity = 0;
            PreparedStatement statement = Main.con.prepareStatement("SELECT * FROM device WHERE deviceID = "+ deviceID + ";");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                availablity = result.getInt("available");
                System.out.println("Information for device ID: " + deviceID);
                System.out.print("Device name: "+ result.getString(2) + " - "+ result.getString(3)+" | Status: " );
                if (availablity == 0) System.out.println(" unavailable");
                    else System.out.println(" available");
                System.out.println();
            }
    }

    public static boolean checkDeviceAvailByDevID(int deviceID) throws SQLException{
        int availablity = 0;
            PreparedStatement statement = Main.con.prepareStatement("SELECT available FROM device WHERE deviceID = "+ deviceID + ";");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                availablity = result.getInt(1);
            }
        return (availablity == 1);
    }

    public static void listOfAllDevices () throws SQLException {
        String availablity = null;

            PreparedStatement statement = Main.con.prepareStatement("SELECT * FROM device;");

            ResultSet result = statement.executeQuery();

            while (result.next()){
                if (result.getInt("available")==0) {availablity = "NOT available";} else {availablity = " is available";}
                System.out.println("device ID: "+result.getInt(1)+" ("+ availablity +")"+ " | Device name: "+ result.getString(2) + " - "+result.getString(3));
            }

        System.out.println();
    }

    //SQL Befehl zum INSERT Device
    public static void postDevice (Device device) throws Exception{
        PreparedStatement posted = Main.con.prepareStatement("INSERT INTO device (deviceName, labelName, fk_inventID) VALUES ('"+device.getDeviceName()+"', '"+device.getLabelName()+"','"+device.getInventID()+"')");
        posted.executeUpdate();}

    @Override
    public String toString() {
        return "Device{" +
                "deviceName='" + deviceName + '\'' +
                ", labelName='" + labelName + '\'' +
                ", inventID=" + inventID +
                ", available=" + available +
/*                ", deviceID=" + deviceID +*/
                '}';
    }

}
