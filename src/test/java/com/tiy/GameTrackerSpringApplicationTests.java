package com.tiy;

import org.hibernate.annotations.SourceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTrackerSpringApplicationTests {

	@Autowired
	UserRepository userRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	UserEventRepository userEventRepo;

	@Autowired
	ContactRequestRepository contactRequestRepo;

	@Test
	public void contextLoads() {
	}

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testCreateUser(){
		logger.debug("testContactRequest() - debug");
		logger.info("testContactRequest() - info");
		logger.warn("testContactRequest() - warn");
		logger.error("testContactRequest() - error");

		User testUser = null;
		User dbUser = null;

		try {

			testUser = new User("test-user121", "test-password121");
			userRepo.save(testUser);
			System.out.println("Created user with Id" + testUser.getId());

			dbUser = userRepo.findOne(testUser.getId());
			assertNotNull(dbUser);

		} finally {
			if (testUser!= null) {
				userRepo.delete(testUser.getId());
			}
			if (dbUser != null) {
				dbUser = userRepo.findOne(testUser.getId());
//				assertNull(dbUser);
			}

		}

	}
	@Test
	public void testCreateEvent(){
		Event testEvent = new Event ("test-events", "test-locations");
		eventRepo.save(testEvent);
		System.out.println("Created event with Id" + testEvent.getId());

		Event dbEvent = eventRepo.findOne(testEvent.getId());
		assertNotNull(dbEvent);


		eventRepo.delete(testEvent.getId());
		dbEvent = eventRepo.findOne(testEvent.getId());
		assertNull(dbEvent);
	}

	@Test
	public void testAttendEvent(){
		Event testEvent = new Event ("test-eventss", "test-locationss");
		eventRepo.save(testEvent);
		System.out.println("Created event with Id" + testEvent.getId());

		Event testEvent2 = new Event ("test-events2", "test-locations2");
		eventRepo.save(testEvent2);
		System.out.println("Created event with Id" + testEvent2.getId());


		User testUser = new User ("test-user1", "test-password1");
		userRepo.save(testUser);
		System.out.println("Created user with Id" + testUser.getId());

		User secondTestUser2 = new User ("test-user2", "test-password2");
		userRepo.save(secondTestUser2);
		System.out.println("Created user with Id" + secondTestUser2.getId());


		UserEvent testUserEvent = new UserEvent(testUser, testEvent);
		userEventRepo.save(testUserEvent);
		UserEvent anotherUserEvent = new UserEvent (testUser, testEvent2);
		userEventRepo.save(anotherUserEvent);
		UserEvent secondTestUserEvent = new UserEvent(secondTestUser2, testEvent2);
		userEventRepo.save(secondTestUserEvent);

		//make sure that we can find all the events for the user
		List<UserEvent> dbUserEvents = userEventRepo.findByUser(testUser);
		assertNotNull(dbUserEvents);
		assertEquals(2, dbUserEvents.size());
		for (UserEvent dbUserEvent: dbUserEvents){
			System.out.println("User event ID" + dbUserEvent.getId()+ " for event " + dbUserEvent.getEvent().getId());
		}

		//make sure we can find a specific event for 1 user
		List <UserEvent> listForSpecificEvent = userEventRepo.findByUserAndEvent(testUser, testEvent2);
		assertNotNull(listForSpecificEvent);
		assertEquals(1, listForSpecificEvent.size());
		assertEquals(testEvent2.getId(), listForSpecificEvent.get(0).getEvent().getId());


		List<UserEvent> listUserEventsForSecondUser = userEventRepo.findByUser(secondTestUser2);
		assertNotNull(listUserEventsForSecondUser);
		assertEquals(1, listUserEventsForSecondUser.size());
		for (UserEvent dbSecondUserEvent: listUserEventsForSecondUser){
			System.out.println("User event ID" + dbSecondUserEvent.getId()+ " for event " + dbSecondUserEvent.getEvent().getId());
		}

		//make sure we can find a specific event for 1 user
		List <UserEvent> listForSpecificEventBySecondUser = userEventRepo.findByUserAndEvent(secondTestUser2, testEvent2);
		assertNotNull(listForSpecificEventBySecondUser);
		assertEquals(1, listForSpecificEventBySecondUser.size());
		assertEquals(testEvent2.getId(), listForSpecificEventBySecondUser.get(0).getEvent().getId());



		userEventRepo.delete(secondTestUserEvent);
		userEventRepo.delete(anotherUserEvent);
		userEventRepo.delete(testUserEvent);
		userRepo.delete(testUser.getId());
		userRepo.delete(secondTestUser2);
		eventRepo.delete(testEvent);
		eventRepo.delete(testEvent2);

	}

	@Test
	public void testContactRequest() {
		User requestingUser = new User ("test-user1321", "test-password1321");
		requestingUser =  userRepo.save(requestingUser);

		User targetUser = new User ("test-user2131", "test-password2131");

		targetUser = userRepo.save(targetUser);

		ContactRequest contactRequest = new ContactRequest(requestingUser, targetUser, "PENDING_APPROVAL");

		contactRequest = contactRequestRepo.save(contactRequest);

		List<ContactRequest> dbListforFindByUserRequesting = contactRequestRepo.findByRequestingUser(requestingUser);
		assertNotNull(dbListforFindByUserRequesting);
		assertEquals(targetUser.getId(), dbListforFindByUserRequesting.get(0).getTargetUser().getId());

		List<ContactRequest> dbListforFindByTargetUser =  contactRequestRepo.findByTargetUser(targetUser);
		assertNotNull(dbListforFindByTargetUser);
		assertEquals(requestingUser.getId(), dbListforFindByTargetUser.get(0).getRequestingUser().getId());

		List<ContactRequest> dbListforFindByBoth =  contactRequestRepo.findByRequestingUserAndTargetUser(requestingUser, targetUser);
		assertNotNull(dbListforFindByBoth);
		assertEquals(targetUser.getId(), dbListforFindByBoth.get(0).getTargetUser().getId());

		contactRequestRepo.delete(contactRequest);
		userRepo.delete(requestingUser);
		userRepo.delete(targetUser);
	}
	@Test
	public void testTargetUserApprovedContact(){
		User requestingUser = new User ("test-user1321", "test-password1321");
		requestingUser =  userRepo.save(requestingUser);

		User targetUser = new User ("test-user2131", "test-password2131");

		targetUser = userRepo.save(targetUser);

		ContactRequest contactRequest = new ContactRequest(requestingUser, targetUser, "APPROVED");

		contactRequest = contactRequestRepo.save(contactRequest);

		List<ContactRequest> dbListforFindByUserRequesting = contactRequestRepo.findByRequestingUser(requestingUser);
		assertNotNull(dbListforFindByUserRequesting);
		assertEquals(targetUser.getId(), dbListforFindByUserRequesting.get(0).getTargetUser().getId());

		List<ContactRequest> dbListforFindByTargetUser =  contactRequestRepo.findByTargetUser(targetUser);
		assertNotNull(dbListforFindByTargetUser);
		assertEquals(requestingUser.getId(), dbListforFindByTargetUser.get(0).getRequestingUser().getId());

		List<ContactRequest> dbListforFindByBoth =  contactRequestRepo.findByRequestingUserAndTargetUser(requestingUser, targetUser);
		assertNotNull(dbListforFindByBoth);
		assertEquals(targetUser.getId(), dbListforFindByBoth.get(0).getTargetUser().getId());

		contactRequestRepo.delete(contactRequest);
		userRepo.delete(requestingUser);
		userRepo.delete(targetUser);
	}
	@Test
	public void testTargetUserDeniedContact(){
		User requestingUser = new User ("test-user1321", "test-password1321");
		requestingUser =  userRepo.save(requestingUser);

		User targetUser = new User ("test-user2131", "test-password2131");

		targetUser = userRepo.save(targetUser);

		ContactRequest contactRequest = new ContactRequest(requestingUser, targetUser, "DENIED");

		contactRequest = contactRequestRepo.save(contactRequest);

		List<ContactRequest> dbListforFindByUserRequesting = contactRequestRepo.findByRequestingUser(requestingUser);
		assertNotNull(dbListforFindByUserRequesting);
		assertEquals(targetUser.getId(), dbListforFindByUserRequesting.get(0).getTargetUser().getId());
		assertTrue(dbListforFindByUserRequesting.get(0).getStatus().equals("DENIED"));

		contactRequestRepo.delete(contactRequest);
		userRepo.delete(requestingUser);
		userRepo.delete(targetUser);
	}
}
