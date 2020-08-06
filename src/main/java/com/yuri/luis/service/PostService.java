package com.yuri.luis.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuri.luis.dto.AuthorDTO;
import com.yuri.luis.model.Comment;
import com.yuri.luis.model.Post;
import com.yuri.luis.model.User;
import com.yuri.luis.repository.CommentRepository;
import com.yuri.luis.repository.PostRepository;
import com.yuri.luis.repository.UserRepository;
import com.yuri.luis.service.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Post> findAll() { // OK
		return postRepository.findAll();
	}

	public Post findById(String id) { // OK
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public void insertCommentToPost(Post post, String id) {
		Optional<Comment> comment = commentRepository.findById(id);
		post.getComments().add(comment.get());
		postRepository.save(post);
	}
	
	/*Salva o novo post com o usuario que criou o post!*/
	public void insert(Post post, String id) { //OK
		Optional<User> user = userRepository.findById(id);
		if(post != null) {
			post.setAuthor(new AuthorDTO(user.get()));
			user.get().getPosts().add(post);
			postRepository.save(post);
			userRepository.save(user.get());
		}
	}

	public void delete(String id) { //OK
		Post post = findById(id); 
		deletaOsComentariosDoPost(post);
		post.getComments().clear();
		postRepository.deleteById(id);
	}
	
	public void deletaOsComentariosDoPost(Post post) {
		List<Comment> listaComentarios = commentRepository.findAll();
		List<Comment> listaComentariosDoPost = post.getComments();
		
		listaComentarios.forEach(comentario ->{
			listaComentariosDoPost.forEach(comentarioPost ->{
				if(comentario.equals(comentarioPost)) {
					String id = comentarioPost.getId();
					commentRepository.deleteById(id);
				}
			});
		});
	}
	
	public Post update(Post obj) { //OK
		Post newObj = findById(obj.getId());
		if(obj.equals(newObj)) {
			updateData(newObj, obj);
			return postRepository.save(newObj);
		}
		
		return null;
	}

	private void updateData(Post newObj, Post obj) { //OK
		newObj.setDate(obj.getDate());
		newObj.setTitle(obj.getTitle());
		newObj.setBody(obj.getBody());
		newObj.setAuthor(obj.getAuthor());
		newObj.setComments(obj.getComments());
	}
	
	public List<Post> findByTitle(String text) {
		return postRepository.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return postRepository.fullSearch(text, minDate, maxDate);
	}

}
