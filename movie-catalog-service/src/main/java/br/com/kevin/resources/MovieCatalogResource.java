package br.com.kevin.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.kevin.models.CatalogItem;
import br.com.kevin.models.Movie;
import br.com.kevin.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<Rating> ratings = Arrays.asList(
			new Rating("123", 3),
			new Rating("321", 2)
		);
			
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getRating(), Movie.class);
			return new CatalogItem(movie.getName(), "test", rating.getRating());
		}).collect(Collectors.toList());
	}
}
