package com.aerolinea.control;

import com.aerolinea.entidad.Aeropuerto;
import com.aerolinea.entidad.Avion;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode
        = ScopedProxyMode.TARGET_CLASS)

public class ControlVuelo implements Serializable {
    private Aeropuerto idorigen;
    private Aeropuerto iddestino;
    private Avion avion;
    
}
