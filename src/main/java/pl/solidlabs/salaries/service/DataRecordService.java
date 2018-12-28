package pl.solidlabs.salaries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.solidlabs.salaries.data.DataRecord;
import pl.solidlabs.salaries.data.DataRecordDto;
import pl.solidlabs.salaries.data.DataRecordRepository;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public List<DataRecordDto> getAllDataRecords() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(DataRecordDto::new)
                .collect(toList());
    }
}
