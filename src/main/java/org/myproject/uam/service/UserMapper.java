package org.myproject.uam.service;

import org.myproject.uam.comman.Constant;
import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.entity.StagingUam;
import org.myproject.uam.entity.UamUser;
import org.myproject.uam.exception.PfNumberNotFound;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        uamUser.setLevel(Constant.userGroupAbbreviation.get(request.getUserGroup()));
        uamUser.setReason(request.getReason());
        uamUser.setPfNumber(request.getPfNumber());
        uamUser.setUserGroup(request.getUserGroup());
        uamUser.setPendingFor(Constant.PENDING_STATUS_APPROVAL);
        uamUser.setStatus(Constant.NEW_STATUS);
        uamUser.setCreatedBy(request.getCreatedBy());
        uamUser.setCreationDate(LocalDate.now());
        uamUser.setModifiedBy(request.getModifiedBy());
        uamUser.setIsDeleted(0);
        stagingUam.setActionOnApproval(Constant.ACTION_ON_APPROVAL_ADD);
        stagingUam.setStatus(Constant.NEW_STATUS);
        stagingUam.setIsDeleted(0);
        stagingUam.setGroupId(Constant.userGroupAbbreviation.get(request.getUserGroup()));
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
        response.setLevel(saveUamUser.getLevel());
        response.setUserGroup(saveUamUser.getUserGroup());
        response.setReason(saveUamUser.getReason());
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
        StagingUam stagingUam = new StagingUam();
        StagingUam stagingUam1 = uamUser.getStagingUam();
        if (uamUser.getEffectiveDate().isEqual(LocalDate.now())) {
            uamUser.setStatus(Constant.ACTIVE_STATUS);
            uamUser.setPendingFor(Constant.PENDING_STATUS);
        } else if (uamUser.getEffectiveDate().isAfter(LocalDate.now())) {
            uamUser.setStatus(Constant.INACTIVE_STATUS);
            uamUser.setPendingFor(Constant.PENDING_STATUS_FUTURE);
        }
        stagingUam1.setStatus(Constant.ACTION_ON_APPROVAL);
        stagingUam1.setIsDeleted(1);
        return uamUser;
    }

    public Response prepareApprovalResponse(UamUser uamUser)
    {
        Response response = new Response();
        response.setUserId(uamUser.getUserId());
        response.setName(uamUser.getName());
        response.setUserId(uamUser.getUserId());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setLevel(uamUser.getLevel());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserGroup(uamUser.getUserGroup());
        response.setPendingFor(uamUser.getPendingFor());
        response.setStatus(uamUser.getStatus());
        response.setReason(uamUser.getReason());
        return response;
    }

    public Response prepareViewAllResponse(UamUser uamUser) {
        Response response = new Response();
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setUserGroup(uamUser.getUserGroup());
        response.setLevel(uamUser.getLevel());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserId(uamUser.getUserId());
        response.setReason(uamUser.getReason());
        return response;
    }

    public Response prepareViewUserResponse(UamUser uamUser) {
        Response response = new Response();
        response.setName(uamUser.getName());
        response.setEmailId(uamUser.getEmailId());
        response.setPfNumber(uamUser.getPfNumber());
        response.setEffectiveDate(uamUser.getEffectiveDate());
        response.setUserId(uamUser.getUserId());
        response.setStatus(uamUser.getStatus());
        response.setPendingFor(uamUser.getPendingFor());
        response.setLevel(uamUser.getLevel());
        response.setUserGroup(uamUser.getUserGroup());
        response.setReason(uamUser.getReason());
        return response;
    }
}
