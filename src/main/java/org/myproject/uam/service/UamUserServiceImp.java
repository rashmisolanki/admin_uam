package org.myproject.uam.service;

import org.myproject.uam.comman.Constant;
import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;
import org.myproject.uam.entity.UamUser;
import org.myproject.uam.entity.UamUserGroupId;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Response approvalRequest(Long userId) {
        Optional<UamUser> uamUser = uamUserRepository.findById(userId);
        uamUser.orElseThrow(() -> new PfNumberNotFound("pf number is not present"));
        UamUser updatedUamUser=userMapper.populateApprovalAddUser(uamUser.get());
        UamUser savedUamUser=uamUserRepository.save(updatedUamUser);
        UamUserGroupId userGroupId= userMapper.populateUamUserGroupId(savedUamUser);
        uamUserGroupIdRepository.save(userGroupId);
        return userMapper.prepareApprovalResponse(savedUamUser);
    }

    public Response rejectAddUser(Long userId) {

        Optional<UamUser> findAllUamUser = uamUserRepository.findById(userId);
        findAllUamUser.orElseThrow(() -> new NoRequestFoundException("request not found"));
        UamUser uamUser=userMapper.populateRejectAddUser(findAllUamUser.get());
        uamUserRepository.save(uamUser);
        return userMapper.prepareResponseRejectAddUser(uamUser);
    }

    public Response editUser(Request request, Long userId) {
        Optional<UamUser> dbUamUser = uamUserRepository.findById(userId);
        dbUamUser.orElseThrow(() -> new NoRequestFoundException("no request found"));
       Optional<UamUserGroupId> dbUamUserGroup= uamUserGroupIdRepository.findById(dbUamUser.get().getPfNumber());
        UamUser uamUser=userMapper.populateEditUser(request,dbUamUser.get(),dbUamUserGroup.get());
        uamUser.setOfId(userId);
        UamUser saveUamUser=uamUserRepository.save(uamUser);
       return userMapper.prepareResponseEditUser(saveUamUser);
    }

    public Response approveEditUser(Long userId) {
        Optional<UamUser> uamUser = uamUserRepository.findById(userId);
        uamUser.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser uamUserApprove=userMapper.populateApproveEditUser(uamUser.get());
        UamUser savedUamUser=uamUserRepository.save(uamUserApprove);
        if(savedUamUser.getEffectiveDate().equals(LocalDate.now())) {
            UamUserGroupId userGroupId = userMapper.populateUamUserGroupId(savedUamUser);
            uamUserGroupIdRepository.save(userGroupId);
        }
        return userMapper.prepareResponseEditApproveUser(savedUamUser);
    }

    public Response rejectEditUser(Long userId) {
        Optional<UamUser> dbUamUser = uamUserRepository.findById(userId);
        dbUamUser.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser uamUser=userMapper.prepareEditRejectUser(dbUamUser.get());
        uamUserRepository.save(uamUser);
        return userMapper.prepareResponseEditRejectUser(uamUser);

    }

    public List<Response> viewAllUser() {
        List<Object[]> result=new ArrayList<>();
        result=uamUserRepository.findAllUserByIsDeleted();
        List<Response> uamResponseList=userMapper.resultOfObject(result);
        return uamResponseList;
    }
    public Response viewUser(Long userId) {
        Optional<UamUser> uamUser = uamUserRepository.findById(userId);
        uamUser.orElseThrow(() -> new NoRequestFoundException("no Request raised for User"));
        return userMapper.prepareViewUserResponse(uamUser.get());

    }

    public Response deactivateUser(Request request, Long userId) {
        Optional<UamUser> adminOptional = uamUserRepository.findById(userId);
        adminOptional.orElseThrow(() -> new NoRequestFoundException("no request found"));
        UamUser admin = adminOptional.get();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = request.getEffectiveDate();
        if (startDate.isBefore(currentDate)) {
            throw new EffectiveStartDateException("effective start date is not before today's date");
        }
        admin.setEffectiveDate(request.getEffectiveDate());
        admin.setPendingFor(Constant.PENDING_APPROVAL_DEACTIVATE);
        uamUserRepository.save(admin);
        Response response = new Response();
        response.setName(admin.getName());
        response.setEmailId(admin.getEmailId());
        response.setPfNumber(admin.getPfNumber());
        response.setStatus(admin.getStatus());
        response.setPendingFor(admin.getPendingFor());
        response.setEffectiveDate(admin.getEffectiveDate());
        response.setUserId(admin.getUserId());
        response.setReason(admin.getReason());
        return response;

    }

}
