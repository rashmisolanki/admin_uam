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

    @GetMapping("/approve/{requestId}")
    public Response approvalRequest(@PathVariable Long requestId )
        {
           return uamUserService.approvalRequest(requestId);
        }

    @GetMapping("/view/allUser")
    public List<Response> viewAllUser()
    {
       return uamUserService.viewAllUser();
    }

    @GetMapping("/view/user/{requestId}")
    public Response viewUser(@PathVariable Long requestId)
    {
       return uamUserService.viewUser(requestId);
    }

    @GetMapping("/reject/{requestId}")
    public Response rejectUser(@PathVariable Long requestId)
    {
      return uamUserService.rejectAddUser(requestId);
    }

    @PutMapping("/edit/user/{requestId}")
        public Response editUser(@RequestBody Request request ,@PathVariable Long requestId)
        {
           return uamUserService.editUser(request,requestId);
        }

     @GetMapping("/edit/approve/{requestId}")
    public Response approveEditUser(@PathVariable Long requestId)
     {
         return uamUserService.approveEditUser(requestId);
     }
     @GetMapping("/edit/reject/{requestId}")
      public Response rejectEditUser(@PathVariable Long requestId)
     {
         return uamUserService.rejectEditUser(requestId);
     }

     @GetMapping("/deactivate/{requestId}")
     public Response deactivateUser(@RequestBody Request request,@PathVariable Long requestId)
     {
          return uamUserService.deactivateUser(request,requestId);
     }
}
