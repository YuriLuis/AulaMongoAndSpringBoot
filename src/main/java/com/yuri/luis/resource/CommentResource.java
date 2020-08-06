package com.yuri.luis.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yuri.luis.model.Comment;
import com.yuri.luis.model.Post;
import com.yuri.luis.service.CommentService;

@Controller
@RequestMapping(value = "/comments")
public class CommentResource {

	@Autowired
	private CommentService commentService;	

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> findAll() {
		List<Comment> list = commentService.findAllComments();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Comment> findById(@PathVariable String id) {
		Comment obj = commentService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Comment comment, @PathVariable String id) {
		
					/*>>>>ID = id do usuario!<<<<<<<*/
		
		commentService.insert(comment, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Post post ,@PathVariable String id) {
		commentService.delete(post, id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Comment obj, @PathVariable String id) {
				obj = commentService.update(obj, id);
		return ResponseEntity.noContent().build();
	}
}
