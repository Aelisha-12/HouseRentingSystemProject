package com.ohrs.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ohrs.models.House;
import com.ohrs.repositories.HouseRepository;
import com.ohrs.security.services.HouseService;

@RestController
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private HouseRepository houseRepo;
	
	@RequestMapping("/home")
	public ModelAndView ShowHouse() {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("addHouse.html");
		return mv;
	}
	
	@GetMapping("/listHouses")
	public ModelAndView showExampleView()
	{
		ModelAndView mv=new ModelAndView();
		List<House> houses = houseService.getAllHouse();
		//model.addAttribute("houses", houses);
		mv.addObject("houses",houses);
		mv.setViewName("listHouses.html");
		return mv;
	}
    @GetMapping("/")
    public String showAddHouse()
    {
    	return "/addHouse.html";
    }
    
    @PostMapping("/addH")
    public ModelAndView saveHouse(@RequestParam("houseImage") MultipartFile file,
    		@RequestParam("houseName") String name,
    		@RequestParam("houseRent") double rent,
    		@RequestParam("houseAddress") String address)
    {
    	houseService.saveHouseToDB(file, name, address, rent);
    	ModelAndView mv=new ModelAndView();
		mv.setViewName("listHouses.html");
		return mv;
    	
    	//return "redirect:/listHouses.html";
    }
    
    @GetMapping("/deleteHouse/{id}")
    public String deleteHouswe(@PathVariable("id") Long id)
    {
    	
    	houseService.deleteHouseById(id);
    	return "redirect:/listHouses.html";
    }
    
    @PostMapping("/changeName")
    public String changeHname(@RequestParam("id") Long id,
    		@RequestParam("newHname") String name)
    {
    
    	houseService.changeHouseName(id, name);
    	return "redirect:/listHouses.html";
    }
    @PostMapping("/changeAddress")
    public String changeAddress(@RequestParam("id") Long id ,
    		@RequestParam("newAddress") String address)
    {
    	houseService.changeHouseAddress(id, address);
    	return "redirect:/listHouses.html";
    }
    
    @PostMapping("/changeRent")
    public String changeRent(@RequestParam("id") Long id ,
    		@RequestParam("newRent") Double rent)
    {
    	houseService.changeHouseRent(id, rent);
    	return "redirect:/listHouses.html";
    }
}
