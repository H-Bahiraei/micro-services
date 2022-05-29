package ir.bki.otpservice.service.impl;

import ir.bki.otpservice.model.ResponseDto;

import java.util.List;

/**
 * @author Bahiraei
 */
public interface ResponseDtoService {

    public void createRdtoIndexBulk(final List<ResponseDto> rDtoList);

    public void createRdtoIndex(final ResponseDto responseDto );

}
