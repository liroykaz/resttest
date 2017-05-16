import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Scanner;

/**
 * Created by lebedev on 04.04.2017.
 */
public class Main {



    public static void main(String[] args) throws Exception {
        HttpClient client = getClient();
        while(true) {
            Scanner in = new Scanner(System.in);
            RestClient http = new RestClient();
            StringBuilder sb = new StringBuilder("");
            sb.append("===============Rest Client================ \n");
            sb.append(" Выбирите пункт для выполнения \n");
            sb.append(" 1. Вывод всех автосервисов \n");
            sb.append(" 2. Создать автосервис \n");
            sb.append(" 3. Вывести все ремонты, проводимые в сервисах \n");
            sb.append(" 4. Создать ремонт \n");
            sb.append(" 5. Вывод всех автосервисов с клиентами \n");
            sb.append(" 6. Сотрудник с максимальной заработной платой\n");
            sb.append(" 7. Сервис с минимальной заработной платой\n");
            sb.append(" 8. Сервис с наибольшим кол-вом клиентов\n ");
            sb.append("0. Выход\n Ваш выбор: ");
            System.out.print(sb);
            int change = in.nextInt();
            switch (change){
                case 1: {
                    http.getServices(client);
                    break;
                }
                case 2: {
                    System.out.println("Введите название автосервиса");
                    String name = in.next();
                    System.out.println("Введите адрес автосервиса");
                    String address = in.next();
                    System.out.println("Введите номер автосервиса");
                    String phone = in.next();
                    http.commitService(name, address, phone, client);
                    break;
                }
                case 3:{
                    System.out.println("Ремонты:");
                    http.getRepairs(client);
                    break;
                }
                case 4: {
                    System.out.println("Укажите описание сервиса:");
                    String desc = in.next();
                    http.commitRepair(desc, client);
                    break;
                }
                case 5:{
                    http.getServicesWithCust(client);
                    break;
                }
                case 6:{
                    http.getMaxSalaryEmpl(client);
                    break;
                }
                case 7:{
                    http.getMinSalaryService(client);
                    break;
                }
                case 8:{
                    http.getMaxCustomersService(client);
                    break;
                }
                case 0: {
                    System.exit(0);
                }

            }

        }

    }

    private static org.apache.http.client.HttpClient getClient(){
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        return client;
    }
}
