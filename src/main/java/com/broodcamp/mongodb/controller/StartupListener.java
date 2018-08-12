package com.broodcamp.mongodb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import com.broodcamp.mongodb.model.Member;
import com.broodcamp.mongodb.service.MemberRegistration;

/**
 * @author Edward P. Legaspi
 **/
@Singleton
@Startup
public class StartupListener {

	@Inject
	private EntityManager em;
	
	@Inject
	private MemberRegistration memberRegistration;

	@PostConstruct
	private void init() {
		Member newMember = new Member();
		newMember.setName("Jane Doe");
		newMember.setEmail("jane@mailinator.com");
		newMember.setPhoneNumber("2125551234");
		try {
			memberRegistration.register(newMember);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
