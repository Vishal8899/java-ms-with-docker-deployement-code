package com.hcl.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.hcl.moviecatalogservice.models.CatalogItem;
import com.hcl.moviecatalogservice.models.Movie;
import com.hcl.moviecatalogservice.models.Rating;
import com.hcl.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	

    @Autowired
    private RestTemplate restTemplate;

    
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
	    
	    public CatalogItem getFallbackCatalogItem(Rating rating) {
	    	
	    	return new CatalogItem("Movie name not found", "", rating.getRating());
	    	
	    }
	    

}
