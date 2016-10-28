/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class HibernateUtil {

	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;
    
    public static Session getHibSession(){
		entityManagerFactory = Persistence.createEntityManagerFactory("gestFideles");
		entityManager = entityManagerFactory.createEntityManager();
//		Query q = entityManager.createNativeQuery("");	
//		q.getResultList();
		return (Session) entityManager.getDelegate();
	}
    
}
