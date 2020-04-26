package com.example.notice.service.impl;

import com.example.notice.model.Notice;
import com.example.notice.repository.NoticeRepository;
import com.example.notice.service.CrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NoticeRepository noticeRepository;

    private final String url = "http://tour.interpark.com/customer/noticeview.aspx?seq=9245&PageNum=1&class=&Gubun=&keyWord=";

    private final String airport = "<제주항공>";

    @Override
    public Notice saveJejuAirNotice() {
        try {
            Document doc = Jsoup.parse(restTemplate.getForObject(url, String.class));
            Elements elems = doc.select(".editor-view p");

            StringBuffer jejuAirNotice = new StringBuffer();
            boolean jejuAirNoti = false;

            for (Element elem : elems) {
                String air = elem.select("strong").text();

                if (air.equals(airport)) {
                    jejuAirNoti = true;
                }

                if (jejuAirNoti) {
                    String content = elem.toString();


                    if (content.contains("--------------------")) {
                        break;
                    } else {
                        jejuAirNotice.append(content);
                    }
                }
            }

            List<Notice> notices = noticeRepository.findAll();

            // 기존 공지사항과 안겹치면 저장
            boolean overlap = false;

            if (!notices.isEmpty()) {
                for (Notice notice : notices) {
                    if (notice.getContent().equals(jejuAirNotice.toString())) {
                        overlap = true;
                        break;
                    }
                }
            }

            if (!overlap) {
                Notice notice = new Notice();
                notice.setContent(jejuAirNotice.toString());
                notice = noticeRepository.saveAndFlush(notice);
                return notice;
            }
        } catch (Exception e) {

        }

        return null;
    }
}
