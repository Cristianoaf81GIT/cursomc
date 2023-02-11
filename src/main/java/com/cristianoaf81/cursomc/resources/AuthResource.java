package com.cristianoaf81.cursomc.resources;

// import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristianoaf81.cursomc.dto.EmailDTO;
import com.cristianoaf81.cursomc.security.JWTUtil;
import com.cristianoaf81.cursomc.security.UserSS;
import com.cristianoaf81.cursomc.services.AuthService;
import com.cristianoaf81.cursomc.services.UserService;

@RestController()
@RequestMapping(value = "/auth")
public class AuthResource {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private AuthService authService;

  @PostMapping("/refresh_token")
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    UserSS user = UserService.authenticated();
    String token = this.jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("access-control-expose-header", "Authorization"); // expoe o header Authorization
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/forgot")
  public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
    authService.sendNewPassword(objDto.getEmail());
    return ResponseEntity.noContent().build();
  }
}
