package Helper;

import model.UserManagerJson;
import org.apache.commons.io.FilenameUtils;

/**
 * factory to get the userManager instance
 * the instance would be depend on the path to file
 * if it is end with Json call the concreteclass of type userMangerInterface that responsible for hanlding json
 * it is easy to add new class handle the new type of the userMangerInterface concrete class
 */
public class UserManager {
    public UserManagerInterface getUserManager(String pathToFileUser, String pathToFileGroup, String pathtoFileMembership){
        if(pathToFileUser.equalsIgnoreCase("")||pathToFileUser==null){
            //pathToFileUser = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\allUser.json";
            pathToFileUser = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\allUser.json";
        }
        if(pathToFileGroup.equalsIgnoreCase("")||pathToFileGroup ==null){
            //pathToFileGroup = "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\group.json";
            pathToFileGroup = "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\group.json";
        }
        if(pathtoFileMembership.equalsIgnoreCase("")||pathtoFileMembership ==null){
            //pathtoFileMembership= "C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\membership.json";
            pathtoFileMembership= "C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\membership.json";
        }
        String className =  FilenameUtils.getExtension(pathToFileUser);
        UserManagerInterface userManagerInterface=null;
        switch (className.toLowerCase()){
            case "json":
                userManagerInterface = UserManagerJson.getInstance(pathToFileUser, pathToFileGroup,pathtoFileMembership);
                break;
            case "xml":
                // return
                break;
            default:
               userManagerInterface = UserManagerJson.getInstance(pathToFileUser, pathToFileGroup,pathtoFileMembership);
               break;
        }
        return userManagerInterface;
    }

}
