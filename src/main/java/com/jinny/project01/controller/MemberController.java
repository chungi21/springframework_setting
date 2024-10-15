package com.jinny.project01.controller;

import com.jinny.project01.dto.MemberDTO;
import com.jinny.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){ // @RequestParam 을 이용할 수 있지만 DTO로 받아오기
        int saveResult = memberService.save(memberDTO);
        if(saveResult>0){
            return "login";
        }else{
            return "save";
        }
    }

    // login 페이지
    @GetMapping("/login")
    public String loginForm(){
     return "login";
    }

    // login 처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        }else{
            return "login";
        }
    }

    // 회원목록 조회
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }

    // 회원 상세 조회
    @GetMapping
    public String findById(@RequestParam("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    // 회원 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){
        memberService.delete(id);
        return  "redirect:/member/";
    }

    // 회원 수정 Form
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member",memberDTO);
        return "update";
    }

    // 회원 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        boolean result = memberService.update(memberDTO);
        if(result){
            return "redirect:/member?id="+memberDTO.getId();
        }else{
            return "index";
        }
    }

}
















