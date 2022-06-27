package ir.bki.otpservice.service.ELS;

import ir.bki.otpservice.repository.model.ResponseDto;

import java.util.List;

/**
 * @author Bahiraei
 */
public interface ResponseDtoService {

    public void createRdtoIndexBulk(final List<ResponseDto> rDtoList);

    public void createRdtoIndex(final ResponseDto responseDto );

}
