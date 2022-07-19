package ca.T3.fab4.it.smart.home.controller;

public class T3UserInfoDatabase {

    //Creating string variables to save user info
    private String userName,userEmail,userPhone,userComment;

    //Class constructor
    public T3UserInfoDatabase(){}

    //User info getters and setters
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

        public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

        public String getUserPhone(){
        return userPhone;
    }
    public void setUserPhone(String userPhone){
        this.userPhone = userPhone;
    }

        public String getUserCmnt(){
        return userComment;
    }
    public void setUserCmnt(String userComment){
        this.userComment = userComment;
    }


}
