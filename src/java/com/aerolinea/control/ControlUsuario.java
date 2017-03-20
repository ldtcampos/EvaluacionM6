package com.aerolinea.control;

import com.aerolinea.dao.PaisDaoImpl;
import com.aerolinea.dao.RolDaoImpl;
import com.aerolinea.dao.UsuarioDaoImpl;
import com.aerolinea.entidad.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ControlUsuario implements Serializable{
    private Usuario usuario;
    private Pais pais;
    private Rol rol;
    private List<Usuario> usuarios;
    private List<Rol> roles;
    private List<Pais> paises;
    @ManagedProperty("#{UsuarioDaoImpl}")
    private UsuarioDaoImpl usuarioDaoImpl;
    @ManagedProperty("#{PaisDaoImpl}")
    private PaisDaoImpl paisDaoImpl;
    @ManagedProperty("#{RolDaoImpl}")
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
    public void init(){
//        Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");
        try {
  
            usuario = usuarioDaoImpl.create();
            pais = paisDaoImpl.create();
            rol=rolDaoImpl.create();
            
        } catch (Exception e) {
        }
        
    }
    public String guardar() throws Exception{
        usuario.setPais(pais);
        usuario.setRol(rol);
        usuarioDaoImpl.saveOrUpdate(usuario);
        return "/index.xhtml?faces-redirect=true";
    }    
    
    public String seleccionaEdit(Usuario u){
        usuario=u;
//        pais=u.getPais();
//        rol=u.getRol();
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", u);
        return "UsuarioForm.xhtml?faces-redirect=true";
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

    public List<Usuario> getUsuarios() throws Exception {
        usuarios=usuarioDaoImpl.findAll();
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
