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
	
	//�ڷ�� �۾��� ��
	@GetMapping("/data/write")
	public ModelAndView dataWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data/dataWrite");
		return mav;
	}
	
	//�ڷ�� �۾���
	@PostMapping("/data/writeOk")
	public ResponseEntity<String> dataWriteOk(DataVO vo, HttpServletRequest request) {
		// vo : subject, content�� request�� ��.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		
		//���Ͼ��ε带 ���� ���ε���ġ�� ���� �ּ�
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path : "+path);
		try {
			//���� ���ε带 ó���ϱ� ���ؼ��� request ��ü���� multipart��ü�� ���Ͽ��� �Ѵ�.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			
			//mr�� ���Ͽ� ����ŭ MurtipartFile ��ü�� �����Ѵ�.
			List<MultipartFile> files = mr.getFiles("filename");
			System.out.println("���ε� ���� �� : "+ files.size());
			
			if(files != null) {
				int cnt = 1; //���ε� ������ ���� filename1,filename2 ���ϸ��� �����ϱ� ���� ����
				for(int i=0; i<files.size(); i++) {
					//1.MultipartFile��ü ������
					MultipartFile mf = files.get(i);
					
					//2.���ε��� ���� ���ϸ��� ���ϱ�
					String orgFileName = mf.getOriginalFilename();
					System.out.println("orgFileName -> " + orgFileName);
					
					//3. rename�ϱ�
					if(orgFileName != null && !orgFileName.equals("")) {
						File f = new File(path, orgFileName);
						
						//������ �����ϴ��� Ȯ�� true :������ �����Ѵ�., false: ������ ����.
						
						if(f.exists()) {
							System.out.println(" i("+i+") : ���� ����");
							for(int renameNum =1;; renameNum++) {
								//Ȯ���ڿ� ������ �и��Ѵ�.
								int point = orgFileName.lastIndexOf(".");
								String fileName = orgFileName.substring(0,point);
								String ext = orgFileName.substring(point+1);
								
								System.out.println("new file name" + fileName);
								f = new File(path, fileName+"("+ renameNum+")."+ext);
								if(!f.exists()) {// ���� ������ ���� ��ü�� ������
									orgFileName = f.getName();
									break;
								}								
							}
						}//f.exists end
						//4. ���� ���ε�
						try {
							mf.transferTo(f); //���� ���ε尡 �߻���.
						}catch(Exception e) {
							
						}
						
						//5. ���ε���(���ο� ���ϸ�)or(���� ���ϸ�) �� vo�� ����
						if(cnt == 1) vo.setFilename1(orgFileName);
						if(cnt == 2) vo.setFilename2(orgFileName);
						cnt++;
					}
					
				}
			}
			//DB��� 
			service.dataInsert(vo);
			
			//���ڵ� �߰� ����
			String msg = getSuccessMessage("�ڷ�� �� ��� �Ϸ�","/myapp/data/dataList");
			
			//���ε� �Ϸ�
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			//���ڵ� �߰� ����
			
			//���� �����
			fileDelete(path,vo.getFilename1());
			fileDelete(path,vo.getFilename2());
			
			//�޼���
			String msg = getFailMessage("�ڷ�� �� ��� ����");
			
			//������������ ������
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//���� �����
	public void fileDelete(String p, String f) {
		if( f != null) {
			File file = new File(p, f);
			file.delete();
		}
	}
	
	//�ڷ�� ���� ����
	@GetMapping("/data/view")
	public ModelAndView view(int no) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	
	//�ڷ�� ���� ��
	@GetMapping("/data/dataEdit")
	public ModelAndView editForm(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataSelect(no);
		
		//DB�� ÷�ε� ���� ���� ���Ѵ�.
		
		int fileCount = 1; //ù��° ÷�������� ������ ����.
		
		if(vo.getFilename2() != null) {
			fileCount++;
		}
		
		mav.addObject("fileCount",fileCount);
		mav.addObject("vo",service.dataSelect(no));
		mav.setViewName("data/dataEdit");
		return mav;
	}
	
	//�ڷ�� ����
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
			String msg = getSuccessMessage("�ڷ�� ���� ����","");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	//�� ���� �޼���
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
