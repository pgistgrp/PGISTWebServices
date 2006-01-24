package org.pgist.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory factory;
    private static final ThreadLocal session = new ThreadLocal();
    private static final ThreadLocal transaction = new ThreadLocal();
    
    
    public static Session getSession() throws Exception {
        Session s = (Session) session.get();

        if (s==null) {
            try {
                if (factory == null) {
                    try {
                        Context context = new InitialContext();
                        factory = (SessionFactory) context.lookup("SessionFactory");
                    } catch (NamingException e) {
                        factory = new Configuration().configure().buildSessionFactory();
                    }
                }
                s = factory.openSession();
            } catch (HibernateException e) {
                throw e;
            }

            session.set(s);
        }
        return s;
    }
    

    public static void closeSession() throws Exception {
        Session s = (Session) session.get();
        session.set(null);
        transaction.set(null);

        if (s != null) {
            s.close();
        }
    }
    
    
    public static void begin() throws Exception {
        Session s = getSession();

        Transaction t = s.beginTransaction();
        transaction.set(t);
    }
    
    
    public static boolean commit() throws Exception {
        Session s = (Session) session.get();
        Transaction t = (Transaction) transaction.get();

        if (s == null || t == null) {
            return false;
        } else {
            t.commit();
        }
        
        return true;
    }


    public static boolean rollback() throws Exception {
        Session s = (Session) session.get();
        Transaction t = (Transaction) transaction.get();

        if (s == null || t == null) {
            return false;
        } else {
            t.rollback();
        }

        return true;
    }


}
