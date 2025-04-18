package com.myshop.internetshop.classes.services;



import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.myshop.internetshop.classes.dto.LogStatusDto;
import com.myshop.internetshop.classes.enums.LogStatusEnum;
import com.myshop.internetshop.classes.exceptions.InternalServerErrorException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.utilities.LogProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class LogService {

    Logger logger = LoggerFactory.getLogger(LogService.class);
    private final LogProcessor logProcessor;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Map<String, LogStatusDto> tasks = new ConcurrentHashMap<>();

    @Autowired
    public LogService(LogProcessor logProcessor) {
        this.logProcessor = logProcessor;
    }

    public ResponseEntity<InputStreamResource> getLogsByDate(String additional,
                                                             String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            String fileName =
                    "logs/internetShop" + additional + "-" + formatter.format(localDate) +
                            ".log";
            Path path = Paths.get(fileName);

            if (!Files.exists(path)) {
                throw new NotFoundException("There is no file with name" + fileName);
            }

            InputStreamResource resource =
                    new InputStreamResource(new FileInputStream(fileName));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + path.getFileName())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public String startGeneration(String additional, String start, String end){
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        String id = UUID.randomUUID().toString();
        LogStatusDto task = new LogStatusDto();
        task.setId(id);
        task.setStatus(LogStatusEnum.PROCESSING);
        task.setAdditional(additional);
        tasks.put(id, task);
        logProcessor.processLogs(start, end, task.getId(), tasks);

        return id;
    }

    public LogStatusDto getTaskById(String id) {
        LogStatusDto task = tasks.get(id);

        if (task == null) {
            throw new NotFoundException("There is no task with this id: " + id);
        }
        return task;
    }

    public ResponseEntity<InputStreamResource> getGeneratedLogsById(String id) {

            LogStatusDto task = this.getTaskById(id);
            if (task == null) {
                throw new NotFoundException("Log file request does not exist");
            }
            if(task.getStatus() != LogStatusEnum.SUCCESS) {
                throw new NotFoundException("Log file is not ready yet. Check its");
            }
        try {
            Path path = Paths.get(task.getLogPath());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + path.getFileName())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new InputStreamResource(new FileInputStream(path.toFile())));

        } catch (Exception e) {
            throw new InternalServerErrorException("Unexpected error: " + e.getMessage());
        }
    }

    public void deleteLogFileByUid(String id) {
        logProcessor.deleteLogFileByUid(id, tasks);
    }
}
