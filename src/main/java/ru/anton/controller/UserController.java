package ru.anton.controller;

import org.springframework.beans.support.PagedListHolder;
import ru.anton.entity.User;
import ru.anton.service.UserService;

import org.jboss.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    private static final int MAX_ROWS_PER_PAGE = 10;

    public UserController() {
        System.out.println("UserController()");
    }

    @Autowired
    private UserService userService;

    @RequestMapping("createUser")
    public ModelAndView createUser(@ModelAttribute User user) {
        logger.info("Creating User. Data: "+user);
        return new ModelAndView("userForm");
    }

    @RequestMapping("editUser")
    public ModelAndView editUser(@RequestParam int id, @ModelAttribute User user) {
        logger.info("Updating the User for the Id "+id);
        user = userService.getUser(id);
        return new ModelAndView("userForm", "userObject", user);
    }

    @RequestMapping("saveUser")
    public ModelAndView saveUser(@ModelAttribute User user) {
        logger.info("Saving the User. Data : "+user);
        if(user.getId() == 0){ // if user id is 0 then creating user other updating user
            userService.createUser(user);
        } else {
            userService.updateUser(user);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(@RequestParam int id)
    {
        logger.info("Deleting the User. Id : " + id);
        userService.deleteUser(id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = {"getAllUsers"})
    public ModelAndView getAllUsers(){
        logger.info("Getting all Users.");
        List<User> userList = userService.getAllUsers();
        return new ModelAndView("userList", "userList", userList);
    }

    @RequestMapping("searchUser")
    public ModelAndView searchUser(@RequestParam("searchName") String searchName){
        logger.info("Searching the User. User Names: "+searchName);
        List<User> usersList = userService.getAllUsers(searchName);
        return new ModelAndView("userList", "userList", usersList);
    }

    @RequestMapping(value="/")
    public ModelAndView listOfUsers(@RequestParam(required = false) Integer page) {
        logger.info("Getting all Users.");
        ModelAndView modelAndView = new ModelAndView("userList");

        List<User> users = userService.getAllUsers();
        PagedListHolder<User> pagedListHolder = new PagedListHolder<>(users);
        pagedListHolder.setPageSize(MAX_ROWS_PER_PAGE);
        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());

        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
            page=1;
        }
        modelAndView.addObject("page", page);
        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
            pagedListHolder.setPage(0);
            modelAndView.addObject("userList", pagedListHolder.getPageList());
        }
        else if(page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page-1);
            modelAndView.addObject("userList", pagedListHolder.getPageList());
        }
        return modelAndView;
    }
}
