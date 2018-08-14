/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.broodcamp.mongodb.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.se.manifest.ManifestDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.broodcamp.mongodb.model.Member;
import com.broodcamp.mongodb.service.MemberRegistration;
import com.broodcamp.mongodb.util.Resources;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		String manifest = Descriptors.create(ManifestDescriptor.class).attribute("Dependencies", "org.hibernate.ogm:5.4 services, org.hibernate.ogm.mongodb:5.4 services")
				.exportAsString();

		return ShrinkWrap.create(WebArchive.class, "test.war") //
				.addClasses(Member.class, MemberRegistration.class, Resources.class) //
				.addAsResource(new StringAsset(manifest), "META-INF/MANIFEST.MF") //
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml") //
				// doesn't work on this version
//				.addAsResource("jboss-deployment-structure.xml", "WEB-INF/jboss-deployment-structure.xml") //
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") //
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	MemberRegistration memberRegistration;

	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		Member newMember = new Member();
		newMember.setName("Jane Doe");
		newMember.setEmail("jane@mailinator.com");
		newMember.setPhoneNumber("2125551234");
		memberRegistration.register(newMember);
		assertNotNull(newMember.getId());
		log.info(newMember.getName() + " was persisted with id " + newMember.getId());
	}

}
