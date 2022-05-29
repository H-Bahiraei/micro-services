package ir.bki.otpservice.service;

import ir.bki.otpservice.model.ResponseDto;
import ir.bki.otpservice.repository.ResponseDtoRepository;
import ir.bki.otpservice.service.impl.ResponseDtoService;
import ir.bki.otpservice.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Bahiraei
 */
@Service
public class ResponseDtoServiceImpl implements ResponseDtoService {

    @Autowired
    private ResponseDtoRepository responseDtoRepository ;

    public void createRdtoIndex(final ResponseDto responseDto ){
        System.out.println("########################");
        System.out.println("responseDto = " + responseDto);
        System.out.println("########################");

        if(AppUtil.isEnabledElastic)
        responseDtoRepository.save(responseDto );
    }


    public void createRdtoIndexBulk(final List<ResponseDto> rDtoList){
        responseDtoRepository.saveAll(rDtoList);
    }


}
