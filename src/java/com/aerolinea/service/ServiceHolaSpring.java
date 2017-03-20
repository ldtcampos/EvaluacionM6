package com.aerolinea.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.stereotype.Service;

@Service("ServiceHolaSpring")
//@Named
//@RequestScoped
public class ServiceHolaSpring {
    public String test(){
        return "hola desde el servicio spring";
    }
}
