package com.bancocuscatlan.mcvs.coworking.reservas.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service 
public class NotificaionService {


    private Logger log = LoggerFactory.getLogger(NotificaionService.class);


    @Async 
    public void enviarCorreo(String correo){

        log.info("Pago realizado con exito... procedemos a enviar correo a " + correo);

    }


}
