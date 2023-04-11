package org.myproject.uam.comman;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final String ACTIVE="active";
    public static final String INACTIVE="inactive";
    public static final String NEW="new";
    public static final String PENDING_APPROVAL_ADD="pending-approval(add)";
    public static final String ADD_FUTURE="add-future";
    public static final String DASH="-";
    public static final String PENDING_APPROVAL_EDIT="pending-approval(Edit)";

    public static final String REJECT_STATUS="effective start is not correct";

    public static final String PENDING_APPROVAL_DEACTIVATE="pending-approval(deactivate)";
    public static final String ADD="add";

    public static final String APPROVAL="approve";

    public static final String REJECT="reject";
    public static final String EDIT="edit";
    public static final String USER_ALREADY_PRESENT_EXCEPTION="User already Present";
    public static final String EFFECTIVE_START_DATE_EXCEPTION="effective start date should not be before current date";
    public static Map<String,String> userGroupAbbreviation=new HashMap<>();

    static
    {
      userGroupAbbreviation.put("product_investment_l3","PI.l3");
      userGroupAbbreviation.put("product_investment_l2","PI.l2");
    }
}
