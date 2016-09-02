package com.utils;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import model.User;

/**
 *
 * @author Harold
 */
@SuppressWarnings("rawtypes")
public class OperationsDb {
	
    
	public static List find (String strEntity, Map<String,Object> mapParams){
        
		List returnedList = null;
        
        switch(strEntity){
            
            case(Constants.users):
                       Criteria criteria = HibernateUtil.getHibSession().createCriteria(User.class);
                       criteria.addOrder(Order.asc("id"));
                       		if(mapParams != null){
                       			
                       			Integer id =  mapParams.get("id") != null ? (Integer) (mapParams.get("id")) : null; 
                           	   String nom = (String)mapParams.get("nom");
                          	   String prenoms = (String)mapParams.get("prenoms");
                          	   String identifiant = (String)mapParams.get("identifiant");
	                          	 String motPasse = (String)mapParams.get("motPasse");
                           	   
                           	   
                       			if ( id!= null ){
                                         criteria.add(Restrictions.eq("id", id));
                                     }                    			

                                if (nom != null && !nom.equals("")){
                                     	criteria.add(Restrictions.ilike("nom", "%"+nom+"%"));
                                }

                                if (prenoms != null && !prenoms.equals("")){
                                     	criteria.add(Restrictions.ilike("prenoms", "%"+prenoms+"%"));
                                }
                                
                                if (identifiant != null && !identifiant.equals("")){
                                 	criteria.add(Restrictions.eq("identifiant", identifiant));
                                }
                                
                                if (motPasse != null && !motPasse.equals("")){
                                 	criteria.add(Restrictions.eq("motPasse", motPasse));
                                }
                                        					
                       		}

                       		returnedList = criteria.list();
                        break;
           
        }
        
        System.out.println("list for keyword: "+strEntity +" -  size: "+returnedList.size());
        return returnedList;
    }
    
  
  public static void persistObject(Object obj){
       
          try{
              Session session = HibernateUtil.getHibSession();
              session.beginTransaction();
              session.save(obj);
              session.getTransaction().commit();
              session.close();
              
          } catch (HibernateException e){
              e.printStackTrace();
              
          }
  }
  
  public static void updateObject(Object obj){
       
          try{
              Session session = HibernateUtil.getHibSession();
              session.beginTransaction();
              session.merge(obj);  
              session.getTransaction().commit();
              session.close();
              
          } catch (HibernateException e){
              e.printStackTrace();
              
          }
  }
  
  public static void deleteById(Class<?> type, Serializable id) {
       
          try{
        	  Session session = HibernateUtil.getHibSession();
        	  Object persistentInstance = session.get(type, id);
        	  if (persistentInstance != null) {
        		  session.beginTransaction();
        	      session.delete(persistentInstance);
        	      session.getTransaction().commit();
                  session.close();
        	  }
        	  
            
//              session.beginTransaction();
//              session.delete(obj);  
//              session.getTransaction().commit();
//              session.close();
              
          } catch (HibernateException e){
              e.printStackTrace();
              
          }
  }
  
    
}
