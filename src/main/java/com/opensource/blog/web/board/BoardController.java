package com.opensource.blog.web.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BoardController {
    @GetMapping("/blog")
    public String home() {
        return "blog/board";
    }


}