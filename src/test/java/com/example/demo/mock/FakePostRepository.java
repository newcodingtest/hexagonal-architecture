package com.example.demo.mock;

import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.domain.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    //소형 테스트는 싱글쓰레드 기준으로 테스트 코드를 작성하기 때문에 멀티쓰레드를 고려할 필요가 없음
    //때문에 단순 new ArrayList를 사용해도 무방하다.
    private final List<Post> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<Post> findById(long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() ==0 ){
            Post newPost = Post.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .writer(post.getWriter())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            data.add(newPost);
            return newPost;
        }
        else{
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }
}
