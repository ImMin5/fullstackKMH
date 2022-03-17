package com.campus.myapp.controller;

import java.io.File;
import java.nio.charset.Charset;
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
	
	//자료실 글쓰기 폼
	@GetMapping("/data/write")
	public ModelAndView dataWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data/dataWrite");
		return mav;
	}
	
	//자료실 글쓰기
	@PostMapping("/data/writeOk")
	public ResponseEntity<String> dataWriteOk(DataVO vo, HttpServletRequest request) {
		// vo : subject, content는 request가 됨.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		
		//파일업로드를 위한 업로드위치의 절대 주소
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path : "+path);
		try {
			//파일 업로드를 처리하기 위해서는 request 객체에서 multipart객체를 구하여야 한다.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			
			//mr에 파일에 수만큼 MurtipartFile 객체가 존재한다.
			List<MultipartFile> files = mr.getFiles("filename");
			System.out.println("업로드 파일 수 : "+ files.size());
			
			if(files != null) {
				int cnt = 1; //업로드 순서에 따라 filename1,filename2 파일명을 대입하기 위한 변수
				for(int i=0; i<files.size(); i++) {
					//1.MultipartFile객체 얻어오기
					MultipartFile mf = files.get(i);
					
					//2.업로드한 실제 파일명을 구하기
					String orgFileName = mf.getOriginalFilename();
					System.out.println("orgFileName -> " + orgFileName);
					
					//3. rename하기
					if(orgFileName != null && !orgFileName.equals("")) {
						File f = new File(path, orgFileName);
						
						//파일이 존재하는지 확인 true :파일이 존재한다., false: 파일이 없다.
						
						if(f.exists()) {
							System.out.println(" i("+i+") : 파일 존재");
							for(int renameNum =1;; renameNum++) {
								//확장자와 파일을 분리한다.
								int point = orgFileName.lastIndexOf(".");
								String fileName = orgFileName.substring(0,point);
								String ext = orgFileName.substring(point+1);
								
								System.out.println("new file name" + fileName);
								f = new File(path, fileName+"("+ renameNum+")."+ext);
								if(!f.exists()) {// 새로 생성된 파일 객체가 없으면
									orgFileName = f.getName();
									break;
								}								
							}
						}//f.exists end
						//4. 파일 업로드
						try {
							mf.transferTo(f); //실제 업로드가 발생함.
						}catch(Exception e) {
							
						}
						
						//5. 업로드한(새로운 파일명)or(기존 파일명) 을 vo에 셋팅
						if(cnt == 1) vo.setFilename1(orgFileName);
						if(cnt == 2) vo.setFilename2(orgFileName);
						cnt++;
					}
					
				}
			}
			//DB등록 
			service.dataInsert(vo);
			
			//레코드 추가 성공
			String msg = getSuccessMessage("자료실 글 등록 완료","/myapp/data/dataList");
			
			//업로드 완료
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			//레코드 추가 실패
			
			//파일 지우기
			fileDelete(path,vo.getFilename1());
			fileDelete(path,vo.getFilename2());
			
			//메세지
			String msg = getFailMessage("자료실 글 등록 실패");
			
			//이전페이지로 보내기
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//파일 지우기
	public void fileDelete(String p, String f) {
		if( f != null) {
			File file = new File(p, f);
			file.delete();
		}
	}
	
	//자료글 내용 보기
	@GetMapping("/data/view")
	public ModelAndView view(int no) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	
	//자료글 수정 폼
	@GetMapping("/data/dataEdit")
	public ModelAndView editForm(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataSelect(no);
		
		//DB에 첨부된 파일 수를 구한다.
		
		int fileCount = 1; //첫번째 첨부파일은 무조건 있음.
		
		if(vo.getFilename2() != null) {
			fileCount++;
		}
		
		mav.addObject("fileCount",fileCount);
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataEdit");
		return mav;
	}
	
	//자료글 수정
	@PostMapping("/data/editOk")
	public ResponseEntity<String> editOk(DataVO vo, HttpSession session){
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
		
		try {
			String msg = getSuccessMessage("자료글 수정 성공","");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	//글 수정 메세지
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
