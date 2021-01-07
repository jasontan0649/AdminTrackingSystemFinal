import java.io.*;
import java.util.ArrayList;

public class Shop extends Role {
    private static final long serialVersionUID = 6529685098267757690L;
    public static ArrayList<Shop> shops = new ArrayList<Shop>();

    private static int count = 0;

    private int id;
    private String manager;
    private final static String FILE_PATH = Initializer.CUR_PATH + "\\Shops.bin";


    public Shop(String name, String password, String phone, String status, String manager) {
        super(name, password, phone, status);
        this.id = ++count;
        setManager(manager);
        shops.add(this);
        Serialize();
    }

    public static void Serialize() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(shops);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Deserialize() {
        shops.clear();
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            shops = (ArrayList) ois.readObject();
            count = shops.size();
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

    public int getId() {
        return id;
    }

    @Override
    public void setStatus(String status) {
        if (status.equals("Normal") || status.equals("Case"))
            super.setStatus(status);
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public static Shop getShopByName(String name) {
        for (Shop s : Shop.shops)
            if (s.getName().equals(name))
                return s;
        return null;
    }


}
