package com.tripleseven.frontapi.controller.mypage.likes;

import com.tripleseven.frontapi.dto.likes.LikesResponseDTO;
import com.tripleseven.frontapi.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class LikesController {

    private LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }
//@RequestHeader("X-User") Long userId; -> 파라미터에 다시 넣어야함
    @GetMapping("/likes-history")
    public String likesPage(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String keyword) {
        List<LikesResponseDTO> likesResponseDTOS = likesService.getAllLikesByUserId(1L, page, size); // userId가 1번인 유저일 때의 페이지(테스트)
        model.addAttribute("likeHistory", likesResponseDTOS);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);

        return "like-history";
    }
}
