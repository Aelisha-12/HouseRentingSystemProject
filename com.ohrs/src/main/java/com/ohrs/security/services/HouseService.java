package com.ohrs.security.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ohrs.models.House;
import com.ohrs.repositories.HouseRepository;

@Service
public class HouseService {

	@Autowired
	private HouseRepository houseRepo;

	
	public void saveHouseToDB(MultipartFile file, String houseName, String houseAddress, double houseRent) {

		House h = new House();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			h.setHouseimage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		h.setHouseAddress(houseAddress);
		h.setHouseName(houseName);
		h.setHouseRent(houseRent);

		houseRepo.save(h);
	}

	public List<House> getAllHouse() {
		return houseRepo.findAll();
	}

	public void deleteHouseById(Long id) {
		houseRepo.deleteById(id);
	}

	public void changeHouseName(Long id, String houseName) {
		House h = new House();
		h = houseRepo.findById(id).get();
		h.setHouseName(houseName);
		houseRepo.save(h);
	}

	public void changeHouseAddress(Long id, String houseAddress) {
		House h = new House();
		h = houseRepo.findById(id).get();
		h.setHouseAddress(houseAddress);
		houseRepo.save(h);
	}

	public void changeHouseRent(Long id, Double houseRent) {
		House h = new House();
		h = houseRepo.findById(id).get();
		h.setHouseRent(houseRent);
		houseRepo.save(h);
	}

}
