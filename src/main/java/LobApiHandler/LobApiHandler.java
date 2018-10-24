package LobApiHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import models.Partner;

import java.io.*;
import java.util.*;

public class LobApiHandler {

    static LinkedHashMap<String, Integer> mp = new LinkedHashMap<String, Integer>();
    static HashMap<String, ArrayList<Partner>> mp1 = new HashMap();
    static HashMap<String, ArrayList<String>> mp2 = new HashMap();

    public static void main(String[] ignored) throws Exception {
        processPartners();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i=1;i<=4;i++) {
            Order[] orders = objectMapper.readValue(new File("src/main/java/JsonInputFiles/orders"+i+".json"), Order[].class);
            for (Order order: orders) {
                if (!order.getDeliverability().equals("undeliverable")) {
                    processOrder(order);
                }
            }
            processOutput(i);
            mp2.clear();
        }
    }


    /* Here we process partners to map orders to partners */
    private static void processPartners() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Partner[] partners = objectMapper.readValue(new File("src/main/java/JsonInputFiles/partners.json"), Partner[].class);
        for (Partner p: partners) {
            mp.put(p.getId(), p.getCapacity());
        }

        //put partners according to resource
        for (Partner p: partners) {
            ArrayList<Partner> list = new ArrayList<Partner>();
            if (mp1.get(p.getResource()) != null) {
                list = mp1.get(p.getResource());
                list.add(p);
                mp1.put(p.getResource(), list);
            } else {
                list.add(p);
                mp1.put(p.getResource(), list);
            }
        }
    }

    /* Here we process order as it comes and map it to the desired partner */
    private static void processOrder(Order order) {
        double min_distance = Integer.MAX_VALUE;
        String resource = order.getResource();
        String type = order.getType();
        ArrayList<Partner> list = new ArrayList<Partner>();
        ArrayList<Partner> feasible_partners = new ArrayList<Partner>();
        list = mp1.get(resource);
        if (list != null) {
            for (Partner p: list) {
                if (p.getType().contains(type)) {
                    feasible_partners.add(p);
                }
            }
        }

        String partner_assigned = "";
        if (feasible_partners != null) {
            if (feasible_partners.size() == 1) {
                makeOrdersPartnersMapping(feasible_partners.get(0).getId(), order.getId());
            } else {
                double distance = 0;
                /*calculate nearest partner for the order*/
                for (Partner p: feasible_partners) {
                    distance = calculateDistance(p.getAddress().getLatitude(), order.getAddress().getLatitude(),p.getAddress().getLongitude()
                    ,order.getAddress().getLongitude());
                    if (distance < min_distance) {
                        min_distance = distance;
                        partner_assigned = p.getId();
                    }
                }
                makeOrdersPartnersMapping(partner_assigned, order.getId());
            }
        }
    }


    private static void makeOrdersPartnersMapping(String partner, String orderId) {
       if (partner != null && orderId != null) {
           ArrayList<String> orders = mp2.get(partner);
           if (orders != null) {
               orders.add(orderId);
           } else {
               mp2.put(partner, new ArrayList<String>(Arrays.asList(orderId)));
           }
       }
    }

    private static void processOutput(int i) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("src/main/java/Output//output"+i+".txt"));
        for (Map.Entry<String, Integer> entry: mp.entrySet()) {
            out.println(entry.getKey());
            ArrayList<String> list = new ArrayList<String>();
            list = mp2.get(entry.getKey());
            if (list != null) {
                for (String str : list) {
                      out.println(str);
                }
            }
        }
        System.setOut(out);
        out.close();
    }


    private static double calculateDistance(Double lat1, Double lat2, Double lon1, Double lon2) {
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c;
    }
}
