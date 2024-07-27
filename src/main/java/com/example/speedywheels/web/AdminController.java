package com.example.speedywheels.web;

import com.example.speedywheels.model.view.UserControlRoomView;
import com.example.speedywheels.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/control-room")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView showControlRoom(ModelAndView model) {
        List<UserControlRoomView> users = this.userService.findAllUsersExcludingVenci777();
        model.addObject("users", users);
        model.setViewName("control-room");
        return model;
    }

    @PostMapping("/add-admin/{userId}")
    public ModelAndView addAdminRole(@PathVariable Long userId, ModelAndView model) {
        this.userService.addAdminRole(userId);
        model.setViewName("redirect:/control-room");
        return model;
    }

    @PostMapping("/remove-admin/{userId}")
    public ModelAndView removeAdminRole(@PathVariable Long userId, ModelAndView model) {
        this.userService.removeAdminRole(userId);
        model.setViewName("redirect:/control-room");
        return model;
    }

    @PostMapping("/block-user/{userId}")
    public ModelAndView blockUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.blockUser(userId);
        model.setViewName("redirect:/control-room");
        return model;
    }

    @PostMapping("/unblock-user/{userId}")
    public ModelAndView unblockUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.unblockUser(userId);
        model.setViewName("redirect:/control-room");
        return model;
    }

    @PostMapping("/delete-user/{userId}")
    public ModelAndView deleteUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.deleteUser(userId);
        model.setViewName("redirect:/control-room");
        return model;
    }


}
