import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by lebedev on 17.04.2017.
 */
public class JsonCustomParserHelper {
    //сделать статическими все методы, убрать висячие переменные, класс название + Helper
   // static JsonObject newService, repair, employee, jsObject;

    public static String jsonToStringServiceParser(JsonArray serviceObject){
        StringBuilder str = new StringBuilder("");
        JsonObject newService;
        for(int i = 0; i< serviceObject.size();i++){
            newService = (JsonObject) serviceObject.get(i);
            str.append("Name: ").append(newService.get("name")).append("\n");
            str.append("Address: ").append(newService.get("address")).append("\n");
            str.append("Phone: ").append(newService.get("phone")).append("\n");
            str.append("----------------------\n");
        }
        return str.toString();
    }

    public static String jsonToStringMaxServiceParser(JsonObject jsonObject, int countCustomers){
        JsonObject newService = jsonObject;
        StringBuilder str = new StringBuilder("");
        str.append("Name: ").append(newService.get("name")).append("\n");
        str.append("Address: ").append(newService.get("address")).append("\n");
        str.append("Phone: ").append(newService.get("phone")).append("\n");
        str.append("Number of customers: ").append(countCustomers).append("\n");
        str.append("----------------------\n");
        return str.toString();
    }

    public static String jsonToStringMinSalaryService(JsonArray obj){
        StringBuilder str = new StringBuilder("");
        JsonObject jsObject, newService;
        for(int i = 0; i< obj.size();i++){
            jsObject = (JsonObject) obj.get(i);
            newService = (JsonObject) jsObject.get("center");
            str.append("       Service\n").append("Name: ").append(newService.get("name")).append("\n");
            str.append("Address: ").append(newService.get("address")).append("\n");
            str.append("Phone: ").append(newService.get("phone")).append("\n");
            str.append("Salary :").append(jsObject.get("salary")).append("\n ============================ \n");
        }
        return str.toString();
    }

    public static String jsonToStringRepairParser(JsonArray obj){
        StringBuilder str = new StringBuilder("");
        JsonObject serviceObject, repair;
        for(int i = 0; i< obj.size();i++){
            repair = (JsonObject) obj.get(i);
            serviceObject = (JsonObject) repair.get("carServiceCenter");

            str.append("Description: ").append(repair.get("description")).append("\n");
            str.append("CarServiceCenter: ").append(serviceObject.get("name")).append("\n");
            str.append("-----------------------\n");
        }
        return str.toString();
    }

    public static String getServiceWithCustomersList(JsonArray obj){
        StringBuilder str = new StringBuilder("");
        JsonObject customer, newService;
        JsonArray customers;
        try {
            for (int i = 0; i < obj.size(); i++) {
                newService = (JsonObject) obj.get(i);
                customers = (JsonArray) newService .get("customers");
                str.append("----Service----").append("\n");
                str.append("Name: ").append(newService .get("name")).append("\n");
                str.append("Address: ").append(newService .get("address")).append("\n");
                str.append("Phone: ").append(newService .get("phone")).append("\n");
                for (int j = 0; j < customers.size(); j++) {
                    customer = (JsonObject) customers.get(j);
                    str.append("-------Customers-----").append("\n");
                    str.append("Name ").append(customer.get("name")).append("\n");
                    str.append("Phone ").append(customer.get("phone")).append("\n");
                    str.append("---------------------------\n");
                }
                }
        } catch (Exception e){
            System.out.print("Error null service");
        }
        return str.toString();
    }

    public static String jsonToStringEmployeeParser(JsonArray array){
        StringBuilder str = new StringBuilder("");
        JsonObject service, employee;
        for(int i = 0; i< array.size();i++){
            employee = (JsonObject) array.get(i);
            service = (JsonObject) ((JsonObject) array.get(i)).get("center");
            str.append("firstName: ").append(employee.get("firstName")).append("\n");
            str.append("LastName: ").append(employee.get("lastName")).append("\n");
            str.append("Salary: ").append(employee.get("salary")).append("\n");
            str.append("Center: ").append(service.get("name")).append("\n");
            str.append("---------------\n");
        }
        return str.toString();
    }
}
