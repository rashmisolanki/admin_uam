package org.myproject.uam.comman;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final String ACTIVE_STATUS="active";
    public static final String INACTIVE_STATUS="inactive";
    public static final String NEW_STATUS="new";
    public static final String PENDING_STATUS_APPROVAL="pending-approval(add)";
    public static final String PENDING_STATUS_FUTURE="add-future";
    public static final String PENDING_STATUS="-";
    public static final String PENDING_STATUS_EDIT="pending-approval(Edit)";

    public static final String REJECT_STATUS="effective start is not correct";

    public static final String PENDING_STATUS_DEACTIVATE="pending-approval(deactivate)";
    public static final String ACTION_ON_APPROVAL_ADD="add";

    public static final String ACTION_ON_APPROVAL="approve";

    public static final String ACTION_ON_APPROVAL_REJECT="reject";
    public static final String USER_ALREADY_PRESENT_EXCEPTION="User already Present";
    public static final String EFFECTIVE_START_DATE_EXCEPTION="effective start date should not be before current date";
    public static Map<String,String> userGroupAbbreviation=new HashMap<>();

    static
    {
      userGroupAbbreviation.put("product_investment_l3","PI.L3");
      userGroupAbbreviation.put("product_investment_l2","PI.l2");
    }
}
