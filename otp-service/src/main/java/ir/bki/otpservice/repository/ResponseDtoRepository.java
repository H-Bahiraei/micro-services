package ir.bki.otpservice.repository;

import ir.bki.otpservice.repository.model.ResponseDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahiraei
 */
@Repository
public interface ResponseDtoRepository extends ElasticsearchRepository<ResponseDto,String> {

}
