package com.financial.domain.fin.user.controller;

import com.financial.domain.fin.user.model.FinUser;
import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.domain.fin.user.service.FinUserService;
import com.financial.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class FinUserController {

    private final FinUserService finUserService;

    @PostMapping("/")
    public Response<?> saveUser(@RequestBody FinUserSaveRequest request) {
        finUserService.saveUser(request);
        return Response.SUCCESS();
    }

    @DeleteMapping("/")
    public Response<?> deleteUser(@RequestBody FinUserDeleteRequest request) {
        finUserService.deleteUser(request);
        return Response.SUCCESS();
    }

}
