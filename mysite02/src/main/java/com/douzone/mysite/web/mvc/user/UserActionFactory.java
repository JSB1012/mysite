package com.douzone.mysite.web.mvc.user;

import com.douzone.mysite.web.mvc.main.DefaultAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {
	Action action = null;

	@Override
	public Action getAction(String actionName) {
		if("joins".equals(actionName)) {
			//action = new JoinAction();
			
		}else if("".equals(actionName)) {
			
		} else {
			action = new DefaultAction();
		}
		return action;
	}

}
