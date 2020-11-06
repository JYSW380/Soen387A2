import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<JSONObject> all = new ArrayList<>();
        JSONObject obj = new JSONObject();
        User user = new User();
        user.setUsername("marry");
        user.setPwd("1234");
        obj.put("name", user.getUsername());
        obj.put("pw", user.getPwd());
        all.add(obj);

        obj = new JSONObject();
        user = new User();
        user.setUsername("tom");
        user.setPwd("3421");
        obj.put("name", user.getUsername());
        obj.put("pw", user.getPwd());
        all.add(obj);

        obj = new JSONObject();
        user = new User();
        user.setUsername("mike");
        user.setPwd("3874");
        obj.put("name", user.getUsername());
        obj.put("pw", user.getPwd());
        all.add(obj);




        JSONArray list = new JSONArray();
        for(JSONObject o : all){
            list.add(o);
        }
//list.add ("java");
//list.add("python");
//list.add("servlet");

//obj.put("course",list);

        try(FileWriter file = new FileWriter("myJson.json")){
            file.write(list.toJSONString());
            file.flush();
        }catch (IOException o) {
            o.printStackTrace();
        }
        System.out.println(obj);

    }
}
