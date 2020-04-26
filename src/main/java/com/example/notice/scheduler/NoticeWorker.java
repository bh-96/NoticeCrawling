package com.example.notice.scheduler;

import com.example.notice.model.Notice;
import com.example.notice.service.CrawlerService;
import com.example.notice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NoticeWorker {

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 */30 * * * ?")
    public void sendNotice() {
        Notice notice = crawlerService.saveJejuAirNotice();

        if (notice != null) {
            mailService.sendMail("96bohyun@naver.com", "[제주항공] 항공권 취소 수수료 안내", notice.getContent());
        }
    }
}
