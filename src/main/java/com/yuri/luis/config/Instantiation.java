package com.yuri.luis.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.yuri.luis.dto.AuthorDTO;
import com.yuri.luis.model.Comment;
import com.yuri.luis.model.Post;
import com.yuri.luis.model.User;
import com.yuri.luis.repository.CommentRepository;
import com.yuri.luis.repository.PostRepository;
import com.yuri.luis.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;
	
	@Autowired
	private PostRepository postReposiroty;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userReposiroty.deleteAll();
		postReposiroty.deleteAll();
		commentRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com", "123456789");
		User alex = new User(null, "Alex Green", "alex@gmail.com", "123456789");
		User bob = new User(null, "Bob Grey", "bob@gmail.com", "123456789");
		
		userReposiroty.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu Viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		Comment c1 = new Comment(null,"Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		Comment c2 = new Comment(null,"Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		Comment c3 = new Comment(null,"Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		
		commentRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postReposiroty.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userReposiroty.save(maria);

		
	}
}
