package com.hirirbox.Ofy4.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import com.hirirbox.Ofy4.DSConnection;
import com.hirirbox.Ofy4.datastore.Message;
import com.hirirbox.Ofy4.datastore.MessageIndex;
import com.hirirbox.Ofy4.datastore.User;


@SuppressWarnings("serial")
public class Ofy4TestServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String query = req.getParameter("query");

		
		if (query == null) {
			List<Key<User>> userMap = createUsers();
			createMessages(userMap);

			
		 resp.setContentType("text/html");				
		 resp.getWriter().println("<h1>Usuarios creados con éxito</h1>");
			
			
		} else {
	
			String userId = req.getParameter("userId");
			
			Key<User> userKey = Key.create(User.class, new Long(userId));
						
			if (query.equals("relationIndex")) {
				
				MessageIndex messageIndex = (MessageIndex) getMessagesByRelationIndex (userKey).list().iterator().next();
				
				System.out.println ("Id del index " + messageIndex.getId());	
				
				System.out.println ("Parent del index " + messageIndex.getParentKey());	
				
				Message message = messageIndex.getParentKey().get();
				
				String response = "<h1>Mensaje consultado</h1><ul>";
				response += "<li>Nombre de usuario: " + message.getUser().getName() + "</li>";
				response += "<li>Asunto del mensaje: " + message.getSubject() + "</li>";
				response += "</ul>";
				
				resp.setContentType("text/html");
				resp.getWriter().println(response);

				
			} else {
				
				getMessagesByReference (userKey);
			}
		}
	}
	
	private List<Key<User>> createUsers () {
		
		System.out.println ("createUsers");
		
		List<Key<User>> usuarios = new ArrayList ();
		
		for (int i = 1 ; i < 100 ; i++) {
			User user = new User ();
			user.setId(new Long(i));
			user.setName("Usuario_" + i);
			usuarios.add(DSConnection.getInstance().putEntity(user));
		}
		
		return usuarios;
	}
	
	private void createMessages (List<Key<User>> userMap) {
		
		
		System.out.println ("createMessages");

		
		for (int i = 1; i < userMap.size() ; i++) {
			
			
			Message message = new Message ();
			message.setParentKey(Ref.create(userMap.get(i)));
			message.setSubject( "Mensaje nº " + i);
			
			Key<Message> messageKey = DSConnection.getInstance().putEntity (message);
			
			MessageIndex mi = new MessageIndex();
			mi.setParentKey(Ref.create(messageKey));
			mi.setReceiverKeyList(userMap);
			mi.setDate(new Date());
			
			DSConnection.getInstance().putEntity(mi);
		}
		
	}
	
	private Query<MessageIndex> getMessagesByRelationIndex (Key<User> userKey) {
		
		
		System.out.println ("getMessagesByRelationIndex :: usuario :: " + userKey.getId());
		Query<MessageIndex> query = DSConnection.getInstance().ofy()
				.load()
				.group(User.class, Message.class)
				.type(MessageIndex.class);
		return query;
		
	}
	
	private Map getMessagesByReference (Key<User> userKey) {
		
		System.out.println ("getMessagesByReference :: usuario :: " + userKey.getId());
		Query<MessageIndex> query = DSConnection.getInstance().ofy().load().group(User.class).type(MessageIndex.class).filter("receiverKeyList", userKey);
		return DSConnection.getInstance().getEntitities(query.keys().list());
	}
	
	
}
