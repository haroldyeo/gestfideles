package com.utils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
                       			
                       			BigDecimal id = mapParams.get("id") != null ? new BigDecimal((String)mapParams.get("id")) : null; 
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
           
            
           
                
            /*case("cuo"):

               Criteria CuoCr = HibernateUtil.getHibSession().createCriteria(CUO.class);
            		CuoCr.addOrder(Order.asc("masterId"));
               		if(mapParams != null){
               		   String phone = (String)mapParams.get("phone");
               		   BigDecimal masterId = mapParams.get("masterId") != null ? new BigDecimal((String)mapParams.get("masterId")) : null; 
                   	   Date dateCreation = (Date)mapParams.get("dateCreation");
                   	   Date dateCessation = (Date)mapParams.get("dateCessation");
                  	   String typeMatch = (String)mapParams.get("typeMatch");
                  	   BigDecimal typeservice = mapParams.get("typeservice") != null ? new BigDecimal((String)mapParams.get("typeservice")) : null;
                   	   
                   	   
                  	 if (phone != null && !phone.equals("")){
                      	CuoCr.add(Restrictions.ilike("phone", "%"+phone+"%"));
                  	 }
               			if ( masterId!= null ){
                                 CuoCr.add(Restrictions.eq("masterId", masterId));
                             }  
               			if ( typeservice!= null ){
                            CuoCr.add(Restrictions.eq("typeservice", typeservice));
                        }

                        if (dateCreation != null){
                             	CuoCr.add(Restrictions.eq("dateCreation", dateCreation));
                        }
                        
                        if (dateCessation != null){
                         	CuoCr.add(Restrictions.eq("dateCessation", dateCessation));
                    }

                        if (typeMatch != null && !typeMatch.equals("")){
                             	CuoCr.add(Restrictions.ilike("typeMatch", "%"+typeMatch+"%"));
                        }			
               		}

               		returnedList = (List<TUsers>)CuoCr.list();
                break;
                
                
            case("lss"):

                Criteria LssCr = HibernateUtil.getHibSession().createCriteria(LSS.class);
        		LssCr.addOrder(Order.asc("idclient"));
                		if(mapParams != null){
                			Integer idclient = (Integer)mapParams.get("idclient");
                		   String nom = (String)mapParams.get("nom");
                		   String prenom = (String)mapParams.get("prenom");
                		   String datnai = (String)mapParams.get("datnai");
                		   String lieunai = (String)mapParams.get("lieunai");
                		   String pieceidentite = (String)mapParams.get("pieceidentite");
                		   String description = (String)mapParams.get("description");
                		   String typePiece = (String)mapParams.get("typePiece");
                		   String adresspostal = (String)mapParams.get("adresspostal");
                		   String phone = (String)mapParams.get("phone");
                		   String cpostalBp = (String)mapParams.get("cpostalBp");
                		   String numerobp = (String)mapParams.get("numerobp");
                		   String refClientExt = (String)mapParams.get("refClientExt");
                		   String phonePrincipal = (String)mapParams.get("phonePrincipal");
                		   String contact = (String)mapParams.get("contact");
                		   String identite = (String)mapParams.get("identite");
                		   BigDecimal typeservice = mapParams.get("typeservice") != null ? new BigDecimal((String)mapParams.get("typeservice")) : null;
                		   String numeroMobile = (String)mapParams.get("numeroMobile");
                		   String statut = (String)mapParams.get("statut");
                		   String email = (String)mapParams.get("email");
                    	   
                    	 
                		 if (idclient != null && !idclient.equals("")){
   	                   		LssCr.add(Restrictions.eq("idclient", "idclient"));
   	                   	 }
                		 if (nom != null && !nom.equals("")){
 	                   		LssCr.add(Restrictions.ilike("nom", "%"+nom+"%"));
 	                   	 }
                		 if (prenom != null && !prenom.equals("")){
 	                   		LssCr.add(Restrictions.ilike("prenom", "%"+prenom+"%"));
 	                   	 }
                		 if (datnai != null && !datnai.equals("")){
 	                   		LssCr.add(Restrictions.ilike("datnai", "%"+datnai+"%"));
 	                   	 }
	                   	 if (lieunai != null && !lieunai.equals("")){
	                   		LssCr.add(Restrictions.ilike("lieunai", "%"+lieunai+"%"));
	                   	 }
	                   	if (pieceidentite != null && !pieceidentite.equals("")){
	                   		LssCr.add(Restrictions.ilike("pieceidentite", "%"+pieceidentite+"%"));
	                   	 }
	                   	if (description != null && !description.equals("")){
	                   		LssCr.add(Restrictions.ilike("description", "%"+description+"%"));
	                   	 }
	                   	if (adresspostal != null && !adresspostal.equals("")){
	                   		LssCr.add(Restrictions.ilike("adresspostal", "%"+adresspostal+"%"));
	                   	 }
	                   	if (typePiece != null && !typePiece.equals("")){
	                   		LssCr.add(Restrictions.ilike("lieunai", "%"+lieunai+"%"));
	                   	 }
	                   	if (phone != null && !phone.equals("")){
	                   		LssCr.add(Restrictions.ilike("phone", "%"+phone+"%"));
	                   	 }
                				
                		}

                		returnedList = (List<TUsers>)LssCr.list();
                   
                 break;
         */   
        }
        
        System.out.println("list for keyword: "+strEntity +" -  size: "+returnedList.size());
        return returnedList;
    }
    
    
//  public static Session getHibSession()
//  {
//      return HibernateUtil.getHibSession().openSession();
//  }

	
  
  public static void persistObject(Object obj){
       
          try{
              Session session = HibernateUtil.getHibSession();
              session.beginTransaction();
              session.save(obj);
              session.getTransaction().commit();
              
          } catch (HibernateException e){
              e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Une erreur est survenue", "Base de données des fidèles", JOptionPane.ERROR);
          }
  }
  
  public static void updateObject(Object obj){
       
          try{
              Session session = HibernateUtil.getHibSession();
              session.beginTransaction();
              session.merge(obj);  
              session.getTransaction().commit();
              
          } catch (HibernateException e){
              e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Une erreur est survenue", "Base de données des fidèles", JOptionPane.ERROR);
          }
  }
  
  public static void deleteObject(Object obj){
       
          try{
//              Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//              session.beginTransaction();
              HibernateUtil.getHibSession().delete(obj);
              HibernateUtil.getHibSession().getTransaction().commit();
              
          } catch (HibernateException e){
              e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Une erreur est survenue", "Base de données des fidèles", JOptionPane.ERROR);
          }
  }
  
    
}
