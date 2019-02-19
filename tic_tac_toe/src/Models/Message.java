
package Models;

import java.io.Serializable;

/**
 *
 * @author Bassant Sayed
 */
public class Message implements Serializable{
    private Client sender;
    private String reciever;
    private String msg;
    private String Type;
    private boolean isValidUser=false;

    public Message() {
        this.sender=new Client();
        this.reciever = "";
        this.msg ="";
        this.Type = "";
        this.isValidUser = false;
    }

    public Message(Client sender, String reciever, String msg, String Type, boolean isValidUser) {
        this.sender = sender;
        this.reciever = reciever;
        this.msg = msg;
        this.Type = Type;
        this.isValidUser = isValidUser;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public boolean isIsValidUser() {
        return isValidUser;
    }

    public void setIsValidUser(boolean isValidUser) {
        this.isValidUser = isValidUser;
    }
}
