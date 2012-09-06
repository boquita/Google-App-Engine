package com.hirirbox.Ofy4.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.hirirbox.Ofy4.DSConnection;
import com.hirirbox.Ofy4.datastore.Message;
import com.hirirbox.Ofy4.datastore.MessageIndex;
import com.hirirbox.Ofy4.datastore.User;


/**
 *
 * @author Iván Rodríguez - Hodeian
 *
 */
@SuppressWarnings("serial")
public class LoadGroupServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		
		String action = req.getParameter("query");
		
		if (action.equals("put")) {
		
			User user = new User ();
			user.setName("Ivan");
			user.setId(100L);
		
			Message msg = new Message();
			
			msg.setCreationDate(new Date());
			msg.setMessage("Hola Ivan");
			msg.setSubject("Saludo");
			
			Key<User> userKey = DSConnection.getInstance().putEntity(user);
			
			msg.setParentKey(Ref.create(userKey));
	
			Key<Message> msgKey = DSConnection.getInstance().putEntity(msg);
			
			MessageIndex mi = new MessageIndex();
				List<Key<User>> receiverKeyList = new ArrayList<Key<User>> ();
				receiverKeyList.add(userKey);
			mi.setDate(new Date());
			mi.setReceiverKeyList(receiverKeyList);
			mi.setParentKey(Ref.create(msgKey));
			
			DSConnection.getInstance().putEntity(mi);
			
			resp.setContentType("text/html");
			resp.getWriter().println("<ul><li>Key de user: " + userKey + "</li><li>" + msgKey + "</li>" + 
			"<li><a href=\"/load?query=retrieve&userId=" + user.getId()  + "&messageId=" + msgKey.getId()  + "\">Consulta aleatoria (acceso directo)</a></li>" +
			"<li><a href=\"/load?query=queryRI\">Consulta Relation Index</a></li></ul>");
			
		} else if(action.equals("queryRI")) {
			
			MessageIndex msgIndex = DSConnection.getInstance().ofy().load().group(User.class, Message.class).type(MessageIndex.class).list().iterator().next();
			
			Message msg = msgIndex.getParentKey().get();
			
			resp.setContentType("text/html");
			resp.getWriter().println("<h1>Usuario:  " + msg.getUser().getName() + "</h1>");
			resp.getWriter().println("<h1>Mensaje consulta query: " + msg.getSubject() + "</h1>");
			
			
		} else if(action.equals("query")) {
			
			Message msg = DSConnection.getInstance().ofy().load()/*.group(User.class)*/.type(Message.class).list().iterator().next();
			
			resp.setContentType("text/html");
			resp.getWriter().println("<h1>Mensaje consulta query: " + msg.getSubject() + "</h1>");
			
			
		} 
		else if(action.equals("retrieve")) {
			
			String userId = req.getParameter("userId");
			String messageId = req.getParameter("messageId");
			
			Key<User> userKey = Key.create(User.class, new Long(userId));
			Key<Message> msgKey = Key.create(userKey, Message.class, new Long(messageId));
			
			Message msg = DSConnection.getInstance().ofy().load().group(User.class).key(msgKey).get();
			
			resp.setContentType("text/html");
			resp.getWriter().println("<h1>Mensaje consulta aleatoria: " + msg.getSubject() + "</h1><br><a href=\"/load?query=query\">Consulta por query</a>");
			
		}
	}
}
