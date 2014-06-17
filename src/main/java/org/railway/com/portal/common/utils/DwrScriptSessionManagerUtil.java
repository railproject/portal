package org.railway.com.portal.common.utils;

import javax.servlet.ServletException;  
  
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.directwebremoting.Container;  
import org.directwebremoting.ServerContextFactory;  
import org.directwebremoting.event.ScriptSessionEvent;  
import org.directwebremoting.event.ScriptSessionListener;  
import org.directwebremoting.extend.ScriptSessionManager;  
import org.directwebremoting.servlet.DwrServlet;
import org.railway.com.portal.service.ShiroRealm;

public class DwrScriptSessionManagerUtil extends DwrServlet {
	private static final Logger logger = Logger.getLogger(DwrScriptSessionManagerUtil.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		ScriptSessionListener listener = new ScriptSessionListener() {
			public void sessionCreated(ScriptSessionEvent ev) {
				ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
				logger.debug("a ScriptSession is created! username:"+user.getUsername());
				ev.getSession().setAttribute("userInfo", user);
			}

			public void sessionDestroyed(ScriptSessionEvent ev) {
				logger.debug("a ScriptSession is distroyed  "+((ShiroRealm.ShiroUser)ev.getSession().getAttribute("userInfo")).getUsername());
			}
		};
		manager.addScriptSessionListener(listener);
	}
}
