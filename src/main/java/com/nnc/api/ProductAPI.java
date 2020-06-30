package com.nnc.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nnc.dto.ContactDTO;
import com.nnc.dto.PhoneDTO;
import com.nnc.entity.Contact;
import com.nnc.entity.Phone;
import com.nnc.entity.Role;

@RestController(value="contactAPI")
public class ProductAPI {
	
	@Autowired
	SessionFactory ss;
	
	@PostMapping("/api/contact")
	@Transactional
	public ContactDTO createNew(@RequestBody ContactDTO contactDTO) {
		Session s = ss.getCurrentSession();
		System.out.println("contact : "+contactDTO.getName()+", "+contactDTO.getAge());
		// MAPPER
//		Contact contact = new Contact();
//		contact.setName(contactDTO.getName());
//		contact.setAge(contactDTO.getAge());
//		Set<Phone> ps = new HashSet<Phone>();
		for(PhoneDTO pdto : contactDTO.getPhones()) {
//			Phone p = new Phone();
//			p.setContext(pdto.getContext());
			System.out.println("Phone : "+pdto.getContext());
			//contact.addPhone(p);
		}
//		contact.setPhones(ps);
		
		// data
//		Integer id =  (Integer) s.save(contact);
//		
//		Contact c = s.createQuery("from contact  where id = :id", Contact.class)
//				.setParameter("id", id).getSingleResult();
		
		// mapper
//		ContactDTO cto = new ContactDTO();
//		cto.setId(id);
//		cto.setName(c.getName());
//		cto.setAge(c.getAge());
//		List<PhoneDTO> pto = new ArrayList<PhoneDTO>();
//		for(Phone p : c.getPhones()) {
//			PhoneDTO p1 = new PhoneDTO();
//			p1.setId(p.getId());p1.setContext(p.getContext());
//			pto.add(p1);
//		}
//		cto.setPhones(pto);
//		return cto;
		return null;
	}	
}
