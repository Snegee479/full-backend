package com.example.appController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.example.dao.AdminDAO;
//import com.example.dao.CustomerDAO;
import com.example.dao.MoviesDAO;
import com.example.dao.ScreenDAO;
import com.example.dao.TheaterDAO;
import com.example.dao.TicketDAO;
import com.example.dao.TimeSlotDAO;
import com.example.pojo.Admin;
//import com.example.pojo.Customer;
import com.example.pojo.Movies;
import com.example.pojo.Screen;
import com.example.pojo.Theater;
import com.example.pojo.Ticket;
import com.example.pojo.TimeSlot;
import com.fasterxml.jackson.databind.ObjectMapper;

import exception.ResourceNotFoundException;



@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/")
public class MainController {
	
	@Autowired
	MoviesDAO mdao;
	@Autowired
	TicketDAO ttdao;
	//@Autowired
//	CustomerDAO cdao;
	@Autowired
	ScreenDAO sdao;
	@Autowired
	TheaterDAO tdao;
	@Autowired
	AdminDAO admindao;
	@Autowired
	TimeSlotDAO tsdao;
	Logger logger=Logger.getAnonymousLogger();
	
	//------------------------------------------------------THEATER------------------------------------------------------------------------
	@GetMapping("/getAllTheater")
	public List<Theater> getAllTheater() throws IOException  { 
	logger.info("inside controller's getAllTheater method ");
	List<Theater> tlist=tdao.getAllTheater();
	return tlist;
	}
	@PostMapping("/insertTheater")
	public Theater insertTheater(@RequestBody Theater t) throws IOException { 
	logger.info("inside controller's insertTheater method, Json is "+t );
	logger.info("inserted theater") ;
	return tdao.insert(t);
	}
	@DeleteMapping("/deleteTheater/{theaterId}")
	public String deleteTheater(@PathVariable int theaterId) throws IOException { 
	logger.info("inside controller's deleteTheater method ");
	logger.info( "deleted theater " );
	return tdao.deleteById(theaterId);
	}
	@PutMapping("/updateTheater/{theaterId}")
	public Theater updateTheater(@PathVariable(value = "theaterId") int id,@Valid @RequestBody Theater t) throws IOException { 
	logger.info("inside controller's update Theater method ");
	logger.info("theater is "+t);
	logger.info( "updated theater") ;
	return tdao.update(id,t);
	}
	@GetMapping("findByTheaterId/{theaterId}")
	public Theater findByTheaterId(@PathVariable int theaterId){
	return tdao.findById(theaterId);
	}

	//------------------------------------------------------TIME SLOT------------------------------------------------------------------------
	@GetMapping("/getAllTimeSlot")
		public List<TimeSlot> getAllTimeSlot() throws IOException  { 
		logger.info("inside controller's getAllTimeSlot method ");
		List<TimeSlot> tslist=	tsdao.getAllTimeSlot();
		ListIterator<TimeSlot> itr=tslist.listIterator();
		while(itr.hasNext()) {
			TimeSlot t =itr.next();
			if(t.getScreen().getMovie().getPic()!=null) {
			Movies m=findByMovieId(t.getScreen().getMovie().getMovieId());
			t.getScreen().setMovie(m);}
		}
		return tslist;}
	@PostMapping("/insertTimeSlot")
		public TimeSlot insertTimeSlot(@RequestBody TimeSlot t) { 
		Screen s=sdao.findById(t.getScreen().getScreenId());
		t.setScreen(s);
		logger.info("inside controller's insertTimeSlot method, the TimeSlot details obtained from jsp are "+t);
		return tsdao.insert(t);
		}
	@DeleteMapping("/deleteTimeSlot/{timeId}")
		public String deleteTimeSlot(@PathVariable int timeId) throws IOException { 
		logger.info("inside controller's deleteTimeSlot method ");
		return 	tsdao.deleteById(Integer.parseInt("timeId"));
		}
	@PutMapping("/updateTimeSlot/{timeId}")
		public TimeSlot updateTimeSlot(@PathVariable(value = "timeId") int id,@Valid @RequestBody TimeSlot t ) throws IOException { 
		Screen s=sdao.findById(t.getScreen().getScreenId());
		t.setScreen(s);
		logger.info("inside controller's update timeslot method ");
		return 	tsdao.updateTimeSlot(id,t);
		}
	@GetMapping("findByTimeId/{timeId}")
		public TimeSlot findByTimeId(@PathVariable int timeId){
			return tsdao.findById(timeId);
		}	
		
	//------------------------------------------------------CUSTOMERS------------------------------------------------------------------------
//	@GetMapping("/getAllCustomers")
//	public List<Customer> getAllCustomers() throws IOException  { 
//	logger.info("inside controller's getAllCustomers method ");
//	List<Customer> clist=cdao.getAllCustomers();
//	logger.info("got the list of customers from cdao");
//	return clist;}

