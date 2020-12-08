package model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserManagerJsonTest {

    @Test
    void initAllUser() {
        // Json file tests
//        String pathtoFileMembership = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\membership-test.json";
//        String pathToFileGroup = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\group-test.json";
//        String pathToFileUser = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\allUser-test.json";

        String pathToFileUser = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\allUser.json";
        String pathToFileGroup = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\group.json";
        String pathtoFileMembership= "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\membership.json";

        // instantiate with right paths to json file tests
        UserManagerJson testUserManagement = UserManagerJson.getInstance(pathToFileUser, pathToFileGroup, pathtoFileMembership);

        List<User> allUserList = new ArrayList<>();

        allUserList.add(new User("john", "john@concordia.ca", "a51dda7c7ff5b61eaea444371f4a6a931e51"));
        allUserList.add(new User("jane", "jane@concordia.ca", "8a8deed44623d4c44268c26652d8945851c4f7f"));
        allUserList.add(new User("jack123", "jack123@concordia.ca", "596727c8a0ea4db3ba2ceceedccbacd3d7b371b8"));

        System.out.println("=========================================================");
        System.out.println(allUserList);
        System.out.println(testUserManagement.getAllUser());

        assertEquals(allUserList, testUserManagement.getAllUser());
    }

    @Test
    void initAllGroup() {
        // Json file tests
//        String pathtoFileMembership = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\membership-test.json";
//        String pathToFileGroup = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\group-test.json";
//        String pathToFileUser = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\allUser-test.json";

        String pathToFileUser = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\allUser.json";
        String pathToFileGroup = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\group.json";
        String pathtoFileMembership= "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\membership.json";

        // instantiate with right paths to json file tests
        UserManagerJson testUserManagement = UserManagerJson.getInstance(pathToFileUser, pathToFileGroup, pathtoFileMembership);

        // create Tree Map
        Map<String, Set<String>> groupMember = new TreeMap<>();
        // create Set
        Set<String> userSet1 = new HashSet<String>();
        Set<String> userSet2 = new HashSet<String>();
        Set<String> userSet3 = new HashSet<String>();

        // order them
        Set myOrderedSet1 = new LinkedHashSet(userSet1);
        Set myOrderedSet2 = new LinkedHashSet(userSet2);
        Set myOrderedSet3 = new LinkedHashSet(userSet3);


        myOrderedSet1.add("");
        groupMember.put("admins", myOrderedSet1);
        myOrderedSet2.add("encs");
        groupMember.put("concordia", myOrderedSet2);
        myOrderedSet3.add("comp");
        myOrderedSet3.add("soen");
        groupMember.put("encs", myOrderedSet3);

        System.out.println("=========================================================");
        System.out.println(groupMember);
        System.out.println(testUserManagement.getAllGroup());

        assertEquals(groupMember, testUserManagement.getAllGroup());
    }

    @Test
    void initMemberShip() {
        // Json file tests
//        String pathtoFileMembership = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\membership-test.json";
//        String pathToFileGroup = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\group-test.json";
//        String pathToFileUser = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\allUser-test.json";

        String pathToFileUser = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\allUser.json";
        String pathToFileGroup = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\group.json";
        String pathtoFileMembership= "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\membership.json";

        // instantiate with right paths to json file tests
        UserManagerJson testUserManagement = UserManagerJson.getInstance(pathToFileUser, pathToFileGroup, pathtoFileMembership);


        //assertEquals(groupMember, testUserManagement.getAllUserGroup());
    }
}