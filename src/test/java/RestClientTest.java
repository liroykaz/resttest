
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import static junit.framework.Assert.*;
import java.io.IOException;

/**
 * Created by lebedev on 07.04.2017.
 */

public class RestClientTest {

    private HttpClient getClient(){
        HttpClient client = HttpClientBuilder.create().build();
        return client;
    }

    HttpClient client = this.getClient();

    private RestClient restClient = new RestClient();
    @Test
    public void testGetSession(){
        String session = restClient.getSession(client);
        assertNotNull(session);
    }

    @Test
    public void testCommitService(){
        try {
            restClient.commitService("TestName", "Chapaevsk", "123124", client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetServices(){
        try {
            restClient.getServices(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRepairs(){
        try {
            restClient.getRepairs(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCommitRepair(){
        try {
            restClient.commitRepair("Repairing Engine", client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetServiceWithCustomers(){
        try {
            restClient.getServicesWithCust(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMaxSalaryEmpl(){
        try {
            restClient.getMaxSalaryEmpl(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMinSalaryService(){
        try {
            restClient.getMinSalaryService(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMaxCustomersService(){
        try {
            restClient.getMaxCustomersService(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
