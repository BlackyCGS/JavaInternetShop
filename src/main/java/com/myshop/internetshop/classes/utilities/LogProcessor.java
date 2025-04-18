package com.myshop.internetshop.classes.utilities;

import com.myshop.internetshop.classes.dto.LogStatusDto;
import com.myshop.internetshop.classes.enums.LogStatusEnum;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;


@Component
@Slf4j
public class LogProcessor {

    Logger logger = LoggerFactory.getLogger(LogProcessor.class);
    @Async
    public void processLogs(String startDate, String endDate,
                            String taskId, Map<String, LogStatusDto> tasks) {
        LogStatusDto task = tasks.get(taskId);
        try {
            sleep(45000);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            List<Path> logFiles = new ArrayList<>();
            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                String fileName =
                        "logs/internetShop" + task.getAdditional() + "-" + formatter.format(date) +
                                ".log";
                Path path = Paths.get(fileName);
                if (Files.exists(path)) {
                    logFiles.add(path);
                }
            }
            if(logFiles.isEmpty()) {
                throw new NotFoundException("There are no logs with this period: "+ startDate + " - " + endDate);
            }
            Path logPath = Paths.get("logs/internetShopGeneratedLog-"+task.getId()+".log");
            try (BufferedWriter writer = Files.newBufferedWriter(logPath)) {
                for (Path path : logFiles) {
                    List<String> lines = Files.readAllLines(path);
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            task.setStatus(LogStatusEnum.SUCCESS);
            task.setLogPath(logPath.toString());
        }
        catch (Exception e) {
            task.setStatus(LogStatusEnum.FAIL);
            task.setErrorMsg(e.getMessage());
            logger.error("Error generationg logs in date range: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    @Async
    public void deleteLogFileByUid(String id, Map<String, LogStatusDto> tasks) {
        try {
            LogStatusDto task = tasks.get(id);
            sleep(15000);
            if (task == null) {
                return;
            }
            Path path = Paths.get(task.getLogPath());
            if (Files.exists(path)) {
                Files.delete(path);
            }

            tasks.remove(id);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
