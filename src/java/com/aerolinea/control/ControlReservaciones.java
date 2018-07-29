package com.aerolinea.control;

import com.aerolinea.dao.PaisDaoImpl;
import com.aerolinea.dao.ReservacionDaoImpl;
import com.aerolinea.dao.RolDaoImpl;
import com.aerolinea.dao.UsuarioDaoImpl;
import com.aerolinea.dao.VueloDaoImpl;
import com.aerolinea.entidad.Reservacion;
import com.aerolinea.entidad.Usuario;
import com.aerolinea.entidad.Vuelo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode
        = ScopedProxyMode.TARGET_CLASS)

public class ControlReservaciones implements Serializable {

    private Reservacion reservacion;
    private Vuelo vuelo;
    private Usuario usuario;
    private int nBoletos;
    private List<Reservacion> reservaciones;
    private List<Vuelo> vuelos;
    private List<Usuario> usuarios;
    @Autowired
    private ReservacionDaoImpl reservacionDaoImpl;
    @Autowired
    private VueloDaoImpl vueloDaoImpl;
    @Autowired
    private UsuarioDaoImpl usuarioDaoImpl;

    public ReservacionDaoImpl getReservacionDaoImpl() {
        return reservacionDaoImpl;
    }

    public void setReservacionDaoImpl(ReservacionDaoImpl reservacionDaoImpl) {
        this.reservacionDaoImpl = reservacionDaoImpl;
    }

    public VueloDaoImpl getVueloDaoImpl() {
        return vueloDaoImpl;
    }

    public void setVueloDaoImpl(VueloDaoImpl vueloDaoImpl) {
        this.vueloDaoImpl = vueloDaoImpl;
    }

    public UsuarioDaoImpl getUsuarioDaoImpl() {
        return usuarioDaoImpl;
    }

    public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
        this.usuarioDaoImpl = usuarioDaoImpl;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getnBoletos() {
        return nBoletos;
    }

    public void setnBoletos(int nBoletos) {
        this.nBoletos = nBoletos;
    }

        //@Secured("ROLE_ADMIN")
    //@RolesAllowed("ROLE_ADMIN")
    @PostAuthorize("returnObject.size() > 1") // Numero de registros
    @PreAuthorize("hasAnyRole({'ROLE_VENDEDOR'})") //Filtra lista usuarios, admin todos, otro/user solo su usuario
    @PostFilter("hasRole('ROLE_VENDEDOR') || "
            + "filterObject.idusuario == principal.username")
    public List<Reservacion> getReservaciones() throws Exception {
        reservaciones = reservacionDaoImpl.findAll();
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @PostConstruct
    public void init() {
        try {
            reservacion = reservacionDaoImpl.create();
            vuelo = vueloDaoImpl.create();
            usuario = usuarioDaoImpl.create();

        } catch (Exception e) {
        }

    }

    public String guardar() throws Exception {
        reservacion.setVuelo(vuelo);
        reservacion.setUsuario(usuario);
        reservacion.setNboletos(nBoletos);
        reservacion.setUsuario(usuario);
        reservacionDaoImpl.saveOrUpdate(reservacion);
        return "/index.xhtml?faces-redirect=true";
    }

    public String seleccionaEdit(Reservacion r) {
        try {
            reservacion = r;
            vuelo = r.getVuelo();
            usuario = r.getUsuario();
            return "UsuarioForm.xhtml?faces-redirect=true";
        } catch (Exception e) {
            return "login.xhtml?faces-redirect=true";
        }

    }




}
