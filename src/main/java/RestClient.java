import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by lebedev on 04.04.2017.
 */
public class RestClient {
    public final StringBuilder ADDRESS =  new StringBuilder("http://localhost:8080/app-portal/api/"); //сделать статическим ADDRESS, сессию получать в каждом методе.

    public String getSession(HttpClient client){
        StringBuilder url = new StringBuilder();
        url.append(ADDRESS);
        url.append("login?u=admin&p=admin&l=ru");
        HttpPost request = new HttpPost(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        JsonObject login = new JsonObject();
        login.addProperty("username", "admin");
        login.addProperty("password", "admin");
        login.addProperty("locale", "en");
        String session = null;
        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(login.toString());
            request.setEntity(stringEntity);
            HttpResponse response = client.execute(request);
            HttpEntity httpEntity = response.getEntity();
            session = EntityUtils.toString(httpEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public void getServices(HttpClient client) throws IOException {

        String entity = "cubatrainingtask$CarServiceCenter";
        String query = "select+c+from+cubatrainingtask$CarServiceCenter+c";
        String view = "carServiceCenter-view";

        StringBuilder url = new StringBuilder();
        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);

        System.out.print(url);
        HttpGet request = new HttpGet(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        HttpResponse response = client.execute(request);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);
        JsonParser jsonParser = new JsonParser();

        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringServiceParser(obj));
    }

    public void commitService(String name, String address, String phone, HttpClient client) throws IOException {
        StringBuilder urlk = new StringBuilder(""); //инициализировать там же, где и получаем
        String nameSet = name; // не надо дублировать, достаточно работать с ними, если не изменяем
        String addressSet = address;
        String phoneSet = phone;

        urlk.append(address);
        urlk.append("commit?s=");
        urlk.append(this.getSession(client));

        HttpPost request = new HttpPost(urlk.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        JsonObject newService = new JsonObject();
        JsonArray serviceArray = new JsonArray();

        JsonObject infoService = new JsonObject();
        infoService.addProperty("id", "NEW-cubatrainingtask$CarServiceCenter");
        infoService.addProperty("name", nameSet);
        infoService.addProperty("address", addressSet);
        infoService.addProperty("phone", phoneSet);
        serviceArray.add(infoService);

        newService.add("commitInstances", serviceArray);

        StringEntity stringEntity = new StringEntity(newService.toString());
        request.setEntity(stringEntity);

        HttpResponse response = client.execute(request);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);

        JsonParser jsonParser = new JsonParser();
        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringServiceParser(obj));
    }

    public void getRepairs(HttpClient client) throws IOException {
        String entity = "cubatrainingtask$Repair";
        String query = "select+c+from+cubatrainingtask$Repair+c";
        String view = "repair-view";

        StringBuilder url = new StringBuilder();
        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);
        HttpGet request = new HttpGet(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        HttpResponse response = client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);

        JsonParser jsonParser = new JsonParser();

        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringRepairParser(obj));
    }

    public void commitRepair(String desc, HttpClient client) throws IOException {
        StringBuilder url = new StringBuilder("");
        url.append(ADDRESS);
        url.append("commit?s=");
        url.append(this.getSession(client));

        HttpPost request = new HttpPost(url.toString());
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        JsonObject newRepair = new JsonObject();
        JsonArray serviceArray = new JsonArray();
        JsonObject service = new JsonObject();

        JsonObject infoService = new JsonObject();
        infoService.addProperty("id", "NEW-cubatrainingtask$Repair");
        infoService.addProperty("description", desc);
            service.addProperty("id", "cubatrainingtask$CarServiceCenter-5b9c5a24-8632-9817-dbf3-a168752f10d5");
            service.addProperty("name", "NY");
        infoService.add("carServiceCenter", service);
        serviceArray.add(infoService);

        newRepair.add("commitInstances", serviceArray);

        StringEntity stringEntity = new StringEntity(newRepair.toString());

        request.setEntity(stringEntity);
        HttpResponse response = client.execute(request);

        int responseCode = response.getStatusLine().getStatusCode();
        System.out.print("Responce code of selecting services = " + responseCode);

        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);

        JsonParser jsonParser = new JsonParser();
        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringRepairParser(obj));
    }

    public void getServicesWithCust(HttpClient client) throws IOException {
        String entity = "cubatrainingtask$CarServiceCenter";
        String query = "select+c+from+cubatrainingtask$CarServiceCenter+c";
        String view = "carServiceCenter-view";

        StringBuilder url = new StringBuilder("");
        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);

        HttpGet request = new HttpGet(url.toString());
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        HttpResponse response = client.execute(request);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);
        JsonParser jsonParser = new JsonParser();

        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.getServiceWithCustomersList(obj));
    }

    public void getMaxSalaryEmpl(HttpClient client) throws IOException {
        String entity = "cubatrainingtask$Employee";
        String query = "select+c+from+cubatrainingtask$Employee+c+where+c.salary+in+(select+max(c.salary)+from+cubatrainingtask$Employee+c)";
        String view = "employee-view";

        StringBuilder url = new StringBuilder("");
        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);

        HttpGet request = new HttpGet(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        HttpResponse response = client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);

        JsonParser jsonParser = new JsonParser();
        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringEmployeeParser(obj));
    }

    public void getMinSalaryService(HttpClient client) throws IOException {
        String entity = "cubatrainingtask$Employee";
        String query = "select+c+from+cubatrainingtask$Employee+c+where+c.salary+in+(select+min(c.salary)+from+cubatrainingtask$Employee+c)";
        String view = "employee-view";

        StringBuilder url = new StringBuilder("");
        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);

        HttpGet request = new HttpGet(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        HttpResponse response = client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);

        JsonParser jsonParser = new JsonParser();
        JsonArray obj = (JsonArray) jsonParser.parse(s);
        System.out.print(JsonCustomParserHelper.jsonToStringMinSalaryService(obj));
    }

    public void getMaxCustomersService(HttpClient client) throws IOException {
        String entity = "cubatrainingtask$CarServiceCenter";
        String query = "select+c+from+cubatrainingtask$CarServiceCenter+c";
        String view = "carServiceCenter-view";
        StringBuilder url = new StringBuilder("");

        url.append(ADDRESS);
        url.append("query.json?e=");
        url.append(entity);
        url.append("&q=");
        url.append(query);
        url.append("&s=");
        url.append(this.getSession(client));
        url.append("&view=");
        url.append(view);

        HttpGet request = new HttpGet(url.toString());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept","application/json");

        JsonObject jsObject;
        HttpResponse response = client.execute(request);

        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity);
        System.out.print(s+"\n");
        JsonParser jsonParser = new JsonParser();

        JsonArray obj = (JsonArray) jsonParser.parse(s);
        JsonObject biggestService = null;
        JsonArray customers;
        int maxSize = 0;
        for(int i = 0; i< obj.size();i++){
            jsObject = (JsonObject) obj.get(i);
            customers = (JsonArray) jsObject.get("customers");
            if(customers.size() > maxSize){
                maxSize = customers.size();
                biggestService = jsObject;
            }
        }

        System.out.print(JsonCustomParserHelper.jsonToStringMaxServiceParser(biggestService, maxSize));
    }
}



