package org.myproject.uam.service;

import org.myproject.uam.comman.Constant;
import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.entity.StagingUam;
import org.myproject.uam.entity.UamUser;
import org.myproject.uam.exception.EffectiveStartDateException;
import org.myproject.uam.exception.NoRequestFoundException;
import org.myproject.uam.exception.PfNumberNotFound;
import org.myproject.uam.exception.UserAlreadyPresentException;
import org.myproject.uam.repository.StagingUamRepository;
import org.myproject.uam.repository.UamUserGroupIdRepository;
import org.myproject.uam.repository.UamUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UamUserServiceImp implements UamUserService {
    @Autowired
    public UamUserRepository uamUserRepository;
    @Autowired
    public StagingUamRepository stagingUamRepository;

    @Autowired
    private UamUserGroupIdRepository uamUserGroupIdRepository;

    @Autowired
    private UserMapper userMapper;

    public Response createNewUser(Request request) {
        Optional<UamUser> uamUserEntity = uamUserRepository.findByPfNumber(request.getPfNumber());
        if (uamUserEntity.isPresent()) {
            throw new UserAlreadyPresentException(Constant.USER_ALREADY_PRESENT_EXCEPTION);
        }
        if (request.getEffectiveDate().isBefore(LocalDate.now())) {
            throw new EffectiveStartDateException(Constant.EFFECTIVE_START_DATE_EXCEPTION);
        }
        UamUser uamUser = userMapper.populateAddUserDetails(request);
        UamUser saveUamUser = uamUserRepository.save(uamUser);
        return userMapper.prepareResponse(saveUamUser);
    }

    public Response approvalRequest(Long UserId) {
        Optional<UamUser> uamUser = uamUserRepository.findById(UserId);
        uamUser.orElseThrow(() -> new PfNumberNotFound("pf number is not present"));
        UamUser updatedUamUser=userMapper.populateApprovalAddUser(uamUser.get());
        UamUser savedUamUser=uamUserRepository.save(updatedUamUser);
        return userMapper.prepareApprovalResponse(savedUamUser);
    }

    public List<Response> viewAllUser() {
        List<UamUser> adminList = uamUserRepository.findAll();
        List<Response> responseList = adminList.stream()
                .map(user -> userMapper.prepareViewAllResponse(user)).collect(Collectors.toList());
        return responseList;
    }


    public Response viewUser(Long requestId) {
        Optional<UamUser> uamUser = uamUserRepository.findById(requestId);
        uamUser.orElseThrow(() -> new NoRequestFoundException("no Request raised for User"));
        return userMapper.prepareViewUserResponse(uamUser.get());

    }

    public Response editUser(Request request, Long requestId) {
        Optional<UamUser> adminOptional = uamUserRepository.findById(requestId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser admin = adminOptional.get();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = request.getEffectiveDate();
        if (startDate.isBefore(currentDate)) {
            throw new EffectiveStartDateException("effective start date is not before today's date");
        }
        if (admin.getStatus().equals("Active") && admin.getUserGroup().equals(request.getUserGroup())) {
            throw new NoRequestFoundException("There is no change to User Group Id.");
        }
        admin.setUserGroup(request.getUserGroup());
        admin.setEffectiveDate(request.getEffectiveDate());
        admin.setPendingFor(Constant.PENDING_STATUS_EDIT);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUserGroup());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        return response;
    }

    public Response rejectUser(Long requestId) {
        UamUser uamUser = new UamUser();
        StagingUam stagingUam = new StagingUam();
        Optional<UamUser> adminOptional = uamUserRepository.findById(requestId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("request not found"));
        UamUser admin = adminOptional.get();
        StagingUam stagingUam1 = uamUser.getStagingUam();
        stagingUam1.setStatus(Constant.ACTION_ON_APPROVAL_REJECT);
        stagingUam1.setIsDeleted(1);
        admin.setStatus(Constant.INACTIVE_STATUS);
        admin.setPendingFor(Constant.PENDING_STATUS);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUserGroup());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        response.setRejectReason(Constant.REJECT_STATUS);
        return response;
    }

    public Response rejectEditUser(Long requestId) {
        Optional<UamUser> adminOptional = uamUserRepository.findById(requestId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser admin = adminOptional.get();

        admin.setStatus(Constant.INACTIVE_STATUS);
        admin.setPendingFor(Constant.PENDING_STATUS);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUserGroup());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        response.setRejectReason(Constant.REJECT_STATUS);
        return response;

    }

    public Response approveEditUser(Long requestId) {
        Optional<UamUser> adminOptional = uamUserRepository.findById(requestId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser admin = adminOptional.get();
        if (admin.getStatus().equals(Constant.ACTIVE_STATUS) && admin.getPendingFor().equals(Constant.PENDING_STATUS_EDIT))
            ;
        {
            admin.setStatus(Constant.PENDING_STATUS);
        }
        admin.setStatus(Constant.ACTIVE_STATUS);
        admin.setPendingFor(Constant.PENDING_STATUS);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUserGroup());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        return response;
    }

    public Response deactivateUser(Request request, Long requestId) {
        Optional<UamUser> adminOptional = uamUserRepository.findById(requestId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser admin = adminOptional.get();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = request.getEffectiveDate();
        if (startDate.isBefore(currentDate)) {
            throw new EffectiveStartDateException("effective start date is not before today's date");
        }
        admin.setEffectiveDate(request.getEffectiveDate());
        admin.setPendingFor(Constant.PENDING_STATUS_DEACTIVATE);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setUserGroup(admin.getUserGroup());
        response.setLevel(admin.getLevel());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        return response;

    }

}
