
package x_o_game;

import java.io.Serializable;

/**
 *
 * @author Bassant Sayed
 */
public class Message implements Serializable{
    int senderId;
    int recieverId;
    String msg;
    boolean isConnectrequest;
    private String email;
    private String password;
    private boolean isValidUser;
    String type;
    public boolean isIsValidUser() {
        return isValidUser;
    }

    public void setIsValidUser(boolean isValidUser) {
        this.isValidUser = isValidUser;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isConnectrequest() {
        return isConnectrequest;
    }

    public void setIsConnectrequest(boolean isConnectrequest) {
        this.isConnectrequest = isConnectrequest;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
}
