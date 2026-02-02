package com.bartun.sandbox2;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Slf4j
@RestController
public class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<Object> hello(HttpServletRequest request) {

    String remoteAddr = request.getRemoteAddr();
    Map<String, Object> result = Map.of("version", "0.3", "clientIp", remoteAddr);

    System.out.println("Remote Address:" + remoteAddr);

    InetAddress address;
    try {
      address = InetAddress.getByName(remoteAddr);
    } catch (UnknownHostException e) {
      System.out.println("Unable to resolve host: " + remoteAddr);
      return ResponseEntity.notFound().build();
    }

    if (address instanceof java.net.Inet6Address) {
      System.out.println("This is an IPv6 address: " + remoteAddr);
      new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      System.out.println("This is an IPv4 address: " + remoteAddr);
    }
    return ResponseEntity.ok(result);
  }

}

