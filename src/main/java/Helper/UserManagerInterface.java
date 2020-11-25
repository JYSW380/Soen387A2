package Helper;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserManagerInterface {

    public List<User> getAllUser();
    public Map<String, ArrayList<String>> getAllGroup();
    public Set<String> getGroup(String name);
    public Map<String, Set<String>> getAllUserGroup();

}