	//-----------------------------------------------------MOVIES-------------------------------------------------------------------------
	@DeleteMapping("/deleteMovie/{movieId}")
	public String deleteMovie(@PathVariable int movieId) throws IOException { 
	logger.info("inside controller's deleteProduct method ");
	return mdao.deleteById(movieId);
	}
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}

	@PostMapping(value="/insertMovies", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Movies insertMovie(@RequestParam(value="movie")  String movie,@RequestParam(value="myFile",required=false) MultipartFile file) throws IOException { 
//         = new Movies( ,file.getContentType(),file.getBytes() );
		logger.info("inside controller's insertMovie method, the value of movie name obtained from jsp is "+ movie);
		logger.info("movie "+ movie);
		ObjectMapper objectMapper = new ObjectMapper();

		Movies MovieObj = objectMapper.readValue(movie, Movies.class);
		logger.info(MovieObj.getMovieName());	
		logger.info("file "+ file+" "+file.getName()+" "+file.getOriginalFilename());
		MovieObj.setPic(compressBytes(file.getBytes()));
	return mdao.insert(MovieObj);
	}
	
	@PutMapping("/updateMovie/{movieId}")
	public Movies updateMovie(@PathVariable(value = "movieId") int id,
			@Valid @RequestBody Movies movie) throws IOException { 
	logger.info("inside controller's updateProduct method ");
	return mdao.update(id,movie);
	}

	@GetMapping("/getAllMovies")
	public List<Movies> getAllMoviesList() throws IOException { 
		logger.info("inside controller's getAllMovies method ");
		List<Movies> mlist=	mdao.getAll();
		return mlist;
	}	
	@GetMapping(value = "/findByMovieId/{movieId}")
	public Movies findByMovieId(@PathVariable int movieId) throws IOException{
		System.out.println("---movieId---"+movieId);
		Movies m = mdao.findByMovieId(movieId);
		logger.info("---movies---"+m);
		logger.info("image type in form of bytes "+m.getPic());
		m.setPic(decompressBytes(m.getPic()));	
		Path path=Paths.get("Downloads");
		Files.write(path,m.getPic());
		logger.info("file type check  "+path);
		return m;
	}
	@GetMapping(value = "/findByMovieName/{movieName}")
	public Movies findByMovieName(@PathVariable String movieName) throws IOException{
		System.out.println("---movieName---"+movieName);
		Movies m = mdao.findByMovieName(movieName);
		logger.info("---movies---"+m);
		logger.info("image type in form of bytes "+m.getPic());
		m.setPic(decompressBytes(m.getPic()));	
		Path path=Paths.get("Downloads");
		Files.write(path,m.getPic());
		logger.info("file type check  "+path);
		return m;
	}
	
	

	//-----------------------------------------------------------SCREEN----------------------------------------------------------------
	@DeleteMapping("/deleteScreen/{screenId}")
	public String deleteScreen(@PathVariable int screenId) throws IOException { 
	logger.info("inside controller's deleteScreen method ");
	return mdao.deleteById(screenId);
	}

	@PostMapping("/insertScreen")
	public Screen insertScreen(@RequestBody Screen s) throws IOException { 
	logger.info("inside controller's insertScreen method, the value of screen name obtained is "+ s);
	s.setAvailableNoOfSeats(s.getTotalNoOfSeats());
	Movies m=mdao.findByMovieId(s.getMovie().getMovieId());
	Theater t=tdao.findById(s.getTheater().getTheaterId());
	s.setMovie(m);s.setTheater(t);
	return sdao.insert(s);
	}
	
	@PutMapping("/updateScreen/{screenId}")
	public Screen updateScreen(@PathVariable(value = "screenId") int id,
			@Valid @RequestBody Screen s) throws IOException { 
		s.setAvailableNoOfSeats(s.getTotalNoOfSeats());
		Movies m=mdao.findByMovieId(s.getMovie().getMovieId());
		Theater t=tdao.findById(s.getTheater().getTheaterId());
		s.setMovie(m);s.setTheater(t);
		logger.info("inside controller's updateScreen method ");
		return sdao.update(s);
	}

	@GetMapping("/getAllScreen")
	public List<Screen> getAllScreen() throws IOException { 
		logger.info("inside controller's getAllScreen method ");
		List<Screen> slist=	sdao.getAll();
		return slist;
	}	
	@GetMapping("findByScreenId/{screenId}")
	public Screen findByScreenId(@PathVariable int screenId){
		return sdao.findById(screenId);
	}
	
	//----------------------------------------------------------TICKET----------------------------------------------------------------
	@GetMapping("/selectMovie")
	public List<Ticket> selectMovie() throws IOException { 
		logger.info("inside controller's selectMovie method ");
		List<Ticket> ttlist=	ttdao.getAllTickets();
		return ttlist;
	}
	
	@DeleteMapping("/deleteTicket/{ticketId}")
	public String deleteTicket(@PathVariable int ticketId) throws IOException { 
	logger.info("inside controller's deleteTicket method ");
	return ttdao.deleteById(ticketId);
	}

	@PostMapping(value="/insertTicket", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Ticket insertTicket(@RequestBody Ticket ticket) throws IOException { 
	logger.info("inside controller's insertTicket method, the value of ticket name obtained is "+ ticket);
	logger.info("time id"+ticket.getTimeSlot().getTimeId());
	logger.info("timslot"+tsdao.findById(ticket.getTimeSlot().getTimeId()));
	ticket.setTimeSlot(tsdao.findById(ticket.getTimeSlot().getTimeId()));
	ticket.setInternetFee(ttdao.calculate_internet_handling_fee(ticket.getTicketCount(),ticket.getTicketPrice()));
	logger.info(ticket+"ticket after altering internetfee");
	ticket.setTotalFee(ttdao.calculate_total_fee(ticket.getTicketCount(), ticket.getTicketPrice(),ticket.getInternetFee()));
	logger.info(ticket+"ticket after altering totalfee");
	int totalnoofseats=ticket.getTimeSlot().getScreen().getTotalNoOfSeats();
	int a=totalnoofseats-(ticket.getTicketCount());
	ticket.getTimeSlot().getScreen().setAvailableNoOfSeats(a);
	return ttdao.insert(ticket);
	}
	
	@PutMapping("/updateTicket")
	public Ticket updateTicket(@RequestBody Ticket t) throws IOException,ResourceNotFoundException { 
		logger.info("inside controller's updateTicket method ");
		return ttdao.updateTicket(t);
	}

	@GetMapping("/getAllTicket")
	public List<Ticket> getAllTicket() throws IOException,ResourceNotFoundException { 
		logger.info("inside controller's getAllTicket method ");
		List<Ticket> ttlist=	ttdao.getAllTickets();
		ListIterator<Ticket> itr=ttlist.listIterator();
		while(itr.hasNext()) {
			Ticket t =itr.next();
			Movies m=mdao.findByMovieId(t.getTimeSlot().getScreen().getMovie().getMovieId());
			t.getTimeSlot().getScreen().setMovie(m);
		}
		return ttlist;
	}	
	@GetMapping("findByTicketId/{ticketId}")
	public Ticket findByTicketId(@PathVariable int ticketId) throws IOException,ResourceNotFoundException{
		Ticket t=ttdao.findById(ticketId);
		Movies m=findByMovieId(t.getTimeSlot().getScreen().getMovie().getMovieId());
		t.getTimeSlot().getScreen().setMovie(m);
		return t;
	}
	
	//---------------------------------------------------------LOGIN LOGOUT------------------------------------------------------------
	
	@PostMapping(value="/register", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Admin register(@RequestBody Admin admin) throws IOException { 
		admin.setRole("user");
	logger.info("inside controller's register method, the value of admin is "+ admin);
	return admindao.insert(admin);
	}

	
	@RequestMapping("/login")
	public Admin login(@RequestBody Admin admindata) throws IOException,ResourceNotFoundException {
		String retrunVal = "";
		String Email=admindata.getEmail();
		String password=admindata.getPassword();
		logger.info("inside the request mapping of login gt this as admin values"+admindata);
		logger.info("object from user"+admindao.findByEmail(Email));
		Admin admin=admindao.findByPassword(password);
		logger.info("admin from password is "+admindao.findByPassword(password));
		logger.info("admin from email is "+admindao.findByEmail(Email));
		if(admin.getRole().equals("admin")|admin.getRole().equals("user")) {
		if(admindao.findByEmail(Email).equals(admindao.findByPassword(password))) {
			logger.info("validation of the admin is successfull");
			retrunVal="Login is successfull";
		}
		else {
			logger.info("failed");retrunVal="login page for admin has failed ! please check your credentials ";
		}
		logger.info("value is "+retrunVal);
		}admin.setPassword(null);admin.setId(null);return admin;
	}
	
//--------------------------------------------------------SELECT MOVIE AND BOOK TICKET----------------------------------------------------------
//	@PostMapping("findByTicketId/{ticketId}")
//	public Ticket bookTicket(Ticket ticket) {
//		logger.info(ticket+"ticket");
//		ticket.setInternetFee(ttdao.calculate_internet_handling_fee(ticket.getTicketCount(),ticket.getTicketPrice()));
//		logger.info(ticket+"ticket after altering internetfee");
//		ticket.setTotalFee(ttdao.calculate_total_fee(ticket.getTicketCount(), ticket.getTicketPrice(),ticket.getInternetFee()));
//		logger.info(ticket+"ticket after altering totalfee");
//		return ttdao.insert(ticket);
//	}
	
	
	
	
	
}
