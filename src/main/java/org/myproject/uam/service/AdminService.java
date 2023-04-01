package org.myproject.uam.service;

import org.myproject.uam.dto.Request;
import org.myproject.uam.dto.Response;

import java.util.List;

public interface AdminService {
    public Response createNewUser(Request request);
    public Response approvalRequest(Long requestId);

    public List<Response> viewAllUser();

    public Response viewUser(Long requestId);

    public Response rejectUser(Long requestId);

    public Response editUser(Request request,Long requestId);

    public Response approveEditUser(Long requestId);
}
