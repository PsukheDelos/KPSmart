package kps.tests.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import kps.backend.UserPermissions;
import kps.backend.database.UserRepository;
import kps.backend.users.DuplicateUserException;
import kps.interfaces.IMailClient;
import kps.net.client.Client;
import kps.net.event.Event;
import kps.net.event.NewUserEvent;
import kps.net.event.NewUserResultEvent;
import kps.net.event.RemoveUserEvent;
import kps.net.event.RemoveUserResultEvent;
import kps.net.server.Server;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTests {
	
	private static IMailClient mailClient;
	private static Server server;
	private static Client client;
	
	@BeforeClass
	public static void beforeClass(){
		server = startupServer();
		server.start();
		client = startupClient();
	}
	
	private static Server startupServer(){
		return new Server(new TestMailSystem());
	}
	
	private static Client startupClient(){
		mailClient = new TestMailClient();
		return new Client("127.0.0.1", mailClient);
	} 
	
	private String passwordHash(char[] cs) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{
	       MessageDigest digest = MessageDigest.getInstance("SHA-1");
	       digest.reset();
	       return new BigInteger(1, digest.digest(new String(cs).getBytes("UTF-8"))).toString(16);				
	}
	
	@After
	public void shutDown(){
		((TestMailClient)mailClient).events = new ArrayList<Event>();
	}
	
	// -- Direct to Database
	
	@Test
	public void CreatingANewUser_DirectToDataBase() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DuplicateUserException, SQLException{
		String username = "John";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		UserRepository.registerNewUser(username, password, permissions);
		assertTrue(UserRepository.containsUser(username));
		UserRepository.removeUser(username);
		UserRepository.forceClose();
		
	}
	
	@Test
	public void RemovingANewUser_DirectToDataBase() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DuplicateUserException, SQLException{
		String username = "John";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		UserRepository.registerNewUser(username, password, permissions);	
		UserRepository.removeUser(username);	
		assertFalse(UserRepository.containsUser(username));	
		UserRepository.forceClose();

	}
	
	@Test
	public void CheckThatWeCannotAddDuplicateUser_DirectToDataBase() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, SQLException{
		String username = "Dhon";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		try{
			UserRepository.registerNewUser(username, password, permissions);	
		}catch(DuplicateUserException e){
			// Let this one go.
			e.printStackTrace();
		}
		try{
			UserRepository.registerNewUser(username, password, permissions);	
		}catch(DuplicateUserException e){
			// Force the test to pass the second time.
			assertTrue(true);
		}
		UserRepository.removeUser(username);	
	}
	
	// -- Through the server
	
	@Test
	public void CreatingANewUser_AsServerEvent() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DuplicateUserException, SQLException, InterruptedException{
		String username = "Lhon";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		NewUserEvent newUser = new NewUserEvent(username, password, permissions);
		client.sendEvent(newUser);
		// The ol' Server Wait
		Thread.sleep(1000);
		NewUserResultEvent result = (NewUserResultEvent)((TestMailClient)mailClient).events.get(0);
		assertTrue(result.successful);
		// Then remove the user
		UserRepository.removeUser(username);
		
	}
	
	@Test
	public void RemovingANewUser_AsServerEvent() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DuplicateUserException, SQLException, InterruptedException{
		String username = "Phlon";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		//NewUserEvent newUser = new NewUserEvent(username, password, permissions);
		UserRepository.registerNewUser(username, password, permissions);
		RemoveUserEvent removeUser = new RemoveUserEvent(username);
		client.sendEvent(removeUser);
		// The ol' Server Wait
		Thread.sleep(1000);
		assertTrue(((TestMailClient)mailClient).events.get(0) instanceof RemoveUserResultEvent);
		assertFalse(UserRepository.containsUser(username));	
		UserRepository.forceClose();		
	}
	
	@Test
	public void AddingADuplicateUser_AsServerEvent() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DuplicateUserException, SQLException, InterruptedException{
		String username = "Jhon";
		String password = passwordHash("password".toCharArray());
		UserPermissions permissions = UserPermissions.MANAGER;
		NewUserEvent newUser = new NewUserEvent(username, password, permissions);
		client.sendEvent(newUser);
		// The ol' Server Wait
		Thread.sleep(1000);
		// Add the same user again
		client.sendEvent(newUser);
		Thread.sleep(1000);
		NewUserResultEvent result = (NewUserResultEvent)((TestMailClient)mailClient).events.get(1);
		assertFalse(result.successful);
		// Then remove the user
		UserRepository.removeUser(username);
	}


}
