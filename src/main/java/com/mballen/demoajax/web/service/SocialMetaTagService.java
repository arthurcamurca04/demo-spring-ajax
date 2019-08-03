package com.mballen.demoajax.web.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mballen.demoajax.web.model.SocialMetaTag;

@Service
public class SocialMetaTagService {
	
	public static Logger log = LoggerFactory.getLogger(SocialMetaTagService.class);
	
	
	public SocialMetaTag getSocialMetaTagByUrl(String url) {
		
		SocialMetaTag twitter = getTwitterCardByUrl(url);
		if(!isEmpty(twitter)) {
			return  twitter;
		}
		
		SocialMetaTag og = getOpenGraphByUrl(url);
		if(!isEmpty(og)) {
			return og;
		}
		
		return null;
	}

	private SocialMetaTag getTwitterCardByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();
		try {
			Document document = Jsoup.connect(url).get();
			tag.setTitle(document.head().select("meta[name=twitter:title]").attr("content"));
			tag.setSite(document.head().select("meta[name=twitter:site]").attr("content"));
			tag.setImage(document.head().select("meta[name=twitter:image]").attr("content"));
			tag.setUrl(document.head().select("meta[name=twitter:url]").attr("content"));
			
		} catch (IOException e) {
			
			log.error(e.getMessage(), e.getCause());
		}
		
		return tag;
	}	
	
	private SocialMetaTag getOpenGraphByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();
		try {
			Document document = Jsoup.connect(url).get();
			tag.setTitle(document.head().select("meta[property=og:title]").attr("content"));
			tag.setSite(document.head().select("meta[property=og:site_name]").attr("content"));
			tag.setImage(document.head().select("meta[property=og:image]").attr("content"));
			tag.setUrl(document.head().select("meta[property=og:url]").attr("content"));
			
		} catch (IOException e) {
			
			log.error(e.getMessage(), e.getCause());
		}
		
		return tag;
	}
	
	private boolean isEmpty(SocialMetaTag tag) {
		if(tag.getImage().isEmpty()) return true;
		if(tag.getSite().isEmpty()) return true;
		if(tag.getTitle().isEmpty()) return true;
		if(tag.getUrl().isEmpty()) return true;
		
		return false;
	}
}
