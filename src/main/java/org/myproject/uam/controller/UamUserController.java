package org.myproject.uam.controller;

import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.service.UamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UamUserController {

    @Autowired
    public UamUserService uamUserService;

    @PostMapping("/add/user")
    public Response createNewUser(@RequestBody Request request)
    {
        return uamUserService.createNewUser(request);
    }

    @GetMapping("/approve/{userId}")
    public Response approvalRequest(@PathVariable Long userId )
        {
           return uamUserService.approvalRequest(userId);
        }

    @GetMapping("/view/allUser")
    public List<Response> viewAllUser()
    {
        return uamUserService.viewAllUser();
    }

    @GetMapping("/view/user/{userId}")
    public Response viewUser(@PathVariable Long userId)
    {
       return uamUserService.viewUser(userId);
    }

    @GetMapping("/reject/{userId}")
    public Response rejectUser(@PathVariable Long userId)
    {
      return uamUserService.rejectAddUser(userId);
    }

    @PutMapping("/edit/user/{userId}")
        public Response editUser(@RequestBody Request request ,@PathVariable Long userId)
        {
           return uamUserService.editUser(request,userId);
        }

     @GetMapping("/edit/approve/{userId}")
    public Response approveEditUser(@PathVariable Long userId)
     {
         return uamUserService.approveEditUser(userId);
     }
     @GetMapping("/edit/reject/{userId}")
      public Response rejectEditUser(@PathVariable Long userId)
     {
         return uamUserService.rejectEditUser(userId);
     }

     @GetMapping("/deactivate/{userId}")
     public Response deactivateUser(@RequestBody Request request,@PathVariable Long userId)
     {
          return uamUserService.deactivateUser(request,userId);
     }
}
