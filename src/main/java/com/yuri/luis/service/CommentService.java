package com.yuri.luis.service;

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
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	/*
	 * @method = retorna todos os commentarios do DB.
	 */
	public List<Comment> findAllComments() { // OK
		return commentRepository.findAll();
	}

	public Comment findById(String id) {// OK
		Optional<Comment> obj = commentRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public void insert(Comment comment, String id) { // OK
		Optional<User> user = userRepository.findById(id);
		comment.setAuthorComment(new AuthorDTO(user.get()));
		commentRepository.save(comment);
	}

	public void delete(Post post, String id) { //OK
		Optional<Comment> comment = commentRepository.findById(id);
		if (id != null) {
			findById(id);
			for (Comment c : post.getComments()) {
				if (c.getId().equals(id)) {
					commentRepository.deleteById(id);
				}
				post.getComments().remove(comment.get());
				postRepository.save(post);
				break;
			}
		}
	}

	public Comment update(Comment obj, String id) { // Checar se atualiza no post. OK
		Comment newObj = findById(obj.getId());

		Optional<Post> post = postRepository.findById(id);
		List<Comment> listaComentario = post.get().getComments();

		for (Comment c : listaComentario) {
			if (c.equals(newObj)) {
				updateData(newObj, obj);
				commentRepository.save(newObj);
				post.get().getComments().remove(c);
				post.get().getComments().add(newObj);
				postRepository.save(post.get());
				break;
			}

		}

		return newObj;
	}

	private void updateData(Comment newObj, Comment obj) { //OK
		newObj.setText(obj.getText());
		newObj.setDate(obj.getDate());
		newObj.setAuthorComment(obj.getAuthorComment());
	}
}
