import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class test {
    public static void main(String[] args) throws IOException {
//        User user1 = new User("john", "john@concordia.ca", "john");
//        User user2 = new User("jane", "jane@concordia.ca", "jane");
//        User user3 = new User("jack123", "jack123@concordia.ca", "jack");
//
//        try {
//            ArrayList<User> allUser = new ArrayList<>();
//            File file = new File("allUser.json");
//            PrintWriter pw = null;
//            allUser.add(user1);
//            allUser.add(user2);
//            allUser.add(user3);
//            pw = new PrintWriter(file);
//            pw.print(new Gson().toJson(allUser));
//            pw.flush();
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String jsonString="";
//        try{
//            File file = new File("allUser.json");
//            Scanner readFile = new Scanner(file);
//            while(readFile.hasNextLine()){
//                jsonString +=readFile.nextLine();
//            }
//            readFile.close();
//            System.out.println(jsonString);
//        } catch (Exception e) {
//            System.out.println("The file can not be found.");
//        }


//        ArrayList<JSONObject> all = new ArrayList<>();
//        JSONObject obj = new JSONObject();
//        User user = new User();
//        user.setUsername("marry");
//        user.setPwd("1234");
//        obj.put("name", user.getUsername());
//        obj.put("pw", user.getPwd());
//        all.add(obj);
//
//        obj = new JSONObject();
//        user = new User();
//        user.setUsername("tom");
//        user.setPwd("3421");
//        obj.put("name", user.getUsername());
//        obj.put("pw", user.getPwd());
//        all.add(obj);
//
//        obj = new JSONObject();
//        user = new User();
//        user.setUsername("mike");
//        user.setPwd("3874");
//        obj.put("name", user.getUsername());
//        obj.put("pw", user.getPwd());
//        all.add(obj);
//
//
//
//
        JSONArray list = new JSONArray();
//        for(JSONObject o : all){
//            list.add(o);
//        }
////list.add ("java");
////list.add("python");
////list.add("servlet");
//
////obj.put("course",list);
////
//        try(FileWriter file = new FileWriter("myJson.json")){
//            file.write(list.toJSONString());
//            file.flush();
//        }catch (IOException o) {
//            o.printStackTrace();
//        }
//        System.out.println(obj);
        JSONArray group = null;
        try {
            group = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\group.json"));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,ArrayList<String>> groupMap = new TreeMap<>();
        for(Object o : group){
            Map<String, String > u = (Map) o;
            if(u.get("parent") == null){
                if(!groupMap.containsKey(u.get("name"))){
                    groupMap.put(u.get("name"), new ArrayList<>());
                }
            }
            else{
                if(groupMap.containsKey(u.get("parent"))){
                    String key =u.get("parent");
                    groupMap.get(key).add(u.get("name"));
                }
                else{
                    groupMap.put(u.get("parent"), new ArrayList<>());
                    groupMap.get(u.get("parent")).add(u.get("name"));
                }

            }
        }
//        groupMap.forEach((k,v)->{
//            System.out.println(k);
//            System.out.println(v);
//        });
        JSONArray membership = null;
        try {
            membership = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\membership.json"));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Set<String>> groupMember = new TreeMap<>();
        for(Object o : membership) {
            Map<String, String> u = (Map) o;
            String [] userGroup= u.get("group").split(",");
            Set<String> userSet = new HashSet<String>();
            for(String s : userGroup){
                if(s.equalsIgnoreCase("admins")){
                    //add every thing
                    groupMap.forEach((k,v)->{
//                        System.out.println(k);
                        userSet.add(k);
                        for(String ch:v){
//                            System.out.println(ch);
                            userSet.add(ch);
                         }
                    });
                    userSet.add(s);
                    continue;
                }
                userSet.add(s);
                if(groupMap.containsKey(s)){
                    ArrayList<String> children = groupMap.get(s);
                    for(String c: children){ // add child one by one
                        userSet.add(c);
                    }
                }
            }
            groupMember.put(u.get("name"), userSet);
        }
        // after you add should you repeat to add another

//        groupMember.forEach((k,v)->{
//            for(String s:v){ // iterate through the group
//                if(groupMap.containsKey(s)){ // if it is the parent add the child group too
//                    // group is the parent add all the children to membership group too
//
//                    ArrayList<String> children = groupMap.get(s);
//                    for(String c: children){ // add child one by one
//                        if(!v.contains(c)){
//                            groupMember.get(k).add(c);
//                        }
//                    }
//                }
//            }
//        });
        groupMember.forEach((k,v)->{
            System.out.println(k);
            System.out.println(v);
//            for(String s:v){
//                System.out.println(s.trim());
//            }
        });


//        group.forEach((k,v)->{
//            System.out.println(k);
//            System.out.println(v);
//        });

    }
}
