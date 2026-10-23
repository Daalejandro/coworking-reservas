package com.damolina.msvc.pago.msvc.validacion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component 
@ConfigurationProperties(prefix = "mock.pago")
public class PagoConfiguraciones {
  public enum Escenario {
        APROBAR,
        RECHAZAR,
        ERROR,
        LENTO
    }

    private Escenario escenario = Escenario.APROBAR;
    private long demoraMs = 5000;

    public Escenario getEscenario() {
        return escenario;
    }

    public void setEscenario(Escenario escenario) {
        this.escenario = escenario;
    }

    public long getDemoraMs() {
        return demoraMs;
    }

    public void setDemoraMs(long demoraMs) {
        this.demoraMs = demoraMs;
    }
}

