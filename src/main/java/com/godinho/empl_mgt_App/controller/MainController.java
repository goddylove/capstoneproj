package com.godinho.empl_mgt_App.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model){
        return "about";
    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }


        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {
            // Invalidate the user session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // Clear any authentication-related cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }

            // Redirect to the login page or home page
            return "redirect:/login"; // Change "/login" to your desired logout destination
        }
    }
