package com.aerolinea.dao;

import com.aerolinea.entidad.Reservacion;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("ReservacionDaoImpl")
public class ReservacionDaoImpl extends GenericDaoImpl<Reservacion, String> 
        implements ReservacionDao, Serializable{
        @Override
    public List<Reservacion> findAll() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("select r from Reservacion r "
                    + " join fetch r.vuelo"
                    + " join fetch r.usuario");
            List<Reservacion> entities = query.list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception ex) {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } catch (Exception exc) {
//                LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
            }
            throw new RuntimeException(ex);
        }finally{session.close();}
    } 
    
}
