package com.bartun.sandbox2;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public Map<String, Object> hello(HttpServletRequest request) {

    Map<String, Object> result = Map.of("version", "0.1", "clientIp", request.getRemoteAddr());

    System.out.println("Remote Address:" + request.getRemoteAddr());

    return result;
  }
}

