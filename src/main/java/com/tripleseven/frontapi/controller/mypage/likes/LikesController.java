package com.tripleseven.frontapi.controller.mypage.likes;

import com.tripleseven.frontapi.dto.likes.LikesResponseDTO;
import com.tripleseven.frontapi.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class LikesController {

    private LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }



    public String likesPage(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String keyword,
                            @RequestHeader("X-USER") Long userId) {

        List<LikesResponseDTO> likesResponseDTOS = likesService.searchLikes(userId, keyword, page, size); // userId가 1번인 유저일 때의 페이지(테스트)



        model.addAttribute("likeHistory", likesResponseDTOS);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);

        return "like-history";
    }

//    @PostMapping("/frontend/likes/{bookId}")
//    public RedirectView addLikes(@PathVariable Long bookId, @RequestHeader("X-USER") Long userId) {
//        likesService.addLikes(bookId, userId);
//        return new RedirectView("/frontend/books/" + bookId);
//    }
//
//    @DeleteMapping("/frontend/likes/{bookId}")
//    public RedirectView deleteLikes(@PathVariable Long bookId, @RequestHeader("X-USER") Long userId) {
//        likesService.deleteLikes(bookId, userId);
//        return new RedirectView("/frontend/books/" + bookId);
//    }
}
