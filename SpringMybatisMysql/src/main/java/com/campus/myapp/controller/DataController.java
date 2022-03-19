package com.campus.myapp.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.DataService;
import com.campus.myapp.vo.DataVO;

@RestController
public class DataController {
	@Autowired
	DataService service;
	
	@GetMapping("/data/dataList")
	public ModelAndView dataList() {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("list", service.dataSelectAll());
		mav.setViewName("data/dataList");	
		
		return mav;
	}
	
	//ÀÚ·á½Ç ±Û¾²±â Æû
	@GetMapping("/data/write")
	public ModelAndView dataWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data/dataWrite");
		return mav;
	}
	
	//ÀÚ·á½Ç ±Û¾²±â
	@PostMapping("/data/writeOk")
	public ResponseEntity<String> dataWriteOk(DataVO vo, HttpServletRequest request) {
		// vo : subject, content´Â request°¡ µÊ.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		
		//ÆÄÀÏ¾÷·Îµå¸¦ À§ÇÑ ¾÷·ÎµåÀ§Ä¡ÀÇ Àý´ë ÁÖ¼Ò
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path : "+path);
		try {
			//ÆÄÀÏ ¾÷·Îµå¸¦ Ã³¸®ÇÏ±â À§ÇØ¼­´Â request °´Ã¼¿¡¼­ multipart°´Ã¼¸¦ ±¸ÇÏ¿©¾ß ÇÑ´Ù.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			
			//mr¿¡ ÆÄÀÏ¿¡ ¼ö¸¸Å­ MurtipartFile °´Ã¼°¡ Á¸ÀçÇÑ´Ù.
			List<MultipartFile> files = mr.getFiles("filename");
			System.out.println("¾÷·Îµå ÆÄÀÏ ¼ö : "+ files.size());
			
			if(files != null) {
				int cnt = 1; //¾÷·Îµå ¼ø¼­¿¡ µû¶ó filename1,filename2 ÆÄÀÏ¸íÀ» ´ëÀÔÇÏ±â À§ÇÑ º¯¼ö
				for(int i=0; i<files.size(); i++) {
					//1.MultipartFile°´Ã¼ ¾ò¾î¿À±â
					MultipartFile mf = files.get(i);
					
					//2.¾÷·ÎµåÇÑ ½ÇÁ¦ ÆÄÀÏ¸íÀ» ±¸ÇÏ±â
					String orgFileName = mf.getOriginalFilename();
					System.out.println("orgFileName -> " + orgFileName);
					
					//3. renameÇÏ±â
					if(orgFileName != null && !orgFileName.equals("")) {
						File f = new File(path, orgFileName);
						
						//ÆÄÀÏÀÌ Á¸ÀçÇÏ´ÂÁö È®ÀÎ true :ÆÄÀÏÀÌ Á¸ÀçÇÑ´Ù., false: ÆÄÀÏÀÌ ¾ø´Ù.
						
						if(f.exists()) {
							System.out.println(" i("+i+") : ÆÄÀÏ Á¸Àç");
							for(int renameNum =1;; renameNum++) {
								//È®ÀåÀÚ¿Í ÆÄÀÏÀ» ºÐ¸®ÇÑ´Ù.
								int point = orgFileName.lastIndexOf(".");
								String fileName = orgFileName.substring(0,point);
								String ext = orgFileName.substring(point+1);
								
								System.out.println("new file name" + fileName);
								f = new File(path, fileName+"("+ renameNum+")."+ext);
								if(!f.exists()) {// »õ·Î »ý¼ºµÈ ÆÄÀÏ °´Ã¼°¡ ¾øÀ¸¸é
									orgFileName = f.getName();
									break;
								}								
							}
						}//f.exists end
						//4. ÆÄÀÏ ¾÷·Îµå
						try {
							mf.transferTo(f); //½ÇÁ¦ ¾÷·Îµå°¡ ¹ß»ýÇÔ.
						}catch(Exception e) {
							
						}
						
						//5. ¾÷·ÎµåÇÑ(»õ·Î¿î ÆÄÀÏ¸í)or(±âÁ¸ ÆÄÀÏ¸í) À» vo¿¡ ¼ÂÆÃ
						if(cnt == 1) vo.setFilename1(orgFileName);
						if(cnt == 2) vo.setFilename2(orgFileName);
						cnt++;
					}
					
				}
			}
			//DBµî·Ï 
			service.dataInsert(vo);
			
			//·¹ÄÚµå Ãß°¡ ¼º°ø
			String msg = getSuccessMessage("ÀÚ·á½Ç ±Û µî·Ï ¿Ï·á","/myapp/data/dataList");
			
			//¾÷·Îµå ¿Ï·á
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			//·¹ÄÚµå Ãß°¡ ½ÇÆÐ
			
			//ÆÄÀÏ Áö¿ì±â
			fileDelete(path,vo.getFilename1());
			fileDelete(path,vo.getFilename2());
			
			//¸Þ¼¼Áö
			String msg = getFailMessage("ÀÚ·á½Ç ±Û µî·Ï ½ÇÆÐ");
			
			//ÀÌÀüÆäÀÌÁö·Î º¸³»±â
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//ÆÄÀÏ Áö¿ì±â
	public void fileDelete(String p, String f) {
		if( f != null) {
			File file = new File(p, f);
			file.delete();
		}
	}
	
	//ÀÚ·á±Û ³»¿ë º¸±â
	@GetMapping("/data/view")
	public ModelAndView view(int no) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	
	//ÀÚ·á±Û ¼öÁ¤ Æû
	@GetMapping("/data/dataEdit")
	public ModelAndView editForm(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataSelect(no);
		
		//DB¿¡ Ã·ºÎµÈ ÆÄÀÏ ¼ö¸¦ ±¸ÇÑ´Ù.
		
		int fileCount = 1; //Ã¹¹øÂ° Ã·ºÎÆÄÀÏÀº ¹«Á¶°Ç ÀÖÀ½.
		
		if(vo.getFilename2() != null) {
			fileCount++;
		}
		
		mav.addObject("fileCount",fileCount);
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataEdit");
		return mav;
	}
	
	//자료 수정
	@PostMapping("/data/editOk")
	public ResponseEntity<String> editOk(DataVO vo, HttpSession session ,MultipartHttpServletRequest req){
		vo.setUserid((String)session.getAttribute("logId"));
		String path = session.getServletContext().getRealPath("/upload");
		
		
		if(vo.getDelFile() != null) {
			for(int k=0; k<vo.getDelFile().length; k++) {
				System.out.println("del :" + vo.getDelFile()[k]);
			}
		}
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");
		System.out.println(vo.toString());
		
		List<String> fileList = new ArrayList<String>();
		List<String> newUpload = new ArrayList<String>();
		try {
			//1. DB에서 파일명 가져오기
			DataVO dbFileVO = service.getFileName(vo.getNo());
			
			System.out.println("no : " + vo.getNo());
			System.out.println("filename2 : " + vo.getFilename2());
			//1번파일 삽입 
			fileList.add(dbFileVO.getFilename1());
			if(dbFileVO.getFilename2() != null ){
				System.out.println("2번 파일 추가 : " + dbFileVO.getFilename2());
				fileList.add(dbFileVO.getFilename2());
			}
			
			//2.번파일 삽입
			if(vo.getDelFile() != null){
				for(String delFile : vo.getDelFile()){
					System.out.println("삭제될 파일 : " + delFile);
					fileList.remove(delFile);
				}
			}

			//3.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;


			//
			List<MultipartFile> newFileList = mr.getFiles("filename");
			if(newFileList != null){
				for(int i=0; i<newFileList.size(); i++){
					MultipartFile newMf = newFileList.get(i);
					String newUploadFilename = newMf.getOriginalFilename();

					if(newUploadFilename != null && !newUploadFilename.equals("")){
						System.out.println("new file : " + newUploadFilename);
						File f = new File(path, newUploadFilename);
						if(f.exists()){
							//rename
							System.out.println(newUploadFilename +"은 이미 존재함");
							for(int n=1;;n++){
								int point = newUploadFilename.lastIndexOf(".");
								String fileNameNoExt = newUploadFilename.substring(0,point);
								String ext = newUploadFilename.substring(point+1);
								//
								String nf = fileNameNoExt +"("+n+")."+ext;
								f = new File(path, nf);
								if(!f.exists()){
									newUploadFilename = nf;
									break;
								}
							}//for
						}
						//upload complte
					
						try{
							newMf.transferTo(f);
							fileList.add(newUploadFilename);//db에 등록할 파일명에 추가
							newUpload.add(newUploadFilename);//새로업로드 목록추가
						}catch(Exception ee){
							
							ee.printStackTrace();
						}
						
						}
					}//for
				}//if

				// fileList 
				
				for(int k=0; k<fileList.size(); k++){
					if(k==0) {
						System.out.println("fileList("+k+") :" + fileList.get(k));
						vo.setFilename1(fileList.get(k));
					}
					if(k==1) {
						System.out.println("fileList("+k+") :" + fileList.get(k));
						vo.setFilename2(fileList.get(k));
					}

				}
				//DB update
				System.out.println("db update");
				service.dataUpdate(vo);

				//DB 수정되었을때
				if(vo.getDelFile() != null) {
					System.out.println("db edited");
					for(String fname: vo.getDelFile()){
						fileDelete(path,fname);
					}
				}
			
				
				//글 내용 보기로 이동
				String msg = getSuccessMessage("자료실 글이 수정되었습니다.","/myapp/data/view?no="+vo.getNo());
			
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			//DB수정 못했을 때
			for(String fname:newUpload) {
				fileDelete(path,fname);
			}
			
			//수정페이지로 이동
			String msg = getFailMessage("글 수정 실패했습니다.");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST);
		}

		
		return entity;
	}
	
	//자료실 삭제
	@GetMapping("/data/dataDel")
	public ResponseEntity<String> dataDel(int no,HttpSession session) {
		String userid = (String)session.getAttribute("logId");
		String path = session.getServletContext().getRealPath("/upload");
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Tpye","text/html; charset=utf-8");
		
		try {
			//1. 삭제할 레코드의 파일명 얻어오기
			DataVO dbFileVO = service.getFileName(no);
			
			//2. 레코드 삭제 
			service.dataDelete(no, userid);
			
			//3. 파일 삭제 
			fileDelete(path, dbFileVO.getFilename1());
			if( dbFileVO.getFilename2() != null) {
				fileDelete(path, dbFileVO.getFilename2());
			}
			
			String msg = getSuccessMessage("자료글이 삭제 되었습니다","/myapp/data/dataList");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			
			String msg = getFailMessage("자료글 삭제 실패");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	public String getSuccessMessage(String msg, String url) {
		String alert = "<script>";
		alert += "alert('"+msg+".\\n');";
		alert += "location.href='"+url+"';";
		alert += "</script> ";
	
		return alert;
	}
	public String getFailMessage(String msg) {
		String alert = "<script>";
		alert += "alert('"+msg+".\\n');";
		alert += "history.back();";
		alert += "</script> ";	
		return alert;
	}

}
