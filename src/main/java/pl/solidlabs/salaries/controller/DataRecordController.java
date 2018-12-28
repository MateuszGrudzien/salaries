package pl.solidlabs.salaries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.solidlabs.salaries.data.DataRecordDto;
import pl.solidlabs.salaries.service.DataRecordService;
import pl.solidlabs.salaries.validator.DataRecordInputValidator;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.solidlabs.salaries.configuration.UrlConfiguration.DATA_RECORD_URL;

@RestController
public class DataRecordController {

    @Autowired
    private DataRecordService service;

    @RequestMapping(value = DATA_RECORD_URL, method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity postRecord(@RequestBody DataRecordDto dataRecordDto) {
        List<String> validationErrors = DataRecordInputValidator.validate(dataRecordDto);
        if (validationErrors.isEmpty()) {
            DataRecordDto persistedRecord = service.saveDataRecord(dataRecordDto);
            return new ResponseEntity<>(persistedRecord, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(validationErrors).toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = DATA_RECORD_URL, method = GET)
    public ResponseEntity getAll() {
        List<DataRecordDto> response = service.getAllDataRecords();
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
