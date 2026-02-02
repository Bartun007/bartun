package com.bartun.sandbox2;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Slf4j
@RestController
public class HelloController {

  @GetMapping("/hello")
  public Map<String, Object> hello(HttpServletRequest request) {

    String remoteAddr = request.getRemoteAddr();
    Map<String, Object> result = Map.of("version", "0.2", "clientIp", remoteAddr);

    System.out.println("Remote Address:" + remoteAddr);

    InetAddress address;
    try {
      address = InetAddress.getByName(remoteAddr);
    } catch (UnknownHostException e) {
      System.out.println("Unable to resolve host: " + remoteAddr);
      address = null;
    }

    if (address instanceof java.net.Inet6Address) {
      System.out.println("This is an IPv6 address: " + remoteAddr);
    } else {
      System.out.println("This is an IPv4 address: " + remoteAddr);
    }

    return result;
  }
}

