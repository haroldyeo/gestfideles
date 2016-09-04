package com.utils;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import model.Fidele;
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
                       Criteria crUsers = HibernateUtil.getHibSession().createCriteria(User.class);
                       crUsers.addOrder(Order.asc("id"));
                       		if(mapParams != null){
                       			
                       			Integer id =  mapParams.get(Constants.id) != null ? (Integer) (mapParams.get(Constants.id)) : null; 
                           	   String nom = (String)mapParams.get(Constants.nom);
                          	   String prenoms = (String)mapParams.get(Constants.prenoms);
                          	   String identifiant = (String)mapParams.get(Constants.identifiant);
	                          	 String motPasse = (String)mapParams.get(Constants.mdp);
                           	   
                           	   
                       			if ( id!= null ){
                                         crUsers.add(Restrictions.eq("id", id));
                                     }                    			

                                if (nom != null && !nom.equals("")){
                                     	crUsers.add(Restrictions.ilike("nom", "%"+nom+"%"));
                                }

                                if (prenoms != null && !prenoms.equals("")){
                                     	crUsers.add(Restrictions.ilike("prenoms", "%"+prenoms+"%"));
                                }
                                
                                if (identifiant != null && !identifiant.equals("")){
                                 	crUsers.add(Restrictions.eq("identifiant", identifiant));
                                }
                                
                                if (motPasse != null && !motPasse.equals("")){
                                 	crUsers.add(Restrictions.eq("motPasse", motPasse));
                                }
                                        					
                       		} // end if params != null

                       		returnedList = crUsers.list();
                        break;
            	case(Constants.fideles):
            	
            	Criteria crFideles = HibernateUtil.getHibSession().createCriteria(Fidele.class);
            	crFideles.addOrder(Order.asc("id"));
            		if(mapParams != null){
            			
            			Integer id =  mapParams.get(Constants.id) != null ? (Integer) (mapParams.get(Constants.id)) : null; 
                    	String nom = (String)mapParams.get(Constants.nom);
                   	    String prenoms = (String)mapParams.get(Constants.prenoms);
               	        Date dob = (Date)mapParams.get(Constants.dob);
                	   
                	   
            			if ( id!= null ){
            				crFideles.add(Restrictions.eq("id", id));
                          }                    			

                     if (nom != null && !nom.equals("")){
                    	 crFideles.add(Restrictions.ilike("nom", "%"+nom+"%"));
                     }

                     if (prenoms != null && !prenoms.equals("")){
                    	 crFideles.add(Restrictions.ilike("prenoms", "%"+prenoms+"%"));
                     }
                     
                     if (dob != null){
                    	 crFideles.add(Restrictions.eq("dob", dob));
                     }
            	}	// end if params != null
           
            	returnedList = crFideles.list();
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
