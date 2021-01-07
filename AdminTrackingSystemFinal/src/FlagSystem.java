import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class FlagSystem {
    /**
     *<h1>Flag System</h1>
     * This class is design to flag either customer or shop status
     * to case, close or normal.
     * A message will be display when flagging is success.
     *
     * @author Lew Zi Xuan
     * @version 1.0
     * @since 2021-01-05
     */
    public static void flagCaseClose() {
        /**
         * This method is used to flag customer's and shop's status to "Case",
         * followed by flag "Close" automation.
         * The visit report will be displayed to let the admin choose the customer that
         * needs to flag.
         * Admin can flag customers one by one before exiting the operation by input
         * -1.
         */

        VisitReport visitReport = new VisitReport();
        visitReport.display();

        while (true) {
            System.out.println("Choose from customer to be flagged:");
            System.out.println("(Please input one by one && input -1 to stop)");
            int custID = InputValid.checkRange(-1, Visit.visits.size());
            if (custID == -1)
                return;
            else
                flagCaseGetRecord(custID);
        }

    }

    public static void flagCaseGetRecord(int custID) {
        /**
         * This method is used to get the selected customer's record
         * from the Visit report and then pass record into
         * flagCaseClose(choice : int) method.
         * After the flagging success, All the customer's and shop's
         * data will be serialized and save to its own file so
         * data won't be discarded.
         * A message will be displayed after successfully flagging.
         * @param custID This is the selected customer to be flag.
         */

        for (int i = 0; i < Visit.visits.size(); i++)
            if (custID == Visit.visits.get(i).getCustID())
                flagCaseClose(i);


        Customer.Serialize();
        Shop.Serialize();
        System.out.println("Flag successfully.\n");
    }

    public static void flagCaseClose(int choice) {
        /**
         * This method used selected customer's visit record
         * to flag the visited shop and the customer's status
         * will be set to case.
         * After that, we add all the potential customer that will
         * become close clause into a temporary list.
         * If the potential customer visited time is within 1-hour range
         * before or after the case's time and is not "Case",
         * The customer's status will change to "Close".
         * @param choice This is selected customer to be flag.
         */

        Visit selectedCase = Visit.visits.get(choice - 1);

        int shopID = selectedCase.getShopID();
        int custID = selectedCase.getCustID();
        LocalDateTime CaseDT = selectedCase.getDt();
        LocalDateTime BfCaseDT = CaseDT.minusHours(1);
        LocalDateTime AfCaseDT = CaseDT.plusHours(1);

        Customer.custs.get(custID - 1).setStatus("Case");
        Shop.shops.get(shopID - 1).setStatus("Case");

        ArrayList<Visit> CloseCustList = new ArrayList<>();

        for (Visit v : Visit.visits) {
            if (v.getShopID() == shopID)
                CloseCustList.add(v);
        }

        for (Visit v : CloseCustList) {
            LocalDateTime VisitDT = v.getDt();
            int VisitCustID = v.getCustID();

            if ((VisitDT.isBefore(CaseDT) && VisitDT.isAfter(BfCaseDT))
                    || (VisitDT.isAfter(CaseDT) && VisitDT.isBefore(AfCaseDT))) {
                if (!Customer.custs.get(VisitCustID - 1).getStatus().equals("Case"))
                    Customer.custs.get(VisitCustID - 1).setStatus("Close");
            }
        }
    }


    public static void flagNormal(int role){
        /**
         * This method is to flag customer's or shop's status
         * to "Normal". Role is to determine which want to be
         * flag.
         * @param role This is key to determine either flag
         *             customer or shop.
         */

        if(role == 1)
            flagCaseNormalCust();
        else
            flagCaseNormalShop();

        System.out.println("Flag Successfully.\n");

    }
    public static void flagCaseNormalCust(){
        /**
         * This method is to flag the customer's status to
         * "Normal". A customer list will be displayed for
         * user to choose the customer to be flag normal.
         * After flagging successful, the latest data will
         * serialized.
         */
        int choice;

        CustReport custReport = new CustReport();
        custReport.display();
        System.out.print("Please enter No. to be flag Normal: ");
        choice = InputValid.checkRange(1,Customer.custs.size());

        Customer.custs.get(choice-1).setStatus("Normal");

        Customer.Serialize();
    }

    public static void flagCaseNormalShop(){
        /**
         * This method is to flag the shop's status to
         * "Normal". A shop list will be displayed for
         * user to choose the shop to be flag normal.
         * After flagging successful, the latest data will
         * be serialized.
         */
        int choice;

        ShopReport shopReport = new ShopReport();
        shopReport.display();
        System.out.print("Please enter No. to be flag Normal: ");
        choice = InputValid.checkRange(1,Shop.shops.size());

        Shop.shops.get(choice-1).setStatus("Normal");

        Shop.Serialize();
    }
}
