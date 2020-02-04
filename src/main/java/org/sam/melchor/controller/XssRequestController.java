package org.sam.melchor.controller;

import lombok.extern.slf4j.Slf4j;
import org.sam.melchor.domain.XssRequestDto;
import org.sam.melchor.domain.XssRequestDto2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class XssRequestController {

    @PostMapping("/xss")
    public XssRequestDto xss (@RequestBody XssRequestDto xssRequestDto) {
      log.info("requestDto={}", xssRequestDto);
      return xssRequestDto;
    }

    @PostMapping("/form")
    public String form (XssRequestDto requestDto) {
        log.info("requestDto={}", requestDto);
        System.out.println(requestDto);
        return requestDto.getContent();
    }

    @PostMapping("/xss2")
    public XssRequestDto2 xss2(@RequestBody XssRequestDto2 xssRequestDto) {
        log.info("requestDto={}", xssRequestDto);
        return xssRequestDto;
    }

}