package model;

import Helper.UserManagerInterface;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * singleteon pattern
 * to get the single instance of the usermanagerJson
 * to read the file from json
 */
public class UserManagerJson implements UserManagerInterface {
    private List<User> allUser;
    private Map<String, ArrayList<String>> allGroup;
    private Map<String, Set<String>> userMembership;

    private static UserManagerJson userManager;

    private UserManagerJson(String pathToFileUser, String pathToFileGroup, String pathtoFileMembership){
        this.allUser = initAllUser(pathToFileUser);
        this.allGroup = initAllGroup(pathToFileGroup);
        this.userMembership = initMemberShip(pathtoFileMembership);
    }

    public static UserManagerJson getInstance(String pathToFileUser, String pathToFileGroup, String pathtoFileMembership){
        if(userManager ==null){
            synchronized (UserManagerJson.class){
                if(userManager == null){
                    userManager = new UserManagerJson(pathToFileUser, pathToFileGroup,pathtoFileMembership);
                }
            }
        }
        return userManager;
    }
    public String readFromFile(){
        String jsonString="";
        try{
//change the path
            File file = new File("C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\allUser.json");
            Scanner readFile = new Scanner(file);
            while(readFile.hasNextLine()){
                jsonString +=readFile.nextLine()+"\r\n";
            }
            readFile.close();
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The file can not be found.");
        }
        return null;
    }
    private List<User> initAllUser(String pathToFileUser){
        JSONArray allUser = null;
        try {
            allUser = (JSONArray) new JSONParser().parse(new FileReader(pathToFileUser));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<User> allUserList = new ArrayList<>();
        for(Object o : allUser){
            Map<String, String> u = (Map) o;
            allUserList.add(new User(u.get("username"), u.get("pwd"), u.get("email")));
        }
        return allUser;
    }
    private Map<String, ArrayList<String>> initAllGroup(String pathToFileGroup){
        JSONArray group = null;
        try {
            group = (JSONArray) new JSONParser().parse(new FileReader(pathToFileGroup));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return groupMap;
    }
    private Map<String, Set<String>> initMemberShip(String pathToFileMembership){
        JSONArray membership = null;
        try {
            membership = (JSONArray) new JSONParser().parse(new FileReader(pathToFileMembership));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                    allGroup.forEach((k,v)->{
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
                if(allGroup.containsKey(s)){
                    ArrayList<String> children = allGroup.get(s);
                    for(String c: children){ // add child one by one
                        userSet.add(c);
                    }
                }
            }
            groupMember.put(u.get("name"), userSet);
        }
        return groupMember;
    }

    @Override
    public List<User> getAllUser() {
        return allUser;
    }

    @Override
    public Map<String, ArrayList<String>> getAllGroup() {
        return allGroup;
    }

    @Override
    public Set<String> getGroup(String name) {
        return userMembership.get(name);
    }

    @Override
    public Map<String, Set<String>> getAllUserGroup() {
        return userMembership;
    }
}
