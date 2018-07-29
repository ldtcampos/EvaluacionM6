package com.aerolinea.dao;

import com.aerolinea.entidad.Reservacion;
import com.aerolinea.entidad.Vuelo;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("VueloDaoImpl")
public class VueloDaoImpl extends GenericDaoImpl<Vuelo, Integer>
        implements VueloDao, Serializable {

        @Override
    public List<Vuelo> findAll() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("select v from Vuelo v "
                    + " join fetch v.aeropuerto"
                    + " join fetch v.avion");
            List<Vuelo> entities = query.list();
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
