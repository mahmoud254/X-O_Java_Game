
package Models;

import java.io.Serializable;

/**
 *
 * @author Bassant Sayed
 */
public class Client implements Serializable{
    private String email;
    private String password;

    public Client() {
    }

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}