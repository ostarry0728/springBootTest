package com.zeus.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zeus.domain.Item;
import com.zeus.service.ItemService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/item")
@MapperScan(basePackages = "com.zeus.mapper")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Value("${upload.path}")
	private String uploadPath;

	// 이미지 게시판 리스트 요청 /WEB-INF/views/item/list.jsp
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception {
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
	}

	// 이미지 게시판 등록 화면 요청 /WEB-INF/views/item/register.jsp
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute(new Item());
		return "item/register";
	}

	// 이미지 게시판 등록 이미지 DB, 파일저장 요청 /WEB-INF/views/item/success.jsp
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Item item, Model model) throws Exception {
		MultipartFile file = item.getPicture();
		log.info("originalName: " + file.getOriginalFilename());
		log.info("size: " + file.getSize());
		log.info("contentType: " + file.getContentType());

		// uploadFile(String originalName, byte[] fileData) 구조임
		String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
		item.setPictureUrl(createdFileName);
		itemService.regist(item);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "item/success";
	}

	// 이미지 게시판 파일수정 요청 /WEB-INF/views/item/modify.jsp
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyForm(Integer itemId, Model model) throws Exception {
		Item item = itemService.read(itemId);
		model.addAttribute(item);
		return "item/modify";
	}

	// 이미지 게시판 수정된 내용 DB저장, 파일저장 요청 /WEB-INF/views/item/success.jsp
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Item item, Model model) throws Exception {
		MultipartFile file = item.getPicture();
		if (file != null && file.getSize() > 0) {
			log.info("originalName: " + file.getOriginalFilename());
			log.info("size: " + file.getSize());
			log.info("contentType: " + file.getContentType());
			String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
			item.setPictureUrl(createdFileName);
		}
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다.");
		return "item/success";
	}

	// 이미지 게시판 제거화면 요청 요청 /WEB-INF/views/item/remove.jsp
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String removeForm(Integer itemId, Model model) throws Exception {
		Item item = itemService.read(itemId);
		model.addAttribute(item);
		return "item/remove";
	}

	// 이미지 게시판 제거내용 처리 요청 /WEB-INF/views/item/success.jsp
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(Item item, Model model) throws Exception {
		itemService.remove(item.getItemId());
		model.addAttribute("msg", "삭제가 완료되었습니다.");
		return "item/success";
	}

	// 멤버함수 -> 파일명을 전달하면 중복이 일어나지 않는 이름_이미지파일.확장자 를 만듬 -> c드라이브의 upload에 저장
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		// 절대 중복이 일어나지 않는 아이디를 생성한다. (UUID) 
		// ex) 2700b024-86e2-4c0f-b81e-4e208b12b5aa_b_whopper.png <- createFileName
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalName;
		//c:/upload/2700b024-86e2-4c0f-b81e-4e208b12b5aa_b_whopper.png <- File target
		File target = new File(uploadPath, createdFileName);
		// 진짜 데이터를 c:/upload/2700b024-86e2-4c0f-b81e-4e208b12b5aa_b_whopper.png를 복사처리. (byte[])
		FileCopyUtils.copy(fileData, target);
		return createdFileName;
	}

	// 브라우저에서 이미지를 <img src="/item/display/파일명(이미지번호)" /> (이미지번호) 게시판에서 불러서 화면에 전달하는
	// 함수
	@ResponseBody
	@RequestMapping("/display")
	public ResponseEntity<byte[]> displayFile(Integer itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String fileName = itemService.getPicture(itemId);
		log.info("FILE NAME: " + fileName);
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + File.separator + fileName);
			if (mType != null) {
				headers.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	// 확장자에 따라서 브라우저에 미디어 타입을 설정해주는 함수
	private MediaType getMediaType(String formatName) {
		if (formatName != null) {
			if (formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if (formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
			if (formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	}
}
