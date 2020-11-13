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

    public Post(){
        this.hashTag="test";
    }

    public Post(String user, String wholeMessage) {
        this.userName = user;
        this.timeStamp = LocalTime.now();
        if(wholeMessage.contains("edit")){
            wholeMessage= wholeMessage.substring(0, wholeMessage.indexOf("edit"));
        }
        setWholeMessage(wholeMessage);
        update =false;
        updateTime = null;
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

        if (wholeMessage !=null){
            String [] arr= wholeMessage.split("#");
            this.message=arr[0];
            if(!wholeMessage.equals(message)){
                this.hashTag=arr[1];
            }

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
        String mes = message==null?"":message;
        String hTag= (hashTag==null || hashTag.equals(""))?"":("#"+hashTag);
        String upTime=updateTime==null?"":  (" edit:"+updateTime.toString());
        return (mes + hTag+ upTime);

    }
}
