/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertictactoe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author amr
 */
@Entity // make this class as table in DB.
@Table(name = "signup")   // if i want to change name ot table in database instead of SignUp to Registration
public class SignUp {

    private String mail="", password;

    @Id // make this property as primary key in table.
    // @GeneratedValue(strategy = GenerationType.TABLE) // to make id increment auto , but we will not use it cuz it's not number.
    @Column(name = "mail", length = 255, nullable = false, unique = true)  // if i want to change name of mail to e-mail
    // and put some constraints.
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

