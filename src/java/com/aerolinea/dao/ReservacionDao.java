package com.aerolinea.dao;

import com.aerolinea.entidad.Reservacion;
import javax.inject.Named;

@Named
public interface ReservacionDao extends GenericDao<Reservacion, String> {
    
}
