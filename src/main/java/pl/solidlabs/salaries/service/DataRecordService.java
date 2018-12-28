package pl.solidlabs.salaries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.solidlabs.salaries.data.DataRecord;
import pl.solidlabs.salaries.data.DataRecordDto;
import pl.solidlabs.salaries.data.DataRecordRepository;

import java.util.Date;

@Service
public class DataRecordService {

    @Autowired
    private DataRecordRepository repository;

    public DataRecordDto saveDataRecord(DataRecordDto dataRecordDto) {
        dataRecordDto.setRecordDate(new Date());
        DataRecord entity = new DataRecord(dataRecordDto);
        repository.saveAndFlush(entity);

        return new DataRecordDto(entity);
    }
}
