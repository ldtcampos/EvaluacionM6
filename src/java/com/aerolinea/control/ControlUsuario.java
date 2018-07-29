package com.aerolinea.control;

import com.aerolinea.dao.PaisDaoImpl;
import com.aerolinea.dao.RolDaoImpl;
import com.aerolinea.dao.UsuarioDaoImpl;
import com.aerolinea.entidad.*;
import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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

//@ManagedBean // Obedece a JSF
//@RequestScoped
//Asegurando un metodo con @Secured, elemento administrado por spring
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode
        = ScopedProxyMode.TARGET_CLASS)

public class ControlUsuario implements Serializable {

    private Usuario usuario;
    private Pais pais;
    private Rol rol;
    private List<Usuario> usuarios;
    private List<Rol> roles;
    private List<Pais> paises;
    // @ManagedProperty("#{UsuarioDaoImpl}") // para jpa
    @Autowired // Para spring
    private UsuarioDaoImpl usuarioDaoImpl;
    //@ManagedProperty("#{PaisDaoImpl}") // para jpa
    @Autowired // Para spring
    private PaisDaoImpl paisDaoImpl;
    //@ManagedProperty("#{RolDaoImpl}") // para jpa
    @Autowired // Para spring
    private RolDaoImpl rolDaoImpl;

    public PaisDaoImpl getPaisDaoImpl() {
        return paisDaoImpl;
    }

    public void setPaisDaoImpl(PaisDaoImpl paisDaoImpl) {
        this.paisDaoImpl = paisDaoImpl;
    }

    public RolDaoImpl getRolDaoImpl() {
        return rolDaoImpl;
    }

    public void setRolDaoImpl(RolDaoImpl rolDaoImpl) {
        this.rolDaoImpl = rolDaoImpl;
    }

    public UsuarioDaoImpl getUsuarioDaoImpl() {
        return usuarioDaoImpl;
    }

    public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
        this.usuarioDaoImpl = usuarioDaoImpl;
    }

    @PostConstruct
    public void init() {
//        Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");
        try {

            usuario = usuarioDaoImpl.create();
            pais = paisDaoImpl.create();
            rol = rolDaoImpl.create();

        } catch (Exception e) {
        }

    }

    @Secured({"ROLE_OTRO", "ROLE_ADMIN"})
    public String guardar() throws Exception {
        usuario.setPais(pais);
        usuario.setRol(rol);
        usuarioDaoImpl.saveOrUpdate(usuario);
        return "/index.xhtml?faces-redirect=true";
    }

    @PreAuthorize("(hasRole('ROLE_ADMIN') and #u.idusuario.length() <= 4)")
    //@PreAuthorize("#u.idusuario == principal.username")
    public String seleccionaEdit(Usuario u) {
        try{
            usuario = u;
       //pais=u.getPais();
        //rol=u.getRol();
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", u);
        return "UsuarioForm.xhtml?faces-redirect=true";
        }catch(Exception e){
            return "login.xhtml?faces-redirect=true";
        }
        
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_OTRO', 'ROLE_USER', 'ROLE_ADMIN'})")
    @PreFilter("hasRole('ROLE_ADMIN') || targetObject.idusuario == principal.username")
    public void eliminar(List<Usuario> u){
        try{
            //usuarioDaoImpl.delete(id);
        } catch (Exception ex) {
            //Logger.getLogger(ControlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //@Secured("ROLE_ADMIN")
    //@RolesAllowed("ROLE_ADMIN")
    //@PostAuthorize("returnObject.size() > 1") // Numero de registros
    //@PreAuthorize("hasAnyRole({'ROLE_OTRO', 'ROLE_USER', 'ROLE_ADMIN'})") //Filtra lista usuarios, admin todos, otro/user solo su usuario
    //@PostFilter("hasRole('ROLE_ADMIN') || "
            //+ "filterObject.idusuario == principal.username")
    public List<Usuario> getUsuarios() throws Exception {
        usuarios = usuarioDaoImpl.findAll();
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Rol> getRoles() throws Exception {
        roles = rolDaoImpl.findAll();
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Pais> getPaises() throws Exception {
        paises = paisDaoImpl.findAll();
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

}
