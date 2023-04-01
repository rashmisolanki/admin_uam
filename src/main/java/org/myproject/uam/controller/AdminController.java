package org.myproject.uam.controller;

import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    public AdminService adminService;

    @PostMapping("/add/user")
    public Response createNewUser(@RequestBody Request request)
    {
        return adminService.createNewUser(request);
    }

    @GetMapping("/approve/{requestId}")
    public Response approvalRequest(@PathVariable Long requestId )
        {
           return adminService.approvalRequest(requestId);
        }

    @GetMapping("/view/allUser")
    public List<Response> viewAllUser()
    {
       return adminService.viewAllUser();
    }

    @GetMapping("/view/user/{requestId}")
    public Response viewUser(@PathVariable Long requestId)
    {
       return adminService.viewUser(requestId);
    }

    @GetMapping("/reject/{requestId}")
    public Response rejectUser(@PathVariable Long requestId)
    {
      return adminService.rejectUser(requestId);
    }

    @PutMapping("/edit/user/{requestId}")
        public Response editUser(@RequestBody Request request ,@PathVariable Long requestId)
        {
           return adminService.editUser(request,requestId);
        }

     @GetMapping("/edit/approve/{requestId}")
    public Response approveEditUser(@PathVariable Long requestId)
     {
         return adminService.approveEditUser(requestId);
     }
     @GetMapping("/edit/reject/{requestId}")
      public Response rejectEditUser(@PathVariable Long requestId)
     {

     }
}
