package org.myproject.uam.service;

import org.myproject.uam.comman.Constant;
import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.entity.StagingUam;
import org.myproject.uam.entity.UamUser;
import org.myproject.uam.entity.UamUserGroupId;
import org.myproject.uam.exception.EffectiveStartDateException;
import org.myproject.uam.exception.NoRequestFoundException;
import org.myproject.uam.exception.PfNumberNotFound;
import org.myproject.uam.repository.UamUserGroupIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {


    public UamUser populateAddUserDetails(Request request)
    {
        UamUser uamUser=new UamUser();
        StagingUam stagingUam=new StagingUam();
        uamUser.setName(request.getName());
        uamUser.setEmailId(request.getEmailId());
        uamUser.setEffectiveDate(request.getEffectiveDate());
        uamUser.setReason(request.getReason());
        uamUser.setPfNumber(request.getPfNumber());
        uamUser.setPendingFor(Constant.PENDING_APPROVAL_ADD);
        uamUser.setStatus(Constant.NEW);
        uamUser.setCreatedBy(request.getCreatedBy());
        uamUser.setCreationDate(LocalDate.now());
        uamUser.setModifiedBy(request.getModifiedBy());
        uamUser.setIsDeleted(0);
        stagingUam.setActionOnApproval(Constant.ADD);
        stagingUam.setStatus(Constant.NEW);
        stagingUam.setIsDeleted(0);
        stagingUam.setGroupId(Constant.userGroupAbbreviation.get(request.getGroupId()));
        stagingUam.setUamUser(uamUser);
        uamUser.setStagingUam(stagingUam);
        return uamUser;
    }

    public Response prepareResponse(UamUser saveUamUser)
    {
        Response response = new Response();
        response.setUserId(saveUamUser.getUserId());
        response.setName(saveUamUser.getName());
        response.setPfNumber(saveUamUser.getPfNumber());
        response.setEmailId(saveUamUser.getEmailId());
        response.setEffectiveDate(saveUamUser.getEffectiveDate());
        response.setReason(saveUamUser.getReason());
        response.setGroupID(saveUamUser.getStagingUam().getGroupId());
        response.setPendingFor(saveUamUser.getStatus());
        response.setStatus(saveUamUser.getStatus());
        response.setCreatedBy(saveUamUser.getCreatedBy());
        response.setIsDeleted(saveUamUser.getIsDeleted());
        response.setCreationDate(saveUamUser.getCreationDate());
        response.setModifiedBy(saveUamUser.getModifiedBy());
        response.setModifiedDate(saveUamUser.getModifiedDate());
        response.setOfId(saveUamUser.getOfId());
        return response;
    }

    public UamUser populateApprovalAddUser(UamUser uamUser)
    {

        StagingUam stagingUamUser = uamUser.getStagingUam();
        if (uamUser.getEffectiveDate().isEqual(LocalDate.now())) {
            uamUser.setStatus(Constant.ACTIVE);
            uamUser.setPendingFor(Constant.DASH);
        } else if (uamUser.getEffectiveDate().isAfter(LocalDate.now())) {
            uamUser.setStatus(Constant.INACTIVE);
            uamUser.setPendingFor(Constant.ADD_FUTURE);
        }
        stagingUamUser.setStatus(Constant.APPROVAL);
        stagingUamUser.setGroupId(stagingUamUser.getGroupId());
        return uamUser;
    }

    public UamUserGroupId populateUamUserGroupId(UamUser uamUser)
    {
        UamUserGroupId uamUserGroupId=new UamUserGroupId();
        uamUserGroupId.setPfNumber(uamUser.getPfNumber());
        uamUserGroupId.setGroupId(uamUser.getStagingUam().getGroupId());
        return  uamUserGroupId;
    }

    public Response prepareApprovalResponse(UamUser uamUser)
    {
        Response response = new Response();
        response.setUserId(uamUser.getUserId());
        response.setName(uamUser.getName());
        response.setUserId(uamUser.getUserId());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setPendingFor(uamUser.getPendingFor());
        response.setStatus(uamUser.getStatus());
        response.setReason(uamUser.getReason());
        return response;
    }
    public List<Response> resultOfObject(List<Object[]> result)
    {
        List<Response> responseDtoList = new ArrayList<Response>();
        for(Object[] userAndGroup:result)
        {
            Response response=new Response();
            if(userAndGroup[0]!=null)
            {
                UamUser uamUser=(UamUser) userAndGroup[0];
                response.setUserId(uamUser.getUserId());
                response.setPfNumber(uamUser.getPfNumber());
                response.setModifiedBy(uamUser.getModifiedBy());
                response.setModifiedDate(uamUser.getModifiedDate());
                response.setCreatedBy(uamUser.getCreatedBy());
            }
                if(userAndGroup[1]!=null)
                {
                    //casting
                    UamUserGroupId uamUserGroupId=(UamUserGroupId) userAndGroup[1];
                    if(uamUserGroupId!=null)
                    {
                      String groupId=  uamUserGroupId.getGroupId();
                        response.setGroupID(uamUserGroupId.getGroupId());
                    }
                }
            responseDtoList.add(response);
        }
         return responseDtoList;
    }
    public Response prepareViewUserResponse(UamUser uamUser) {
        Response response = new Response();
        response.setUserId(response.getUserId());
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserId(uamUser.getUserId());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setReason(uamUser.getReason());
        response.setCreationDate(uamUser.getCreationDate());
        response.setModifiedDate(uamUser.getModifiedDate());
        response.setCreatedBy(uamUser.getCreatedBy());
        response.setModifiedBy(uamUser.getModifiedBy());
        response.setGroupID(uamUser.getStagingUam().getGroupId());
        return response;
    }

    public UamUser populateRejectAddUser(UamUser uamUser)
    {
        StagingUam stagingUam = new StagingUam();
        uamUser.setStatus(Constant.INACTIVE);
        uamUser.setPendingFor(Constant.DASH);
        uamUser.setIsDeleted(1);
        StagingUam stagingUam1=uamUser.getStagingUam();
        stagingUam1.setStatus(Constant.REJECT);
        stagingUam1.setIsDeleted(1);
        return uamUser;
    }

    public Response prepareResponseRejectAddUser(UamUser uamUser)
    {
        Response response = new Response();
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserId(uamUser.getUserId());
        response.setReason(uamUser.getReason());
        response.setRejectReason(Constant.REJECT_STATUS);
        return response;
    }

    public UamUser populateEditUser(Request request, UamUser dbUamUser, UamUserGroupId dbUamUserGroupId)
    {
        if (request.getEffectiveDate().isBefore(LocalDate.now())) {
            throw new EffectiveStartDateException("effective start date is not before today's date");
        }
       String abbreviatedGroup= Constant.userGroupAbbreviation.get(request.getGroupId());
        if (dbUamUser.getStatus().equals("active") && abbreviatedGroup.equals(dbUamUserGroupId.getGroupId())) {
            throw new NoRequestFoundException("There is no change to User Group Id.");
        }
        UamUser uamUser=new UamUser();
        uamUser.setName(dbUamUser.getName());
        uamUser.setPfNumber(dbUamUser.getPfNumber());
        uamUser.setEmailId(dbUamUser.getEmailId());
        uamUser.setCreationDate(dbUamUser.getEffectiveDate());
        uamUser.setCreatedBy(dbUamUser.getCreatedBy());
        uamUser.setModifiedDate(LocalDate.now());
        uamUser.setPendingFor(Constant.PENDING_APPROVAL_EDIT);
        uamUser.setIsDeleted(0);
        uamUser.setStatus(dbUamUser.getStatus());
        uamUser.setEffectiveDate(request.getEffectiveDate());
        uamUser.setPendingFor(Constant.PENDING_APPROVAL_EDIT);
        StagingUam stagingUam=new StagingUam();
        stagingUam.setActionOnApproval(Constant.EDIT);
        stagingUam.setGroupId(abbreviatedGroup);
        stagingUam.setStatus(Constant.NEW);
        stagingUam.setUamUser(uamUser);
        uamUser.setStagingUam(stagingUam);
        return uamUser;
    }

    public Response prepareResponseEditUser(UamUser uamUser)
    {
        Response response = new Response();
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setOfId(uamUser.getOfId());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserId(uamUser.getUserId());
        response.setReason(uamUser.getReason());
        return response;
    }

    public UamUser populateApproveEditUser(UamUser dbuamUser)
    {
        StagingUam stagingUam= dbuamUser.getStagingUam();
        if(dbuamUser.getEffectiveDate().isEqual(LocalDate.now())) {
                dbuamUser.setPendingFor(Constant.DASH);
                dbuamUser.setStatus(Constant.ACTIVE);
            }
        else if (dbuamUser.getEffectiveDate().isAfter(LocalDate.now())) {
                dbuamUser.setStatus(Constant.INACTIVE);
                dbuamUser.setPendingFor(Constant.ADD_FUTURE);
            }

        stagingUam.setStatus(Constant.APPROVAL);
        return dbuamUser;
    }

    public Response prepareResponseEditApproveUser(UamUser uamUser)
    {
        Response response = new Response();
        response.setUserId(uamUser.getUserId());
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setReason(uamUser.getReason());
        return response;
    }

    public UamUser prepareEditRejectUser(UamUser dbUamUser)
    {
       StagingUam stagingUam= dbUamUser.getStagingUam();
        dbUamUser.setStatus(Constant.INACTIVE);
        dbUamUser.setPendingFor(Constant.DASH);
        stagingUam.setStatus(Constant.REJECT);
        return dbUamUser;
    }

    public Response prepareResponseEditRejectUser(UamUser uamUser)
    {
        Response response = new Response();
        response.setUserId(uamUser.getUserId());
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setGroupID(uamUser.getStagingUam().getGroupId());
        response.setReason(uamUser.getReason());
        response.setRejectReason(Constant.REJECT);
        return response;
    }
}
