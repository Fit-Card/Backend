package com.financial.domain.fin.user.controller;

import com.financial.domain.fin.user.model.FinUser;
import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserGetIdRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.domain.fin.user.model.dto.response.FinUserGetIdResponse;
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
    public Response<?> createUser(@RequestBody FinUserSaveRequest request) {
        finUserService.createUser(request);
        return Response.SUCCESS();
    }

    @DeleteMapping("/")
    public Response<?> deleteUser(@RequestBody FinUserDeleteRequest request) {
        finUserService.deleteUser(request);
        return Response.SUCCESS();
    }

    @GetMapping("")
    public Response<FinUserGetIdResponse> getUser(@RequestParam String phone,
                                                  @RequestParam String verificationCode) {
        FinUserGetIdResponse response = finUserService.getFinUserId(new FinUserGetIdRequest(phone, verificationCode));
        return Response.SUCCESS(response);
    }

}
