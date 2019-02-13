/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x_o_game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author amr
 */
public class Crud {

    public void insert(SignUp s) {
        Session se = NewHibernateUtil.getSessionFactory().openSession();
        try {
            se.beginTransaction();
            se.save(s);
            se.getTransaction().commit();
        } catch (HibernateException e) {
            se.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            se.close();
        }

    }

//    public SignUp select(String str) {
//
//        Session se = NewHibernateUtil.getSessionFactory().openSession();
//        SignUp s = new SignUp();
//        try {
//
//            se.beginTransaction();
//            s.setMail("");
//            s = (SignUp) se.get(SignUp.class, str);
//            se.getTransaction().commit();
//
//        } catch (HibernateException e) {
//            se.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
//            se.close();
//            return s;
//        }
//
//    }
    public boolean select1(String str) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();

        boolean flag = false;
        try {
            Criteria q = se.createCriteria(SignUp.class);
            List<SignUp> al = q.list();
            for (SignUp s : al) {
                System.out.println("first " + s.getMail());
                if (str.equalsIgnoreCase(s.getMail())) {
                    System.out.println("if " + s.getMail());
                    flag = true;
                    break;
                }
            }

        } catch (HibernateException e) {
            se.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            se.close();
            return flag;
        }

    }

    public boolean select2(String mail, String passwd) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();

        boolean flag = false;
        try {
            Criteria q = se.createCriteria(SignUp.class);
            List<SignUp> al = q.list();
            for (SignUp s : al) {
                System.out.println("first " + s.getPassword());
                if (passwd.equalsIgnoreCase(s.getPassword()) && mail.equalsIgnoreCase(s.getMail())) {
                    System.out.println("mail is " + s.getMail() + " passwd is" + s.getPassword());
                    flag = true;
                    break;
                }
            }

        } catch (HibernateException e) {
            se.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            se.close();
            return flag;
        }

    }

    public void update(SignUp s) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();
        try {

            se.beginTransaction();
            se.update(s);
            System.out.println("The update done");
            se.getTransaction().commit();
        } catch (HibernateException e) {
            se.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            se.close();
        }

    }

    public void delete(SignUp s, String str) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();
        try {

            se.beginTransaction();
            s = (SignUp) se.get(SignUp.class, str);
            se.delete(s);
            System.out.println("The delete done");
            se.getTransaction().commit();
        } catch (HibernateException e) {
            se.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            se.close();
        }

    }

}
