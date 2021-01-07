import java.io.*;
import java.util.ArrayList;

public class Customer extends Role  {
    private static final long serialVersionUID = 6523585098267757690L;
    private static int count = 0;
    public static ArrayList<Customer> custs = new ArrayList<Customer>();

    private int id;

    private String password;
    private final static String FILE_PATH = Initializer.CUR_PATH + "\\Customers.bin";

    public static void Serialize() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(custs);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Deserialize() {
        custs.clear();
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            custs = (ArrayList) ois.readObject();
            count = custs.size();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            return; //No serialization found
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Customer(String name, String password, String phone, String status) {
        super(name, password, phone, status);
        this.id = ++count;
        custs.add(this);
        Serialize();
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        if (status.equals("Normal") || status.equals("Case") || status.equals("Close"))
            super.setStatus(status);
    }

    public static Customer getCustByNumber (String number) {
        for (Customer c : Customer.custs)
            if (c.getPhone().equals(number))
                return c;
        return null;
    }
}
