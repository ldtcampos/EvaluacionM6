
package com.aerolinea.control;

import com.aerolinea.service.ServiceHolaSpring;
import java.io.Serializable;

/*JSF*/
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
//------------------------------------------------
/*Spring*/
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
//-------------------------------------------------------------
/*CDI*/
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
//---------------------------------------------------------
/*JSF*/
//@ManagedBean
//@RequestScoped
/*Spring*/
//@Controller
//@Scope("request")
/*CDI*/
@Named
@RequestScoped
public class ControlHola implements Serializable{
    
//    @ManagedProperty("#{ServiceHolaSpring}") //jsf
    @Inject //spring y cdi: @Inject
    private ServiceHolaSpring serviceHolaSpring;
    
    public String saludar(){
        return "hola desde el managed bean";
    }
    public ServiceHolaSpring getServiceHolaSpring() {
        return serviceHolaSpring;
    }
    public void setServiceHolaSpring(ServiceHolaSpring serviceHolaSpring) {
        this.serviceHolaSpring = serviceHolaSpring;
    }
    public String hola(){return serviceHolaSpring.test();}
}
