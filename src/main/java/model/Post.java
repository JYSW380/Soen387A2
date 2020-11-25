package model;
import java.io.Serializable;
import java.time.LocalTime;

public class Post implements Serializable{


    private int id;
    private String userName;
    private LocalTime timeStamp;
    private String hashTag;
    private String message;
    private boolean update;
    private LocalTime updateTime;
    private String group;

    public Post(){
    }

    public Post(String user, String wholeMessage,String group) {
        this.userName = user;
        this.timeStamp = LocalTime.now();
        if(wholeMessage.contains("edit")){
            wholeMessage= wholeMessage.substring(0, wholeMessage.indexOf("edit"));
        }
        setWholeMessage(wholeMessage);
        this.update =false;
        this.updateTime = null;
        if(group ==null || group.equals("")){
            this.group = "public";
        }
        else{
            this.group=group;
        }
    }
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String user) {
        this.userName = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }



    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message=message;
    }
    public void setWholeMessage(String wholeMessage) {

        if (!wholeMessage.equals("")){
            String [] arr= wholeMessage.split("#");
            this.message=arr[0];
            if(!wholeMessage.equals(message)){
                this.hashTag=arr[1];
            }
            else{
                this.hashTag="";
            }
        }
        else{
            this.message="";
            this.hashTag="";
        }
    }
    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public LocalTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        String mes = message.equals("")?" ":message;
        String hTag= (hashTag==null || hashTag.equals(""))?"":(" #"+hashTag);
        String upTime=updateTime==null?" ":  (" edit:"+updateTime.toString());
        return (mes + hTag+ upTime);


    }
}
