package org.railway.com.portal.service.message;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.railway.com.portal.entity.Role;
import org.railway.com.portal.service.ShiroRealm;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendMsgService {
	private static final Logger logger = Logger.getLogger(SendMsgService.class);
	
	/**
	 * 消息推送
	 * @param message 消息内容
	 * @param page 页面url
	 * @param jsFuncName 页面js方法名称
	 */
	public void sendMessage(final String message, final String pageUrl, final String jsFuncName) {
		logger.debug("~~~~向页面page："+pageUrl +"  推送消息内容："+message +" jsFuncName:"+jsFuncName);
		
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if ((pageUrl).equals(session.getPage())) {
					return true;
				} else {
					return false;
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall(jsFuncName, message);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	
	
	
	/**
	 * 消息推送给某用户
	 * @param username 登录名
	 * @param message 消息内容
	 * @param page 页面url
	 * @param jsFuncName 页面js方法名称
	 */
	public void sendMessageToUser(final String username, final String message, final String pageUrl, final String jsFuncName) {
		logger.debug("~~~~username:"+username+"  向页面page："+pageUrl +"  推送消息内容："+message +" jsFuncName:"+jsFuncName);
		
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)session.getAttribute("userInfo");
				//System.err.println("!!!!!!!! session.getPage()="+session.getPage()+" session.username ="+user.getUsername());
				if ((pageUrl).equals(session.getPage()) && user!=null && user.getUsername().equals(username)) {
					return true;
				} else {
					return false;
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall(jsFuncName, message);
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	
	
	
	/**
	 * 消息推送给某类角色
	 * @param rolename 
	 * @param message 消息内容
	 * @param page 页面url
	 * @param jsFuncName 页面js方法名称
	 */
	public void sendMessageToRole(final String rolename, final String message, final String pageUrl, final String jsFuncName) {
		logger.info("~~~~rolename:"+rolename+"  向页面page："+pageUrl +"  推送消息内容："+message +" jsFuncName:"+jsFuncName);
		
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if ((pageUrl).equals(session.getPage())) {
					ShiroRealm.ShiroUser shiroUser = (ShiroRealm.ShiroUser)session.getAttribute("userInfo");
	                if(shiroUser!=null && shiroUser.getRoleList() != null && !shiroUser.getRoleList().isEmpty()) {
	                	for(Role role: shiroUser.getRoleList()) {
	                		if (rolename.equals(role.getName())) {
	                			return true;
	                		}
	                    }
	                } else {
	                	return false;
	                }
				} else {
					return false;
				}
				return false;
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall(jsFuncName, message);
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
