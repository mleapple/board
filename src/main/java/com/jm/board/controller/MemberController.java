package com.jm.board.controller;

import com.jm.board.config.SessionConst;
import com.jm.board.entity.*;
import com.jm.board.file.FileStore;
import com.jm.board.mapperRepository.Membercrud;
import com.jm.board.mapperRepository.Profilecrud;
import com.jm.board.upload.UploadFile;
import com.jm.board.valid.LoginValidator;
import com.jm.board.valid.MemberValidator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {


    private final Membercrud membercrud;

    private final Profilecrud profilecrud;

    private final MemberValidator memberValidator;

    private final LoginValidator loginValidator;

    private final FileStore fileStore;



    @InitBinder("loginForm")
    public void initDataBinder(WebDataBinder dataBinder)
    {
        dataBinder.addValidators(loginValidator );
    }

    @InitBinder("member")
    public void initDataMemeBinder(WebDataBinder dataBinder)
    {
        dataBinder.addValidators(memberValidator );
    }


    @GetMapping("/join")
    public String joinform(Model model) {
        model.addAttribute("member", new Member());
        return "/views/board/member/joinform";
    }

    @PostMapping("/new")
    public String newMember(@Validated @ModelAttribute Member member
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Model model ) {

       // memberValidator.validate(member, bindingResult);
        //
        // 아이디
     /*   if(!StringUtils.hasText(member.getMemid())) {
            bindingResult.addError(new FieldError("member","memid","아이디는  필수 입니다"));
        }
        // 이름
        if(!StringUtils.hasText(member.getName())) {
            bindingResult.addError(new FieldError("member", "name", "이름은 필수 입니다"));
        }
        //비넌
        if(!StringUtils.hasText(member.getPassword())) {
            bindingResult.addError(new FieldError("member","password","비밀번호는 필수 입니다"));
        }*/

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "/views/board/member/joinform";
        }
        int n =  membercrud.memberInsert(member);
        // 회원 가입 완료 되면 게사판 메인으로

       // redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String loginform(Model model){
        model.addAttribute("loginForm" , new LoginForm());
        return "/views/board/member/loginform";
    }


    @PostMapping("/login")
    public String loginProc(@Validated @ModelAttribute  LoginForm loginForm , BindingResult bindingResult ,
            @RequestParam(defaultValue = "/") String redirectURL
            , HttpServletRequest request){


       //loginValidator.validate(loginForm,bindingResult);
        // 로그체크
        if(bindingResult.hasErrors()){
            return "/views/board/member/loginform";
        }
        Member mem = membercrud.findByLoginId(loginForm.getMemid()  , loginForm.getPassword());
        if(mem == null) {

            bindingResult.reject("loginFail",  "아이디 또는 비밀번호가 맞지 않습니다");
            return "/views/board/member/loginform";
        }

        // 정보 존재함
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER ,mem );
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 존재하면 세션 가져 오고 없으면 null 반환 한다
        if(session !=null){
            session.invalidate(); // 세션이 존재하면 세션 무효화
        }
        return "redirect:/";
    }

    @GetMapping("/profileupload")
    public String profileupload(Model model){
        model.addAttribute("profileForm", new ProfileForm());
        return "/views/board/member/profileform"; //
    }

    @PostMapping("/profileupload")
    public String profileuploadproc(@ModelAttribute ProfileForm form , RedirectAttributes redirectAttributes) throws IOException {
        // 단건 업로드
        UploadFile uploadFile = fileStore.storeFile(form.getAttachfile());
        // 여러파일  업로드
        List<UploadFile> uploadFileList = fileStore.storeFiles(form.getImageFiles());

        ProfileVo profileVo = new ProfileVo();
        profileVo.setMidx(form.getMidx());
        profileVo.setNicname(form.getNicname());

        int fidx = profilecrud.profileInsert(profileVo);

        log.info("fid={}",fidx);
       Fitem fitem = new Fitem();
        fitem.setMidx(profileVo.getMidx());
        fitem.setStoreFileName(uploadFile.getStoreFileName());
        fitem.setUuidFileName(uploadFile.getUploadFileName());

        profilecrud.filePathInsert(fitem);

        uploadFileList.stream().forEach(upf->{Fitems fitems = new Fitems();
                                            fitems.setMidx(profileVo.getMidx());
                                            fitems.setStoreFileName(upf.getStoreFileName());
                                            fitems.setUuidFileName(upf.getUploadFileName());
                                            profilecrud.imgfilePathInsert(fitems);
        });




        // 저장하기
        redirectAttributes.addAttribute("fitem" , fitem.getMidx());
        return  "redirect:/member/profileview/{fitem}";

    }
    @GetMapping("/profileview/{id}")
    public String profileview(@PathVariable Long id , Model model){


        ProfileVo pv =   profilecrud.profilefindById(id);

       Fitem  fi  =  profilecrud.filePathfilefindById(id);
        List<Fitems>  f2 =  profilecrud.imgfilePathfindById(id);

        ProFileItem proFileItem = new ProFileItem();
        proFileItem.setNicname(pv.getNicname() );
        proFileItem.setAttachfilename(fi.getStoreFileName());
        proFileItem.setImageFiles(f2);
        proFileItem.setMidx(pv.getMidx());
        model.addAttribute("profileForm", proFileItem);
        return "/views/board/member/profileview"; //
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }



    @GetMapping("/attach/{midx}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long midx) throws MalformedURLException {
       // Item item = itemRepository.findById(midx);

        Fitem  fi  =  profilecrud.filePathfilefindById(midx);

       /* String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();*/
        String storeFileName  = fi.getStoreFileName();
        String uploadFileName = fi.getUuidFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }




}
