package com.nkpdqz.action;


import com.nkpdqz.domain.Image;
import com.nkpdqz.domain.User;
import com.nkpdqz.service.ImageService;
import com.nkpdqz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Controller
@RequestMapping("/UserAction")
@SessionAttributes("user")
public class Controller {

    @Autowired
    private UserService service;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/register.html")
    public void register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("repassword") String repassword,
                           HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String result;
        if (username.isEmpty()){
            result = "1";
        } else if (password.isEmpty()){
            result = "2";
        }else if (repassword.isEmpty()) {
            result = "3";
        }else if (!password.equals(repassword)){
            result = "4";
        } else if (!service.isgetUsername(username)){
            result = "5";
        }else{
            System.out.println("working haha");
            service.register(username, password, repassword);
            result = "6";
        }
        out.print(result);
    }

    @ModelAttribute("user")
    public User getUser(){
        User user = new User();
        return user;
    }


    @RequestMapping("/login.html")
    public void login(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      Model model, HttpServletResponse response,HttpServletRequest request) throws IOException {
        System.out.println("working!!!!");
        String result;
        PrintWriter out = response.getWriter();
        User user = service.login(username, password);
        if (username.isEmpty()){
            result = "1";
        } else if (password.isEmpty()){
            result = "2";
        } else if (user == null){
            result = "3";
        } else {
            result = "5";
            model.addAttribute("user",user);
            System.out.println(user);
            request.getSession().setAttribute("imageList",imageService.getImageListById(user.getId()));
            //model.addAttribute("imageList",imageService.getImageListById(user.getId()));
            List<Image> list = imageService.getImageListById(user.getId());
            if (list!=null){
                for (Image image :list){
                    System.out.println("hahahhafuck");
                    System.out.println(image.toString());
                    System.out.println("fuckhahaha");
                }
            } else {
                System.out.println("NULL!!!");
            }
        }
            /*ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user",user);
            modelAndView.setViewName("home");*/
        System.out.println("this");
        out.print(result);
        System.out.println(result);
    }

    @RequestMapping(value = "/exit.html",method = RequestMethod.GET)
    public String exit(){
        System.out.println("done!");
        return "index";
    }

    @RequestMapping("/upload.html")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("image_name") String name, ModelMap modelMap,HttpServletRequest request) throws IOException {
        Image image = new Image();
        User user = (User) modelMap.get("user");
        image.setName(name);
        image.setUser(user);
        image.setUrl(user.getUsername()+"/"+ UUID.randomUUID());
        image.setDate(new Date());
        if (!file.isEmpty()){
            file.transferTo(new File("/usr/temp/"+file.getOriginalFilename()));
            System.out.println(image.toString());
            imageService.addImage(image,file.getInputStream());
            request.getSession().setAttribute("imageList",imageService.getImageListById(image.getUser().getId()));
            //modelMap.addAttribute("imageList",imageService.getImageListById(image.getUser().getId()));
        }else {
            return "redirect:/home.jsp";
        }
        return "redirect:/home.jsp";
    }

    @RequestMapping("/delete.html")
    public String delete(@RequestParam("ids") String id,@RequestParam("urls") String url, ModelMap model,HttpServletRequest request){
        imageService.deleteImageById(id,url);
        User user = (User) model.get("user");
        //model.addAttribute("imageList",imageService.getImageListById(user.getId()));
        request.getSession().setAttribute("imageList",imageService.getImageListById(user.getId()));
        return "redirect:/home.jsp";
    }

}
