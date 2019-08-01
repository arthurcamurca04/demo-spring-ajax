package com.mballen.demoajax.web.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.mballen.demoajax.web.model.SocialMetaTag;

@Service
public class SocialMetaTagService {

	public SocialMetaTag getOpenGraphByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();
		try {
			Document document = Jsoup.connect(url).get();
			tag.setTitle(document.head().select("meta[property=og:title]").attr("content"));
			tag.setSite(document.head().select("meta[property=og:site_name]").attr("content"));
			tag.setImage(document.head().select("meta[property=og:image]").attr("content"));
			tag.setUrl(document.head().select("meta[property=og:url]").attr("content"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return tag;
	}
}
