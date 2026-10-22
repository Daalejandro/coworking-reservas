package com.bancocuscatlan.mcvs.coworking.reservas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.request.PaymantRequest;
import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.PaymantResponse;

@FeignClient(url = "localhost:9000",name = "msvc-validacion")
public interface PaymantFeingClient {

    @PostMapping("/validate")
    PaymantResponse validar(@RequestBody PaymantRequest request);

}
