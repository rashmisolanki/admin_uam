package org.myproject.uam.service;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.myproject.uam.comman.Constant;
import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.entity.Admin;
import org.myproject.uam.exception.EffectiveStartDateException;
import org.myproject.uam.exception.NoRequestFoundException;
import org.myproject.uam.exception.PfNumberNotFound;
import org.myproject.uam.exception.UserAlreadyPresentException;
import org.myproject.uam.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    public AdminRepository adminRepository;

    public Response createNewUser(Request request)
    {
        Admin adminEntity=new Admin();
          adminEntity.setName(request.getName());
          adminEntity.setEmailId(request.getEmailId());
          adminEntity.setEffectivedate(request.getEffectiveDate());
          adminEntity.setLevel(request.getLevel());
          adminEntity.setReason(request.getReason());
          adminEntity.setPfNumber(request.getPfNumber());
          adminEntity.setUsergroup(request.getUserGroup());
          adminEntity.setUsergroupId(request.getUserGroupId());
          adminEntity.setPendingStatus(Constant.PENDING_STATUS_APPROVAL);
          adminEntity.setStatus(Constant.NEW_STATUS);
        Optional<Admin> adminOptionalEntity=  adminRepository.findByPfNumber(request.getPfNumber());
        if(adminOptionalEntity.isPresent())
        {
            throw new UserAlreadyPresentException("User Not Present");
        }
        Admin saveEntity = adminRepository.save(adminEntity);
          Response response = new Response();
          response.setRequestId(saveEntity.getRequestId());
          response.setName(saveEntity.getName());
          response.setPfNumber(saveEntity.getPfNumber());
          response.setEmailId(saveEntity.getEmailId());
          response.setEffectiveDate(saveEntity.getEffectivedate());
          response.setLevel(saveEntity.getLevel());
          response.setUserGroup(saveEntity.getUsergroup());
          response.setUserGroupId(saveEntity.getUsergroupId());
          response.setReason(saveEntity.getReason());
          response.setPendingStatus(saveEntity.getPendingStatus());
          response.setStatus(saveEntity.getStatus());
          return response;
    }

    public Response approvalRequest(Long requestId)  {
        Optional<Admin> adminEntity=adminRepository.findById(requestId);
        adminEntity.orElseThrow(()->new PfNumberNotFound("pf number is not present") );
       Admin getAdminEntity= adminEntity.get();
       LocalDate currentDateTime= LocalDate.now();
       LocalDate startDateTime=getAdminEntity.getEffectivedate();
          if(startDateTime.isEqual(currentDateTime))
          {
              getAdminEntity.setStatus(Constant.ACTIVE_STATUS);
              getAdminEntity.setPendingStatus(Constant.PENDING_STATUS);
          }
          else if (startDateTime.isAfter(currentDateTime))
         {
            getAdminEntity.setStatus(Constant.INACTIVE_STATUS);
            getAdminEntity.setPendingStatus(Constant.PENDING_STATUS_FUTURE);
          }
            adminRepository.save(getAdminEntity);
          Response response=new Response();
          response.setName(getAdminEntity.getName());
          response.setRequestId(getAdminEntity.getRequestId());
          response.setEmailId(getAdminEntity.getEmailId());
          response.setPfNumber(getAdminEntity.getPfNumber());
          response.setLevel(getAdminEntity.getLevel());
          response.setEffectiveDate(getAdminEntity.getEffectivedate());
          response.setUserGroup(getAdminEntity.getUsergroup());
          response.setUserGroupId(getAdminEntity.getUsergroupId());
          response.setPendingStatus(getAdminEntity.getPendingStatus());
          response.setStatus(getAdminEntity.getStatus());
          response.setReason(getAdminEntity.getReason());
          return response;
    }

    public List<Response> viewAllUser()
    {
      List<Admin> adminList= adminRepository.findAll();
       List<Response>responseList= adminList.stream().map(user->returnResponse(user)).collect(Collectors.toList());
       return responseList;
    }

    public Response returnResponse(Admin admin )
    {
        Response response=new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUsergroup());
        response.setUserGroupId(admin.getUsergroupId());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingStatus(admin.getPendingStatus());
        response.setEffectiveDate(admin.getEffectivedate());
        response.setRequestId(admin.getRequestId());
        response.setReason(admin.getReason());
        return response;
    }

    public Response viewUser(Long requestId)
    {
        Optional<Admin> adminView=adminRepository.findById(requestId);
        adminView.orElseThrow(()->new NoRequestFoundException("no Request raised for User"));
       Admin adminEntityView=adminView.get();
       Response response=new Response();
       response.setName(adminEntityView.getName());
       response.setEmailId(adminEntityView.getEmailId());
       response.setPfNumber(adminEntityView.getPfNumber());
       response.setEffectiveDate(adminEntityView.getEffectivedate());
       response.setRequestId(adminEntityView.getRequestId());
       response.setStatus(adminEntityView.getStatus());
       response.setPendingStatus(adminEntityView.getPendingStatus());
       response.setLevel(adminEntityView.getLevel());
       response.setUserGroup(adminEntityView.getUsergroup());
       response.setUserGroupId(adminEntityView.getUsergroupId());
       response.setReason(adminEntityView.getReason());
       return  response;
    }

    public Response editUser(Request request,Long requestId)
    {
       Optional<Admin> adminOptional= adminRepository.findById(requestId);
       adminOptional.orElseThrow(()->new NoRequestFoundException("no request found"));
       Admin admin=adminOptional.get();
       LocalDate currentDate=LocalDate.now();
       LocalDate startDate= request.getEffectiveDate();
       if(startDate.isBefore(currentDate))
       {
           throw new EffectiveStartDateException("effective start date is not before today's date");
       }
       if (admin.getStatus().equals("Active") && admin.getUsergroupId().equals(request.getUserGroupId()) )
       {
           throw new NoRequestFoundException("There is no change to User Group Id.");
       }
       admin.setUsergroupId(request.getUserGroupId());
       admin.setEffectivedate(request.getEffectiveDate());
       admin.setPendingStatus(Constant.PENDING_STATUS_EDIT);
        adminRepository.save(admin);
       Response response=new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUsergroup());
        response.setUserGroupId(admin.getUsergroupId());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingStatus(admin.getPendingStatus());
        response.setEffectiveDate(admin.getEffectivedate());
        response.setRequestId(admin.getRequestId());
        response.setReason(admin.getReason());
        return response;
    }
    public Response rejectUser(Long requestId)
    {
       Optional<Admin> adminOptional= adminRepository.findById(requestId);
       adminOptional.orElseThrow(()->new NoRequestFoundException("request not found"));
       Admin admin=adminOptional.get();
       admin.setStatus(Constant.INACTIVE_STATUS);
       admin.setPendingStatus(Constant.PENDING_STATUS);
       adminRepository.save(admin);
       Response response=new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUsergroup());
        response.setUserGroupId(admin.getUsergroupId());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingStatus(admin.getPendingStatus());
        response.setEffectiveDate(admin.getEffectivedate());
        response.setRequestId(admin.getRequestId());
        response.setReason(admin.getReason());
        response.setRejectReason(Constant.REJECT_STATUS);
        return response;
    }

    public Response rejectEditUser(Long requestId)
    {
        Optional<Admin> adminOptional=adminRepository.findById(requestId);
        adminOptional.orElseThrow(()->new NoRequestFoundException("no request found"));
        Admin admin= adminOptional.get();
        
        admin.setStatus(Constant.INACTIVE_STATUS);
        admin.setPendingStatus(Constant.PENDING_STATUS);
        adminRepository.save(admin);
        Response response=new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUsergroup());
        response.setUserGroupId(admin.getUsergroupId());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingStatus(admin.getPendingStatus());
        response.setEffectiveDate(admin.getEffectivedate());
        response.setRequestId(admin.getRequestId());
        response.setReason(admin.getReason());
        response.setRejectReason(Constant.REJECT_STATUS);
        return response;

    }
    public Response approveEditUser(Long requestId)
    {
      Optional<Admin> adminOptional=adminRepository.findById(requestId);
      adminOptional.orElseThrow(()->new NoRequestFoundException("no request found"));
      Admin admin= adminOptional.get();
      if(admin.getStatus().equals("Active") && admin.getPendingStatus().equals("Pending Approval(Edit)"));
        {
            admin.setStatus(Constant.PENDING_STATUS);
        }
        admin.setStatus(Constant.ACTIVE_STATUS);
        admin.setPendingStatus(Constant.PENDING_STATUS);
        adminRepository.save(admin);
        Response response=new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUsergroup());
        response.setUserGroupId(admin.getUsergroupId());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingStatus(admin.getPendingStatus());
        response.setEffectiveDate(admin.getEffectivedate());
        response.setRequestId(admin.getRequestId());
        response.setReason(admin.getReason());
        return response;
    }

}
