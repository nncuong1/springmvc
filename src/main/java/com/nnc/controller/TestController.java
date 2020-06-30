package com.nnc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nnc.dto.ContactDTO;
import com.nnc.entity.Authority;
import com.nnc.entity.Contact;
import com.nnc.entity.Phone;
import com.nnc.entity.Role;
import com.nnc.util.ConfigLoader;
import com.nnc.util.Message;
import com.nnc.util.Post;

@Controller
@RequestMapping("/test")
public class TestController {
//	
//	@Autowired
//	SessionFactory ss;
	
//	@Transactional
	@GetMapping("/123")
	public String message(Model model) {
//		Contact c= new Contact();
//		c.setAge(29);
//		c.setName("cc123");
//		Session s = ss.getCurrentSession();
//		Integer id = (Integer) s.save(c);
//		if(id!=null) {
//			System.out.println("save success : "+id);
//		}
//		Contact cc = s.createQuery("from contact  where id = :id", Contact.class)
//				.setParameter("id", id).getSingleResult();
//		System.out.println(" : "+cc.getId()+" "+cc.getAge()+" "+cc.getName());
//		List<Contact> ctc = s.createQuery("from contact",Contact.class).getResultList();
//		for(Contact ccc: ctc) {
//			System.out.println(ccc.getName());
//		}
//		Message msg = new Message();
//		msg.setId(1);
//		msg.setContent("Alo alo 12343");
//		List<String> ops = Arrays.asList("red","blue","green");
//		List<Post> posts = Arrays.asList(new Post(1,"post1"), new Post(2,"post2"));
//		msg.setOptions(ops);
//		msg.setPosts(posts);
//		model.addAttribute("msg", msg);
		return "register";
	}
//	

//	
//	@Transactional
	@GetMapping("/t1")
	public String home(Model model) {
	model.addAttribute("productDtoForm", new ContactDTO());
	//model.addAttribute("msg","cuong");
//	Session s = ss.getCurrentSession();
//		Contact ct = s.get(Contact.class, 5);
	//	List<Contact> ctc = s.createQuery("from contact",Contact.class).getResultList();
	//	Set<Phone> ps = ct.getPhones();
		
//		for(Phone p : ps) {
//			System.out.println(p.getContext());
//		}

	
//		Phone p = s.createQuery("select p from phone p join fetch p.contact where p.id = :id ", Phone.class)
//				.setParameter("id", 1).getSingleResult();
//		Contact c = p.getContact();
//		System.out.println("1. : "+c.getId()+c.getName());
		//System.out.println("2. : "+c.getName());
	//	model.addAttribute("contact", new Contact());
//		Session session = sessionFactory.getCurrentSession();
//		
//		String sql = "from contact";
//		List<Contact> list = session.createQuery(sql).getResultList();
		
//		List<Contact> list = contactService.getAllContacts();
//		for(Contact ct : list) {
//			System.out.println("Contact	 : "+ct.getName());
//		}	
		return "lol";
	}
	
	@PostMapping("/t1")
	public String formTest(@ModelAttribute("productDtoForm") ContactDTO contactDTO) {
		System.out.println(contactDTO.getName());
		return "lol";
	}
	
//	@Transactional
//	@GetMapping
//	public String test3() {
//		Session s = ss.getCurrentSession();
//		//Role role = s.get(Role.class,1);
//		List<Authority> auth = s.createQuery(
//                "SELECT a FROM authority a JOIN FETCH a.menu WHERE a.role.id= 1",
//                Authority.class).getResultList();
//		for(Authority a : auth) {
//			System.out.println("Hello :"+a.getMenu().getName());
//		}
//		return "lol";
//	}
//	
//	@Transactional
	@PostMapping
	public String addContact(@ModelAttribute("contact") Contact contact) throws IllegalStateException, IOException {
		
		MultipartFile file = contact.getMultipartFile();
		String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
		
		contact.setImgUrl(fileName);
		processUploadFile(file,fileName);
//		try {
//			File newFile = new File("F:\\data\\uploadInventory\\test\\"+fileName);
//			
//			file.transferTo(newFile);
//			contact.setImgUrl(fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		Session s = ss.getCurrentSession();
//		s.save(contact);
		return "redirect:/test/upload-file";
	}
	
	@GetMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("contactForm", new Contact());
		//System.out.println("Chuoi : "+ConfigLoader.getInstance().getValue("upload.servlet.location"));
		
//		contactService.deleteContact(6);
//		Contact ctx = new Contact();
//		ctx.setName("D5");
//		ctx.setAge(2);
//		
//		Phone p1 = new Phone();p1.setContext("1111");
//		Phone p2 = new Phone();p2.setContext("0000");
		
//		Set<Phone> ps = new HashSet<Phone>();
//		ps.add(p1);
//		ps.add(p2);
//		p1.setContact(ctx);
//		p2.setContact(ctx);	
//		ctx.getPhones().add(p1);
//		ctx.getPhones().add(p2);
//		contactService.saveContact(ctx);
		//phoneService.savePhone(p1);
		return "upload";
	}
	
//	@Transactional
	@GetMapping("/upload-file")
	public String upload(Model model) {
//	Session s = ss.getCurrentSession();
//	List<Contact> ctc = s.createQuery("from contact",Contact.class).getResultList();
		//System.out.println("Chuoi : "+ConfigLoader.getInstance().getValue("upload.location"));
//		model.addAttribute("contacts", ctc);
		return "viewUpload";
	}
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest request,@RequestParam(name="file") MultipartFile file, @ModelAttribute("contact") Contact contact) {
		request.setAttribute("file", file);
		// luu file 
		try {
			File newFile = new File("F:\\data\\uploadInventory\\"+file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		contact.setImgUrl(file.getOriginalFilename());
		
		return "redirect:/test/upload-file";
	}
	
	private void processUploadFile(MultipartFile multipartFile, String fileName) throws IllegalStateException, IOException {
		//File newFile = new File("F:\\data\\uploadInventory\\test\\"+fileName);
		String path = "F:\\data\\uploadInventory\\test2\\";
		if(multipartFile!=null) {
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			//String fileName = System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
			File file = new File(path,fileName);
		//	System.out.println("Chuoi : "+ConfigLoader.getInstance().getValue("upload.location"));
			multipartFile.transferTo(file);
		}
	}
}
